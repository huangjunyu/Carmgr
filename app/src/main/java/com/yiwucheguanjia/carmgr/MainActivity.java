package com.yiwucheguanjia.carmgr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.callyiwu.CallYiwu;
import com.yiwucheguanjia.carmgr.commercial.view.CommercialFragment;
import com.yiwucheguanjia.carmgr.home.view.HomeFragment;
import com.yiwucheguanjia.carmgr.progress.view.ProgressFragment;

/**
 * 主Activity
 *
 * @author
 *
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{

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

    private SharedPreferences sharedPreferences;

    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    private int ft_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("CARMGR", MainActivity.MODE_PRIVATE);
//        isLogin();
        initUI();
        initTab();
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
        homeLayout = (RelativeLayout)findViewById(R.id.rl_home);
        commercialLayout = (RelativeLayout)findViewById(R.id.rl_commercial);
        progressLayout = (RelativeLayout)findViewById(R.id.rl_progress);
        callYiwuLayout = (RelativeLayout)findViewById(R.id.rl_callyiwu);
        homeTxt = (TextView)findViewById(R.id.tabHomeTxt);
        commercialTxt = (TextView)findViewById(R.id.tabCommercialTxt);
        progressTxt = (TextView)findViewById(R.id.tabProgressTxt);
        callYiwuTxt = (TextView)findViewById(R.id.tabCallYiwutext);
        homeImg = (ImageView)findViewById(R.id.tabHomeImg);
        commercialImg = (ImageView)findViewById(R.id.tabCommercialImg);
        progressImg = (ImageView)findViewById(R.id.tabProgressImg);
        callYiwuImg = (ImageView)findViewById(R.id.tabCallYiwuImg);
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
                Log.e("haha","kkk");
                clickTab1Layout(0);
                break;
            case R.id.rl_commercial: // 商户标签
                Log.e("haha","kkk");
                clickTab1Layout(1);
                break;
            case R.id.rl_progress: // 进度标签
                Log.e("haha","kkk");
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
        if (requestCode == 1 && resultCode == 1){
            Log.e("activity","activity");
            fragmentManager.beginTransaction().remove(homeFragment).commit();
            homeFragment = new HomeFragment();
            commercialFragment = new CommercialFragment();
            progressFragment = new ProgressFragment();
            callYiwuFragment = new CallYiwu();
            addOrShowFragment(fragmentManager.beginTransaction(),homeFragment);
        }else if (resultCode == 2 && requestCode == 2){
            Log.e("activ23ity","activity33");
            fragmentManager.beginTransaction().remove(homeFragment).commit();
            homeFragment = new HomeFragment();
            commercialFragment = new CommercialFragment();
            progressFragment = new ProgressFragment();
            callYiwuFragment = new CallYiwu();
            addOrShowFragment(fragmentManager.beginTransaction(),homeFragment);
        }else if (resultCode == 1 && requestCode == 3){
            Log.e("result = 1","request = 3");
            fragmentManager.beginTransaction().remove(homeFragment).commit();
            homeFragment = new HomeFragment();
            commercialFragment = new CommercialFragment();
            progressFragment = new ProgressFragment();
            callYiwuFragment = new CallYiwu();
            addOrShowFragment(fragmentManager.beginTransaction(),homeFragment);
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
        }else if (id == 3){
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
            Log.e("return", "return");
            return;
        }

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            Log.e("return1", "return");
            transaction.hide(currentFragment).add(R.id.content_layout, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
            Log.e("return2", "return");
        }

        currentFragment = fragment;
    }

    @Override
    protected void onDestroy() { // TODO Auto-generated method stub
        super.onDestroy();
    }

}