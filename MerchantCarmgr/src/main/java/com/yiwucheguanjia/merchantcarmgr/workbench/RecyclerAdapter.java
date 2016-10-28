package com.yiwucheguanjia.merchantcarmgr.workbench;

/**
 * Created by Administrator on 2016/10/25.
 */

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiwucheguanjia.merchantcarmgr.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/1.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<CommentBean> commentBeans;
    private Activity context;

    private int resourceId;
    private Handler handler;

    public RecyclerAdapter(Activity context, List<CommentBean> commentBeans, int resourcedId, Handler handler) {
        this.context = context;
        this.commentBeans = commentBeans;
        this.resourceId = resourcedId;
        this.handler = handler;
        mInflater = LayoutInflater.from(this.context);
    }


    @Override
    public int getItemCount() {
        Log.e("size",commentBeans.size() + "");
        return commentBeans.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(this.resourceId,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final CommentBean rateBean = commentBeans.get(position);
        viewHolder.commentNickname.setText("hehahe");
        viewHolder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ok","ok");
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.commentItemImg)
        public ImageView commentItemImg;            //评论人图片
//        @BindView(R.id.commentNickname)
        public TextView commentNickname;            //评论人昵称

        @BindView(R.id.replyText)
        public TextView replyText;                    //回复
        @BindView(R.id.commentItemTime)
        public TextView commentItemTime;            //评论时间
        @BindView(R.id.commentItemContent)
        public TextView commentItemContent;            //评论内容
//        @BindView(R.id.replyList)

        public LinearLayout itemLl;

        public ViewHolder(View viewHolder) {
            super(viewHolder);
            commentNickname = (TextView)viewHolder.findViewById(R.id.commentNickname);
            itemLl = (LinearLayout)viewHolder.findViewById(R.id.comment_item_ll);
            replyText = (TextView)viewHolder.findViewById(R.id.replyText);
            ButterKnife.bind(this,viewHolder);

        }

    }

}


