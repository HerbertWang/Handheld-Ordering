package com.everyware.handheld.utils;

import com.everyware.handheld.bean.TableDto;

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
