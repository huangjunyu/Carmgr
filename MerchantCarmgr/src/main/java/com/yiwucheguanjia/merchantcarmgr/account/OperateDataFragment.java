package com.yiwucheguanjia.merchantcarmgr.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.city.utils.GrideImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/17.
 */
public class OperateDataFragment extends Fragment {
    private LinearLayout operateDataView;
    private Activity activity;
    private ImagePicker imagePicker;
    @BindView(R.id.enter_data_next_btn) Button nextBtn;
    @BindView(R.id.enter_data_id_img_front)
    ImageView idImgFront;//身份证正面
    @BindView(R.id.enter_data_id_img_reverse)
    ImageView idImgReverse;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFunction();
    }

    @OnClick({R.id.enter_data_id_img_front,R.id.enter_data_id_img_reverse})
    void setOnClickListener(View button){
        switch (button.getId()){
            case R.id.enter_data_id_img_front:
                uploadIdCar(1);
                break;
            case R.id.enter_data_id_img_reverse:
                uploadIdCar(2);
                break;
            default:
                break;
        }
    }
    private void initFunction(){
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GrideImageLoader());
        imagePicker.setMultiMode(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        operateDataView = (LinearLayout) inflater.inflate(R.layout.activity_enter_data,null,false);
        Log.e("jwk","jjwkwkw44");
        ButterKnife.bind(this,operateDataView);
        return operateDataView;
    }


    public void uploadIdCar(int i) {
        imagePicker.setImageLoader(new GrideImageLoader());
        imagePicker.setCrop(false);
        //打开选择,本次允许选择的数量
        ImagePicker.getInstance().setSelectLimit(5);
        Intent intent = new Intent(getActivity(), ImageGridActivity.class);
        startActivityForResult(intent, i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 1) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem image = images.get(0);
                imagePicker.getImageLoader().displayImage(getActivity(), image.path, idImgFront, 300, 300);
            } else if (data != null && requestCode == 2){
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem image = images.get(0);
                imagePicker.getImageLoader().displayImage(getActivity(), image.path, idImgReverse, 300, 300);
            } else{
                Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @OnClick(R.id.enter_data_next_btn) void nextStep(){
        Intent intent = new Intent();
        intent.setAction("action.next_one");
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
