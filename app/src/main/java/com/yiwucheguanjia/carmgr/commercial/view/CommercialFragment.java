package com.yiwucheguanjia.carmgr.commercial.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.my.SystemMsgActivity;
import com.yiwucheguanjia.carmgr.city.CityActivity;
import com.yiwucheguanjia.carmgr.city.utils.SharedPreferencesUtils;
import com.yiwucheguanjia.carmgr.commercial.controller.MerchantItemAdapter;
import com.yiwucheguanjia.carmgr.commercial.model.MerchantItemBean;
import com.yiwucheguanjia.carmgr.commercial.model.MerchantSelectItemBean;
import com.yiwucheguanjia.carmgr.commercial.controller.PopupWindowSimpleAdapter;
import com.yiwucheguanjia.carmgr.scanner.CaptureActivity;
import com.yiwucheguanjia.carmgr.utils.ScreenUtils;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by Administrator on 2016/6/20.
 */
public class CommercialFragment extends Fragment implements View.OnClickListener {
    private LinearLayout commercialView;
    private RelativeLayout businessSelectRl;
    private RelativeLayout merchantPersonalRl;
    private RelativeLayout sortSelectRl;//排序
    private RelativeLayout positionRl;
    private RelativeLayout citySelectRl;//城市选择
    private RelativeLayout scannerRl;//扫描
    private Spinner mySpinner;
    private ArrayList<MerchantSelectItemBean> businessSelectItemBeans;
    private MerchantSelectItemBean businessSelectItemBean;
    private MerchantItemAdapter itemAdapter;
    private ArrayList<MerchantItemBean> merchantItemBeens;
    private PopupWindow popupWindow;
    private PopupWindow popupWindowCity;
    private LayoutInflater popupwindowinflater;
    private ListView popupwindowListView;
    private PopupWindowSimpleAdapter popupWindowSimpleAdapter;
    private View popupDivision;
    private TextView businessSelectTxt;//业务选择
    private TextView sortSelectTv;//排序
    private TextView citySelectTv;//区选择
    private ImageView businessPullDownImg;
    private ImageView sortPullDownImg;
    private ImageView cityDirection;
    //盛装所有弹出项的item
    private List<Map<String, String>> businessList = new ArrayList<Map<String, String>>();
    private List<Map<String, String>> sortList = new ArrayList<>();
    private List<Map<String, String>> areaList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private RecyclerView myRecyclerView;
    private TextView starsTxt;
    private TextView positionTv;
    private TextView nothingTv;
    private String businessStr = "全部";//业务类型
    private String areaStr = "全部";//地区选择
    String[] businessArray = {"全部", "上牌", "驾考", "检车", "维修", "租车", "保养", "二手车",
            "车贷", "新车", "急救", "用品", "停车"};
    String[] sortArray = {"默认排序", "离我最近", "评价最高", "最新发布", "人气最高", "价格最低",
            "价格最高"};
    String[] areArray = {"全部", "天河区", "东山区", "白云区", "海珠区", "荔湾区", "越秀区", "黄埔区", "番禺区", "花都区", "增城区", "从化区", "市郊"};
    final public static int REQUEST_CODE_ASK_CAMERA = 1002;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("CARMGR", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        commercialView = (LinearLayout) inflater.inflate(R.layout.activity_commercial,
                null, false);
        itemPopupWindow();
        initView();
        popupwindowinflater = LayoutInflater.from(getActivity());
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(linearLayoutManager);
        getMerchantsList(sharedPreferences.getString("ACCOUNT", null),
                SharedPreferencesUtils.getCityName(getActivity()), "全部",
                sharedPreferences.getString("TOKEN", null), UrlString.APP_VERSION,
                UrlString.APP_GET_MERCHANTS_LIST, 1);
        return commercialView;
    }

