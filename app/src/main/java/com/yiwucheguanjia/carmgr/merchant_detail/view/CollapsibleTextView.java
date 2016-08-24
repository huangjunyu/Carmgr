package com.yiwucheguanjia.carmgr.merchant_detail.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.yiwucheguanjia.carmgr.R;

public class CollapsibleTextView extends LinearLayout implements
        OnClickListener {

    /** default text show max lines */
    private static final int DEFAULT_MAX_LINE_COUNT = 2;
    private static final int COLLAPSIBLE_STATE_NONE = 0;
    private static final int COLLAPSIBLE_STATE_SHRINKUP = 1;
    private static final int COLLAPSIBLE_STATE_SPREAD = 2;
    private TextView desc;
    private RelativeLayout descOp;
    private TextView descopTv;
    private String shrinkup;
    private String spread;
    private int mState;
    private boolean flag;
    private ImageView moreImg;
    public CollapsibleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        shrinkup = context.getString(R.string.desc_shrinkup);
        spread = context.getString(R.string.desc_spread);
        View view = inflate(context, R.layout.collapsible_textview, this);
        view.setPadding(0, -1, 0, 0);
        initView(view);
        descOp.setOnClickListener(this);
    }
        private void initView(View view){
        desc = (TextView) view.findViewById(R.id.desc_tv);
        descOp = (RelativeLayout) view.findViewById(R.id.descRl);
        descopTv = (TextView) view.findViewById(R.id.desc_op_tv);
        moreImg = (ImageView) view.findViewById(R.id.more_img);
    }

    public CollapsibleTextView(Context context) {
        this(context, null);
    }

    public final void setDesc(CharSequence charSequence, BufferType bufferType) {
        desc.setText(charSequence, bufferType);
        mState = COLLAPSIBLE_STATE_SPREAD;
        requestLayout();
    }

    @Override
    public void onClick(View v) {
        flag = false;
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!flag) {
            flag = true;
            desc.post(new Runnable() {
                @Override
                public void run() {

            if (desc.getLineCount() <= DEFAULT_MAX_LINE_COUNT) {
                mState = COLLAPSIBLE_STATE_NONE;
                descOp.setVisibility(View.GONE);
                desc.setMaxLines(DEFAULT_MAX_LINE_COUNT + 1);
            } else {
                if (mState == COLLAPSIBLE_STATE_SPREAD) {
                    desc.setMaxLines(DEFAULT_MAX_LINE_COUNT);
                    descOp.setVisibility(View.VISIBLE);
                    descopTv.setText(spread);
                    moreImg.setImageResource(R.mipmap.pull_down_black);
                    mState = COLLAPSIBLE_STATE_SHRINKUP;
                } else if (mState == COLLAPSIBLE_STATE_SHRINKUP) {
                    desc.setMaxLines(Integer.MAX_VALUE);
                    descOp.setVisibility(View.VISIBLE);
                    descopTv.setText(shrinkup);
                    moreImg.setImageResource(R.mipmap.pull_up_black);
                    mState = COLLAPSIBLE_STATE_SPREAD;
                }
            }
                }
            });
        }
    }
}
