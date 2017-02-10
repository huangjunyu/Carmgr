package com.yiwucheguanjia.carmgr.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.callback.MyStringCallback;
import com.yiwucheguanjia.carmgr.city.CityForCarActivity;
import com.yiwucheguanjia.carmgr.utils.SharedPreferencesUtil;
import com.yiwucheguanjia.carmgr.utils.UrlString;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 添加车辆信息
 */
public class AddCarActivity extends Activity implements View.OnClickListener {
    private RelativeLayout gobackRl;
    private RelativeLayout searchCityRl;
    private TextView cityTv;
    private EditText carNumberEd;
    private EditText engineNumberEd;
    private EditText frameNumberEd;
    private Button searchBtn;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView provinceShortTv;
    private ArrayList<String> textViewArrayList;
    private String[] provinceShortArray;
    private String cityName;
    private int changeCase;
    private String srString;
    private RelativeLayout carBrandRl;
    private TextView carBrand;
    private static final int CAR_REQUEST_CITY_CODE = 5000;
    private static final int CAR_RESULT_CITY_CODE = 5001;
    private static final int ADD_CAR_REQUEST = 6000;
    private static final int ADD_CAR_RESULT = 6001;
    private static final int ADD_CAR_BRAND_REQUEST = 5002;
    private static final int ADD_CAR_BRAND_RESULT = 5003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 50);
        setContentView(R.layout.activity_addcar);
        provinceShortArray = getResources().getStringArray(R.array.province_short);
        textViewArrayList = new ArrayList<>();
        initView();
        radioButton = (RadioButton) AddCarActivity.this.findViewById(radioGroup.getCheckedRadioButtonId());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) AddCarActivity.this.findViewById(group.getCheckedRadioButtonId());
            }
        });
        //将小写输入转换成大写
        carNumberEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            @Override
            public void afterTextChanged(Editable s) {
                // 将输入的小写字母实时转换成大写
                if (changeCase == 0) {
                    srString = s.toString().toUpperCase();
                    if(s.toString().equals("")){
                        changeCase = 0;
                    }else{
                        changeCase = 1;
                    }
                    s.clear();
                } else if (changeCase == 1) {
                    changeCase = 2;
                    s.append(srString);
                } else {
                    changeCase = 0;
                }
            }
        });
    }

    private void initView() {
        gobackRl = (RelativeLayout) findViewById(R.id.addcar_goback_rl);
        searchCityRl = (RelativeLayout) findViewById(R.id.addcar_search_city_rl);
        cityTv = (TextView) findViewById(R.id.addcar_city_txt);
        carNumberEd = (EditText) findViewById(R.id.addcar_car_number_Et);
        engineNumberEd = (EditText) findViewById(R.id.addcar_engine_number);
        frameNumberEd = (EditText) findViewById(R.id.addcar_frame_num_ed);
        searchBtn = (Button) findViewById(R.id.search_btn);
        radioGroup = (RadioGroup) findViewById(R.id.addcar_radiogroup);
        provinceShortTv = (TextView) findViewById(R.id.addcar_province_short);
        carBrandRl = (RelativeLayout) findViewById(R.id.addcar_type_Rl);
        carBrand = (TextView) findViewById(R.id.addcar_car_brand);
        carBrandRl.setOnClickListener(this);
        gobackRl.setOnClickListener(this);
        searchCityRl.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addcar_goback_rl:
                this.finish();
                break;
            case R.id.addcar_search_city_rl:
                Intent cityIntent = new Intent(AddCarActivity.this, CityForCarActivity.class);
                startActivityForResult(cityIntent, CAR_REQUEST_CITY_CODE);
                break;
            case R.id.search_btn:
                if (checkData()) {
                    try {
                        postData();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.addcar_type_Rl:
                Intent carBrandIntent = new Intent(AddCarActivity.this, CarBrandActivity.class);
                startActivityForResult(carBrandIntent, ADD_CAR_BRAND_REQUEST);
                break;
            default:
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAR_REQUEST_CITY_CODE && resultCode == CAR_RESULT_CITY_CODE) {
            cityName = data.getExtras().getString("carRequestCity", "");
            cityTv.setText(data.getExtras().getString("carRequestCity", ""));
            provinceShortTv.setText(getProvinceShort(data.getExtras().getInt("provinceNum", 0)));
        }else if (requestCode == ADD_CAR_BRAND_REQUEST && resultCode == ADD_CAR_BRAND_RESULT){
            Log.e("kwkwaa","nnoooo");
            carBrand.setText(data.getExtras().getString("carBrand",""));
        }
    }

    private Boolean checkData() {
        textViewArrayList.clear();
        textViewArrayList.add(carBrand.getText().toString().trim());
        textViewArrayList.add(cityTv.getText().toString().trim());
        textViewArrayList.add(carNumberEd.getText().toString().trim());
        textViewArrayList.add(engineNumberEd.getText().toString().trim());
        textViewArrayList.add(frameNumberEd.getText().toString().trim());
        textViewArrayList.add(radioButton.getText().toString().trim());

        for (int i = 0; i < textViewArrayList.size(); i++) {
            if (TextUtils.isEmpty(textViewArrayList.get(i))) {
                Toast.makeText(AddCarActivity.this,"请填写完整资料",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void postData() throws UnsupportedEncodingException {
        String s="林溦";
        String is =new String(s.getBytes("GBK"),"ISO-8859-1");

        OkGo.post(UrlString.APP_ADD_CAR_INFO)
                .params("username", SharedPreferencesUtil.getInstance(AddCarActivity.this).usernameSharedPreferences())
                .params("car_type", "")
                .params("city", new String(cityTv.getText().toString().trim().getBytes("UTF-8"),"ISO-8859-1"))
//                .params("city",new String("广州".getBytes("UTF-8"),"ISO-8859-1"))
                .params("vehicle_number", carNumberEd.getText().toString().trim())
                .params("engine_number", engineNumberEd.getText().toString().trim())
                .params("frame_number", frameNumberEd.getText().toString().trim())
                .params("vehicle_brand",new String(carBrand.getText().toString().trim().getBytes("UTF-8"),"ISO-8859-1"))
                .params("token",SharedPreferencesUtil.getInstance(AddCarActivity.this).tokenSharedPreference())
                .params("version", UrlString.APP_VERSION)
                .execute(new MyStringCallback(AddCarActivity.this, getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("sss",s + carBrand.getText().toString().trim());
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (TextUtils.equals(jsonObject.optString("opt_state"),"success")){
                                //携带数据返回
                                setResult(ADD_CAR_RESULT);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    private String getProvinceShort(int provinceNum){
//        provinceNum = 1;
        String provinceShort;
        switch (provinceNum){
            case 2://北京
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 3://安徽
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 4://福建
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 5:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 6:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 7:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 8:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 9:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 10:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 11:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 12:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 13:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 14:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 15:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 16:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 17:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 18:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 19:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 20:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 21:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 22:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 23:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 24:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 25:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 26:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 27:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 28:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 29:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 30:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 31:
                provinceShort = provinceShortArray[provinceNum];
                break;
            case 32:
                provinceShort = provinceShortArray[provinceNum];
                break;
            default:
                provinceShort = provinceShortArray[provinceNum];
                break;
        }
        if (provinceNum == 1){
            switch (cityName){
                case "北京":
                    provinceShort = "京";
                    break;
                case "上海":
                    provinceShort = "沪";
                    break;
                case "天津":
                    provinceShort = "津";
                    break;
                default:
                    break;
            }
        }
        Log.e("kkksa",provinceShort);
        return provinceShort;
    }
}
