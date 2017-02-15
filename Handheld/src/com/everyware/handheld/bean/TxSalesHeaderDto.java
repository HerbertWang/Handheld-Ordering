package com.everyware.handheld.bean;


/**
 * 
 * @author ALEX
 * 
 */
public class TxSalesHeaderDto {
	private String AmountChange;
	private String AmountDiscount;
	private String AmountMinChargeOffset;
	private String AmountPaid;
	private String AmountRounding;
	private String AmountServiceCharge;
	private String AmountSubtotal;
	private String AmountTaxation;
	private String AmountTips;
	private String AmountTotal;
	private String CashDrawerCode;
	private String CashierDatetime;
	private String CashierPrinterName;
	// private String CashierPrinterName = ConstantUtils.mTableDto
	// .getCashierPrinterName();
	private String CashierUserId;
	private String CashierUserName;
	private String CheckinDatetime;
	// private String CheckinDatetime = CommonUtils.getCurrentDateTime();
	private String CheckinUserId;
	// private String CheckinUserId = ConstantUtils.userInfo.getUserId();
	private String CheckinUserName;
	// private String CheckinUserName = ConstantUtils.userInfo.getUserName();
	private String CheckoutDatetime;
	private String CheckoutUserId;
	private String CheckoutUserName;
	private String CompleteDatetime;
	private String CusCount = "0";
	private String CusName;
	private String DeliveryUserName;
	private String DisabledByUserId;
	private String DisabledByUserName;
	private String DisabledDateTime;
	private String DisabledReasonDesc;
	private String DisabledReasonId;
	private String DiscountByUserId;
	private String DiscountByUserName;
	private String DiscountId;
	private String DiscountName;
	private String Enabled = "true";//
	private String IsCompleted;
	private String IsCurrentTx = "true";//
	private String IsMemberTx;
	private String IsMinChargeOffsetWaived;
	private String IsMinChargeTx;
	private String IsTakeAway = "false";//
	private String IsTimeLimited;
	private String Line1;
	private String Line2;
	private String Line3;
	private String Line4;
	private String Line5;
	private String MemberDeductAmount;
	private String MemberTypeName;
	private String ModifiedDate;
	// private String ModifiedDate = CommonUtils.getCurrentDateTime();
	private String OclDeviceNum;
	private String OclNum;
	private String OclRemainValue;
    // 20150330 Michael - TODO should be null value if it has not been set
	//private String PaymentMethodId = "0";
	private String PhoneNum;
	private String PreviousTableCode;
	private String PreviousTableId;
	private String ReceiptNo;
	private String Remark1;
	private String Remark2;
	private String Remark3;
	private String Remark4;
	private String Remark5;
	private String SectionId;
	// private String SectionId = ConstantUtils.sectionId;
	private String SectionName;
	// private String SectionName = ConstantUtils.sectionName;
	private String ServiceChargeId;
	private String ServiceChargeName;
	private String ShopId;
	// private String ShopId = ConstantUtils.userInfo.getShopId();
	private String TableCode;
	// private String TableCode = ConstantUtils.mTableDto.getTableCode();
	private String TableId;
	// private String TableId = ConstantUtils.mTableDto.getTableId();
	private String TakeAwayRunningIndex = "0";
	private String TakeoutDatetime;

	// 20150330 Michael - TODO should be null value if it has not been set
    //private String TaxationId = "0";
	private String TaxationName;
	private String TimeLimitedMinutes = "0";
	private String TxChecked = "false";//
	private String TxCode = "0";//
	private String TxCompleted = "false";//
	private String TxDate;
	// private String TxDate = CommonUtils.getCurrentDateTime();
	private String TxPaymentDtoList;
	private String TxSalesDetailDtoList;
	private String TxSalesHeaderId = "0";//
	private String Voided = "false";//
	private String WorkdayPeriodDetailId;
	private String WorkdayPeriodName;

	public String getAmountChange() {
		return AmountChange;
	}

	public void setAmountChange(String amountChange) {
		AmountChange = amountChange;
	}

	public String getAmountDiscount() {
		return AmountDiscount;
	}

	public void setAmountDiscount(String amountDiscount) {
		AmountDiscount = amountDiscount;
	}

	public String getAmountMinChargeOffset() {
		return AmountMinChargeOffset;
	}

	public void setAmountMinChargeOffset(String amountMinChargeOffset) {
		AmountMinChargeOffset = amountMinChargeOffset;
	}

	public String getAmountPaid() {
		return AmountPaid;
	}

	public void setAmountPaid(String amountPaid) {
		AmountPaid = amountPaid;
	}

