package com.alex.food.http;

import java.util.HashMap;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.alex.food.R;
import android.widget.Toast;

/**
 * 
 * @author ALEX
 *
 */
public class AsyncHttpRequest extends
		AsyncTask<Void, Integer, HashMap<String, Object>> {
	private Context mContext;
	private boolean isShowProgressDialog;
	private ProgressDialog dialog;
	private HandlerCallBack mCallBack;
	private SoapObject mSoapObject;
	private String mathodName;
    private Exception error;

	public AsyncHttpRequest(Context context, boolean isShowProgressDialog,
			SoapObject mSoapObject, String mathodName, HandlerCallBack callBack) {
		mContext = context;
		this.isShowProgressDialog = isShowProgressDialog;
		this.mSoapObject = mSoapObject;
		mCallBack = callBack;
		this.mathodName = mathodName;
	}

	@Override
	protected void onPreExecute() {
		if (isShowProgressDialog) {
			dialog = new ProgressDialog(mContext);
			dialog.setMessage(mContext.getString(R.string.loading));
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}
	}

	@Override
	protected HashMap<String, Object> doInBackground(Void... params) {
		HashMap<String, Object> result = new HttpHandler(mContext)
				.requestWebService(mSoapObject, mathodName);
		return result;
	}

	@Override
	protected void onPostExecute(HashMap<String, Object> result) {
        if(((Activity) mContext).isDestroyed()) {
            return;
        }
        if (error != null) {
            Toast.makeText(mContext, error.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
		if (null != dialog && dialog.isShowing()) {
			dialog.dismiss();
		}
		if (null != result && !result.isEmpty()) {
			mCallBack.handleFinish(result);
		} else {
			mCallBack.handleFailure();
		}
	}
}
