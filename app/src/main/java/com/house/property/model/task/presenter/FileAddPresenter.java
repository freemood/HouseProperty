package com.house.property.model.task.presenter;

import com.house.property.base.BasePresenter;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.model.task.biz.FileAddBiz;
import com.house.property.model.task.biz.PoiAddBiz;
import com.house.property.model.task.entity.DimFileinfoVO;
import com.house.property.model.task.entity.DimPOIVO;
import com.house.property.model.task.entity.MessageEventVO;
import com.house.property.model.task.entity.MessageFileEventVO;
import com.house.property.model.task.iface.IFileAddView;
import com.house.property.model.task.iface.IPoiAddView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class FileAddPresenter extends BasePresenter<IFileAddView, LifecycleProvider> {
    private final String TAG = FileAddPresenter.class.getSimpleName();

    public FileAddPresenter(IFileAddView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取任务列表
     */
    public void insertFileInfo(DimFileinfoVO bean) {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((MessageFileEventVO) object[0]);
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

        new FileAddBiz().insertFileInfo(bean, getActivity(), httpRxCallback);
    }
}
