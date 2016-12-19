package com.yiwucheguanjia.carmgr.my;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiwucheguanjia.carmgr.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * 订单记录适配器.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.HolderView> {

    private Activity activity;
    private ArrayList<OrderRecordBean> orderRecordBeans;
    private LayoutInflater layoutInflater;

    public OrderAdapter(Activity activity, ArrayList<OrderRecordBean> orderRecorBeans) {
        layoutInflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.orderRecordBeans = orderRecorBeans;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.item_order_record, parent, false);
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