    private void initView() {
        merchantPersonalRl = (RelativeLayout) commercialView.findViewById(R.id.merchant_personal_rl);
        businessSelectRl = (RelativeLayout) commercialView.findViewById(R.id.business_select_rl);
        sortSelectRl = (RelativeLayout) commercialView.findViewById(R.id.sort_select_rl);
        positionRl = (RelativeLayout) commercialView.findViewById(R.id.merchant_position_rl);
        citySelectRl = (RelativeLayout) commercialView.findViewById(R.id.city_select_rl);
        popupDivision = (View) commercialView.findViewById(R.id.commercial_popup_division);
        businessSelectTxt = (TextView) commercialView.findViewById(R.id.business_select_txt);
        myRecyclerView = (RecyclerView) commercialView.findViewById(R.id.commercial_item_lv);
        scannerRl = (RelativeLayout) commercialView.findViewById(R.id.merchant_scan_rl);
//        starsTxt = (TextView) commercialView.findViewById(R.id.merchant_stars_txt);
        businessPullDownImg = (ImageView) commercialView.findViewById(R.id.business_direction_img);
        sortSelectTv = (TextView) commercialView.findViewById(R.id.sort_select_txt);
        sortPullDownImg = (ImageView) commercialView.findViewById(R.id.sort_direction_img);
        cityDirection = (ImageView) commercialView.findViewById(R.id.city_direction_img);
        positionTv = (TextView) commercialView.findViewById(R.id.progress_position_Tv);
        citySelectTv = (TextView) commercialView.findViewById(R.id.city_select_txt);
        nothingTv = (TextView) commercialView.findViewById(R.id.commercial_nothing_tv);
        positionTv.setText(SharedPreferencesUtils.getCityName(getActivity()));
        citySelectRl.setOnClickListener(this);
        positionRl.setOnClickListener(this);
        businessSelectRl.setOnClickListener(this);
        merchantPersonalRl.setOnClickListener(this);
        sortSelectRl.setOnClickListener(this);
        scannerRl.setOnClickListener(this);
    }

    private void cityPopupWindow() {
        if (!TextUtils.equals(SharedPreferencesUtils.getCityName(getActivity()), "广州")) {
            int k = 0;
            areaList.clear();
            while (true) {
                if (SharedPreferencesUtils.getAreaName(getActivity(), k) != null) {
                    Map<String, String> listItem = new HashMap<>();
                    Log.e("notnull", SharedPreferencesUtils.getAreaName(getActivity(), k));
                    listItem.put("area", SharedPreferencesUtils.getAreaName(getActivity(), k++));
                    areaList.add(listItem);
                } else {
                    break;
                }
            }
            ;
        } else {
            Log.e("notnull", "that");
            SharedPreferencesUtils.clearData(getActivity());
            areaList.clear();
            for (int n = 0; n < areArray.length; n++) {
                SharedPreferencesUtils.saveAreaName(getActivity(), areArray[n], n);
                Map<String, String> listItem = new HashMap<>();
                listItem.put("area", areArray[n]);
                Log.e("area", areArray[n]);
                areaList.add(listItem);
            }
        }
    }

    public void itemPopupWindow() {

        for (int i = 0; i < businessArray.length; i++) {
            Map<String, String> listItem = new HashMap<>();
            listItem.put("business", businessArray[i]);
            businessList.add(listItem);
        }
        for (int j = 0; j < sortArray.length; j++) {
            Map<String, String> listItem = new HashMap<>();
            listItem.put("sort", sortArray[j]);
            sortList.add(listItem);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5 && resultCode == 5) {
            positionTv.setText(SharedPreferencesUtils.getCityName(getActivity()));
            Log.e("sett", SharedPreferencesUtils.getCityName(getActivity()));
        } else if (requestCode == 2 && resultCode == 10) {
            positionTv.setText(SharedPreferencesUtils.getCityName(getActivity()));
        }
    }

