package com.everyware.handheld.adapter;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.everyware.handheld.R;
import com.everyware.handheld.bean.ItemMasterDto;
import com.everyware.handheld.bean.OrderListBean;
import com.everyware.handheld.bean.TxSalesDetailDto;
import com.everyware.handheld.utils.HandlerOrderListCallBack;

/**
 * 
 * @author ALEX
 * 
 */
public class OrderListAdapter extends BaseAdapter {
	private Context mContext;
	private List<OrderListBean> list;
	private LayoutInflater inflater;
	public static final String NORMAL = "normal";
	public static final String MODIFIER = "modifier";
	public static final String FOLLOWSET = "followset";
	public static final String FOLLOWSET_MODIFIER = "followset_modifier";
	public static final int VOID = -1;
	private HandlerOrderListCallBack callBack;

	public OrderListAdapter(Context context, List<OrderListBean> list,
			HandlerOrderListCallBack callBack) {
		mContext = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		this.callBack = callBack;
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ItemMasterDto dto = null;
		TxSalesDetailDto detailDto = null;
		ViewHolder holder = null;
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.order_list_item, null);
			holder = new ViewHolder();
			holder.tvLevelOneStatus = (TextView) convertView
					.findViewById(R.id.tvLevelOneStatus);
			holder.tvNumber = (TextView) convertView
					.findViewById(R.id.tvNumber);
			holder.tvLevelOneName = (TextView) convertView
					.findViewById(R.id.tvLevelOneName);
			holder.tvLevelOneCount = (TextView) convertView
					.findViewById(R.id.tvLevelOneCount);
			holder.tvLevelOnePrice = (TextView) convertView
					.findViewById(R.id.tvLevelOnePrice);
			holder.linearLayoutLevelOne = (LinearLayout) convertView
					.findViewById(R.id.linearLayoutLevelOne);
			holder.linearLayoutLevelTwo = (LinearLayout) convertView
					.findViewById(R.id.linearLayoutLevelTwo);
			holder.linearLayoutLevelThree = (LinearLayout) convertView
					.findViewById(R.id.linearLayoutLevelThree);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvNumber.setText(String.valueOf(list.get(position).getNumber()));
		detailDto = list.get(position).getDetailDto();
		if (null != detailDto) {
			if (!TextUtils.isEmpty(detailDto.getItemName())) {
				holder.tvLevelOneName.setText(detailDto.getItemName());
			}
			if (!TextUtils.isEmpty(detailDto.getQty())) {
				holder.tvLevelOneCount.setText(detailDto.getQty());
			}
			if (!TextUtils.isEmpty(detailDto.getItemName())) {
				holder.tvLevelOnePrice.setText(detailDto.getAmount());
			}
            if(list.get(position).isSelect()) {
                holder.linearLayoutLevelOne.setBackgroundColor(mContext
                        .getResources().getColor(R.color.highlight_blue));
            }
			else if (list.get(position).isBg()) {
				holder.linearLayoutLevelOne.setBackgroundColor(mContext
						.getResources().getColor(R.color.light_yellow_1));
			}
            else if ("false".equals(list.get(position).getDetailDto().getEnabled())) {
                holder.linearLayoutLevelOne.setBackgroundColor(mContext
                        .getResources().getColor(R.color.bg_color_dark_gray));
            }
            else
            {
				holder.linearLayoutLevelOne
						.setBackgroundColor(Color.TRANSPARENT);
			}
			holder.tvLevelOneStatus.setVisibility(View.GONE);
			if ("true".equals(detailDto.getIsItemOnHold())) {
				if ("true".equals(detailDto.getIsItemFired())) {
//					if ("true".equals(detailDto.getIsLocalChangedItem())) {
//						holder.tvLevelOneStatus.setVisibility(View.VISIBLE);
//						holder.tvLevelOneStatus
//								.setBackgroundResource(R.drawable.icon_to_be_served);
//					} else {
//						holder.tvLevelOneStatus.setVisibility(View.VISIBLE);
//						holder.tvLevelOneStatus
//								.setBackgroundResource(R.drawable.icon_already_served);
//					}
                    holder.tvLevelOneStatus.setVisibility(View.VISIBLE);
                    holder.tvLevelOneStatus
                            .setBackgroundResource(R.drawable.icon_already_served);
				} else {
					holder.tvLevelOneStatus.setVisibility(View.VISIBLE);
					holder.tvLevelOneStatus
							.setBackgroundResource(R.drawable.icon_to_be_served);
				}
			} else {
				holder.tvLevelOneStatus.setVisibility(View.GONE);
				holder.tvLevelOneStatus.setBackgroundDrawable(null);
			}
			// if (!TextUtils.isEmpty(list.get(position).getQiCai())) {
			// holder.tvLevelOneStatus.setVisibility(View.VISIBLE);
			// holder.tvLevelOneStatus
			// .setBackgroundResource(R.drawable.icon_already_served);
			// } else {
			// if (!TextUtils.isEmpty(list.get(position).getJiaoQi())) {
			// holder.tvLevelOneStatus.setVisibility(View.VISIBLE);
			// holder.tvLevelOneStatus
			// .setBackgroundResource(R.drawable.icon_to_be_served);
			// } else {
			// holder.tvLevelOneStatus.setVisibility(View.GONE);
			// holder.tvLevelOneStatus.setBackgroundDrawable(null);
			// }
			// }

			detailDto = null;
			if (null != holder.linearLayoutLevelTwo
					&& null != list.get(position).getModifierDto()) {
				holder.linearLayoutLevelTwo.setVisibility(View.VISIBLE);
				addLevelTwoSubmitted(holder.linearLayoutLevelTwo, position,
						list.get(position).getModifierDto());
			} else {
				holder.linearLayoutLevelTwo.setVisibility(View.GONE);
			}
			if (null != holder.linearLayoutLevelThree
					&& null != list.get(position).getFollowSetDto()) {
				holder.linearLayoutLevelThree.setVisibility(View.VISIBLE);
				addLevelThreeSubmitted(holder.linearLayoutLevelThree, position,
						list.get(position).getFollowSetDto());
			} else {
				holder.linearLayoutLevelThree.setVisibility(View.GONE);
			}
			holder.linearLayoutLevelOne
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							callBack.handlerCallBack(position, VOID, VOID,
									NORMAL);
						}
					});
		} else {
			dto = list.get(position).getDto();
			if (null != dto) {
				if (!TextUtils.isEmpty(dto.getItemName())) {
					holder.tvLevelOneName.setText(dto.getItemName());
				}
				if (!TextUtils.isEmpty(dto.getItemQty())) {
					holder.tvLevelOneCount.setText(dto.getItemQty());
				}
				if (!TextUtils.isEmpty(dto.getItemPrice())) {
					holder.tvLevelOnePrice.setText(getTotalPrice(
							dto.getItemPrice(), dto.getItemQty()));
				}
				dto = null;
			}
			if (!list.get(position).isItemOnHold()) {
				holder.tvLevelOneStatus.setVisibility(View.GONE);
				holder.tvLevelOneStatus.setBackgroundDrawable(null);
			} else {
				holder.tvLevelOneStatus.setVisibility(View.VISIBLE);
				holder.tvLevelOneStatus
						.setBackgroundResource(R.drawable.icon_to_be_served);
			}
			// if (!TextUtils.isEmpty(list.get(position).getJiaoQi())) {
			// holder.tvLevelOneStatus.setVisibility(View.GONE);
			// holder.tvLevelOneStatus.setBackgroundDrawable(null);
			// } else {
			// holder.tvLevelOneStatus.setVisibility(View.VISIBLE);
			// holder.tvLevelOneStatus
			// .setBackgroundResource(R.drawable.icon_to_be_served);
			// }
            if (list.get(position).isSelect())
            {
                holder.linearLayoutLevelOne.setBackgroundColor(mContext
                        .getResources().getColor(R.color.highlight_blue));
            }
			else if (list.get(position).isBg()) {
				holder.linearLayoutLevelOne.setBackgroundColor(mContext
						.getResources().getColor(R.color.light_yellow_1));
			}
            else if ("false".equals(list.get(position).getDetailDto().getEnabled())) {
                holder.linearLayoutLevelOne.setBackgroundColor(mContext
                        .getResources().getColor(R.color.bg_color_dark_gray));
            }else {
				holder.linearLayoutLevelOne
						.setBackgroundColor(Color.TRANSPARENT);
			}
			if (null != holder.linearLayoutLevelTwo
					&& null != list.get(position).getModifier()) {
				holder.linearLayoutLevelTwo.setVisibility(View.VISIBLE);
				addLevelTwo(holder.linearLayoutLevelTwo, position,
						list.get(position).getModifier());
			} else {
				holder.linearLayoutLevelTwo.setVisibility(View.GONE);
			}
			if (null != holder.linearLayoutLevelThree
					&& null != list.get(position).getFollowSet()) {
				holder.linearLayoutLevelThree.setVisibility(View.VISIBLE);
				addLevelThree(holder.linearLayoutLevelThree, position, list
						.get(position).getFollowSet());
			} else {
				holder.linearLayoutLevelThree.setVisibility(View.GONE);
			}

			holder.linearLayoutLevelOne
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							callBack.handlerCallBack(position, VOID, VOID,
									NORMAL);
						}
					});
		}

		return convertView;
	}

	private class ViewHolder {
		TextView tvNumber;
		TextView tvLevelOneName;
		TextView tvLevelOneCount;
		TextView tvLevelOnePrice;
		TextView tvLevelOneStatus;
		LinearLayout linearLayoutLevelOne;
		LinearLayout linearLayoutLevelTwo;
		LinearLayout linearLayoutLevelThree;
	}

	private void addLevelTwo(LinearLayout layout, final int position,
			List<OrderListBean> beanList) {
		if (null != layout) {
			layout.removeAllViews();
		}
		if (null == beanList || beanList.size() <= 0) {
			return;
		}
		View view = null;
		RelativeLayout relativeLayoutLevelTwo = null;
		TextView tvLevelTwoName = null;
		TextView tvLevelTwoPrice = null;
		ItemMasterDto dto = null;
		for (int i = 0; i < beanList.size(); i++) {
			view = inflater.inflate(R.layout.order_list_item_1, null);
			relativeLayoutLevelTwo = (RelativeLayout) view
					.findViewById(R.id.relativeLayoutLevelTwo);
			tvLevelTwoName = (TextView) view.findViewById(R.id.tvLevelTwoName);
			tvLevelTwoPrice = (TextView) view
					.findViewById(R.id.tvLevelTwoPrice);
            if(beanList.get(i).isSelect()) {
                relativeLayoutLevelTwo.setBackgroundColor(mContext
                        .getResources().getColor(R.color.highlight_blue));
            }
			else if (beanList.get(i).isBg()) {
				relativeLayoutLevelTwo.setBackgroundColor(mContext
						.getResources().getColor(R.color.light_yellow_1));
			}
            else if ("false".equals(list.get(position).getDetailDto().getEnabled())) {
                relativeLayoutLevelTwo.setBackgroundColor(mContext
                        .getResources().getColor(R.color.bg_color_dark_gray));
            }
            else {
				relativeLayoutLevelTwo.setBackgroundColor(Color.TRANSPARENT);
			}
			dto = beanList.get(i).getDto();
			if (null != dto) {
				if (!TextUtils.isEmpty(dto.getItemName())) {
					tvLevelTwoName.setText(dto.getItemName());
				}
				if (!TextUtils.isEmpty(dto.getItemPrice())) {
					tvLevelTwoPrice.setText(getTotalPrice(dto.getItemPrice(),
							dto.getItemQty()));
				}
				dto = null;
			}
			relativeLayoutLevelTwo.setTag(i);
			relativeLayoutLevelTwo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int index = (Integer) v.getTag();
					callBack.handlerCallBack(position, index, VOID, MODIFIER);
				}
			});
			layout.addView(view);
		}
	}

	private void addLevelThree(LinearLayout layout, final int position,
			List<OrderListBean> beanList) {
		if (null != layout) {
			layout.removeAllViews();
		}
		if (null == beanList || beanList.size() <= 0) {
			return;
		}
		View view = null;
		RelativeLayout relativeLayoutLevelThree = null;
		TextView tvLevelThreeStatus = null;
		TextView tvLevelThreeName = null;
		TextView tvLevelThreePrice = null;
		LinearLayout linearLayoutLevelFour = null;
		ItemMasterDto dto = null;
		for (int i = 0; i < beanList.size(); i++) {
			view = inflater.inflate(R.layout.order_list_item_2, null);
			relativeLayoutLevelThree = (RelativeLayout) view
					.findViewById(R.id.relativeLayoutLevelThree);
			tvLevelThreeStatus = (TextView) view
					.findViewById(R.id.tvLevelThreeStatus);
			tvLevelThreeName = (TextView) view
					.findViewById(R.id.tvLevelThreeName);
			tvLevelThreePrice = (TextView) view
					.findViewById(R.id.tvLevelThreePrice);
			linearLayoutLevelFour = (LinearLayout) view
					.findViewById(R.id.linearLayoutLevelFour);
			if (null != linearLayoutLevelFour
					&& null != beanList.get(i).getModifier()) {
				linearLayoutLevelFour.setVisibility(View.VISIBLE);
				addLevelFour(linearLayoutLevelFour, position, i, beanList
						.get(i).getModifier());
			} else {
				linearLayoutLevelFour.setVisibility(View.GONE);
			}
/*			if (beanList.get(i).isItemOnHold()) {
				tvLevelThreeStatus.setVisibility(View.GONE);
				tvLevelThreeStatus.setBackgroundDrawable(null);
			} else {
				tvLevelThreeStatus.setVisibility(View.VISIBLE);
				tvLevelThreeStatus
						.setBackgroundResource(R.drawable.icon_already_served);
			}*/
/*
            if ("true".equals(beanList.get(i).getDetailDto().getIsItemOnHold())) {
                if ("true".equals(beanList.get(i).getDetailDto().getIsItemFired())) {
                    if ("true".equals(beanList.get(i).getDetailDto().getIsLocalChangedItem())) {
                        tvLevelThreeStatus.setVisibility(View.VISIBLE);
                        tvLevelThreeStatus
                                .setBackgroundResource(R.drawable.icon_to_be_served);
                    } else {
                        tvLevelThreeStatus.setVisibility(View.VISIBLE);
                        tvLevelThreeStatus
                                .setBackgroundResource(R.drawable.icon_already_served);
                    }
                } else {
                    tvLevelThreeStatus.setVisibility(View.VISIBLE);
                    tvLevelThreeStatus
                            .setBackgroundResource(R.drawable.icon_already_served);
                }
            } else {
                tvLevelThreeStatus.setVisibility(View.GONE);
                tvLevelThreeStatus.setBackgroundDrawable(null);
            }*/
			// if (!TextUtils.isEmpty(beanList.get(position).getJiaoQi())) {
			// tvLevelThreeStatus.setVisibility(View.GONE);
			// tvLevelThreeStatus.setBackgroundDrawable(null);
			// } else {
			// tvLevelThreeStatus.setVisibility(View.VISIBLE);
			// tvLevelThreeStatus
			// .setBackgroundResource(R.drawable.icon_to_be_served);
			// }

            if(beanList.get(i).isSelect()) {
                relativeLayoutLevelThree.setBackgroundColor(mContext
                        .getResources().getColor(R.color.highlight_blue));
            }
			else if (beanList.get(i).isBg()) {
				relativeLayoutLevelThree.setBackgroundColor(mContext
						.getResources().getColor(R.color.light_yellow_1));
			}
            else if ("false".equals(list.get(position).getDetailDto().getEnabled())) {
                relativeLayoutLevelThree.setBackgroundColor(mContext
                        .getResources().getColor(R.color.bg_color_dark_gray));
            }
            else {
				relativeLayoutLevelThree.setBackgroundColor(Color.TRANSPARENT);
			}
			dto = beanList.get(i).getDto();
			if (null != dto) {
				if (!TextUtils.isEmpty(dto.getItemName())) {
					tvLevelThreeName.setText(dto.getItemName());
				}
				if (!TextUtils.isEmpty(dto.getItemPrice())) {
					tvLevelThreePrice.setText("+"
							+ getTotalPrice(dto.getItemPrice(),
									dto.getItemQty()));
				}
				dto = null;
			}
			relativeLayoutLevelThree.setTag(i);
			relativeLayoutLevelThree.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int index = (Integer) v.getTag();
					callBack.handlerCallBack(position, index, VOID, FOLLOWSET);
				}
			});
			layout.addView(view);
		}

	}

	private void addLevelFour(LinearLayout layout, final int position,
			int subPosition, List<OrderListBean> beanList) {
		if (null != layout) {
			layout.removeAllViews();
		}
		if (null == beanList || beanList.size() <= 0) {
			return;
		}
		View view = null;
		TextView tvLevelFourName = null;
		RelativeLayout relativeLayoutLevelFour = null;
		ItemMasterDto dto = null;
		for (int i = 0; i < beanList.size(); i++) {
			view = inflater.inflate(R.layout.order_list_item_3, null);
			tvLevelFourName = (TextView) view
					.findViewById(R.id.tvLevelFourName);
			relativeLayoutLevelFour = (RelativeLayout) view
					.findViewById(R.id.relativeLayoutLevelFour);
            if( beanList.get(i).isSelect()){
                relativeLayoutLevelFour.setBackgroundColor(mContext
                        .getResources().getColor(R.color.highlight_blue));
            }
			else if (beanList.get(i).isBg() ) {
				relativeLayoutLevelFour.setBackgroundColor(mContext
						.getResources().getColor(R.color.light_yellow_1));
			}
            else if ("false".equals(list.get(position).getDetailDto().getEnabled())) {
                relativeLayoutLevelFour.setBackgroundColor(mContext
                        .getResources().getColor(R.color.bg_color_dark_gray));
            }else {
				relativeLayoutLevelFour.setBackgroundColor(Color.TRANSPARENT);
			}
			dto = beanList.get(i).getDto();
			if (null != dto) {
				if (!TextUtils.isEmpty(dto.getItemName())) {
					tvLevelFourName.setText(dto.getItemName());
				}
				dto = null;
			}
			relativeLayoutLevelFour.setTag(i + "#" + subPosition);
			relativeLayoutLevelFour.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String tag = (String) v.getTag();
					String[] array = tag.split("#");
					callBack.handlerCallBack(position,
							Integer.valueOf(array[1]),
							Integer.valueOf(array[0]), FOLLOWSET_MODIFIER);
				}
			});
			layout.addView(view);
		}
	}

	private void addLevelTwoSubmitted(LinearLayout layout, final int position,
			List<OrderListBean> beanList) {
		if (null != layout) {
			layout.removeAllViews();
		}
		if (null == beanList || beanList.size() <= 0) {
			return;
		}
		View view = null;
		RelativeLayout relativeLayoutLevelTwo = null;
		TextView tvLevelTwoName = null;
		TextView tvLevelTwoPrice = null;
		TextView tvLevelTwoStatus = null;
		TxSalesDetailDto dto = null;
		for (int i = 0; i < beanList.size(); i++) {
			view = inflater.inflate(R.layout.order_list_item_1, null);
			relativeLayoutLevelTwo = (RelativeLayout) view
					.findViewById(R.id.relativeLayoutLevelTwo);
			tvLevelTwoName = (TextView) view.findViewById(R.id.tvLevelTwoName);
			tvLevelTwoPrice = (TextView) view
					.findViewById(R.id.tvLevelTwoPrice);
			tvLevelTwoStatus = (TextView) view
					.findViewById(R.id.tvLevelTwoStatus);
			if(beanList.get(i).isSelect()){
                relativeLayoutLevelTwo.setBackgroundColor(mContext
                        .getResources().getColor(R.color.highlight_blue));
            }
            else if (beanList.get(i).isBg()) {
				relativeLayoutLevelTwo.setBackgroundColor(mContext
						.getResources().getColor(R.color.light_yellow_1));
			} else if ("false".equals(list.get(position).getDetailDto().getEnabled())) {
                relativeLayoutLevelTwo.setBackgroundColor(mContext
                        .getResources().getColor(R.color.bg_color_dark_gray));
            }else {
				relativeLayoutLevelTwo.setBackgroundColor(Color.TRANSPARENT);
			}
			dto = beanList.get(i).getDetailDto();
			if (null != dto) {
				if (!TextUtils.isEmpty(dto.getItemName())) {
					tvLevelTwoName.setText(dto.getItemName());
				}
				if (!TextUtils.isEmpty(dto.getAmount())) {
					tvLevelTwoPrice.setText(dto.getAmount());
				}
				tvLevelTwoStatus.setVisibility(View.GONE);
				if ("true".equals(dto.getIsItemOnHold())) {
					if ("true".equals(dto.getIsItemFired())) {
//						if ("true".equals(dto.getIsLocalChangedItem())) {
//							tvLevelTwoStatus.setVisibility(View.VISIBLE);
//							tvLevelTwoStatus
//									.setBackgroundResource(R.drawable.icon_to_be_served);
//						} else {
//							tvLevelTwoStatus.setVisibility(View.VISIBLE);
//							tvLevelTwoStatus
//									.setBackgroundResource(R.drawable.icon_already_served);
//						}
                        tvLevelTwoStatus.setVisibility(View.VISIBLE);
                        tvLevelTwoStatus
                                .setBackgroundResource(R.drawable.icon_already_served);
					} else {
						tvLevelTwoStatus.setVisibility(View.VISIBLE);
						tvLevelTwoStatus
								.setBackgroundResource(R.drawable.icon_to_be_served);
					}
				} else {
					tvLevelTwoStatus.setVisibility(View.GONE);
					tvLevelTwoStatus.setBackgroundDrawable(null);
				}
				dto = null;
			}
			relativeLayoutLevelTwo.setTag(i);
			relativeLayoutLevelTwo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int index = (Integer) v.getTag();
					callBack.handlerCallBack(position, index, VOID, MODIFIER);
				}
			});
			layout.addView(view);
		}
	}

	private void addLevelThreeSubmitted(LinearLayout layout,
			final int position, List<OrderListBean> beanList) {
		if (null != layout) {
			layout.removeAllViews();
		}
		if (null == beanList || beanList.size() <= 0) {
			return;
		}
		View view = null;
		RelativeLayout relativeLayoutLevelThree = null;
		TextView tvLevelThreeStatus = null;
		TextView tvLevelThreeName = null;
		TextView tvLevelThreePrice = null;
		LinearLayout linearLayoutLevelFour = null;
		TxSalesDetailDto dto = null;
		for (int i = 0; i < beanList.size(); i++) {
			view = inflater.inflate(R.layout.order_list_item_2, null);
			relativeLayoutLevelThree = (RelativeLayout) view
					.findViewById(R.id.relativeLayoutLevelThree);
			tvLevelThreeStatus = (TextView) view
					.findViewById(R.id.tvLevelThreeStatus);
			tvLevelThreeName = (TextView) view
					.findViewById(R.id.tvLevelThreeName);
			tvLevelThreePrice = (TextView) view
					.findViewById(R.id.tvLevelThreePrice);
			linearLayoutLevelFour = (LinearLayout) view
					.findViewById(R.id.linearLayoutLevelFour);
			if (null != linearLayoutLevelFour
					&& null != beanList.get(i).getModifier()) {
				linearLayoutLevelFour.setVisibility(View.VISIBLE);
				addLevelFourSubmitted(linearLayoutLevelFour, position, i,
						beanList.get(i).getModifierDto());
			} else {
				linearLayoutLevelFour.setVisibility(View.GONE);
			}
            if(beanList.get(i).isSelect()){
                relativeLayoutLevelThree.setBackgroundColor(mContext
                        .getResources().getColor(R.color.highlight_blue));
            }
			else if (beanList.get(i).isBg()) {
				relativeLayoutLevelThree.setBackgroundColor(mContext
						.getResources().getColor(R.color.light_yellow_1));
			} else if ("false".equals(list.get(position).getDetailDto().getEnabled())) {
                relativeLayoutLevelThree.setBackgroundColor(mContext
                        .getResources().getColor(R.color.bg_color_dark_gray));
            }else {
				relativeLayoutLevelThree.setBackgroundColor(Color.TRANSPARENT);
			}
			dto = beanList.get(i).getDetailDto();
			if (null != dto) {
				if (!TextUtils.isEmpty(dto.getItemName())) {
					tvLevelThreeName.setText(dto.getItemName());
				}
				if (!TextUtils.isEmpty(dto.getAmount())) {
					tvLevelThreePrice.setText("+" + dto.getAmount());
				}
				if ("true".equals(dto.getIsItemOnHold())) {
					if ("true".equals(dto.getIsItemFired())) {
//						if ("true".equals(dto.getIsLocalChangedItem())) {
//							tvLevelThreeStatus
//									.setBackgroundResource(R.drawable.icon_to_be_served);
//						} else {
//							tvLevelThreeStatus
//									.setBackgroundResource(R.drawable.icon_already_served);
//						}
                        tvLevelThreeStatus
                                .setBackgroundResource(R.drawable.icon_already_served);
					} else {
						tvLevelThreeStatus
								.setBackgroundResource(R.drawable.icon_to_be_served);
					}
				} else {
					tvLevelThreeStatus.setBackgroundDrawable(null);
				}
				// if (!TextUtils.isEmpty(beanList.get(position).getQiCai())) {
				// tvLevelThreeStatus
				// .setBackgroundResource(R.drawable.icon_already_served);
				// } else {
				// if (!TextUtils.isEmpty(beanList.get(position).getJiaoQi())) {
				// tvLevelThreeStatus
				// .setBackgroundResource(R.drawable.icon_to_be_served);
				// } else {
				// tvLevelThreeStatus.setBackgroundDrawable(null);
				// }
				// }
				dto = null;
			}
			relativeLayoutLevelThree.setTag(i);
			relativeLayoutLevelThree.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int index = (Integer) v.getTag();
					callBack.handlerCallBack(position, index, VOID, FOLLOWSET);
				}
			});
			layout.addView(view);
		}

	}

	private void addLevelFourSubmitted(LinearLayout layout, final int position,
			int subPosition, List<OrderListBean> beanList) {
		if (null != layout) {
			layout.removeAllViews();
		}
		if (null == beanList || beanList.size() <= 0) {
			return;
		}
		View view = null;
		TextView tvLevelFourName = null;
		RelativeLayout relativeLayoutLevelFour = null;
		TextView tvLevelFourStatus = null;
		TxSalesDetailDto dto = null;
		for (int i = 0; i < beanList.size(); i++) {
			view = inflater.inflate(R.layout.order_list_item_3, null);
			tvLevelFourName = (TextView) view
					.findViewById(R.id.tvLevelFourName);
			relativeLayoutLevelFour = (RelativeLayout) view
					.findViewById(R.id.relativeLayoutLevelFour);
			tvLevelFourStatus = (TextView) view
					.findViewById(R.id.tvLevelFourStatus);
			if(beanList.get(i).isSelect()) {
                relativeLayoutLevelFour.setBackgroundColor(mContext
                        .getResources().getColor(R.color.highlight_blue));
            }
            else if (beanList.get(i).isBg()){
				relativeLayoutLevelFour.setBackgroundColor(mContext
						.getResources().getColor(R.color.light_yellow_1));
			} else if ("false".equals(list.get(position).getDetailDto().getEnabled())) {
                relativeLayoutLevelFour.setBackgroundColor(mContext
                        .getResources().getColor(R.color.bg_color_dark_gray));
            }else {
				relativeLayoutLevelFour.setBackgroundColor(Color.TRANSPARENT);
			}
			dto = beanList.get(i).getDetailDto();
			if (null != dto) {
				if (!TextUtils.isEmpty(dto.getItemName())) {
					tvLevelFourName.setText(dto.getItemName());
				}
				tvLevelFourStatus.setVisibility(View.GONE);
				if ("true".equals(dto.getIsItemOnHold())) {
					if ("true".equals(dto.getIsItemFired())) {
//						if ("true".equals(dto.getIsLocalChangedItem())) {
//							tvLevelFourStatus.setVisibility(View.VISIBLE);
//							tvLevelFourStatus
//									.setBackgroundResource(R.drawable.icon_to_be_served);
//						} else {
//							tvLevelFourStatus.setVisibility(View.VISIBLE);
//							tvLevelFourStatus
//									.setBackgroundResource(R.drawable.icon_already_served);
//						}
                        tvLevelFourStatus.setVisibility(View.VISIBLE);
                        tvLevelFourStatus
                                .setBackgroundResource(R.drawable.icon_already_served);
					} else {
						tvLevelFourStatus.setVisibility(View.VISIBLE);
						tvLevelFourStatus
								.setBackgroundResource(R.drawable.icon_to_be_served);
					}
				} else {
					tvLevelFourStatus.setVisibility(View.GONE);
					tvLevelFourStatus.setBackgroundDrawable(null);
				}
				dto = null;
			}
			relativeLayoutLevelFour.setTag(i + "#" + subPosition);
			relativeLayoutLevelFour.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String tag = (String) v.getTag();
					String[] array = tag.split("#");
					callBack.handlerCallBack(position,
							Integer.valueOf(array[1]),
							Integer.valueOf(array[0]), FOLLOWSET_MODIFIER);
				}
			});
			layout.addView(view);
		}
	}

	private String getTotalPrice(String strPrice, String strCount) {
		BigDecimal price = new BigDecimal(strPrice);
		BigDecimal count = new BigDecimal(strCount);
		BigDecimal total = price.multiply(count);
		return total.toString();
	}
}
