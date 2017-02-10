package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.city.CityActivity;
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
 * Created by Administrator on 2016/9/18.
 */
public class MerchantDataFragment extends Fragment {
    private LinearLayout merchantDataView;
    @BindView(R.id.merchant_next_btn)
    Button nextBtn;
    @BindView(R.id.merchant_license_img)
    ImageView licenseImg;
    @BindView(R.id.merchant_name_edit)
    EditText storeNameEd;
    @BindView(R.id.merchant_area_tv)
    TextView areaTv;
    @BindView(R.id.merchant_detail_addr_ed)
    EditText detailAddrEd;
    @BindView(R.id.merchant_service_pho_ed)
    EditText servicePhoEd;

    private ImagePicker imagePicker;
    private ArrayList<ImageItem> images;
    public String businessLicensePathResponse = null;
    private Boolean uploadImgState = false;
    private SharedPreferences sharedPreferences;
    private int CITY_REQUEST = 102;
    private int CITY_RESULT = 1021;

    private Boolean checked() {

        if (TextUtils.isEmpty(storeNameEd.getText().toString())) {
            Toast.makeText(getActivity(), "店面名称不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(areaTv.getText().toString())) {
            Toast.makeText(getActivity(), "请选择所在地区", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(detailAddrEd.getText().toString())) {
            Toast.makeText(getActivity(), "详细地址不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(servicePhoEd.getText().toString())) {
            Toast.makeText(getActivity(), "电话号码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!uploadImgState) {
            Toast.makeText(getActivity(), "请上传相关照片", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("CARMGR_MERCHANT", getActivity().MODE_PRIVATE);
        initFunction();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        merchantDataView = (LinearLayout) inflater.inflate(R.layout.fragment_merchant_data, null);
        ButterKnife.bind(this, merchantDataView);
        return merchantDataView;
    }

    private void initFunction() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(false);
    }

    @OnClick({R.id.merchant_next_btn, R.id.merchant_license_img, R.id.merchant_upload_btn, R.id.merchant_area_tv})
    void onClick(View button) {
        switch (button.getId()) {
            case R.id.merchant_next_btn:
                if (checked()) {
                    Intent intent = new Intent();
                    intent.setAction("action.next_two");
                    getActivity().sendBroadcast(intent);
                    //此处将全部已填写的资料提交
                    MerchantEnterFragmentActivity merchantEnterFragmentActivity = (MerchantEnterFragmentActivity) getActivity();
                    merchantEnterFragmentActivity.nextStep(3);
                }
                break;
            case R.id.merchant_license_img:
                uploadLicense(3);
                break;
            case R.id.merchant_upload_btn:
                formUpload();
                break;
            case R.id.merchant_area_tv:
                Intent cityIntent = new Intent(getActivity(), CityActivity.class);
                startActivityForResult(cityIntent, CITY_REQUEST);
                break;
            default:
                break;
        }
    }

    public void uploadLicense(int i) {
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
            if (data != null && requestCode == 3) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem image = images.get(0);
                imagePicker.getImageLoader().displayImage(getActivity(), image.path, licenseImg, 300, 300);
            } else {
                Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == CITY_REQUEST && resultCode == CITY_RESULT) {
            areaTv.setText(data.getExtras().getString("city_area"));
        }
    }

    public void formUpload() {
        ArrayList<File> files = new ArrayList<>();
        if (images != null && images.size() > 0) {
            OkGo.post(UrlString.APP_UPLOAD)
                    .tag(this)
                    .params("username", sharedPreferences.getString("ACCOUNT", "null"))
                    .params("type", "shop_license_img")
                    .params("token", sharedPreferences.getString("TOKEN", "null"))
                    .params("version", UrlString.APP_VERSION)
                    .params("file1", new File(images.get(0).path))
                    .params("file_count", 2).execute(
                    new MyStringCallback(getActivity(), getResources().getString(R.string.loading)) {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                final JSONObject jsonObject = new JSONObject(s);
                                if (TextUtils.equals(jsonObject.getString("opt_state"), "success")) {
                                    uploadImgState = true;
                                    JSONArray fileStoreList = jsonObject.getJSONArray("file_store_list");
                                    for (int i = 0; i < fileStoreList.length(); i++) {

                                        businessLicensePathResponse = fileStoreList.getJSONObject(i).getString("store_path");
                                        businessLicensePathResponse = businessLicensePathResponse + "^";
                                    }
                                    businessLicensePathResponse = businessLicensePathResponse.substring(0,businessLicensePathResponse.length() - 1);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
        } else {//如果没有图片，则不能上传
            Toast.makeText(getActivity(), "请选择图片", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}