    //解析JSON数据
    protected void analysisJson(String response) {
        Log.e("mer", response);

        try {
            JSONObject jsonObject = new JSONObject(response);
//            JSONArray jsonArray = jsonObject.getJSONArray("merchants_list");
            merchantItemBeens = new ArrayList<>();
            if (jsonObject.getInt("list_size") <= 0) {
                nothingTv.setVisibility(View.VISIBLE);
                myRecyclerView.removeAllViews();
                myRecyclerView.setVisibility(View.GONE);
                return;
            }
            nothingTv.setVisibility(View.GONE);
            myRecyclerView.setVisibility(View.VISIBLE);
            for (int i = 0; i < jsonObject.getJSONArray("merchants_list").length(); i++) {
                MerchantItemBean merchantItemBean = new MerchantItemBean();
                JSONObject merchantJson = jsonObject.getJSONArray("merchants_list").getJSONObject(i);
                merchantItemBean.setMerchantImgUrl(merchantJson.getString("img_path"));
                merchantItemBean.setMerchantDistance(merchantJson.getString("distance"));
                merchantItemBean.setMerchantArea(merchantJson.getString("area"));
                merchantItemBean.setMerchantIntroduce(merchantJson.getString("merchant_introduce"));
                merchantItemBean.setMerchantName(merchantJson.getString("merchant_name"));
                merchantItemBean.setMerchantRoad(merchantJson.getString("road"));
                merchantItemBean.setMerchantServiceItem(merchantJson.getString("service_item"));
                merchantItemBean.setMerchantStars(merchantJson.getDouble("stars"));
                merchantItemBean.setMerchantStarsStr(merchantJson.getString("stars"));
                merchantItemBeens.add(merchantItemBean);
            }

            MerchantItemAdapter merchantItemAdapter = new MerchantItemAdapter(getActivity(), merchantItemBeens);
            myRecyclerView.setAdapter(merchantItemAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
            MerchantItemAdapter merchantItemAdapter = new MerchantItemAdapter(getActivity(), merchantItemBeens);
            myRecyclerView.setAdapter(merchantItemAdapter);
        }
    }

    //打开相机，获取权限
    private void openCamera() {
        int hasWriteContactsPermission = checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            // 弹窗询问 ，让用户自己判断
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_CAMERA);
            return;
        } else {
            Intent capterIntent = new Intent(getActivity(), CaptureActivity.class);
            capterIntent.setAction(Intent.ACTION_CAMERA_BUTTON);
            startActivityForResult(capterIntent, 2);
        }
    }

    /**
     * 用户进行权限设置后的回调函数 , 来响应用户的操作，无论用户是否同意权限，Activity都会
     * 执行此回调方法，所以我们可以把具体操作写在这里
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //这里写你需要相关权限的操作
                    Intent capterIntent = new Intent(getActivity(), CaptureActivity.class);
                    capterIntent.setAction(Intent.ACTION_CAMERA_BUTTON);
                    startActivityForResult(capterIntent, 2);
                } else {
                    Toast.makeText(getActivity(), "权限没有开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initPopupwindow(String params, List<Map<String, String>> paramsList, final int i) {
        View view = popupwindowinflater.inflate(R.layout.commercial_select_list, null);
        popupwindowListView = (ListView) view.findViewById(R.id.mylist);
        // 创建一个SimpleAdapter
        popupWindowSimpleAdapter = new PopupWindowSimpleAdapter(getActivity(), paramsList, R.layout.commercial_sort,
                new String[]{params}, new int[]{R.id.typeTopic});
        popupwindowListView.setAdapter(popupWindowSimpleAdapter);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setHeight(ScreenUtils.getScreenW(getActivity()) * 2 / 3);
        popupWindow.setWidth(ScreenUtils.getScreenH(getActivity()));
//                backgroundAlpha(0.5f);
        //监听popupWindow消失状态并且实现想要的操作
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
                businessSelectTxt.setTextColor(getResources().getColor(R.color.gray_default));
                businessPullDownImg.setImageResource(R.mipmap.pull_down_black);
            }
        });

        popupWindow.showAsDropDown(popupDivision, 0, 0);
        popupwindowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //判断点击的是哪个下拉窗口
                if (i == 1) {
                    businessSelectTxt.setText(businessArray[position]);
                    businessStr = businessArray[position];
                    //提交筛选方法
                    getMerchantsList(sharedPreferences.getString("ACCOUNT", null),
                            areaStr,
                            businessArray[position].toString(),
                            sharedPreferences.getString("TOKEN", null),
                            UrlString.APP_VERSION,
                            UrlString.APP_GET_MERCHANTS_LIST, 1);
                    popupWindow.dismiss();
                    businessSelectTxt.setTextColor(getResources().getColor(R.color.gray_default));
                } else if (i == 2) {
                    citySelectTv.setText(SharedPreferencesUtils.getAreaName(getActivity(), position));
                    areaStr = SharedPreferencesUtils.getAreaName(getActivity(), position);
                    //提交筛选方法
                    getMerchantsList(sharedPreferences.getString("ACCOUNT", null),
                            SharedPreferencesUtils.getAreaName(getActivity(), position),
                            businessStr,
                            sharedPreferences.getString("TOKEN", null), UrlString.APP_VERSION,
                            UrlString.APP_GET_MERCHANTS_LIST, 1);

                    popupWindowCity.dismiss();
                    citySelectTv.setTextColor(getResources().getColor(R.color.gray_default));
                } else if (i == 3) {
                    sortSelectTv.setText(sortArray[position]);
                    popupWindow.dismiss();
                    sortSelectTv.setTextColor(getResources().getColor(R.color.gray_default));
                }
            }
        });
        if (popupWindow.isShowing() && i == 1){
            businessSelectTxt.setTextColor(getResources().getColor(R.color.orange));
            businessPullDownImg.setImageResource(R.mipmap.pull_down_pre);
        }else if (popupWindow.isShowing() && i == 2){
            citySelectTv.setTextColor(getResources().getColor(R.color.orange));
            cityDirection.setImageResource(R.mipmap.pull_down_pre);
        }else if (popupWindow.isShowing() && i == 3){
            sortSelectTv.setTextColor(getResources().getColor(R.color.orange));
            sortPullDownImg.setImageResource(R.mipmap.pull_down_pre);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.business_select_rl:
                initPopupwindow("business", businessList, 1);
                break;
            //城市选择
            case R.id.city_select_rl:
                cityPopupWindow();
                initPopupwindow("area", areaList, 2);
                break;
            //默认排序
            case R.id.sort_select_rl:
                initPopupwindow("sort", sortList, 3);
                break;
            case R.id.merchant_personal_rl:
//                if (sharedPreferences.getString("ACCOUNT", null) != null) {
//                    Intent intentPersonal = new Intent(getActivity(), personalActivity.class);
//                    getActivity().startActivity(intentPersonal);
//                } else {
//                    Intent personalIntent = new Intent(getActivity(), LoginBaseFragmentActivity.class);
//                    getActivity().startActivityForResult(personalIntent, 1);
//                }
                ;
                Intent personalIntent = new Intent(getActivity(), SystemMsgActivity.class);
                getActivity().startActivityForResult(personalIntent, 1);
                break;
            case R.id.merchant_position_rl:
                Intent intent = new Intent(getActivity(), CityActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.merchant_scan_rl:
                openCamera();
                break;
            default:
                break;
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;// 0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    //一个Okhttputils封装类的示例
    private void getMerchantsList(String username, String city_filter, String service_filter, String token,
                                  String version, String url, int id) {
        if (TextUtils.isEmpty(token)) {
            Toast.makeText(getActivity(), getResources().getText(R.string.login_hint),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("city_filter", city_filter)
                .addParams("service_filter", service_filter)
                .addParams("token", token)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new CommercialStringCallback());
    }

    protected class CommercialStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 1:
                    analysisJson(response);
                    break;
                default:
                    break;
            }
        }
    }

}
