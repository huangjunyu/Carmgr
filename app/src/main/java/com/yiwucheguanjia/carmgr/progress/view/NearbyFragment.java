package com.yiwucheguanjia.carmgr.progress.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.progress.CloudOverlay;
import com.yiwucheguanjia.carmgr.progress.PageIndicatorView;
import com.yiwucheguanjia.carmgr.progress.PageRecyclerView;
import com.yiwucheguanjia.carmgr.progress.model.ServiceBean;
import com.yiwucheguanjia.carmgr.utils.AMapUtil;
import com.yiwucheguanjia.carmgr.utils.Constants;
import com.yiwucheguanjia.carmgr.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * AMapV2地图中介绍自定义定位小蓝点
 */
public class NearbyFragment extends Fragment implements LocationSource,
        AMapLocationListener, AMap.OnMarkerClickListener, View.OnClickListener, CloudSearch.OnCloudSearchListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter {
    private MarkerOptions markerOption;
    private LatLng latlng = new LatLng(39.91746, 116.396481);
    private Marker marker;
    private Marker markerDefault;
    private ArrayList<Marker> markerArrayList;
    CloudSearch cloudSearch;
    private CloudSearch.Query mQuery;
    private AMap aMap;
    private MapView mapView;
    private CloudOverlay mPoiCloudOverlay;
    private String mTableID = "5851feb0afdf520ea8f630b7";
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private RadioGroup mGPSModeGroup;
    private List<CloudItem> mCloudItems;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private View view;
    private LatLonPoint mCenterPoint = new LatLonPoint(23.121066, 113.390571); // 周边搜索中心点
    private LatLonPoint mPoint1 = new LatLonPoint(39.941711, 116.382248);
    private LatLonPoint mPoint2 = new LatLonPoint(39.884882, 116.359566);
    private LatLonPoint mPoint3 = new LatLonPoint(39.878120, 116.437630);
    private LatLonPoint mPoint4 = new LatLonPoint(39.941711, 116.382248);
    private String mLocalCityName = "天河区";
    private String mKeyWord = "上牌"; // 搜索关键字
    private RadioGroup radioOption;
    Bitmap bitmap1 = null;
    private Map<String, Integer> positionMap = new HashMap<>();

    private PageRecyclerView mRecyclerView = null;
    private Button button;
    private List<String> dataList = null;
    private ArrayList<ServiceBean> serviceBeanArrayList;
    private ServiceBean serviceBean;
    private PageRecyclerView.PageAdapter myAdapter = null;
    private Button markerbtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceBeanArrayList = new ArrayList<>();
        markerArrayList = new ArrayList<>();
//        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.custommarker_activity, container, false);
        mapView = (MapView) view.findViewById(R.id.map);
        radioOption = (RadioGroup) view.findViewById(R.id.custom_info_window_options);

        markerbtn = (Button) view.findViewById(R.id.marker_button);
        markerbtn.setOnClickListener(this);

        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
        mRecyclerView = (PageRecyclerView) view.findViewById(R.id.cusom_swipe_view);
        // 设置指示器
        mRecyclerView.setIndicator((PageIndicatorView) view.findViewById(R.id.indicator));
        // 设置行数和列数
        mRecyclerView.setPageSize(1, 1);
        // 设置页间距
        mRecyclerView.setPageMargin(30);

        return view;
    }


    private void initData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 47; i++) {
            dataList.add(String.valueOf(i));
        }
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
        aMap.setOnInfoWindowClickListener(this);
        aMap.setInfoWindowAdapter(this);
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

    public void searchByBound() {
//        showProgressDialog("searchByBound");
//        items.clear();
        CloudSearch.SearchBound bound = new CloudSearch.SearchBound(new LatLonPoint(
                mCenterPoint.getLatitude(), mCenterPoint.getLongitude()), 4000);
        try {
            mQuery = new CloudSearch.Query(mTableID, mKeyWord, bound);
            mQuery.setPageSize(10);
//            CloudSearch.Sortingrules sorting = new CloudSearch.Sortingrules(
//                    "_id", false);
//            mQuery.setSortingrules(sorting);
            cloudSearch.searchCloudAsyn(mQuery);// 异步搜索
        } catch (AMapException e) {
//            ToastUtil.show(this, e.getErrorMessage());
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
        markerDefault = aMap.addMarker(markerOption);
        markerDefault.showInfoWindow();
    }

    /**
     * marker点击时跳动一下
     */
    public void jumpPoint(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        Point startPoint = proj.toScreenLocation(Constants.XIAN);
        startPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * Constants.XIAN.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * Constants.XIAN.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                aMap.invalidate();// 刷新地图
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.e("you had click marker", "marker");
        if (aMap != null) {
            for (int i = 0; i < markerArrayList.size(); i++) {
                if (marker.equals(markerArrayList.get(i))) {
                    Toast.makeText(getActivity(), "您点击了" + marker.getTitle() + marker.getId(), Toast.LENGTH_LONG).show();

                }
            }
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.marker_button:
//                searchByBound();
                searchByLocal();
                break;
            default:
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            serviceBean = new ServiceBean();
            Bundle bundle = new Bundle();
            bundle = msg.getData();
            Double a = bundle.getDouble("latitud");
            Double b = bundle.getDouble("longtud");
            int value = bundle.getInt("value", 0);
            positionMap.put(bundle.getString("key", "key"), value);
            serviceBean.setHeaderUrl("url");
            serviceBean.setServiceType(bundle.getString("title"));
            serviceBean.setServiceTitle(bundle.getString("title"));
            serviceBean.setServicePrice(bundle.getString("title"));
            serviceBeanArrayList.add(serviceBean);
            markerOption = new MarkerOptions();
//            markerOption.getTitle();
//            marker.getTitle();
            markerOption.title(bundle.getString("title"));
            LatLng latLng = new LatLng(a, b);
            markerOption.position(latLng);
            markerOption.draggable(true);
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap1));
            marker = aMap.addMarker(markerOption);
            markerArrayList.add(marker);
        }
    };

    @Override
    public void onCloudSearched(CloudResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {
                if (result.getQuery().equals(mQuery)) {
                    mCloudItems = result.getClouds();
                    Log.e("resultt", mCloudItems.get(0).getTitle() + mCloudItems.size() + mCloudItems.get(1).getCloudImage());
                    if (mCloudItems != null && mCloudItems.size() > 0) {
                        aMap.clear();
                        mPoiCloudOverlay = new CloudOverlay(aMap, mCloudItems);
                        mPoiCloudOverlay.removeFromMap();
                        mPoiCloudOverlay.addToMap();
                        // mPoiCloudOverlay.zoomToSpan();

                        for (int i = 0; i < mCloudItems.size(); i++) {
                            double latitude = mCloudItems.get(i).getLatLonPoint().getLatitude();
                            double longlng = mCloudItems.get(i).getLatLonPoint().getLongitude();

                            Log.e("a", latitude + "");
                            Log.e("b", longlng + "");
                            String title = mCloudItems.get(i).getTitle();
                            String snipet = mCloudItems.get(i).getSnippet();

                            if (mCloudItems.get(i).getCloudImage().size() > 0) {
                                //传入一个键与item对应
                                setGeniusIcon(mCloudItems.get(i).getCloudImage().get(0).getUrl(), latitude, longlng, title, snipet, i, mCloudItems.get(i).getID());
                            } else {
                                serviceBean = new ServiceBean();
                                serviceBean.setHeaderUrl("url");
                                serviceBean.setServicePrice(mCloudItems.get(i).getTitle());
                                serviceBean.setServiceTitle(mCloudItems.get(i).getTitle());
                                serviceBean.setServiceType(mCloudItems.get(i).getTitle());
                                positionMap.put(mCloudItems.get(i).getID(), i);
                                LatLng latLng = new LatLng(latitude, longlng);
                                markerOption = new MarkerOptions();
                                markerOption.position(latLng);
                                markerOption.title(mCloudItems.get(i).getTitle());
                                markerOption.snippet(mCloudItems.get(i).getSnippet());
                                markerOption.draggable(true);
                                markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.after_sale));
                                marker = aMap.addMarker(markerOption);
                                markerArrayList.add(marker);
                                serviceBeanArrayList.add(serviceBean);
                            }
                        }
                        // 设置数据
                        mRecyclerView.setAdapter(myAdapter = mRecyclerView.new PageAdapter(serviceBeanArrayList, new PageRecyclerView.CallBack() {
                            @Override
                            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_service, parent, false);
                                return new MyHolder(view);
                            }

                            @Override
                            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                                Log.e("ttttitle", serviceBeanArrayList.get(position).getServiceTitle());
                                ((MyHolder) holder).tv.setText(serviceBeanArrayList.get(position).getServiceTitle());
                            }

                            @Override
                            public void onItemClickListener(View view, int position) {
                                Toast.makeText(getActivity(), "点击："
                                        + serviceBeanArrayList.get(position), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onItemLongClickListener(View view, int position) {
                                Toast.makeText(getActivity(), "删除："
                                        + serviceBeanArrayList.get(position), Toast.LENGTH_SHORT).show();
                                myAdapter.remove(position);
                            }
                        }));
                        for (CloudItem item : mCloudItems) {
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


    public Bitmap setGeniusIcon(String url, final double latitud, final double longtud, final String title, final String snipet, final int value, final String key) {

        Glide.with(this).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                View view1 = getActivity().getLayoutInflater().inflate(R.layout.view_to_icon, null);
                ImageView vv;
                vv = (ImageView) view1.findViewById(R.id.vti_igm);
                vv.setImageBitmap(resource);
                bitmap1 = resource;
                bitmap1 = convertViewToBitmap(vv);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putDouble("latitud", latitud);
                bundle.putDouble("longtud", longtud);
                bundle.putString("title", title);
                bundle.putString("snipet", snipet);
                bundle.putInt("value", value);
                bundle.putString("key", key);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });

        return bitmap1;
    }

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    @Override
    public void onCloudItemDetailSearched(CloudItemDetail cloudItemDetail, int i) {
        Log.e("mydata", "mydata");
    }

    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
        if (radioOption.getCheckedRadioButtonId() == R.id.custom_info_contents) {
            ((ImageView) view.findViewById(R.id.badge))
                    .setImageResource(R.mipmap.pull_up_black);
        } else if (radioOption.getCheckedRadioButtonId() == R.id.custom_info_window) {
            ImageView imageView = (ImageView) view.findViewById(R.id.badge);
            imageView.setImageResource(R.mipmap.pull_up_black);
        }
        String title = marker.getTitle();
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(Color.RED), 0,
                    titleText.length(), 0);
            titleUi.setTextSize(15);
            titleUi.setText(titleText);

        } else {
            titleUi.setText("");
        }
        String snippet = marker.getSnippet();
        TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
        if (snippet != null) {
            SpannableString snippetText = new SpannableString(snippet);
            snippetText.setSpan(new ForegroundColorSpan(Color.GREEN), 0,
                    snippetText.length(), 0);
            snippetUi.setTextSize(20);
            snippetUi.setText(snippetText);
        } else {
            snippetUi.setText("");
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView tv = null;

        public MyHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_service_title);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
//        if (radioOption.getCheckedRadioButtonId() != R.id.custom_info_window) {
//            Log.e("infow", "infowindow");
//            return null;
//        }
        View infoWindow = getActivity().getLayoutInflater().inflate(
                R.layout.custom_info_window, null);

        render(marker, infoWindow);
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
//        if (radioOption.getCheckedRadioButtonId() != R.id.custom_info_contents) {
//            Log.e("infow223", "infowindow");
//            return null;
//        }
        View infoContent = getActivity().getLayoutInflater().inflate(
                R.layout.custom_info_contents, null);
        render(marker, infoContent);
        return infoContent;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        ToastUtil.show(getActivity(), "你点击了infoWindow窗口" + marker.getTitle());
    }
}

