package com.yiwucheguanjia.carmgr.home.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.squareup.picasso.Picasso;
import com.yiwucheguanjia.carmgr.MyGridView;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.view.LoginActivity;
import com.yiwucheguanjia.carmgr.home.controller.BusinessAdapter;
import com.yiwucheguanjia.carmgr.home.controller.FavorabledRecommendAdapter;
import com.yiwucheguanjia.carmgr.home.controller.HotSecondCarAdapter;
import com.yiwucheguanjia.carmgr.home.controller.RollViewPagerAdapter;
import com.yiwucheguanjia.carmgr.home.model.BusinessBean;
import com.yiwucheguanjia.carmgr.home.model.FavorabledRecommendBean;
import com.yiwucheguanjia.carmgr.home.model.HotRecommendBean;
import com.yiwucheguanjia.carmgr.home.model.HotSecondCarBean;
import com.yiwucheguanjia.carmgr.home.model.RollViewPagerBean;
import com.yiwucheguanjia.carmgr.personal.personalActivity;
import com.yiwucheguanjia.carmgr.utils.MyScrollview;
import com.yiwucheguanjia.carmgr.utils.Tools;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/6/20.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private LinearLayout homeView;
    private LinearLayout homeLinearLayout;
    private LinearLayout homeActionLayout;
    private SharedPreferences sharedPreferences;
    private ArrayList<BusinessBean> businessBeans;
    private ArrayList<FavorabledRecommendBean> favorabledRecommenddBeens;
    private RelativeLayout personalRl;
    private ImageView recommendImg1;//配置资源ZY_0002
    private ImageView recommendImg2;//配置资源ZY_0003
    private ImageView recommendImg3;//配置资源ZY_0004
    private ArrayList<HotSecondCarBean> hotSecondCarBeens;//配置资源ZY_0005数据
    private MyGridView businessGridView;//业务视图
    private MyGridView secondHandGridView;//二手推荐视图
    private RecyclerView hotSecondCarView;//热门二手车
    private HotSecondCarAdapter recyclerAdapter;
    private ArrayList<Integer> mDatas;
    private ArrayList<HotRecommendBean> hotRecommendBeens;
    private RecyclerView hotRecommendRclV;//热闹推荐
    private RollPagerView mRollViewPager;
    private ArrayList<RollViewPagerBean> rollViewPagerBeens;
    private MyScrollview myScrollview;
    //支持下拉刷新的ViewGroup
    private in.srain.cube.views.ptr.PtrClassicFrameLayout mPtrFrame;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("CARMGR", getActivity().MODE_PRIVATE);
    }

    protected void loginDialog(String unlogin,String logintimeout,String title,String positiveTxt,
                               String negativeTxt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (sharedPreferences.getString("TOKEN",null) == null){
        builder.setMessage(unlogin);
        builder.setTitle(title);
        builder.setPositiveButton(positiveTxt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent loginActivityIntent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivityForResult(loginActivityIntent, 1);
            }
        });
        builder.setNegativeButton(negativeTxt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getActivity().finish();
            }
        });
        builder.create().show();
        }else {
            builder.setMessage(logintimeout);
            builder.setTitle(title);
            builder.setPositiveButton(positiveTxt, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent loginActivityIntent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivityForResult(loginActivityIntent, 1);
                }
            });
            builder.setNegativeButton(negativeTxt, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    getActivity().finish();
                }
            });
            builder.create().show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = (LinearLayout) inflater.inflate(R.layout.home_view, null);
        initView();
        initDatas();
        homeActionLayout = (LinearLayout) homeView.findViewById(R.id.home_action_layout);
        mPtrFrame = (in.srain.cube.views.ptr.PtrClassicFrameLayout) homeView.findViewById(R.id.rotate_header_web_view_frame);
        myScrollview = (MyScrollview) homeView.findViewById(R.id.home_scrollview);
        //下拉刷新支持时间
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        //下拉刷新一些设置 详情参考文档
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);

        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        setupViews(mPtrFrame);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mPtrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mPtrFrame.refreshComplete();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                Log.e("refresh", "refresh");
                mPtrFrame.refreshComplete();
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return super.checkCanDoLoadMore(frame, myScrollview, footer);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, myScrollview, header);
            }
        });
