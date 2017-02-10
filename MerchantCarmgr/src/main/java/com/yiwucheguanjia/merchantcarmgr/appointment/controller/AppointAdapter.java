package com.yiwucheguanjia.merchantcarmgr.appointment.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.yiwucheguanjia.merchantcarmgr.appointment.model.AppointmentBean;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.utils.SharedPreferencesUtil;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/28.
 */
public class AppointAdapter extends RecyclerView.Adapter<AppointAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private Activity activity;
    private ArrayList<AppointmentBean> appointmentBeanArrayList;
    private Handler handler;

    public AppointAdapter(Activity activity, ArrayList<AppointmentBean> appointmentBeanArrayList, Handler handler) {
        this.activity = activity;
        this.appointmentBeanArrayList = appointmentBeanArrayList;
        this.handler = handler;
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_appointment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final AppointmentBean appointmentBean = appointmentBeanArrayList.get(position);
        Glide.with(activity).load(appointmentBean.getSubscribe_service_img()).error(R.mipmap.default_image).centerCrop().into(holder.goodsImg);
        holder.orderNumTv.setText(appointmentBean.getSubscribe_order());
        holder.phoneTv.setText(appointmentBean.getSubscribe_user_mobile());
        holder.goodsTv.setText(appointmentBean.getSubscribe_service_name());
        holder.goodsNumTv.setText("共计" + appointmentBean.getSubscribe_service_total() + "件商品");
        holder.priceTv.setText("价格：" + appointmentBean.getSubscribe_price());
        holder.customerTv.setText(appointmentBean.getSubscribe_user_mobile());
        holder.appointType.setText((appointmentBean.getSubscribe_state() == "0" ? activity.getResources().getString(R.string.going) : activity.getResources().getString(R.string.going)));
        holder.timeTv.setText(appointmentBean.getSubscribe_date());
        holder.refuseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationDialog(appointmentBean.getSubscribe_service_id(), position, "你确认要拒绝该预约吗？", 0);
            }
        });
        holder.acceptTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationDialog(appointmentBean.getSubscribe_service_id(), position, "确认接受该预约吗？", 1);
            }
        });
        holder.editTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationDialog(appointmentBean.getSubscribe_service_id(), position, "该服务已完成？", 3);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentBeanArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.allappoint_order_num_tv)
        TextView orderNumTv;
        @BindView(R.id.allappoint_customer_tv)
        TextView customerTv;
        @BindView(R.id.allappoint_phone_tv)
        TextView phoneTv;
        @BindView(R.id.allapoint_goods_img)
        ImageView goodsImg;
        @BindView(R.id.allappoint_goods_name_tv)
        TextView goodsTv;
        @BindView(R.id.appoin_goods_num_tv)
        TextView goodsNumTv;
        @BindView(R.id.appoint_price_tv)
        TextView priceTv;
        @BindView(R.id.it_appoint_refuse_tv)
        TextView refuseTv;
        @BindView(R.id.it_appoin_edit_tv)
        TextView editTv;
        @BindView(R.id.it_appoin_accept_tv)
        TextView acceptTv;
        @BindView(R.id.allappoint_type)
        TextView appointType;
        @BindView(R.id.allapoint_time)
        TextView timeTv;
        Activity activity;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            this.activity = activity;
        }

        @OnClick({R.id.it_appoint_refuse_tv, R.id.it_appoin_accept_tv})
        void click(View view) {
            switch (view.getId()) {
                case R.id.it_appoint_refuse_tv:
                    break;
                case R.id.it_appoin_accept_tv:

                default:
                    break;
            }
        }
    }

    public void operationDialog(final String serviceId, final int position, String hint, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getString(R.string.hint)).setMessage(hint)
                .setNegativeButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.cancel();
                    }
                }).setPositiveButton(activity.getResources().getString(R.string.checked), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                operationAppoint(position, type);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void operationAppoint(final int listPosition, int type) {
        Log.e("service_id", appointmentBeanArrayList.get(listPosition).getSubscribe_service_id());
        OkGo.post(UrlString.MAPP_SUBSERVICEOPT)
                .params("username", SharedPreferencesUtil.getInstance(activity).usernameSharedPreferences())
                .params("token", SharedPreferencesUtil.getInstance(activity).tokenSharedPreference())
                .params("service_id", appointmentBeanArrayList.get(listPosition).getSubscribe_service_id())
                .params("opt_type", type)
                .params("version", UrlString.APP_VERSION)
                .execute(new MyStringCallback(this.activity, this.activity.getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("refuse", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (TextUtils.equals(jsonObject.optString("opt_state", "error"), "success")) {
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
