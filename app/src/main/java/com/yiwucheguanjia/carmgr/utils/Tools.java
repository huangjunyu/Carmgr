package com.yiwucheguanjia.carmgr.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yiwucheguanjia.carmgr.R;

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
     * @param i
     * @return
     */
    public static ImageView createImageview(Activity activity,int i){
        ImageView mImageView = new ImageView(activity);
        mImageView.setImageResource(R.mipmap.heart);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dipTopx(activity,10),dipTopx(activity,10));
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.setMargins(i * dipTopx(activity,16),0,0,0);
        mImageView.setLayoutParams(params);
        return mImageView;
    }
    public static ImageView createImageview2(Activity activity,int i){
        ImageView mImageView = new ImageView(activity);
        mImageView.setImageResource(R.mipmap.half_heart);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dipTopx(activity,10),dipTopx(activity,10));
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.setMargins(i * dipTopx(activity,16),0,0,0);
        mImageView.setLayoutParams(params);
        return mImageView;
    }
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
    public static TextView createImageView3(Activity activity,String[] tags,int k){
        ImageView mImageView = new ImageView(activity);
        TextView textView = new TextView(activity);
        textView.setTextColor(activity.getResources().getColor(R.color.white));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
        textView.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(40,40);
        params.setMargins(k * 50,0,0,0);
        params.addRule(RelativeLayout.RIGHT_OF,R.id.merchant_name_txt);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        textView.setLayoutParams(params);
        if (tags[k].equals("综")){
            textView.setBackgroundResource(R.mipmap.round_rectangle_7);
            textView.setText("综");
        }else if (tags[k].equals("金")){
            textView.setBackgroundResource(R.mipmap.round_rectangle_1);
            textView.setText("金");
        }else if (tags[k].equals("洗")){
            textView.setBackgroundResource(R.mipmap.round_rectangle_3);
            textView.setText("洗");
        }else if (tags[k].equals("修")){
            textView.setBackgroundResource(R.mipmap.round_rectangle_4);
            textView.setText("修");
        }else if (tags[k].equals("牌")){
            textView.setBackgroundResource(R.mipmap.round_rectangle_1);
            textView.setText("牌");
        }
        return textView;
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
    public AlertDialog dialog(String title,String content,String checked,Activity context){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title).setMessage(content).setNegativeButton(checked, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        return alert;
    }
}
