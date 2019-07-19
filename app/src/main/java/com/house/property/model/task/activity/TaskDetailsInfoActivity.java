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
import com.house.property.model.task.entity.CommunityDetailsBean;
import com.house.property.model.task.entity.CommunityManagementBean;
import com.house.property.model.task.entity.DimFileinfoVO;
import com.house.property.model.task.entity.FileCoordinateInfoVO;
import com.house.property.model.task.entity.MessageEventVO;
import com.house.property.model.task.entity.MessageFileEventVO;
import com.house.property.model.task.entity.PdSVO;
import com.house.property.model.task.entity.PhotoBean;
import com.house.property.model.task.iface.ICommDetailsAddView;
import com.house.property.model.task.iface.ICommDetailsUpdateView;
import com.house.property.model.task.iface.ICommDetailsView;
import com.house.property.model.task.iface.IFileAddView;
import com.house.property.model.task.presenter.CommDetailsAddPresenter;
import com.house.property.model.task.presenter.CommDetailsPresenter;
import com.house.property.model.task.presenter.CommDetailsUpdatePresenter;
import com.house.property.model.task.upload.ApiUtil;
import com.house.property.utils.CornerTransform;
import com.house.property.utils.PhotoUtils;
import com.house.property.utils.SharedPreferencesUtil;
import com.house.property.utils.ToastUtils;
import com.house.property.utils.Utils;
import com.house.property.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;

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
import static com.house.property.base.Constants.ATTRIBUTE_TYPE_COMMUNICATION;
import static com.house.property.base.Constants.ATTRIBUTE_TYPE_COMM_MANAGEMENT;
import static com.house.property.base.Constants.COMM_FACILITIES_PHOTO;
import static com.house.property.base.Constants.COMM_GATEHOUSE_PHOTO;
import static com.house.property.base.Constants.COMM_HEALTH_PHOTO;
import static com.house.property.base.Constants.COMM_NEGATIVE_PHOTO;
import static com.house.property.base.Constants.COMM_PLACES_PHOTO;
import static com.house.property.base.Constants.COMM_RODE_PHOTO;
import static com.house.property.base.Constants.COMM_SCENERY_PHOTO;
import static com.house.property.base.Constants.PDSVO_LIST;
import static com.house.property.base.Constants.PDS_TYPE_BUILD_STRUCTURE;
import static com.house.property.base.Constants.PDS_TYPE_BUILD_TYPE;
import static com.house.property.base.Constants.PDS_TYPE_CHANQQUANNIANXIAN;
import static com.house.property.base.Constants.PDS_TYPE_COMMERCIAL_FACILITIE;
import static com.house.property.base.Constants.PDS_TYPE_EPOWER_SUPP;
import static com.house.property.base.Constants.PDS_TYPE_EVN_LEVEL;
import static com.house.property.base.Constants.PDS_TYPE_FIRE_FIGHTING;
import static com.house.property.base.Constants.PDS_TYPE_GREEN_BUILDING_KEJI;
import static com.house.property.base.Constants.PDS_TYPE_GREEN_BUILDING_STAR;
import static com.house.property.base.Constants.PDS_TYPE_HOUSE_ATTRIBUTE;
import static com.house.property.base.Constants.PDS_TYPE_INNER_EDU;
import static com.house.property.base.Constants.PDS_TYPE_INNER_PEITAOMIAOSHU;
import static com.house.property.base.Constants.PDS_TYPE_JINGGUAN;
import static com.house.property.base.Constants.PDS_TYPE_MANAGEMENT;
import static com.house.property.base.Constants.PDS_TYPE_ONE_TO_SERVEN;
import static com.house.property.base.Constants.PDS_TYPE_ROAD;
import static com.house.property.base.Constants.PDS_TYPE_ROAD_COMDITION;
import static com.house.property.base.Constants.PDS_TYPE_SAFETY_SYSTEM;
import static com.house.property.base.Constants.PDS_TYPE_SANITARY;
import static com.house.property.base.Constants.PDS_TYPE_TUDIXINGZHI;
import static com.house.property.base.Constants.PDS_TYPE_WATER_SUPP;
import static com.house.property.base.Constants.PDS_TYPE_WEIQIANG_FANGHU;
import static com.house.property.base.Constants.PDS_TYPE_WG_SECURITY;
import static com.house.property.base.Constants.PDS_TYPE_YN;
import static com.house.property.base.Constants.TAKE_PHOTO;
import static com.house.property.utils.TaskUtils.addUpdateAttributeList;
import static com.house.property.utils.TaskUtils.getAttributeTypeList;
import static com.house.property.utils.TaskUtils.getSpinnerKey;
import static com.house.property.utils.TaskUtils.getTypeList;
import static com.house.property.utils.TaskUtils.initSpinner;
import static com.house.property.utils.TaskUtils.reTextNull;
import static com.house.property.utils.TaskUtils.showNullToast;

/**
 * 小区详情列表
 */
public class TaskDetailsInfoActivity extends BaseFragmentActivity implements IFileAddView, AMapLocationListener, ICommDetailsView, ICommDetailsUpdateView, ICommDetailsAddView {

    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;


    @BindView(R.id.appearance_house_time_tv)
    TextView appearanceHouseTimeTv;

    @BindView(R.id.id_gallery_gatehouse)
    LinearLayout mGalleryGatehouse;
    @BindView(R.id.id_gallery_places)
    LinearLayout mGalleryPlaces;
    @BindView(R.id.id_gallery_facilities)
    LinearLayout mGalleryFacilities;
    @BindView(R.id.id_gallery_negative)
    LinearLayout mGalleryNegative;
    @BindView(R.id.id_gallery_daoluzhuangkuang)
    LinearLayout mGalleryDaoluzhuangkuang;
    @BindView(R.id.id_gallery_jingguan)
    LinearLayout mGalleryJingguan;
    @BindView(R.id.id_gallery_weisheng)
    LinearLayout mGalleryWeisheng;


