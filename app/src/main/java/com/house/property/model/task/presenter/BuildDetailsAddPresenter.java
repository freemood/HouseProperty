package com.house.property.model.task.presenter;

import com.house.property.base.BasePresenter;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.model.task.biz.BuildDetailsAddBiz;
import com.house.property.model.task.entity.BuildDetailsVO;
import com.house.property.model.task.entity.GisObjVO;
import com.house.property.model.task.iface.IBuildDetailsAddView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class BuildDetailsAddPresenter extends BasePresenter<IBuildDetailsAddView, LifecycleProvider> {
    private final String TAG = BuildDetailsAddPresenter.class.getSimpleName();

    public BuildDetailsAddPresenter(IBuildDetailsAddView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取任务列表
     */
    public void addBuildDetails(BuildDetailsVO bean) {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((GisObjVO) object[0]);
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

        new BuildDetailsAddBiz().addBuildDetails(bean, getActivity(), httpRxCallback);
    }
}
