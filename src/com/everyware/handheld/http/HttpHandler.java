package com.everyware.handheld.http;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.text.TextUtils;

import com.everyware.handheld.R;
import com.everyware.handheld.utils.CommonUtils;
import com.everyware.handheld.utils.ConstantUtils;
import com.everyware.handheld.utils.ExtendedSoapSerializationEnvelope;
import com.everyware.handheld.utils.SoapUtils;

/**
 * 
 * @author ALEX
 *
 */
public class HttpHandler {

	private Context mContext;
	public static final String STATUS = "status";
	public static final String RESULT_SUCCESS = "success";
	public static final String RESULT_FAIL = "failure";
	public static final String RESULT = "result";

	public HttpHandler(Context context) {
		mContext = context;
	}

	public HashMap<String, Object> requestWebService(SoapObject mSoapObject,
			String mathodName) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			String mSoapAction = SoapUtils.SOAP_ACTION_HEAD + mathodName;
			String url = getHttpUrl();

			// 生成调用WebService方法的SOAP请求信息，并指定SOAP的版�?
			ExtendedSoapSerializationEnvelope envelope = new ExtendedSoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope.dotNet = true;
			envelope.implicitTypes = true;
			envelope.setAddAdornments(false);
			envelope.setOutputSoapObject(mSoapObject);

			// 创建HttpTransportSE对象
			final HttpTransportSE transport = new HttpTransportSE(url);
			transport.debug = true;

            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));

			transport.call(mSoapAction, envelope, headerPropertyArrayList);

			System.out.println("request---->" + transport.requestDump);
			System.out.println("response---->" + transport.responseDump);

			Object o = envelope.bodyIn;

			SoapObject object = (SoapObject) envelope.bodyIn;
			if (null != object) {
				result.put(STATUS, RESULT_SUCCESS);
				result.put(RESULT, object);
			} else {
				result.put(STATUS, RESULT_FAIL);
				result.put(RESULT,
						mContext.getString(R.string.remote_server_error));
			}

		} catch (SocketTimeoutException e) {
			result.put(STATUS, RESULT_FAIL);
			result.put(RESULT, mContext.getString(R.string.timout));
		} catch (Exception e) {
			result.put(STATUS, RESULT_FAIL);
			result.put(RESULT, mContext.getString(R.string.remote_server_error));
		}
		return result;
	}

	private String getHttpUrl() {
		String url = null;
		String ip = CommonUtils.getSharedPreferences(mContext,
                mContext.getString(R.string.ip));
		if (!TextUtils.isEmpty(ip)) {
			if (!ip.contains(ConstantUtils.HTTP_HEAD)) {
				url = ConstantUtils.HTTP_HEAD + ip;
			} else {
				url = ip;
			}
			if (!ip.contains(ConstantUtils.HTTP_PORT)) {
				url = url + ConstantUtils.HTTP_PORT;
			}
			url = url + ConstantUtils.WEB_SERVICE_NAME;
		}
		return url;
	}

}
