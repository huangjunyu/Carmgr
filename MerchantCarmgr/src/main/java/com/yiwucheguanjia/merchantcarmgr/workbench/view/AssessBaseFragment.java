package com.yiwucheguanjia.merchantcarmgr.workbench.view;

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

import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.checkpictureutils.ItemEntity;
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
 * Created by Administrator on 2016/11/17.
 */
public abstract class AssessBaseFragment extends Fragment {
    @BindView(R.id.star_rv)
    RecyclerView recyclerView;
    RateAdapter rateAdapter;
    private SharedPreferences sharedPreferences;
    LinearLayoutManager linearLayoutManager;
    //Item数据实体集合
    private ArrayList<ItemEntity> itemEntities;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.fragment_star, container, false);
        ButterKnife.bind(this, homeView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getData();
        return homeView;
    }

    protected abstract int getStar();

    private void getData() {

        itemEntities = new ArrayList<ItemEntity>();
        final ArrayList<String> urls_1 = new ArrayList<String>();
        urls_1.add("http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg");
        sharedPreferences = getActivity().getSharedPreferences("CARMGR_MERCHANT", getActivity().MODE_PRIVATE);
        OkGo.post(UrlString.GET_ADVISE)
                .tag(this)
                .params("username", "13560102795")
                .params("token", sharedPreferences.getString("TOKEN", "null"))
                .params("version", UrlString.APP_VERSION)
                .execute(new MyStringCallback(getActivity(), getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String username = jsonObject.getString("username");
                            String content = jsonObject.getString("advise_content");
                            String time = jsonObject.getString("advise_date");
                            String nickName = jsonObject.getString("custom_username");
                            int star = jsonObject.getInt("advise_star");
                            ItemEntity entity = new ItemEntity(urls_1, content, nickName, time);
                            itemEntities.add(entity);
                            //筛选后的数据传给adapter处理
                            rateAdapter = new RateAdapter(getActivity(), itemEntities, getStar());
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
