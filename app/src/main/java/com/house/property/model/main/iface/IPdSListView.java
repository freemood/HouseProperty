package com.house.property.model.main.iface;

import com.house.property.model.main.entity.PdSListBean;
import com.house.property.view.IBaseView;

public interface IPdSListView extends IBaseView {
    //显示结果
    void showResult(PdSListBean bean);

}
