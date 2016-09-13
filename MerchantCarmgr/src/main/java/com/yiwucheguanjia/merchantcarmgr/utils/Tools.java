package com.yiwucheguanjia.merchantcarmgr.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwucheguanjia.merchantcarmgr.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/7/12.
 */
public class Tools {

    public volatile static Tools tools;
    public static Tools getInstance(){
        Tools instance = null;
        if (tools == null){
            if (instance == null){
                instance = new Tools();
                tools = instance;
            }
        }
        return instance;
    }
    /***
     * 判断 String 是否是 int
     *
     * @param input
     * @return
     */
    public static boolean isInteger(String input){
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }

    /**
     * 动态创建图片
     * @param activity
     * @param
     * @return
     */
    public static Button createButton(Activity activity){
        Button myBtn = new Button(activity);
        myBtn.setBackgroundResource(R.color.black);
//        myBtn.setBackgroundColor();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(400,100);
//        params.addRule(RelativeLayout.ABOVE,R.id.pointLL);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        params.setMargins(0,0,0,300);
        myBtn.setLayoutParams(params);
        return myBtn;
    }
    //获取屏幕分辨率
    public static String getScreen(Activity activity){

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return size.x + "x" + size.y;
    }
    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面
        // 可以是0～9的数字，有9位。
        String telRegex = "[1][358]\\d{9}";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dipTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int dipTopxInt(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int pxTodip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
