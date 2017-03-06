package com.yiwucheguanjia.carmgr.nearby.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolygonOptions;
import com.amap.api.services.cloud.CloudItem;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.commercial.view.CommercialActivity;
import com.yiwucheguanjia.carmgr.overwrite.WrapcontentViewpager;
import com.yiwucheguanjia.carmgr.post_help.PostHelpActivity;
import com.yiwucheguanjia.carmgr.nearby.CloudOverlay;
import com.yiwucheguanjia.carmgr.nearby.PageRecyclerView;
import com.yiwucheguanjia.carmgr.nearby.controller.ServiceTypeOnMapAdapter;
import com.yiwucheguanjia.carmgr.nearby.model.MapItemFragmentBean;
import com.yiwucheguanjia.carmgr.nearby.model.MarkerItem;
import com.yiwucheguanjia.carmgr.nearby.model.MyFragmentStatPageAdapter;
import com.yiwucheguanjia.carmgr.nearby.model.ServiceBean;
import com.yiwucheguanjia.carmgr.utils.AMapUtil;
import com.yiwucheguanjia.carmgr.utils.CircularImage;
import com.yiwucheguanjia.carmgr.utils.RecyclerViewDivider;
import com.yiwucheguanjia.carmgr.utils.SharedPreferencesUtil;
import com.yiwucheguanjia.carmgr.utils.ToastUtil;
import com.yiwucheguanjia.carmgr.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NearbyFragment extends Fragment implements OnMarkerClickListener, LocationSource, AMapLocationListener, CloudSearch.OnCloudSearchListener, AMap.OnCameraChangeListener, ViewPager.OnPageChangeListener {
    private View view;
    private MarkerOptions markerOption;
    private TextView markerText;
    private Button markerButton;// 获取屏幕内所有marker的button
    private RadioGroup radioOption;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private CloudSearch.Query mQuery;
    private String mLocalCityName = "天河区";
    private String mTableID = "5851feb0afdf520ea8f630b7";
    private String mKeyWord = "上牌"; // 搜索关键字
    CloudSearch cloudSearch;
    private List<CloudItem> mCloudItems;
    private CloudOverlay mPoiCloudOverlay;
    private ArrayList<Marker> markerArrayList;
    private ServiceBean serviceBean;
    private Map<String, Integer> positionMap = new HashMap<>();
    private Marker marker;
    private ArrayList<ServiceBean> serviceBeanArrayList;
    private ArrayList<MapItemFragmentBean> fragmentArrayList;
    private ArrayList<MarkerItem> smallBpItemArrayList;
    private ArrayList<MarkerItem> largeBpItemArrayList;
    private PageRecyclerView mRecyclerView = null;
    private PageRecyclerView.PageAdapter myAdapter = null;
    private LatLonPoint mCenterPoint; // 周边搜索中心点
    private LatLonPoint mPoint1 = new LatLonPoint(39.941711, 116.382248);
    private LatLonPoint mPoint2 = new LatLonPoint(39.884882, 116.359566);
    private LatLonPoint mPoint3 = new LatLonPoint(39.878120, 116.437630);
    private LatLonPoint mPoint4 = new LatLonPoint(39.941711, 116.382248);
    private double longitude;//经度
    private double latitude;//纬度
    private String[] serviceType;//服务类型
    private Boolean isClickMarker = false;
    @BindView(R.id.nearby_ft_service_type)
    RecyclerView serviceTypeRv;
    @BindView(R.id.map)
    MapView mapView;
    AMap aMap;
    private ServiceTypeOnMapAdapter serviceTypeOnMapAdapter;
    private Marker mlastMarker;
    @BindView(R.id.fragment_nearby_vp)
    WrapcontentViewpager viewPager;
    @BindView(R.id.post_help_post1)
    ImageView helpImg;
    @BindView(R.id.post_help_post2)
    ImageView merchantImg;
    MyFragmentStatPageAdapter myFragmentStatPageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceType = getResources().getStringArray(R.array.service_type);
        serviceTypeOnMapAdapter = new ServiceTypeOnMapAdapter(getActivity(), serviceType, handler);
        serviceBeanArrayList = new ArrayList<>();
        fragmentArrayList = new ArrayList<>();
        markerArrayList = new ArrayList<>();
        smallBpItemArrayList = new ArrayList<>();
        largeBpItemArrayList = new ArrayList<>();
        serviceTypeOnMapAdapter.setOnItemClickLitener(new ServiceTypeOnMapAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, TextView textView, int position) {
                mKeyWord = textView.getText().toString();
                viewPager.setAdapter(myFragmentStatPageAdapter);
                searchByLocal();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nearby, container, false);
        ButterKnife.bind(this, view);
        serviceTypeRv.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getActivity(), R.color.gray_default)));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        serviceTypeRv.setLayoutManager(linearLayoutManager);
        serviceTypeRv.setAdapter(serviceTypeOnMapAdapter);
        mapView.onCreate(savedInstanceState);
        init(view);
        return view;
    }

    /**
     * 初始化AMap对象
     */
    private void init(View view) {
        viewPager.addOnPageChangeListener(this);
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(SharedPreferencesUtil.getInstance(getActivity()).latitudeSharedPreferences(),
                SharedPreferencesUtil.getInstance(getActivity()).longitudeSharedPreferences())));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(20));
    }

    private void setUpMap() {
        cloudSearch = new CloudSearch(getActivity());
        cloudSearch.setOnCloudSearchListener(this);
//        aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
//        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setLocationSource(this);// 设置定位监听
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器
    }

    public void searchByLocal() {
        // 设置中心点及检索范围
        CloudSearch.SearchBound bound = new CloudSearch.SearchBound(new LatLonPoint(
                mCenterPoint.getLatitude(), mCenterPoint.getLongitude()), 2000);
        try {
            mQuery = new CloudSearch.Query(mTableID, mKeyWord, bound);
            cloudSearch.searchCloudAsyn(mQuery);
        } catch (AMapException e) {
            ToastUtil.show(getActivity(), e.getErrorMessage());
            e.printStackTrace();
        }

    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
        if (!smallBpItemArrayList.isEmpty()) for (int i = 0; i < smallBpItemArrayList.size(); i++) {
            smallBpItemArrayList.get(i).getMarkerBitmap().recycle();
            smallBpItemArrayList.get(i).setMarkerBitmap(null);
        }
    }

    /**
     * 对marker标注点点击响应事件
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (aMap != null) {
            for (int i = 0; i < markerArrayList.size(); i++) {
                if (marker.equals(markerArrayList.get(i))) {
                    isClickMarker = true;
                    viewPager.setCurrentItem(i, false);//这里可以加入检索id来保证marker与item的数据绝对一致
                    if (mlastMarker == null) {
                        mlastMarker = marker;
                        Log.e("mlastM", mlastMarker.getId() + "");
                    } else {
                        Boolean isVirtualMarker = false;
                        // 将上一个marker恢复大小
                        for (int j = 0; j < smallBpItemArrayList.size(); j++) {
                            if (TextUtils.equals(mlastMarker.getId(), smallBpItemArrayList.get(j).getMarkerId())) {
                                mlastMarker.setIcon(BitmapDescriptorFactory.fromBitmap(smallBpItemArrayList.get(j).getMarkerBitmap()));
                                Log.e("id", mlastMarker.getId());
                                isVirtualMarker = true;
                            }
                        }
                        if (isVirtualMarker == true) {
                            mlastMarker = null;
                            mlastMarker = marker;//记录当前marker，点击下一个marker时，将上一个marker恢复状态

                        } else {
                            mlastMarker.destroy();
                            mlastMarker = marker;
                        }
                    }
                    this.marker = marker;
                    for (int k = 0; k < largeBpItemArrayList.size(); k++) {
                        if (TextUtils.equals(this.marker.getId(), largeBpItemArrayList.get(k).getMarkerId())) {
                            this.marker.setIcon(BitmapDescriptorFactory.fromBitmap(largeBpItemArrayList.get(i).getMarkerBitmap()));
                        }
                    }
                }
            }
        }
        return true;
    }

    @OnClick({R.id.post_help_post1, R.id.post_help_post2})
    void click(View view) {
        switch (view.getId()) {
            case R.id.post_help_post1:
                Intent intent = new Intent(getActivity(), PostHelpActivity.class);
                startActivity(intent);
                break;
            case R.id.post_help_post2:
                Intent merchantIntent = new Intent(getActivity(), CommercialActivity.class);
                startActivity(merchantIntent);
            default:
                break;
        }
    }

    protected void setGeniusIcon1(final CloudItem cloudItem) {
        if (!cloudItem.getCloudImage().isEmpty()) {
            Glide.with(this).load(cloudItem.getCloudImage().get(0).getUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    View smallView = getActivity().getLayoutInflater().inflate(R.layout.view_to_icon, null);
                    View largeView = getActivity().getLayoutInflater().inflate(R.layout.view_to_icon_large, null);
                    CircularImage circularLargeImg;
                    CircularImage circularSmallImg;
                    circularSmallImg = (CircularImage) smallView.findViewById(R.id.view_to_icon_img);
                    circularLargeImg = (CircularImage) largeView.findViewById(R.id.view_to_larger_img);
                    circularSmallImg.setImageBitmap(ThumbnailUtils.extractThumbnail(resource, resource.getWidth(), resource.getHeight()));
                    circularLargeImg.setImageBitmap(ThumbnailUtils.extractThumbnail(resource, resource.getWidth(), resource.getHeight()));
                    resource.recycle();//释放资源
                    markerOption = new MarkerOptions();
                    markerOption.position(new LatLng(cloudItem.getLatLonPoint().getLatitude(), cloudItem.getLatLonPoint().getLongitude()));
                    markerOption.draggable(true).period(1);
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(smallView, 50)));
                    marker = aMap.addMarker(markerOption);
                    Log.e("maker", marker.getId());
                    ServiceBean serviceBean = new ServiceBean();
                    serviceBean.setServiceTitle(cloudItem.getTitle());
                    serviceBeanArrayList.add(serviceBean);
                    MapItemFragmentBean mapItemFragmentBean = new MapItemFragmentBean();
                    mapItemFragmentBean.setServiceTitle(cloudItem.getTitle());
                    mapItemFragmentBean.setId(marker.getId());
                    mapItemFragmentBean.setServicePrice(cloudItem.getCustomfield().get("price"));
                    mapItemFragmentBean.setContent(cloudItem.getSnippet());
                    mapItemFragmentBean.setStar(3);//此数据从易务后台获取，与地图商无关
                    fragmentArrayList.add(mapItemFragmentBean);
                    if (myFragmentStatPageAdapter != null) {
                        myFragmentStatPageAdapter.notifyDataSetChanged();
                    }
                    markerArrayList.add(marker);
                    MarkerItem markerItem = new MarkerItem();
                    markerItem.setMarkerBitmap(convertViewToBitmap(smallView, 50));
                    markerItem.setMarkerId(marker.getId());
                    smallBpItemArrayList.add(markerItem);
                    MarkerItem largeMarkerItem = new MarkerItem();
                    largeMarkerItem.setMarkerBitmap(convertViewToBitmap(largeView, 70));
                    largeMarkerItem.setMarkerId(marker.getId());
                    largeBpItemArrayList.add(largeMarkerItem);
                }
            });
        } else {
            View smallView = getActivity().getLayoutInflater().inflate(R.layout.view_to_icon, null);
            View largeView = getActivity().getLayoutInflater().inflate(R.layout.view_to_icon_large, null);
            CircularImage circularLargeImage;
            CircularImage circularSmallImg;
            circularSmallImg = (CircularImage) smallView.findViewById(R.id.view_to_icon_img);
            circularLargeImage = (CircularImage) largeView.findViewById(R.id.view_to_larger_img);
            circularLargeImage.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.yangmin), 100, 100));
            circularSmallImg.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(getActivity().getResources(),
                    R.mipmap.ic_launcher), Tools.getInstance().dipTopx(getActivity(), 60), Tools.getInstance().dipTopx(getActivity(), 60)));
            markerOption = new MarkerOptions();
            markerOption.position(new LatLng(cloudItem.getLatLonPoint().getLatitude(), cloudItem.getLatLonPoint().getLongitude()));
            markerOption.draggable(true).period(1);
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(smallView, 50)));
            marker = aMap.addMarker(markerOption);
            ServiceBean serviceBean = new ServiceBean();
            serviceBean.setServiceTitle(cloudItem.getTitle());
            serviceBeanArrayList.add(serviceBean);
            MapItemFragmentBean mapItemFragmentBean = new MapItemFragmentBean();
            mapItemFragmentBean.setServicePrice(cloudItem.getCustomfield().get("price"));
            mapItemFragmentBean.setServiceTitle(cloudItem.getTitle());
            mapItemFragmentBean.setContent(cloudItem.getSnippet());
            mapItemFragmentBean.setStar(4);
            fragmentArrayList.add(mapItemFragmentBean);
            if (myFragmentStatPageAdapter != null) {
                myFragmentStatPageAdapter.notifyDataSetChanged();
            }

            markerArrayList.add(marker);
            MarkerItem markerItem = new MarkerItem();
            markerItem.setMarkerBitmap(convertViewToBitmap(smallView, 50));
            markerItem.setMarkerId(marker.getId());
            smallBpItemArrayList.add(markerItem);
            MarkerItem largeMarkerItem = new MarkerItem();
            largeMarkerItem.setMarkerBitmap(convertViewToBitmap(largeView, 70));
            largeMarkerItem.setMarkerId(marker.getId());
            largeBpItemArrayList.add(largeMarkerItem);
        }
    }

    public Bitmap convertViewToBitmap(View view, int mulriple) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth() / mulriple, view.getMeasuredHeight() / mulriple);
        if (Tools.getInstance().dipTopx(getActivity(), mulriple) > view.getMeasuredWidth()) {
            view.layout(0, 0, view.getMeasuredHeight(), view.getMeasuredHeight());
        } else {

            view.layout(0, 0, Tools.getInstance().dipTopx(getActivity(), mulriple), Tools.getInstance().dipTopx(getActivity(), mulriple));
        }
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getActivity().getApplicationContext());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setOnceLocation(true);
            mLocationOption.setMockEnable(true);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                aMap.moveCamera(CameraUpdateFactory.zoomTo(20));
                SharedPreferences p = getActivity().getSharedPreferences("CARMGR", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = p.edit();
                edit.putFloat("longitude", (float) aMapLocation.getLongitude());
                edit.putFloat("latitude", (float) aMapLocation.getLatitude());
                edit.commit();
                longitude = aMapLocation.getLongitude();
                latitude = aMapLocation.getLatitude();
                mCenterPoint = new LatLonPoint(latitude, longitude);
                mLocalCityName = aMapLocation.getStreet();
                searchByLocal();
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Bundle bundle = msg.getData();
                    ArrayList<CloudItem> cloudItemArrayList = cloudItemArrayList = bundle.getParcelableArrayList("mCloudItems");
                    setCloudDataRv(cloudItemArrayList);
                    break;
                case 1://处理云返回的数据
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    };

    private void setCloudDataRv(ArrayList<CloudItem> cloudItemArrayList) {
        //创建两个数组，分别保存marker以及marker对应的信息
        serviceBeanArrayList.clear();
        fragmentArrayList.clear();
        markerArrayList.clear();
        smallBpItemArrayList.clear();
        largeBpItemArrayList.clear();
        for (int i = 0; i < cloudItemArrayList.size(); i++) {
            setGeniusIcon1(cloudItemArrayList.get(i));
        }
        myFragmentStatPageAdapter = new MyFragmentStatPageAdapter(getActivity().getSupportFragmentManager(), fragmentArrayList);
        viewPager.setAdapter(myFragmentStatPageAdapter);
    }

    @Override
    public void onCloudSearched(CloudResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {
                if (result.getQuery().equals(mQuery)) {
                    mCloudItems = result.getClouds();
                    if (mCloudItems != null && mCloudItems.size() > 0) {
                        aMap.clear();

                        mPoiCloudOverlay = new CloudOverlay(aMap, mCloudItems);
                        markerArrayList.clear();
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("mCloudItems", (ArrayList<? extends Parcelable>) mCloudItems);
                        message.setData(bundle);
                        message.what = 0;
                        handler.sendMessage(message);
                        if (mQuery.getBound().getShape()
                                .equals(CloudSearch.SearchBound.BOUND_SHAPE)) {// 圆形
                            mPoiCloudOverlay.zoomToSpan();//可以使地图移动到当前位置

                        } else if (mQuery.getBound().getShape()
                                .equals(CloudSearch.SearchBound.POLYGON_SHAPE)) {
                            aMap.addPolygon(new PolygonOptions()
                                    .add(AMapUtil.convertToLatLng(mPoint1))
                                    .add(AMapUtil.convertToLatLng(mPoint2))
                                    .add(AMapUtil.convertToLatLng(mPoint3))
                                    .add(AMapUtil.convertToLatLng(mPoint4))
                                    .fillColor(Color.argb(50, 1, 1, 1))
                                    .strokeColor(Color.RED).strokeWidth(1));
                            LatLngBounds bounds = new LatLngBounds.Builder()
                                    .include(AMapUtil.convertToLatLng(mPoint1))
                                    .include(AMapUtil.convertToLatLng(mPoint2))
                                    .include(AMapUtil.convertToLatLng(mPoint3))
                                    .build();
                            aMap.moveCamera(CameraUpdateFactory
                                    .newLatLngBounds(bounds, 50));
                        } else if ((mQuery.getBound().getShape()
                                .equals(CloudSearch.SearchBound.LOCAL_SHAPE))) {
                            mPoiCloudOverlay.zoomToSpan();//可以使地图移动到当前位置
                        }

                    } else {

                        ToastUtil.show(getActivity(), "没有搜索到相关数据！");
                    }
                }
            } else {
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(SharedPreferencesUtil.getInstance(getActivity()).latitudeSharedPreferences(),
                        SharedPreferencesUtil.getInstance(getActivity()).longitudeSharedPreferences())));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(20));
                ToastUtil.show(getActivity(), "没有搜索到相关数据！");
            }
        } else {
            ToastUtil.showerror(getActivity(), rCode);
        }
    }

    @Override
    public void onCloudItemDetailSearched(CloudItemDetail cloudItemDetail, int i) {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        Log.e("cameraPosition", "cameraP");
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        Log.e("cameraPosition", "cameraPFinish");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.e("position", position + "");
    }

    @Override
    public void onPageSelected(int position) {
        Log.e("marker", "marker");
        if (!isClickMarker) {
            if (mlastMarker == null) {
                mlastMarker = markerArrayList.get(position);
            } else {
                Boolean isArrayMarker = false;
                // 将上一个marker恢复大小
                for (int j = 0; j < smallBpItemArrayList.size(); j++) {
                    if (TextUtils.equals(mlastMarker.getId(), smallBpItemArrayList.get(j).getMarkerId())) {
                        mlastMarker.setIcon(BitmapDescriptorFactory.fromBitmap(smallBpItemArrayList.get(j).getMarkerBitmap()));
//                    mlastMarker.setIcon(BitmapDescriptorFactory.fromBitmap(smallBpItemArrayList.get(j).getMarkerBitmap()));
                        isArrayMarker = true;//如果是数组里面的marker，则标记下来
                        break;
                    }
                }
                if (!isArrayMarker) {
                    mlastMarker.destroy();
                }
            }
            markerOption = new MarkerOptions();
            markerOption.position(markerArrayList.get(position).getPosition());
            markerOption.draggable(true).period(1);
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(largeBpItemArrayList.get(position).getMarkerBitmap()));
            mlastMarker = aMap.addMarker(markerOption);

        }
        isClickMarker = false;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.e("state", state + "");
    }
}
