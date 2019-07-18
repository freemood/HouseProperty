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
import android.widget.AdapterView;
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
import com.house.property.adapter.MySpinnerAdapter;
import com.house.property.base.BaseFragmentActivity;
import com.house.property.base.Constants;
import com.house.property.http.retrofit.HttpResponse;
import com.house.property.model.task.entity.DimFileinfoVO;
import com.house.property.model.task.entity.DimPOIVO;
import com.house.property.model.task.entity.FileCoordinateInfoVO;
import com.house.property.model.task.entity.MessageEventVO;
import com.house.property.model.task.entity.PhotoBean;
import com.house.property.model.task.entity.PoiTypeBean;
import com.house.property.model.task.iface.IPoiAddPhotoView;
import com.house.property.model.task.iface.IPoiAddView;
import com.house.property.model.task.presenter.PoiAddPhotoPresenter;
import com.house.property.model.task.presenter.PoiAddPresenter;
import com.house.property.model.task.upload.ApiUtil;
import com.house.property.utils.CornerTransform;
import com.house.property.utils.PhotoUtils;
import com.house.property.utils.SharedPreferencesUtil;
import com.house.property.utils.ToastUtils;
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
import static com.house.property.base.Constants.COMM_FACILITIES_PHOTO;
import static com.house.property.base.Constants.COMM_GATEHOUSE_PHOTO;
import static com.house.property.base.Constants.COMM_NEGATIVE_PHOTO;
import static com.house.property.base.Constants.COMM_PLACES_PHOTO;
import static com.house.property.base.Constants.COMM_POI_PHOTO;
import static com.house.property.base.Constants.TAKE_PHOTO;
import static com.house.property.utils.TaskUtils.reTextNull;
import static com.house.property.utils.TaskUtils.showNullToast;
import static com.house.property.utils.Utils.removeDuplicate;

public class TaskAddPOIActivity extends BaseFragmentActivity implements IPoiAddPhotoView,IPoiAddView, AMapLocationListener {

    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;
    @BindView(R.id.page_title)
    TextView pageTitleTv;

    @BindView(R.id.id_gallery_poi)
    LinearLayout mGalleryPoi;

    @BindView(R.id.house_task_name)
    TextView houseTaskNameTv;

    @BindView(R.id.sheshi_name_et)
    TextView sheshiNameEt;
    @BindView(R.id.sheshi_adress_et)
    TextView sheshiadressEt;
    @BindView(R.id.sheshi_sheng_et)
    TextView sheshishengEt;
    @BindView(R.id.sheshi_city_et)
    TextView sheshicityEt;
    @BindView(R.id.sheshi_qu_et)
    TextView sheshiQuEt;
    @BindView(R.id.sheshi_level_one_spinner)
    Spinner sheLevelOneSpinner;
    @BindView(R.id.sheshi_level_two_spinner)
    Spinner sheLevelTwoSpinner;
    @BindView(R.id.sheshi_level_three_spinner)
    Spinner sheLevelThreeSpinner;
    private String photoType;
    private RLoadingDialog mLoadingDialog;
    private DimPOIVO dimPOIVO;
    private List<PoiTypeBean> poiTypeBeanList = new ArrayList<>();
    private List<String> listTwo = new ArrayList<>();
    private List<String> listThree = new ArrayList<>();

