package com.house.property.model.task.iface;

import com.house.property.model.task.entity.MessageEventVO;
import com.house.property.view.IBaseView;

public interface IBuildDetailsUpdateView extends IBaseView {
    //显示结果
    void showResult(MessageEventVO bean);

}
