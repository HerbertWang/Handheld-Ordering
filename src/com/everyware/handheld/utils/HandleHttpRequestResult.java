package com.everyware.handheld.utils;

import java.util.HashMap;

import org.ksoap2.serialization.SoapObject;

import android.content.Context;
import android.text.TextUtils;

import com.everyware.handheld.R;
import com.everyware.handheld.http.HttpHandler;

/**
 * 
 * @author ALEX
 *
 */
public class HandleHttpRequestResult {

	public static boolean isError(Context context, boolean isShowToast,
			String methodName, HashMap<String, Object> result) {
		if (null == result || result.isEmpty() || TextUtils.isEmpty(methodName)) {
			if (isShowToast) {
				CommonUtils.showToast(context,
						context.getString(R.string.remote_server_error));
			}
			return true;
		}
		if (TextUtils.isEmpty(String.valueOf(result.get(HttpHandler.STATUS)))) {
			if (isShowToast) {
				CommonUtils.showToast(context,
						context.getString(R.string.remote_server_error));
			}
			return true;
		}
		if (!HttpHandler.RESULT_SUCCESS.equals(String.valueOf(result
				.get(HttpHandler.STATUS)))) {
			if (isShowToast) {
				CommonUtils.showToast(context,
						String.valueOf(result.get(HttpHandler.RESULT)));
			}
			return true;
		}
		SoapObject object = (SoapObject) result.get(HttpHandler.RESULT);
		if (null == object) {
			if (isShowToast) {
				CommonUtils.showToast(context,
						context.getString(R.string.remote_server_error));
			}
			return true;
		}
		String property = String.format(SoapUtils.REQUEST_METHOD_RESULT,
				methodName);
		if (null == object.getProperty(property)) {
			if (isShowToast) {
				CommonUtils.showToast(context,
						context.getString(R.string.remote_server_error));
			}
			return true;
		}
		String resultState = String.valueOf(object.getProperty(property));
		if (TextUtils.isEmpty(resultState)) {
			if (isShowToast) {
				CommonUtils.showToast(context,
						context.getString(R.string.remote_server_error));
			}
			return true;
		}
		if (!ConstantUtils.IS_OK.equals(resultState)) {
			if (isShowToast) {
				CommonUtils.showToast(context,
						context.getString(R.string.remote_server_error));
			}
			return true;
		}
		return false;
	}

	public static SoapObject getResultSoapObject(HashMap<String, Object> result) {
		SoapObject object = (SoapObject) result.get(HttpHandler.RESULT);

        SoapObject mObject = (SoapObject) object
                .getProperty(SoapUtils.RESULT_LIST);

        return mObject;
	}

    public static SoapObject getResultItemMasterDtoSoapObject(HashMap<String, Object> result) {
        SoapObject object = (SoapObject) result.get(HttpHandler.RESULT);

        if(object.hasProperty(SoapUtils.ITEM_MASTER_DTO_LIST)) {
            SoapObject mObject = (SoapObject) object
                    .getProperty(SoapUtils.ITEM_MASTER_DTO_LIST);

            return mObject;
        } else {
            return  null;
        }
    }

}
