package com.yiwucheguanjia.merchantcarmgr.post;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.post.controller.PostServiceItemAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/17.
 */
public class PostFragment extends Fragment {

    LinearLayout postFragmentHomeLl;
    @BindView(R.id.postfragment_post_tv)
    TextView postTv;
    @BindView(R.id.post_ft_item_rv)
    RecyclerView postItemRv;
    PostServiceItemAdapter postServiceItemAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postFragmentHomeLl = (LinearLayout)inflater.inflate(R.layout.activity_post_fragment,container,false);
        ButterKnife.bind(this,postFragmentHomeLl);
        postServiceItemAdapter = new PostServiceItemAdapter(getActivity());
        postItemRv.setAdapter(postServiceItemAdapter);
        return postFragmentHomeLl;
    }

    @OnClick(R.id.postfragment_post_tv)
    void onClickView(View view){
        switch (view.getId()){
            case R.id.postfragment_post_tv:
                Intent postSIntent = new Intent(getActivity(),PostServic.class);
                startActivity(postSIntent);
//                getActivity().finish();
                break;
            default:
                break;
        }
    }
}
