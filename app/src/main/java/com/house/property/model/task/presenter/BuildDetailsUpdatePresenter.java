package com.house.property.model.task.presenter;

import com.house.property.base.BasePresenter;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.model.task.biz.BuildDetailsUpdateBiz;
import com.house.property.model.task.biz.CommDetailsUpdateBiz;
import com.house.property.model.task.entity.BuildDetailsVO;
import com.house.property.model.task.entity.CommunityDetailsBean;
import com.house.property.model.task.entity.MessageEventVO;
import com.house.property.model.task.iface.IBuildDetailsAddView;
import com.house.property.model.task.iface.IBuildDetailsUpdateView;
import com.house.property.model.task.iface.ICommDetailsUpdateView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class BuildDetailsUpdatePresenter extends BasePresenter<IBuildDetailsUpdateView, LifecycleProvider> {
    private final String TAG = BuildDetailsUpdatePresenter.class.getSimpleName();

    public BuildDetailsUpdatePresenter(IBuildDetailsUpdateView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取任务列表
     */
    public void updateBuildDetails(BuildDetailsVO bean) {

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

        new BuildDetailsUpdateBiz().updateBuildDetails(bean, getActivity(), httpRxCallback);
    }

}
