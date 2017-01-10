package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/17.
 */
public class MerchantEnterFragmentActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    FragmentManager fragmentManager = getSupportFragmentManager();
    private OperateDataFragment operateDataFragment;
    private MerchantDataFragment merchantFragment;
    private JionDataFragment jionFragment;
    private Fragment currentFragment;
    @BindView(R.id.enter_operate_data)
    TextView operateDataTv;
    @BindView(R.id.enter_merchant_img)
    ImageView guideImg1;
    @BindView(R.id.enter_merchant_data_tv)
    TextView merchantDataTv;
    @BindView(R.id.merchant_data_img)
    ImageView guideImg2;
    @BindView(R.id.enter_jion_data_tv)
    TextView jionDataTv;
    @BindView(R.id.enter_data_goback_rl)
    RelativeLayout gobackRl;
    public String pwdString;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("CARMGR_MERCHANT", MODE_PRIVATE);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        setContentView(R.layout.activity_merchant_enter);
        ButterKnife.bind(this);

        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        pwdString = bundle.getString("password");//getString()返回指定key的值
        Log.e("password",pwdString);
        initTab();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.next_one");
        intentFilter.addAction("action.next_two");
//        intentFilter.addAction("action.appointment");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
    }
    @OnClick(R.id.enter_data_goback_rl)
    void setGobackRl(){
        finish();
    }

    /**
     * 点击下一步的时候，切换fragment
     * @param i 标记的fragment
     */
    public void nextStep(int i){
        if(i == 0){
            if(operateDataFragment == null){
                operateDataFragment = new OperateDataFragment();
                operateDataFragment.uploadIdCar(2);
            }
            addShowFragment(fragmentManager.beginTransaction(), operateDataFragment);
            operateDataTv.setTextColor(ContextCompat.getColor(this,R.color.orange));
            guideImg1.setImageResource(R.mipmap.register_right_pre);
            merchantDataTv.setTextColor(ContextCompat.getColor(this,R.color.buseness_black));
            guideImg2.setImageResource(R.mipmap.register_right_nor);
            jionDataTv.setTextColor(ContextCompat.getColor(this,R.color.buseness_black));
        }else if (i == 1){
            if (merchantFragment == null){
                merchantFragment = new MerchantDataFragment();
            }
            Log.e("1","one");
            addShowFragment(fragmentManager.beginTransaction(),merchantFragment);
            operateDataTv.setTextColor(ContextCompat.getColor(this,R.color.buseness_black));
            guideImg1.setImageResource(R.mipmap.register_right_pre);
            merchantDataTv.setTextColor(ContextCompat.getColor(this,R.color.orange));
            guideImg2.setImageResource(R.mipmap.register_right_pre);
            jionDataTv.setTextColor(ContextCompat.getColor(this,R.color.buseness_black));
        }else if ((i == 2)){
            if (jionFragment == null){
                jionFragment = new JionDataFragment();
            }
            addShowFragment(fragmentManager.beginTransaction(),jionFragment);
            operateDataTv.setTextColor(ContextCompat.getColor(this,R.color.buseness_black));
            guideImg1.setImageResource(R.mipmap.register_right_nor);
            merchantDataTv.setTextColor(ContextCompat.getColor(this,R.color.buseness_black));
            guideImg2.setImageResource(R.mipmap.register_right_nor);
            jionDataTv.setTextColor(ContextCompat.getColor(this,R.color.orange));
        }
    }
    public void addShowFragment(FragmentTransaction fragmentTransaction,Fragment fragment){
        if (currentFragment == fragment){
            return;
        }
        if (!fragment.isAdded()){
            fragmentTransaction.hide(currentFragment).add(R.id.content_layout,fragment).commitAllowingStateLoss();
        }else {
            fragmentTransaction.hide(currentFragment).show(fragment).commitAllowingStateLoss();
            //此处可用广播通知切换界面
        }
        currentFragment = fragment;
    }

    /**
     * 初始化底部标签
     */
    private void initTab() {
        if (operateDataFragment == null) {
            operateDataFragment = new OperateDataFragment();
        }

        if (!operateDataFragment.isAdded()) {
            // 提交事务
            fragmentManager.beginTransaction().add(R.id.content_layout, operateDataFragment).commit();
            // 记录当前Fragment
            currentFragment = operateDataFragment;
        }
    }
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.next_one")) {
                nextStep(1);

            } else if (action.equals("action.next_two")) {
                nextStep(2);
            } else if (action.equals("action.next_three")) {
                }else {
                }
        }
    };
    /*
    * 提交几个fragment填写的数据
    * */
    public void postData(){
       Log.e("post",sharedPreferences.getString("ACCOUNT",null));
        OkGo.post(UrlString.SUBMIT_PARKINFO)
                .tag(this)
                .params("username",sharedPreferences.getString("ACCOUNT",null))
                .params("operator_name",operateDataFragment.nameEd.getText().toString())
                .params("operator_id",operateDataFragment.idCarEd.getText().toString().trim())
                .params("operator_id_img_a",operateDataFragment.imgPathFrontResponse)
                .params("operator_id_img_b",operateDataFragment.imgPathReverseResponse)
                .params("shop_name",merchantFragment.storeNameEd.getText().toString())
                .params("shop_imgs",jionFragment.SHOP_IMG_PATH_RESPONSE)
                .params("shop_area",merchantFragment.areaTv.getText().toString())
                .params("shop_address",merchantFragment.detailAddrEd.getText().toString())
                .params("shop_mobile",merchantFragment.servicePhoEd.getText().toString().trim())
                .params("shop_license_img",merchantFragment.businessLicensePathResponse)
                .params("token",sharedPreferences.getString("TOKEN","null"))
                .params("version",UrlString.APP_VERSION)
                .params("shop_type",jionFragment.serviceTypeTv.getText().toString().trim())
                .params("shop_introduce",jionFragment.introEd.getText().toString().trim())
                .params("password",pwdString)
                .execute(new MyStringCallback(MerchantEnterFragmentActivity.this,getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("enter",s);
                        Intent dataCheckIntent = new Intent(MerchantEnterFragmentActivity.this,DataCheckActivity.class);
                        startActivity(dataCheckIntent);
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() { // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(mRefreshBroadcastReceiver);
    }
}