    @BindView(R.id.bolck_residential_gas_spinner)
    Spinner bolckResidentialGasSpinner;
    @BindView(R.id.bolck_residential_heat_spinner)
    Spinner bolckResidentialHeatSpinner;
    @BindView(R.id.property_grade_spinner)
    Spinner propertyGradeSpinner;
    @BindView(R.id.property_card_spinner)
    Spinner propertyCardSpinner;
    @BindView(R.id.property_control_spinner)
    Spinner propertyControlSpinner;
    @BindView(R.id.property_face_spinner)
    Spinner propertyFacelSpinner;
    @BindView(R.id.property_talkback_spinner)
    Spinner propertyTalkbacklSpinner;
    @BindView(R.id.property_close_spinner)
    Spinner propertyCloseSpinner;
    @BindView(R.id.property_sanitation_spinner)
    Spinner propertySanitationSpinner;
    @BindView(R.id.bolck_residential_road_spinner)
    Spinner propertyRoadSpinner;
    //    @BindView(R.id.bolck_residential_xiaoshou_spinner)
//    Spinner xiaoshouStateSpinner;
    @BindView(R.id.bolck_residential_lvsejianzxingji_spinner)
    Spinner bolckResidentialLvsejianzxingjiSpinner;


    @BindView(R.id.house_task_name)
    TextView houseTaskNameTv;
    @BindView(R.id.house_task_adress)
    TextView houseTaskAdressTv;
    @BindView(R.id.sheshi_name_et)
    TextView appearanceLotEt;
    @BindView(R.id.appearance_exit_et)
    TextView appearanceExitEt;
    @BindView(R.id.attribute_trading_et)
    TextView attributeTradingEt;
    @BindView(R.id.attribute_totalland_et)
    TextView attributeroTallandEt;
    @BindView(R.id.attribute_totalhouse_et)
    TextView attributeTotalhouseEt;
    @BindView(R.id.attribute_apartment_et)
    TextView attributeApartmentEt;
    @BindView(R.id.property_adress_et)
    TextView propertyAdressEt;
    @BindView(R.id.appearance_renfangtcw_et)
    TextView appearanceRenfangtcwEt;
    @BindView(R.id.appearance_chanquantcw_et)
    TextView appearanceChanquantcwEt;
    @BindView(R.id.appearance_dishangtcw_et)
    TextView appearanceDishangtcwEt;
    @BindView(R.id.appearance_dishangtcw_shoufei_et)
    TextView appearanceDishangtcwShoufeiEt;
    @BindView(R.id.appearance_dixiatcw_et)
    TextView appearanceDixiatcwEt;
    @BindView(R.id.appearance_dixiatcw_shoufei_et)
    TextView appearanceDixiatcwShoufeiEt;
    @BindView(R.id.appearance_dixiatcw_youeryuan_et)
    TextView appearanceYoueryuanEt;
    @BindView(R.id.appearance_chuxin_time_tv)
    TextView appearanceChuxinTimeTv;


    @BindView(R.id.house_attribute_volume_tv)
    TextView attributeVolumeTv;
    @BindView(R.id.house_attribute_green_tv)
    TextView attributeGreenTv;
    @BindView(R.id.house_attribute_developers_tv)
    TextView attributeDevelopersTv;
    @BindView(R.id.appearance_house_attribute_communication_et)
    TextView attributeCommunicationEt;

    //多选
    @BindView(R.id.appearance_equipment_tv)
    TextView appearanceEquipmenTv;
    @BindView(R.id.appearance_education_tv)
    TextView appearanceEducationTv;
    @BindView(R.id.appearance_scenery_tv)
    TextView attributeSceneryTv;
    @BindView(R.id.appearance_house_attribute_tv)
    TextView attributeHouseTv;
    @BindView(R.id.appearance_enclosure_tv)
    TextView attributeEnclosureTv;
    @BindView(R.id.appearance_house_land_tv)
    TextView attributeHouselandTv;
    @BindView(R.id.appearance_attribute_buile_tv)
    TextView attributeBuileTv;
    @BindView(R.id.appearance_attribute_buile_type_tv)
    TextView attributeBuileTypeTv;
    @BindView(R.id.bolck_residential_water_tv)
    TextView bolckResidentialWaterTv;
    @BindView(R.id.bolck_residential_electric_tv)
    TextView bolckResidentialElectricTv;
    @BindView(R.id.appearance_security_tv)
    TextView appearancePropertySecurityTv;
    @BindView(R.id.appearance_house_chanquannianxian_tv)
    TextView appearancehouseChanquannianxianTv;


    @BindView(R.id.property_brand_et)
    TextView propertyBrandEt;
    @BindView(R.id.property_charging_et)
    TextView propertyChargingEt;
    @BindView(R.id.appearance_kejizhuzhai_type_tv)
    TextView appearanceKejizhuzhaiTv;
    @BindView(R.id.appearance_peijiansheshi_type_tv)
    TextView appearancePeijiansheshiTv;
    @BindView(R.id.appearance_xiaofangsheshi_type_tv)
    TextView appearanceXiaofangsheshiTv;
    @BindView(R.id.appearance_daoluzhuangkuang_type_tv)
    TextView appearanceDaoluzhuangkuangTv;
    @BindView(R.id.property_guanliyongfang_et)
    TextView propertyGuanliyongfangEt;
    @BindView(R.id.property_wuyeshoufei_et)
    TextView propertyWuyeshoufeiEt;
    @BindView(R.id.appearance_anquanzidonghua_tv)
    TextView appearanceAnquanzidonghuaTv;
    @BindView(R.id.appearance_guanli_tv)
    TextView appearanceGuanlizidonghuaTv;
    @BindView(R.id.appearance_weishenghuanjing_tv)
    TextView appearanceWeishenghuanjingTv;
    @BindView(R.id.comm_alias_et)
    TextView commAliasEt;

    private CommDetailsPresenter commDetailsPresenter = new CommDetailsPresenter(this, this);
    private CommDetailsUpdatePresenter commDetailsUpdatePresenter = new CommDetailsUpdatePresenter(this, this);
    private CommDetailsAddPresenter commDetailsAddPresenter = new CommDetailsAddPresenter(this, this);

