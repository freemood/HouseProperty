package com.house.property.model.task.iface;

import com.house.property.model.task.entity.CommunityDetailsBean;
import com.house.property.view.IBaseView;

public interface ICommDetailsView extends IBaseView {
    //显示结果
    void showResult(CommunityDetailsBean bean);

}
