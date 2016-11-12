package com.yiwucheguanjia.carmgr;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.carmgr.callyiwu.CallYiwu;
import com.yiwucheguanjia.carmgr.commercial.view.CommercialFragment;
import com.yiwucheguanjia.carmgr.home.view.HomeFragment;
import com.yiwucheguanjia.carmgr.myrxjava.rxbus.ChangeAnswerEvent;
import com.yiwucheguanjia.carmgr.myrxjava.rxbus.RxBus;
import com.yiwucheguanjia.carmgr.progress.view.ProgressFragment;

import rx.Subscription;
import rx.functions.Action1;

/**
 * 主Activity
 *
 * @author
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    // 以下四个是底部控件
    private RelativeLayout homeLayout;
    private RelativeLayout commercialLayout;
    private RelativeLayout progressLayout;
    private RelativeLayout callYiwuLayout;

    // 以下四个是底部标签的文本
    private TextView homeTxt;
    private TextView commercialTxt;
    private TextView progressTxt;
    private TextView callYiwuTxt;

    //以下四个是底部标签的图标
    private ImageView homeImg;
    private ImageView commercialImg;
    private ImageView progressImg;
    private ImageView callYiwuImg;

    // 以下四个是底部标签切换的Fragment
    private Fragment homeFragment;
    private Fragment commercialFragment;
    private Fragment progressFragment;
    private Fragment callYiwuFragment;
    //当前显示的Fragment
    private Fragment currentFragment;
    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    private int ft_pos;
    Subscription mSubscription;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        StatusBarUtil.setColor(this,ContextCompat.getColor(this,R.color.orange),1);
//        StatusBarUtil.setColorNoTranslucent(this,ContextCompat.getColor(this,R.color.orange));
        setContentView(R.layout.activity_main);
        //联网判断是否登录或登录过期，过期则跳到登录界面，登录界面不可返回
        initUI();
        initTab();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.loginfresh");
        intentFilter.addAction("action.loginout");
        intentFilter.addAction("action.appointment");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        ft_pos = savedInstanceState.getInt("position");
        clickTab1Layout(ft_pos);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 记录当前的position
        outState.putInt("position", ft_pos);
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        homeLayout = (RelativeLayout) findViewById(R.id.rl_home);
        commercialLayout = (RelativeLayout) findViewById(R.id.rl_commercial);
        progressLayout = (RelativeLayout) findViewById(R.id.rl_progress);
        callYiwuLayout = (RelativeLayout) findViewById(R.id.rl_callyiwu);
        homeTxt = (TextView) findViewById(R.id.tabHomeTxt);
        commercialTxt = (TextView) findViewById(R.id.tabCommercialTxt);
        progressTxt = (TextView) findViewById(R.id.tabProgressTxt);
        callYiwuTxt = (TextView) findViewById(R.id.tabCallYiwutext);
        homeImg = (ImageView) findViewById(R.id.tabHomeImg);
        commercialImg = (ImageView) findViewById(R.id.tabCommercialImg);
        progressImg = (ImageView) findViewById(R.id.tabProgressImg);
        callYiwuImg = (ImageView) findViewById(R.id.tabCallYiwuImg);

        homeLayout.setOnClickListener(this);
        commercialLayout.setOnClickListener(this);
        progressLayout.setOnClickListener(this);
        callYiwuLayout.setOnClickListener(this);
    }

    /**
     * 初始化底部标签
     */
    private void initTab() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }

        if (!homeFragment.isAdded()) {
            // 提交事务
            fragmentManager.beginTransaction().add(R.id.content_layout, homeFragment).commit();

            // 记录当前Fragment
            currentFragment = homeFragment;
            // 设置图片文本的变化
            homeImg.setImageResource(R.mipmap.tab_home_img_pre);
            homeTxt.setTextColor(getResources().getColor(R.color.orange));
            commercialImg.setImageResource(R.mipmap.tab_commercial_img_nor);
            commercialTxt.setTextColor(getResources().getColor(R.color.gray_default));
            progressImg.setImageResource(R.mipmap.tab_progress_img_nor);
            progressTxt.setTextColor(getResources().getColor(R.color.gray_default));
            callYiwuImg.setImageResource(R.mipmap.tab_callyiwu_img_nor);
            callYiwuTxt.setTextColor(getResources().getColor(R.color.gray_default));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_home: // 首页标签
                Log.e("haha", "kkk");
                clickTab1Layout(0);
                break;
            case R.id.rl_commercial: // 商户标签
                Log.e("haha", "kkk");
                clickTab1Layout(1);
                break;
            case R.id.rl_progress: // 进度标签
                Log.e("haha", "kkk");
                clickTab1Layout(2);
                break;
            case R.id.rl_callyiwu:
                clickTab1Layout(3);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //这是登录处返回的请求码与结果码，规定HomeFragment发出的请求码全部是1
        if (requestCode == 1 && resultCode == 1) {
            fragmentManager.beginTransaction().remove(homeFragment).commit();
            homeFragment = new HomeFragment();
            commercialFragment = new CommercialFragment();
            progressFragment = new ProgressFragment();
            callYiwuFragment = new CallYiwu();
            addOrShowFragment(fragmentManager.beginTransaction(), homeFragment);
        } else if (requestCode == 1 && resultCode == 10) {//HomeFragment选择地区
            homeFragment.onActivityResult(1, 2, null);
        } else if (requestCode == 2 && resultCode == 10) {//CommercialFragment选择地区
            commercialFragment.onActivityResult(2, 10, null);
        } else if (requestCode == 3 && resultCode == 10) {//ProgressFragment选择地区
            progressFragment.onActivityResult(3, 10, null);
        } else if (requestCode == 4 && resultCode == 10) {//CallYiwu选择地区
            callYiwuFragment.onActivityResult(4, 10, null);
        }
        else if (resultCode == 1 && requestCode == 3) {
            fragmentManager.beginTransaction().remove(homeFragment).commit();
            homeFragment = new HomeFragment();
            commercialFragment = new CommercialFragment();
            progressFragment = new ProgressFragment();
            callYiwuFragment = new CallYiwu();
            addOrShowFragment(fragmentManager.beginTransaction(), homeFragment);
        } else if (requestCode == 1 && resultCode == 20) {//扫描界面返回了homeFragment

        } else if (requestCode == 2 && resultCode == 20) {//扫描界面返回了merchantFragment

        } else if (requestCode == 3 && resultCode == 20) {//扫描界面返回了progressFragment

        } else if (requestCode == 4 && resultCode == 20) {//扫描界面返回了yiwuFragment

        }

    }

    /**
     * 点击第一个tab
     */
    private void clickTab1Layout(int id) {
        ft_pos = id;
        if (id == 0) {
            if (homeFragment == null) {

                homeFragment = new HomeFragment();
            }
            addOrShowFragment(fragmentManager.beginTransaction(), homeFragment);
            // 设置底部tab变化
            homeImg.setImageResource(R.mipmap.tab_home_img_pre);
            homeTxt.setTextColor(getResources().getColor(R.color.orange));
            commercialImg.setImageResource(R.mipmap.tab_commercial_img_nor);
            commercialTxt.setTextColor(getResources().getColor(R.color.gray_default));
            progressImg.setImageResource(R.mipmap.tab_progress_img_nor);
            progressTxt.setTextColor(getResources().getColor(R.color.gray_default));
            callYiwuImg.setImageResource(R.mipmap.tab_callyiwu_img_nor);
            callYiwuTxt.setTextColor(getResources().getColor(R.color.gray_default));
        } else if (id == 1) {
            if (commercialFragment == null) {
                commercialFragment = new CommercialFragment();
            }
            addOrShowFragment(fragmentManager.beginTransaction(), commercialFragment);
            homeImg.setImageResource(R.mipmap.tab_home_img_nor);
            homeTxt.setTextColor(getResources().getColor(R.color.gray_default));
            commercialImg.setImageResource(R.mipmap.tab_commercial_img_pre);
            commercialTxt.setTextColor(getResources().getColor(R.color.orange));
            progressImg.setImageResource(R.mipmap.tab_progress_img_nor);
            progressTxt.setTextColor(getResources().getColor(R.color.gray_default));
            callYiwuImg.setImageResource(R.mipmap.tab_callyiwu_img_nor);
            callYiwuTxt.setTextColor(getResources().getColor(R.color.gray_default));
        } else if (id == 2) {
            if (progressFragment == null) {
                progressFragment = new ProgressFragment();
            }
            addOrShowFragment(fragmentManager.beginTransaction(), progressFragment);
            homeImg.setImageResource(R.mipmap.tab_home_img_nor);
            homeTxt.setTextColor(getResources().getColor(R.color.gray_default));
            commercialImg.setImageResource(R.mipmap.tab_commercial_img_nor);
            commercialTxt.setTextColor(getResources().getColor(R.color.gray_default));
            progressImg.setImageResource(R.mipmap.tab_progress_img_pre);
            progressTxt.setTextColor(getResources().getColor(R.color.orange));
            callYiwuImg.setImageResource(R.mipmap.tab_callyiwu_img_nor);
            callYiwuTxt.setTextColor(getResources().getColor(R.color.gray_default));
        } else if (id == 3) {
            if (callYiwuFragment == null) {
                callYiwuFragment = new CallYiwu();
            }
            addOrShowFragment(fragmentManager.beginTransaction(), callYiwuFragment);
            homeImg.setImageResource(R.mipmap.tab_home_img_nor);
            homeTxt.setTextColor(getResources().getColor(R.color.gray_default));
            commercialImg.setImageResource(R.mipmap.tab_commercial_img_nor);
            commercialTxt.setTextColor(getResources().getColor(R.color.gray_default));
            progressImg.setImageResource(R.mipmap.tab_progress_img_nor);
            progressTxt.setTextColor(getResources().getColor(R.color.gray_default));
            callYiwuImg.setImageResource(R.mipmap.tab_callyiwu_img_pre);
            callYiwuTxt.setTextColor(getResources().getColor(R.color.orange));
        }

    }

    /**
     * 返回键的点击事件
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            Exit();
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 退出事件
     */
    public void Exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("友情提示!").setMessage("您确定退出吗？").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.cancel();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.this.finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == fragment) {
            return;
        }

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment).add(R.id.content_layout, fragment).commitAllowingStateLoss();
        } else {
            transaction.hide(currentFragment).show(fragment).commitAllowingStateLoss();
            fragment.onActivityResult(5, 5, null);//跳转到fragment界面后立即更新相关组件
        }

        currentFragment = fragment;
    }

    private void observorRefrsh() {
        mSubscription = RxBus.getDefault().toObserverable(ChangeAnswerEvent.class)
                .subscribe(new Action1<ChangeAnswerEvent>() {
                    @Override
                    public void call(ChangeAnswerEvent changeAnswerEvent) {
                        Toast.makeText(MainActivity.this, "I get your answer ~ ", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.loginfresh")) {
                fragmentManager.beginTransaction().remove(homeFragment).commitAllowingStateLoss();
                homeFragment = new HomeFragment();
                commercialFragment = new CommercialFragment();
                progressFragment = new ProgressFragment();
                callYiwuFragment = new CallYiwu();
                addOrShowFragment(fragmentManager.beginTransaction(), homeFragment);
            } else if (action.equals("action.loginout")) {
                MainActivity.this.finish();
            } else if (action.equals("action.appointment")) {
                if (callYiwuFragment != null) {
                    addOrShowFragment(fragmentManager.beginTransaction(), callYiwuFragment);
                }else {
                    addOrShowFragment(fragmentManager.beginTransaction(), new CallYiwu());
                }
            }
        }
    };


    @Override
    protected void onDestroy() { // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(mRefreshBroadcastReceiver);
    }

}