package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.yiwucheguanjia.merchantcarmgr.city.CityActivity;
import com.yiwucheguanjia.merchantcarmgr.utils.GrideImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/18.
 */
public class JionDataFragment extends Fragment {
    private LinearLayout merchantDataView;
    private ImagePicker imagePicker;
    @BindView(R.id.jion_next_btn)
    Button nextBtn;
    @BindView(R.id.jion_stor_img1)
    ImageView img1;
    @BindView(R.id.jion_stor_img2)
    ImageView img2;
    @BindView(R.id.jion_stor_img3)
    ImageView img3;
//    @BindViews({R.id.jion_stor_img1,R.id.jion_stor_img2,R.id.jion_stor_img3})
//    ImageView[] img1,img2,img3;

//    ArrayList<ImageView> imgs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFunction();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        merchantDataView = (LinearLayout)inflater.inflate(R.layout.activity_jion,container,false);
        ButterKnife.bind(this,merchantDataView);
        return merchantDataView;
    }
    private void initFunction(){
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GrideImageLoader());
        imagePicker.setMultiMode(false);
    }
    @OnClick({R.id.jion_stor_img1,R.id.jion_stor_img2,R.id.jion_stor_img3})void setImg(View view){
        switch (view.getId()){
            case R.id.jion_stor_img1:
                uploadIdCar(1);
                break;
            case R.id.jion_stor_img2:
                uploadIdCar(2);
                break;
            case R.id.jion_stor_img3:
                uploadIdCar(3);
                break;
            default:
                break;
        }
    }
    public void uploadIdCar(int i) {
        imagePicker.setImageLoader(new GrideImageLoader());
        imagePicker.setCrop(false);
        //打开选择,本次允许选择的数量
        ImagePicker.getInstance().setSelectLimit(5);
        Intent intent = new Intent(getActivity(), ImageGridActivity.class);
        startActivityForResult(intent, i);
    }
    @OnClick(R.id.jion_next_btn)
    void setNextBtn(){
        Intent intent = new Intent(getActivity(), CityActivity.class);
        startActivity(intent);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 1) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem image = images.get(0);
                imagePicker.getImageLoader().displayImage(getActivity(), image.path,img1 , 300, 300);
            }else if (data != null &&requestCode == 2){
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem image = images.get(0);
                imagePicker.getImageLoader().displayImage(getActivity(), image.path, img2, 300, 300);
            }else if (data != null &&requestCode == 3){
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem image = images.get(0);
                imagePicker.getImageLoader().displayImage(getActivity(), image.path, img3, 300, 300);
            }
            else{
                Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
