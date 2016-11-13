package com.everyware.handheld.utils;

import org.ksoap2.serialization.SoapObject;

import com.everyware.handheld.bean.TxSalesHeaderDto;

/**
 * 
 * @author ALEX
 * 
 */
public class GetTxSalesHeaderDto {

	public static TxSalesHeaderDto parseHeader(SoapObject object) {
		TxSalesHeaderDto bean = new TxSalesHeaderDto();
		if (null != object.getProperty("AmountChange")) {
			bean.setAmountChange(String.valueOf(object
					.getProperty("AmountChange")));
		}
		if (null != object.getProperty("AmountDiscount")) {
			bean.setAmountDiscount(String.valueOf(object
					.getProperty("AmountDiscount")));
		}
		if (null != object.getProperty("AmountMinChargeOffset")) {
			bean.setAmountMinChargeOffset(String.valueOf(object
					.getProperty("AmountMinChargeOffset")));
		}
		if (null != object.getProperty("AmountPaid")) {
			bean.setAmountPaid(String.valueOf(object.getProperty("AmountPaid")));
		}
		if (null != object.getProperty("AmountRounding")) {
			bean.setAmountRounding(String.valueOf(object
					.getProperty("AmountRounding")));
		}
		if (null != object.getProperty("AmountServiceCharge")) {
			bean.setAmountServiceCharge(String.valueOf(object
					.getProperty("AmountServiceCharge")));
		}
		if (null != object.getProperty("AmountSubtotal")) {
			bean.setAmountSubtotal(String.valueOf(object
					.getProperty("AmountSubtotal")));
		}
		if (null != object.getProperty("AmountTaxation")) {
			bean.setAmountTaxation(String.valueOf(object
					.getProperty("AmountTaxation")));
		}
		if (null != object.getProperty("AmountTips")) {
			bean.setAmountTips(String.valueOf(object.getProperty("AmountTips")));
		}
		if (null != object.getProperty("AmountTotal")) {
			bean.setAmountTotal(String.valueOf(object
					.getProperty("AmountTotal")));
		}
		if (null != object.getProperty("CashDrawerCode")) {
			bean.setCashDrawerCode(String.valueOf(object
					.getProperty("CashDrawerCode")));
		}
		if (null != object.getProperty("CashierDatetime")) {
			bean.setCashierDatetime(String.valueOf(object
					.getProperty("CashierDatetime")));
		}
		if (null != object.getProperty("CashierPrinterName")) {
			bean.setCashierPrinterName(String.valueOf(object
					.getProperty("CashierPrinterName")));
		}
		if (null != object.getProperty("CashierUserId")) {
			bean.setCashierUserId(String.valueOf(object
					.getProperty("CashierUserId")));
		}
		if (null != object.getProperty("CashierUserName")) {
			bean.setCashierUserName(String.valueOf(object
					.getProperty("CashierUserName")));
		}
		if (null != object.getProperty("CheckinDatetime")) {
			bean.setCheckinDatetime(String.valueOf(object
					.getProperty("CheckinDatetime")));
		}
		if (null != object.getProperty("CheckinUserId")) {
			bean.setCheckinUserId(String.valueOf(object
					.getProperty("CheckinUserId")));
		}
		if (null != object.getProperty("CheckinUserName")) {
			bean.setCheckinUserName(String.valueOf(object
					.getProperty("CheckinUserName")));
		}
		if (null != object.getProperty("CheckoutDatetime")) {
			bean.setCheckoutDatetime(String.valueOf(object
					.getProperty("CheckoutDatetime")));
		}
		if (null != object.getProperty("CheckoutUserId")) {
			bean.setCheckoutUserId(String.valueOf(object
					.getProperty("CheckoutUserId")));
		}
		if (null != object.getProperty("CheckoutUserName")) {
			bean.setCheckoutUserName(String.valueOf(object
					.getProperty("CheckoutUserName")));
		}
		if (null != object.getProperty("CompleteDatetime")) {
			bean.setCompleteDatetime(String.valueOf(object
					.getProperty("CompleteDatetime")));
		}
		if (null != object.getProperty("CusCount")) {
			bean.setCusCount(String.valueOf(object.getProperty("CusCount")));
		}
		if (null != object.getProperty("CusName")) {
			bean.setCusName(String.valueOf(object.getProperty("CusName")));
		}
		if (null != object.getProperty("DeliveryUserName")) {
			bean.setDeliveryUserName(String.valueOf(object
					.getProperty("DeliveryUserName")));
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
		if (null != object.getProperty("DiscountByUserId")) {
			bean.setDiscountByUserId(String.valueOf(object
					.getProperty("DiscountByUserId")));
		}
		if (null != object.getProperty("DiscountByUserName")) {
			bean.setDiscountByUserName(String.valueOf(object
					.getProperty("DiscountByUserName")));
		}
		if (null != object.getProperty("DiscountId")) {
			bean.setDiscountId(String.valueOf(object.getProperty("DiscountId")));
		}
		if (null != object.getProperty("DiscountName")) {
			bean.setDiscountName(String.valueOf(object
					.getProperty("DiscountName")));
		}
		if (null != object.getProperty("Enabled")) {
			bean.setEnabled(String.valueOf(object.getProperty("Enabled")));
		}
		if (null != object.getProperty("IsCompleted")) {
			bean.setIsCompleted(String.valueOf(object
					.getProperty("IsCompleted")));
		}
		if (null != object.getProperty("IsCurrentTx")) {
			bean.setIsCurrentTx(String.valueOf(object
					.getProperty("IsCurrentTx")));
		}
		if (null != object.getProperty("IsMemberTx")) {
			bean.setIsMemberTx(String.valueOf(object.getProperty("IsMemberTx")));
		}
		if (null != object.getProperty("IsMinChargeOffsetWaived")) {
			bean.setIsMinChargeOffsetWaived(String.valueOf(object
					.getProperty("IsMinChargeOffsetWaived")));
		}
		if (null != object.getProperty("IsMinChargeTx")) {
			bean.setIsMinChargeTx(String.valueOf(object
					.getProperty("IsMinChargeTx")));
		}
		if (null != object.getProperty("IsTakeAway")) {
			bean.setIsTakeAway(String.valueOf(object.getProperty("IsTakeAway")));
		}
		if (null != object.getProperty("IsTimeLimited")) {
			bean.setIsTimeLimited(String.valueOf(object
					.getProperty("IsTimeLimited")));
		}
		if (null != object.getProperty("Line1")) {
			bean.setLine1(String.valueOf(object.getProperty("Line1")));
		}
		if (null != object.getProperty("Line2")) {
			bean.setLine2(String.valueOf(object.getProperty("Line2")));
		}
		if (null != object.getProperty("Line3")) {
			bean.setLine3(String.valueOf(object.getProperty("Line3")));
		}
		if (null != object.getProperty("Line4")) {
			bean.setLine4(String.valueOf(object.getProperty("Line4")));
		}
		if (null != object.getProperty("Line5")) {
			bean.setLine5(String.valueOf(object.getProperty("Line5")));
		}
		if (null != object.getProperty("MemberDeductAmount")) {
			bean.setMemberDeductAmount(String.valueOf(object
					.getProperty("MemberDeductAmount")));
		}
		if (null != object.getProperty("MemberTypeName")) {
			bean.setMemberTypeName(String.valueOf(object
					.getProperty("MemberTypeName")));
		}
		if (null != object.getProperty("ModifiedDate")
				&& !"0001-01-01T00:00:00".equals(object.getProperty(
						"ModifiedDate").toString())) {
			bean.setModifiedDate(String.valueOf(object
					.getProperty("ModifiedDate")));
		}
		if (null != object.getProperty("OclDeviceNum")) {
			bean.setOclDeviceNum(String.valueOf(object
					.getProperty("OclDeviceNum")));
		}
		if (null != object.getProperty("OclNum")) {
			bean.setOclNum(String.valueOf(object.getProperty("OclNum")));
		}
		if (null != object.getProperty("OclRemainValue")) {
			bean.setOclRemainValue(String.valueOf(object
					.getProperty("OclRemainValue")));
		}
        // 20150330 - Michael - temp disabled
		/*if (null != object.getProperty("PaymentMethodId")) {
			bean.setPaymentMethodId(String.valueOf(object
					.getProperty("PaymentMethodId")));
		}*/
		if (null != object.getProperty("PhoneNum")) {
			bean.setPhoneNum(String.valueOf(object.getProperty("PhoneNum")));
		}
		if (null != object.getProperty("PreviousTableCode")) {
			bean.setPreviousTableCode(String.valueOf(object
					.getProperty("PreviousTableCode")));
		}
		if (null != object.getProperty("PreviousTableId")) {
			bean.setPreviousTableId(String.valueOf(object
					.getProperty("PreviousTableId")));
		}
		if (null != object.getProperty("ReceiptNo")) {
			bean.setReceiptNo(String.valueOf(object.getProperty("ReceiptNo")));
		}
		if (null != object.getProperty("Remark1")) {
			bean.setRemark1(String.valueOf(object.getProperty("Remark1")));
		}
		if (null != object.getProperty("Remark2")) {
			bean.setRemark2(String.valueOf(object.getProperty("Remark2")));
		}
		if (null != object.getProperty("Remark3")) {
			bean.setRemark3(String.valueOf(object.getProperty("Remark3")));
		}
		if (null != object.getProperty("Remark4")) {
			bean.setRemark4(String.valueOf(object.getProperty("Remark4")));
		}
		if (null != object.getProperty("Remark5")) {
			bean.setRemark5(String.valueOf(object.getProperty("Remark5")));
		}
		if (null != object.getProperty("SectionId")) {
			bean.setSectionId(String.valueOf(object.getProperty("SectionId")));
		}
		if (null != object.getProperty("SectionName")) {
			bean.setSectionName(String.valueOf(object
					.getProperty("SectionName")));
		}
		if (null != object.getProperty("ServiceChargeId")) {
			bean.setServiceChargeId(String.valueOf(object
					.getProperty("ServiceChargeId")));
		}
		if (null != object.getProperty("ServiceChargeName")) {
			bean.setServiceChargeName(String.valueOf(object
					.getProperty("ServiceChargeName")));
		}
		if (null != object.getProperty("ShopId")) {
			bean.setShopId(String.valueOf(object.getProperty("ShopId")));
		}
		if (null != object.getProperty("TableCode")) {
			bean.setTableCode(String.valueOf(object.getProperty("TableCode")));
		}
		if (null != object.getProperty("TableId")) {
			bean.setTableId(String.valueOf(object.getProperty("TableId")));
		}
		if (null != object.getProperty("TakeAwayRunningIndex")) {
			bean.setTakeAwayRunningIndex(String.valueOf(object
					.getProperty("TakeAwayRunningIndex")));
		}
		if (null != object.getProperty("TakeoutDatetime")) {
			bean.setTakeoutDatetime(String.valueOf(object
					.getProperty("TakeoutDatetime")));
		}
        // 20150330 - Michael - temp disabled
		/*if (null != object.getProperty("TaxationId")) {
			bean.setTaxationId(String.valueOf(object.getProperty("TaxationId")));
		}*/
		if (null != object.getProperty("TaxationName")) {
			bean.setTaxationName(String.valueOf(object
					.getProperty("TaxationName")));
		}
		if (null != object.getProperty("TimeLimitedMinutes")) {
			bean.setTimeLimitedMinutes(String.valueOf(object
					.getProperty("TimeLimitedMinutes")));
		}
		if (null != object.getProperty("TxChecked")) {
			bean.setTxChecked(String.valueOf(object.getProperty("TxChecked")));
		}
		if (null != object.getProperty("TxCode")) {
			bean.setTxCode(String.valueOf(object.getProperty("TxCode")));
		}
		if (null != object.getProperty("TxCompleted")) {
			bean.setTxCompleted(String.valueOf(object
					.getProperty("TxCompleted")));
		}
		if (null != object.getProperty("TxDate")
				&& !"0001-01-01T00:00:00".equals(object.getProperty("TxDate")
						.toString())) {
			bean.setTxDate(String.valueOf(object.getProperty("TxDate")));
		}
		if (null != object.getProperty("TxPaymentDtoList")) {
			bean.setTxPaymentDtoList(String.valueOf(object
					.getProperty("TxPaymentDtoList")));
		}
		if (null != object.getProperty("TxSalesDetailDtoList")) {
			bean.setTxSalesDetailDtoList(String.valueOf(object
					.getProperty("TxSalesDetailDtoList")));
		}
		if (null != object.getProperty("TxSalesHeaderId")) {
			bean.setTxSalesHeaderId(String.valueOf(object
					.getProperty("TxSalesHeaderId")));
		}
		if (null != object.getProperty("Voided")) {
			bean.setVoided(String.valueOf(object.getProperty("Voided")));
		}
		if (null != object.getProperty("WorkdayPeriodDetailId")) {
			bean.setWorkdayPeriodDetailId(String.valueOf(object
					.getProperty("WorkdayPeriodDetailId")));
		}
		if (null != object.getProperty("WorkdayPeriodName")) {
			bean.setWorkdayPeriodName(String.valueOf(object
					.getProperty("WorkdayPeriodName")));
		}
		return bean;
	}

}