    private RLoadingDialog mLoadingDialog;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION};
    private AMapLocationClient locationClientContinue;
    private double gaoDeLatitude = 0;
    private double gaoDeLongitude = 0;
    private double wgs84Latitude = 0;
    private double wgs84Longitude = 0;

    private String photoType;
    private String communityId;
    private String gisId;
    private String taskId;
    private List<PdSVO> yseOrnoList = new ArrayList<>();
    private List<PdSVO> managementLevelList = new ArrayList<>();
    private List<PdSVO> envLevelList = new ArrayList<>();
    private List<PdSVO> roadList = new ArrayList<>();
    private List<PdSVO> xiaoshouStateList = new ArrayList<>();
    private List<PdSVO> jianZhiXingJiList = new ArrayList<>();

    private List<AttributeVO> allAttributeList = new ArrayList<>();
    private List<PdSVO> equipmentTypeList = new ArrayList<>();
    private List<PdSVO> educationTypeList = new ArrayList<>();
    private List<PdSVO> sceneryTypeList = new ArrayList<>();
    private List<PdSVO> houseTypeList = new ArrayList<>();
    private List<PdSVO> enclosureList = new ArrayList<>();
    private List<PdSVO> houseLandList = new ArrayList<>();
    private List<PdSVO> buileList = new ArrayList<>();
    private List<PdSVO> buileTypeList = new ArrayList<>();
    private List<PdSVO> waterList = new ArrayList<>();
    private List<PdSVO> electricList = new ArrayList<>();
    private List<PdSVO> securityList = new ArrayList<>();
    private List<PdSVO> chanquanList = new ArrayList<>();
    private List<PdSVO> kejiZhuzhaiList = new ArrayList<>();
    private List<PdSVO> peijianShangyeList = new ArrayList<>();
    private List<PdSVO> xiaofangSheshiList = new ArrayList<>();
    private List<PdSVO> daoluZhuangkuangList = new ArrayList<>();
    private List<PdSVO> anquanZidonghuaList = new ArrayList<>();
    private List<PdSVO> guanliZidonghuaList = new ArrayList<>();
    private List<PdSVO> weishengHuanjingList = new ArrayList<>();

    private List<AttributeVO> allUpdateAttributeList = new ArrayList<>();
    private CommunityDetailsBean commBean;
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
        return R.layout.activity_task_details;
    }

    @Override
    protected void initBundleData() {
        presentersList.add(commDetailsPresenter);
        presentersList.add(commDetailsUpdatePresenter);
        presentersList.add(commDetailsAddPresenter);
        Intent intent = getIntent();
        if (null != intent) {
            communityId = intent.getStringExtra("communityId");
            gisId = intent.getStringExtra("gisId");
            taskId = intent.getStringExtra("taskId");
        }
    }

    @Override
    protected void initView() {
        // registeredEventBus();
        mLoadingDialog = new RLoadingDialog(this, true);
        titleTv.setText(R.string.house_task_details);
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


    @Override
    protected void initData() {
        commDetailsPresenter.getCommunityDetails(communityId);
    }


    //获取权限
    private void getPermission() {
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //已经打开权限
//            Toast.makeText(this, "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用与GPS权限", 1, permissions);
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

    @OnClick({R.id.appearance_scenery_tv, R.id.appearance_enclosure_tv, R.id.appearance_house_land_tv,
            R.id.appearance_equipment_tv, R.id.add_saomiao_negative, R.id.appearance_attribute_buile_tv,
            R.id.add_saomiao_facilities, R.id.add_saomiao_places, R.id.appearance_attribute_buile_type_tv,
            R.id.add_saomiao_gatehouse, R.id.page_back, R.id.bolck_residential_water_tv, R.id.appearance_house_chanquannianxian_tv,
            R.id.page_right_rl, R.id.appearance_education_tv, R.id.bolck_residential_electric_tv,
            R.id.appearance_house_attribute_tv, R.id.appearance_house_time_tv, R.id.appearance_security_tv,
            R.id.appearance_chuxin_time_tv,R.id.appearance_kejizhuzhai_type_tv,R.id.appearance_peijiansheshi_type_tv,
            R.id.appearance_xiaofangsheshi_type_tv,R.id.appearance_daoluzhuangkuang_type_tv,R.id.appearance_anquanzidonghua_tv,
            R.id.appearance_guanli_tv,R.id.appearance_weishenghuanjing_tv,R.id.add_daoluzhuangkuang_negative,
            R.id.add_jingguan_negative,R.id.add_weisheng_negative,
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appearance_equipment_tv:
                Utils.showMultiChoiceItems(this, equipmentTypeList, appearanceEquipmenTv);
                break;
            case R.id.appearance_education_tv:
                Utils.showMultiChoiceItems(this, educationTypeList, appearanceEducationTv);
                break;
            case R.id.appearance_house_attribute_tv:
                Utils.showMultiChoiceItems(this, houseTypeList, attributeHouseTv);
                break;
            case R.id.appearance_house_time_tv:
                Utils.showDatePickerDialog(this, appearanceHouseTimeTv);
                break;
            case R.id.appearance_scenery_tv:
                Utils.showMultiChoiceItems(this, sceneryTypeList, attributeSceneryTv);
                break;
            case R.id.appearance_enclosure_tv:
                Utils.showMultiChoiceItems(this, enclosureList, attributeEnclosureTv);
                break;
            case R.id.appearance_house_land_tv:
                Utils.showMultiChoiceItems(this, houseLandList, attributeHouselandTv);
                break;
            case R.id.appearance_attribute_buile_tv:
                Utils.showMultiChoiceItems(this, buileList, attributeBuileTv);
                break;
            case R.id.appearance_attribute_buile_type_tv:
                Utils.showMultiChoiceItems(this, buileTypeList, attributeBuileTypeTv);
                break;
            case R.id.bolck_residential_water_tv:
                Utils.showMultiChoiceItems(this, waterList, bolckResidentialWaterTv);
                break;
            case R.id.bolck_residential_electric_tv:
                Utils.showMultiChoiceItems(this, electricList, bolckResidentialElectricTv);
                break;
            case R.id.appearance_security_tv:
                Utils.showMultiChoiceItems(this, securityList, appearancePropertySecurityTv);
                break;
            case R.id.appearance_house_chanquannianxian_tv:
                Utils.showMultiChoiceItems(this, chanquanList, appearancehouseChanquannianxianTv);
                break;
            case R.id.page_right_rl:
                if (null == upDimFileinfoVOList || 0 == upDimFileinfoVOList.size()) {
                    update();
                    return;
                }
                upFilesInfo();
                // update();
                break;
            case R.id.page_back:
                finish();
                break;
            case R.id.add_saomiao_gatehouse:
                PhotoUtils.takePicture(this, TAKE_PHOTO);
                photoType = COMM_GATEHOUSE_PHOTO;
                break;
            case R.id.add_saomiao_places:
                PhotoUtils.takePicture(this, TAKE_PHOTO);
                photoType = COMM_PLACES_PHOTO;
                break;
            case R.id.add_saomiao_facilities:
                PhotoUtils.takePicture(this, TAKE_PHOTO);
                photoType = COMM_FACILITIES_PHOTO;
                break;
            case R.id.add_saomiao_negative:
                PhotoUtils.takePicture(this, TAKE_PHOTO);
                photoType = COMM_NEGATIVE_PHOTO;
                break;
            case R.id.appearance_chuxin_time_tv:
                Utils.showDatePickerDialog(this, appearanceChuxinTimeTv);
                break;
            case R.id.appearance_kejizhuzhai_type_tv:
                Utils.showMultiChoiceItems(this, kejiZhuzhaiList, appearanceKejizhuzhaiTv);
                break;
            case R.id.appearance_peijiansheshi_type_tv:
                Utils.showMultiChoiceItems(this, peijianShangyeList, appearancePeijiansheshiTv);
                break;
            case R.id.appearance_xiaofangsheshi_type_tv:
                Utils.showMultiChoiceItems(this, xiaofangSheshiList, appearanceXiaofangsheshiTv);
                break;
            case R.id.appearance_daoluzhuangkuang_type_tv:
                Utils.showMultiChoiceItems(this, daoluZhuangkuangList, appearanceDaoluzhuangkuangTv);
                break;
            case R.id.appearance_anquanzidonghua_tv:
                Utils.showMultiChoiceItems(this, anquanZidonghuaList, appearanceAnquanzidonghuaTv);
                break;
            case R.id.appearance_guanli_tv:
                Utils.showMultiChoiceItems(this, guanliZidonghuaList, appearanceGuanlizidonghuaTv);
                break;
            case R.id.appearance_weishenghuanjing_tv:
                Utils.showMultiChoiceItems(this, weishengHuanjingList, appearanceWeishenghuanjingTv);
                break;
            case R.id.add_daoluzhuangkuang_negative:
                PhotoUtils.takePicture(this, TAKE_PHOTO);
                photoType = COMM_RODE_PHOTO;
                break;
            case R.id.add_jingguan_negative:
                PhotoUtils.takePicture(this, TAKE_PHOTO);
                photoType = COMM_SCENERY_PHOTO;
                break;
            case R.id.add_weisheng_negative:
                PhotoUtils.takePicture(this, TAKE_PHOTO);
                photoType = COMM_HEALTH_PHOTO;
                break;
        }
    }

    private void update() {
        if (null != allUpdateAttributeList) {
            allUpdateAttributeList.clear();
        }
        String propertyRoadSpinnerStr = getSpinnerKey(propertyRoadSpinner.getSelectedItem().toString(), roadList);
        String bolckResidentialGasSpinnerStr = getSpinnerKey(bolckResidentialGasSpinner.getSelectedItem().toString(), yseOrnoList);
        String bolckResidentialHeatSpinnerStr = getSpinnerKey(bolckResidentialHeatSpinner.getSelectedItem().toString(), yseOrnoList);
        String propertyGradeSpinnerStr = getSpinnerKey(propertyGradeSpinner.getSelectedItem().toString(), managementLevelList);
        String propertyCardSpinnerStr = getSpinnerKey(propertyCardSpinner.getSelectedItem().toString(), yseOrnoList);
        String propertyControlSpinnerStr = getSpinnerKey(propertyControlSpinner.getSelectedItem().toString(), yseOrnoList);
        String propertyFacelSpinnerStr = getSpinnerKey(propertyFacelSpinner.getSelectedItem().toString(), yseOrnoList);
        String propertyTalkbacklSpinnerStr = getSpinnerKey(propertyTalkbacklSpinner.getSelectedItem().toString(), yseOrnoList);
        String propertyCloseSpinnerStr = getSpinnerKey(propertyCloseSpinner.getSelectedItem().toString(), yseOrnoList);
        String propertySanitationSpinnerStr = getSpinnerKey(propertySanitationSpinner.getSelectedItem().toString(), envLevelList);
        String bolckResidentialLvsejianzxingjiStr = getSpinnerKey(bolckResidentialLvsejianzxingjiSpinner.getSelectedItem().toString(), jianZhiXingJiList);

        //  String xiaoshouStateSpinnerStr= getSpinnerKey(xiaoshouStateSpinner.getSelectedItem().toString(), xiaoshouStateList);
        // String propertyChargingStr =  getSpinnerKey(propertyChargingEt.getSelectedItem().toString(), managementLevelList);

        String houseTaskNameStr = houseTaskNameTv.getText().toString();
        String houseTaskAdressStr = houseTaskAdressTv.getText().toString();
        String propertyChargingStr = propertyChargingEt.getText().toString();
        String appearanceLotStr = appearanceLotEt.getText().toString();
        String appearanceExitStr = appearanceExitEt.getText().toString();
        String attributeTradingStr = attributeTradingEt.getText().toString();
        String attributeroTallandStr = attributeroTallandEt.getText().toString();
        String attributeTotalhouseStr = attributeTotalhouseEt.getText().toString();
        String attributeApartmentStr = attributeApartmentEt.getText().toString();
        String attributeCommunicationStr = attributeCommunicationEt.getText().toString();
        String propertyBrandStr = propertyBrandEt.getText().toString();
        String propertyWuyeshoufeiStr = propertyWuyeshoufeiEt.getText().toString();
        String commAliasStr = commAliasEt.getText().toString();

        String appearanceRenfangtcwStr = appearanceRenfangtcwEt.getText().toString();
        String appearanceChanquantcwStr = appearanceChanquantcwEt.getText().toString();
        String appearanceDishangtcwStr = appearanceDishangtcwEt.getText().toString();
        String appearanceDishangtcwShoufeiStr = appearanceDishangtcwShoufeiEt.getText().toString();
        String appearanceDixiatcwStr = appearanceDixiatcwEt.getText().toString();
        String appearanceDixiatcwShoufeiStr = appearanceDixiatcwShoufeiEt.getText().toString();
        String appearanceYoueryuanStr = appearanceYoueryuanEt.getText().toString();
        String appearanceChuxinTimeStr = appearanceChuxinTimeTv.getText().toString();

        String propertyAdressStr = propertyAdressEt.getText().toString();
        String appearanceHouseTimeStr = appearanceHouseTimeTv.getText().toString();
        String propertyGuanliyongfangStr =  propertyGuanliyongfangEt.getText().toString();

        String appearanceEquipmenStr = appearanceEquipmenTv.getText().toString();


        addUpdateAttributeList(allUpdateAttributeList, equipmentTypeList, appearanceEquipmenStr, commBean.getId(), ATTRIBUTE_TYPE_COMMUNICATION);
        String appearanceEducationStr = appearanceEducationTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, educationTypeList, appearanceEducationStr, commBean.getId(), ATTRIBUTE_TYPE_COMMUNICATION);
        String attributeEnclosureStr = attributeEnclosureTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, enclosureList, attributeEnclosureStr, commBean.getId(), ATTRIBUTE_TYPE_COMMUNICATION);
        String attributeSceneryStr = attributeSceneryTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, sceneryTypeList, attributeSceneryStr, commBean.getId(), ATTRIBUTE_TYPE_COMMUNICATION);
        String attributeHouseStr = attributeHouseTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, houseTypeList, attributeHouseStr, commBean.getId(), ATTRIBUTE_TYPE_COMMUNICATION);
        String attributeHouselandStr = attributeHouselandTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, houseLandList, attributeHouselandStr, commBean.getId(), ATTRIBUTE_TYPE_COMMUNICATION);
        String attributeBuileStr = attributeBuileTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, buileList, attributeBuileStr, commBean.getId(), ATTRIBUTE_TYPE_COMMUNICATION);
        String attributeBuileTypeStr = attributeBuileTypeTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, buileTypeList, attributeBuileTypeStr, commBean.getId(), ATTRIBUTE_TYPE_COMMUNICATION);
        String bolckResidentialWaterStr = bolckResidentialWaterTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, waterList, bolckResidentialWaterStr, commBean.getId(), ATTRIBUTE_TYPE_COMMUNICATION);
        String bolckResidentialElectricStr = bolckResidentialElectricTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, electricList, bolckResidentialElectricStr, commBean.getId(), ATTRIBUTE_TYPE_COMMUNICATION);
        String appearancePropertySecurityStr = appearancePropertySecurityTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, securityList, appearancePropertySecurityStr, commBean.getId(), ATTRIBUTE_TYPE_COMM_MANAGEMENT);
        String appearancehouseChanquannianxianTvStr = appearancehouseChanquannianxianTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, chanquanList, appearancehouseChanquannianxianTvStr, commBean.getId(), ATTRIBUTE_TYPE_COMMUNICATION);
        String appearanceKejizhuzhaiTvStr = appearanceKejizhuzhaiTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, kejiZhuzhaiList, appearanceKejizhuzhaiTvStr, commBean.getId(), ATTRIBUTE_TYPE_COMMUNICATION);
        String appearancePeijiansheshiTvStr = appearancePeijiansheshiTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, peijianShangyeList, appearancePeijiansheshiTvStr, commBean.getId(), ATTRIBUTE_TYPE_COMMUNICATION);
        String appearanceXiaofangsheshiTvStr = appearanceXiaofangsheshiTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, xiaofangSheshiList, appearanceXiaofangsheshiTvStr, commBean.getId(), ATTRIBUTE_TYPE_COMMUNICATION);
        String appearanceDaoluzhuangkuangTvStr = appearanceDaoluzhuangkuangTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, daoluZhuangkuangList, appearanceDaoluzhuangkuangTvStr, commBean.getId(), ATTRIBUTE_TYPE_COMMUNICATION);
        String appearanceAnquanzidonghuaTvStr = appearanceAnquanzidonghuaTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, anquanZidonghuaList, appearanceAnquanzidonghuaTvStr, commBean.getId(), ATTRIBUTE_TYPE_COMM_MANAGEMENT);
        String appearanceGuanlizidonghuaTvStr = appearanceGuanlizidonghuaTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, guanliZidonghuaList, appearanceGuanlizidonghuaTvStr, commBean.getId(), ATTRIBUTE_TYPE_COMM_MANAGEMENT);
        String appearanceWeishenghuanjingTvStr = appearanceWeishenghuanjingTv.getText().toString();
        addUpdateAttributeList(allUpdateAttributeList, weishengHuanjingList, appearanceWeishenghuanjingTvStr, commBean.getId(), ATTRIBUTE_TYPE_COMM_MANAGEMENT);


        if (showNullToast(houseTaskNameStr, getResources().getString(R.string.house_task_area_name))) {
            return;
        }
        if (showNullToast(houseTaskAdressStr, getResources().getString(R.string.house_task_area_adress))) {
            return;
        }

