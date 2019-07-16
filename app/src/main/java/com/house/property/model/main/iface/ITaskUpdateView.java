package com.house.property.model.main.iface;

import com.house.property.model.main.entity.TaskUpdateEventBean;
import com.house.property.view.IBaseView;

public interface ITaskUpdateView extends IBaseView {
    //显示结果
    void showResult(TaskUpdateEventBean bean);

}
