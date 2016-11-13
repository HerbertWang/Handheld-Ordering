package com.everyware.handheld.utils;

import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.everyware.handheld.bean.TxPaymentDto;
import com.everyware.handheld.bean.TxSalesDetailDto;
import com.everyware.handheld.bean.TxSalesDetailDtoNormal;
import com.everyware.handheld.bean.TxSalesHeaderDto;

public class FormatCommitSoapObject {

	public static SoapObject getFormatUserInfo() {
        SoapObject object = new SoapObject(null, "tem:loginUser");

        object.addProperty("pos:AccountId",
                ConstantUtils.userInfo.getAccountId());
        object.addProperty("pos:CardNo", ConstantUtils.userInfo.getCardNo());
        object.addProperty("pos:CreatedBy",
                ConstantUtils.userInfo.getCreatedBy());
        object.addProperty("pos:CreatedDate",
                ConstantUtils.userInfo.getCreatedDate());

        object.addProperty("pos:EffectiveDateFrom",
                ConstantUtils.userInfo.getEffectiveDateFrom());
        object.addProperty("pos:EffectiveDateTo",
                ConstantUtils.userInfo.getEffectiveDateTo());

        object.addProperty("pos:EnableCardNoLogin",
                ConstantUtils.userInfo.getEnableCardNoLogin());
        object.addProperty("pos:EnableStaffCodeLogin",
                ConstantUtils.userInfo.getEnableStaffCodeLogin());
        object.addProperty("pos:EnableUserIdLogin",
                ConstantUtils.userInfo.getEnableUserIdLogin());
        object.addProperty("pos:Enabled", ConstantUtils.userInfo.getEnabled());
        object.addProperty("pos:ModifiedBy",
                ConstantUtils.userInfo.getModifiedBy());
        object.addProperty("pos:ModifiedDate",
                ConstantUtils.userInfo.getModifiedDate());
        object.addProperty("pos:ShopId", ConstantUtils.userInfo.getShopId());
        object.addProperty("pos:StaffCode",
                ConstantUtils.userInfo.getStaffCode());
        object.addProperty("pos:UserAltName",
                ConstantUtils.userInfo.getUserAltName());
        object.addProperty("pos:UserId", ConstantUtils.userInfo.getUserId());
        object.addProperty("pos:UserName", ConstantUtils.userInfo.getUserName());

        return object;
	}

