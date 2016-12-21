package com.yiwucheguanjia.merchantcarmgr.post;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.post.controller.PostServiceItemAdapter;
import com.yiwucheguanjia.merchantcarmgr.post.controller.PostedAdapter;
import com.yiwucheguanjia.merchantcarmgr.post.model.ServiceItemBean;
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
 * Created by Administrator on 2016/10/17.
 */
public class PostFragment extends Fragment {

    LinearLayout postFragmentHomeLl;
    @BindView(R.id.postfragment_post_tv)
    TextView postTv;
    @BindView(R.id.post_ft_item_rv)
    RecyclerView postItemRv;
    PostServiceItemAdapter postServiceItemAdapter;//选择图片后的图片展示列表
    private int POST_MANAGE_REQUEST = 201;//发布服务后的请求码
    private int POST_MANAGE_RESULT = 202;//发布服务后的结果码
    SharedPreferences sharedPreferences;
    private ArrayList<ServiceItemBean> serviceItemBeenList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("CARMGR_MERCHANT", getActivity().MODE_PRIVATE);
//        Log.e("data", sharedPreferences.getString("TOKEN", null));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        postFragmentHomeLl = (LinearLayout) inflater.inflate(R.layout.activity_post_fragment, container, false);
        ButterKnife.bind(this, postFragmentHomeLl);
        getData();
//        postServiceItemAdapter = new PostServiceItemAdapter(getActivity());
//        postItemRv.setAdapter(postServiceItemAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        postItemRv.setLayoutManager(linearLayoutManager);
        return postFragmentHomeLl;
    }

    @OnClick(R.id.postfragment_post_tv)
    void onClickView(View view) {
        switch (view.getId()) {
            case R.id.postfragment_post_tv:
                Intent postSIntent = new Intent(getActivity(), PostServiceActivity.class);
                startActivity(postSIntent);
//                getActivity().finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == POST_MANAGE_REQUEST && resultCode == POST_MANAGE_RESULT) {
            Log.e("shewww", "haheheh");
            //刷新界面
            getData();
        }
    }

    private void getData() {


        OkGo.post(UrlString.APP_GET_PUBEDSERVICE)
                .tag(this)
                .params("username", sharedPreferences.getString("ACCOUNT", null))
                .params("token", sharedPreferences.getString("TOKEN", null))
                .params("version", UrlString.APP_VERSION)
                .execute(new MyStringCallback(getActivity(), getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            serviceItemBeenList = new ArrayList<ServiceItemBean>();
                            JSONObject itemJson;
                            Log.e("jsong",s);
                            if (jsonObject.getJSONArray("services_list").length() > 0) {
                                for (int i = 0; i < jsonObject.getJSONArray("services_list").length(); i++) {
                                    ServiceItemBean serviceItemBean = new ServiceItemBean();
                                    itemJson = jsonObject.getJSONArray("services_list").getJSONObject(i);
                                    Log.e("getpost", itemJson.getString("img_path"));
                                    serviceItemBean.setImg_path(itemJson.getString("img_path"));
                                    serviceItemBean.setState(itemJson.getString("state"));
                                    serviceItemBean.setService_name(itemJson.getString("service_name"));
                                    serviceItemBean.setAccess_times(itemJson.getString("access_times"));
                                    serviceItemBean.setDate_time(itemJson.getString("date_time"));
                                    serviceItemBeenList.add(serviceItemBean);
                                }
                                PostedAdapter postedAdapter = new PostedAdapter(getActivity(), serviceItemBeenList);
                                postItemRv.setAdapter(postedAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                });
    }
}
