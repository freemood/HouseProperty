package com.house.property.model.main.actvity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;
import com.house.property.R;
import com.house.property.adapter.WindowAdapter;
import com.house.property.adapter.WindowBlockAdapter;
import com.house.property.base.BaseFragmentActivity;
import com.house.property.base.Constants;
import com.house.property.manager.ActivityStackManager;
import com.house.property.model.main.entity.CommunityBriefBean;
import com.house.property.model.main.entity.PdSListBean;
import com.house.property.model.main.entity.TaskBean;
import com.house.property.model.main.entity.TaskListBean;
import com.house.property.model.main.iface.IPdSListView;
import com.house.property.model.main.iface.ITaskListView;
import com.house.property.model.main.presenter.PdSListPresenter;
import com.house.property.model.main.presenter.TaskListPresenter;
import com.house.property.model.task.activity.TaskAddPOIActivity;
import com.house.property.model.task.activity.TaskBolckDetailsInfoActivity;
import com.house.property.model.task.activity.TaskListActivity;
import com.house.property.model.task.entity.BuildDetailsVO;
import com.house.property.model.task.entity.DimPOIVO;
import com.house.property.model.task.entity.GisObjVO;
import com.house.property.model.task.entity.MessageEventVO;
import com.house.property.utils.SharedPreferencesUtil;
import com.house.property.utils.ToastUtils;
import com.house.property.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.house.property.base.Constants.TAKE_TYPE_DEALT;
import static com.house.property.base.Constants.TASK_NUMBER;
import static com.house.property.utils.TaskUtils.reTextNull;

public class MainActivity extends BaseFragmentActivity implements IPdSListView, ITaskListView, AMap.OnMapClickListener, AMap.OnCameraChangeListener {
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.page_cancle)
    TextView pageCancle;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;
    @BindView(R.id.dealt_info)
    TextView dealtInfo;
    private MessageEventVO commBean;
    private long clickTime = 0; // 第一次点击的时间
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private boolean isLocation = false;
    private Marker marker;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    /**
     * 地图中绘制多边形、圆形的边界颜色
     */
    public static final int STROKE_COLOR_MAP = Color.argb(180, 63, 145, 252);
    /**
     * 地图中绘制多边形、圆形的填充颜色
     */
    public static final int FILL_COLOR_MAP = Color.argb(163, 118, 212, 243);

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    private static final int PERMISSON_REQUESTCODE = 0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;
    /**
     * 添加多个market
     */
    private List<GisObjVO> marketList = new ArrayList<>();
    private List<Polygon> polygonList = new ArrayList<>();
    private List<Marker> markerList = new ArrayList<>();
    private WindowBlockAdapter mWindowBlockAdapter;
    private WindowAdapter mWindowAdapterArea;
    private int zoomNumber = 0;
    private String userName;
    private RLoadingDialog mLoadingDialog;
    private TaskListPresenter taskListPresenter = new TaskListPresenter(this, this);
    private PdSListPresenter pdSListPresenter = new PdSListPresenter(this, this);
    private List<TaskBean> taskList = new ArrayList<>();
    private TaskBean taskBean;
    private Marker buildMarker;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initBundleData() {
        presentersList.add(taskListPresenter);
        presentersList.add(pdSListPresenter);
    }

    @Override
    protected void initView() {
        registeredEventBus();
        mLoadingDialog = new RLoadingDialog(this, true);
        initMap();
        pageCancle.setVisibility(View.GONE);
        pageRightRl.setVisibility(View.VISIBLE);
        pageRightTv.setText(getResources().getString(R.string.house_sheshi));
        userName = (String) SharedPreferencesUtil.getData("name", "");
        initTaskList();

    }

    private void initTaskList() {
        taskListPresenter.getTaskList(userName, TAKE_TYPE_DEALT);
    }

    private void initMap() {
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
            // setUpMap();
        }

