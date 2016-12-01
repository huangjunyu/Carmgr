package com.yiwucheguanjia.merchantcarmgr.workbench.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;
import com.yiwucheguanjia.merchantcarmgr.workbench.controller.ComplaintAdapter;
import com.yiwucheguanjia.merchantcarmgr.workbench.model.RateImgBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/1.
 */
public class NoFragment extends Fragment {
    @BindView(R.id.complain_item_rv)
    RecyclerView recyclerView;
    ComplaintAdapter complaintAdapter;
    private ArrayList<RateImgBean> rateImgBeens;
    private SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.fragment_complain,container,false);
        ButterKnife.bind(this,homeView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getData();
        return homeView;
    }

    private void getData() {
        rateImgBeens = new ArrayList<>();
        final RateImgBean rateImgBean = new RateImgBean();
        sharedPreferences = getActivity().getSharedPreferences("CARMGR_MERCHANT", getActivity().MODE_PRIVATE);
        OkGo.post(UrlString.GET_COMPLAINT)
                .tag(this)
                .params("username", "13560102795")
                .params("token", sharedPreferences.getString("TOKEN", "null"))
                .params("version", UrlString.APP_VERSION)
                .execute(new MyStringCallback(getActivity(), getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("complain", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (TextUtils.equals(jsonObject.getString("opt_state"), "success")) {
                                rateImgBean.setComplaint_date(jsonObject.getString("complaint_date"));
                                rateImgBean.setComplaint_content(jsonObject.getString("complaint_content"));
                                rateImgBean.setCustom_username(jsonObject.getString("custom_username"));
                                rateImgBeens.add(rateImgBean);
                                Log.e("comn", ",ww");
                                complaintAdapter = new ComplaintAdapter(getActivity(), rateImgBeens);
                                recyclerView.setAdapter(complaintAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
