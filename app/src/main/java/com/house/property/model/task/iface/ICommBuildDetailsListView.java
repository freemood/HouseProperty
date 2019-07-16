package com.house.property.model.task.iface;

import com.house.property.model.task.entity.CommBuildListDetailsVO;
import com.house.property.view.IBaseView;

public interface ICommBuildDetailsListView extends IBaseView {
    //显示结果
    void showResult(CommBuildListDetailsVO bean);

}
