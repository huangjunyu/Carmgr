package com.yiwucheguanjia.merchantcarmgr.city;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.city.adapter.CitySortAdapter;
import com.yiwucheguanjia.merchantcarmgr.city.adapter.HotCityRecyclerAdapter;
import com.yiwucheguanjia.merchantcarmgr.city.adapter.SecondCityItemAdapter;
import com.yiwucheguanjia.merchantcarmgr.city.bean.CityModel;
import com.yiwucheguanjia.merchantcarmgr.city.bean.RegionInfo;
import com.yiwucheguanjia.merchantcarmgr.city.bean.SecondCityModel;
import com.yiwucheguanjia.merchantcarmgr.city.db.RegionFunction;
import com.yiwucheguanjia.merchantcarmgr.city.utils.ClearEditText;
import com.yiwucheguanjia.merchantcarmgr.city.utils.KeyBoard;
import com.yiwucheguanjia.merchantcarmgr.city.utils.LocationPosition;
import com.yiwucheguanjia.merchantcarmgr.city.utils.PinyinComparator;
import com.yiwucheguanjia.merchantcarmgr.city.utils.ScreenUtils;
import com.yiwucheguanjia.merchantcarmgr.city.utils.SharedPreferencesUtils;
import com.yiwucheguanjia.merchantcarmgr.city.widget.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 城市选择的主类
 */
public class CityActivity extends Activity {
    private LocationClient mLocationClient;
    //设置布局管理器
    LinearLayoutManager linearLayoutManager, linearLayoutManager2, linearLayoutManager3;
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
    private RecyclerViewHeader rvHeader;

    //定位相关

    public LocationPosition.MyLocationListener myLocationListener;
    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Battery_Saving;
    private final String tempcoor="gcj02";
    public MyLocationListener mMyLocationListener;
    private Button testlocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        //StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.orange),0);
        setContentView(R.layout.activity_city);
        testlocation = (Button)findViewById(R.id.testlocation);
        testlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("nwk","kkw");
                mLocationClient.start();
            }
        });
        popupwindowinflater = LayoutInflater.from(CityActivity.this);
        mLocationClient = new LocationClient(CityActivity.this);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        initData();
        initViews();
        setListener();
        setPopupwindow();
        popupwindowRv.setLayoutManager(linearLayoutManager);
        hotCityRv.setLayoutManager(linearLayoutManager2);
        citySortRv.setLayoutManager(linearLayoutManager3);
        rvHeader.attachTo(citySortRv, true);
        InitLocation();
    }

    private void initData() {
        linearLayoutManager = new LinearLayoutManager(CityActivity.this);
        linearLayoutManager2 = new LinearLayoutManager(CityActivity.this);
        linearLayoutManager3 = new LinearLayoutManager(CityActivity.this);
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
        rvHeader = (RecyclerViewHeader) findViewById(R.id.recyclerview_header);
        citySortRv = (RecyclerView) findViewById(R.id.level_city_rv);
        hotCityRv = (RecyclerView) findViewById(R.id.gridview_hot);
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        clearEditText = (ClearEditText) findViewById(R.id.filter_edit);
        topRl = (RelativeLayout) findViewById(R.id.rl_top);
        hotCityRvAdapter = new HotCityRecyclerAdapter(this, hotCity);
        citySortAdapter = new CitySortAdapter(CityActivity.this, SourceDateList);
        hotCityRv.setAdapter(hotCityRvAdapter);
        sideBar.setTextView(dialog);
        citySortRv.setAdapter(citySortAdapter);

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
                    citySortRv.scrollToPosition(position);
                }

            }
        });

        //根据输入框输入值的改变来过滤搜索
        clearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                Log.e("clear", s.toString());
                //filterData(s.toString());
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
        //backgroundAlpha(0.5f);
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
                SecondCityItemAdapter secondCityItemAdapter = new SecondCityItemAdapter(CityActivity.this, secondCityModels);
                popupwindowRv.setAdapter(secondCityItemAdapter);
                popupWindow.showAsDropDown(clearEditText, citySortRv.getWidth(), 0);
                ArrayList<String> earList = new ArrayList<>();
                SharedPreferencesUtils.clearData(CityActivity.this);
                SharedPreferencesUtils.saveAreaName(CityActivity.this, "全部", 0);
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        KeyBoard.closeSoftKeyboard(CityActivity.this);
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Log.e("citylo","kkew");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void InitLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setLocationMode(tempMode);//设置定位模式
        option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
        int span=1000;
//        try {
//            span = Integer.valueOf(frequence.getText().toString());
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//        option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

}
