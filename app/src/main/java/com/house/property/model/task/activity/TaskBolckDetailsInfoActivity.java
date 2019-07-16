package com.house.property.model.task.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.house.property.R;
import com.house.property.base.BaseFragmentActivity;
import com.house.property.base.Constants;
import com.house.property.http.retrofit.HttpResponse;
import com.house.property.model.task.entity.AttributeVO;
import com.house.property.model.task.entity.BuildDetailsVO;
import com.house.property.model.task.entity.CommBuildListDetailsVO;
import com.house.property.model.task.entity.DimFileinfoVO;
import com.house.property.model.task.entity.FileCoordinateInfoVO;
import com.house.property.model.task.entity.GisObjVO;
import com.house.property.model.task.entity.MessageEventVO;
import com.house.property.model.task.entity.PdSVO;
import com.house.property.model.task.entity.PhotoBean;
import com.house.property.model.task.iface.IBuildDetailsAddView;
import com.house.property.model.task.iface.IBuildDetailsQueryView;
import com.house.property.model.task.iface.IBuildDetailsUpdateView;
import com.house.property.model.task.iface.ICommBuildDetailsListView;
import com.house.property.model.task.presenter.BuildDetailsAddPresenter;
import com.house.property.model.task.presenter.BuildDetailsQueryPresenter;
import com.house.property.model.task.presenter.BuildDetailsUpdatePresenter;
import com.house.property.model.task.presenter.CommBuildDetailsListPresenter;
import com.house.property.model.task.upload.ApiUtil;
import com.house.property.utils.CornerTransform;
import com.house.property.utils.PhotoUtils;
import com.house.property.utils.ToastUtils;
import com.house.property.utils.Utils;
import com.house.property.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static com.house.property.base.Constants.ATTRIBUTE_TYPE_DIM_BUILD;
import static com.house.property.base.Constants.COMM_ENTIRE_PHOTO;
import static com.house.property.base.Constants.COMM_GATE_PHOTO;
import static com.house.property.base.Constants.PDS_TYPE_BUILD_STRUCTURE;
import static com.house.property.base.Constants.PDS_TYPE_BUILD_TYPE;
import static com.house.property.base.Constants.PDS_TYPE_DIAN_WULAN;
import static com.house.property.base.Constants.PDS_TYPE_DINGCENG;
import static com.house.property.base.Constants.PDS_TYPE_GUAN_WULAN;
import static com.house.property.base.Constants.PDS_TYPE_HOUSE_ATTRIBUTE;
import static com.house.property.base.Constants.PDS_TYPE_JIANZU_LEVEL;
import static com.house.property.base.Constants.PDS_TYPE_QIWEI_WULAN;
import static com.house.property.base.Constants.PDS_TYPE_TESUCEN_TEDIAN;
import static com.house.property.base.Constants.PDS_TYPE_WAIQIANG;
import static com.house.property.base.Constants.PDS_TYPE_WENHUA_YINXIAN;
import static com.house.property.base.Constants.PDS_TYPE_YN;
import static com.house.property.base.Constants.PDS_TYPE_ZAOYIN_WULAN;
import static com.house.property.base.Constants.PDS_TYPE_ZHUANGXIU_INFO;
import static com.house.property.base.Constants.PDS_TYPE_ZUFANG_BAOYANG;
import static com.house.property.base.Constants.TAKE_PHOTO;
import static com.house.property.utils.TaskUtils.addUpdateAttributeList;
import static com.house.property.utils.TaskUtils.getAttributeTypeList;
import static com.house.property.utils.TaskUtils.getSpinnerKey;
import static com.house.property.utils.TaskUtils.getTypeList;
import static com.house.property.utils.TaskUtils.initSpinner;
import static com.house.property.utils.TaskUtils.reTextNull;
import static com.house.property.utils.TaskUtils.showNullToast;

/**
 * 小区幢楼详情列表
 */
public class TaskBolckDetailsInfoActivity extends BaseFragmentActivity implements IBuildDetailsUpdateView, AMapLocationListener, IBuildDetailsAddView, IBuildDetailsQueryView, ICommBuildDetailsListView {

    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;

    @BindView(R.id.attribute_house_time_tv)
    TextView attributeHouseTimeTv;
    @BindView(R.id.bolck_residential_science_tv)
    TextView bolckResidentialScienceTv;

    @BindView(R.id.bolck_negative_electric_tv)
    TextView bolckNegativeElectricTv;
    @BindView(R.id.bolck_negative_sound_tv)
    TextView bolckNegativeSoundTv;
    @BindView(R.id.bolck_negative_gas_tv)
    TextView bolckNegativeGasTv;
    @BindView(R.id.bolck_negative_light_tv)
    TextView bolckNegativeLightTv;
    @BindView(R.id.house_task_name)
    TextView houseTaskNameTv;
    @BindView(R.id.attribute_tudi_time_tv)
    TextView attributetudiTimeTv;
    @BindView(R.id.bolck_negative_wenhua_tv)
    TextView attributewenhuaTv;
    @BindView(R.id.bolck_negative_tesc_tv)
    TextView negativeTescTv;


