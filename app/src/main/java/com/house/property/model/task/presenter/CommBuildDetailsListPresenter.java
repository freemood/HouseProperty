package com.house.property.model.task.presenter;

import com.house.property.base.BasePresenter;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.model.task.biz.CommBuildListDetailsBiz;
import com.house.property.model.task.biz.CommDetailsBiz;
import com.house.property.model.task.entity.CommBuildListDetailsVO;
import com.house.property.model.task.entity.CommunityDetailsBean;
import com.house.property.model.task.iface.ICommBuildDetailsListView;
import com.house.property.model.task.iface.ICommDetailsView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class CommBuildDetailsListPresenter extends BasePresenter<ICommBuildDetailsListView, LifecycleProvider> {
    private final String TAG = CommBuildDetailsListPresenter.class.getSimpleName();

    public CommBuildDetailsListPresenter(ICommBuildDetailsListView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取任务列表
     */
    public void getCommBuildDetailsList(String id) {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((CommBuildListDetailsVO) object[0]);
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

        new CommBuildListDetailsBiz().getCommBuildDetailsList(id, getActivity(), httpRxCallback);
    }

}
