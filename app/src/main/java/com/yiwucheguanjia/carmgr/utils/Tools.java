package com.yiwucheguanjia.carmgr.utils;

import android.app.Activity;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(33,27);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.setMargins(i * 50,0,0,0);
        mImageView.setLayoutParams(params);
        return mImageView;
//        myItemViewHolder.merchantStarsLL.addView(mImageView);
    }
    public static ImageView createImageview2(Activity activity,int i){
        ImageView mImageView = new ImageView(activity);
        mImageView.setImageResource(R.mipmap.half_heart);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(33,27);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.setMargins(i * 50,0,0,0);
        mImageView.setLayoutParams(params);
        return mImageView;
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
//        params.addRule(RelativeLayout.);
        textView.setLayoutParams(params);
        if (tags[k].equals("综")){
//            mImageView.setImageResource(R.mipmap.round_rectangle_7);
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
        Display display = activity.getWindowManager().getDefaultDisplay(); //Activity#getWindowManager()
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return size.x + "x" + size.y;
    }
}
