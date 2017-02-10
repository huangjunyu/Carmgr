package com.yiwucheguanjia.carmgr.progress.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolygonOptions;
import com.amap.api.maps2d.model.Text;
import com.amap.api.maps2d.model.TextOptions;
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
import com.yiwucheguanjia.carmgr.progress.controller.ServiceTypeOnMapAdapter;
import com.yiwucheguanjia.carmgr.progress.model.ServiceBean;
import com.yiwucheguanjia.carmgr.utils.AMapUtil;
import com.yiwucheguanjia.carmgr.utils.ClickImageView;
import com.yiwucheguanjia.carmgr.utils.Constants;
import com.yiwucheguanjia.carmgr.utils.RecyclerViewDivider;
import com.yiwucheguanjia.carmgr.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * AMapV1地图中简单介绍一些Marker的用法.
 */
public class NearbyFragment1 extends Fragment implements OnMarkerClickListener, LocationSource, AMapLocationListener, CloudSearch.OnCloudSearchListener,
        OnClickListener, AMap.OnCameraChangeListener {
    private View view;
    private MarkerOptions markerOption;
    private TextView markerText;
    private Button markerButton;// 获取屏幕内所有marker的button
    private RadioGroup radioOption;
    private AMap aMap;
    private MapView mapView;
    private Marker marker2;// 有跳动效果的marker对象
    private LatLng latlng = new LatLng(36.061, 103.834);
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
    private PageRecyclerView mRecyclerView = null;
    private PageRecyclerView.PageAdapter myAdapter = null;
    private LatLonPoint mCenterPoint; // 周边搜索中心点
    private LatLonPoint mPoint1 = new LatLonPoint(39.941711, 116.382248);
    private LatLonPoint mPoint2 = new LatLonPoint(39.884882, 116.359566);
    private LatLonPoint mPoint3 = new LatLonPoint(39.878120, 116.437630);
    private LatLonPoint mPoint4 = new LatLonPoint(39.941711, 116.382248);
    private double longitude;//经度
    private double latitude;//纬度
    Bitmap viewToBitmap = null;
    private String[] serviceType;//服务类型
    @BindView(R.id.nearby_ft_service_type)
    RecyclerView serviceTypeRv;
    private ServiceTypeOnMapAdapter serviceTypeOnMapAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceType = getResources().getStringArray(R.array.service_type);
        serviceTypeOnMapAdapter = new ServiceTypeOnMapAdapter(getActivity(), serviceType, handler);
        serviceBeanArrayList = new ArrayList<>();
        markerArrayList = new ArrayList<>();
        serviceTypeOnMapAdapter.setOnItemClickLitener(new ServiceTypeOnMapAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, TextView textView, int position) {
                Log.e("posss", position + textView.getText().toString());
                mKeyWord = textView.getText().toString();
                searchByLocal();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.custommarker_activity, container, false);
        ButterKnife.bind(this, view);
        serviceTypeRv.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getActivity(), R.color.gray_default)));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        serviceTypeRv.setLayoutManager(linearLayoutManager);
        serviceTypeRv.setAdapter(serviceTypeOnMapAdapter);
        mapView = (MapView) view.findViewById(R.id.map);
        AMapOptions aMapOptions = new AMapOptions();