	public String getAmountRounding() {
		return AmountRounding;
	}

	public void setAmountRounding(String amountRounding) {
		AmountRounding = amountRounding;
	}

	public String getAmountServiceCharge() {
		return AmountServiceCharge;
	}

	public void setAmountServiceCharge(String amountServiceCharge) {
		AmountServiceCharge = amountServiceCharge;
	}

	public String getAmountSubtotal() {
		return AmountSubtotal;
	}

	public void setAmountSubtotal(String amountSubtotal) {
		AmountSubtotal = amountSubtotal;
	}

	public String getAmountTaxation() {
		return AmountTaxation;
	}

	public void setAmountTaxation(String amountTaxation) {
		AmountTaxation = amountTaxation;
	}

	public String getAmountTips() {
		return AmountTips;
	}

	public void setAmountTips(String amountTips) {
		AmountTips = amountTips;
	}

	public String getAmountTotal() {
		return AmountTotal;
	}

	public void setAmountTotal(String amountTotal) {
		AmountTotal = amountTotal;
	}

	public String getCashDrawerCode() {
		return CashDrawerCode;
	}

	public void setCashDrawerCode(String cashDrawerCode) {
		CashDrawerCode = cashDrawerCode;
	}

	public String getCashierDatetime() {
		return CashierDatetime;
	}

	public void setCashierDatetime(String cashierDatetime) {
		CashierDatetime = cashierDatetime;
	}

	public String getCashierPrinterName() {
		return CashierPrinterName;
	}

	public void setCashierPrinterName(String cashierPrinterName) {
		CashierPrinterName = cashierPrinterName;
	}

	public String getCashierUserId() {
		return CashierUserId;
	}

	public void setCashierUserId(String cashierUserId) {
		CashierUserId = cashierUserId;
	}

	public String getCashierUserName() {
		return CashierUserName;
	}

	public void setCashierUserName(String cashierUserName) {
		CashierUserName = cashierUserName;
	}

	public String getCheckinDatetime() {
		return CheckinDatetime;
	}

	public void setCheckinDatetime(String checkinDatetime) {
		CheckinDatetime = checkinDatetime;
	}

	public String getCheckinUserId() {
		return CheckinUserId;
	}

	public void setCheckinUserId(String checkinUserId) {
		CheckinUserId = checkinUserId;
	}

	public String getCheckinUserName() {
		return CheckinUserName;
	}

	public void setCheckinUserName(String checkinUserName) {
		CheckinUserName = checkinUserName;
	}

	public String getCheckoutDatetime() {
		return CheckoutDatetime;
	}

	public void setCheckoutDatetime(String checkoutDatetime) {
		CheckoutDatetime = checkoutDatetime;
	}

	public String getCheckoutUserId() {
		return CheckoutUserId;
	}

	public void setCheckoutUserId(String checkoutUserId) {
		CheckoutUserId = checkoutUserId;
	}

	public String getCheckoutUserName() {
		return CheckoutUserName;
	}

	public void setCheckoutUserName(String checkoutUserName) {
		CheckoutUserName = checkoutUserName;
	}

	public String getCompleteDatetime() {
		return CompleteDatetime;
	}

	public void setCompleteDatetime(String completeDatetime) {
		CompleteDatetime = completeDatetime;
	}

	public String getCusCount() {
		return CusCount;
	}

	public void setCusCount(String cusCount) {
		CusCount = cusCount;
	}

	public String getCusName() {
		return CusName;
	}

	public void setCusName(String cusName) {
		CusName = cusName;
	}

	public String getDeliveryUserName() {
		return DeliveryUserName;
	}

	public void setDeliveryUserName(String deliveryUserName) {
		DeliveryUserName = deliveryUserName;
	}

	public String getDisabledByUserId() {
		return DisabledByUserId;
	}

	public void setDisabledByUserId(String disabledByUserId) {
		DisabledByUserId = disabledByUserId;
	}

	public String getDisabledByUserName() {
		return DisabledByUserName;
	}

	public void setDisabledByUserName(String disabledByUserName) {
		DisabledByUserName = disabledByUserName;
	}

	public String getDisabledDateTime() {
		return DisabledDateTime;
	}

	public void setDisabledDateTime(String disabledDateTime) {
		DisabledDateTime = disabledDateTime;
	}

	public String getDisabledReasonDesc() {
		return DisabledReasonDesc;
	}

	public void setDisabledReasonDesc(String disabledReasonDesc) {
		DisabledReasonDesc = disabledReasonDesc;
	}

	public String getDisabledReasonId() {
		return DisabledReasonId;
	}

