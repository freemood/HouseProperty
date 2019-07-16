package com.house.property.model.task.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.house.property.R;
import com.house.property.adapter.MySpinnerAdapter;
import com.house.property.base.BaseFragmentActivity;
import com.house.property.model.task.entity.DimPOIVO;
import com.house.property.model.task.entity.MessageEventVO;
import com.house.property.model.task.entity.PoiTypeBean;
import com.house.property.model.task.iface.IPoiAddView;
import com.house.property.model.task.presenter.PoiAddPresenter;
import com.house.property.utils.ToastUtils;
import com.house.property.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.house.property.utils.TaskUtils.reTextNull;
import static com.house.property.utils.TaskUtils.showNullToast;
import static com.house.property.utils.Utils.removeDuplicate;

public class TaskAddPOIActivity extends BaseFragmentActivity implements IPoiAddView {

    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;
    @BindView(R.id.page_title)
    TextView pageTitleTv;

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

    private RLoadingDialog mLoadingDialog;
    private DimPOIVO dimPOIVO;
    private List<PoiTypeBean> poiTypeBeanList = new ArrayList<>();
    private List<String> listTwo = new ArrayList<>();
    private List<String> listThree = new ArrayList<>();

    private PoiAddPresenter poiAddPresenter = new PoiAddPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_task_add_poi;
    }

    @Override
    protected void initBundleData() {
        presentersList.add(poiAddPresenter);
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

    @OnClick({R.id.page_back, R.id.page_right_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_right_rl:
                insertPoi();
                break;
            case R.id.page_back:
                finish();
                break;
        }
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
        ToastUtils.showToast(mContext, bean.getResult());
        finish();
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onFamilyEvent(DimPOIVO dimPOIVO) {
        this.dimPOIVO = dimPOIVO;
        houseTaskNameTv.setText(dimPOIVO.getCommName());
        // 移除粘性事件
        EventBus.getDefault().removeStickyEvent(dimPOIVO);
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
    protected void onDestroy() {
        super.onDestroy();
    }
}
