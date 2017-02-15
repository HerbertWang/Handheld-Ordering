package com.alex.food.utils;

import org.ksoap2.serialization.SoapObject;

import com.alex.food.bean.TxSalesDetailDto;

/**
 * 
 * @author ALEX
 *
 */
public class GetTxSalesDetailDto {

	public static TxSalesDetailDto parseTxSalesDetailDto(SoapObject object) {

		TxSalesDetailDto bean = new TxSalesDetailDto();

		if (null != object.getProperty("Amount")) {
			bean.setAmount(String.valueOf(object.getProperty("Amount")));
		}
		if (null != object.getProperty("AmountPoint")) {
			bean.setAmountPoint(String.valueOf(object
					.getProperty("AmountPoint")));
		}
		if (null != object.getProperty("CategoryId")) {
			bean.setCategoryId(String.valueOf(object.getProperty("CategoryId")));
		}
		if (null != object.getProperty("ChaseCount")) {
			bean.setChaseCount(String.valueOf(object.getProperty("ChaseCount")));
		}
		if (null != object.getProperty("ChaseDateTime")) {
			bean.setChaseDateTime(String.valueOf(object
					.getProperty("ChaseDateTime")));
		}
		if (null != object.getProperty("ChaseUserId")) {
			bean.setChaseUserId(String.valueOf(object
					.getProperty("ChaseUserId")));
		}
		if (null != object.getProperty("ChaseUserName")) {
			bean.setChaseUserName(String.valueOf(object
					.getProperty("ChaseUserName")));
		}
		if (null != object.getProperty("CreatedBy")) {
			bean.setCreatedBy(String.valueOf(object.getProperty("CreatedBy")));
		}
		if (null != object.getProperty("CreatedDate")) {
			bean.setCreatedDate(String.valueOf(object
					.getProperty("CreatedDate")));
		}
		if (null != object.getProperty("DepartmentId")) {
			bean.setDepartmentId(String.valueOf(object
					.getProperty("DepartmentId")));
		}
		if (null != object.getProperty("DepartmentName")) {
			bean.setDepartmentName(String.valueOf(object
					.getProperty("DepartmentName")));
		}
		if (null != object.getProperty("DeptRunningIndex")) {
			bean.setDeptRunningIndex(String.valueOf(object
					.getProperty("DeptRunningIndex")));
		}
		if (null != object.getProperty("DisabledByUserId")) {
			bean.setDisabledByUserId(String.valueOf(object
					.getProperty("DisabledByUserId")));
		}
		if (null != object.getProperty("DisabledByUserName")) {
			bean.setDisabledByUserName(String.valueOf(object
					.getProperty("DisabledByUserName")));
		}
		if (null != object.getProperty("DisabledDateTime")) {
			bean.setDisabledDateTime(String.valueOf(object
					.getProperty("DisabledDateTime")));
		}
		if (null != object.getProperty("DisabledReasonDesc")) {
			bean.setDisabledReasonDesc(String.valueOf(object
					.getProperty("DisabledReasonDesc")));
		}
		if (null != object.getProperty("DisabledReasonId")) {
			bean.setDisabledReasonId(String.valueOf(object
					.getProperty("DisabledReasonId")));
		}
		if (null != object.getProperty("DisplayIndexString")) {
			bean.setDisplayIndexString(String.valueOf(object
					.getProperty("DisplayIndexString")));
		}
		if (null != object.getProperty("IsItemFired")) {
			bean.setIsItemFired(String.valueOf(object
					.getProperty("IsItemFired")));
		}
		if (null != object.getProperty("IsItemFiredSuccessfully")) {
			bean.setIsItemFiredSuccessfully(String.valueOf(object
					.getProperty("IsItemFiredSuccessfully")));
		}
		if (null != object.getProperty("IsItemOnHold")) {
			bean.setIsItemOnHold(String.valueOf(object
					.getProperty("IsItemOnHold")));
		}
		if (null != object.getProperty("IsItemShow")) {
			bean.setIsItemShow(String.valueOf(object.getProperty("IsItemShow")));
		}
		if (null != object.getProperty("IsItemShowInKitchenChecklist")) {
			bean.setIsItemShowInKitchenChecklist(String.valueOf(object
					.getProperty("IsItemShowInKitchenChecklist")));
		}
		if (null != object.getProperty("IsLimitedItem")) {
			bean.setIsLimitedItem(String.valueOf(object
					.getProperty("IsLimitedItem")));
		}
		if (null != object.getProperty("IsLocalChangedItem")) {
			bean.setIsLocalChangedItem(String.valueOf(object
					.getProperty("IsLocalChangedItem")));
		}
		if (null != object.getProperty("IsModifier")) {
			bean.setIsModifier(String.valueOf(object.getProperty("IsModifier")));
		}
		if (null != object.getProperty("IsModifierConcatToParent")) {
			bean.setIsModifierConcatToParent(String.valueOf(object
					.getProperty("IsModifierConcatToParent")));
		}
		if (null != object.getProperty("IsNoPointEarnItem")) {
			bean.setIsNoPointEarnItem(String.valueOf(object
					.getProperty("IsNoPointEarnItem")));
		}
		if (null != object.getProperty("IsNonDiscountItem")) {
			bean.setIsNonDiscountItem(String.valueOf(object
					.getProperty("IsNonDiscountItem")));
		}
		if (null != object.getProperty("IsNonServiceChargeItem")) {
			bean.setIsNonServiceChargeItem(String.valueOf(object
					.getProperty("IsNonServiceChargeItem")));
		}
		if (null != object.getProperty("IsNonTaxableItem")) {
			bean.setIsNonTaxableItem(String.valueOf(object
					.getProperty("IsNonTaxableItem")));
		}
		if (null != object.getProperty("IsPointPaidItem")) {
			bean.setIsPointPaidItem(String.valueOf(object
					.getProperty("IsPointPaidItem")));
		}
		if (null != object.getProperty("IsPriceInPercentage")) {
			bean.setIsPriceInPercentage(String.valueOf(object
					.getProperty("IsPriceInPercentage")));
		}
		if (null != object.getProperty("IsPriceShow")) {
			bean.setIsPriceShow(String.valueOf(object
					.getProperty("IsPriceShow")));
		}
		if (null != object.getProperty("IsPrintLabel")) {
			bean.setIsPrintLabel(String.valueOf(object
					.getProperty("IsPrintLabel")));
		}
		if (null != object.getProperty("IsPrintLabelTakeaway")) {
			bean.setIsPrintLabelTakeaway(String.valueOf(object
					.getProperty("IsPrintLabelTakeaway")));
		}
		if (null != object.getProperty("IsPromoComboItem")) {
			bean.setIsPromoComboItem(String.valueOf(object
					.getProperty("IsPromoComboItem")));
		}
		if (null != object.getProperty("IsSubItem")) {
			bean.setIsSubItem(String.valueOf(object.getProperty("IsSubItem")));
		}
		if (null != object.getProperty("ItemCode")) {
			bean.setItemCode(String.valueOf(object.getProperty("ItemCode")));
		}
		if (null != object.getProperty("ItemFiredDateTime")) {
			bean.setItemFiredDateTime(String.valueOf(object
					.getProperty("ItemFiredDateTime")));
		}
		if (null != object.getProperty("ItemFiredUserId")) {
			bean.setItemFiredUserId(String.valueOf(object
					.getProperty("ItemFiredUserId")));
		}
		if (null != object.getProperty("ItemFiredUserName")) {
			bean.setItemFiredUserName(String.valueOf(object
					.getProperty("ItemFiredUserName")));
		}
		if (null != object.getProperty("ItemId")) {
			bean.setItemId(String.valueOf(object.getProperty("ItemId")));
		}
		if (null != object.getProperty("ItemName")) {
			bean.setItemName(String.valueOf(object.getProperty("ItemName")));
		}
		if (null != object.getProperty("ItemNameAlt")) {
			bean.setItemNameAlt(String.valueOf(object
					.getProperty("ItemNameAlt")));
		}
		if (null != object.getProperty("ItemNameAlt2")) {
			bean.setItemNameAlt2(String.valueOf(object
					.getProperty("ItemNameAlt2")));
		}
		if (null != object.getProperty("ItemNameAlt3")) {
			bean.setItemNameAlt3(String.valueOf(object
					.getProperty("ItemNameAlt3")));
		}
		if (null != object.getProperty("ItemNameAlt4")) {
			bean.setItemNameAlt4(String.valueOf(object
					.getProperty("ItemNameAlt4")));
		}
		if (null != object.getProperty("ItemOnHoldDateTime")) {
			bean.setItemOnHoldDateTime(String.valueOf(object
					.getProperty("ItemOnHoldDateTime")));
		}
		if (null != object.getProperty("ItemOnHoldUserId")) {
			bean.setItemOnHoldUserId(String.valueOf(object
					.getProperty("ItemOnHoldUserId")));
		}
		if (null != object.getProperty("ItemOnHoldUserName")) {
			bean.setItemOnHoldUserName(String.valueOf(object
					.getProperty("ItemOnHoldUserName")));
		}
		if (null != object.getProperty("ItemOrderRunningIndex")) {
			bean.setItemOrderRunningIndex(String.valueOf(object
					.getProperty("ItemOrderRunningIndex")));
		}
		if (null != object.getProperty("ItemPath")) {
			bean.setItemPath(String.valueOf(object.getProperty("ItemPath")));
		}
		if (null != object.getProperty("ItemSetRunningIndex")) {
			bean.setItemSetRunningIndex(String.valueOf(object
					.getProperty("ItemSetRunningIndex")));
		}
		if (null != object.getProperty("LocalPrinterName")) {
			bean.setLocalPrinterName(String.valueOf(object
					.getProperty("LocalPrinterName")));
		}
		if (null != object.getProperty("LocalPrinterName2")) {
			bean.setLocalPrinterName2(String.valueOf(object
					.getProperty("LocalPrinterName2")));
		}
		if (null != object.getProperty("LocalPrinterName3")) {
			bean.setLocalPrinterName3(String.valueOf(object
					.getProperty("LocalPrinterName3")));
		}
		if (null != object.getProperty("LocalPrinterName4")) {
			bean.setLocalPrinterName4(String.valueOf(object
					.getProperty("LocalPrinterName4")));
		}
		if (null != object.getProperty("LocalPrinterName5")) {
			bean.setLocalPrinterName5(String.valueOf(object
					.getProperty("LocalPrinterName5")));
		}
		if (null != object.getProperty("ModifiedBy")) {
			bean.setModifiedBy(String.valueOf(object.getProperty("ModifiedBy")));
		}
		if (null != object.getProperty("ModifiedDate")) {
			bean.setModifiedDate(String.valueOf(object
					.getProperty("ModifiedDate")));
		}
		if (null != object.getProperty("OrderDateTime")) {
			bean.setOrderDateTime(String.valueOf(object
					.getProperty("OrderDateTime")));
		}
		if (null != object.getProperty("OrderUserId")) {
			bean.setOrderUserId(String.valueOf(object
					.getProperty("OrderUserId")));
		}
		if (null != object.getProperty("OrderUserName")) {
			bean.setOrderUserName(String.valueOf(object
					.getProperty("OrderUserName")));
		}
		if (null != object.getProperty("ParentTxSalesDetailId")) {
			bean.setParentTxSalesDetailId(String.valueOf(object
					.getProperty("ParentTxSalesDetailId")));
		}
		if (null != object.getProperty("Point")) {
			bean.setPoint(String.valueOf(object.getProperty("Point")));
		}
		if (null != object.getProperty("PreviousTxSalesHeaderId")) {
			bean.setPreviousTxSalesHeaderId(String.valueOf(object
					.getProperty("PreviousTxSalesHeaderId")));
		}
		if (null != object.getProperty("Price")) {
			bean.setPrice(String.valueOf(object.getProperty("Price")));
		}
		if (null != object.getProperty("PrintedKitchen")) {
			bean.setPrintedKitchen(String.valueOf(object
					.getProperty("PrintedKitchen")));
		}
		if (null != object.getProperty("PrintedKitchenByUserId")) {
			bean.setPrintedKitchenByUserId(String.valueOf(object
					.getProperty("PrintedKitchenByUserId")));
		}
		if (null != object.getProperty("PrintedKitchenByUserName")) {
			bean.setPrintedKitchenByUserName(String.valueOf(object
					.getProperty("PrintedKitchenByUserName")));
		}
		if (null != object.getProperty("PrintedKitchenDateTime")) {
			bean.setPrintedKitchenDateTime(String.valueOf(object
					.getProperty("PrintedKitchenDateTime")));
		}
		if (null != object.getProperty("Qty")) {
			bean.setQty(String.valueOf(object.getProperty("Qty")));
		}
		if (null != object.getProperty("SeqNo")) {
			bean.setSeqNo(String.valueOf(object.getProperty("SeqNo")));
		}
		if (null != object.getProperty("SubItemLevel")) {
			bean.setSubItemLevel(String.valueOf(object
					.getProperty("SubItemLevel")));
		}
		if (null != object.getProperty("TakeawaySurcharge")) {
			bean.setTakeawaySurcharge(String.valueOf(object
					.getProperty("TakeawaySurcharge")));
		}
		if (null != object.getProperty("TxSalesDetailId")) {
			bean.setTxSalesDetailId(String.valueOf(object
					.getProperty("TxSalesDetailId")));
		}
		if (null != object.getProperty("TxSalesHeaderId")) {
			bean.setTxSalesHeaderId(String.valueOf(object
					.getProperty("TxSalesHeaderId")));
		}
		if (null != object.getProperty("Voided")) {
			bean.setVoided(String.valueOf(object.getProperty("Voided")));
		}

		return bean;
	}
}
