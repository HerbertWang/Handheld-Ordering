package com.everyware.handheld.http;

import java.util.HashMap;

/**
 * 
 * @author ALEX
 *
 */
public interface HandlerCallBack {
	void handleFinish(HashMap<String, Object> result);

	void handleFailure();
}