//        if (showNullToast(appearanceLotStr, getResources().getString(R.string.house_details_appearance_lot))) {
//            return;
//        }
//        if (showNullToast(appearanceExitStr, getResources().getString(R.string.house_details_appearance_exit))) {
//            return;
//        }
//        if (showNullToast(appearanceEducationStr, getResources().getString(R.string.house_details_appearance_education))) {
//            return;
//        }
//        if (showNullToast(attributeEnclosureStr, getResources().getString(R.string.house_details_appearance_enclosure))) {
//            return;
//        }
//        if (showNullToast(attributeSceneryStr, getResources().getString(R.string.house_details_appearance_scenery))) {
//            return;
//        }
//        if (showNullToast(xiaoshouStateSpinnerStr, getResources().getString(R.string.house_task_bolck_residential_xiaoshou))) {
//            return;
//        }
//        if (showNullToast(attributeHouseStr, getResources().getString(R.string.house_details_appearance_house_attribute))) {
//            return;
//        }
//        if (showNullToast(appearancehouseChanquannianxianTvStr, getResources().getString(R.string.house_details_appearance_house_attribute_chanquannianxian))) {
//            return;
//        }
//        if (showNullToast(attributeHouselandStr, getResources().getString(R.string.house_details_appearance_house_attribute_land))) {
//            return;
//        }
//        if (showNullToast(attributeBuileStr, getResources().getString(R.string.house_details_appearance_house_attribute_buile_structure))) {
//            return;
//        }
//        if (showNullToast(attributeBuileTypeStr, getResources().getString(R.string.house_details_appearance_house_attribute_buile_type))) {
//            return;
//        }
//        if (showNullToast(attributeTradingStr, getResources().getString(R.string.house_details_appearance_house_attribute_trading))) {
//            return;
//        }
//        if (showNullToast(attributeroTallandStr, getResources().getString(R.string.house_details_appearance_house_attribute_totalland))) {
//            return;
//        }
//        if (showNullToast(attributeTotalhouseStr, getResources().getString(R.string.house_details_appearance_house_attribute_totalhouse))) {
//            return;
//        }
//        if (showNullToast(attributeApartmentStr, getResources().getString(R.string.house_details_appearance_house_attribute_apartment))) {
//            return;
//        }
//        if (showNullToast(propertyRoadSpinnerStr, getResources().getString(R.string.house_details_appearance_house_attribute_road))) {
//            return;
//        }
//        if ("请选择".equals(appearanceHouseTimeStr)) {
//            ToastUtils.showToast(this, "请选择建筑年代");
//            return;
//        }

