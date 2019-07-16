package com.house.property.model.task.presenter;

import com.house.property.base.BasePresenter;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.model.task.biz.BuildDetailsAddBiz;
import com.house.property.model.task.biz.BuildDetailsQueryBiz;
import com.house.property.model.task.entity.BuildDetailsVO;
import com.house.property.model.task.entity.MessageEventVO;
import com.house.property.model.task.iface.IBuildDetailsAddView;
import com.house.property.model.task.iface.IBuildDetailsQueryView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class BuildDetailsQueryPresenter extends BasePresenter<IBuildDetailsQueryView, LifecycleProvider> {
    private final String TAG = BuildDetailsQueryPresenter.class.getSimpleName();

    public BuildDetailsQueryPresenter(IBuildDetailsQueryView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取楼栋列表
     */
    public void queryBuildDetails(String id) {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((BuildDetailsVO) object[0]);
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

        new BuildDetailsQueryBiz().queryBuildDetails(id, getActivity(), httpRxCallback);
    }
}