//        hotRecommendRclV.setLayoutManager(layoutManager);
        //进入Activity就进行自动下拉刷新
/*        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);*/

        //下拉刷新
//        mPtrFrame.setPtrHandler(new MyPtrDefaultHandler());

        hotSecondCarView.setLayoutManager(linearLayoutManager);

        Log.e("sharedpreferences", sharedPreferences.getString("ACCOUNT", null)
                + sharedPreferences.getString("TOKEN", null));
        appGetConfig(sharedPreferences.getString("ACCOUNT", null), "ZY_0001",
                Tools.getInstance().getScreen(getActivity()),
                sharedPreferences.getString("TOKEN", null),
                UrlString.APP_VERSION, UrlString.APP_GET_CONFIG, 1);
        appGetConfig(sharedPreferences.getString("ACCOUNT", null), "ZY_0002",
                Tools.getInstance().getScreen(getActivity()),
                sharedPreferences.getString("TOKEN", null),
                UrlString.APP_VERSION, UrlString.APP_GET_CONFIG, 2);
        appGetConfig(sharedPreferences.getString("ACCOUNT", null), "ZY_0003",
                Tools.getInstance().getScreen(getActivity()),
                sharedPreferences.getString("TOKEN", null),
                UrlString.APP_VERSION, UrlString.APP_GET_CONFIG, 3);
        appGetConfig(sharedPreferences.getString("ACCOUNT", null), "ZY_0004",
                Tools.getInstance().getScreen(getActivity()),
                sharedPreferences.getString("TOKEN", null),
                UrlString.APP_VERSION, UrlString.APP_GET_CONFIG, 4);
        appGetConfig(sharedPreferences.getString("ACCOUNT", null), "ZY_0005",
                Tools.getInstance().getScreen(getActivity()),
                sharedPreferences.getString("TOKEN", null),
                UrlString.APP_VERSION, UrlString.APP_GET_CONFIG, 5);
        getSecondHandcar(sharedPreferences.getString("ACCOUNT", null),
                sharedPreferences.getString("TOKEN", null),
                UrlString.APP_VERSION, UrlString.APPGETSECONDHANDCAR, 6);
        appGetServices(sharedPreferences.getString("ACCOUNT", null),
                UrlString.APP_VERSION, sharedPreferences.getString("TOKEN", null),
                UrlString.APPGETSERVICES, 7);
        appgetrecommend(sharedPreferences.getString("ACCOUNT", null),
                "全部", sharedPreferences.getString("TOKEN", null),
                UrlString.APP_VERSION, UrlString.APPGETRECOMMEND, 8);
        return homeView;
    }

    protected void setupViews(final PtrClassicFrameLayout ptrFrame) {

    }

    private void initDatas() {
        mDatas = new ArrayList<>(Arrays.asList(R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
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

    private void initView() {
        personalRl = (RelativeLayout) homeView.findViewById(R.id.merchant_personal_rl);
        businessGridView = (MyGridView) homeView.findViewById(R.id.business_gridview);
        businessGridView.setFocusable(false);
        secondHandGridView = (MyGridView) homeView.findViewById(R.id.second_hand_gridview);
        secondHandGridView.setFocusable(false);
        hotSecondCarView = (RecyclerView) homeView.findViewById(R.id.id_recyclerview_horizontal);
//        hotRecommendRclV = (RecyclerView) homeView.findViewById(R.id.home_hot_recommend);
        recommendImg1 = (ImageView) homeView.findViewById(R.id.recommend_1);
        recommendImg2 = (ImageView) homeView.findViewById(R.id.recommend_2);
        recommendImg3 = (ImageView) homeView.findViewById(R.id.recommend_3);
        personalRl.setOnClickListener(this);
    }

    /**
     * 热门业务
     *
     * @param username 用户名
     * @param version  app版本
     * @param token    密钥
     */
    private void appGetServices(String username, String version, String token, String url, int id) {
        if (username == null || token == null) {
            username = "username";
            token = "token";
        }
        Log.e("testToken", token);
        OkHttpUtils.get()
                .url(url)
                .addParams("username", username)
                .addParams("token", token)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new pagerStringCallback());
    }

    /**
     * 热门推荐
     *
     * @param username 用户名
     * @param filter   查找条件
     * @param token    密钥
     * @param version  APP版本
     */
    private void appgetrecommend(String username, String filter, String token, String version,
                                 String url, int id) {
        if (username == null || token == null) {
            username = "username";
            token = "token";
        }
        OkHttpUtils.get()
                .url(url)
                .addParams("username", username)
                .addParams("filter", filter)
                .addParams("token", token)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new pagerStringCallback());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_personal_img:
                if (sharedPreferences.getString("ACCOUNT", null) != null) {
                    Intent intentPersonal = new Intent(getActivity(), personalActivity.class);
                    getActivity().startActivity(intentPersonal);
                } else {
                    Intent personalIntent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivityForResult(personalIntent, 1);

                }
                ;
        }
    }

    private void appGetConfig(String username, String resouce, String screenSize, String token,
                              String version, String url, int id) {
        if (username == null || token == null) {
            username = "username";
            token = "token";
        }
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("config_key", resouce)
                .addParams("screen_size", screenSize)
                .addParams("token", token)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new pagerStringCallback());
    }

    private void getSecondHandcar(String username, String token, String version, String url, int id) {
        if (username == null || token == null) {
            username = "username";
            token = "token";
        }
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("token", token)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new pagerStringCallback());
    }

    private void parseJson(String response) {
        Log.e("response", response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("opt_state").equals("success")) {

                int listSize = jsonObject.getInt("list_size");
                JSONArray configValueList = jsonObject.getJSONArray("config_value_list");
                rollViewPagerBeens = new ArrayList<>();
                rollViewPagerBeens.clear();
                for (int i = 0; i < listSize; i++) {
                    JSONObject listItem = configValueList.getJSONObject(i);
                    String configValue = listItem.getString("config_value");
                    //点击图片时跳转的url
                    String url = listItem.getString("url");
                    RollViewPagerBean rollViewPagerBean = new RollViewPagerBean();
                    rollViewPagerBean.setRollViewPagerUrl(configValue);
                    rollViewPagerBean.setRollViewPagerClickUrl(url);
                    rollViewPagerBeens.add(rollViewPagerBean);
                }
                mRollViewPager = (RollPagerView) homeView.findViewById(R.id.roll_view_pager);
                //设置播放时间间隔
                mRollViewPager.setPlayDelay(5000);
                mRollViewPager.setAnimationDurtion(500);
                //设置适配器
                mRollViewPager.setAdapter(new RollViewPagerAdapter(getActivity(), rollViewPagerBeens));
                //设置指示器（顺序依次）
                //自定义指示器图片
                //设置圆点指示器颜色
                //设置文字指示器
                //隐藏指示器
                mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.TRANSPARENT, Color.WHITE));
            }else {
                loginDialog(getActivity().getResources().getText(R.string.login_hint).toString(),
                        getActivity().getResources().getText(R.string.login_timeout).toString(),
                        getActivity().getResources().getText(R.string.hint).toString(),
                        getActivity().getResources().getText(R.string.login_account).toString(),
                        getActivity().getResources().getText(R.string.cancel).toString());
                        ;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseJson2(String response, ImageView imageView) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int listSize = jsonObject.getInt("list_size");
            JSONArray configValueList = jsonObject.getJSONArray("config_value_list");
            for (int i = 0; i < listSize; i++) {
                JSONObject listItem = configValueList.getJSONObject(i);
                String configValue = listItem.getString("config_value");
                Picasso.with(getActivity()).load(configValue).error(R.mipmap.picture_default).into(imageView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseJson3(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int listSize = jsonObject.getInt("list_size");
            JSONArray configValueList = jsonObject.getJSONArray("config_value_list");
            favorabledRecommenddBeens = new ArrayList<>();
            for (int i = 0; i < listSize; i++) {
                FavorabledRecommendBean favorabledRecommendBean = new FavorabledRecommendBean();
                JSONObject listItem = configValueList.getJSONObject(i);
                String configValue = listItem.getString("config_value");
                favorabledRecommendBean.setImgUrl(configValue);
                favorabledRecommenddBeens.add(favorabledRecommendBean);
            }
            FavorabledRecommendAdapter favorabledRecommendAdapter = new FavorabledRecommendAdapter(getActivity(), favorabledRecommenddBeens);
            //设置适配器
            secondHandGridView.setAdapter(favorabledRecommendAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseBusiness(String response) {
        businessBeans = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            int listSize = jsonObject.getInt("list_size");
            JSONArray servicesList = jsonObject.getJSONArray("services_list");
            for (int k = 0; k < listSize; k++) {
                BusinessBean businessBean = new BusinessBean();
                JSONObject listItem = servicesList.getJSONObject(k);
                businessBean.setBusinessImgUrl(listItem.getString("icon_path"));
                businessBean.setBusinessName(listItem.getString("service_name"));
                businessBeans.add(businessBean);
            }
            BusinessAdapter businessAdapter = new BusinessAdapter(getActivity(), businessBeans);
            businessGridView.setAdapter(businessAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseHotRecommend(String response) {
        hotRecommendBeens = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            int listSize = jsonObject.getInt("list_size");
            JSONArray servicesList = jsonObject.getJSONArray("services_list");
            for (int n = 0; n < listSize; n++) {
                HotRecommendBean hotRecommendBean = new HotRecommendBean();
                JSONObject listItem = servicesList.getJSONObject(n);
                hotRecommendBean.setHotRecommendUrlImg(listItem.getString("img_path"));
                hotRecommendBeens.add(hotRecommendBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseSecondHandCarJson(String response) {
        hotSecondCarBeens = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            int listSize = jsonObject.getInt("list_size");
            JSONArray configValueList = jsonObject.getJSONArray("car_list");
            for (int j = 0; j < listSize; j++) {
                HotSecondCarBean hotSecondCarBean = new HotSecondCarBean();
                JSONObject listItem = configValueList.getJSONObject(j);
                String configValue = listItem.getString("img_path");
                hotSecondCarBean.setImgUrlStr(configValue);
                hotSecondCarBeens.add(hotSecondCarBean);
            }
            HotSecondCarAdapter hotSecondCarAdapter = new HotSecondCarAdapter(getActivity(), hotSecondCarBeens);
            hotSecondCarView.setAdapter(hotSecondCarAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class pagerStringCallback extends com.zhy.http.okhttp.callback.StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 1:
                    parseJson(response);
                    break;
                case 2:
                    parseJson2(response, recommendImg1);
                    break;
                case 3:
                    parseJson2(response, recommendImg2);
                    break;
                case 4:
                    Log.e("4", response);
                    parseJson2(response, recommendImg3);
                    break;
                case 5:
                    parseJson3(response);
                    break;
                case 6:
                    Log.e("second", response);
                    parseSecondHandCarJson(response);
                    break;
                case 7:
                    Log.e("appservic", response);
                    parseBusiness(response);
                    break;
                case 8:
                    parseHotRecommend(response);
                    hotRecommendBeens = new ArrayList<>();
                    break;
                default:
                    break;
            }
        }
    }
}