	public static SoapObject getFormatTxSalesHeaderDto(TxSalesHeaderDto dto, TxPaymentDto payDto, TxSalesDetailDtoNormal detailDto) {
		SoapObject soapObject = new SoapObject(null, "tem:txSalesHeaderDto");
		soapObject.addProperty("pos:AmountChange", dto.getAmountChange());
		soapObject.addProperty("pos:AmountDiscount", dto.getAmountDiscount());
		soapObject.addProperty("pos:AmountMinChargeOffset", dto.getAmountMinChargeOffset());
		soapObject.addProperty("pos:AmountPaid", dto.getAmountPaid());
		soapObject.addProperty("pos:AmountRounding", dto.getAmountRounding());
		soapObject.addProperty("pos:AmountServiceCharge", dto.getAmountServiceCharge());
		soapObject.addProperty("pos:AmountSubtotal", dto.getAmountSubtotal());
		soapObject.addProperty("pos:AmountTaxation", dto.getAmountTaxation());
		soapObject.addProperty("pos:AmountTips", dto.getAmountTips());
		soapObject.addProperty("pos:AmountTotal", dto.getAmountTotal());
		soapObject.addProperty("pos:CashDrawerCode", dto.getCashDrawerCode());
		soapObject.addProperty("pos:CashierDatetime", dto.getCashierDatetime());
		soapObject.addProperty("pos:CashierPrinterName", dto.getCashierPrinterName());
		soapObject.addProperty("pos:CashierUserId", dto.getCashierUserId());
		soapObject.addProperty("pos:CashierUserName", dto.getCashierUserName());
        if(null == dto.getCheckinDatetime() || "" == dto.getCheckinDatetime())
            soapObject.addProperty("pos:CheckinDatetime", CommonUtils.getCurrentDateTime());
        else
            soapObject.addProperty("pos:CheckinDatetime", dto.getCheckinDatetime());
		soapObject.addProperty("pos:CheckinUserId", ConstantUtils.userInfo.getUserId());
		soapObject.addProperty("pos:CheckinUserName", ConstantUtils.userInfo.getUserName());
		soapObject.addProperty("pos:CheckoutDatetime", dto.getCheckoutDatetime());
		soapObject.addProperty("pos:CheckoutUserId", dto.getCheckoutUserId());
		soapObject.addProperty("pos:CheckoutUserName", dto.getCheckoutUserName());
		soapObject.addProperty("pos:CompleteDatetime", dto.getCompleteDatetime());
		soapObject.addProperty("pos:CusCount", dto.getCusCount());
		soapObject.addProperty("pos:CusName", dto.getCusName());
		soapObject.addProperty("pos:DeliveryUserName", dto.getDeliveryUserName());
		soapObject.addProperty("pos:DisabledByUserId", dto.getDisabledByUserId());
		soapObject.addProperty("pos:DisabledByUserName", dto.getDisabledByUserName());
		soapObject.addProperty("pos:DisabledDateTime", dto.getDisabledDateTime());
		soapObject.addProperty("pos:DisabledReasonDesc", dto.getDisabledReasonDesc());
		soapObject.addProperty("pos:DisabledReasonId", dto.getDisabledReasonId());
		soapObject.addProperty("pos:DiscountByUserId", dto.getDiscountByUserId());
		soapObject.addProperty("pos:DiscountByUserName", dto.getDiscountByUserName());
		soapObject.addProperty("pos:DiscountId", dto.getDiscountId());
		soapObject.addProperty("pos:DiscountName", dto.getDiscountName());
        soapObject.addProperty("pos:Enabled", "true");
		/*if(ConstantUtils.isEnabled) {
			soapObject.addProperty("pos:Enabled", "true");
		} else {
			soapObject.addProperty("pos:Enabled", "false");
		}*/
		soapObject.addProperty("pos:IsCompleted", dto.getIsCompleted());
        soapObject.addProperty("pos:IsCurrentTx", "true");
		/*if(ConstantUtils.isEnabled) {
			soapObject.addProperty("pos:IsCurrentTx", "false");
		} else {
			soapObject.addProperty("pos:IsCurrentTx", "true");
		}*/
		soapObject.addProperty("pos:IsMemberTx", dto.getIsCurrentTx());
		soapObject.addProperty("pos:IsMinChargeOffsetWaived", dto.getIsMemberTx());
		soapObject.addProperty("pos:IsMinChargeTx", dto.getIsMinChargeOffsetWaived());
		soapObject.addProperty("pos:IsTakeAway", dto.getIsTakeAway());
		soapObject.addProperty("pos:IsTimeLimited", dto.getIsTimeLimited());
		soapObject.addProperty("pos:Line1", dto.getLine1());
		soapObject.addProperty("pos:Line2", dto.getLine2());
		soapObject.addProperty("pos:Line3", dto.getLine3());
		soapObject.addProperty("pos:Line4", dto.getLine4());
		soapObject.addProperty("pos:Line5", dto.getLine5());
		soapObject.addProperty("pos:MemberDeductAmount", dto.getMemberDeductAmount());
		soapObject.addProperty("pos:MemberTypeName", dto.getMemberTypeName());
        if(null == dto.getModifiedDate()) {
            soapObject.addProperty("pos:ModifiedDate", CommonUtils.getCurrentDateTime());
        } else {
            soapObject.addProperty("pos:ModifiedDate", dto.getModifiedDate());
        }
		soapObject.addProperty("pos:OclDeviceNum", dto.getOclDeviceNum());
		soapObject.addProperty("pos:OclNum", dto.getOclNum());
		soapObject.addProperty("pos:OclRemainValue", dto.getOclRemainValue());
        // 20150330 - Michael - temp disabled
		//soapObject.addProperty("pos:PaymentMethodId", dto.getPaymentMethodId());
		soapObject.addProperty("pos:PhoneNum", dto.getPhoneNum());
		soapObject.addProperty("pos:PreviousTableCode", dto.getPreviousTableCode());
		soapObject.addProperty("pos:PreviousTableId", dto.getPreviousTableId());
		soapObject.addProperty("pos:ReceiptNo", dto.getReceiptNo());
		soapObject.addProperty("pos:Remark1", dto.getRemark1());
		soapObject.addProperty("pos:Remark2", dto.getRemark2());
		soapObject.addProperty("pos:Remark3", dto.getRemark3());
		soapObject.addProperty("pos:Remark4", dto.getRemark4());
		soapObject.addProperty("pos:Remark5", dto.getRemark5());
		soapObject.addProperty("pos:SectionId", ConstantUtils.sectionId);
		soapObject.addProperty("pos:SectionName", ConstantUtils.sectionName);
		soapObject.addProperty("pos:ServiceChargeId", dto.getServiceChargeId());
		soapObject.addProperty("pos:ServiceChargeName", dto.getServiceChargeName());
		soapObject.addProperty("pos:ShopId", ConstantUtils.userInfo.getShopId());
		soapObject.addProperty("pos:TableCode", ConstantUtils.mTableDto.getTableCode());
		soapObject.addProperty("pos:TableId", ConstantUtils.mTableDto.getTableId());
		soapObject.addProperty("pos:TakeAwayRunningIndex", dto.getTakeAwayRunningIndex());
		soapObject.addProperty("pos:TakeoutDatetime", dto.getTakeoutDatetime());
        // 20150330 - Michael - temp disabled
		//soapObject.addProperty("pos:TaxationId", ((dto.getTaxationId().equals("0")) ? dto.getTaxationId() : null));
		soapObject.addProperty("pos:TaxationName", dto.getTaxationName());
		soapObject.addProperty("pos:TimeLimitedMinutes", dto.getTimeLimitedMinutes());
		soapObject.addProperty("pos:TxChecked", dto.getTxChecked());
        // 20150330 - Michael - temp disabled
		//soapObject.addProperty("pos:TxCode", dto.getTxCode());
        if("0".equals(dto.getTxSalesHeaderId())){
            // do nothing
        } else {
            soapObject.addProperty("pos:TxCode", dto.getTxCode());
        }
		soapObject.addProperty("pos:TxCompleted", dto.getTxCompleted());
		soapObject.addProperty("pos:TxDate", CommonUtils.getCurrentDate());
		
		// SoapObject mSoapObject = new SoapObject(null,
		// "pos:TxPaymentDtoList");
		// SoapObject object = new SoapObject(null, "pos:TxPaymentDto");
		// object.addProperty("pos:AccountId", payDto.getAccountId());
		// object.addProperty("pos:OclDeviceNum", payDto.getOclDeviceNum());
		// object.addProperty("pos:OclNum", payDto.getOclNum());
		// object.addProperty("pos:OclRemainValue", payDto.getOclRemainValue());
		// object.addProperty("pos:PaidAmount", payDto.getPaidAmount());
		// object.addProperty("pos:PaymentMethodId",
		// payDto.getPaymentMethodId());
		// object.addProperty("pos:PaymentMethodName",
		// payDto.getPaymentMethodName());
		// object.addProperty("pos:ShopId", payDto.getShopId());
		// object.addProperty("pos:TotalAmount", payDto.getTotalAmount());
		// object.addProperty("pos:TxPaymentId", payDto.getTxPaymentId());
		// object.addProperty("pos:TxSalesHeaderId",
		// payDto.getTxSalesHeaderId());
		// mSoapObject.addSoapObject(object);
		// soapObject.addSoapObject(mSoapObject);
		
//		SoapObject mObject = new SoapObject(null, "pos:TxSalesDetailDtoList");
//		mObject.addSoapObject(getFormatTxSalesDetailDto(detailDto));
//		soapObject.addSoapObject(mObject);
		
//		soapObject.addProperty("pos:TxPaymentDtoList", null);
		soapObject.addProperty("pos:TxSalesDetailDtoList", null);
		
		soapObject.addProperty("pos:TxSalesHeaderId", dto.getTxSalesHeaderId());
		soapObject.addProperty("pos:Voided", dto.getVoided());
		soapObject.addProperty("pos:WorkdayPeriodDetailId", dto.getWorkdayPeriodDetailId());
		soapObject.addProperty("pos:WorkdayPeriodName", dto.getWorkdayPeriodName());
		return soapObject;
	}

