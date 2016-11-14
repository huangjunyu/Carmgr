package com.yiwucheguanjia.merchantcarmgr.animation;

import android.app.Activity;
import android.content.DialogInterface;

import com.yiwucheguanjia.merchantcarmgr.R;

/**
 * Created by Administrator on 2016/11/14.
 */
public class AlertDialog {
    AlertDialog alertDialog;
    Activity activity;
    String hintContent;

    private final static AlertDialog dialog = null;
    public AlertDialog instance(){
        AlertDialog dialog = null;
        if (dialog == null){
            dialog = new AlertDialog(activity,hintContent);
        }
        return dialog;
    }
    public AlertDialog(Activity activity,String hintContent){
        this.activity = activity;
        this.hintContent = hintContent;
    }
    public void showAlertDialog(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.hint))
                .setMessage(hintContent)
                .setPositiveButton(activity.getString(R.string.i_know),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }
}
