package com.house.property.model.main.actvity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.house.property.R;
import com.house.property.base.BaseFragmentActivity;
import com.house.property.manager.ActivityStackManager;
import com.house.property.model.main.entity.LoginBean;

import com.house.property.model.main.iface.ILoginView;
import com.house.property.model.main.presenter.LoginPresenter;
import com.house.property.utils.NetworkUtils;
import com.house.property.utils.SharedPreferencesUtil;
import com.house.property.utils.ToastUtils;
import com.house.property.widget.RLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseFragmentActivity implements ILoginView {

    @BindView(R.id.et_user_name)
    TextView nameEt;
    @BindView(R.id.et_password)
    TextView passwordEv;

    private long clickTime = 0; // 第一次点击的时间
    private String mAccountNumber = "";
    private RLoadingDialog mLoadingDialog;
    private LoginPresenter mLoginPresenter = new LoginPresenter(this, this);


    @Override
    protected int getContentViewId() {
        return R.layout.activity_home_login;
    }

    @Override
    protected void initBundleData() {
        presentersList.add(mLoginPresenter);
    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(this, true);
    }

    @Override
    protected void initData() {
        String name = (String)SharedPreferencesUtil.getData("name", "");
        nameEt.setText(name);

    }

    @OnClick({R.id.login,R.id.page_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
//                startActivity(new Intent(this, MainActivity.class));
//                finish();
                    String name = nameEt.getText().toString();
                    String password = passwordEv.getText().toString();
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                        showToast(getResources().getString(R.string.home_login_null));
                        return;
                    }
                    if (!(password.length() >= 6 && password.length() <= 18)) {
                        showToast(getResources().getString(R.string.home_login_password_length));
                        return;
                    }

                    if (!NetworkUtils.isNetworkConnected()) {
                        showToast(getResources().getString(R.string.home_not_network));
                        return;
                    }
                    mAccountNumber = name;

                    SharedPreferencesUtil.putData("name", name);
                    SharedPreferencesUtil.putData("password", password);
                    mLoginPresenter.startLogin(name, password);


                break;
            case R.id.page_back:
                ActivityStackManager.getManager().exitApp(mContext);
                break;
        }
    }


    @Override
    public void showResult(LoginBean bean) {
        if (null == bean) {
            return;
        }
        Intent intentMain = new Intent(this, MainActivity.class);
        intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentMain);
        finish();
    }

    @Override
    public void showErrorResult(int code) {
//        if (500 == code) {
//            userRegister();
//        }
    }

    @Override
    public void showLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoading() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg);
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 是否触发按键为back键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        } else {
            // 如果不是back键正常响应
            return super.onKeyDown(keyCode, event);
        }
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            ToastUtils.showToast(this, getResources().getString(R.string.home_exits));
            clickTime = System.currentTimeMillis();
        } else {
            ActivityStackManager.getManager().exitApp(mContext);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
