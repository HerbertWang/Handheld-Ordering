package com.alex.food.bean;

import java.util.List;

/**
 * 
 * @author ALEX
 * 
 */
public class OrderListBean {
	private boolean isLight = false;
	private int number;
	private ItemMasterDto dto;
	private boolean isBg = false;
	private List<OrderListBean> modifier;
	private List<OrderListBean> followSet;
	private boolean isSubmitted = false;
	private boolean isQiCai = false;
	private boolean isJiaoQi = false;
	private boolean isSelect = false;
	private TxSalesDetailDto detailDto;
	private List<OrderListBean> modifierDto;
	private List<OrderListBean> followSetDto;
	private boolean isItemOnHold = false;
	private String qiCai;
	private String jiaoQi;

	public boolean isLight() {
		return isLight;
	}

	public void setLight(boolean isLight) {
		this.isLight = isLight;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public ItemMasterDto getDto() {
		return dto;
	}

	public void setDto(ItemMasterDto dto) {
		this.dto = dto;
	}

	public boolean isBg() {
		return isBg;
	}

	public void setBg(boolean isBg) {
		this.isBg = isBg;
	}

	public List<OrderListBean> getModifier() {
		return modifier;
	}

	public void setModifier(List<OrderListBean> modifier) {
		this.modifier = modifier;
	}

	public List<OrderListBean> getFollowSet() {
		return followSet;
	}

	public void setFollowSet(List<OrderListBean> followSet) {
		this.followSet = followSet;
	}

	public boolean isSubmitted() {
		return isSubmitted;
	}

	public void setSubmitted(boolean isSubmitted) {
		this.isSubmitted = isSubmitted;
	}

	public boolean isQiCai() {
		return isQiCai;
	}

	public void setQiCai(boolean isQiCai) {
		this.isQiCai = isQiCai;
	}

	public boolean isJiaoQi() {
		return isJiaoQi;
	}

	public void setJiaoQi(boolean isJiaoQi) {
		this.isJiaoQi = isJiaoQi;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public TxSalesDetailDto getDetailDto() {
		return detailDto;
	}

	public void setDetailDto(TxSalesDetailDto detailDto) {
		this.detailDto = detailDto;
	}

	public List<OrderListBean> getModifierDto() {
		return modifierDto;
	}

	public void setModifierDto(List<OrderListBean> modifierDto) {
		this.modifierDto = modifierDto;
	}

	public List<OrderListBean> getFollowSetDto() {
		return followSetDto;
	}

	public void setFollowSetDto(List<OrderListBean> followSetDto) {
		this.followSetDto = followSetDto;
	}

	public boolean isItemOnHold() {
		return isItemOnHold;
	}

	public void setItemOnHold(boolean isItemOnHold) {
		this.isItemOnHold = isItemOnHold;
	}

	public String getQiCai() {
		return qiCai;
	}

	public void setQiCai(String qiCai) {
		this.qiCai = qiCai;
	}

	public String getJiaoQi() {
		return jiaoQi;
	}

	public void setJiaoQi(String jiaoQi) {
		this.jiaoQi = jiaoQi;
	}
}
