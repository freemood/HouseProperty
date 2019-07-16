package com.house.property.model.main.iface;

import com.house.property.model.main.entity.TaskListBean;
import com.house.property.view.IBaseView;

public interface ITaskListView extends IBaseView {
    //显示结果
    void showResult(TaskListBean bean);

}
