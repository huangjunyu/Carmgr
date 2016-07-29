package com.yiwucheguanjia.carmgr.commercial.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.view.LoginActivity;
import com.yiwucheguanjia.carmgr.commercial.controller.MerchantItemAdapter;
import com.yiwucheguanjia.carmgr.commercial.model.MerchantItemBean;
import com.yiwucheguanjia.carmgr.commercial.model.MerchantSelectItemBean;
import com.yiwucheguanjia.carmgr.commercial.controller.PopupWindowSimpleAdapter;
import com.yiwucheguanjia.carmgr.personal.personalActivity;
import com.yiwucheguanjia.carmgr.utils.MyListView;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/6/20.
 */
public class CommercialFragment extends Fragment implements View.OnClickListener {
    private LinearLayout commercialView;
    private RelativeLayout businessSelectRl;
    private RelativeLayout merchantPersonalRl;
    private RelativeLayout sortSelectRl;//排序
    private Spinner mySpinner;
    private ArrayList<MerchantSelectItemBean> businessSelectItemBeans;
    private MerchantSelectItemBean businessSelectItemBean;
    private MerchantItemAdapter itemAdapter;
    private ArrayList<MerchantItemBean> merchantItemBeens;
    private PopupWindow popupWindow;
    private LayoutInflater popupwindowinflater;
    private ListView popupwindowListView;
    private PopupWindowSimpleAdapter popupWindowSimpleAdapter;
    private View popupDivision;
    private TextView businessSelectTxt;//业务选择
    private TextView sortSelectTv;//排序
    private ImageView businessPullDownImg;
    private ImageView sortPullDownImg;
    //盛装所有弹出项的item
    private List<Map<String, String>> businessList = new ArrayList<Map<String, String>>();
    private List<Map<String,String>> sortList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private MyListView myListView;
    private TextView starsTxt;
    String[] businessArray = {"全部", "上牌", "驾考", "检车", "维修", "租车", "保养", "二手车",
            "车贷", "新车", "急救", "用品", "停车"};
    String[] sortArray = {"默认排序","离我最近","评价最高","最新发布","人气最高","价格最低",
            "价格最高"};

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
        testPopup();
        initView();
        popupwindowinflater = LayoutInflater.from(getActivity());
        getMerchantsList(sharedPreferences.getString("ACCOUNT",null),"广州","全部",
                sharedPreferences.getString("TOKEN",null),UrlString.APP_VERSION,
                UrlString.APP_GET_MERCHANTS_LIST,1);
        return commercialView;
    }

    private void initView() {
        merchantPersonalRl = (RelativeLayout) commercialView.findViewById(R.id.merchant_personal_rl);
        businessSelectItemBeans = new ArrayList<MerchantSelectItemBean>();
        businessSelectItemBeans.add(new MerchantSelectItemBean("kjaja"));
        businessSelectItemBeans.add(new MerchantSelectItemBean("kj44"));
        businessSelectRl = (RelativeLayout) commercialView.findViewById(R.id.business_select_rl);
        sortSelectRl = (RelativeLayout) commercialView.findViewById(R.id.sort_select_rl);
        popupDivision = (View) commercialView.findViewById(R.id.commercial_popup_division);
        businessSelectTxt = (TextView) commercialView.findViewById(R.id.business_select_txt);
        myListView = (MyListView) commercialView.findViewById(R.id.commercial_item_lv);
        starsTxt = (TextView) commercialView.findViewById(R.id.merchant_stars_txt);
        businessPullDownImg = (ImageView) commercialView.findViewById(R.id.business_direction_img);
        sortSelectTv = (TextView)commercialView.findViewById(R.id.sort_select_txt);
        sortPullDownImg = (ImageView)commercialView.findViewById(R.id.sort_direction_img);
        businessSelectRl.setOnClickListener(this);
        merchantPersonalRl.setOnClickListener(this);
        sortSelectRl.setOnClickListener(this);
    }

    private void addImageView() {
        ImageView mImageView = new ImageView(getActivity());
        mImageView.setImageResource(R.mipmap.heart);

    }

    public void testPopup() {

        for (int i = 0; i < businessArray.length; i++) {
            Map<String, String> listItem = new HashMap<>();
            listItem.put("business", businessArray[i]);
            businessList.add(listItem);
        }
        for (int j = 0;j < sortArray.length;j++){
            Map<String,String> listItem = new HashMap<>();
            listItem.put("sort",sortArray[j]);
            sortList.add(listItem);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    break;
                default:
                    break;
            }
        }
    };

    //解析JSON数据
    protected void analysisJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("merchants_list");
            merchantItemBeens = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                MerchantItemBean merchantItemBean = new MerchantItemBean();
                JSONObject merchantJson = jsonArray.getJSONObject(i);
                merchantItemBean.setMerchantImgUrl(merchantJson.getString("img_path"));
                merchantItemBean.setMerchantDistance(merchantJson.getString("distance"));
                merchantItemBean.setMerchantArea(merchantJson.getString("area"));
                merchantItemBean.setMerchantIntroduce(merchantJson.getString("merchant_introduce"));
                merchantItemBean.setMerchantName(merchantJson.getString("merchant_name"));
                merchantItemBean.setMerchantRoad(merchantJson.getString("road"));
                merchantItemBean.setMerchantServiceItem(merchantJson.getString("service_item"));
                merchantItemBean.setMerchantMobile(merchantJson.getString("mobile"));
                merchantItemBean.setMerchantStars(merchantJson.getDouble("stars"));
                merchantItemBean.setMerchantStarsStr(merchantJson.getString("stars"));
                merchantItemBeens.add(merchantItemBean);
            }

            MerchantItemAdapter merchantItemAdapter = new MerchantItemAdapter(getActivity(), merchantItemBeens);
            myListView.setAdapter(merchantItemAdapter);
            addImageView();
        } catch (JSONException e) {
            Log.e("mobile","mobile");

            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.business_select_rl:
                View view = popupwindowinflater.inflate(R.layout.commercial_select_list, null);
                popupwindowListView = (ListView) view.findViewById(R.id.mylist);
                // 创建一个SimpleAdapter
                popupWindowSimpleAdapter = new PopupWindowSimpleAdapter(getActivity(), businessList, R.layout.commercial_sort,
                        new String[]{"business"}, new int[]{R.id.typeTopic});
                popupwindowListView.setAdapter(popupWindowSimpleAdapter);
                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                        android.view.WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);
                popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
                popupWindow.update();
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true);
//                backgroundAlpha(0.5f);
                //监听popupWindow消失状态并且实现想要的操作
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1f);
                        businessSelectTxt.setTextColor(getResources().getColor(R.color.gray_default));
                        businessPullDownImg.setImageResource(R.mipmap.pull_dowon_black);
                    }
                });

                popupWindow.showAsDropDown(popupDivision, 0, 0);
                popupwindowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("position", position + "");
                        businessSelectTxt.setText(businessArray[position]);
                        popupWindow.dismiss();
                        businessSelectTxt.setTextColor(getResources().getColor(R.color.gray_default));
                    }
                });
                if (popupWindow.isShowing()) {
                    businessSelectTxt.setTextColor(getResources().getColor(R.color.orange));
                    businessPullDownImg.setImageResource(R.mipmap.pull_down_pre);
                }
                break;
            //城市选择
            case R.id.city_select_rl:

                break;
            //默认排序
            case R.id.sort_select_rl:
                View sortView = popupwindowinflater.inflate(R.layout.commercial_select_list, null);
                popupwindowListView = (ListView) sortView.findViewById(R.id.mylist);
                // 创建一个SimpleAdapter
                popupWindowSimpleAdapter = new PopupWindowSimpleAdapter(getActivity(), sortList, R.layout.commercial_sort,
                        new String[]{"sort"}, new int[]{R.id.typeTopic});
                popupwindowListView.setAdapter(popupWindowSimpleAdapter);
                popupWindow = new PopupWindow(sortView, ViewGroup.LayoutParams.MATCH_PARENT,
                        android.view.WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);
                popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
                popupWindow.update();
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true);
//                backgroundAlpha(0.5f);
                //监听popupWindow消失状态并且实现想要的操作
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1f);
                        sortSelectTv.setTextColor(getResources().getColor(R.color.gray_default));
                        sortPullDownImg.setImageResource(R.mipmap.pull_dowon_black);
                    }
                });

                popupWindow.showAsDropDown(popupDivision, 0, 0);
                popupwindowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("position", position + "");
                        sortSelectTv.setText(sortArray[position]);
                        popupWindow.dismiss();
                        sortSelectTv.setTextColor(getResources().getColor(R.color.gray_default));
                    }
                });
                if (popupWindow.isShowing()) {
                    sortSelectTv.setTextColor(getResources().getColor(R.color.orange));
                    sortPullDownImg.setImageResource(R.mipmap.pull_down_pre);
                }
                break;
            case R.id.merchant_personal_rl:
                if (sharedPreferences.getString("ACCOUNT", null) != null) {
                    Intent intentPersonal = new Intent(getActivity(), personalActivity.class);
                    getActivity().startActivity(intentPersonal);
                } else {
                    Intent personalIntent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivityForResult(personalIntent, 1);

                }
                ;
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
    private void getMerchantsList(String username,String city_filter,String service_filter, String token,
                                  String version,String url,int id) {
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("city_filter", city_filter)
                .addParams("service_filter",service_filter)
                .addParams("token", token)
                .addParams("version", version)
                .id(1)
                .build()
                .execute(new CommercialStringCallback());
    }
    protected class CommercialStringCallback extends StringCallback{

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            switch (id){
                case 1:
                    Log.e("mer",response);
                    analysisJson(response);
                    break;
                default:
                    break;
            }
        }
    }

}
