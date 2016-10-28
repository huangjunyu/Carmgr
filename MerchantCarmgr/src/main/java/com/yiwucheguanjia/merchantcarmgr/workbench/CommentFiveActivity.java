package com.yiwucheguanjia.merchantcarmgr.workbench;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yiwucheguanjia.merchantcarmgr.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CommentFiveActivity extends Activity {

    private Button commentButton;        //评论按钮
    private EditText commentEdit;        //评论输入框
    private TextView senderNickname;    //发表者昵称
    private TextView sendTime;            //发表的时间
    private TextView sendContent;        //发表的内容
    private ImageView senderImg;        //发送者图片
    private RecyclerView commentList;//评论数据列表
    private LinearLayout bottomLinear;    //底部分享、评论等线性布局
    private LinearLayout commentLinear;    //评论输入框线性布局
    private LinearLayout itemLL;
    private int count;                    //记录评论ID
    private int position;                //记录回复评论的索引
    private int[] imgs;                    //图片资源ID数组
    private boolean isReply;            //是否是回复
    private String comment = "";        //记录对话框中的内容
    private List<CommentBean> list;

    private RecyclerAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_comment);
        initViews();
        init();
    }

    /**
     * 初始化UI界面
     */
    private void initViews() {
        commentButton = (Button) findViewById(R.id.commentButton);
        commentEdit = (EditText) findViewById(R.id.commentEdit);
        senderNickname = (TextView) findViewById(R.id.senderNickname);
        sendTime = (TextView) findViewById(R.id.sendTime);
        sendContent = (TextView) findViewById(R.id.sendContent);
        senderImg = (ImageView) findViewById(R.id.senderImg);

        commentList = (RecyclerView) findViewById(R.id.commentList);
        itemLL = (LinearLayout)findViewById(R.id.main_item_ll);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commentList.setLayoutManager(linearLayoutManager);

        commentLinear = (LinearLayout) findViewById(R.id.commentLinear);

        ClickListener cl = new ClickListener();
        commentButton.setOnClickListener(cl);
        itemLL.setOnClickListener(cl);
    }

    /**
     * 初始化数据
     */
    private void init() {
        //初始化发布者信息
//        InputStream is = getResources().openRawResource(R.drawable.dynamic1);
//        Bitmap bitmap = BitmapFactory.decodeStream(is);
        Glide.with(this).load(R.mipmap.appoint_manage_nor).into(senderImg);
//        senderImg.setImageBitmap(bitmap);
        senderNickname.setText("5疯子");
        sendTime.setText("13:00");
        sendContent.setText("我不想不想不想出差，要过年过年过年。好好玩玩玩玩玩玩");

//		adapter = new CommentAdapter(this, getCommentData(),R.layout.comment_item,handler);
//		commentList.setAdapter(adapter);
        Log.e("hello", getCommentData().size() + "");
        rvAdapter = new RecyclerAdapter(this, getCommentData(), R.layout.comment_item, handler);
        commentList.setAdapter(rvAdapter);
    }

    /**
     * 解析json获取评论列表数据
     */
    private List<CommentBean> getCommentData() {
        imgs = new int[]{R.mipmap.goback, R.mipmap.goback,
                R.mipmap.goback, R.mipmap.goback};
        list = new ArrayList<CommentBean>();
        count = imgs.length;
        for (int i = 0; i < imgs.length; i++) {
            CommentBean bean = new CommentBean();
            bean.setId(i + 1);
            bean.setCommentImgId(imgs[i]);
            bean.setCommentNickname("昵称" + i);
            bean.setCommentTime("13:" + i + "5");
            bean.setCommnetAccount("12345" + i);
            bean.setCommentContent("评论内容评论内容评论内容");
            bean.setReplyList(getReplyData());
            list.add(bean);
        }
        return list;
    }

    /**
     * 获取回复列表数据
     */
    private List<ReplyBean> getReplyData() {
        List<ReplyBean> replyList = new ArrayList<ReplyBean>();
        return replyList;
    }

    /**
     * 显示或隐藏输入法
     */
    private void onFocusChange(boolean hasFocus) {
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        commentEdit.getContext().getSystemService(INPUT_METHOD_SERVICE);
                if (isFocus) {
                    //显示输入法
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(commentEdit.getWindowToken(), 0);


                }
            }
        }, 100);
    }

    /**
     * 判断对话框中是否输入内容
     */
    private boolean isEditEmply() {
        comment = commentEdit.getText().toString().trim();
        if (comment.equals("")) {
            Toast.makeText(getApplicationContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        commentEdit.setText("");
        return true;
    }

    /**
     * 发表评论
     */
    private void publishComment() {
        CommentBean bean = new CommentBean();
        bean.setId(count);
        bean.setCommentImgId(imgs[count % 4]);
        bean.setCommentNickname("昵称" + count);
        bean.setCommentTime("13:" + count % 6 + "5");
        bean.setCommnetAccount("12345" + count);
        bean.setCommentContent(comment);
        list.add(bean);
        count++;
//        adapter.notifyDataSetChanged();
        Log.e("notity","data");
        rvAdapter.notifyDataSetChanged();
    }

    /**
     * 回复评论
     */
    private void replyComment() {
        ReplyBean bean = new ReplyBean();
        bean.setId(count + 10);
        bean.setCommentNickname(list.get(position).getCommentNickname());
        bean.setReplyNickname("我是回复的人");
        bean.setReplyContent(comment);
//        adapter.getReplyComment(bean, position);
//        adapter.notifyDataSetChanged();


    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) {
                isReply = true;
                position = (Integer) msg.obj;
                commentLinear.setVisibility(View.VISIBLE);
//                bottomLinear.setVisibility(View.GONE);
                onFocusChange(true);
            }
        }
    };

    /**
     * 事件点击监听器
     */
    private final class ClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.commentButton:    //发表评论按钮
                    if (isEditEmply()) {        //判断用户是否输入内容
                        if (isReply) {
                            replyComment();
                        } else {
                            publishComment();
                        }
                        onFocusChange(false);
                    }
                    break;
                case R.id.main_item_ll:

                    isReply = false;
                    commentLinear.setVisibility(View.VISIBLE);
//                    bottomLinear.setVisibility(View.GONE);
                    onFocusChange(true);
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //判断控件是否显示
        if (commentLinear.getVisibility() == View.VISIBLE) {
            commentLinear.setVisibility(View.GONE);
            bottomLinear.setVisibility(View.VISIBLE);
        }
    }

}