	public void setDisabledReasonId(String disabledReasonId) {
		DisabledReasonId = disabledReasonId;
	}

	public String getDiscountByUserId() {
		return DiscountByUserId;
	}

	public void setDiscountByUserId(String discountByUserId) {
		DiscountByUserId = discountByUserId;
	}

	public String getDiscountByUserName() {
		return DiscountByUserName;
	}

	public void setDiscountByUserName(String discountByUserName) {
		DiscountByUserName = discountByUserName;
	}

	public String getDiscountId() {
		return DiscountId;
	}

	public void setDiscountId(String discountId) {
		DiscountId = discountId;
	}

	public String getDiscountName() {
		return DiscountName;
	}

	public void setDiscountName(String discountName) {
		DiscountName = discountName;
	}

	public String getEnabled() {
		return Enabled;
	}

	public void setEnabled(String enabled) {
		Enabled = enabled;
	}

	public String getIsCompleted() {
		return IsCompleted;
	}

	public void setIsCompleted(String isCompleted) {
		IsCompleted = isCompleted;
	}

	public String getIsCurrentTx() {
		return IsCurrentTx;
	}

	public void setIsCurrentTx(String isCurrentTx) {
		IsCurrentTx = isCurrentTx;
	}

	public String getIsMemberTx() {
		return IsMemberTx;
	}

	public void setIsMemberTx(String isMemberTx) {
		IsMemberTx = isMemberTx;
	}

	public String getIsMinChargeOffsetWaived() {
		return IsMinChargeOffsetWaived;
	}

	public void setIsMinChargeOffsetWaived(String isMinChargeOffsetWaived) {
		IsMinChargeOffsetWaived = isMinChargeOffsetWaived;
	}

	public String getIsMinChargeTx() {
		return IsMinChargeTx;
	}

	public void setIsMinChargeTx(String isMinChargeTx) {
		IsMinChargeTx = isMinChargeTx;
	}

	public String getIsTakeAway() {
		return IsTakeAway;
	}

	public void setIsTakeAway(String isTakeAway) {
		IsTakeAway = isTakeAway;
	}

	public String getIsTimeLimited() {
		return IsTimeLimited;
	}

	public void setIsTimeLimited(String isTimeLimited) {
		IsTimeLimited = isTimeLimited;
	}

	public String getLine1() {
		return Line1;
	}

	public void setLine1(String line1) {
		Line1 = line1;
	}

	public String getLine2() {
		return Line2;
	}

	public void setLine2(String line2) {
		Line2 = line2;
	}

	public String getLine3() {
		return Line3;
	}

	public void setLine3(String line3) {
		Line3 = line3;
	}

	public String getLine4() {
		return Line4;
	}

	public void setLine4(String line4) {
		Line4 = line4;
	}

	public String getLine5() {
		return Line5;
	}

	public void setLine5(String line5) {
		Line5 = line5;
	}

	public String getMemberDeductAmount() {
		return MemberDeductAmount;
	}

	public void setMemberDeductAmount(String memberDeductAmount) {
		MemberDeductAmount = memberDeductAmount;
	}

	public String getMemberTypeName() {
		return MemberTypeName;
	}

	public void setMemberTypeName(String memberTypeName) {
		MemberTypeName = memberTypeName;
	}

	public String getModifiedDate() {
		return ModifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		ModifiedDate = modifiedDate;
	}

	public String getOclDeviceNum() {
		return OclDeviceNum;
	}

	public void setOclDeviceNum(String oclDeviceNum) {
		OclDeviceNum = oclDeviceNum;
	}

	public String getOclNum() {
		return OclNum;
	}

	public void setOclNum(String oclNum) {
		OclNum = oclNum;
	}

	public String getOclRemainValue() {
		return OclRemainValue;
	}

	public void setOclRemainValue(String oclRemainValue) {
		OclRemainValue = oclRemainValue;
	}

/*	public String getPaymentMethodId() {
		return PaymentMethodId;
	}

	public void setPaymentMethodId(String paymentMethodId) {
		PaymentMethodId = paymentMethodId;
	}*/

	public String getPhoneNum() {
		return PhoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		PhoneNum = phoneNum;
	}

	public String getPreviousTableCode() {
		return PreviousTableCode;
	}

	public void setPreviousTableCode(String previousTableCode) {
		PreviousTableCode = previousTableCode;
	}

	public String getPreviousTableId() {
		return PreviousTableId;
	}

	public void setPreviousTableId(String previousTableId) {
		PreviousTableId = previousTableId;
	}

	public String getReceiptNo() {
		return ReceiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		ReceiptNo = receiptNo;
	}