	public static SoapObject getFormatTxSalesDetailDtoList(
			List<TxSalesDetailDto> dto, boolean isLocalChange) {
		SoapObject soapObject = new SoapObject(null, "tem:txSalesDetailDtoList");

		for (int i = 0; i < dto.size(); i++) {
            if(isLocalChange &&  "false".equals(dto.get(i).getIsLocalChangedItem()))
            {
                continue;
            }
			SoapObject object = new SoapObject(null, "pos:TxSalesDetailDto");
			object.addProperty("pos:Amount", dto.get(i).getAmount());
			object.addProperty("pos:AmountPoint", dto.get(i).getAmountPoint());
			object.addProperty("pos:CategoryId", dto.get(i).getCategoryId());
			object.addProperty("pos:ChaseCount", dto.get(i).getChaseCount());
			object.addProperty("pos:ChaseDateTime", dto.get(i).getChaseDateTime());
			object.addProperty("pos:ChaseUserId", dto.get(i).getChaseUserId());
			object.addProperty("pos:ChaseUserName", dto.get(i).getChaseUserName());
			object.addProperty("pos:CreatedBy", dto.get(i).getCreatedBy());
			object.addProperty("pos:CreatedDate", dto.get(i).getCreatedDate());
			object.addProperty("pos:DepartmentId", dto.get(i).getDepartmentId());
			object.addProperty("pos:DepartmentName", dto.get(i).getDepartmentName());
			object.addProperty("pos:DeptRunningIndex", dto.get(i).getDeptRunningIndex());
			object.addProperty("pos:DisabledByUserId", dto.get(i).getDisabledByUserId());
			object.addProperty("pos:DisabledByUserName", dto.get(i).getDisabledByUserName());
			object.addProperty("pos:DisabledDateTime", dto.get(i).getDisabledDateTime());
			object.addProperty("pos:DisabledReasonDesc", dto.get(i).getDisabledReasonDesc());
			object.addProperty("pos:DisabledReasonId", dto.get(i).getDisabledReasonId());
			object.addProperty("pos:DisplayIndexString", dto.get(i).getDisplayIndexString());
			object.addProperty("pos:Enabled", dto.get(i).getEnabled());
			object.addProperty("pos:IsItemFired", dto.get(i).getIsItemFired());
			object.addProperty("pos:IsItemFiredSuccessfully", dto.get(i).getIsItemFiredSuccessfully());
			object.addProperty("pos:IsItemOnHold", dto.get(i).getIsItemOnHold());
			object.addProperty("pos:IsItemShow", dto.get(i).getIsItemShow());
			object.addProperty("pos:IsItemShowInKitchenChecklist", dto.get(i).getIsItemShowInKitchenChecklist());
			object.addProperty("pos:IsLimitedItem", dto.get(i).getIsLimitedItem());
			object.addProperty("pos:IsLocalChangedItem", dto.get(i).getIsLocalChangedItem());
			object.addProperty("pos:IsModifier", dto.get(i).getIsModifier());
			object.addProperty("pos:IsModifierConcatToParent", dto.get(i).getIsModifierConcatToParent());
			object.addProperty("pos:IsNoPointEarnItem", dto.get(i).getIsNoPointEarnItem());
			object.addProperty("pos:IsNonDiscountItem", dto.get(i).getIsNonDiscountItem());
			object.addProperty("pos:IsNonServiceChargeItem", dto.get(i).getIsNonServiceChargeItem());
			object.addProperty("pos:IsNonTaxableItem", dto.get(i).getIsNonTaxableItem());
			object.addProperty("pos:IsPointPaidItem", dto.get(i).getIsPointPaidItem());
			object.addProperty("pos:IsPriceInPercentage", dto.get(i).getIsPriceInPercentage());
			object.addProperty("pos:IsPriceShow", dto.get(i).getIsPriceShow());
			object.addProperty("pos:IsPrintLabel", dto.get(i).getIsPrintLabel());
			object.addProperty("pos:IsPrintLabelTakeaway", dto.get(i).getIsPrintLabelTakeaway());
			object.addProperty("pos:IsPromoComboItem", dto.get(i).getIsPromoComboItem());
			object.addProperty("pos:IsSubItem", dto.get(i).getIsSubItem());
			object.addProperty("pos:ItemCode", dto.get(i).getItemCode());
			object.addProperty("pos:ItemFiredDateTime", dto.get(i).getItemFiredDateTime());
			object.addProperty("pos:ItemFiredUserId", dto.get(i).getItemFiredUserId());
			object.addProperty("pos:ItemFiredUserName", dto.get(i).getItemFiredUserName());
			object.addProperty("pos:ItemId", dto.get(i).getItemId());
			object.addProperty("pos:ItemName", dto.get(i).getItemName());
			object.addProperty("pos:ItemNameAlt", null);
			object.addProperty("pos:ItemNameAlt2", null);
			object.addProperty("pos:ItemNameAlt3", null);
			object.addProperty("pos:ItemNameAlt4", null);
			object.addProperty("pos:ItemOnHoldDateTime", dto.get(i).getItemOnHoldDateTime());
			object.addProperty("pos:ItemOnHoldUserId", dto.get(i).getItemOnHoldUserId());
			object.addProperty("pos:ItemOnHoldUserName", dto.get(i).getItemOnHoldUserName());
			object.addProperty("pos:ItemOrderRunningIndex", dto.get(i).getItemOrderRunningIndex());
			object.addProperty("pos:ItemPath", dto.get(i).getItemPath());
			object.addProperty("pos:ItemSetRunningIndex", dto.get(i).getItemSetRunningIndex());
			object.addProperty("pos:LocalPrinterName", dto.get(i).getLocalPrinterName());
			object.addProperty("pos:LocalPrinterName2", null);
			object.addProperty("pos:LocalPrinterName3", null);
			object.addProperty("pos:LocalPrinterName4", null);
			object.addProperty("pos:LocalPrinterName5", null);
			object.addProperty("pos:ModifiedBy", dto.get(i).getModifiedBy());
			object.addProperty("pos:ModifiedDate", dto.get(i).getModifiedDate());
			object.addProperty("pos:OrderDateTime", dto.get(i).getOrderDateTime());
			object.addProperty("pos:OrderUserId", dto.get(i).getOrderUserId());
			object.addProperty("pos:OrderUserName", dto.get(i).getOrderUserName());
			object.addProperty("pos:ParentTxSalesDetailId", dto.get(i).getParentTxSalesDetailId());
			object.addProperty("pos:Point", dto.get(i).getPoint());
			object.addProperty("pos:PreviousTxSalesHeaderId", dto.get(i).getPreviousTxSalesHeaderId());
			object.addProperty("pos:Price", dto.get(i).getPrice());
			object.addProperty("pos:PrintedKitchen", dto.get(i).getPrintedKitchen());
			object.addProperty("pos:PrintedKitchenByUserId", dto.get(i).getPrintedKitchenByUserId());
			object.addProperty("pos:PrintedKitchenByUserName", dto.get(i).getPrintedKitchenByUserName());
			object.addProperty("pos:PrintedKitchenDateTime", dto.get(i).getPrintedKitchenDateTime());
			object.addProperty("pos:Qty", dto.get(i).getQty());
			object.addProperty("pos:SeqNo", dto.get(i).getSeqNo());
			object.addProperty("pos:SubItemLevel", dto.get(i).getSubItemLevel());
			object.addProperty("pos:TakeawaySurcharge", dto.get(i).getTakeawaySurcharge());
			object.addProperty("pos:TxSalesDetailId", dto.get(i).getTxSalesDetailId());
			object.addProperty("pos:TxSalesHeaderId", dto.get(i).getTxSalesHeaderId());
			object.addProperty("pos:Voided", dto.get(i).getVoided());
			soapObject.addSoapObject(object);
		}

		return soapObject;
	}
	
