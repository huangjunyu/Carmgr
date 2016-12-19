package com.yiwucheguanjia.carmgr.progress.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.PolygonOptions;
import com.amap.api.services.cloud.CloudItem;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.progress.CloudOverlay;
import com.yiwucheguanjia.carmgr.utils.AMapUtil;
import com.yiwucheguanjia.carmgr.utils.ToastUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * AMapV2地图中介绍自定义定位小蓝点
 */
public class NearbyFragment extends Fragment implements LocationSource,
        AMapLocationListener,AMap.OnMarkerClickListener,View.OnClickListener,CloudSearch.OnCloudSearchListener {
    private MarkerOptions markerOption;
    private LatLng latlng = new LatLng(39.91746, 116.396481);
    private Marker marker;
    CloudSearch cloudSearch;
    private CloudSearch.Query mQuery;
    private AMap aMap;
    private MapView mapView;
    private CloudOverlay mPoiCloudOverlay;
    private String mTableID = "您的tableid";
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private RadioGroup mGPSModeGroup;
    private List<CloudItem> mCloudItems;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private View view;
    private LatLonPoint mCenterPoint = new LatLonPoint(39.942753, 116.428650); // 周边搜索中心点
    private LatLonPoint mPoint1 = new LatLonPoint(39.941711, 116.382248);
    private LatLonPoint mPoint2 = new LatLonPoint(39.884882, 116.359566);
    private LatLonPoint mPoint3 = new LatLonPoint(39.878120, 116.437630);
    private LatLonPoint mPoint4 = new LatLonPoint(39.941711, 116.382248);
    private String mLocalCityName = "东城区";
    private String mKeyWord = "公园"; // 搜索关键字
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.custommarker_activity,container,false);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
        searchByLocal();
        return view;
    }

    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            addMarkersToMap();// 往地图上添加marker
            setUpMap();
        }

    }

    public void searchByLocal() {
//        showProgressDialog("searchByLocal");
//        items.clear();
        CloudSearch.SearchBound bound = new CloudSearch.SearchBound(mLocalCityName);
        try {
            mQuery = new CloudSearch.Query(mTableID, mKeyWord, bound);
            cloudSearch.searchCloudAsyn(mQuery);
        } catch (AMapException e) {
            ToastUtil.show(getActivity(), e.getErrorMessage());
            e.printStackTrace();
        }

    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        cloudSearch = new CloudSearch(getActivity());
        cloudSearch.setOnCloudSearchListener(this);
        aMap.setOnMarkerClickListener(this);//绑定这个后才可以进行标记点击
        aMap.setLocationSource(this);// 设置定位监听
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        setupLocationStyle();
    }

    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.all_tab));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
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
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
        Log.e("location", "location");
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getActivity().getApplicationContext());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap() {
        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(latlng)
                .title("标题")
                .snippet("详细信息")
                .draggable(true);
        marker = aMap.addMarker(markerOption);
        marker.showInfoWindow();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.e("you had click marker","marker");
        if (aMap != null) {
//            jumpPoint(marker);
        }
        Toast.makeText(getActivity(), "您点击了Marker", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCloudSearched(CloudResult result, int rCode) {
//        dissmissProgressDialog();
        Log.e("oncloudsear","onCloudS");
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {
                if (result.getQuery().equals(mQuery)) {
                    mCloudItems = result.getClouds();

                    if (mCloudItems != null && mCloudItems.size() > 0) {
                        aMap.clear();
                        mPoiCloudOverlay = new CloudOverlay(aMap, mCloudItems);
                        mPoiCloudOverlay.removeFromMap();
                        mPoiCloudOverlay.addToMap();
                        // mPoiCloudOverlay.zoomToSpan();
                        for (CloudItem item : mCloudItems) {
//                            items.add(item);
//                            Log.d(TAG, "_id " + item.getID());
//                            Log.d(TAG, "_location "
//                                    + item.getLatLonPoint().toString());
//                            Log.d(TAG, "_name " + item.getTitle());
//                            Log.d(TAG, "_address " + item.getSnippet());
//                            Log.d(TAG, "_caretetime " + item.getCreatetime());
//                            Log.d(TAG, "_updatetime " + item.getUpdatetime());
//                            Log.d(TAG, "_distance " + item.getDistance());
                            Iterator iter = item.getCustomfield().entrySet()
                                    .iterator();
                            while (iter.hasNext()) {
                                Map.Entry entry = (Map.Entry) iter.next();
                                Object key = entry.getKey();
                                Object val = entry.getValue();
                                Log.d("iwaz", key + "   " + val);
                            }
                        }
                        if (mQuery.getBound().getShape()
                                .equals(CloudSearch.SearchBound.BOUND_SHAPE)) {// 圆形
                            aMap.addCircle(new CircleOptions()
                                    .center(new LatLng(mCenterPoint
                                            .getLatitude(), mCenterPoint
                                            .getLongitude())).radius(5000)
                                    .strokeColor(
                                            // Color.argb(50, 1, 1, 1)
                                            Color.RED)
                                    .fillColor(Color.argb(50, 1, 1, 1))
                                    .strokeWidth(5));

                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mCenterPoint.getLatitude(),
                                            mCenterPoint.getLongitude()), 12));

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
                            mPoiCloudOverlay.zoomToSpan();
                        }

                    } else {
                        ToastUtil.show(getActivity(), "对不起，没有搜索到相关数据！");
                    }
                }
            } else {
                ToastUtil.show(getActivity(), "对不起，没有搜索到相关数据！");
            }
        } else {
            ToastUtil.showerror(getActivity(), rCode);
        }
    }

    @Override
    public void onCloudItemDetailSearched(CloudItemDetail cloudItemDetail, int i) {

    }
}