//        aMapOptions.camera(CameraPosition.fromLatLngZoom(new LatLng(31.238068, 121.501654),10f));
        aMapOptions.camera(new CameraPosition(new LatLng(31.238068, 121.501654),10f,0,0));
        mapView = new MapView(getActivity(),aMapOptions);
        mapView.onCreate(savedInstanceState);
        init(view);
        mRecyclerView = (PageRecyclerView) view.findViewById(R.id.cusom_swipe_view);
        // 设置指示器
        mRecyclerView.setIndicator((PageIndicatorView) view.findViewById(R.id.indicator));
        // 设置行数和列数
        mRecyclerView.setPageSize(1, 1);
        // 设置页间距
        mRecyclerView.setPageMargin(30);
        return view;
    }

    /**
     * 初始化AMap对象
     */
    private void init(View view) {
        markerText = (TextView) view.findViewById(R.id.mark_listenter_text);
        radioOption = (RadioGroup) view.findViewById(R.id.custom_info_window_options);
        markerButton = (Button) view.findViewById(R.id.marker_button);
        markerButton.setOnClickListener(this);
        Button clearMap = (Button) view.findViewById(R.id.clearMap);
        clearMap.setOnClickListener(this);
        Button resetMap = (Button) view.findViewById(R.id.resetMap);
        resetMap.setOnClickListener(this);
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
            aMap.moveCamera(CameraUpdateFactory.zoomTo(20));
    }

    private void setUpMap() {
        cloudSearch = new CloudSearch(getActivity());
        cloudSearch.setOnCloudSearchListener(this);
//        aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
//        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器

//        addMarkersToMap();// 往地图上添加marker
//        searchByLocal();
    }

    public void searchByLocal() {
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
     * 在地图上添加marker
     */
    private void addMarkersToMap() {
        // 动画效果
        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
        giflist.add(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//        giflist.add(BitmapDescriptorFactory.fromResource())
        giflist.add(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED));
        giflist.add(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .position(Constants.ZHENGZHOU).title("郑州市").icons(giflist)
                .draggable(true).period(1));
//        drawMarkers();// 添加10个带有系统默认icon的marker
    }

    /**
     * 对marker标注点点击响应事件
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {
//        marker.setIcon();
        if (aMap != null) {
            for (int i = 0; i < markerArrayList.size(); i++) {
                if (marker.equals(markerArrayList.get(i))) {
                    markerText.setText("你点击的是" + marker.getTitle());
                    mRecyclerView.scrollToPosition(i);
                }
            }
        }
        return true;
    }

    public Bitmap setGeniusIcon(String url, final double latitud, final double longtud, final String title, final String snipet, final int value, final String key) {
    if (!TextUtils.isEmpty(url)){

        Glide.with(this).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                View view1 = getActivity().getLayoutInflater().inflate(R.layout.view_to_icon, null);
                ImageView vv;
                vv = (ImageView) view1.findViewById(R.id.vti_igm);
                vv.setImageBitmap(Bitmap.createScaledBitmap(resource,180,180,false));
                viewToBitmap = convertViewToBitmap(vv);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putDouble("latitud", latitud);
                bundle.putDouble("longtud", longtud);
                bundle.putString("title", title);
                bundle.putString("snipet", snipet);
                bundle.putInt("value", value);
                bundle.putString("key", key);
                bundle.putParcelable("bitmap", viewToBitmap);
                message.setData(bundle);
                message.what = 0;
                handler.sendMessage(message);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {//如果加载失败则从本地生成一张默认的图标
                super.onLoadFailed(e, errorDrawable);
                View view1 = getActivity().getLayoutInflater().inflate(R.layout.view_to_icon, null);
                ImageView vv;
                vv = (ImageView) view1.findViewById(R.id.vti_igm);
                vv.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.ic_launcher),180,180));
                viewToBitmap = convertViewToBitmap(vv);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putDouble("latitud", latitud);
                bundle.putDouble("longtud", longtud);
                bundle.putString("title", title);
                bundle.putString("snipet", snipet);
                bundle.putInt("value", value);
                bundle.putString("key", key);
                bundle.putParcelable("bitmap", viewToBitmap);
                message.setData(bundle);
                message.what = 0;
                handler.sendMessage(message);

            }
        });
    }else {
        View view1 = getActivity().getLayoutInflater().inflate(R.layout.view_to_icon, null);
        ImageView vv;
        vv = (ImageView) view1.findViewById(R.id.vti_igm);
        vv.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.after_sale),180,180));
        viewToBitmap = convertViewToBitmap(vv);
        ThumbnailUtils.extractThumbnail(viewToBitmap,60,60);
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putDouble("latitud", latitud);
        bundle.putDouble("longtud", longtud);
        bundle.putString("title", title);
        bundle.putString("snipet", snipet);
        bundle.putInt("value", value);
        bundle.putString("key", key);
        bundle.putParcelable("bitmap", viewToBitmap);
        message.setData(bundle);
        message.what = 0;
        handler.sendMessage(message);
    }

        return viewToBitmap;
    }

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 清空地图上所有已经标注的marker
             */
            case R.id.clearMap:
                if (aMap != null) {
                    aMap.clear();
                }
                break;
            /**
             * 重新标注所有的marker
             */
            case R.id.resetMap:
                if (aMap != null) {
                    aMap.clear();
                    addMarkersToMap();
                }
                break;
            // 获取屏幕所有marker
            case R.id.marker_button:
                searchByLocal();
//                if (aMap != null) {
//                    List<Marker> markers = aMap.getMapScreenMarkers();
//                    if (markers == null || markers.size() == 0) {
//                        ToastUtil.show(getActivity(), "当前屏幕内没有Marker");
//                        return;
//                    }
//                    String tile = "屏幕内有：";
//                    for (Marker marker : markers) {
//                        tile = tile + " " + marker.getTitle();
//
//                    }
//                    ToastUtil.show(getActivity(), tile);
//
//                }
                break;
            default:
                break;
        }
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
//                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                aMap.moveCamera(CameraUpdateFactory.zoomTo(20));
                Log.e("经纬度", aMapLocation.getLongitude() + "," + aMapLocation.getLatitude() + aMapLocation.getStreet());
                longitude = aMapLocation.getLongitude();
                latitude = aMapLocation.getLatitude();
                mCenterPoint = new LatLonPoint(longitude, latitude);
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
                    serviceBean = new ServiceBean();
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    int value = bundle.getInt("value", 0);
                    positionMap.put(bundle.getString("key", "key"), value);
                    markerOption = new MarkerOptions();
                    markerOption.position(new LatLng(bundle.getDouble("latitud"),bundle.getDouble("longtud")));
                    markerOption.draggable(true).period(1);
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap((Bitmap)bundle.getParcelable("bitmap")));
                    marker = aMap.addMarker(markerOption);
                    markerArrayList.add(bundle.getInt("value",-1),marker);
                    addMarkersToMap();
                    break;
                case 1://处理云返回的数据
                    Bundle bundle1 = new Bundle();
                    bundle = msg.getData();
                    ArrayList<CloudItem> cloudItemArrayList = new ArrayList<>();
                    cloudItemArrayList = bundle.getParcelableArrayList("mCloudItems");
                    setCloudDataRv(cloudItemArrayList);
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    };

    //    private ArrayList<ServiceBean> serviceBeanArrayList;
    private void setCloudDataRv(final ArrayList<CloudItem> cloudItemArrayList) {
        //创建两个数组，分别保存marker以及marker对应的信息
        serviceBeanArrayList.clear();
        markerArrayList.clear();
        for (int i = 0; i < cloudItemArrayList.size(); i++) {
            serviceBean = new ServiceBean();
            serviceBean.setServiceType(cloudItemArrayList.get(i).getTitle());
            serviceBean.setServiceTitle(cloudItemArrayList.get(i).getTitle());
            serviceBean.setServicePrice(cloudItemArrayList.get(i).getCustomfield().get("price"));
            serviceBeanArrayList.add(i, serviceBean);
            if (!cloudItemArrayList.get(i).getCloudImage().isEmpty()){
            setGeniusIcon(cloudItemArrayList.get(i).getCloudImage().get(0).getUrl(),
                    cloudItemArrayList.get(i).getLatLonPoint().getLatitude(),
                    cloudItemArrayList.get(i).getLatLonPoint().getLongitude(),
                    cloudItemArrayList.get(i).getTitle(),
                    cloudItemArrayList.get(i).getSnippet(),i,cloudItemArrayList.get(i).getID());

            }else {
                setGeniusIcon("",
                        cloudItemArrayList.get(i).getLatLonPoint().getLatitude(),
                        cloudItemArrayList.get(i).getLatLonPoint().getLongitude(),
                        cloudItemArrayList.get(i).getTitle(),
                        cloudItemArrayList.get(i).getSnippet(),i,cloudItemArrayList.get(i).getID());
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
//                Log.e("position", cloudItemArrayList.get(position).getTitle());
                ((MyHolder) holder).titleTv.setText(serviceBeanArrayList.get(position).getServiceTitle());
                ((MyHolder) holder).priceTv.setText(serviceBeanArrayList.get(position).getServicePrice() + "元/次");
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
//                        mPoiCloudOverlay.removeFromMap();
//                        mPoiCloudOverlay.addToMap();
                        // mPoiCloudOverlay.zoomToSpan();
                        markerArrayList.clear();
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("mCloudItems", (ArrayList<? extends Parcelable>) mCloudItems);
                        message.setData(bundle);
                        message.what = 1;
                        handler.sendMessage(message);

                        for (CloudItem item : mCloudItems) {
                            Iterator iter = item.getCustomfield().entrySet()
                                    .iterator();
                            while (iter.hasNext()) {
                                Map.Entry entry = (Map.Entry) iter.next();
                                Object key = entry.getKey();
                                Object val = entry.getValue();
                                Log.e("iwaz", key + "   " + val);
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
                            Log.e("circle", "circle");
                            mPoiCloudOverlay.zoomToSpan();//可以使地图移动到当前位置
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

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        Log.e("cameraPosition", "cameraP");
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        Log.e("cameraPosition", "cameraPFinish");
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView titleTv;
        private TextView priceTv;

        public MyHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.item_service_title);
            priceTv = (TextView) itemView.findViewById(R.id.item_service_price);
        }
    }
}
