package com.everyware.handheld.bean;

/**
 * Created by lenovo on 10/5/2015.
 */
public class ReasonDto {
    private String ReasonId;
    private String ReasonGroupCode;
    private String ReasonCode;
    private String ReasonDesc;
    private String Enabled;

    public String getReasonId() {
        return ReasonId;
    }

    public void setReasonId(String reasonId) {
        ReasonId = reasonId;
    }

    public String getReasonGroupCode() {
        return ReasonGroupCode;
    }

    public void setReasonGroupCode(String reasonGroupCode) {
        ReasonGroupCode = reasonGroupCode;
    }

    public String getReasonCode() {
        return ReasonCode;
    }

    public void setReasonCode(String reasonCode) {
        ReasonCode = reasonCode;
    }

    public String getReasonDesc() {
        return ReasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        ReasonDesc = reasonDesc;
    }

    public String getEnabled() {
        return Enabled;
    }

    public void setEnabled(String enabled) {
        Enabled = enabled;
    }

}
