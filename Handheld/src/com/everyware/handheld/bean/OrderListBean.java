package com.everyware.handheld.bean;

import java.util.ArrayList;
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
    private boolean isReassignNeeded = false;

    @Override
    public OrderListBean clone(){
        OrderListBean newOrderListBean = new OrderListBean();

        newOrderListBean.isLight = this.isLight;
        newOrderListBean.number = this.number;
        newOrderListBean.dto = this.dto;
        newOrderListBean.isBg = this.isBg;

        if(null != this.modifier && this.modifier.size() > 0) {
            newOrderListBean.modifier = new ArrayList<OrderListBean>();
            for(int i = 0; i<this.modifier.size(); i++) {
                newOrderListBean.modifier.add(this.modifier.get(i).clone());
            }
        }

        if(null != this.followSet && this.followSet.size() > 0) {
            newOrderListBean.followSet = new ArrayList<OrderListBean>();
            for(int i = 0; i<this.followSet.size(); i++) {
                newOrderListBean.followSet.add(this.followSet.get(i).clone());
            }
        }

        newOrderListBean.isSubmitted = this.isSubmitted;
        newOrderListBean.isQiCai = this.isQiCai;
        newOrderListBean.isJiaoQi = this.isJiaoQi;
        // always set the select flag to false when clone
        newOrderListBean.isSelect = false;

        if(this.detailDto != null) {
            newOrderListBean.detailDto = this.detailDto.clone();
            // mark all the cloned item be newly added item by changing the txsalesdetailid = 0
            newOrderListBean.detailDto.setTxSalesDetailId("0");
        }

        if(null != this.modifierDto && this.modifierDto.size() > 0) {
            newOrderListBean.modifierDto = new ArrayList<OrderListBean>();
            for(int i = 0; i<this.modifierDto.size(); i++) {
                newOrderListBean.modifierDto.add(this.modifierDto.get(i).clone());
            }
        }

        if(null != this.followSetDto && this.followSetDto.size() > 0) {
            newOrderListBean.followSetDto = new ArrayList<OrderListBean>();
            for(int i = 0; i<this.followSetDto.size(); i++) {
                newOrderListBean.followSetDto.add(this.followSetDto.get(i).clone());
            }
        }

        newOrderListBean.isItemOnHold = this.isItemOnHold;
        newOrderListBean.qiCai = this.qiCai;
        newOrderListBean.jiaoQi = this.jiaoQi;
        newOrderListBean.isReassignNeeded = true;

        return newOrderListBean;
    }

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

    public boolean isReassignNeeded() {
        return isReassignNeeded;
    }

    public void setReassignNeeded(boolean isReassignNeeded) {
        this.isReassignNeeded = isReassignNeeded;
    }
}
