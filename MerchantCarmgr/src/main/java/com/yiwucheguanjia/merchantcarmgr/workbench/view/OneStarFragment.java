package com.yiwucheguanjia.merchantcarmgr.workbench.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.checkpictureutils.ItemEntity;
import com.yiwucheguanjia.merchantcarmgr.utils.SharedPreferencesUtil;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;
import com.yiwucheguanjia.merchantcarmgr.workbench.controller.RateAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/31.
 */
public class OneStarFragment extends AssessBaseFragment {
    @BindView(R.id.star_rv)
    RecyclerView recyclerView;
    RateAdapter rateAdapter;
    private SharedPreferences sharedPreferences;
    LinearLayoutManager linearLayoutManager;
    //Item数据实体集合
    private ArrayList<ItemEntity> itemEntities;

    private View view;
    public OneStarFragment() {
        Log.e("ssssssss", "world");
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_star,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    protected void initData() {
        getData();
    }


    //    protected abstract int getStar();
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private void getData() {

        itemEntities = new ArrayList<ItemEntity>();
        final ArrayList<String> urls_1 = new ArrayList<String>();
        urls_1.add("http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg");
        sharedPreferences = getActivity().getSharedPreferences("CARMGR_MERCHANT", getActivity().MODE_PRIVATE);
        OkGo.post(UrlString.GET_ADVISE)
                .tag(this)
                .params("username", SharedPreferencesUtil.getInstance(getActivity()).usernameSharedPreferences())
                .params("token", SharedPreferencesUtil.getInstance(getActivity()).tokenSharedPreference())
                .params("version", UrlString.APP_VERSION)
                .execute(new MyStringCallback(getActivity(), getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            handler.sendEmptyMessage(0);
                            JSONObject jsonObject = new JSONObject(s);
                            String username = jsonObject.getString("username");
                            String content = jsonObject.getString("advise_content");
                            String time = jsonObject.getString("advise_date");
                            String nickName = jsonObject.getString("custom_username");
                            int star = jsonObject.getInt("advise_star");
                            ItemEntity entity = new ItemEntity(urls_1, content, nickName, time);
                            itemEntities.add(entity);
                            linearLayoutManager = new LinearLayoutManager(getActivity());
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            //筛选后的数据传给adapter处理
                            rateAdapter = new RateAdapter(getActivity(), itemEntities,3);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(rateAdapter);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                        Log.e("ssssssss", s);
                    }
                });
    }
}