	public String getRemark1() {
		return Remark1;
	}

	public void setRemark1(String remark1) {
		Remark1 = remark1;
	}

	public String getRemark2() {
		return Remark2;
	}

	public void setRemark2(String remark2) {
		Remark2 = remark2;
	}

	public String getRemark3() {
		return Remark3;
	}

	public void setRemark3(String remark3) {
		Remark3 = remark3;
	}

	public String getRemark4() {
		return Remark4;
	}

	public void setRemark4(String remark4) {
		Remark4 = remark4;
	}

	public String getRemark5() {
		return Remark5;
	}

	public void setRemark5(String remark5) {
		Remark5 = remark5;
	}

	public String getSectionId() {
		return SectionId;
	}

	public void setSectionId(String sectionId) {
		SectionId = sectionId;
	}

	public String getSectionName() {
		return SectionName;
	}

	public void setSectionName(String sectionName) {
		SectionName = sectionName;
	}

	public String getServiceChargeId() {
		return ServiceChargeId;
	}

	public void setServiceChargeId(String serviceChargeId) {
		ServiceChargeId = serviceChargeId;
	}

	public String getServiceChargeName() {
		return ServiceChargeName;
	}

	public void setServiceChargeName(String serviceChargeName) {
		ServiceChargeName = serviceChargeName;
	}

	public String getShopId() {
		return ShopId;
	}

	public void setShopId(String shopId) {
		ShopId = shopId;
	}

	public String getTableCode() {
		return TableCode;
	}

	public void setTableCode(String tableCode) {
		TableCode = tableCode;
	}

	public String getTableId() {
		return TableId;
	}

	public void setTableId(String tableId) {
		TableId = tableId;
	}

	public String getTakeAwayRunningIndex() {
		return TakeAwayRunningIndex;
	}

	public void setTakeAwayRunningIndex(String takeAwayRunningIndex) {
		TakeAwayRunningIndex = takeAwayRunningIndex;
	}

	public String getTakeoutDatetime() {
		return TakeoutDatetime;
	}

	public void setTakeoutDatetime(String takeoutDatetime) {
		TakeoutDatetime = takeoutDatetime;
	}

/*	public String getTaxationId() {
		return TaxationId;
	}

	public void setTaxationId(String taxationId) {
		TaxationId = taxationId;
	}*/

	public String getTaxationName() {
		return TaxationName;
	}

	public void setTaxationName(String taxationName) {
		TaxationName = taxationName;
	}

	public String getTimeLimitedMinutes() {
		return TimeLimitedMinutes;
	}

	public void setTimeLimitedMinutes(String timeLimitedMinutes) {
		TimeLimitedMinutes = timeLimitedMinutes;
	}

	public String getTxChecked() {
		return TxChecked;
	}

	public void setTxChecked(String txChecked) {
		TxChecked = txChecked;
	}

	public String getTxCode() {
		return TxCode;
	}

	public void setTxCode(String txCode) {
		TxCode = txCode;
	}

	public String getTxCompleted() {
		return TxCompleted;
	}

	public void setTxCompleted(String txCompleted) {
		TxCompleted = txCompleted;
	}

	public String getTxDate() {
		return TxDate;
	}

	public void setTxDate(String txDate) {
		TxDate = txDate;
	}

	public String getTxPaymentDtoList() {
		return TxPaymentDtoList;
	}

	public void setTxPaymentDtoList(String txPaymentDtoList) {
		TxPaymentDtoList = txPaymentDtoList;
	}

	public String getTxSalesDetailDtoList() {
		return TxSalesDetailDtoList;
	}

	public void setTxSalesDetailDtoList(String txSalesDetailDtoList) {
		TxSalesDetailDtoList = txSalesDetailDtoList;
	}

	public String getTxSalesHeaderId() {
		return TxSalesHeaderId;
	}

	public void setTxSalesHeaderId(String txSalesHeaderId) {
		TxSalesHeaderId = txSalesHeaderId;
	}

	public String getVoided() {
		return Voided;
	}

	public void setVoided(String voided) {
		Voided = voided;
	}

	public String getWorkdayPeriodDetailId() {
		return WorkdayPeriodDetailId;
	}

	public void setWorkdayPeriodDetailId(String workdayPeriodDetailId) {
		WorkdayPeriodDetailId = workdayPeriodDetailId;
	}

	public String getWorkdayPeriodName() {
		return WorkdayPeriodName;
	}

	public void setWorkdayPeriodName(String workdayPeriodName) {
		WorkdayPeriodName = workdayPeriodName;
	}
}
