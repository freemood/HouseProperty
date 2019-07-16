package com.house.property.model.task.presenter;

import com.house.property.base.BasePresenter;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.model.task.biz.CommDetailsBiz;
import com.house.property.model.task.biz.CommDetailsUpdateBiz;
import com.house.property.model.task.entity.CommunityDetailsBean;
import com.house.property.model.task.entity.MessageEventVO;
import com.house.property.model.task.iface.ICommDetailsUpdateView;
import com.house.property.model.task.iface.ICommDetailsView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class CommDetailsUpdatePresenter extends BasePresenter<ICommDetailsUpdateView, LifecycleProvider> {
    private final String TAG = CommDetailsUpdatePresenter.class.getSimpleName();

    public CommDetailsUpdatePresenter(ICommDetailsUpdateView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取任务列表
     */
    public void updateCommunityDetails(CommunityDetailsBean bean) {

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

        new CommDetailsUpdateBiz().updateCommunityDetails(bean, getActivity(), httpRxCallback);
    }

}