    @BindView(R.id.bolck_number_et)
    TextView bolckNumberEt;
    @BindView(R.id.bolck_cellnumber_et)
    TextView bolckCellnumberEt;
    @BindView(R.id.household_et)
    TextView householdEt;
    @BindView(R.id.details_spacing_et)
    TextView detailsSpacingEt;
    @BindView(R.id.details_orientation_et)
    TextView detailsOrientationEt;
    @BindView(R.id.details_commercial_et)
    TextView detailsCommercialEt;
    @BindView(R.id.commercial_number1_et)
    TextView commercialNumberEt;
    @BindView(R.id.details_top_szc_et)
    TextView detailsTopSzcTv;
    @BindView(R.id.details_zz_szc_et)
    TextView detailsZzSzcTv;
    @BindView(R.id.dong_attribute_et)
    TextView dongAttributeEt;
    @BindView(R.id.attribute_tdmj_et)
    TextView attributeTdmjEt;


    @BindView(R.id.sheshi_level_one_spinner)
    Spinner detailsMaintainSpinner;
    @BindView(R.id.details_wall_spinner)
    Spinner detailsWallSpinner;
    @BindView(R.id.details_top_spinner)
    Spinner detailsTopSpinner;
    @BindView(R.id.details_scenery_spinner)
    Spinner detailsScenerySpinner;
    @BindView(R.id.house_attribute_spinner)
    Spinner houseAttributeSpinner;
    //    @BindView(R.id.housebuy_attribute_spinner)
//    Spinner housebuyAttributeSpinner;
    @BindView(R.id.structure_attribute_spinner)
    Spinner structureAttributeSpinner;
    @BindView(R.id.attribute_level_spinner)
    Spinner attributeLevelSpinner;
    @BindView(R.id.attribute_type_spinner)
    Spinner attributeTypeSpinner;
    @BindView(R.id.attribute_zxcd_spinner)
    Spinner attributeZxcdSpinner;
    @BindView(R.id.residential_card_spinner)
    Spinner residentialCardSpinner;
    @BindView(R.id.residential_storeroom_spinner)
    Spinner residentialStoreroomdSpinner;
    @BindView(R.id.residential_electric_spinner)
    Spinner residentialElectricSpinner;
    @BindView(R.id.resideantial_gas_spinner)
    Spinner residentialGasSpinner;
    @BindView(R.id.residential_heat_spinner)
    Spinner propertyHeatSpinner;
    @BindView(R.id.jizhonggongnuan_spinner)
    Spinner jizhonggongnuanSpinner;
    @BindView(R.id.property_security_dspinner)
    Spinner securitySpinner;
    @BindView(R.id.residential_ggyl_spinner)
    Spinner residentialGgylSpinner;
    @BindView(R.id.residential_xfss_spinner)
    Spinner residentialXfsslSpinner;
    @BindView(R.id.residential_echs_spinner)
    Spinner residentialEchslSpinner;
    @BindView(R.id.residential_gl_spinner)
    Spinner residentialGlSpinner;
    @BindView(R.id.residential_dt_spinner)
    Spinner residentialDtSpinner;
    @BindView(R.id.residential_dinuan_spinner)
    Spinner residentialDinuanSpinner;
    @BindView(R.id.residential_zykt_spinner)
    Spinner residentialzyktSpinner;
    @BindView(R.id.negative_refuse_spinner)
    Spinner negativeRefuseSpinner;
    @BindView(R.id.residential_mengjin_spinner)
    Spinner negativeMengjinSpinner;
    @BindView(R.id.residential_xinfeng_spinner)
    Spinner negativeXinfengSpinner;
    @BindView(R.id.residential_tikong_spinner)
    Spinner negativeTikongSpinner;


    @BindView(R.id.gallery_bolck_other_gate)
    LinearLayout mGalleryGatehouse;
    @BindView(R.id.gallery_entire)
    LinearLayout mGalleryEntire;
    @BindView(R.id.task_moban)
    LinearLayout taskMobanll;

    @BindView(R.id.task_fuyong1)
    TextView taskFuyongTvOne;
    @BindView(R.id.task_fuyong2)
    TextView taskFuyongTvTwo;

    private BuildDetailsAddPresenter buildDetailsAddPresenter = new BuildDetailsAddPresenter(this, this);
    private BuildDetailsQueryPresenter buildDetailsQueryPresenter = new BuildDetailsQueryPresenter(this, this);
    private BuildDetailsUpdatePresenter buildDetailsUpdatePresenter = new BuildDetailsUpdatePresenter(this, this);
    private CommBuildDetailsListPresenter commBuildDetailsListPresenter = new CommBuildDetailsListPresenter(this, this);

