package com.nobug.bean;

import java.io.Serializable;
import java.util.List;

public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Area> areaList;// 区域列表

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }
}
