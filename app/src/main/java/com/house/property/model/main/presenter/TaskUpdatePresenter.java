package com.house.property.model.main.presenter;

import com.house.property.base.BasePresenter;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.model.main.biz.TaskListBiz;
import com.house.property.model.main.biz.TaskUpdateBiz;
import com.house.property.model.main.entity.TaskListBean;
import com.house.property.model.main.entity.TaskUpdateEventBean;
import com.house.property.model.main.iface.ITaskListView;
import com.house.property.model.main.iface.ITaskUpdateView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class TaskUpdatePresenter extends BasePresenter<ITaskUpdateView, LifecycleProvider> {
    private final String TAG = TaskUpdatePresenter.class.getSimpleName();

    public TaskUpdatePresenter(ITaskUpdateView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取更新任务列表
     */
    public void updateTask(String id) {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((TaskUpdateEventBean) object[0]);
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

        new TaskUpdateBiz().updateTask(id, getActivity(), httpRxCallback);
    }

}
