package com.everyware.handheld.utils;

import com.everyware.handheld.bean.ReasonDto;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by lenovo on 10/5/2015.
 */
public class GetReasonDto {
    public static ReasonDto getReasonDto(SoapObject mSoapObject) {
        ReasonDto bean = new ReasonDto();

        if (null != mSoapObject.getProperty("ReasonId")) {
            bean.setReasonId(String.valueOf(mSoapObject
                    .getProperty("ReasonId")));
        }

        if (null != mSoapObject.getProperty("ReasonGroupCode")) {
            bean.setReasonGroupCode(String.valueOf(mSoapObject
                    .getProperty("ReasonGroupCode")));
        }

        if (null != mSoapObject.getProperty("ReasonCode")) {
            bean.setReasonCode(String.valueOf(mSoapObject
                    .getProperty("ReasonCode")));
        }

        if (null != mSoapObject.getProperty("ReasonDesc")) {
            bean.setReasonDesc(String.valueOf(mSoapObject
                    .getProperty("ReasonDesc")));
        }

        if (null != mSoapObject.getProperty("Enabled")) {
            bean.setEnabled(String.valueOf(mSoapObject
                    .getProperty("Enabled")));
        }

        return bean;
    }
}
