package com.yiwucheguanjia.carmgr.commercial;

import android.content.Context;
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
import com.yiwucheguanjia.carmgr.home.BusinessAdapter;
import com.yiwucheguanjia.carmgr.utils.MyListView;
import com.yiwucheguanjia.carmgr.utils.OkhttpManager;
import com.yiwucheguanjia.carmgr.utils.UrlString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;

/**
 * Created by Administrator on 2016/6/20.
 */
public class CommercialFragment extends Fragment implements View.OnClickListener{
    private LinearLayout commercialView;
    private RelativeLayout businessSelectRl;
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
    private TextView businessSelectTxt;
    //盛装所有弹出项的item
    private List<Map<String, String>> listItems = new ArrayList<Map<String, String>>();
    private BusinessAdapter businessAdapter;
    private SharedPreferences sharedPreferences;
    private MyListView myListView;
    private TextView starsTxt;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("CARMGR", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        commercialView = (LinearLayout)inflater.inflate(R.layout.activity_commercial,null);
//        mySpinner = (Spinner)commercialView.findViewById(R.id.business_select_sp);
        testPopup();
        initView();
        popupwindowinflater = LayoutInflater.from(getActivity());
        appgetmerchantslist(sharedPreferences.getString("ACCOUNT",null),"全部",sharedPreferences.getString("TOKEN",null),"1.0",1,2);
        return commercialView;
    }
    private void initView(){
        businessSelectItemBeans = new ArrayList<MerchantSelectItemBean>();
        businessSelectItemBeans.add(new MerchantSelectItemBean("kjaja"));
        businessSelectItemBeans.add(new MerchantSelectItemBean("kj44"));
        businessSelectRl = (RelativeLayout)commercialView.findViewById(R.id.business_select_rl);
        popupDivision = (View)commercialView.findViewById(R.id.commercial_popup_division);
        businessSelectTxt = (TextView)commercialView.findViewById(R.id.business_select_txt);
        myListView = (MyListView) commercialView.findViewById(R.id.commercial_item_lv);
        starsTxt = (TextView)commercialView.findViewById(R.id.merchant_stars_txt);
        businessSelectRl.setOnClickListener(this);

    }
    private void addImageView(){
        RelativeLayout r = new RelativeLayout(getActivity());
        ImageView mImageView = new ImageView(getActivity());
        mImageView.setImageResource(R.mipmap.heart);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(15, 15);
//        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.RIGHT_OF,R.id.merchant_stars_txt);//在stars左边
        mImageView.setLayoutParams(params);
//        r.add(mImageView);
        commercialView.addView(mImageView);
    }
    public void testPopup(){
        Map<String,String> listItem = new HashMap<>();
        listItem.put("1","ha");
        Map<String,String> listItem1 = new HashMap<>();
        listItem1.put("1","wo");
        listItems.add(listItem);
        listItems.add(listItem1);

    }
    private void appgetmerchantslist(String username,String filter,String token,
                                     String version,int success,int fail){
        FormBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("filter",filter)
                .add("token",token)
                .add("version", version)
                .build();
        OkhttpManager.getInstance().OKhttpPost(UrlString.APPGETMERCHANTSLIST,
                handler,formBody,success,fail);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Log.e("commercial",msg.obj.toString());
                    analysisJson(msg.obj.toString());
                    break;
                default:
                    break;
            }
        }
    };
    //解析JSON数据
    private void analysisJson(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("merchants_list");
            merchantItemBeens = new ArrayList<>();
            for (int i = 0;i < jsonArray.length();i++){
                MerchantItemBean merchantItemBean = new MerchantItemBean();
                JSONObject merchantJson = jsonArray.getJSONObject(i);
/*                String tags = merchantJson.getString("tags");
                String imgPath = merchantJson.getString("img_path");
                String address = merchantJson.getString("address");
                String stars = merchantJson.getString("stars");
                String merchantName = merchantJson.getString("merchant_name");
                String totalRate = merchantJson.getString("total_rate");
                String serviceItem = merchantJson.getString("service_item");
                String mobile = merchantJson.getString("mobile");*/
                merchantItemBean.setMerchantImgUrl(merchantJson.getString("img_path"));
                merchantItemBean.setMerchantAddress(merchantJson.getString("address"));
                merchantItemBean.setMerchantMobile(merchantJson.getString("mobile"));
                merchantItemBean.setMerchantName(merchantJson.getString("merchant_name"));
                merchantItemBean.setMerchantStars(merchantJson.getString("stars"));
                merchantItemBean.setMerchantTag(merchantJson.getString("tags"));
                merchantItemBeens.add(merchantItemBean);
            }
            MerchantItemAdapter merchantItemAdapter = new MerchantItemAdapter(getActivity(),merchantItemBeens);
            myListView.setAdapter(merchantItemAdapter);
            addImageView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.business_select_rl:
                View view = popupwindowinflater.inflate(R.layout.commercial_select_list, null);
                popupwindowListView = (ListView) view.findViewById(R.id.mylist);
                // 创建一个SimpleAdapter
                popupWindowSimpleAdapter = new PopupWindowSimpleAdapter(getActivity(), listItems, R.layout.commercial_sort,
                        new String[] { "1" }, new int[] { R.id.typeTopic });
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
                    }
                });

                popupWindow.showAsDropDown(popupDivision, 0, 0);
                popupwindowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("position",position + "");
                        businessSelectTxt.setTextColor(getResources().getColor(R.color.gray_default));
                    }
                });
                if (popupWindow.isShowing()){
                    businessSelectTxt.setTextColor(getResources().getColor(R.color.orange));

                }
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
}
