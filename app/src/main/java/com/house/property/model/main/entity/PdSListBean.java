package com.house.property.model.main.entity;

import com.house.property.model.task.entity.PdSVO;

import java.io.Serializable;
import java.util.List;

public class PdSListBean implements Serializable {
    private List<PdSVO> list;


    public List<PdSVO> getList() {
        return list;
    }

    public void setList(List<PdSVO> list) {
        this.list = list;
    }
}
