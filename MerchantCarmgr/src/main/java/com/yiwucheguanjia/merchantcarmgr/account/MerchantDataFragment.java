package com.yiwucheguanjia.merchantcarmgr.account;

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
import com.yiwucheguanjia.merchantcarmgr.utils.GrideImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/18.
 */
public class MerchantDataFragment extends Fragment {
    private LinearLayout merchantDataView;
    @BindView(R.id.merchant_next_btn)
    Button nextBtn;
    @BindView(R.id.merchant_license_img)
    ImageView licenseImg;
    private ImagePicker imagePicker;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFunction();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        merchantDataView = (LinearLayout)inflater.inflate(R.layout.enter_merchant_data,null);
        ButterKnife.bind(this,merchantDataView);
        return merchantDataView;
    }
    private void initFunction(){
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GrideImageLoader());
        imagePicker.setMultiMode(false);
    }
    @OnClick({R.id.merchant_next_btn,R.id.merchant_license_img})
    void nextOnClick(View button){
        Log.e("1","wwwwjj");
        switch (button.getId()){
            case R.id.merchant_next_btn:
                Intent intent = new Intent();
                intent.setAction("action.next_two");
                getActivity().sendBroadcast(intent);
                Log.e("1","ww");
                break;
            case R.id.merchant_license_img:
                uploadLicense(3);
                break;
            default:
                break;
        }
    }
    public void uploadLicense(int i) {
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
            if (data != null && requestCode == 3) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem image = images.get(0);
                imagePicker.getImageLoader().displayImage(getActivity(), image.path, licenseImg, 300, 300);
            }else{
                Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
