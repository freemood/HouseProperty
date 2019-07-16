package com.house.property.model.main.entity;

import java.io.Serializable;
import java.util.List;

public class TaskListBean implements Serializable {
    private List<TaskBean> list;


    public List<TaskBean> getList() {
        return list;
    }

    public void setList(List<TaskBean> list) {
        this.list = list;
    }
}
