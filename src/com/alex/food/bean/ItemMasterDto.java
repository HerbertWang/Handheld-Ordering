package com.alex.food.bean;

/**
 * 
 * @author ALEX
 *
 */
public class ItemMasterDto {
	private String AutoRedirectToModifier;
	private String BackgroundColor;
	private String ButtonHeight;
	private String ButtonWidth;
	private String CategoryId;
	private String DepartmentId;
	private String DepartmentName;
	private String DisplayIndex;
	private String FontSize;
	private String HasModifier;
	private String ImageFileName;
	private String ImageModeButtonHeight;
	private String ImageModeButtonWidth;
	private String ImageModeFontSize;
	private String ImageModeResourceStyleName;
	private String IsFollowSet;
	private String IsFollowSetDynamic;
	private String IsFollowSetStandard;
	private String IsGroupRightItem;
	private String IsItemShow;
	private String IsItemShowInKitchenChecklist;
	private String IsLimitedItem;
	private String IsManualName;
	private String IsManualPrice;
	private String IsModifier;
	private String IsModifierConcatToParent;
	private String IsNoPointEarnItem;
	private String IsNonDiscountItem;
	private String IsNonServiceChargeItem;
	private String IsNonTaxableItem;
	private String IsOutOfStock;
	private String IsPointPaidItem;
	private String IsPriceInPercentage;
	private String IsPriceShow;
	private String IsPrintLabel;
	private String IsPrintLabelTakeaway;
	private String ItemCode;
	private String ItemCount;
	private String ItemId;
	private String ItemName;
	private String ItemNameAlt;
	private String ItemNameAlt2;
	private String ItemNameAlt3;
	private String ItemNameAlt4;
	private String ItemPoint;
	private String ItemPosName;
	private String ItemPosNameAlt;
	private String ItemPrice;
	private String ItemQty;
	private String ItemSetSeq;
	private String LinkedGroupHeaderId;
	private String MaxModifierSelectCount;
	private String ModifierGroupHeaderId;
	private String Order1;
	private String Order2;
	private String Order3;
	private String PrinterName;
	private String PrinterName2;
	private String PrinterName3;
	private String PrinterName4;
	private String PrinterName5;
	private String ResourceStyleName;
	private String TakeawaySurcharge;

	private int currentCount = 0;
	private boolean isClick = true;

	public String getAutoRedirectToModifier() {
		return AutoRedirectToModifier;
	}

	public void setAutoRedirectToModifier(String autoRedirectToModifier) {
		AutoRedirectToModifier = autoRedirectToModifier;
	}

	public String getBackgroundColor() {
		return BackgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		BackgroundColor = backgroundColor;
	}

	public String getButtonHeight() {
		return ButtonHeight;
	}

	public void setButtonHeight(String buttonHeight) {
		ButtonHeight = buttonHeight;
	}

	public String getButtonWidth() {
		return ButtonWidth;
	}

	public void setButtonWidth(String buttonWidth) {
		ButtonWidth = buttonWidth;
	}

	public String getCategoryId() {
		return CategoryId;
	}

	public void setCategoryId(String categoryId) {
		CategoryId = categoryId;
	}

	public String getDepartmentId() {
		return DepartmentId;
	}

	public void setDepartmentId(String departmentId) {
		DepartmentId = departmentId;
	}

	public String getDepartmentName() {
		return DepartmentName;
	}

	public void setDepartmentName(String departmentName) {
		DepartmentName = departmentName;
	}

	public String getDisplayIndex() {
		return DisplayIndex;
	}

	public void setDisplayIndex(String displayIndex) {
		DisplayIndex = displayIndex;
	}

	public String getFontSize() {
		return FontSize;
	}

	public void setFontSize(String fontSize) {
		FontSize = fontSize;
	}

	public String getHasModifier() {
		return HasModifier;
	}

	public void setHasModifier(String hasModifier) {
		HasModifier = hasModifier;
	}

	public String getImageFileName() {
		return ImageFileName;
	}

	public void setImageFileName(String imageFileName) {
		ImageFileName = imageFileName;
	}

	public String getImageModeButtonHeight() {
		return ImageModeButtonHeight;
	}

	public void setImageModeButtonHeight(String imageModeButtonHeight) {
		ImageModeButtonHeight = imageModeButtonHeight;
	}

	public String getImageModeButtonWidth() {
		return ImageModeButtonWidth;
	}

	public void setImageModeButtonWidth(String imageModeButtonWidth) {
		ImageModeButtonWidth = imageModeButtonWidth;
	}

	public String getImageModeFontSize() {
		return ImageModeFontSize;
	}

	public void setImageModeFontSize(String imageModeFontSize) {
		ImageModeFontSize = imageModeFontSize;
	}

