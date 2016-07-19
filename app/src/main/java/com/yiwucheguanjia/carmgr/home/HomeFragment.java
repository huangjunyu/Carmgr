package com.yiwucheguanjia.carmgr.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.squareup.picasso.Picasso;
import com.yiwucheguanjia.carmgr.MyGridView;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.LoginActivity;
import com.yiwucheguanjia.carmgr.adviewpager.ImageBean;
import com.yiwucheguanjia.carmgr.adviewpager.SamplePagerView;
import com.yiwucheguanjia.carmgr.personal.personalActivity;
import com.yiwucheguanjia.carmgr.utils.OkhttpManager;
import com.yiwucheguanjia.carmgr.utils.Tools;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.yiwucheguanjia.carmgr.utils.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.FormBody;

/**
 * Created by Administrator on 2016/6/20.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    private LinearLayout homeView;
    private LinearLayout homeLinearLayout;
    private LinearLayout homeActionLayout;
    private RelativeLayout home_personal_rl;
    private SamplePagerView pagerView;
    private SharedPreferences sharedPreferences;
    private ArrayList<BusinessBean> businessBeans;
    private ArrayList<SecondHandBean>secondHandBeens;
    private ImageView recommendImg1;//配置资源ZY_0002
    private ImageView recommendImg2;//配置资源ZY_0003
    private ImageView recommendImg3;//配置资源ZY_0004
    private ArrayList<RecyclerBean> recyclerBeens;//配置资源ZY_0005数据
    private MyGridView businessGridView;//业务视图
    private MyGridView secondHandGridView;//二手推荐视图
    private RecyclerView mRecyclerView;//热门二手车
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<Integer> mDatas;
    private ArrayList<HotRecommendBean> hotRecommendBeens;
    private ArrayList<ImageBean> imageBeans = new ArrayList<ImageBean>();
    private MyListView mListView;
    private RollPagerView mRollViewPager;
    private ArrayList<RollViewPagerBean> rollViewPagerBeens;
    private static HomeFragment newInstance(ImageBean bean) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("CARMGR", getActivity().MODE_WORLD_READABLE);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = (LinearLayout) inflater.inflate(R.layout.home_view,null);
        initView();
        homeActionLayout = (LinearLayout)homeView.findViewById(R.id.home_action_layout);

        initDatas();
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        Log.e("sharedpreferences",sharedPreferences.getString("ACCOUNT",null)
                + sharedPreferences.getString("TOKEN",null));
        appGetConfig(sharedPreferences.getString("ACCOUNT",null),"ZY_0001",
                Tools.getInstance().getScreen(getActivity()),
                sharedPreferences.getString("TOKEN",null),
                UrlString.APP_VERSION,UrlString.APP_GET_CONFIG,1);
        appGetConfig(sharedPreferences.getString("ACCOUNT",null),"ZY_0002",
                Tools.getInstance().getScreen(getActivity()),
                sharedPreferences.getString("TOKEN",null),
                UrlString.APP_VERSION,UrlString.APP_GET_CONFIG,2);
        appGetConfig(sharedPreferences.getString("ACCOUNT",null),"ZY_0003",
                Tools.getInstance().getScreen(getActivity()),
                sharedPreferences.getString("TOKEN",null),
                UrlString.APP_VERSION,UrlString.APP_GET_CONFIG,3);
        appGetConfig(sharedPreferences.getString("ACCOUNT",null),"ZY_0004",
                Tools.getInstance().getScreen(getActivity()),
                sharedPreferences.getString("TOKEN",null),
                UrlString.APP_VERSION,UrlString.APP_GET_CONFIG,4);
        appGetConfig(sharedPreferences.getString("ACCOUNT",null),"ZY_0005",
                Tools.getInstance().getScreen(getActivity()),
                sharedPreferences.getString("TOKEN",null),
                UrlString.APP_VERSION,UrlString.APP_GET_CONFIG,5);
        getSecondHandcar(sharedPreferences.getString("ACCOUNT",null),
                sharedPreferences.getString("TOKEN",null),
                UrlString.APP_VERSION,UrlString.APPGETSECONDHANDCAR,6);
        appGetServices(sharedPreferences.getString("ACCOUNT",null),"1.0",sharedPreferences.getString("TOKEN",null),1,2);
        appgetrecommend(sharedPreferences.getString("ACCOUNT",null),"全部",sharedPreferences.getString("TOKEN",null),"1.0",3,4);
        appgetrecommend(sharedPreferences.getString("ACCOUNT",null),"全部",sharedPreferences.getString("TOKEN",null),"1.0",10,11);
        return homeView;
    }
    private class TestNormalAdapter extends StaticPagerAdapter {
        private int[] imgs = {
                R.mipmap.testimg,
                R.mipmap.testimg,
                R.mipmap.testimg,
                R.mipmap.testimg,
        };


        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }


        @Override
        public int getCount() {
            return imgs.length;
        }
    }
    private void initDatas()
    {
        mDatas = new ArrayList<>(Arrays.asList(R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1://appGetServices方法请求数据成功
                    Log.e("appservic",msg.obj.toString());
                    businessBeans = new ArrayList<>();
                    for (int i = 0;i < 10;i++){
                        BusinessBean businessBean = new BusinessBean();
                        businessBean.setBusinessName("业务");
                        businessBean.setBusinessImgUrl("www.baidu.com");
                        businessBeans.add(businessBean);
                    }
                    BusinessAdapter businessAdapter = new BusinessAdapter(getActivity(),businessBeans);
                    businessGridView.setAdapter(businessAdapter);
                    break;
                case 2://appGetServices方法请求失败
                    break;
                case 3://appgetrecommend方法请求数据成功

                    break;
                case 4://appgetrecommend方法请求数据失败
                    break;
                case 5:
                    Log.e("config",msg.obj.toString());
                    break;
                case 6:
                    Log.e("config0002",msg.obj.toString());
                    break;
                case 7:
                    Log.e("config0003",msg.obj.toString());
                    break;
                case 10://appgetrecommend方法请求数据成功
                    Log.e("apprecommend10",msg.obj.toString());
                    hotRecommendBeens = new ArrayList<>();
                    for (int i = 0;i < 7;i++){
                        HotRecommendBean hotRecommendBean = new HotRecommendBean();
                        hotRecommendBean.setHotRecommendUrlImg("null");
                        hotRecommendBeens.add(hotRecommendBean);
                    }
                    HotRecommendAdapter hotRecommendAdapter = new HotRecommendAdapter(getActivity(),hotRecommendBeens);
                    mListView.setAdapter(hotRecommendAdapter);
                    break;
                case 11://appgetrecommend方法请求数据失败
                    break;
                default:
                    break;
            }
        }
    };
    private void initView(){
        home_personal_rl = (RelativeLayout)homeView.findViewById(R.id.home_personal_rl);
        businessGridView = (MyGridView)homeView.findViewById(R.id.business_gridview);
        secondHandGridView = (MyGridView)homeView.findViewById(R.id.second_hand_gridview);
        mRecyclerView = (RecyclerView)homeView.findViewById(R.id.id_recyclerview_horizontal);
        mListView = (MyListView)homeView.findViewById(R.id.home_hot_recommend);
        recommendImg1 = (ImageView)homeView.findViewById(R.id.recommend_1);
        recommendImg2 = (ImageView)homeView.findViewById(R.id.recommend_2);
        recommendImg3 = (ImageView)homeView.findViewById(R.id.recommend_3);
        home_personal_rl.setOnClickListener(this);
    }

    /**
     * 热闹业务
     * @param username 用户名
     * @param version app版本
     * @param token 密钥
     */
     private void appGetServices(String username,String version,String token,int success,int fail){
         if (username == null || token == null) {
             username = "username";
             token = "token";
         }
         Log.e("testToken",token);
         FormBody formBody = new FormBody.Builder()
                 .add("username", username)
                 .add("token",token)
                 .add("version", version)
                 .build();
         OkhttpManager.getInstance().OKhttpPost(UrlString.APPGETSERVICES, handler, formBody, success, fail);

     }

    /**
     * 热门推荐
     * @param username 用户名
     * @param filter 查找条件
     * @param token 密钥
     * @param version APP版本
     */
    private void appgetrecommend(String username,String filter,String token,String version,
                                 int success,int fail){
        if (username == null || token == null){
            username = "username";
            token = "token";
        }
        FormBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("filter",filter)
                .add("token",token)
                .add("version", version)
                .build();
        OkhttpManager.getInstance().OKhttpPost(UrlString.APPGETRECOMMEND, handler, formBody, success, fail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_personal_rl:
                if (sharedPreferences.getString("ACCOUNT",null) != null){
                    Intent intentPersonal = new Intent(getActivity(),personalActivity.class);
                    getActivity().startActivity(intentPersonal);
                }else {
                Intent personalIntent = new Intent(getActivity(),LoginActivity.class);
                    getActivity().startActivityForResult(personalIntent,1);

                };
        }
    }
    private void appGetConfig(String username,String resouce,String screenSize,String token,
                              String version,String url,int id){
        if (username == null || token == null) {
            username = "username";
            token = "token";
        }
        OkHttpUtils.get().url(url)
                .addParams("username",username)
                .addParams("config_key",resouce)
                .addParams("screen_size",screenSize)
                .addParams("token",token)
                .addParams("version",version)
                .id(id)
                .build()
                .execute(new pagerStringCallback());
    }
    private void getSecondHandcar(String username,String token,String version,String url,int id){
        if (username == null || token == null) {
            username = "username";
            token = "token";
        }
        OkHttpUtils.get().url(url)
                .addParams("username",username)
                .addParams("token",token)
                .addParams("version",version)
                .id(id)
                .build()
                .execute(new pagerStringCallback());
    }
    private void parseJson(String response){
        Log.e("response",response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int listSize = jsonObject.getInt("list_size");
            JSONArray configValueList = jsonObject.getJSONArray("config_value_list");
            rollViewPagerBeens = new ArrayList<>();
            rollViewPagerBeens.clear();
            for (int i = 0;i < listSize;i++){
                JSONObject listItem = configValueList.getJSONObject(i);
                String configValue = listItem.getString("config_value");
                //点击图片时跳转的url
                String url = listItem.getString("url");
                RollViewPagerBean rollViewPagerBean = new RollViewPagerBean();
                rollViewPagerBean.setRollViewPagerUrl(configValue);
                rollViewPagerBean.setRollViewPagerClickUrl(url);
                rollViewPagerBeens.add(rollViewPagerBean);
            }
//            pagerView = new SamplePagerView(getActivity(),imageBeans);
//            homeActionLayout.addView(pagerView,0);
//            pagerView.init();
            mRollViewPager = (RollPagerView)homeView.findViewById(R.id.roll_view_pager);
            //设置播放时间间隔
            mRollViewPager.setPlayDelay(5000);
            //设置透明度
            mRollViewPager.setAnimationDurtion(500);
            //设置适配器
            mRollViewPager.setAdapter(new RollViewPagerAdapter(getActivity(),rollViewPagerBeens));

            //设置指示器（顺序依次）
            //自定义指示器图片
            //设置圆点指示器颜色
            //设置文字指示器
            //隐藏指示器
            mRollViewPager.setHintView(new ColorPointHintView(getActivity(),Color.RED,Color.WHITE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void parseJson2(String response,ImageView imageView){
        Log.e("response",response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int listSize = jsonObject.getInt("list_size");
            JSONArray configValueList = jsonObject.getJSONArray("config_value_list");
            for (int i = 0;i < listSize;i++){
                JSONObject listItem = configValueList.getJSONObject(i);
                String configValue = listItem.getString("config_value");
                Picasso.with(getActivity()).load(configValue).error(R.mipmap.picture_default).into(imageView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void parseJson3(String response){
        Log.e("response7",response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int listSize = jsonObject.getInt("list_size");
            JSONArray configValueList = jsonObject.getJSONArray("config_value_list");
            secondHandBeens = new ArrayList<>();
            for (int i = 0;i < listSize;i++){
                SecondHandBean secondHandBean = new SecondHandBean();
                JSONObject listItem = configValueList.getJSONObject(i);
                String configValue = listItem.getString("config_value");
                secondHandBean.setImgUrl(configValue);
                secondHandBeens.add(secondHandBean);
            }
            SecondHandAdapter secondHandAdapter = new SecondHandAdapter(getActivity(),secondHandBeens);
            //设置适配器
            secondHandGridView.setAdapter(secondHandAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void parseSecondHandCarJson(String response){
        recyclerBeens = new ArrayList<>();
        for (int i = 0;i < 10;i++){
            RecyclerBean recyclerBean = new RecyclerBean();
            recyclerBean.setImgUrlStr("kksks");
            recyclerBeens.add(recyclerBean);
        }
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getActivity(),recyclerBeens);
        mRecyclerView.setAdapter(recyclerAdapter);
    }
    public class pagerStringCallback extends com.zhy.http.okhttp.callback.StringCallback{

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            switch (id){
                case 1:
                    parseJson(response);
                    break;
                case 2:
                    parseJson2(response,recommendImg1);
                    break;
                case 3:
                    parseJson2(response,recommendImg2);
                    break;
                case 4:
                    Log.e("4",response);
                    parseJson2(response,recommendImg3);
                    break;
                case 5:
                    parseJson3(response);
                    break;
                case 6:
                    Log.e("second",response);
                    parseSecondHandCarJson(response);
                    break;
                default:
                    break;
            }
        }
    }
}
