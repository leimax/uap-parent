package com.deppon.uap.message.model.po;

import com.deppon.uap.framework.model.BaseEntity;

public class PerMesType extends BaseEntity {

    private String mesType;

    private String mesCode;

    private String mesName;

    private String mesCheck;

    private String empCode;

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

    public String getMesCheck() {
        return mesCheck;
    }

    public void setMesCheck(String mesCheck) {
        this.mesCheck = mesCheck;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }
}