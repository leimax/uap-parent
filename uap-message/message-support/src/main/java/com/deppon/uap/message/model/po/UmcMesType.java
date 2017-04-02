package com.deppon.uap.message.model.po;

import com.deppon.uap.framework.model.BaseEntity;

public class UmcMesType extends BaseEntity {

    private String mesType;

    private String mesCode;

    private String mesName;

    private String mesDefault;

    private String mesRed;

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    public String getMesCode() {
        return mesCode;
    }

    public void setMesCode(String mesCode) {
        this.mesCode = mesCode;
    }

    public String getMesName() {
        return mesName;
    }

    public void setMesName(String mesName) {
        this.mesName = mesName;
    }

    public String getMesDefault() {
        return mesDefault;
    }

    public void setMesDefault(String mesDefault) {
        this.mesDefault = mesDefault;
    }

    public String getMesRed() {
        return mesRed;
    }

    public void setMesRed(String mesRed) {
        this.mesRed = mesRed;
    }
}