//        aMap.setOnMyLocationChangeListener(this);
        aMap.setOnMapClickListener(this);

        aMap.setOnCameraChangeListener(this);

        // 1.获取屏幕的宽高
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        buildMarker = aMap.addMarker(new MarkerOptions()

                // .title("好好学习")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true));
        //marker.setRotateAngle(90);// 设置marker旋转90度
        buildMarker.setPositionByPixels(width / 2, height / 2);
        buildMarker.showInfoWindow();// 设置默认显示一个infowinfow
        LatLng latLng = new LatLng(32.393159, 119.421003);
        aMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }


    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 设置默认定位按钮是否显示
        // aMap.getUiSettings().setMyLocationButtonEnabled(true);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // aMap.setMyLocationEnabled(true);
        //  setupLocationStyle();
    }

    /**
     * 设置自定义定位蓝点
     */
    private void setupLocationStyle() {
        // 自定义系统定位蓝点
//        myLocationStyle = new MyLocationStyle();
//        // 自定义定位蓝点图标
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
//                fromResource(R.mipmap.gps_point));
//        // 自定义精度范围的圆形边框颜色
//        myLocationStyle.strokeColor(STROKE_COLOR);
//        //自定义精度范围的圆形边框宽度
//        myLocationStyle.strokeWidth(5);
//        // 设置圆形的填充颜色
//        myLocationStyle.radiusFillColor(FILL_COLOR);
//        // 将自定义的 myLocationStyle 对象添加到地图上
//        // myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
//        aMap.setMyLocationStyle(myLocationStyle);
    }

    @Override
    protected void initData() {
        //drawPolygon();
    }

    private void drawPolygon() {
        if (null == taskList || 0 == taskList.size()) {
            return;
        }
        for (TaskBean taskBean : taskList) {
            // 声明 多边形参数对象
            PolygonOptions polygonOption = new PolygonOptions();
// 添加 多边形的每个顶点（顺序添加）
            if (null == taskBean.getLatLngs()) {
                return;
            }
            polygonOption.add(taskBean.getLatLngs());
            polygonOption.strokeWidth(15) // 多边形的边框
                    .strokeColor(STROKE_COLOR_MAP) // 边框颜色
                    .fillColor(FILL_COLOR_MAP);   // 多边形的填充色
            // 添加一个多边形
            Polygon mPolygon = aMap.addPolygon(polygonOption);
            polygonList.add(mPolygon);
//            taskBean.setPolygon(mPolygon);
//        mPolyonBean.setId("1");
//        mPolyonBean.setPolygon(mPolygon);
            // addMoreMarket();
        }

    }

    private void addMoreMarket() {

        if (marketList == null) {
            marketList = new ArrayList<>();
        }

        for (int i = 0; i < marketList.size(); i++) {
            Marker marker = aMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(marketList.get(i).getCenterY()),//设置纬度
                            Double.parseDouble(marketList.get(i).getCenterX())))//设置经度
                    .title(reTextNull(marketList.get(i).getBuildNo()))//设置标题
                    .snippet(reTextNull(marketList.get(i).getBuildName()))//设置内容
                    // .setFlat(true) // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                    .draggable(false) //设置Marker可拖动
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_selected)));
            markerList.add(marker);
        }
        mWindowBlockAdapter = new WindowBlockAdapter(this, marketList);
        //设置自定义弹窗
        aMap.setInfoWindowAdapter(mWindowBlockAdapter);
        //绑定信息窗点击事件
        aMap.setOnInfoWindowClickListener(mWindowBlockAdapter);
        aMap.setOnMarkerClickListener(mWindowBlockAdapter);
    }

    @OnClick({R.id.page_right_tv, R.id.dealt_info})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_right_tv:
                addBuild();
                break;
            case R.id.dealt_info:
                Intent intent = new Intent(this, TaskListActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void addBuild() {
        LatLng latLng = buildMarker.getPosition();
        TaskBean taskBean = getTaskBean(latLng);
        if (null == taskBean || TextUtils.isEmpty(taskBean.getId())) {
            ToastUtils.showToast(this, "请在小区区域内补录");
            return;
        }
        showDailog(latLng, taskBean);

    }

    private void buildInsert(LatLng latLng, TaskBean taskBean) {
        CommunityBriefBean community = taskBean.getCommunity();
        if (null == taskBean || TextUtils.isEmpty(community.getId())) {
            ToastUtils.showToast(this, "请先添加小区信息");
            return;
        }
        BuildDetailsVO buildDetailsVO = new BuildDetailsVO();
        GisObjVO gisObjVO = new GisObjVO();
        gisObjVO.setCenterX(String.valueOf(latLng.longitude));
        gisObjVO.setCenterY(String.valueOf(latLng.latitude));
        buildDetailsVO.setGisObjVO(gisObjVO);
        buildDetailsVO.setCommId(community.getId());
        buildDetailsVO.setCommName(community.getCommName());
        buildDetailsVO.setTaskId(taskBean.getId());
        EventBus.getDefault().postSticky(buildDetailsVO);
        Intent intent = new Intent(this, TaskBolckDetailsInfoActivity.class);
        startActivity(intent);
    }

    private void showDailog(LatLng latLng, TaskBean taskBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("选择：");
        //    指定下拉列表的显示数据
        final String[] cities = {"楼栋补录", "设施补录"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        buildInsert(latLng, taskBean);
                        break;
                    case 1:
                        poiInsert(latLng, taskBean);
                        break;
                }
                // cities[which];
            }
        });
        // builder.setCancelable(false);
        builder.show();
    }

    private void poiInsert(LatLng latLng, TaskBean taskBean) {
        CommunityBriefBean community = taskBean.getCommunity();
        if (null == taskBean || TextUtils.isEmpty(community.getId())) {
            ToastUtils.showToast(this, "请先添加小区信息");
            return;
        }

        DimPOIVO dimPOIVO = new DimPOIVO();
        StringBuffer latSb = new StringBuffer();
        latSb.append(latLng.longitude).append(",").append(latLng.latitude);
        dimPOIVO.setLocation(latSb.toString());
        dimPOIVO.setCommId(community.getId());
        dimPOIVO.setCommName(community.getCommName());
        dimPOIVO.setDataSrc("app采集");
        EventBus.getDefault().postSticky(dimPOIVO);
        Intent intent = new Intent(this, TaskAddPOIActivity.class);
        startActivity(intent);
    }
