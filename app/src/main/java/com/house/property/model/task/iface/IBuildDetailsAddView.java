package com.house.property.model.task.iface;

import com.house.property.model.task.entity.GisObjVO;
import com.house.property.view.IBaseView;

public interface IBuildDetailsAddView extends IBaseView {
    //显示结果
    void showResult(GisObjVO bean);

}
