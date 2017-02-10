package com.yiwucheguanjia.merchantcarmgr.post.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.post.PostServiceActivity;
import com.yiwucheguanjia.merchantcarmgr.post.model.ServiceItemBean;
import com.yiwucheguanjia.merchantcarmgr.utils.SharedPreferencesUtil;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 已发布服务适配器.
 */
public class PostedAdapter extends RecyclerView.Adapter<PostedAdapter.HolderView> {

    private Activity activity;
    private ArrayList<ServiceItemBean> serviceItemBeanArrayList;
    private LayoutInflater layoutInflater;
    private Handler handler;
    public PostedAdapter(Activity activity, Handler handler,ArrayList<ServiceItemBean> serviceItemBeanArrayList) {
        layoutInflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.handler = handler;
        this.serviceItemBeanArrayList = serviceItemBeanArrayList;

    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.post_item, parent, false);
        HolderView holderView = new HolderView(view, this.activity);
        return holderView;
    }

    @Override
    public void onBindViewHolder(HolderView holder, final int position) {
        final ServiceItemBean serviceItemBean = serviceItemBeanArrayList.get(position);
        Log.e("imgpaht",serviceItemBean.getImg_path());
        Glide.with(activity).load(serviceItemBean.getImg_path()).error(R.mipmap.default_image).centerCrop().into(holder.serviceImg);
        holder.serviceNameTv.setText(serviceItemBean.getService_name());
        holder.stateTv.setText(serviceItemBean.getState());
        holder.postTimeTv.setText(serviceItemBean.getDate_time());
        holder.access.setText(serviceItemBean.getAccess_times() + " 次");
        holder.deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog(serviceItemBean.getService_id(),position);
            }
        });
        holder.editTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postServiceIntent = new Intent(activity,PostServiceActivity.class);
                postServiceIntent.putExtra("postType","edit");//来自于哪里的标识，首发或者编辑修改
                postServiceIntent.putExtra("serviceTittle",serviceItemBean.getService_name());
                postServiceIntent.putExtra("serviceContent",serviceItemBean.getDetail());
                postServiceIntent.putExtra("servicePrice",serviceItemBean.getPrice());
                postServiceIntent.putExtra("serviceType","");
                postServiceIntent.putExtra("serviceScope",serviceItemBean.getScope());
                activity.getApplicationContext().sendBroadcast(postServiceIntent);
                activity.startActivity(postServiceIntent);
            }
        });
    }

    public void deleteDialog(final String serviceId, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getString(R.string.alert)).setMessage(activity.getResources().getString(R.string.delete_it))
                .setNegativeButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.cancel();
            }
        }).setPositiveButton(activity.getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteService(serviceId,position);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public int getItemCount() {
        return serviceItemBeanArrayList.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        @BindView(R.id.post_item_img)
        ImageView serviceImg;
        @BindView(R.id.post_it_servic_name_tv)
        TextView serviceNameTv;
        @BindView(R.id.post_it_state_tv)
        TextView stateTv;
        @BindView(R.id.post_it_time_tv)
        TextView postTimeTv;
        @BindView(R.id.post_it_access)
        TextView access;
        @BindView(R.id.post_it_delet)
        TextView deleteTv;
        @BindView(R.id.post_it_edit_tv)
        TextView editTv;
        private Activity activity;

        public HolderView(View view, Activity activity) {
            super(view);
            ButterKnife.bind(this, view);
            this.activity = activity;
        }
    }
    private void deleteService(final String position, final int listPosition){
        OkGo.post(UrlString.APP_DELETESERVICE)
                .params("username", SharedPreferencesUtil.getInstance(activity).usernameSharedPreferences())
                .params("token",SharedPreferencesUtil.getInstance(activity).tokenSharedPreference())
                .params("service_id",position)
                .params("version",UrlString.APP_VERSION)
                .execute(new MyStringCallback(this.activity,this.activity.getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("refuse",s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (TextUtils.equals(jsonObject.optString("opt_state","error"),"success")){
                                //if delete success,notifychange listview
                               Message message = Message.obtain();
                                message.what = 0;
                                message.arg1 = listPosition;
                                handler.sendMessage(message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
}
