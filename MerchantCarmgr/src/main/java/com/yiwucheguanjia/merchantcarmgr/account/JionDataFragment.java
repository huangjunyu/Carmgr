package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.my.controller.ImagePickerAdapter;
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
public class JionDataFragment extends Fragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    private LinearLayout merchantDataView;
    private ImagePicker imagePicker;
    private int maxImgCount = 3;
    @BindView(R.id.jion_next_btn)
    Button nextBtn;
    @BindView(R.id.jion_img_rv)
    RecyclerView imgRv;
    @BindView(R.id.jion_intro_ed)
    EditText introEd;
    @BindView(R.id.jion_service_type)
    TextView serviceTypeTv;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private final static int RESULT_SELECTED = 0;
    private final static int RESULT_NOTHING_SELECT = 1;
    private final static int REQUEST_ZERO = 0;
    private final static int REQUEST_ONE = 1;
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private SharedPreferences sharedPreferences;
    private Boolean uploadImgState = false;
    private static final int CHECKBOX_REQUEST = 300;
    private static final int CHECKBOX_RESULT = 301;
    public String SHOP_IMG_PATH_RESPONSE = "";//所有图片路径
    private Boolean checked() {
        if (TextUtils.isEmpty(serviceTypeTv.getText().toString().trim())) {
            Toast.makeText(getActivity(), "请选择服务类型", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(introEd.getText().toString().trim())) {
            Toast.makeText(getActivity(), "商家简介不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!uploadImgState) {
            Toast.makeText(getActivity(), "请上传图片", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("CARMGR_MERCHANT", getActivity().MODE_PRIVATE);

    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
        imagePicker.setMultiMode(true);
    }

    private void initWidget() {
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(getActivity(), selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        imgRv.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        imgRv.setHasFixedSize(true);
        imgRv.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        merchantDataView = (LinearLayout) inflater.inflate(R.layout.activity_jion, container, false);
        ButterKnife.bind(this, merchantDataView);
        initImagePicker();
        initWidget();
        return merchantDataView;
    }

    @OnClick({R.id.jion_next_btn, R.id.join_upload_btn, R.id.jion_service_type})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.jion_next_btn:
                if (checked()) {
                    MerchantEnterFragmentActivity merchantEnterFragmentActivity = (MerchantEnterFragmentActivity) getActivity();
                    merchantEnterFragmentActivity.postData();
                }
                break;
            case R.id.join_upload_btn:
                formUpload();
                break;
            case R.id.jion_service_type:
                Intent serviceTypeIntent = new Intent(getActivity(), ServiceTypeActivity.class);
                startActivityForResult(serviceTypeIntent, CHECKBOX_REQUEST);
                break;
            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("nkw", requestCode + "r" + resultCode + "");
        if (requestCode == REQUEST_ZERO && resultCode == RESULT_SELECTED) {

        } else if (requestCode == REQUEST_ZERO && resultCode == RESULT_NOTHING_SELECT) {
            //没有选择服务类型
        } else if (resultCode == CHECKBOX_RESULT && requestCode == CHECKBOX_REQUEST) {
            if (data.getExtras().getString("serviceCheckBoxType") != null) {
                serviceTypeTv.setText(data.getExtras().getString("serviceCheckBoxType"));
            }
        }

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                selImageList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Log.e("imaget", selImageList.get(0).path);
//                selImageList.addAll(selImageList);
                adapter.setImages(selImageList);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                selImageList.clear();
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        }
    }

    public void formUpload() {
        sharedPreferences = getActivity().getSharedPreferences("CARMGR_MERCHANT", getActivity().MODE_PRIVATE);
        ArrayList<File> files = new ArrayList<>();
        if (selImageList != null && selImageList.size() > 0) {
            for (int i = 0; i < selImageList.size(); i++) {
                files.add(new File(selImageList.get(i).path));
            }
        } else {//如果没有图片，则不能上传
            Toast.makeText(getActivity(), "请选择图片", Toast.LENGTH_SHORT).show();
            return;
        }
        //拼接参数
        PostRequest okGo = OkGo.post(UrlString.APP_UPLOAD)//
                .tag(this);//
        okGo.params("username", sharedPreferences.getString("ACCOUNT", "null"))
                .params("type", "service_introduce_img")
                .params("token", sharedPreferences.getString("TOKEN", "null"))
                .params("version", UrlString.APP_VERSION)
                .params("file_count", files.size());
        for (int i = 0; i < files.size(); i++) {
            okGo.params("file" + (i + 1), files.get(i));
        }
        okGo.execute(
                new MyStringCallback(getActivity(), getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        uploadImgState = true;
                        Log.e("string", s);

                        try {
                            final JSONObject jsonObject = new JSONObject(s);
                            JSONObject storePaht;
                            int listSize;
                            if (TextUtils.equals(jsonObject.getString("opt_state"), "success")) {
                                listSize = jsonObject.getInt("list_size");
                                JSONArray fileStoreList = jsonObject.getJSONArray("file_store_list");
                                for (int i = 0; i < listSize; i++) {
                                    storePaht = (JSONObject) fileStoreList.get(i);
                                    SHOP_IMG_PATH_RESPONSE = SHOP_IMG_PATH_RESPONSE + storePaht.getString("store_path") + "^";
                                }
                                SHOP_IMG_PATH_RESPONSE = SHOP_IMG_PATH_RESPONSE.substring(0, SHOP_IMG_PATH_RESPONSE.length() - 1);
                                Log.e("imgpaths", SHOP_IMG_PATH_RESPONSE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
        );
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                Log.e("kwq", "jwnw");
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(getActivity(), ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }
}
