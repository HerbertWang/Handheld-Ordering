package com.alex.food.utils;

import org.ksoap2.serialization.SoapObject;

import com.alex.food.bean.ItemMasterDto;

/**
 * 
 * @author ALEX
 *
 */
public class GetItemMasterDto {
	
	public static ItemMasterDto getItemMasterDto(SoapObject mSoapObject) {
		ItemMasterDto bean = new ItemMasterDto();
		if (null != mSoapObject.getProperty("AutoRedirectToModifier")) {
			bean.setAutoRedirectToModifier(String.valueOf(mSoapObject
					.getProperty("AutoRedirectToModifier")));
		}
		if (null != mSoapObject.getProperty("BackgroundColor")) {
			bean.setBackgroundColor(String.valueOf(mSoapObject
					.getProperty("BackgroundColor")));
		}
		if (null != mSoapObject.getProperty("ButtonHeight")) {
			bean.setButtonHeight(String.valueOf(mSoapObject
					.getProperty("ButtonHeight")));
		}
		if (null != mSoapObject.getProperty("ButtonWidth")) {
			bean.setButtonWidth(String.valueOf(mSoapObject
					.getProperty("ButtonWidth")));
		}
		if (null != mSoapObject.getProperty("CategoryId")) {
			bean.setCategoryId(String.valueOf(mSoapObject
					.getProperty("CategoryId")));
		}

		if (null != mSoapObject.getProperty("DepartmentId")) {
			bean.setDepartmentId(String.valueOf(mSoapObject
					.getProperty("DepartmentId")));
		}
		if (null != mSoapObject.getProperty("DepartmentName")) {
			bean.setDepartmentName(String.valueOf(mSoapObject
					.getProperty("DepartmentName")));
		}
		if (null != mSoapObject.getProperty("DisplayIndex")) {
			bean.setDisplayIndex(String.valueOf(mSoapObject
					.getProperty("DisplayIndex")));
		}
		if (null != mSoapObject.getProperty("FontSize")) {
			bean.setFontSize(String.valueOf(mSoapObject.getProperty("FontSize")));
		}
		if (null != mSoapObject.getProperty("HasModifier")) {
			bean.setHasModifier(String.valueOf(mSoapObject
					.getProperty("HasModifier")));
		}
		if (null != mSoapObject.getProperty("ImageFileName")) {
			bean.setImageFileName(String.valueOf(mSoapObject
					.getProperty("ImageFileName")));
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
		if (null != mSoapObject.getProperty("IsFollowSet")) {
			bean.setIsFollowSet(String.valueOf(mSoapObject
					.getProperty("IsFollowSet")));
		}
		if (null != mSoapObject.getProperty("IsFollowSetDynamic")) {
			bean.setIsFollowSetDynamic(String.valueOf(mSoapObject
					.getProperty("IsFollowSetDynamic")));
		}
		if (null != mSoapObject.getProperty("IsFollowSetStandard")) {
			bean.setIsFollowSetStandard(String.valueOf(mSoapObject
					.getProperty("IsFollowSetStandard")));
		}
		if (null != mSoapObject.getProperty("IsGroupRightItem")) {
			bean.setIsGroupRightItem(String.valueOf(mSoapObject
					.getProperty("IsGroupRightItem")));
		}
		if (null != mSoapObject.getProperty("IsItemShow")) {
			bean.setIsItemShow(String.valueOf(mSoapObject
					.getProperty("IsItemShow")));
		}
		if (null != mSoapObject.getProperty("IsItemShowInKitchenChecklist")) {
			bean.setIsItemShowInKitchenChecklist(String.valueOf(mSoapObject
					.getProperty("IsItemShowInKitchenChecklist")));
		}
		if (null != mSoapObject.getProperty("IsLimitedItem")) {
			bean.setIsLimitedItem(String.valueOf(mSoapObject
					.getProperty("IsLimitedItem")));
		}
		if (null != mSoapObject.getProperty("IsManualName")) {
			bean.setIsManualName(String.valueOf(mSoapObject
					.getProperty("IsManualName")));
		}
		if (null != mSoapObject.getProperty("IsManualPrice")) {
			bean.setIsManualPrice(String.valueOf(mSoapObject
					.getProperty("IsManualPrice")));
		}
		if (null != mSoapObject.getProperty("IsModifier")) {
			bean.setIsModifier(String.valueOf(mSoapObject
					.getProperty("IsModifier")));
		}
		if (null != mSoapObject.getProperty("IsModifierConcatToParent")) {
			bean.setIsModifierConcatToParent(String.valueOf(mSoapObject
					.getProperty("IsModifierConcatToParent")));
		}
		if (null != mSoapObject.getProperty("IsNoPointEarnItem")) {
			bean.setIsNoPointEarnItem(String.valueOf(mSoapObject
					.getProperty("IsNoPointEarnItem")));
		}
		if (null != mSoapObject.getProperty("IsNonDiscountItem")) {
			bean.setIsNonDiscountItem(String.valueOf(mSoapObject
					.getProperty("IsNonDiscountItem")));
		}
		if (null != mSoapObject.getProperty("IsNonServiceChargeItem")) {
			bean.setIsNonServiceChargeItem(String.valueOf(mSoapObject
					.getProperty("IsNonServiceChargeItem")));
		}
		if (null != mSoapObject.getProperty("IsNonTaxableItem")) {
			bean.setIsNonTaxableItem(String.valueOf(mSoapObject
					.getProperty("IsNonTaxableItem")));
		}
		if (null != mSoapObject.getProperty("IsOutOfStock")) {
			bean.setIsOutOfStock(String.valueOf(mSoapObject
					.getProperty("IsOutOfStock")));
		}
		if (null != mSoapObject.getProperty("IsPointPaidItem")) {
			bean.setIsPointPaidItem(String.valueOf(mSoapObject
					.getProperty("IsPointPaidItem")));
		}
		if (null != mSoapObject.getProperty("IsPriceInPercentage")) {
			bean.setIsPriceInPercentage(String.valueOf(mSoapObject
					.getProperty("IsPriceInPercentage")));
		}
		if (null != mSoapObject.getProperty("IsPriceShow")) {
			bean.setIsPriceShow(String.valueOf(mSoapObject
					.getProperty("IsPriceShow")));
		}
		if (null != mSoapObject.getProperty("IsPrintLabel")) {
			bean.setIsPrintLabel(String.valueOf(mSoapObject
					.getProperty("IsPrintLabel")));
		}
		if (null != mSoapObject.getProperty("IsPrintLabelTakeaway")) {
			bean.setIsPrintLabelTakeaway(String.valueOf(mSoapObject
					.getProperty("IsPrintLabelTakeaway")));
		}
		if (null != mSoapObject.getProperty("ItemCode")) {
			bean.setItemCode(String.valueOf(mSoapObject.getProperty("ItemCode")));
		}
		if (null != mSoapObject.getProperty("ItemCount")) {
			bean.setItemCount(String.valueOf(mSoapObject
					.getProperty("ItemCount")));
		}
		if (null != mSoapObject.getProperty("ItemId")) {
			bean.setItemId(String.valueOf(mSoapObject.getProperty("ItemId")));
		}
		if (null != mSoapObject.getProperty("ItemName")) {
			bean.setItemName(String.valueOf(mSoapObject.getProperty("ItemName")));
		}
		if (null != mSoapObject.getProperty("ItemNameAlt")) {
			bean.setItemNameAlt(String.valueOf(mSoapObject
					.getProperty("ItemNameAlt")));
		}
		if (null != mSoapObject.getProperty("ItemNameAlt2")) {
			bean.setItemNameAlt2(String.valueOf(mSoapObject
					.getProperty("ItemNameAlt2")));
		}
		if (null != mSoapObject.getProperty("ItemNameAlt2")) {
			bean.setItemNameAlt2(String.valueOf(mSoapObject
					.getProperty("ItemNameAlt2")));
		}
		if (null != mSoapObject.getProperty("ItemNameAlt3")) {
			bean.setItemNameAlt3(String.valueOf(mSoapObject
					.getProperty("ItemNameAlt3")));
		}
		if (null != mSoapObject.getProperty("ItemNameAlt4")) {
			bean.setItemNameAlt4(String.valueOf(mSoapObject
					.getProperty("ItemNameAlt4")));
		}
		if (null != mSoapObject.getProperty("ItemPoint")) {
			bean.setItemPoint(String.valueOf(mSoapObject
					.getProperty("ItemPoint")));
		}
		if (null != mSoapObject.getProperty("ItemPosName")) {
			bean.setItemPosName(String.valueOf(mSoapObject
					.getProperty("ItemPosName")));
		}
		if (null != mSoapObject.getProperty("ItemPosNameAlt")) {
			bean.setItemPosNameAlt(String.valueOf(mSoapObject
					.getProperty("ItemPosNameAlt")));
		}
		if (null != mSoapObject.getProperty("ItemPrice")) {
			bean.setItemPrice(String.valueOf(mSoapObject
					.getProperty("ItemPrice")));
		}
		if (null != mSoapObject.getProperty("ItemQty")) {
			bean.setItemQty(String.valueOf(mSoapObject.getProperty("ItemQty")));
		}
		if (null != mSoapObject.getProperty("ItemSetSeq")) {
			bean.setItemSetSeq(String.valueOf(mSoapObject
					.getProperty("ItemSetSeq")));
		}
		if (null != mSoapObject.getProperty("LinkedGroupHeaderId")) {
			bean.setLinkedGroupHeaderId(String.valueOf(mSoapObject
					.getProperty("LinkedGroupHeaderId")));
		}
		if (null != mSoapObject.getProperty("MaxModifierSelectCount")) {
			bean.setMaxModifierSelectCount(String.valueOf(mSoapObject
					.getProperty("MaxModifierSelectCount")));
		}
		if (null != mSoapObject.getProperty("ModifierGroupHeaderId")) {
			bean.setModifierGroupHeaderId(String.valueOf(mSoapObject
					.getProperty("ModifierGroupHeaderId")));
		}
		if (null != mSoapObject.getProperty("Order1")) {
			bean.setOrder1(String.valueOf(mSoapObject.getProperty("Order1")));
		}
		if (null != mSoapObject.getProperty("Order2")) {
			bean.setOrder2(String.valueOf(mSoapObject.getProperty("Order2")));
		}
		if (null != mSoapObject.getProperty("Order3")) {
			bean.setOrder3(String.valueOf(mSoapObject.getProperty("Order3")));
		}
		if (null != mSoapObject.getProperty("PrinterName")) {
			bean.setPrinterName(String.valueOf(mSoapObject
					.getProperty("PrinterName")));
		}
		if (null != mSoapObject.getProperty("PrinterName2")) {
			bean.setPrinterName2(String.valueOf(mSoapObject
					.getProperty("PrinterName2")));
		}
		if (null != mSoapObject.getProperty("PrinterName3")) {
			bean.setPrinterName3(String.valueOf(mSoapObject
					.getProperty("PrinterName3")));
		}
		if (null != mSoapObject.getProperty("PrinterName4")) {
			bean.setPrinterName4(String.valueOf(mSoapObject
					.getProperty("PrinterName4")));
		}
		if (null != mSoapObject.getProperty("PrinterName5")) {
			bean.setPrinterName5(String.valueOf(mSoapObject
					.getProperty("PrinterName5")));
		}
		if (null != mSoapObject.getProperty("ResourceStyleName")) {
			bean.setResourceStyleName(String.valueOf(mSoapObject
					.getProperty("ResourceStyleName")));
		}
		if (null != mSoapObject.getProperty("TakeawaySurcharge")) {
			bean.setTakeawaySurcharge(String.valueOf(mSoapObject
					.getProperty("TakeawaySurcharge")));
		}
		return bean;
	}
}