//        if (showNullToast(attributeCommunicationStr, getResources().getString(R.string.house_details_appearance_house_attribute_communication))) {
//            return;
//        }
//        if (showNullToast(bolckResidentialHeatSpinnerStr, getResources().getString(R.string.house_task_bolck_residential_heat))) {
//            return;
//        }
//        if (showNullToast(bolckResidentialGasSpinnerStr, getResources().getString(R.string.house_task_bolck_residential_gas))) {
//            return;
//        }
//        if (showNullToast(bolckResidentialElectricStr, getResources().getString(R.string.house_task_bolck_residential_electric))) {
//            return;
//        }
//        if (showNullToast(bolckResidentialWaterStr, getResources().getString(R.string.house_task_bolck_residential_water))) {
//            return;
//        }

        if (showNullToast(propertyBrandStr, getResources().getString(R.string.house_details_appearance_property_brand))) {
            return;
        }
//        if (showNullToast(propertyChargingStr, getResources().getString(R.string.house_details_appearance_property_charging))) {
//            return;
//        }
//        if (showNullToast(propertyGradeSpinnerStr, getResources().getString(R.string.house_details_appearance_property_grade))) {
//            return;
//        }
//        if (showNullToast(propertyCardSpinnerStr, getResources().getString(R.string.house_details_appearance_property_card))) {
//            return;
//        }
//        if (showNullToast(propertyControlSpinnerStr, getResources().getString(R.string.house_details_appearance_property_control))) {
//            return;
//        }
//        if (showNullToast(propertyFacelSpinnerStr, getResources().getString(R.string.house_details_appearance_property_face))) {
//            return;
//        }
//        if (showNullToast(propertyTalkbacklSpinnerStr, getResources().getString(R.string.house_details_appearance_property_talkback))) {
//            return;
//        }
//        if (showNullToast(propertyCloseSpinnerStr, getResources().getString(R.string.house_details_appearance_property_close))) {
//            return;
//        }
//        if (showNullToast(propertySanitationSpinnerStr, getResources().getString(R.string.house_details_appearance_property_sanitation))) {
//            return;
//        }

