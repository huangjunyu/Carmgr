package com.yiwucheguanjia.carmgr.my.controller;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.my.AddressBean;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * 用户投诉适配器.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.HolderView> {

    private Activity activity;
    private ArrayList<AddressBean> rateImgBeens;
    private LayoutInflater layoutInflater;

    public AddressAdapter(Activity activity, ArrayList<AddressBean> addressBeen) {
        layoutInflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.rateImgBeens = addressBeen;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.item_address, parent, false);
        HolderView holderView = new HolderView(view, this.activity);
        return holderView;
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
//        holder.usernameTv.setText(rateImgBeens.get(position).getCustom_username());
//        holder.complainTimeTv.setText(rateImgBeens.get(position).getComplaint_date());
//        holder.complainCommentTv.setText(rateImgBeens.get(position).getComplaint_content());
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class HolderView extends RecyclerView.ViewHolder {
//        @BindView(R.id.complain_user_img)
//        ImageView headerImg;
//        @BindView(R.id.complain_user_name_tv)
//        TextView usernameTv;
//        @BindView(R.id.complain_time)
//        TextView complainTimeTv;
//        @BindView(R.id.complain_comment)
//        TextView complainCommentTv;
//        private Activity activity;

        public HolderView(View view, Activity activity) {
            super(view);
            ButterKnife.bind(this, view);
//            this.activity = activity;
        }
    }
}
