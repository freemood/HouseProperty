package com.house.property.model.task.iface;

import com.house.property.model.task.entity.MessageFileEventVO;
import com.house.property.view.IBaseView;

public interface IFileAddView extends IBaseView {
    //显示结果
    void showResult(MessageFileEventVO bean);

}
