package com.everyware.handheld.utils;

import java.util.List;

import com.everyware.handheld.bean.OrderListBean;

public class OrderListUtils {

	public static void setOrderListItemIsSelectFalse() {
		if (null == ConstantUtils.orderList
				|| ConstantUtils.orderList.size() <= 0) {
			return;
		}
		OrderListBean bean = null;
		List<OrderListBean> modifierList = null;
		OrderListBean modifierListBean = null;
		List<OrderListBean> followSetList = null;
		OrderListBean followSetListBean = null;
		List<OrderListBean> followSetModifierList = null;
		OrderListBean followSetModifierListBean = null;
		List<OrderListBean> modifier = null;
		OrderListBean modifierBean = null;
		List<OrderListBean> followSet = null;
		OrderListBean followSetBean = null;
		List<OrderListBean> followSetModifier = null;
		OrderListBean followSetModifierBean = null;
		for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
			bean = ConstantUtils.orderList.get(i);
			if (null != bean) {
				bean.setSelect(false);
				if (null != bean.getDetailDto()) {
					modifierList = bean.getModifierDto();
					if (null != modifierList && modifierList.size() > 0) {
						for (int j = 0; j < modifierList.size(); j++) {
							modifierListBean = modifierList.get(j);
							if (null != modifierListBean) {
								modifierListBean.setSelect(false);
								modifierList.set(j, modifierListBean);
								modifierListBean = null;
							}
						}
						bean.setModifierDto(modifierList);
						ConstantUtils.orderList.set(i, bean);
						modifierList = null;
					}
					followSetList = bean.getFollowSetDto();
					if (null != followSetList) {
						for (int j = 0; j < followSetList.size(); j++) {
							followSetListBean = followSetList.get(j);
							if (null != followSetListBean) {
								followSetListBean.setSelect(false);
								followSetModifierList = followSetListBean
										.getModifierDto();
								if (null != followSetModifierList
										&& followSetModifierList.size() > 0) {
									for (int k = 0; k < followSetModifierList
											.size(); k++) {
										followSetModifierListBean = followSetModifierList
												.get(k);
										if (null != followSetModifierListBean) {
											followSetModifierListBean
													.setSelect(false);
											followSetModifierList.set(k,
													followSetModifierListBean);
											followSetModifierListBean = null;
										}
									}
									followSetListBean
											.setModifierDto(followSetModifierList);
									followSetModifierList = null;
								}
								followSetList.set(j, followSetListBean);
								followSetListBean = null;
							}
						}
						bean.setFollowSetDto(followSetList);
						ConstantUtils.orderList.set(i, bean);
						followSetList = null;
					}
				} else {
					modifier = bean.getModifier();
					if (null != modifier && modifier.size() > 0) {
						for (int j = 0; j < modifier.size(); j++) {
							modifierBean = modifier.get(j);
							if (null != modifierBean) {
								modifierBean.setSelect(false);
								modifier.set(j, modifierBean);
								modifierBean = null;
							}
						}
						bean.setModifier(modifier);
						ConstantUtils.orderList.set(i, bean);
						modifier = null;
					}
					followSet = bean.getFollowSet();
					if (null != followSet) {
						for (int j = 0; j < followSet.size(); j++) {
							followSetBean = followSet.get(j);
							if (null != followSetBean) {
								followSetBean.setSelect(false);
								followSetModifier = followSetBean.getModifier();
								if (null != followSetModifier
										&& followSetModifier.size() > 0) {
									for (int k = 0; k < followSetModifier
											.size(); k++) {
										followSetModifierBean = followSetModifier
												.get(k);
										if (null != followSetModifierBean) {
											followSetModifierBean
													.setSelect(false);
											followSetModifier.set(k,
													followSetModifierBean);
											followSetModifierBean = null;
										}
									}
									followSetBean
											.setModifier(followSetModifier);
									followSetModifier = null;
								}
								followSet.set(j, followSetBean);
								followSetBean = null;
							}
						}
						bean.setFollowSet(followSet);
						ConstantUtils.orderList.set(i, bean);
						followSet = null;
					}
				}
				ConstantUtils.orderList.set(i, bean);
				bean = null;
			}
		}
	}

}
