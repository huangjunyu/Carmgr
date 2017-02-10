package com.yiwucheguanjia.merchantcarmgr.account;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.utils.GlideImageLoader;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/17.
 */
public class OperateDataFragment extends Fragment {
    private LinearLayout operateDataView;
    private Activity activity;
    private ImagePicker imagePicker;
    @BindView(R.id.enter_data_next_btn)
    Button nextBtn;
    @BindView(R.id.enter_data_id_img_front)
    ImageView idImgFront;//身份证正面
    @BindView(R.id.enter_data_id_img_reverse)
    ImageView idImgReverse;
    @BindView(R.id.enter_data_name_edit)
    EditText nameEd;
    @BindView(R.id.enter_data_id_car_ed)
    EditText idCarEd;
    private ArrayList<ImageItem> imageItems;
    private ArrayList<ImageItem> imageItemFronts;
    private ArrayList<ImageItem> imageItemReverses;
    private SharedPreferences sharedPreferences;
    private Boolean imgFrontState = false;
    private Boolean imgReverseState = false;
    public String imgPathFrontResponse = null;
    public String imgPathReverseResponse = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("CARMGR_MERCHANT", getActivity().MODE_PRIVATE);
        initFunction();
    }

    private Boolean checked() {
        Log.e("nae", nameEd.getText().toString() + "1  " + idCarEd.getText().toString().length() + "2" + imgFrontState + imgReverseState);
        if (TextUtils.isEmpty(nameEd.getText().toString())) {
            Toast.makeText(getActivity(), "经营者姓名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (idCarEd.getText().toString().length() != 18) {
            Toast.makeText(getActivity(), "身份证号码位数必须是18位", Toast.LENGTH_SHORT).show();
            return false;
        } else if (imgReverseState != true && imgFrontState != true) {
            Toast.makeText(getActivity(), "请上传身份证正反面照片", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    @OnClick({R.id.enter_data_id_img_front, R.id.enter_data_id_img_reverse})
    void setOnClickListener(View button) {
        switch (button.getId()) {
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

    private void initFunction() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        operateDataView = (LinearLayout) inflater.inflate(R.layout.fragment_operate_data, null, false);
        Log.e("jwk", "jjwkwkw44");
        ButterKnife.bind(this, operateDataView);
        return operateDataView;
    }


    public void uploadIdCar(int i) {
        imagePicker.setImageLoader(new GlideImageLoader());
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
                //只能选择单张图片，这里获取单张图片
                imageItemFronts = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                imagePicker.getImageLoader().displayImage(getActivity(), imageItemFronts.get(0).path, idImgFront, 300, 300);
            } else if (data != null && requestCode == 2) {
                imageItemReverses = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                imagePicker.getImageLoader().displayImage(getActivity(), imageItemReverses.get(0).path, idImgReverse, 300, 300);
            } else {
                Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick({R.id.enter_data_next_btn, R.id.enter_data_id_submit_bt})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.enter_data_next_btn:
                if (checked()) {
                    Log.e("next", "next");
                    Intent intent = new Intent();
                    intent.setAction("action.next_one");
                    getActivity().sendBroadcast(intent);

                }
                break;
            case R.id.enter_data_id_submit_bt:
                formUpload();
                break;
            default:
                break;
        }
    }

    public void formUpload() {
        ArrayList<File> files = new ArrayList<>();
        if (imageItemFronts != null && imageItemFronts.size() > 0 && imageItemReverses != null && imageItemReverses.size() > 0) {
        } else {//如果没有图片，则不能上传
            Toast.makeText(getActivity(), "请选择图片", Toast.LENGTH_SHORT).show();
            return;
        }
        postImg(imageItemFronts, "operator_id_img_a");
        postImg(imageItemReverses, "operator_id_img_b");
    }

    private void postImg(ArrayList<ImageItem> imageItems, final String imgType) {
        OkGo.post(UrlString.APP_UPLOAD)
                .tag(this)
                .params("username", sharedPreferences.getString("ACCOUNT", "null"))
                .params("type", imgType)
                .params("token", sharedPreferences.getString("TOKEN", "null"))
                .params("version", UrlString.APP_VERSION)
                .params("file1", new File(imageItems.get(0).path))
                .params("file_count", 2).execute(
                new MyStringCallback(getActivity(), getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("string", s);
                        try {
                            final JSONObject jsonObject = new JSONObject(s);
                            if (TextUtils.equals(jsonObject.getString("opt_state"), "success")) {
                                JSONArray fileStoreList = jsonObject.getJSONArray("file_store_list");
                                if (TextUtils.equals(imgType, "operator_id_img_a")) {
                                    //如果图片上传成功，则设置对应该图片上传的boolean为true,
                                    imgFrontState = true;
                                    //记下上传该图片成功后返回的服务器存储路径
                                    imgPathFrontResponse = fileStoreList.getJSONObject(0).getString("store_path");
                                } else {
                                    imgReverseState = true;
                                    imgPathReverseResponse = fileStoreList.getJSONObject(0).getString("store_path");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
