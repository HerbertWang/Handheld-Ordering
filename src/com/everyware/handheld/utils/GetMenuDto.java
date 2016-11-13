package com.everyware.handheld.utils;

import org.ksoap2.serialization.SoapObject;

import com.everyware.handheld.bean.MenuHeaderDto;;

public class GetMenuDto {

	public static MenuHeaderDto getMenuDto(SoapObject mSoapObject) {
		 MenuHeaderDto bean = new  MenuHeaderDto();
		if (null != mSoapObject.getProperty("AccountId")) {
			bean.setAccountId(String.valueOf(mSoapObject
					.getProperty("AccountId")));
		}
		if (null != mSoapObject.getProperty("DisplayOrder")) {
			bean.setDisplayOrder(String.valueOf(mSoapObject
					.getProperty("DisplayOrder")));
		}
		if (null != mSoapObject.getProperty("IsBuiltIn")) {
			bean.setIsBuiltIn(String.valueOf(mSoapObject
					.getProperty("IsBuiltIn")));
		}
		if (null != mSoapObject.getProperty("MenuId")) {
			bean.setMenuId(String.valueOf(mSoapObject
					.getProperty("MenuId")));
		}
		if (null != mSoapObject.getProperty("MenuName")) {
			bean.setMenuName(String.valueOf(mSoapObject
					.getProperty("MenuName")));
		}

		if (null != mSoapObject.getProperty("MenuNameAlt")) {
			bean.setMenuNameAlt(String.valueOf(mSoapObject
					.getProperty("MenuNameAlt")));
		}
		
		return bean;
	}
}

	

