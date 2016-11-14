package com.yiwucheguanjia.merchantcarmgr.city;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.city.adapter.CitySortAdapter;
import com.yiwucheguanjia.merchantcarmgr.city.adapter.HotCityRecyclerAdapter;
import com.yiwucheguanjia.merchantcarmgr.city.adapter.SecondCityItemAdapter;
import com.yiwucheguanjia.merchantcarmgr.city.adapter.SortAdapter;
import com.yiwucheguanjia.merchantcarmgr.city.bean.CityModel;
import com.yiwucheguanjia.merchantcarmgr.city.bean.RegionInfo;
import com.yiwucheguanjia.merchantcarmgr.city.bean.SecondCityModel;
import com.yiwucheguanjia.merchantcarmgr.city.db.RegionFunction;
import com.yiwucheguanjia.merchantcarmgr.city.utils.ClearEditText;
import com.yiwucheguanjia.merchantcarmgr.city.utils.KeyBoard;
import com.yiwucheguanjia.merchantcarmgr.city.utils.PinyinComparator;
import com.yiwucheguanjia.merchantcarmgr.city.utils.ScreenUtils;
import com.yiwucheguanjia.merchantcarmgr.city.utils.SharedPreferencesUtils;
import com.yiwucheguanjia.merchantcarmgr.city.widget.SideBar;
import com.yiwucheguanjia.merchantcarmgr.utils.CheckPermissionsActivity;
import com.yiwucheguanjia.merchantcarmgr.utils.RecyclerViewDivider;
import com.yiwucheguanjia.merchantcarmgr.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 城市选择的主类
 */