//        if (showNullToast(appearancePropertySecurityStr, getResources().getString(R.string.house_details_appearance_property_security))) {
//            return;
//        }
        CommunityDetailsBean communityDetailsBean = commBean;
        communityDetailsBean.setCommName(houseTaskNameStr);
        communityDetailsBean.setCommAddress(houseTaskAdressStr);
        communityDetailsBean.setId(commBean.getId());
        communityDetailsBean.setAttributeLits(allUpdateAttributeList);
        communityDetailsBean.setTingcheweiCnt(appearanceLotStr);
        communityDetailsBean.setInOutCarparkCnt(appearanceExitStr);
        communityDetailsBean.setShangquanname(attributeTradingStr);
        communityDetailsBean.setZongmianji(attributeroTallandStr);
        communityDetailsBean.setHouseCnt(attributeTotalhouseStr);
        communityDetailsBean.setMainHuxing(attributeApartmentStr);
        communityDetailsBean.setIcdDev(attributeCommunicationStr);
        communityDetailsBean.setJianzhuniandai(appearanceHouseTimeStr);
        communityDetailsBean.setLumianQingkuang(propertyRoadSpinnerStr);
        communityDetailsBean.setGasSupp(bolckResidentialGasSpinnerStr);
        communityDetailsBean.setHeatSupp(bolckResidentialHeatSpinnerStr);
        communityDetailsBean.setDefenseCarsTotal(appearanceRenfangtcwStr);
        communityDetailsBean.setPropertyCarsTotal(appearanceChanquantcwStr);
        communityDetailsBean.setLandParkingCarsTotal(appearanceDishangtcwStr);
        communityDetailsBean.setLandParkingCarsCharge(appearanceDishangtcwShoufeiStr);
        communityDetailsBean.setUndergroundCarsTotal(appearanceDixiatcwStr);
        communityDetailsBean.setUndergroundCarsCharge(appearanceDixiatcwShoufeiStr);
        communityDetailsBean.setCommercialNurserySchool(appearanceYoueryuanStr);
        communityDetailsBean.setNewDistrict(appearanceChuxinTimeStr);
        communityDetailsBean.setGreenBuildingStar(bolckResidentialLvsejianzxingjiStr);
        communityDetailsBean.setGreenBuildingKeji(appearanceKejizhuzhaiTvStr);
        communityDetailsBean.setCommercialFacilities(appearancePeijiansheshiTvStr);
        communityDetailsBean.setFireFightingFacilities(appearanceXiaofangsheshiTvStr);
        communityDetailsBean.setRoadComditionSituation(appearanceDaoluzhuangkuangTvStr);
        communityDetailsBean.setManagementId(commBean.getManagementId());
        communityDetailsBean.setGisId(gisId);
        communityDetailsBean.setChanqquannianxian(appearancehouseChanquannianxianTvStr);
        communityDetailsBean.setCommPhotos(blackDimFileinfoVOList);
        communityDetailsBean.setCommOthername(commAliasStr);

        CommunityManagementBean managementObj = commBean.getManagementObj();
        if (null == managementObj) {
            managementObj = new CommunityManagementBean();
        }
        managementObj.setManagementName(propertyBrandStr);
        managementObj.setFeeStd(propertyChargingStr);
        managementObj.setManagementAddr(propertyAdressStr);
        managementObj.setManagementLevel(propertyGradeSpinnerStr);
        managementObj.setIsEcarControl(propertyCardSpinnerStr);
        managementObj.setIsMenjin(propertyControlSpinnerStr);
        managementObj.setIsFaceControl(propertyFacelSpinnerStr);
        managementObj.setIsVisitorInterview(propertyTalkbacklSpinnerStr);
        managementObj.setIsFengbi(propertyCloseSpinnerStr);
        managementObj.setEnvLevel(propertySanitationSpinnerStr);
        managementObj.setId(communityDetailsBean.getManagementId());
        managementObj.setPropertyManagementHousing(propertyGuanliyongfangStr);
        managementObj.setPropertyCharges(propertyWuyeshoufeiStr);
        managementObj.setSafetyAutomationSystem(appearanceAnquanzidonghuaTvStr);
        managementObj.setManagementAutomation(appearanceGuanlizidonghuaTvStr);
        managementObj.setSanitaryEnvironment(appearanceWeishenghuanjingTvStr);
        communityDetailsBean.setManagementObj(managementObj);

        if (null == commBean || TextUtils.isEmpty(commBean.getId())) {
            commDetailsAddPresenter.addCommunityDetails(communityDetailsBean);
            commBean = communityDetailsBean;
        } else {
            commDetailsUpdatePresenter.updateCommunityDetails(communityDetailsBean);
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
                        photoPath = Constants.CAMERA_SAVE_PATH;
                    } else {
                        photoPath = PhotoUtils.getOutputMediaFileUri(this).getEncodedPath();
                    }
                }
                PhotoBean photoBean = new PhotoBean();
                photoBean.setId(communityId);
                photoBean.setGaoDeLatitude(gaoDeLatitude);
                photoBean.setGaoDeLongitude(gaoDeLongitude);
                photoBean.setWgs84Latitude(wgs84Latitude);
                photoBean.setWgs84Longitude(wgs84Longitude);
                photoBean.setPath(photoPath);
                photoBean.setType(photoType);
                showPhoto(photoBean);
                break;

            case PRIVATE_CODE:
                break;
        }

    }

    private void showPhoto(PhotoBean photoBean) {
        String type = photoBean.getType();
        String path = photoBean.getPath();
        String gaoDeLatitude = String.valueOf(photoBean.getGaoDeLatitude());
        String gaoDeLongitude = String.valueOf(photoBean.getGaoDeLongitude());
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
                        dimFileinfoVO.setRelaSrctab("DIM_COMMUNITY");
                        dimFileinfoVO.setFileType(type);
                        dimFileinfoVO.setFileBtype("photo");
                        dimFileinfoVO.setFileSdcardPath(file.getPath());
                        dimFileinfoVO.setFileName(file.getName());
                        FileCoordinateInfoVO fileCoordinateInfo = new FileCoordinateInfoVO();
                        fileCoordinateInfo.setGaodeLatitude(gaoDeLatitude);
                        fileCoordinateInfo.setGaodeLongitude(gaoDeLongitude);
                        fileCoordinateInfo.setWgs84Latitude(wgs84Latitude);
                        fileCoordinateInfo.setWgs84Longitude(wgs84Longitude);
                        String name = (String) SharedPreferencesUtil.getData("name", "");
                        fileCoordinateInfo.setUserName(name);
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

    private void showGildPhoto(String type, String path) {
        LinearLayout mGallery = null;
        if (COMM_GATEHOUSE_PHOTO.equals(type)) {
            mGallery = mGalleryGatehouse;
        } else if (COMM_PLACES_PHOTO.equals(type)) {
            mGallery = mGalleryPlaces;
        } else if (COMM_FACILITIES_PHOTO.equals(type)) {
            mGallery = mGalleryFacilities;
        } else if (COMM_NEGATIVE_PHOTO.equals(type)) {
            mGallery = mGalleryNegative;
        }else if (COMM_RODE_PHOTO.equals(type)) {
            mGallery = mGalleryDaoluzhuangkuang;
        }else if (COMM_SCENERY_PHOTO.equals(type)) {
            mGallery = mGalleryJingguan;
        }else if (COMM_HEALTH_PHOTO.equals(type)) {
            mGallery = mGalleryWeisheng;
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


    @Override
    public void showResult(CommunityDetailsBean bean) {
        if (null == bean) {
            return;
        }
        this.commBean = bean;
        houseTaskNameTv.setText(reTextNull(bean.getCommName()));
        houseTaskAdressTv.setText(reTextNull(bean.getCommAddress()));
        //   appearanceEquipmenTv.setText(commBean.getCommAddress());
        appearanceLotEt.setText(reTextNull(bean.getTingcheweiCnt()));
        appearanceExitEt.setText(reTextNull(bean.getInOutCarparkCnt()));
        attributeTradingEt.setText(reTextNull(bean.getShangquanname()));
        attributeroTallandEt.setText(reTextNull(bean.getZongmianji()));
        attributeTotalhouseEt.setText(reTextNull(bean.getHouseCnt()));
        attributeApartmentEt.setText(reTextNull(bean.getMainHuxing()));
        attributeVolumeTv.setText(reTextNull(bean.getRongjilv()));
        attributeGreenTv.setText(reTextNull(bean.getLvhualv()));
        attributeDevelopersTv.setText(reTextNull(bean.getKaifashang()));
        attributeCommunicationEt.setText(reTextNull(bean.getIcdDev()));
        appearanceRenfangtcwEt.setText(reTextNull(bean.getDefenseCarsTotal()));
        appearanceChanquantcwEt.setText(reTextNull(bean.getPropertyCarsTotal()));
        appearanceDishangtcwEt.setText(reTextNull(bean.getLandParkingCarsTotal()));
        appearanceDishangtcwShoufeiEt.setText(reTextNull(bean.getLandParkingCarsCharge()));
        appearanceDixiatcwEt.setText(reTextNull(bean.getUndergroundCarsTotal()));
        appearanceDixiatcwShoufeiEt.setText(reTextNull(bean.getUndergroundCarsCharge()));
        appearanceYoueryuanEt.setText(reTextNull(bean.getCommercialNurserySchool()));
        commAliasEt.setText(reTextNull(bean.getCommOthername()));
        if (!TextUtils.isEmpty(bean.getJianzhuniandai())) {
            appearanceHouseTimeTv.setText(bean.getJianzhuniandai());
        }
        if (!TextUtils.isEmpty(bean.getNewDistrict())) {
            appearanceChuxinTimeTv.setText(bean.getNewDistrict());
        }
        yseOrnoList = getTypeList(PDSVO_LIST, PDS_TYPE_YN);
//        initSpinner(bolckResidentialWaterSpinner, yseOrnoList, commBean.getWaterSupp());
//        initSpinner(bolckResidentialElectricSpinner, yseOrnoList, commBean.getEpowerSupp());
        initSpinner(this, bolckResidentialGasSpinner, yseOrnoList, bean.getGasSupp());
        initSpinner(this, bolckResidentialHeatSpinner, yseOrnoList, bean.getHeatSupp());
        roadList = getTypeList(PDSVO_LIST, PDS_TYPE_ROAD);
        initSpinner(this, propertyRoadSpinner, roadList, bean.getLumianQingkuang());
        jianZhiXingJiList = getTypeList(PDSVO_LIST, PDS_TYPE_GREEN_BUILDING_STAR);
        initSpinner(this, bolckResidentialLvsejianzxingjiSpinner, jianZhiXingJiList, bean.getGreenBuildingStar());

        equipmentTypeList = getTypeList(PDSVO_LIST, PDS_TYPE_INNER_PEITAOMIAOSHU);
        educationTypeList = getTypeList(PDSVO_LIST, PDS_TYPE_INNER_EDU);
        sceneryTypeList = getTypeList(PDSVO_LIST, PDS_TYPE_JINGGUAN);
        houseTypeList = getTypeList(PDSVO_LIST, PDS_TYPE_HOUSE_ATTRIBUTE);
        enclosureList = getTypeList(PDSVO_LIST, PDS_TYPE_WEIQIANG_FANGHU);
        houseLandList = getTypeList(PDSVO_LIST, PDS_TYPE_TUDIXINGZHI);
        buileList = getTypeList(PDSVO_LIST, PDS_TYPE_BUILD_STRUCTURE);
        buileTypeList = getTypeList(PDSVO_LIST, PDS_TYPE_BUILD_TYPE);
        waterList = getTypeList(PDSVO_LIST, PDS_TYPE_WATER_SUPP);
        electricList = getTypeList(PDSVO_LIST, PDS_TYPE_EPOWER_SUPP);
        chanquanList = getTypeList(PDSVO_LIST, PDS_TYPE_CHANQQUANNIANXIAN);
        kejiZhuzhaiList = getTypeList(PDSVO_LIST, PDS_TYPE_GREEN_BUILDING_KEJI);
        peijianShangyeList= getTypeList(PDSVO_LIST, PDS_TYPE_COMMERCIAL_FACILITIE);
        xiaofangSheshiList= getTypeList(PDSVO_LIST, PDS_TYPE_FIRE_FIGHTING);
        daoluZhuangkuangList= getTypeList(PDSVO_LIST, PDS_TYPE_ROAD_COMDITION);
        anquanZidonghuaList= getTypeList(PDSVO_LIST, PDS_TYPE_SAFETY_SYSTEM);
        guanliZidonghuaList= getTypeList(PDSVO_LIST, PDS_TYPE_MANAGEMENT);
        weishengHuanjingList= getTypeList(PDSVO_LIST, PDS_TYPE_SANITARY);
        CommunityManagementBean communityManagementBean = bean.getManagementObj();
        if (null != communityManagementBean) {
            propertyBrandEt.setText(reTextNull(communityManagementBean.getManagementName()));
            propertyChargingEt.setText(reTextNull(communityManagementBean.getFeeStd()));
            propertyAdressEt.setText(reTextNull(communityManagementBean.getManagementAddr()));
            propertyGuanliyongfangEt.setText(reTextNull(communityManagementBean.getPropertyManagementHousing()));
            propertyWuyeshoufeiEt.setText(reTextNull(communityManagementBean.getPropertyCharges()));
        }
        managementLevelList = getTypeList(PDSVO_LIST, PDS_TYPE_ONE_TO_SERVEN);
        envLevelList = getTypeList(PDSVO_LIST, PDS_TYPE_EVN_LEVEL);
        securityList = getTypeList(PDSVO_LIST, PDS_TYPE_WG_SECURITY);
        initSpinner(this, propertyGradeSpinner, managementLevelList, communityManagementBean == null ? "" : communityManagementBean.getManagementLevel());
        initSpinner(this, propertyCardSpinner, yseOrnoList, communityManagementBean == null ? "" : communityManagementBean.getIsEcarControl());
        initSpinner(this, propertyControlSpinner, yseOrnoList, communityManagementBean == null ? "" : communityManagementBean.getIsMenjin());
        initSpinner(this, propertyFacelSpinner, yseOrnoList, communityManagementBean == null ? "" : communityManagementBean.getIsFaceControl());
        initSpinner(this, propertyTalkbacklSpinner, yseOrnoList, communityManagementBean == null ? "" : communityManagementBean.getIsVisitorInterview());
        initSpinner(this, propertyCloseSpinner, yseOrnoList, communityManagementBean == null ? "" : communityManagementBean.getIsFengbi());
        initSpinner(this, propertySanitationSpinner, envLevelList, communityManagementBean == null ? "" : communityManagementBean.getEnvLevel());
        //   initSpinner(this, propertyChargingEt, managementLevelList, communityManagementBean == null ? "" : communityManagementBean.getFeeStd());

        allAttributeList = bean.getAttributeLits();
        if (null != allAttributeList && 0 != allAttributeList.size()) {
            getAttributeTypeList(allAttributeList, PDS_TYPE_INNER_PEITAOMIAOSHU, appearanceEquipmenTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_INNER_EDU, appearanceEducationTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_JINGGUAN, attributeSceneryTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_HOUSE_ATTRIBUTE, attributeHouseTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_WEIQIANG_FANGHU, attributeEnclosureTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_TUDIXINGZHI, attributeHouselandTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_BUILD_STRUCTURE, attributeBuileTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_BUILD_TYPE, attributeBuileTypeTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_WATER_SUPP, bolckResidentialWaterTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_EPOWER_SUPP, bolckResidentialElectricTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_WG_SECURITY, appearancePropertySecurityTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_CHANQQUANNIANXIAN, appearancehouseChanquannianxianTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_GREEN_BUILDING_KEJI, appearanceKejizhuzhaiTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_COMMERCIAL_FACILITIE, appearancePeijiansheshiTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_FIRE_FIGHTING, appearanceXiaofangsheshiTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_ROAD_COMDITION, appearanceDaoluzhuangkuangTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_MANAGEMENT, appearanceGuanlizidonghuaTv);
            getAttributeTypeList(allAttributeList, PDS_TYPE_SANITARY, appearanceWeishenghuanjingTv);

            getAttributeTypeList(allAttributeList, PDS_TYPE_SAFETY_SYSTEM, appearanceAnquanzidonghuaTv);

        }

        List<DimFileinfoVO> commPhotos = bean.getCommPhotos();
        if (null == commPhotos || 0 == commPhotos.size()) {
            return;
        }
        for (DimFileinfoVO dimFileinfoVO : commPhotos) {
            showGildPhoto(dimFileinfoVO.getFileType(), dimFileinfoVO.getFilePath());
        }
    }

    @Override
    public void showResult(MessageEventVO bean) {
        if (null == bean) {
            return;
        }
        ToastUtils.showToast(this, bean.getResult());
        EventBus.getDefault().postSticky(bean);
        finish();
    }

    @Override
    public void showResult(MessageFileEventVO bean) {
        if (null == bean) {
            return;
        }
        ToastUtils.showToast(this, bean.getResult());
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


}