	public static SoapObject getFormatTxSalesDetailDto(
			TxSalesDetailDtoNormal dto) {
		SoapObject object = new SoapObject(null, "pos:TxSalesDetailDto");
		object.addProperty("pos:Amount", dto.getAmount());
		object.addProperty("pos:AmountPoint", dto.getAmountPoint());
		object.addProperty("pos:CategoryId", dto.getCategoryId());
		object.addProperty("pos:ChaseCount", dto.getChaseCount());
		object.addProperty("pos:ChaseDateTime", dto.getChaseDateTime());
		object.addProperty("pos:ChaseUserId", dto.getChaseUserId());
		object.addProperty("pos:ChaseUserName", dto.getChaseUserName());
		object.addProperty("pos:CreatedBy", dto.getCreatedBy());
		object.addProperty("pos:CreatedDate", dto.getCreatedDate());
		object.addProperty("pos:DepartmentId", dto.getDepartmentId());
		object.addProperty("pos:DepartmentName", dto.getDepartmentName());
		object.addProperty("pos:DeptRunningIndex", dto.getDeptRunningIndex());
		object.addProperty("pos:DisabledByUserId", dto.getDisabledByUserId());
		object.addProperty("pos:DisabledByUserName", dto.getDisabledByUserName());
		object.addProperty("pos:DisabledDateTime", dto.getDisabledDateTime());
		object.addProperty("pos:DisabledReasonDesc", dto.getDisabledReasonDesc());
		object.addProperty("pos:DisabledReasonId", dto.getDisabledReasonId());
		object.addProperty("pos:DisplayIndexString", dto.getDisplayIndexString());
		object.addProperty("pos:Enabled", dto.getEnabled());
		object.addProperty("pos:IsItemFired", dto.getIsItemFired());
		object.addProperty("pos:IsItemFiredSuccessfully", dto.getIsItemFiredSuccessfully());
		object.addProperty("pos:IsItemOnHold", dto.getIsItemOnHold());
		object.addProperty("pos:IsItemShow", dto.getIsItemShow());
		object.addProperty("pos:IsItemShowInKitchenChecklist", dto.getIsItemShowInKitchenChecklist());
		object.addProperty("pos:IsLimitedItem", dto.getIsLimitedItem());
		object.addProperty("pos:IsLocalChangedItem", dto.getIsLocalChangedItem());
		object.addProperty("pos:IsModifier", dto.getIsModifier());
		object.addProperty("pos:IsModifierConcatToParent", dto.getIsModifierConcatToParent());
		object.addProperty("pos:IsNoPointEarnItem", dto.getIsNoPointEarnItem());
		object.addProperty("pos:IsNonDiscountItem", dto.getIsNonDiscountItem());
		object.addProperty("pos:IsNonServiceChargeItem", dto.getIsNonServiceChargeItem());
		object.addProperty("pos:IsNonTaxableItem", dto.getIsNonTaxableItem());
		object.addProperty("pos:IsPointPaidItem", dto.getIsPointPaidItem());
		object.addProperty("pos:IsPriceInPercentage", dto.getIsPriceInPercentage());
		object.addProperty("pos:IsPriceShow", dto.getIsPriceShow());
		object.addProperty("pos:IsPrintLabel", dto.getIsPrintLabel());
		object.addProperty("pos:IsPrintLabelTakeaway", dto.getIsPrintLabelTakeaway());
		object.addProperty("pos:IsPromoComboItem", dto.getIsPromoComboItem());
		object.addProperty("pos:IsSubItem", dto.getIsSubItem());
		object.addProperty("pos:ItemCode", dto.getItemCode());
		object.addProperty("pos:ItemFiredDateTime", dto.getItemFiredDateTime());
		object.addProperty("pos:ItemFiredUserId", dto.getItemFiredUserId());
		object.addProperty("pos:ItemFiredUserName", dto.getItemFiredUserName());
		object.addProperty("pos:ItemId", dto.getItemId());
		object.addProperty("pos:ItemName", dto.getItemName());
		object.addProperty("pos:ItemNameAlt", dto.getItemNameAlt());
		object.addProperty("pos:ItemNameAlt2", dto.getItemNameAlt2());
		object.addProperty("pos:ItemNameAlt3", dto.getItemNameAlt3());
		object.addProperty("pos:ItemNameAlt4", dto.getItemNameAlt4());
		object.addProperty("pos:ItemOnHoldDateTime", dto.getItemOnHoldDateTime());
		object.addProperty("pos:ItemOnHoldUserId", dto.getItemOnHoldUserId());
		object.addProperty("pos:ItemOnHoldUserName", dto.getItemOnHoldUserName());
		object.addProperty("pos:ItemOrderRunningIndex", dto.getItemOrderRunningIndex());
		object.addProperty("pos:ItemPath", dto.getItemPath());
		object.addProperty("pos:ItemSetRunningIndex", dto.getItemSetRunningIndex());
		object.addProperty("pos:LocalPrinterName", dto.getLocalPrinterName());
		object.addProperty("pos:LocalPrinterName2", dto.getLocalPrinterName2());
		object.addProperty("pos:LocalPrinterName3", dto.getLocalPrinterName3());
		object.addProperty("pos:LocalPrinterName4", dto.getLocalPrinterName4());
		object.addProperty("pos:LocalPrinterName5", dto.getLocalPrinterName5());
		object.addProperty("pos:ModifiedBy", dto.getModifiedBy());
		object.addProperty("pos:ModifiedDate", dto.getModifiedDate());
		object.addProperty("pos:OrderDateTime", dto.getOrderDateTime());
		object.addProperty("pos:OrderUserId", dto.getOrderUserId());
		object.addProperty("pos:OrderUserName", dto.getOrderUserName());
		object.addProperty("pos:ParentTxSalesDetailId", dto.getParentTxSalesDetailId());
		object.addProperty("pos:Point", dto.getPoint());
		object.addProperty("pos:PreviousTxSalesHeaderId", dto.getPreviousTxSalesHeaderId());
		object.addProperty("pos:Price", dto.getPrice());
		object.addProperty("pos:PrintedKitchen", dto.getPrintedKitchen());
		object.addProperty("pos:PrintedKitchenByUserId", dto.getPrintedKitchenByUserId());
		object.addProperty("pos:PrintedKitchenByUserName", dto.getPrintedKitchenByUserName());
		object.addProperty("pos:PrintedKitchenDateTime", dto.getPrintedKitchenDateTime());
		object.addProperty("pos:Qty", dto.getQty());
		object.addProperty("pos:SeqNo", dto.getSeqNo());
		object.addProperty("pos:SubItemLevel", dto.getSubItemLevel());
		object.addProperty("pos:TakeawaySurcharge", dto.getTakeawaySurcharge());
		object.addProperty("pos:TxSalesDetailId", dto.getTxSalesDetailId());
		object.addProperty("pos:TxSalesHeaderId", dto.getTxSalesHeaderId());
		object.addProperty("pos:Voided", dto.getVoided());
		
		return object;
	}
}
