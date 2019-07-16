package com.house.property.model.main.presenter;

import com.house.property.base.BasePresenter;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.model.main.biz.TaskListBiz;
import com.house.property.model.main.entity.TaskListBean;
import com.house.property.model.main.iface.ITaskListView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class TaskListPresenter extends BasePresenter<ITaskListView, LifecycleProvider> {
    private final String TAG = TaskListPresenter.class.getSimpleName();

    public TaskListPresenter(ITaskListView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取任务列表
     */
    public void getTaskList(String username, String status) {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((TaskListBean) object[0]);
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

        new TaskListBiz().getTaskList(username, status, getActivity(), httpRxCallback);
    }

}