	public String getImageModeResourceStyleName() {
		return ImageModeResourceStyleName;
	}

	public void setImageModeResourceStyleName(String imageModeResourceStyleName) {
		ImageModeResourceStyleName = imageModeResourceStyleName;
	}

	public String getIsFollowSet() {
		return IsFollowSet;
	}

	public void setIsFollowSet(String isFollowSet) {
		IsFollowSet = isFollowSet;
	}

	public String getIsFollowSetDynamic() {
		return IsFollowSetDynamic;
	}

	public void setIsFollowSetDynamic(String isFollowSetDynamic) {
		IsFollowSetDynamic = isFollowSetDynamic;
	}

	public String getIsFollowSetStandard() {
		return IsFollowSetStandard;
	}

	public void setIsFollowSetStandard(String isFollowSetStandard) {
		IsFollowSetStandard = isFollowSetStandard;
	}

	public String getIsGroupRightItem() {
		return IsGroupRightItem;
	}

	public void setIsGroupRightItem(String isGroupRightItem) {
		IsGroupRightItem = isGroupRightItem;
	}

	public String getIsItemShow() {
		return IsItemShow;
	}

	public void setIsItemShow(String isItemShow) {
		IsItemShow = isItemShow;
	}

	public String getIsItemShowInKitchenChecklist() {
		return IsItemShowInKitchenChecklist;
	}

	public void setIsItemShowInKitchenChecklist(
			String isItemShowInKitchenChecklist) {
		IsItemShowInKitchenChecklist = isItemShowInKitchenChecklist;
	}

	public String getIsLimitedItem() {
		return IsLimitedItem;
	}

	public void setIsLimitedItem(String isLimitedItem) {
		IsLimitedItem = isLimitedItem;
	}

	public String getIsManualName() {
		return IsManualName;
	}

	public void setIsManualName(String isManualName) {
		IsManualName = isManualName;
	}

	public String getIsManualPrice() {
		return IsManualPrice;
	}

	public void setIsManualPrice(String isManualPrice) {
		IsManualPrice = isManualPrice;
	}

	public String getIsModifier() {
		return IsModifier;
	}

	public void setIsModifier(String isModifier) {
		IsModifier = isModifier;
	}

	public String getIsModifierConcatToParent() {
		return IsModifierConcatToParent;
	}

	public void setIsModifierConcatToParent(String isModifierConcatToParent) {
		IsModifierConcatToParent = isModifierConcatToParent;
	}

	public String getIsNoPointEarnItem() {
		return IsNoPointEarnItem;
	}

	public void setIsNoPointEarnItem(String isNoPointEarnItem) {
		IsNoPointEarnItem = isNoPointEarnItem;
	}

	public String getIsNonDiscountItem() {
		return IsNonDiscountItem;
	}

	public void setIsNonDiscountItem(String isNonDiscountItem) {
		IsNonDiscountItem = isNonDiscountItem;
	}

	public String getIsNonServiceChargeItem() {
		return IsNonServiceChargeItem;
	}

	public void setIsNonServiceChargeItem(String isNonServiceChargeItem) {
		IsNonServiceChargeItem = isNonServiceChargeItem;
	}

	public String getIsNonTaxableItem() {
		return IsNonTaxableItem;
	}

	public void setIsNonTaxableItem(String isNonTaxableItem) {
		IsNonTaxableItem = isNonTaxableItem;
	}

	public String getIsOutOfStock() {
		return IsOutOfStock;
	}

	public void setIsOutOfStock(String isOutOfStock) {
		IsOutOfStock = isOutOfStock;
	}

	public String getIsPointPaidItem() {
		return IsPointPaidItem;
	}

	public void setIsPointPaidItem(String isPointPaidItem) {
		IsPointPaidItem = isPointPaidItem;
	}

	public String getIsPriceInPercentage() {
		return IsPriceInPercentage;
	}

	public void setIsPriceInPercentage(String isPriceInPercentage) {
		IsPriceInPercentage = isPriceInPercentage;
	}

	public String getIsPriceShow() {
		return IsPriceShow;
	}

	public void setIsPriceShow(String isPriceShow) {
		IsPriceShow = isPriceShow;
	}

	public String getIsPrintLabel() {
		return IsPrintLabel;
	}

	public void setIsPrintLabel(String isPrintLabel) {
		IsPrintLabel = isPrintLabel;
	}

	public String getIsPrintLabelTakeaway() {
		return IsPrintLabelTakeaway;
	}

	public void setIsPrintLabelTakeaway(String isPrintLabelTakeaway) {
		IsPrintLabelTakeaway = isPrintLabelTakeaway;
	}

