package com.house.property.model.main.presenter;

import com.house.property.base.BasePresenter;
import com.house.property.http.observer.HttpRxCallback;
import com.house.property.model.main.biz.LoginBiz;
import com.house.property.model.main.entity.LoginBean;
import com.house.property.model.main.iface.ILoginView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class LoginPresenter extends BasePresenter<ILoginView, LifecycleProvider> {
    private final String TAG = LoginPresenter.class.getSimpleName();

    public LoginPresenter(ILoginView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 登录
     */
    public void startLogin(String username, String password) {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((LoginBean) object[0]);
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

        new LoginBiz().startLoginParm(username, password, getActivity(), httpRxCallback);
    }

}