    private BuildDetailsVO upVO;
    private BuildDetailsVO buildBean;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION};
    private AMapLocationClient locationClientContinue;
    private double gaoDeLatitude = 0;
    private double gaoDeLongitude = 0;
    private double wgs84Latitude = 0;
    private double wgs84Longitude = 0;
    private String photoType;
    private String buildId = "";
    private String taskId = "";
    private RLoadingDialog mLoadingDialog;
    private List<PdSVO> yseOrnoList = new ArrayList<>();
    private List<PdSVO> zfbyList = new ArrayList<>();
    private List<PdSVO> wallList = new ArrayList<>();
    private List<PdSVO> topList = new ArrayList<>();
    private List<PdSVO> houseAttrList = new ArrayList<>();
    private List<PdSVO> structureList = new ArrayList<>();
    private List<PdSVO> jianzhuList = new ArrayList<>();
    private List<PdSVO> jianzhuTypeList = new ArrayList<>();
    private List<PdSVO> zhuangxiuList = new ArrayList<>();

    private List<AttributeVO> allAttributeList = new ArrayList<>();
    private List<PdSVO> dianwuranList = new ArrayList<>();
    private List<PdSVO> zaoyinList = new ArrayList<>();
    private List<PdSVO> qiweiList = new ArrayList<>();
    private List<PdSVO> guangwuranList = new ArrayList<>();
    private List<PdSVO> wenhuaList = new ArrayList<>();
    private List<PdSVO> teshucList = new ArrayList<>();
    private List<AttributeVO> allUpdateAttributeList = new ArrayList<>();
    private BuildDetailsVO fuyongOneVO;
    private BuildDetailsVO fuyongTwoVO;

    private List<DimFileinfoVO> upDimFileinfoVOList = new ArrayList<>();
    private List<DimFileinfoVO> blackDimFileinfoVOList = new ArrayList<>();

    // 获取位置管理服务
    private LocationManager locationManager;
    private Location location;
    private static final int PRIVATE_CODE = 1315;//开启GPS权限
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {
            wgs84Longitude = location.getLongitude();
            wgs84Latitude = location.getLatitude();
        }
    };
    @Override
    protected int getContentViewId() {
        return R.layout.activity_task_bolck_details;
    }

    @Override
    protected void initBundleData() {
        presentersList.add(buildDetailsAddPresenter);
        presentersList.add(buildDetailsQueryPresenter);
        presentersList.add(buildDetailsUpdatePresenter);
        presentersList.add(commBuildDetailsListPresenter);
        Intent intent = getIntent();
        if (null != intent) {
            buildId = intent.getStringExtra("buildId");
            taskId = intent.getStringExtra("taskId");
        }

    }

    @Override
    protected void initView() {
        registeredEventBus();
        mLoadingDialog = new RLoadingDialog(this, true);
        titleTv.setText(R.string.house_task_bolck_details);
        pageRightRl.setVisibility(View.VISIBLE);
        pageRightTv.setText(R.string.btn_save);
        showGPSContacts();
        startGaoDeGps();
    }

    private void startGaoDeGps() {
        locationClientContinue = new AMapLocationClient(this.getApplicationContext());
        locationClientContinue.setLocationListener(this);
        AMapLocationClientOption locationClientContinueOption = new AMapLocationClientOption();
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        locationClientContinueOption.setInterval(10000);
        //给定位客户端对象设置定位参数
        locationClientContinue.setLocationOption(locationClientContinueOption);
        //启动定位
        locationClientContinue.startLocation();
    }

    /**
     * 获取具体位置的经纬度
     */
    private void getLocation() {

        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) this.getSystemService(serviceName);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        // 设置定位精确度
        // Criteria.ACCURACY_COARSE比较粗略，
        // Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        // 设置是否需要方位信息
        criteria.setBearingRequired(false);
        // 设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        /**这段代码不需要深究，是locationManager.getLastKnownLocation(provider)自动生成的，不加会出错**/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
        updateLocation(location);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1, locationListener);

    }

    @Override
    protected void initData() {
        buildDetailsQueryPresenter.queryBuildDetails(buildId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getCommunityEvent(BuildDetailsVO buildDetailsVO) {
        this.upVO = buildDetailsVO;
        // 移除粘性事件
        EventBus.getDefault().removeStickyEvent(buildDetailsVO);
    }

    //获取权限
    private void getPermission() {
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //已经打开权限
//            Toast.makeText(this, "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用权限", 1, permissions);
        }

    }

    /**
     * 检测GPS、位置权限是否开启
     */
    public void showGPSContacts() {
        locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        boolean ok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (Build.VERSION.SDK_INT >= 23) { //判断是否为android6.0系统版本，如果是，需要动态添加权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PERMISSION_GRANTED) {// 没有权限，申请权限。
                    finish();
                    ToastUtils.showToast(getApplicationContext(), "请打开GPS定位");
                } else {
                    getLocation();//getLocation为定位方法
                }
            } else {
                getLocation();//getLocation为定位方法
            }
        } else {
            Toast.makeText(this, "系统检测到未开启GPS定位服务,请开启", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, PRIVATE_CODE);
        }
    }


    /**
     * 获取到当前位置的经纬度
     *
     * @param location
     */
    private void updateLocation(Location location) {
        if (location != null) {
            wgs84Latitude = location.getLatitude();
            wgs84Longitude = location.getLongitude();
            //  Log.e("维度：" + latitude + "\n经度" + longitude);
        } else {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.task_fuyong1, R.id.task_fuyong2, R.id.attribute_tudi_time_tv, R.id.bolck_negative_tesc_tv, R.id.bolck_negative_wenhua_tv, R.id.add_saomiao_places_bolck_other_entire, R.id.add_saomiao_bolck_other_gate, R.id.bolck_negative_electric_tv, R.id.bolck_negative_sound_tv, R.id.bolck_negative_gas_tv, R.id.bolck_negative_light_tv, R.id.bolck_residential_science_tv, R.id.attribute_house_time_tv, R.id.page_back, R.id.page_right_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bolck_negative_electric_tv:
                Utils.showMultiChoiceItems(this, dianwuranList, bolckNegativeElectricTv);
                break;
            case R.id.bolck_negative_sound_tv:
                Utils.showMultiChoiceItems(this, zaoyinList, bolckNegativeSoundTv);
                break;
            case R.id.bolck_negative_gas_tv:
                Utils.showMultiChoiceItems(this, qiweiList, bolckNegativeGasTv);
                break;
            case R.id.bolck_negative_light_tv:
                Utils.showMultiChoiceItems(this, guangwuranList, bolckNegativeLightTv);
                break;
            case R.id.bolck_negative_wenhua_tv:
                Utils.showMultiChoiceItems(this, wenhuaList, attributewenhuaTv);
                break;
            case R.id.bolck_negative_tesc_tv:
                Utils.showMultiChoiceItems(this, teshucList, negativeTescTv);
                break;
            case R.id.attribute_tudi_time_tv:
                Utils.showDatePickerDialog(this, attributetudiTimeTv);
                break;
            case R.id.page_right_rl:
                if (null == upDimFileinfoVOList || 0 == upDimFileinfoVOList.size()) {
                    update();
                    return;
                }
                upFilesInfo();
                //update();
                break;
            case R.id.page_back:
                finish();
                break;
            case R.id.add_saomiao_bolck_other_gate:
                PhotoUtils.takePicture(this, TAKE_PHOTO);
                photoType = COMM_GATE_PHOTO;
                break;
            case R.id.add_saomiao_places_bolck_other_entire:
                PhotoUtils.takePicture(this, TAKE_PHOTO);
                photoType = COMM_ENTIRE_PHOTO;
                break;
            case R.id.task_fuyong1:
                refreshUi(fuyongOneVO);
                break;
            case R.id.task_fuyong2:
                refreshUi(fuyongTwoVO);
                break;
        }
    }

    private void update() {
        if (null != allUpdateAttributeList) {
            allUpdateAttributeList.clear();
        }
        String houseTaskNameStr = houseTaskNameTv.getText().toString();
        String bolckNumberEtStr = bolckNumberEt.getText().toString();
        String bolckCellnumberEtStr = bolckCellnumberEt.getText().toString();
        String householdEtStr = householdEt.getText().toString();
        String detailsSpacingEtStr = detailsSpacingEt.getText().toString();
        String detailsOrientationEtStr = detailsOrientationEt.getText().toString();
        String detailsCommercialEtStr = detailsCommercialEt.getText().toString();
        String commercialNumberEtStr = commercialNumberEt.getText().toString();
        String detailsTopSzcTvStr = detailsTopSzcTv.getText().toString();
        String detailsZzSzcTvStr = detailsZzSzcTv.getText().toString();
        String dongAttributeEtStr = dongAttributeEt.getText().toString();
        String attributeTdmjEtStr = attributeTdmjEt.getText().toString();
        String attributetudiTimeTvStr = attributetudiTimeTv.getText().toString();
        if (showNullToast(bolckNumberEtStr, getResources().getString(R.string.house_task_details_bolck_number))) {
            return;
        }
        if (showNullToast(bolckCellnumberEtStr, getResources().getString(R.string.house_task_details_bolck_cellnumber))) {
            return;
        }
        if (showNullToast(householdEtStr, getResources().getString(R.string.house_task_details_household))) {
            return;
        }
        if (showNullToast(detailsSpacingEtStr, getResources().getString(R.string.house_task_details_spacing))) {
            return;
        }
        if (showNullToast(detailsOrientationEtStr, getResources().getString(R.string.house_task_details_orientation))) {
            return;
        }


        String detailsMaintainSpinnerStr = getSpinnerKey(detailsMaintainSpinner.getSelectedItem().toString(), zfbyList);
        String detailsWallSpinnerStr = getSpinnerKey(detailsWallSpinner.getSelectedItem().toString(), wallList);
        String detailsTopSpinnerStr = getSpinnerKey(detailsTopSpinner.getSelectedItem().toString(), topList);
        String detailsScenerySpinnerStr = getSpinnerKey(detailsScenerySpinner.getSelectedItem().toString(), yseOrnoList);
        String houseAttributeSpinnerStr = getSpinnerKey(houseAttributeSpinner.getSelectedItem().toString(), houseAttrList);
        String structureAttributeSpinnerStr = getSpinnerKey(structureAttributeSpinner.getSelectedItem().toString(), structureList);
        String attributeLevelSpinnerStr = getSpinnerKey(attributeLevelSpinner.getSelectedItem().toString(), jianzhuList);
        String attributeTypeSpinnerStr = getSpinnerKey(attributeTypeSpinner.getSelectedItem().toString(), jianzhuTypeList);
        String attributeZxcdSpinnerStr = getSpinnerKey(attributeZxcdSpinner.getSelectedItem().toString(), zhuangxiuList);
        String residentialCardSpinnerStr = getSpinnerKey(residentialCardSpinner.getSelectedItem().toString(), yseOrnoList);
        String residentialStoreroomdSpinnerStr = getSpinnerKey(residentialStoreroomdSpinner.getSelectedItem().toString(), yseOrnoList);
        String residentialElectricSpinnerStr = getSpinnerKey(residentialElectricSpinner.getSelectedItem().toString(), yseOrnoList);
        String residentialGasSpinnerStr = getSpinnerKey(residentialGasSpinner.getSelectedItem().toString(), yseOrnoList);
        String propertyHeatSpinnerStr = getSpinnerKey(propertyHeatSpinner.getSelectedItem().toString(), yseOrnoList);
        String jizhonggongnuanSpinnerStr = getSpinnerKey(jizhonggongnuanSpinner.getSelectedItem().toString(), yseOrnoList);
        String securitySpinnerStr = getSpinnerKey(securitySpinner.getSelectedItem().toString(), yseOrnoList);
        String residentialGgylSpinnerStr = getSpinnerKey(residentialGgylSpinner.getSelectedItem().toString(), yseOrnoList);
        String residentialXfsslSpinnerStr = getSpinnerKey(residentialXfsslSpinner.getSelectedItem().toString(), yseOrnoList);
        String residentialEchslSpinnerStr = getSpinnerKey(residentialEchslSpinner.getSelectedItem().toString(), yseOrnoList);
        String residentialGlSpinnerStr = getSpinnerKey(residentialGlSpinner.getSelectedItem().toString(), yseOrnoList);
        String residentialDtSpinnerStr = getSpinnerKey(residentialDtSpinner.getSelectedItem().toString(), yseOrnoList);
        String residentialDinuanSpinnerStr = getSpinnerKey(residentialDinuanSpinner.getSelectedItem().toString(), yseOrnoList);
        String residentialzyktSpinnerStr = getSpinnerKey(residentialzyktSpinner.getSelectedItem().toString(), yseOrnoList);
        String negativeRefuseSpinnerStr = getSpinnerKey(negativeRefuseSpinner.getSelectedItem().toString(), yseOrnoList);
        String negativeMengjinSpinnerStr = getSpinnerKey(negativeMengjinSpinner.getSelectedItem().toString(), yseOrnoList);
        String negativeXinfengSpinnerStr = getSpinnerKey(negativeXinfengSpinner.getSelectedItem().toString(), yseOrnoList);
        String negativeTikongSpinnerStr = getSpinnerKey(negativeTikongSpinner.getSelectedItem().toString(), yseOrnoList);


        String bolckNegativeElectricTvStr = bolckNegativeElectricTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, dianwuranList, bolckNegativeElectricTvStr, buildBean.getId(), ATTRIBUTE_TYPE_DIM_BUILD);
        String bolckNegativeSoundTvStr = bolckNegativeSoundTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, zaoyinList, bolckNegativeSoundTvStr, buildBean.getId(), ATTRIBUTE_TYPE_DIM_BUILD);
        String bolckNegativeGasTvStr = bolckNegativeGasTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, qiweiList, bolckNegativeGasTvStr, buildBean.getId(), ATTRIBUTE_TYPE_DIM_BUILD);
        String bolckNegativeLightTvStr = bolckNegativeLightTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, guangwuranList, bolckNegativeLightTvStr, buildBean.getId(), ATTRIBUTE_TYPE_DIM_BUILD);
        String attributewenhuaTvStr = attributewenhuaTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, wenhuaList, attributewenhuaTvStr, buildBean.getId(), ATTRIBUTE_TYPE_DIM_BUILD);
        String negativeTescTvStr = negativeTescTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, teshucList, negativeTescTvStr, buildBean.getId(), ATTRIBUTE_TYPE_DIM_BUILD);

        BuildDetailsVO buildDetailsVO = buildBean;
        buildDetailsVO.setCommName(houseTaskNameStr);
        buildDetailsVO.setAttributeLits(allUpdateAttributeList);
        buildDetailsVO.setBuildCareinfo(detailsMaintainSpinnerStr);
        buildDetailsVO.setBuildNo(bolckNumberEtStr);
        buildDetailsVO.setBuildFloorcnt(Long.parseLong(detailsSpacingEtStr));
        buildDetailsVO.setUnderFloorcnt(Long.parseLong(detailsCommercialEtStr));
        buildDetailsVO.setGroundFloorno(commercialNumberEtStr);
        buildDetailsVO.setTopFloorno(detailsTopSzcTvStr);
        buildDetailsVO.setHouseFloorno(detailsZzSzcTvStr);
        buildDetailsVO.setBuildStructure(structureAttributeSpinnerStr);
        buildDetailsVO.setBuildLevel(attributeLevelSpinnerStr);
        buildDetailsVO.setBuildType(attributeTypeSpinnerStr);
        buildDetailsVO.setSalesLicense(dongAttributeEtStr);
        buildDetailsVO.setLandArea(attributeTdmjEtStr);
        buildDetailsVO.setLandDate(attributetudiTimeTvStr);
        buildDetailsVO.setBuildcellCnt(bolckCellnumberEtStr);
        buildDetailsVO.setBuildcellInfo(householdEtStr);
        buildDetailsVO.setBuildChaoxiang(detailsOrientationEtStr);
        buildDetailsVO.setBuildAttr(houseAttributeSpinnerStr);
        // buildDetailsVO.setShopFloorno();
        buildDetailsVO.setBuildCareinfo(detailsMaintainSpinnerStr);
        buildDetailsVO.setExtWall(detailsWallSpinnerStr);
        buildDetailsVO.setTopFloorinfo(detailsTopSpinnerStr);
        buildDetailsVO.setIsJingguanlou(detailsScenerySpinnerStr);
        buildDetailsVO.setZhuangxiuInfo(attributeZxcdSpinnerStr);
        buildDetailsVO.setIsXinfeng(negativeXinfengSpinnerStr);
        buildDetailsVO.setIsJizhonggongnuan(jizhonggongnuanSpinnerStr);
        buildDetailsVO.setIsMengjin(negativeMengjinSpinnerStr);
        buildDetailsVO.setIsTikong(negativeTikongSpinnerStr);
        buildDetailsVO.setIsAnfang(securitySpinnerStr);
        buildDetailsVO.setIsPubFunspace(residentialGgylSpinnerStr);
        buildDetailsVO.setIsFpd(residentialXfsslSpinnerStr);
        buildDetailsVO.setIsGarage(residentialCardSpinnerStr);
        buildDetailsVO.setIsStoreroom(residentialStoreroomdSpinnerStr);
        buildDetailsVO.setIsWaterSupp(residentialEchslSpinnerStr);
        buildDetailsVO.setIsEpowerSupp(residentialElectricSpinnerStr);
        buildDetailsVO.setIsGasSupp(residentialGasSpinnerStr);
        buildDetailsVO.setIsHeatSupp(propertyHeatSpinnerStr);
        buildDetailsVO.setIsGelou(residentialGlSpinnerStr);
        buildDetailsVO.setIsLift(residentialDtSpinnerStr);
        buildDetailsVO.setIsHeat(residentialDinuanSpinnerStr);
        buildDetailsVO.setIsZhongyangkongtiao(residentialzyktSpinnerStr);
        buildDetailsVO.setIsRubbstation(negativeRefuseSpinnerStr);
        buildDetailsVO.setCommPhotos(blackDimFileinfoVOList);
        GisObjVO gisObjVO = buildBean.getGisObjVO();
        if (null == gisObjVO || TextUtils.isEmpty(gisObjVO.getId())) {
            gisObjVO = new GisObjVO();
            if (null != upVO) {
                gisObjVO = upVO.getGisObjVO();
                buildDetailsVO.setCommId(upVO.getCommId());
                buildDetailsVO.setCommName(upVO.getCommName());
                gisObjVO.setType("point");
                StringBuffer sb = new StringBuffer();
                sb.append("[[").append(gisObjVO.getCenterX()).append(",").append(gisObjVO.getCenterY()).append("]]");
                gisObjVO.setData(sb.toString());
                gisObjVO.setLevelId("poi_190403");
                gisObjVO.setLabelTxt(bolckNumberEtStr);
                gisObjVO.setLabelX(gisObjVO.getCenterX());
                gisObjVO.setLabelY(gisObjVO.getCenterY());
                gisObjVO.setName(upVO.getCommName());
                gisObjVO.setState("ENABLED");
            }

        }
        buildDetailsVO.setGisObjVO(gisObjVO);
        submit(buildDetailsVO);
        // showShengheDialog(buildDetailsVO);

    }

    private void submit(BuildDetailsVO buildDetailsVO) {
        if (null == buildBean || TextUtils.isEmpty(buildBean.getId())) {
            if (null != upVO) {
                buildDetailsVO.setCommId(upVO.getCommId());
                buildDetailsVO.setCommName(upVO.getCommName());
            }
            buildDetailsAddPresenter.addBuildDetails(buildDetailsVO);
            buildBean = buildDetailsVO;
        } else {
            buildDetailsUpdatePresenter.updateBuildDetails(buildDetailsVO);
        }
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null == aMapLocation) {
            return;
        }
        gaoDeLatitude = aMapLocation.getLatitude();
        gaoDeLongitude = aMapLocation.getLongitude();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //框架要求必须这么写
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    //成功打开权限
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        Toast.makeText(this, "相关权限获取成功", Toast.LENGTH_SHORT).show();
    }

    //用户未同意权限
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "请同意相关权限，否则功能无法使用", Toast.LENGTH_SHORT).show();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String photoPath = "";
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        photoPath = String.valueOf(Constants.CAMERA_SAVE_PATH);
                    } else {
                        photoPath = PhotoUtils.getOutputMediaFileUri(this).getEncodedPath();
                    }
                }
                PhotoBean photoBean = new PhotoBean();
                photoBean.setId(buildId);
                photoBean.setGaoDeLatitude(gaoDeLatitude);
                photoBean.setGaoDeLongitude(gaoDeLongitude);
                photoBean.setWgs84Latitude(wgs84Latitude);
                photoBean.setWgs84Longitude(wgs84Longitude);
                photoBean.setPath(photoPath);
                photoBean.setType(photoType);
                showPhoto(photoBean);
                break;
        }

    }

    private void showPhoto(PhotoBean photoBean) {
        String type = photoBean.getType();
        String path = photoBean.getPath();
        String gaoDeLatitude  = String.valueOf(photoBean.getGaoDeLatitude());
        String gaoDeLongitude= String.valueOf(photoBean.getGaoDeLongitude());
        String wgs84Latitude = String.valueOf(photoBean.getWgs84Latitude());
        String wgs84Longitude = String.valueOf(photoBean.getWgs84Longitude());
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        Luban.with(mContext)
                .load(file)
                .ignoreBy(100)
                //.setTargetDir(PHOTO_PATH)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        DimFileinfoVO dimFileinfoVO = new DimFileinfoVO();
                        dimFileinfoVO.setRelaSrckey(reTextNull(photoBean.getId()));
                        dimFileinfoVO.setRelaSrctab("DIM_BUILD");
                        dimFileinfoVO.setFileType(type);
                        dimFileinfoVO.setFileBtype("photo");
                        dimFileinfoVO.setFileSdcardPath(file.getPath());
                        dimFileinfoVO.setFileName(file.getName());
                        FileCoordinateInfoVO fileCoordinateInfo = new FileCoordinateInfoVO();
                        fileCoordinateInfo.setGaodeLatitude(gaoDeLatitude);
                        fileCoordinateInfo.setGaodeLongitude(gaoDeLongitude);
                        fileCoordinateInfo.setWgs84Latitude(wgs84Latitude);
                        fileCoordinateInfo.setWgs84Longitude(wgs84Longitude);
                        dimFileinfoVO.setFileCoordinateInfo(fileCoordinateInfo);
                        upDimFileinfoVOList.add(dimFileinfoVO);
                        showGildPhoto(type, file.getPath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();

    }

    @Override
    public void showResult(CommBuildListDetailsVO bean) {
        if (null == bean) {
            return;
        }
        List<BuildDetailsVO> list = bean.getList();
        if (null == list || 0 == list.size()) {
            return;
        }

        taskMobanll.setVisibility(View.VISIBLE);
        StringBuffer sbone = new StringBuffer();
        fuyongOneVO = list.get(list.size() - 1);
        sbone.append("模板").append(" ").append(reTextNull(fuyongOneVO.getBuildNo()));
        taskFuyongTvOne.setText(sbone.toString());
        if (list.size() >= 2) {
            StringBuffer sbtwo = new StringBuffer();
            fuyongTwoVO = list.get(list.size() - 2);
            sbtwo.append("模板").append(" ").append(reTextNull(fuyongTwoVO.getBuildNo()));
            taskFuyongTvTwo.setText(sbtwo.toString());
        } else {
            taskFuyongTvTwo.setVisibility(View.GONE);
        }
    }

    public void showResult(MessageEventVO bean) {
        if (null == bean) {
            return;
        }
        ToastUtils.showToast(this, bean.getResult());
        //EventBus.getDefault().postSticky(bean);
        finish();
        // showShengheDialog();
    }

    @Override
    public void showResult(GisObjVO bean) {
        if (null == bean) {
            return;
        }
        EventBus.getDefault().postSticky(bean);
        ToastUtils.showToast(this, "添加成功");

        finish();
        // showShengheDialog();
    }


    public void showResult(BuildDetailsVO bean) {
        if (null == bean) {
            return;
        }
        if (TextUtils.isEmpty(bean.getId())) {
            if (!TextUtils.isEmpty(upVO.getCommId())) {
                commBuildDetailsListPresenter.getCommBuildDetailsList(upVO.getCommId());
            }
        } else {
            taskMobanll.setVisibility(View.GONE);
        }
        buildBean = bean;
        refreshUi(bean);
        List<DimFileinfoVO> commPhotos = bean.getCommPhotos();
        if (null == commPhotos || 0 == commPhotos.size()) {
            return;
        }
        for (DimFileinfoVO dimFileinfoVO : commPhotos) {
            showGildPhoto(dimFileinfoVO.getFileType(), dimFileinfoVO.getFilePath());
        }
    }

    private void refreshUi(BuildDetailsVO bean) {
        String commName = "";
        if (TextUtils.isEmpty(bean.getId())) {
            commName = upVO.getCommName();
        } else {
            commName = bean.getCommName();
        }
        houseTaskNameTv.setText(reTextNull(commName));
        bolckNumberEt.setText(reTextNull(bean.getBuildNo()));
        bolckCellnumberEt.setText(reTextNull(bean.getBuildcellCnt()));
        householdEt.setText(reTextNull(bean.getBuildcellInfo()));
        detailsSpacingEt.setText(null == bean.getBuildFloorcnt() ? String.valueOf(0) : String.valueOf(bean.getBuildFloorcnt()));
        detailsOrientationEt.setText(reTextNull(bean.getBuildChaoxiang()));
        detailsCommercialEt.setText(null == bean.getUnderFloorcnt() ? String.valueOf(0) : String.valueOf(bean.getUnderFloorcnt()));
        commercialNumberEt.setText(reTextNull(bean.getGroundFloorno()));
        detailsTopSzcTv.setText(reTextNull(bean.getTopFloorno()));
        detailsZzSzcTv.setText(reTextNull(bean.getHouseFloorno()));
        dongAttributeEt.setText(reTextNull(bean.getSalesLicense()));
        attributeTdmjEt.setText(reTextNull(bean.getLandArea()));
        attributetudiTimeTv.setText(reTextNull(bean.getLandDate()));
        if (TextUtils.isEmpty(bean.getLandDate())) {
            attributetudiTimeTv.setText("请选择");
        }

//        /attributeHouseTimeTv.setText(reTextNull(bean.getBuildDate()));

        yseOrnoList = getTypeList(Constants.PDSVO_LIST, PDS_TYPE_YN);
        zfbyList = getTypeList(Constants.PDSVO_LIST, PDS_TYPE_ZUFANG_BAOYANG);
        wallList = getTypeList(Constants.PDSVO_LIST, PDS_TYPE_WAIQIANG);
        topList = getTypeList(Constants.PDSVO_LIST, PDS_TYPE_DINGCENG);
        houseAttrList = getTypeList(Constants.PDSVO_LIST, PDS_TYPE_HOUSE_ATTRIBUTE);
        structureList = getTypeList(Constants.PDSVO_LIST, PDS_TYPE_BUILD_STRUCTURE);
        jianzhuList = getTypeList(Constants.PDSVO_LIST, PDS_TYPE_JIANZU_LEVEL);
        jianzhuTypeList = getTypeList(Constants.PDSVO_LIST, PDS_TYPE_BUILD_TYPE);
        zhuangxiuList = getTypeList(Constants.PDSVO_LIST, PDS_TYPE_ZHUANGXIU_INFO);
        dianwuranList = getTypeList(Constants.PDSVO_LIST, PDS_TYPE_DIAN_WULAN);
        zaoyinList = getTypeList(Constants.PDSVO_LIST, PDS_TYPE_ZAOYIN_WULAN);
        qiweiList = getTypeList(Constants.PDSVO_LIST, PDS_TYPE_QIWEI_WULAN);
        guangwuranList = getTypeList(Constants.PDSVO_LIST, PDS_TYPE_GUAN_WULAN);
        wenhuaList = getTypeList(Constants.PDSVO_LIST, PDS_TYPE_WENHUA_YINXIAN);
        teshucList = getTypeList(Constants.PDSVO_LIST, PDS_TYPE_TESUCEN_TEDIAN);

        initSpinner(this, detailsMaintainSpinner, zfbyList, bean.getBuildCareinfo());
        initSpinner(this, detailsWallSpinner, wallList, bean.getExtWall());
        initSpinner(this, detailsTopSpinner, topList, bean.getTopFloorinfo());
        initSpinner(this, detailsScenerySpinner, yseOrnoList, bean.getTopFloorinfo());
        initSpinner(this, houseAttributeSpinner, houseAttrList, bean.getBuildAttr());
//        initSpinner(this, housebuyAttributeSpinner, houseAttrList, bean.getBuildAttr());
        initSpinner(this, structureAttributeSpinner, structureList, bean.getBuildStructure());
        initSpinner(this, attributeLevelSpinner, jianzhuList, bean.getBuildLevel());
        initSpinner(this, attributeTypeSpinner, jianzhuTypeList, bean.getBuildType());
        initSpinner(this, attributeZxcdSpinner, zhuangxiuList, bean.getZhuangxiuInfo());
        initSpinner(this, residentialCardSpinner, yseOrnoList, bean.getIsGarage());
        initSpinner(this, residentialStoreroomdSpinner, yseOrnoList, bean.getIsStoreroom());
        initSpinner(this, residentialElectricSpinner, yseOrnoList, bean.getIsEpowerSupp());
        initSpinner(this, residentialGasSpinner, yseOrnoList, bean.getIsGasSupp());
        initSpinner(this, propertyHeatSpinner, yseOrnoList, bean.getIsHeatSupp());
        initSpinner(this, jizhonggongnuanSpinner, yseOrnoList, bean.getIsJizhonggongnuan());
        initSpinner(this, securitySpinner, yseOrnoList, bean.getIsAnfang());
        initSpinner(this, residentialGgylSpinner, yseOrnoList, bean.getIsPubFunspace());
        initSpinner(this, residentialXfsslSpinner, yseOrnoList, bean.getIsFpd());
        initSpinner(this, residentialEchslSpinner, yseOrnoList, bean.getIsWaterSupp());
        initSpinner(this, residentialGlSpinner, yseOrnoList, bean.getIsGelou());
        initSpinner(this, residentialDtSpinner, yseOrnoList, bean.getIsLift());
        initSpinner(this, residentialDinuanSpinner, yseOrnoList, bean.getIsHeat());
        initSpinner(this, residentialzyktSpinner, yseOrnoList, bean.getIsZhongyangkongtiao());
        initSpinner(this, negativeRefuseSpinner, yseOrnoList, bean.getIsRubbstation());
        initSpinner(this, negativeMengjinSpinner, yseOrnoList, bean.getIsMengjin());
        initSpinner(this, negativeXinfengSpinner, yseOrnoList, bean.getIsXinfeng());
        initSpinner(this, negativeTikongSpinner, yseOrnoList, bean.getIsTikong());
        allAttributeList = bean.getAttributeLits();
        if (null != allAttributeList && 0 != allAttributeList.size()) {
            getAttributeTypeList(allAttributeList, PDS_TYPE_DIAN_WULAN, bolckNegativeElectricTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_ZAOYIN_WULAN, bolckNegativeSoundTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_QIWEI_WULAN, bolckNegativeGasTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_GUAN_WULAN, bolckNegativeLightTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_WENHUA_YINXIAN, attributewenhuaTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_TESUCEN_TEDIAN, negativeTescTv);
        } else {
            bolckNegativeElectricTv.setText("请选择");
            bolckNegativeSoundTv.setText("请选择");
            bolckNegativeGasTv.setText("请选择");
            bolckNegativeLightTv.setText("请选择");
            attributewenhuaTv.setText("请选择");
            negativeTescTv.setText("请选择");
        }
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

    public void upFilesInfo() {
        showLoading();
        Call<HttpResponse> callUpload = ApiUtil.upFilesInfo(upDimFileinfoVOList);
        callUpload.enqueue(new Callback<HttpResponse>() {//返回结果
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccessful()) {
                    JsonElement url = response.body().getResult();
                    List<DimFileinfoVO> bean = new Gson().fromJson(url, new TypeToken<List<DimFileinfoVO>>() {
                    }.getType());
                    if (null == bean || 0 == bean.size()) {
                        return;
                    }
                    blackDimFileinfoVOList = bean;
                    update();
                } else {
                    showToast("上传失败");
                    // Log.i(TAG, "上传失败啦");
                    closeLoading();
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                showToast("上传失败");
                //Log.i(TAG, "上传失败啦");
                closeLoading();
            }
        });

    }

    private void showGildPhoto(String type, String path) {
        LinearLayout mGallery = null;
        if (COMM_GATE_PHOTO.equals(type)) {
            mGallery = mGalleryGatehouse;
        } else if (COMM_ENTIRE_PHOTO.equals(type)) {
            mGallery = mGalleryEntire;
        }
        if (null == mGallery) {
            return;
        }
        View view = LayoutInflater.from(this).inflate(R.layout.photo_list_item, mGallery, false);
        ImageView img = view.findViewById(R.id.photo_preview);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) img.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        linearParams.setMarginStart(8);// 控件的宽强制设成30
        img.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        if (!TextUtils.isEmpty(path)) {
//            File file = new File(path);
//            if (file.exists()) {
            Glide.with(mContext).
                    load(path).
                    apply(new RequestOptions().
                            diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).override(120, 120).
                            transform(new CornerTransform(mContext, 15))).
                    into(img);
//            }
        }
        mGallery.addView(view);
    }
}
