package com.house.property.model.task.presenter;

import com.house.property.base.BasePresenter;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.model.task.biz.CommDetailsBiz;
import com.house.property.model.task.entity.CommunityDetailsBean;
import com.house.property.model.task.iface.ICommDetailsView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class CommDetailsPresenter extends BasePresenter<ICommDetailsView, LifecycleProvider> {
    private final String TAG = CommDetailsPresenter.class.getSimpleName();

    public CommDetailsPresenter(ICommDetailsView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取任务列表
     */
    public void getCommunityDetails(String id) {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((CommunityDetailsBean) object[0]);
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

        new CommDetailsBiz().getCommunityDetails(id, getActivity(), httpRxCallback);
    }

}
