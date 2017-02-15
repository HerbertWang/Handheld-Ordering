package com.alex.food.bean;

public class TxPaymentDto {
	private String AccountId = "0";
	private String OclDeviceNum;
	private String OclNum;
	private String OclRemainValue;
	private String PaidAmount;
	private String PaymentMethodId = "0";
	private String PaymentMethodName;
	private String ShopId = "0";
	private String TotalAmount;
	private String TxPaymentId = "0";
	private String TxSalesHeaderId = "0";

	public String getAccountId() {
		return AccountId;
	}

	public void setAccountId(String accountId) {
		AccountId = accountId;
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

	public String getPaidAmount() {
		return PaidAmount;
	}

	public void setPaidAmount(String paidAmount) {
		PaidAmount = paidAmount;
	}

	public String getPaymentMethodId() {
		return PaymentMethodId;
	}

	public void setPaymentMethodId(String paymentMethodId) {
		PaymentMethodId = paymentMethodId;
	}

	public String getPaymentMethodName() {
		return PaymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		PaymentMethodName = paymentMethodName;
	}

	public String getShopId() {
		return ShopId;
	}

	public void setShopId(String shopId) {
		ShopId = shopId;
	}

	public String getTotalAmount() {
		return TotalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		TotalAmount = totalAmount;
	}

	public String getTxPaymentId() {
		return TxPaymentId;
	}

	public void setTxPaymentId(String txPaymentId) {
		TxPaymentId = txPaymentId;
	}

	public String getTxSalesHeaderId() {
		return TxSalesHeaderId;
	}

	public void setTxSalesHeaderId(String txSalesHeaderId) {
		TxSalesHeaderId = txSalesHeaderId;
	}
}
