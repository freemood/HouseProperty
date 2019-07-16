package com.house.property.model.main.presenter;

import com.house.property.base.BasePresenter;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.model.main.biz.PdSListBiz;
import com.house.property.model.main.biz.TaskListBiz;
import com.house.property.model.main.entity.PdSListBean;
import com.house.property.model.main.entity.TaskListBean;
import com.house.property.model.main.iface.IPdSListView;
import com.house.property.model.main.iface.ITaskListView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class PdSListPresenter extends BasePresenter<IPdSListView, LifecycleProvider> {
    private final String TAG = PdSListPresenter.class.getSimpleName();

    public PdSListPresenter(IPdSListView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取任务列表
     */
    public void getPdsTypes() {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((PdSListBean) object[0]);
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

        new PdSListBiz().getPdsTypes(getActivity(), httpRxCallback);
    }

}
