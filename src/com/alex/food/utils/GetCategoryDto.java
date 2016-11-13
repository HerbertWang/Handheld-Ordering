package com.alex.food.utils;

import org.ksoap2.serialization.SoapObject;

import com.alex.food.bean.CategoryDto;

public class GetCategoryDto {

	public static CategoryDto getCategoryDto(SoapObject mSoapObject) {
		CategoryDto bean = new CategoryDto();
		if (null != mSoapObject.getProperty("BackgroundColor")) {
			bean.setBackgroundColor(String.valueOf(mSoapObject
					.getProperty("BackgroundColor")));
		}
		if (null != mSoapObject.getProperty("ButtonHeight")) {
			bean.setButtonHeight(String.valueOf(mSoapObject
					.getProperty("ButtonHeight")));
		}
		if (null != mSoapObject.getProperty("ButtonStyleId")) {
			bean.setButtonStyleId(String.valueOf(mSoapObject
					.getProperty("ButtonStyleId")));
		}
		if (null != mSoapObject.getProperty("ButtonWidth")) {
			bean.setButtonWidth(String.valueOf(mSoapObject
					.getProperty("ButtonWidth")));
		}
		if (null != mSoapObject.getProperty("CategoryId")) {
			bean.setCategoryId(String.valueOf(mSoapObject
					.getProperty("CategoryId")));
		}
		if (null != mSoapObject.getProperty("CategoryName")) {
			bean.setCategoryName(String.valueOf(mSoapObject
					.getProperty("CategoryName")));
		}
		if (null != mSoapObject.getProperty("CategoryNameAlt")) {
			bean.setCategoryNameAlt(String.valueOf(mSoapObject
					.getProperty("CategoryNameAlt")));
		}
		if (null != mSoapObject.getProperty("DisplayIndex")) {
			bean.setDisplayIndex(String.valueOf(mSoapObject
					.getProperty("DisplayIndex")));
		}
		if (null != mSoapObject.getProperty("FontSize")) {
			bean.setFontSize(String.valueOf(mSoapObject.getProperty("FontSize")));
		}
		if (null != mSoapObject.getProperty("ImageModeButtonHeight")) {
			bean.setImageModeButtonHeight(String.valueOf(mSoapObject
					.getProperty("ImageModeButtonHeight")));
		}
		if (null != mSoapObject.getProperty("ImageModeButtonWidth")) {
			bean.setImageModeButtonWidth(String.valueOf(mSoapObject
					.getProperty("ImageModeButtonWidth")));
		}
		if (null != mSoapObject.getProperty("ImageModeFontSize")) {
			bean.setImageModeFontSize(String.valueOf(mSoapObject
					.getProperty("ImageModeFontSize")));
		}
		if (null != mSoapObject.getProperty("ImageModeResourceStyleName")) {
			bean.setImageModeResourceStyleName(String.valueOf(mSoapObject
					.getProperty("ImageModeResourceStyleName")));
		}
		if (null != mSoapObject.getProperty("IsSmartCategory")) {
			bean.setIsSmartCategory(String.valueOf(mSoapObject
					.getProperty("IsSmartCategory")));
		}
		if (null != mSoapObject.getProperty("IsTerminal")) {
			bean.setIsTerminal(String.valueOf(mSoapObject
					.getProperty("IsTerminal")));
		}
		if (null != mSoapObject.getProperty("ParentCategoryId")) {
			bean.setParentCategoryId(String.valueOf(mSoapObject
					.getProperty("ParentCategoryId")));
		}
		if (null != mSoapObject.getProperty("PrinterName")) {
			bean.setPrinterName(String.valueOf(mSoapObject
					.getProperty("PrinterName")));
		}
		if (null != mSoapObject.getProperty("ResourceStyleName")) {
			bean.setResourceStyleName(String.valueOf(mSoapObject
					.getProperty("ResourceStyleName")));
		}
		return bean;
	}
}
