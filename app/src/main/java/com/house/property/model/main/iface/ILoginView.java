package com.house.property.model.main.iface;


import com.house.property.model.main.entity.LoginBean;
import com.house.property.view.IBaseView;

public interface ILoginView extends IBaseView {
    //显示结果
    void showResult(LoginBean bean);
    void showErrorResult(int code);

}
