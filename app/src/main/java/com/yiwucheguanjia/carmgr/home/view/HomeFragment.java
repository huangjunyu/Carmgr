package com.yiwucheguanjia.carmgr.home.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.squareup.picasso.Picasso;
import com.yiwucheguanjia.carmgr.MyGridView;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.view.LoginBaseFragmentActivity;
import com.yiwucheguanjia.carmgr.my.SystemMsgActivity;
import com.yiwucheguanjia.carmgr.city.CityActivity;
import com.yiwucheguanjia.carmgr.city.utils.SharedPreferencesUtils;
import com.yiwucheguanjia.carmgr.commercial.view.MerchantListActivity;
import com.yiwucheguanjia.carmgr.home.controller.BusinessAdapter;
import com.yiwucheguanjia.carmgr.home.controller.FavorabledRecommendAdapter;
import com.yiwucheguanjia.carmgr.home.controller.HotSecondCarAdapter;
import com.yiwucheguanjia.carmgr.home.controller.PicassoOnScrollListener;
import com.yiwucheguanjia.carmgr.home.controller.RollViewPagerAdapter;
import com.yiwucheguanjia.carmgr.home.model.BusinessBean;
import com.yiwucheguanjia.carmgr.home.model.FavorabledRecommendBean;
import com.yiwucheguanjia.carmgr.home.model.HotRecommendBean;
import com.yiwucheguanjia.carmgr.home.model.HotSecondCarBean;
import com.yiwucheguanjia.carmgr.home.model.RollViewPagerBean;
import com.yiwucheguanjia.carmgr.scanner.CaptureActivity;
import com.yiwucheguanjia.carmgr.utils.ConfiModel;
import com.yiwucheguanjia.carmgr.utils.MyScrollview;
import com.yiwucheguanjia.carmgr.utils.RequestSerives;
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
import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private LinearLayout homeView;
    private LinearLayout homeLinearLayout;
    private LinearLayout homeActionLayout;
    private SharedPreferences sharedPreferences;
    private ArrayList<BusinessBean> businessBeans;
    private ArrayList<FavorabledRecommendBean> favorabledRecommenddBeens;
    private RelativeLayout personalRl;
    private RelativeLayout positionRl;
    private RelativeLayout scannerRl;
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
    private TextView positionTv;
    private final static int requestTimes = 3;
    private int one = 0;
    private int two = 0;
    private int three = 0;
    private int four = 0;
    private int five = 0;
    private int six = 0;
    private int seven = 0;
    private int eight = 0;
    //支持下拉刷新的ViewGroup
    private in.srain.cube.views.ptr.PtrClassicFrameLayout mPtrFrame;
    final public static int REQUEST_CODE_ASK_CAMERA = 1004;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("CARMGR", getActivity().MODE_PRIVATE);
    }

    protected void loginDialog(String unlogin, String logintimeout, String title, String positiveTxt,
                               String negativeTxt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (sharedPreferences.getString("TOKEN", null) == null) {
            builder.setMessage(unlogin);
            builder.setTitle(title);
            builder.setPositiveButton(positiveTxt, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent loginActivityIntent = new Intent(getActivity(), LoginBaseFragmentActivity.class);
                    getActivity().startActivityForResult(loginActivityIntent, 1);
                }
            }).setCancelable(false);
            builder.setNegativeButton(negativeTxt, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    getActivity().finish();
                }
            });
            builder.create().show();
        } else {
            builder.setMessage(logintimeout);
            builder.setTitle(title);
            builder.setPositiveButton(positiveTxt, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent loginActivityIntent = new Intent(getActivity(), LoginBaseFragmentActivity.class);
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

    private void initView() {
        personalRl = (RelativeLayout) homeView.findViewById(R.id.home_personal_rl);
        positionRl = (RelativeLayout) homeView.findViewById(R.id.home_position_rl);
        scannerRl = (RelativeLayout) homeView.findViewById(R.id.home_scan_rl);
        mRollViewPager = (RollPagerView) homeView.findViewById(R.id.roll_view_pager);
        businessGridView = (MyGridView) homeView.findViewById(R.id.business_gridview);
        businessGridView.setFocusable(false);
        secondHandGridView = (MyGridView) homeView.findViewById(R.id.second_hand_gridview);
        secondHandGridView.setFocusable(false);
        hotSecondCarView = (RecyclerView) homeView.findViewById(R.id.id_recyclerview_horizontal);
        recommendImg1 = (ImageView) homeView.findViewById(R.id.recommend_1);
        recommendImg2 = (ImageView) homeView.findViewById(R.id.recommend_2);
        recommendImg3 = (ImageView) homeView.findViewById(R.id.recommend_3);
        positionTv = (TextView) homeView.findViewById(R.id.progress_position_Tv);
        positionTv.setText(SharedPreferencesUtils.getCityName(getActivity()));//设置地区
        personalRl.setOnClickListener(this);
        positionRl.setOnClickListener(this);
        scannerRl.setOnClickListener(this);
        mRollViewPager.setOnClickListener(this);
        recommendImg1.setOnClickListener(this);
        recommendImg2.setOnClickListener(this);
        recommendImg3.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5 && resultCode == 5) {
            positionTv.setText(SharedPreferencesUtils.getCityName(getActivity()));
        } else if (requestCode == 1 && resultCode == 10) {//选择地区返回
            String cityName = SharedPreferencesUtils.getCityName(getActivity());
            positionTv.setText(cityName);
        } else if (requestCode == 1 && resultCode == 20) {
            Log.e("scannerResult", data.getStringExtra("scan_result").toString());
        }
    }

    /**
     * 热门业务
     *
     * @param username 用户名
     * @param version  app版本
     * @param token    密钥
     */
    private void appGetServices(String username, String version, String token, String url, int id) {
        if (TextUtils.isEmpty(token)) {
            return;
        }
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
        if (TextUtils.isEmpty(token)) {
            return;
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
            case R.id.home_personal_rl:
                if (sharedPreferences.getString("ACCOUNT", null) != null) {
//                    Intent intentPersonal = new Intent(getActivity(), personalActivity.class);
//                    getActivity().startActivity(intentPersonal);
                Intent personalIntent = new Intent(getActivity(), SystemMsgActivity.class);
                getActivity().startActivityForResult(personalIntent, 1);
                } else {
                    Intent personalIntent = new Intent(getActivity(), LoginBaseFragmentActivity.class);
                    getActivity().startActivityForResult(personalIntent, 1);
                }
                break;
            case R.id.home_position_rl:
                Intent cityIntent = new Intent(getActivity(), CityActivity.class);
                startActivityForResult(cityIntent, 1);
                break;
            case R.id.home_scan_rl:
                init2();
                openCamera();
                break;
            case R.id.roll_view_pager:
                break;
            case R.id.recommend_1:
                Intent intent = new Intent(getActivity(), MerchantListActivity.class);
                intent.putExtra("business", "保养");
                getActivity().startActivity(intent);
                break;
            case R.id.recommend_2:
                Intent intent2 = new Intent(getActivity(), MerchantListActivity.class);
                intent2.putExtra("business", "维修");
                getActivity().startActivity(intent2);
                break;
            case R.id.recommend_3:
                Intent intent3 = new Intent(getActivity(), MerchantListActivity.class);
                intent3.putExtra("business", "加油");
                getActivity().startActivity(intent3);
                break;
            default:

                break;
        }
    }

    private void openCamera() {
        int hasWriteContactsPermission = checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            // 弹窗询问 ，让用户自己判断
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_CAMERA);
            return;
        } else {
            Intent capterIntent = new Intent(getActivity(), CaptureActivity.class);
            capterIntent.setAction(Intent.ACTION_CAMERA_BUTTON);
            startActivityForResult(capterIntent, 1);
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
                    startActivityForResult(capterIntent, 1);
                } else {
                    Toast.makeText(getActivity(), "权限没有开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void appGetConfig(String username, String resouce, String screenSize, String token,
                              String version, String url, int id) {

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
        if (TextUtils.isEmpty(token)) {
            return;
        }
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("token", token)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new pagerStringCallback());
    }

    private void init2() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://112.74.13.51:8080/carmgr/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        final RequestSerives requestSerives = retrofit.create(RequestSerives.class);


        retrofit2.Call<ConfiModel> call = (retrofit2.Call<ConfiModel>) requestSerives.postData(sharedPreferences.getString("ACCOUNT", null), "ZY_0001",
                Tools.getInstance().getScreen(getActivity()),
                sharedPreferences.getString("TOKEN", null),
                UrlString.APP_VERSION);

        Observable<ConfiModel> confiModel = requestSerives.postData(sharedPreferences.getString("ACCOUNT", null), "ZY_0001",
                Tools.getInstance().getScreen(getActivity()),
                sharedPreferences.getString("TOKEN", null),
                UrlString.APP_VERSION);
        confiModel.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ConfiModel>() {
                    @Override
                    public void call(ConfiModel confiModel) {
                        Log.e("confim", confiModel.getUsername());

                    }

                });
        call.enqueue(new Callback<ConfiModel>() {
            @Override
            public void onResponse(retrofit2.Call<ConfiModel> call, Response<ConfiModel> response) {
                Log.e("成功7", response.body().getUsername());

            }

            @Override
            public void onFailure(retrofit2.Call<ConfiModel> call, Throwable t) {
                Log.e("失败", t.getMessage());
            }


        });
        call.enqueue(new retrofitCallback<ConfiModel>(2));

    }

    //每次传入一个参数，区别于来自哪个方法的请求，统一调用这个回调
    public class retrofitCallback<T> implements Callback<T> {
        int num;

        public retrofitCallback(int num) {
            this.num = num;
        }

        @Override
        public void onResponse(retrofit2.Call call, Response response) {
            Log.e("call", response.toString());
        }

        @Override
        public void onFailure(retrofit2.Call call, Throwable t) {

        }
    }

    private void parseJson(String response) {
        OkHttpClient okHttpClient = new OkHttpClient();

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
            } else {
                loginDialog(getActivity().getResources().getText(R.string.login_hint).toString(),
                        getActivity().getResources().getText(R.string.login_timeout).toString(),
                        getActivity().getResources().getText(R.string.hint).toString(),
                        getActivity().getResources().getText(R.string.login_account).toString(),
                        getActivity().getResources().getText(R.string.cancel).toString());
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
            hotSecondCarView.addOnScrollListener(new PicassoOnScrollListener(getActivity()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    protected class myRunnable implements java.lang.Runnable {
        int id;

        public myRunnable(int id) {
            this.id = id;
        }

        ;

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                switch (id) {
                    case 1:
                        if (one <= requestTimes) {
                            appGetConfig(sharedPreferences.getString("ACCOUNT", null), "ZY_0001",
                                    Tools.getInstance().getScreen(getActivity()),
                                    sharedPreferences.getString("TOKEN", null),
                                    UrlString.APP_VERSION, UrlString.APP_GET_CONFIG, 1);
                        }
                        one++;
                        break;
                    case 2:
                        if (two <= requestTimes) {
                            appGetConfig(sharedPreferences.getString("ACCOUNT", null), "ZY_0002",
                                    Tools.getInstance().getScreen(getActivity()),
                                    sharedPreferences.getString("TOKEN", null),
                                    UrlString.APP_VERSION, UrlString.APP_GET_CONFIG, 2);

                        }
                        two++;
                        break;
                    case 3:
                        if (three <= requestTimes) {

                            appGetConfig(sharedPreferences.getString("ACCOUNT", null), "ZY_0003",
                                    Tools.getInstance().getScreen(getActivity()),
                                    sharedPreferences.getString("TOKEN", null),
                                    UrlString.APP_VERSION, UrlString.APP_GET_CONFIG, 3);
                        }
                        three++;
                        break;
                    case 4:
                        if (four <= requestTimes) {

                            appGetConfig(sharedPreferences.getString("ACCOUNT", null), "ZY_0004",
                                    Tools.getInstance().getScreen(getActivity()),
                                    sharedPreferences.getString("TOKEN", null),
                                    UrlString.APP_VERSION, UrlString.APP_GET_CONFIG, 4);
                        }
                        four++;
                        break;
                    case 5:
                        if (five <= requestTimes) {
                            appGetConfig(sharedPreferences.getString("ACCOUNT", null), "ZY_0005",
                                    Tools.getInstance().getScreen(getActivity()),
                                    sharedPreferences.getString("TOKEN", null),
                                    UrlString.APP_VERSION, UrlString.APP_GET_CONFIG, 5);

                        }
                        five++;
                        break;
                    case 6:
                        if (six <= requestTimes) {

                            getSecondHandcar(sharedPreferences.getString("ACCOUNT", null),
                                    sharedPreferences.getString("TOKEN", null),
                                    UrlString.APP_VERSION, UrlString.APPGETSECONDHANDCAR, 6);
                        }
                        six++;
                        break;
                    case 7:
                        if (seven <= requestTimes) {

                            appGetServices(sharedPreferences.getString("ACCOUNT", null),
                                    UrlString.APP_VERSION, sharedPreferences.getString("TOKEN", null),
                                    UrlString.APPGETSERVICES, 7);
                        }
                        seven++;
                        break;
                    case 8:
                        if (eight <= requestTimes) {

                            appgetrecommend(sharedPreferences.getString("ACCOUNT", null),
                                    "全部", sharedPreferences.getString("TOKEN", null),
                                    UrlString.APP_VERSION, UrlString.APPGETRECOMMEND, 8);
                        }
                        eight++;
                    default:
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class pagerStringCallback extends com.zhy.http.okhttp.callback.StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            new Thread(new myRunnable(id)).start();
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
