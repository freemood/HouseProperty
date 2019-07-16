package com.house.property.model.task.entity;

import java.io.Serializable;
import java.util.List;

public class CommBuildListDetailsVO implements Serializable {
    private List<BuildDetailsVO> list;


    public List<BuildDetailsVO> getList() {
        return list;
    }

    public void setList(List<BuildDetailsVO> list) {
        this.list = list;
    }
}
