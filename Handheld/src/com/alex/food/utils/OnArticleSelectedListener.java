package com.alex.food.utils;

import com.alex.food.bean.TableDto;

/**
 * 
 * @author ALEX
 *
 */
public interface OnArticleSelectedListener {

	public void onArticleSelectedListener(String accountId, String shopId, String tableCode,
			String tableStatus, String tableId, TableDto tableDto);

	public void onArticleClearListener(boolean isClear);

}
