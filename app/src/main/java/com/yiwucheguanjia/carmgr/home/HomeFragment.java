package com.yiwucheguanjia.carmgr.home;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yiwucheguanjia.carmgr.MyGridView;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.LoginActivity;
import com.yiwucheguanjia.carmgr.adviewpager.ImageBean;
import com.yiwucheguanjia.carmgr.adviewpager.SamplePagerView;
import com.yiwucheguanjia.carmgr.personal.personalActivity;
import com.yiwucheguanjia.carmgr.utils.OkhttpManager;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.yiwucheguanjia.carmgr.utils.MyListView;

import java.util.ArrayList;
import java.util.Arrays;

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
    private LinearLayout llRoot;
    private ImageBean bean;
    private ArrayList<BusinessBean> businessBeans;
    private ArrayList<SecondHandBean>secondHandBeens;
    private MyGridView businessGridView;//业务视图
    private MyGridView secondHandGridView;//二手推荐视图
    private RecyclerView mRecyclerView;//热门二手车
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<Integer> mDatas;
    private ArrayList<HotRecommendBean> hotRecommendBeens;
    private MyListView mListView;
    public static HomeFragment newInstance(ImageBean bean) {
        HomeFragment fragment = new HomeFragment();
        fragment.bean = bean;
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("CARMGR", getActivity().MODE_WORLD_READABLE);
        pagerView = new SamplePagerView(getActivity());
        pagerView.init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = (LinearLayout) inflater.inflate(R.layout.home_view,null);
        initView();
        homeActionLayout = (LinearLayout)homeView.findViewById(R.id.home_action_layout);
        homeActionLayout.addView(pagerView,1);
        initDatas();
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        recyclerAdapter = new RecyclerAdapter(getActivity(), mDatas);
        mRecyclerView.setAdapter(recyclerAdapter);
        Log.e("sharedpreferences",sharedPreferences.getString("ACCOUNT",null) + sharedPreferences.getString("TOKEN",null));
        appGetServices(sharedPreferences.getString("ACCOUNT",null),"1.0",sharedPreferences.getString("TOKEN",null));
        appgetrecommend(sharedPreferences.getString("ACCOUNT",null),"全部",sharedPreferences.getString("TOKEN",null),"1.0",3,4);
        appgetrecommend(sharedPreferences.getString("ACCOUNT",null),"全部",sharedPreferences.getString("TOKEN",null),"1.0",10,11);
        return homeView;
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
                        businessBean.setBusinessName("kk");
                        businessBean.setBusinessImgUrl("www.baidu.com");
                        businessBeans.add(businessBean);
                    }
                    BusinessAdapter businessAdapter = new BusinessAdapter(getActivity(),businessBeans);
                    businessGridView.setAdapter(businessAdapter);
                    break;
                case 2://appGetServices方法请求失败
                    break;
                case 3://appgetrecommend方法请求数据成功
                    secondHandBeens = new ArrayList<>();
                    for (int i = 0;i < 6;i++){
                        SecondHandBean secondHandBean = new SecondHandBean();
                        secondHandBean.setImgUrl("url");
                        secondHandBeens.add(secondHandBean);
                    }
                    SecondHandAdapter secondHandAdapter = new SecondHandAdapter(getActivity(),secondHandBeens);
                    secondHandGridView.setAdapter(secondHandAdapter);
                    break;
                case 4://appgetrecommend方法请求数据失败
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
        home_personal_rl.setOnClickListener(this);
    }

    /**
     * 热闹业务
     * @param username 用户名
     * @param version app版本
     * @param token 密钥
     */
     private void appGetServices(String username,String version,String token){
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
         OkhttpManager.getInstance().OKhttpPost(UrlString.APPGETSERVICES, handler, formBody, 1, 2);
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
        OkhttpManager.getInstance().OKhttpPost(UrlString.APPGETSERVICES, handler, formBody, success, fail);
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
//                getActivity().startActivity(personalIntent);
                    getActivity().startActivityForResult(personalIntent,1);

                };
        }
    }
}