//    @Override
//    public void onMyLocationChange(Location location) {

// 定位回调监听
//        if (location != null) {
//            Log.e("amap", "onMyLocationChange 定位成功， lat: " + location.getLatitude() + " lon: " + location.getLongitude());
//            Bundle bundle = location.getExtras();
//            if (bundle != null) {
//                int errorCode = bundle.getInt(MyLocationStyle.ERROR_CODE);
//                String errorInfo = bundle.getString(MyLocationStyle.ERROR_INFO);
//                // 定位类型，可能为GPS WIFI等，具体可以参考官网的定位SDK介绍
//                int locationType = bundle.getInt(MyLocationStyle.LOCATION_TYPE);
//                // 定位、但不会移动到地图中心点，并且会跟随设备移动。
//                if (isLocation) {
//                    aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER));
//                }
//                isLocation = true;
//
//                //aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
//                /*
//                errorCode
//                errorInfo
//                locationType
//                */
//                Log.e("amap", "定位信息， code: " + errorCode + " errorInfo: " + errorInfo + " locationType: " + locationType);
//            } else {
//                Log.e("amap", "定位信息， bundle is null ");
//
//            }
//        } else {
//            Log.e("amap", "定位失败");
//        }

//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (null != mMapView) {
            mMapView.onDestroy();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            super.onResume();
            if (Build.VERSION.SDK_INT >= 23) {
                if (isNeedCheck) {
                    checkPermissions(needPermissions);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(GEOFENCE_BROADCAST_ACTION);
//        registerReceiver(mGeoFenceReceiver, filter);

        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(mGeoFenceReceiver);
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * @param
     * @since 2.5.0
     */
    @TargetApi(23)
    private void checkPermissions(String... permissions) {
        try {
            if (Build.VERSION.SDK_INT >= 23 && getApplicationInfo().targetSdkVersion >= 23) {
                List<String> needRequestPermissonList = findDeniedPermissions(permissions);
                if (null != needRequestPermissonList
                        && needRequestPermissonList.size() > 0) {
                    try {
                        String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
                        Method method = getClass().getMethod("requestPermissions", new Class[]{String[].class, int.class});
                        method.invoke(this, array, 0);
                    } catch (Throwable e) {

                    }
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    @TargetApi(23)
    private List<String> findDeniedPermissions(String[] permissions) {
        try {
            List<String> needRequestPermissonList = new ArrayList<String>();
            if (Build.VERSION.SDK_INT >= 23 && getApplicationInfo().targetSdkVersion >= 23) {
                for (String perm : permissions) {
                    if (checkMySelfPermission(perm) != PackageManager.PERMISSION_GRANTED
                            || shouldShowMyRequestPermissionRationale(perm)) {
                        needRequestPermissonList.add(perm);
                    }
                }
            }
            return needRequestPermissonList;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private int checkMySelfPermission(String perm) {
        try {
            Method method = getClass().getMethod("checkSelfPermission", new Class[]{String.class});
            Integer permissionInt = (Integer) method.invoke(this, perm);
            return permissionInt;
        } catch (Throwable e) {
        }
        return -1;
    }

    private boolean shouldShowMyRequestPermissionRationale(String perm) {
        try {
            Method method = getClass().getMethod("shouldShowRequestPermissionRationale", new Class[]{String.class});
            Boolean permissionInt = (Boolean) method.invoke(this, perm);
            return permissionInt;
        } catch (Throwable e) {
        }
        return false;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        try {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return true;
    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                if (requestCode == PERMISSON_REQUESTCODE) {
                    if (!verifyPermissions(paramArrayOfInt)) {
                        showMissingPermissionDialog();
                        isNeedCheck = false;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("当前应用缺少必要权限。\\n\\n请点击\\\"设置\\\"-\\\"权限\\\"-打开所需权限");

            // 拒绝, 退出应用
            builder.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                finish();
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });

            builder.setPositiveButton("设置",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                startAppSettings();
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });

            builder.setCancelable(false);

            builder.show();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        try {
            Intent intent = new Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onMapClick(LatLng latLng) {
        if (zoomNumber > 15) {
            return;
        }

        if (isContainsPolygon(latLng)) {
            // 是否在区域内
            // 点击地图，显示标注
            clearMarker();
            taskBean = getTaskBean(latLng);
            if (null != commBean && taskBean.getBusiObjId().equals(commBean.getGisId())) {
                CommunityBriefBean communityBriefBean = taskBean.getCommunity();
                if (null == communityBriefBean) {
                    communityBriefBean = new CommunityBriefBean();
                }
                communityBriefBean.setId(commBean.getCommunityId());
                communityBriefBean.setGisId(commBean.getGisId());
                taskBean.setCommunity(communityBriefBean);
            }

            mWindowAdapterArea = new WindowAdapter(this, "polygon", aMap, marker, taskBean, latLng);
            marker = mWindowAdapterArea.getMarker();
        } else {
            clearMarker();
        }
    }

    //判定点击的点是否在区域内
    private boolean isContainsPolygon(LatLng latLng) {
        boolean flag = false;
        for (Polygon polygon : polygonList) {
            flag = polygon.contains(latLng);
            if (flag) {
                return flag;
            }

        }
        return flag;
    }

    private TaskBean getTaskBean(LatLng latLng) {
        for (TaskBean taskBean : taskList) {
            PolygonOptions polygonOption = new PolygonOptions();
            polygonOption.add(taskBean.getLatLngs());
            Polygon mPolygon = aMap.addPolygon(polygonOption);
            if (mPolygon.contains(latLng)) {
                mPolygon.remove();
                return taskBean;
            }
            mPolygon.remove();
        }
        return new TaskBean();
    }


    private void clearMarker() {
        if (null != marker) {
            marker.remove();
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        //dealtInfo.setText("当前地图的缩放级别为: " + cameraPosition.zoom);
        zoomNumber = (int) (cameraPosition.zoom);
        if (zoomNumber > 15) {
            removePolygons();
            clearMarker();
            addMoreMarket();

        } else {
            removeMarkers();
            drawPolygon();
        }
    }

    private void removePolygons() {
        if (null != polygonList && polygonList.size() > 0) {
            for (Polygon polygon : polygonList) {
                polygon.remove();
            }
            polygonList.clear();
        }
    }

    private void removeMarkers() {
        if (null != markerList && markerList.size() > 0) {
            for (Marker marker : markerList) {
                marker.remove();
            }
            markerList.clear();
        }
    }

    @Override
    public void showResult(PdSListBean bean) {
        if (null == bean) {
            return;
        }
        Constants.PDSVO_LIST = bean.getList();
    }

    @Override
    public void showResult(TaskListBean bean) {
        pdSListPresenter.getPdsTypes();
        if (null == bean) {
            return;
        }
        TASK_NUMBER = String.valueOf(bean.getList().size());
        String testStr = getResources().getString(R.string.house_task);
        String result = String.format(testStr, userName, TASK_NUMBER);
        dealtInfo.setText(result);
        taskList = bean.getList();
        if (null == taskList || 0 == taskList.size()) {
            return;
        }
        try {
            for (TaskBean taskBean : taskList) {
                GisObjVO gisObjBriefBean = taskBean.getGisObj();
                String data = gisObjBriefBean.getData();
                if (!TextUtils.isEmpty(data)) {
                    JSONArray jsonArray = new JSONArray(data);
                    int length = jsonArray.length();
                    LatLng[] latLngs = new LatLng[length];
                    for (int i = 0; i < length; i++) {
                        JSONArray value = (JSONArray) jsonArray.opt(i);
                        Double longitude = (Double) value.opt(0);
                        Double latitude = (Double) value.opt(1);
                        LatLng latLng = new LatLng(latitude, longitude);
                        latLngs[i] = latLng;
                    }
                    taskBean.setLatLngs(latLngs);
                }
                List<GisObjVO> gisObjBuildList = taskBean.getGisObjBuildList();
                for (GisObjVO gisObjVO : gisObjBuildList) {
                    gisObjVO.setTaskId(taskBean.getId());
                    marketList.add(gisObjVO);
                }
            }
            drawPolygon();
            addMoreMarket();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCenterEvent(LatLng latLng) {
        aMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        // 移除粘性事件
        EventBus.getDefault().removeStickyEvent(latLng);
    }


    @Override
    public void showLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoading() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg);
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 是否触发按键为back键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        } else {
            // 如果不是back键正常响应
            return super.onKeyDown(keyCode, event);
        }
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            ToastUtils.showToast(this, getResources().getString(R.string.home_exits));
            clickTime = System.currentTimeMillis();
        } else {
            ActivityStackManager.getManager().exitApp(mContext);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCommunityEvent(MessageEventVO commBean) {
        this.commBean = commBean;
        if (null != commBean && taskBean.getBusiObjId().equals(commBean.getGisId())) {
            CommunityBriefBean communityBriefBean = taskBean.getCommunity();
            if (null == communityBriefBean) {
                communityBriefBean = new CommunityBriefBean();
            }
            communityBriefBean.setId(commBean.getCommunityId());
            communityBriefBean.setGisId(commBean.getGisId());
            taskBean.setCommunity(communityBriefBean);
        }
        // 移除粘性事件
        EventBus.getDefault().removeStickyEvent(commBean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onBuildEvent(GisObjVO gisObjVO) {
        if (null == marketList || 0 == marketList.size()) {
            marketList = new ArrayList<>();
        }
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(gisObjVO.getCenterY()),//设置纬度
                        Double.parseDouble(gisObjVO.getCenterX())))//设置经度
                .title(reTextNull(gisObjVO.getBuildNo()))//设置标题
                .snippet(reTextNull(gisObjVO.getBuildName()))//设置内容
                // .snippet(marketList.get(i).getContent())//设置内容
                // .setFlat(true) // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                .draggable(false) //设置Marker可拖动
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_selected)));
        markerList.add(marker);
        marketList.add(gisObjVO);
        EventBus.getDefault().removeStickyEvent(gisObjVO);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        String testStr = getResources().getString(R.string.house_task);
        String result = String.format(testStr, userName, TASK_NUMBER);
        dealtInfo.setText(result);
    }
}