	public String getItemCode() {
		return ItemCode;
	}

	public void setItemCode(String itemCode) {
		ItemCode = itemCode;
	}

	public String getItemCount() {
		return ItemCount;
	}

	public void setItemCount(String itemCount) {
		ItemCount = itemCount;
	}

	public String getItemId() {
		return ItemId;
	}

	public void setItemId(String itemId) {
		ItemId = itemId;
	}

	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
	}

	public String getItemNameAlt() {
		return ItemNameAlt;
	}

	public void setItemNameAlt(String itemNameAlt) {
		ItemNameAlt = itemNameAlt;
	}

	public String getItemNameAlt2() {
		return ItemNameAlt2;
	}

	public void setItemNameAlt2(String itemNameAlt2) {
		ItemNameAlt2 = itemNameAlt2;
	}

	public String getItemNameAlt3() {
		return ItemNameAlt3;
	}

	public void setItemNameAlt3(String itemNameAlt3) {
		ItemNameAlt3 = itemNameAlt3;
	}

	public String getItemNameAlt4() {
		return ItemNameAlt4;
	}

	public void setItemNameAlt4(String itemNameAlt4) {
		ItemNameAlt4 = itemNameAlt4;
	}

	public String getItemPoint() {
		return ItemPoint;
	}

	public void setItemPoint(String itemPoint) {
		ItemPoint = itemPoint;
	}

	public String getItemPosName() {
		return ItemPosName;
	}

	public void setItemPosName(String itemPosName) {
		ItemPosName = itemPosName;
	}

	public String getItemPosNameAlt() {
		return ItemPosNameAlt;
	}

	public void setItemPosNameAlt(String itemPosNameAlt) {
		ItemPosNameAlt = itemPosNameAlt;
	}

	public String getItemPrice() {
		return ItemPrice;
	}

	public void setItemPrice(String itemPrice) {
		ItemPrice = itemPrice;
	}

	public String getItemQty() {
		return ItemQty;
	}

	public void setItemQty(String itemQty) {
		ItemQty = itemQty;
	}

	public String getItemSetSeq() {
		return ItemSetSeq;
	}

	public void setItemSetSeq(String itemSetSeq) {
		ItemSetSeq = itemSetSeq;
	}

	public String getLinkedGroupHeaderId() {
		return LinkedGroupHeaderId;
	}

	public void setLinkedGroupHeaderId(String linkedGroupHeaderId) {
		LinkedGroupHeaderId = linkedGroupHeaderId;
	}

	public String getMaxModifierSelectCount() {
		return MaxModifierSelectCount;
	}

	public void setMaxModifierSelectCount(String maxModifierSelectCount) {
		MaxModifierSelectCount = maxModifierSelectCount;
	}

	public String getModifierGroupHeaderId() {
		return ModifierGroupHeaderId;
	}

	public void setModifierGroupHeaderId(String modifierGroupHeaderId) {
		ModifierGroupHeaderId = modifierGroupHeaderId;
	}

	public String getOrder1() {
		return Order1;
	}

	public void setOrder1(String order1) {
		Order1 = order1;
	}

	public String getOrder2() {
		return Order2;
	}

	public void setOrder2(String order2) {
		Order2 = order2;
	}

	public String getOrder3() {
		return Order3;
	}

	public void setOrder3(String order3) {
		Order3 = order3;
	}

	public String getPrinterName() {
		return PrinterName;
	}

	public void setPrinterName(String printerName) {
		PrinterName = printerName;
	}

	public String getPrinterName2() {
		return PrinterName2;
	}

	public void setPrinterName2(String printerName2) {
		PrinterName2 = printerName2;
	}

	public String getPrinterName3() {
		return PrinterName3;
	}

	public void setPrinterName3(String printerName3) {
		PrinterName3 = printerName3;
	}

	public String getPrinterName4() {
		return PrinterName4;
	}

	public void setPrinterName4(String printerName4) {
		PrinterName4 = printerName4;
	}

	public String getPrinterName5() {
		return PrinterName5;
	}

	public void setPrinterName5(String printerName5) {
		PrinterName5 = printerName5;
	}

	public String getResourceStyleName() {
		return ResourceStyleName;
	}

	public void setResourceStyleName(String resourceStyleName) {
		ResourceStyleName = resourceStyleName;
	}

	public String getTakeawaySurcharge() {
		return TakeawaySurcharge;
	}

	public void setTakeawaySurcharge(String takeawaySurcharge) {
		TakeawaySurcharge = takeawaySurcharge;
	}

	public int getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}

	public boolean isClick() {
		return isClick;
	}

	public void setClick(boolean isClick) {
		this.isClick = isClick;
	}
}