    private PoiAddPresenter poiAddPresenter = new PoiAddPresenter(this, this);
    private PoiAddPhotoPresenter poiAddPhotoPresenter = new PoiAddPhotoPresenter(this, this);
    private List<DimFileinfoVO> upDimFileinfoVOList = new ArrayList<>();
    private List<DimFileinfoVO> blackDimFileinfoVOList = new ArrayList<>();
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION};
    private AMapLocationClient locationClientContinue;
    private double gaoDeLatitude = 0;
    private double gaoDeLongitude = 0;
    private double wgs84Latitude = 0;
    private double wgs84Longitude = 0;
    private String poiId;
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

    // 获取位置管理服务
    private LocationManager locationManager;
    private Location location;
    private static final int PRIVATE_CODE = 1315;//开启GPS权限

    @Override
    protected int getContentViewId() {
        return R.layout.activity_task_add_poi;
    }

    @Override
    protected void initBundleData() {
        presentersList.add(poiAddPresenter);
        presentersList.add(poiAddPhotoPresenter);

    }

    @Override
    protected void initView() {
        registeredEventBus();
        mLoadingDialog = new RLoadingDialog(this, true);
        pageRightRl.setVisibility(View.VISIBLE);
        pageTitleTv.setText("设施补录");
        pageRightTv.setText(getResources().getString(R.string.btn_save));
        sheshishengEt.setText("江苏省");
        sheshicityEt.setText("扬州市");
        showGPSContacts();
        startGaoDeGps();
    }

    @Override
    protected void initData() {
        initPoiTypes();
        initSpinnerOne();
        initSpinnerTwo();
        initSpinnerThree();
        listTwo.add("请选择");
        listThree.add("请选择");
    }

    private void initPoiTypes() {
        String[] poiTypes = getResources().getStringArray(R.array.sheshi_level_all_array);
        int length = poiTypes.length;
        for (int i = 0; i < length; i++) {
            PoiTypeBean poiTypeBean = new PoiTypeBean();
            String[] types = poiTypes[i].split(";");
            poiTypeBean.setTypeCode(types[0]);
            poiTypeBean.setTypeName(types[1]);
            String[] typeNames = types[1].split(",");
            poiTypeBean.setTypeLevelOne(typeNames[0]);
            poiTypeBean.setTypeLevelTwo(typeNames[1]);
            poiTypeBean.setTypeLevelThree(typeNames[2]);
            poiTypeBeanList.add(poiTypeBean);
        }
    }

    private void initSpinnerOne() {
        List<String> list = new ArrayList<>();
        String[] sheLevelOnes = getResources().getStringArray(R.array.sheshi_level_one_array);
        int length = sheLevelOnes.length;
        for (int i = 0; i < length; i++) {
            list.add(sheLevelOnes[i]);
        }
        list.add("请选择");
        MySpinnerAdapter mySpinnerAdapter = new MySpinnerAdapter(this, com.house.property.R.layout.support_simple_spinner_dropdown_item, list);
        sheLevelOneSpinner.setAdapter(mySpinnerAdapter);
        sheLevelOneSpinner.setSelection(list.size() - 1, true);
        sheLevelOneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listTwo.clear();
                String strOne = list.get(position);
                for (PoiTypeBean poiTypeBean : poiTypeBeanList) {
                    if (poiTypeBean.getTypeLevelOne().equals(strOne)) {
                        listTwo.add(poiTypeBean.getTypeLevelTwo());
                        continue;
                    }
                }
                listTwo = removeDuplicate(listTwo);
                listTwo.add("请选择");
                initSpinnerTwo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initSpinnerTwo() {
        MySpinnerAdapter mySpinnerAdapter = new MySpinnerAdapter(this, com.house.property.R.layout.support_simple_spinner_dropdown_item, listTwo);
        sheLevelTwoSpinner.setAdapter(mySpinnerAdapter);
        sheLevelTwoSpinner.setSelection(listTwo.size() - 1, true);
        sheLevelTwoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (1 == listTwo.size()) {
                    return;
                }
                listThree.clear();
                String strTwo = listTwo.get(position);
                for (PoiTypeBean poiTypeBean : poiTypeBeanList) {
                    if (poiTypeBean.getTypeLevelTwo().equals(strTwo)) {
                        listThree.add(poiTypeBean.getTypeLevelThree());
                        continue;
                    }
                }
                listThree = removeDuplicate(listThree);
                listThree.add("请选择");
                initSpinnerThree();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinnerThree() {
        MySpinnerAdapter mySpinnerAdapter = new MySpinnerAdapter(this, com.house.property.R.layout.support_simple_spinner_dropdown_item, listThree);
        sheLevelThreeSpinner.setAdapter(mySpinnerAdapter);
        sheLevelThreeSpinner.setSelection(listThree.size() - 1, true);
        mySpinnerAdapter.notifyDataSetChanged();

        sheLevelThreeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (1 == listThree.size()) {
                    return;
                }
//                String strThree = listThree.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OnClick({R.id.page_back, R.id.page_right_rl, R.id.add_poi,})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_right_rl:
                insertPoi();
                break;
            case R.id.page_back:
                finish();
                break;
            case R.id.add_poi:
                PhotoUtils.takePicture(this, TAKE_PHOTO);
                photoType = COMM_POI_PHOTO;
                break;
        }
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
                photoBean.setId(poiId);
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
                        dimFileinfoVO.setRelaSrctab("DIM_POI");
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
        if (COMM_POI_PHOTO.equals(type)) {
            mGallery = mGalleryPoi;
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

    private void insertPoi() {

        StringBuffer stringBuffer = new StringBuffer();
        String sheLevelOneSpinnerStr = sheLevelOneSpinner.getSelectedItem().toString();
        String sheLevelTwoStr = sheLevelTwoSpinner.getSelectedItem().toString();
        String sheLevelThreeSpinnerStr = sheLevelThreeSpinner.getSelectedItem().toString();
        String sheshiNameEtStr = reTextNull(sheshiNameEt.getText().toString());
        String sheshiadressEtStr = reTextNull(sheshiadressEt.getText().toString());
        String shengStr = reTextNull(sheshishengEt.getText().toString());
        String cityStr = reTextNull(sheshicityEt.getText().toString());
        String quStr = reTextNull(sheshiQuEt.getText().toString());
        if (showNullToast(sheshiNameEtStr, getResources().getString(R.string.house_bulu_sheshi_name))) {
            return;
        }
        if (showNullToast(sheshiadressEtStr, getResources().getString(R.string.house_bulu_sheshi_adress))) {
            return;
        }
        if (showNullToast(shengStr, getResources().getString(R.string.house_bulu_sheshi_sheng))) {
            return;
        }
        if (showNullToast(cityStr, getResources().getString(R.string.house_bulu_sheshi_city))) {
            return;
        }
        if (showNullToast(quStr, getResources().getString(R.string.house_bulu_sheshi_qu))) {
            return;
        }
        if (showNullToast(sheLevelOneSpinnerStr, getResources().getString(R.string.house_bulu_sheshi_level_one))) {
            return;
        }
        if (showNullToast(sheLevelTwoStr, getResources().getString(R.string.house_bulu_sheshi_level_two))) {
            return;
        }
        if (showNullToast(sheLevelThreeSpinnerStr, getResources().getString(R.string.house_bulu_sheshi_level_three))) {
            return;
        }
        dimPOIVO.setName(sheshiNameEtStr);
        stringBuffer.append(sheLevelOneSpinnerStr).append(",").append(sheLevelTwoStr).append(",").append(sheLevelThreeSpinnerStr);
        dimPOIVO.setType(stringBuffer.toString());
        for (PoiTypeBean poiTypeBean : poiTypeBeanList) {
            if (stringBuffer.toString().contains(poiTypeBean.getTypeName())) {
                dimPOIVO.setTypecode(poiTypeBean.getTypeCode());
                continue;
            }
        }
        dimPOIVO.setAddress(sheshiadressEtStr);

        dimPOIVO.setPname(shengStr);
        if (shengStr.contains("江苏")) {
            dimPOIVO.setPcode("32000");
        }

        dimPOIVO.setCityname(cityStr);
        if (cityStr.contains("扬州")) {
            dimPOIVO.setCitycode("0514");
        }

        dimPOIVO.setAdname(quStr);
        if (quStr.contains("仪征")) {
            dimPOIVO.setAdcode("321081");
        } else if (quStr.contains("江都")) {
            dimPOIVO.setAdcode("321012");
        } else if (quStr.contains("宝应")) {
            dimPOIVO.setAdcode("321023");
        } else if (quStr.contains("邗江")) {
            dimPOIVO.setAdcode("321003");
        } else if (quStr.contains("广陵")) {
            dimPOIVO.setAdcode("321002");
        } else if (quStr.contains("高邮")) {
            dimPOIVO.setAdcode("321084");
        }
        poiAddPresenter.addNewPoi(dimPOIVO);
    }


    @Override
    public void showResult(MessageEventVO bean) {
        if (null == bean) {
            return;
        }

        if (TextUtils.isEmpty(bean.getGisId()) || null == upDimFileinfoVOList || 0 == upDimFileinfoVOList.size()) {
            ToastUtils.showToast(mContext, bean.getResult());
            finish();
        } else {
            if (!TextUtils.isEmpty(bean.getGisId())) {
                poiId = bean.getGisId();
                upFilesInfo();
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onFamilyEvent(DimPOIVO dimPOIVO) {
        this.dimPOIVO = dimPOIVO;
        houseTaskNameTv.setText(dimPOIVO.getCommName());
        poiId = dimPOIVO.getId();
        // 移除粘性事件
        EventBus.getDefault().removeStickyEvent(dimPOIVO);
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
        for (DimFileinfoVO dimFileinfoVO : upDimFileinfoVOList) {
            dimFileinfoVO.setRelaSrckey(poiId);
        }
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
                    poiAddPhotoPresenter.addNewPoiPhoto(blackDimFileinfoVOList);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
