package com.house.property.model.task.presenter;

import com.house.property.base.BasePresenter;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.model.task.biz.BuildDetailsAddBiz;
import com.house.property.model.task.biz.PoiAddBiz;
import com.house.property.model.task.entity.BuildDetailsVO;
import com.house.property.model.task.entity.DimPOIVO;
import com.house.property.model.task.entity.GisObjVO;
import com.house.property.model.task.entity.MessageEventVO;
import com.house.property.model.task.iface.IBuildDetailsAddView;
import com.house.property.model.task.iface.IPoiAddView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class PoiAddPresenter extends BasePresenter<IPoiAddView, LifecycleProvider> {
    private final String TAG = PoiAddPresenter.class.getSimpleName();

    public PoiAddPresenter(IPoiAddView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取任务列表
     */
    public void addNewPoi(DimPOIVO bean) {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((MessageEventVO) object[0]);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showToast(desc);
                }
            }

            @Override
            public void onCancel() {
                if (getView() != null) {
                    getView().closeLoading();
                }
            }
        };

        new PoiAddBiz().addNewPoi(bean, getActivity(), httpRxCallback);
    }
}
