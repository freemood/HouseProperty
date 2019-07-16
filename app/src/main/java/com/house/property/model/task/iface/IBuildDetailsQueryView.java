package com.house.property.model.task.iface;

import com.house.property.model.task.entity.BuildDetailsVO;
import com.house.property.view.IBaseView;

public interface IBuildDetailsQueryView extends IBaseView {
    //显示结果
    void showResult(BuildDetailsVO bean);

}