public class CityActivity extends CheckPermissionsActivity {

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    //设置布局管理器
    LinearLayoutManager linearLayoutManager, linearLayoutManager2;
    GridLayoutManager linearLayoutManager3;
    private List<RegionInfo> countyList;//用于存放县一级的城市名
    private List<RegionInfo> cityList;//用于存放城市名（二级城市，省下直接下辖单位）
    private List<RegionInfo> provinceList;//用于存放省级地名（江苏、北京、山东等）
    private List<String> countyName;
    private List<String> cityName;
    private List<String> provinceName;
    private RecyclerView citySortRv;
    private SideBar sideBar;
    private TextView dialog;
    private CitySortAdapter citySortAdapter;
    private SortAdapter adapter;
    private ListView sortListView;
    private ClearEditText clearEditText;
    private ArrayList<RegionInfo> hotCity;//热门城市列表
    private HotCityRecyclerAdapter hotCityRvAdapter;
    private List<CityModel> SourceDateList;//用于存放排序后的二级城市，最主要的功能
    //新增存放二级城市的列表
    private PopupWindow popupWindow;
    private LayoutInflater popupwindowinflater;
    private RecyclerView popupwindowRv;
    //根据拼音来排列ListView里面的数据类
    private PinyinComparator pinyinComparator;
    RelativeLayout topRl;
    private ArrayList<SecondCityModel> secondCityModels;
    private RecyclerView hotCityRv;
    private RelativeLayout positionRl;
    private TextView positionTv;
    private Button testlocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化地图定位功能
        initMap();
        initLocation();
        startLocation();
        // 透明状态栏
        setContentView(R.layout.activity_city);
        popupwindowinflater = LayoutInflater.from(CityActivity.this);
        initData();
        initViews();
        setListener();
        setPopupwindow();
        popupwindowRv.setLayoutManager(linearLayoutManager);
        hotCityRv.setLayoutManager(linearLayoutManager2);
    }

    private void initMap(){
        locationOption = new AMapLocationClientOption();
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
    }

    private void initData() {
        linearLayoutManager = new LinearLayoutManager(CityActivity.this);
        linearLayoutManager2 = new LinearLayoutManager(CityActivity.this);
        linearLayoutManager3 = new GridLayoutManager(CityActivity.this,1);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);

        //获取所有的省级城市
        provinceList = RegionFunction.getProvencesOrCity(1);
        //获取数据库中的所有二级城市信息
        cityList = RegionFunction.getProvencesOrCity(2);
        //获取数据库中所有的三级城市信息
        countyList = RegionFunction.getProvencesOrCity(3);
        provinceName = new ArrayList<>();
        cityName = new ArrayList<>();
        for (RegionInfo info : provinceList) {
            provinceName.add(info.getName().trim());
        }
        //四个直辖市，港、澳、台特殊处理
        provinceName.remove("北京");
        provinceName.remove("天津");
        provinceName.remove("上海");
        provinceName.remove("重庆");
        provinceName.remove("香港");
        provinceName.remove("澳门");
        provinceName.remove("台湾");

        for (RegionInfo info : cityList) {
            cityName.add(info.getName().trim());
        }
        cityName.add("北京");
        cityName.add("天津");
        cityName.add("上海");
        cityName.add("重庆");
        cityName.add("香港");
        cityName.add("澳门");
        cityName.add("台湾");

        countyName = new ArrayList<>();
        for (RegionInfo info : countyList) {
            countyName.add(info.getName().trim());
        }

        //热门城市的数据初始化
        hotCity = new ArrayList<>();
        //手动设置热城市
        hotCity.add(new RegionInfo(2, 1, "北京", "B"));
        hotCity.add(new RegionInfo(25, 1, "上海", "S"));
        hotCity.add(new RegionInfo(77, 6, "深圳", "S"));
        hotCity.add(new RegionInfo(76, 6, "广州", "G"));
        hotCity.add(new RegionInfo(343, 1, "天津", "T"));
        pinyinComparator = new PinyinComparator();
        // 根据a-z进行排序源数据
        SourceDateList = filledData(cityList);
        Collections.sort(SourceDateList, pinyinComparator);
    }

    private void initViews() {
        View view = View.inflate(this, R.layout.city_rv_header, null);
        hotCityRv = (RecyclerView) view.findViewById(R.id.header_hot_city_rv);
        positionRl = (RelativeLayout) view.findViewById(R.id.header_position_rl);
        positionTv = (TextView) view.findViewById(R.id.header_position_tv);
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        clearEditText = (ClearEditText) findViewById(R.id.filter_edit);
        topRl = (RelativeLayout) findViewById(R.id.rl_top);
        hotCityRvAdapter = new HotCityRecyclerAdapter(this, hotCity);
        hotCityRv.setAdapter(hotCityRvAdapter);
        hotCityRv.addItemDecoration(new RecyclerViewDivider(this,LinearLayoutManager.HORIZONTAL,1,ContextCompat.getColor(this,R.color.gray_divide)));
        citySortAdapter = new CitySortAdapter(CityActivity.this, SourceDateList);
        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.addHeaderView(view);
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);
        sideBar.setTextView(dialog);
    }

    private void setListener() {
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                KeyBoard.closeSoftKeyboard(CityActivity.this);
                //该字母首次出现的位置
                int position = citySortAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                String cityName = ((CityModel) adapter.getItem(position - 1)).getName();
                if (cityName != null && cityName.length() > 0) {
                    Toast.makeText(CityActivity.this, cityName, Toast.LENGTH_SHORT).show();
                    KeyBoard.closeSoftKeyboard(CityActivity.this);
                    SharedPreferencesUtils.saveCityName(CityActivity.this, cityName);
                    searchKey(cityName);
                }
            }
        });

        //根据输入框输入值的改变来过滤搜索
        clearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                Log.e("clear", s.toString());
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setPopupwindow() {
        View view = popupwindowinflater.inflate(R.layout.popupwindow_list, null);
        popupwindowRv = (RecyclerView) view.findViewById(R.id.lsecond_city_rv);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.setWidth(ScreenUtils.getScreenW(CityActivity.this) / 2);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(false);
        //监听popupWindow消失状态并且实现想要的操作
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        if (popupWindow.isShowing()) {
            Log.e("wiwi", "show");
        }
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;// 0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * 为ListView填充数据
     */
    private List<CityModel> filledData(List<RegionInfo> date) {
        List<CityModel> mSortList = new ArrayList<>();

        for (int i = 0; i < date.size(); i++) {
            CityModel cityModel = new CityModel();
            String name = date.get(i).getName();
            String firstName = date.get(i).getFirstName();
            cityModel.setName(name);
            cityModel.setFirstName(firstName);
            mSortList.add(cityModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     */
    public void filterData(String keyword) {
        List<CityModel> filterDateList = new ArrayList<>();
        Log.e("keyword", keyword + "," + provinceName.size());
        if (TextUtils.isEmpty(keyword)) {
            filterDateList = SourceDateList;
        } else {
            if (provinceName.contains(keyword)) {
                filterDateList.clear();
                //匹配省级城市的名单
                for (int i = 0; i < provinceList.size(); i++) {
                    String name = provinceList.get(i).getName();
                    int id = provinceList.get(i).getId();
                    if (name.startsWith(keyword)) {
                        filterDateList.addAll(filledData(RegionFunction.getProvencesOrCityOnParent(id)));
                    }
                }
            } else if (cityName.contains(keyword)) {
                filterDateList.clear();
                //匹配二级城市菜单
                for (int i = 0; i < cityList.size(); i++) {
                    String name = cityList.get(i).getName();
                    int id = cityList.get(i).getId();
                    if (name.equals(keyword)) {
                        filterDateList.addAll(filledData(RegionFunction.getProvencesOrCityOnParent(id)));
                    }
                }
            } else if (!cityName.contains(keyword) && !provinceName.contains(keyword)) {
                filterDateList.clear();
                //模糊匹配二级城市
                for (CityModel cityModel : SourceDateList) {
                    String name = cityModel.getName();
                    String firstName = cityModel.getFirstName().toLowerCase();
                    if (name.indexOf(keyword.toString()) != -1 || firstName.startsWith(keyword.toString())) {
                        filterDateList.add(cityModel);
                    }
                }
                if (filterDateList.size() == 0) {
                    //二级城市匹配失败，匹配县级城市（三级城市）
                    for (int i = 0; i < countyList.size(); i++) {
                        String name = countyList.get(i).getName();
                        String firstName = countyList.get(i).getFirstName();
                        if (name.startsWith(keyword)) {
                            CityModel model = new CityModel();
                            model.setName(name);
                            model.setFirstName(firstName);
                            filterDateList.add(model);
                        }
                    }
                    if (filterDateList.size() == 0) {
                        //数据库中无法找到对应的信息，提示错误信息
                        Toast.makeText(CityActivity.this, "您输入的关键字有误，请重新输入！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        citySortAdapter.updateListView(filterDateList);
        KeyBoard.closeSoftKeyboard(CityActivity.this);
    }

    /**
     * 点击搜索列表项后，获得下属地区
     *
     * @param keyword
     */
    public void searchKey(String keyword) {
        List<CityModel> filterDateList = new ArrayList<>();
        if (TextUtils.isEmpty(keyword)) {
//            filterDateList = SourceDateList;
            finish();
            return;
        } else {
            if (provinceName.contains(keyword)) {
                filterDateList.clear();
                //匹配省级城市的名单
                for (int i = 0; i < provinceList.size(); i++) {
                    String name = provinceList.get(i).getName();
                    int id = provinceList.get(i).getId();
                    if (name.startsWith(keyword)) {
                        filterDateList.addAll(filledData(RegionFunction.getProvencesOrCityOnParent(id)));
                    }
                }
                //cityName存放的是区级市的下级市
            } else if (cityName.contains(keyword)) {
                filterDateList.clear();

                //点击区级城市后跳到这里
                //cityList已经从数据库里面查询出所有二级城市数据
                //匹配二级城市菜单
                for (int i = 0; i < cityList.size(); i++) {
                    String name = cityList.get(i).getName();
                    int id = cityList.get(i).getId();
                    if (name.equals(keyword)) {
                        filterDateList.addAll(filledData(RegionFunction.getProvencesOrCityOnParent(id)));
                    }
                }
            } else if (!cityName.contains(keyword) && !provinceName.contains(keyword)) {
                Log.e("serach", "search2");
                filterDateList.clear();
                //模糊匹配二级城市
                for (CityModel cityModel : SourceDateList) {
                    String name = cityModel.getName();
                    String firstName = cityModel.getFirstName().toLowerCase();
                    if (name.indexOf(keyword.toString()) != -1 || firstName.startsWith(keyword.toString())) {
                        filterDateList.add(cityModel);
                    }
                }
                if (filterDateList.size() == 0) {
                    //二级城市匹配失败，匹配县级城市（三级城市）
                    for (int i = 0; i < countyList.size(); i++) {
                        String name = countyList.get(i).getName();
                        String firstName = countyList.get(i).getFirstName();
                        if (name.startsWith(keyword)) {
                            CityModel model = new CityModel();
                            model.setName(name);
                            model.setFirstName(firstName);
                            filterDateList.add(model);
                        }
                    }
                    if (filterDateList.size() == 0) {
                        //数据库中无法找到对应的信息，提示错误信息
                        Toast.makeText(CityActivity.this, "您输入的关键字有误，请重新输入！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            //选择的城市有数据，将数据记录到全局变量里面
            if (filterDateList.size() > 0) {
                //setPopupwindow();
                secondCityModels = new ArrayList<>();
                for (int i = 1; i < filterDateList.size(); i++) {
                    SharedPreferencesUtils.saveAreaName(CityActivity.this, filterDateList.get(i).getName(), i);
                    SecondCityModel secondCityModel = new SecondCityModel();
                    secondCityModel.setSecondCityName(filterDateList.get(i).getName());
                    secondCityModels.add(secondCityModel);
                }
                SecondCityItemAdapter secondCityItemAdapter = new SecondCityItemAdapter(CityActivity.this, secondCityModels,handler);
                popupwindowRv.setAdapter(secondCityItemAdapter);
                popupWindow.showAsDropDown(clearEditText, sortListView.getWidth(), 0);
                ArrayList<String> earList = new ArrayList<>();
                SharedPreferencesUtils.clearData(CityActivity.this);
                SharedPreferencesUtils.saveAreaName(CityActivity.this, "全部", 0);
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        KeyBoard.closeSoftKeyboard(CityActivity.this);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    //如果不加这行代码，会报错
                    popupWindow.dismiss();
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
//        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        Log.e("location","location");

    }

    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
//    private AMapLocationClientOption getDefaultOption(){
//        AMapLocationClientOption mOption = new AMapLocationClientOption();
//        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
//        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
//        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
//        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
//        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是ture
//        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
//        mOption.setOnceLocationLatest(true);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
//        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
//        return mOption;
//    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                positionRl.setVisibility(View.VISIBLE);

                //将最后一个“市”字去掉，适配对数据库的检索
                positionTv.setText(loc.getCity());
                //解析定位结果
                String result = Utils.getLocationStr(loc);
            } else {
//                tvReult.setText("定位失败，loc is null");
            }
        }
    };


    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void startLocation(){
        locationOption.setOnceLocation(true);
        locationOption.setOnceLocationLatest(true);
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        locationOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是ture
        //根据控件的选择，重新设置定位参数
//        resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }
}
