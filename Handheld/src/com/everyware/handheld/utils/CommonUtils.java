package com.everyware.handheld.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.everyware.handheld.R;

/**
 * 
 * @author ALEX
 * 
 */
public class CommonUtils {

	private static final String TAG = "CommonUtils";

	public static void changeActivity(Context context, Class<?> toClass) {
		Intent intent = new Intent(context, toClass);
		context.startActivity(intent);
	}

	public static void exitSystem(Context context) {
		new AlertDialog.Builder(context)
				.setTitle(context.getString(R.string.system_prompt))
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setMessage(context.getString(R.string.exit_system))
				.setPositiveButton(context.getString(R.string.confirm),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								ComponentsManager.getComponentManager()
										.popAllComponent();
								System.exit(0);
							}
						})
				.setNegativeButton(context.getString(R.string.cancel),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}

	public static void setSharedPreferences(Context context, String key,
			String value) {
		SharedPreferences sp = context.getSharedPreferences(
				context.getString(R.string.sharedpreferences),
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getSharedPreferences(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(
				context.getString(R.string.sharedpreferences),
				Context.MODE_PRIVATE);
		return sp.getString(key, "");
	}

	public static boolean networkIsAvaiable(Context context) {
		ConnectivityManager connManager = null;
		NetworkInfo networkInfo = null;
		boolean result = false;
		try {
			connManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			networkInfo = connManager.getActiveNetworkInfo();
			if (networkInfo != null) {
				result = networkInfo.isConnected();
			}
			return result;
		} catch (Exception ex) {
			printLog(TAG, "networkIsAvaiable", ex.getMessage());
			return result;
		} finally {
			connManager = null;
			networkInfo = null;
		}
	}

	public static void printLog(String className, String methodName, String info) {
		if (null != className && null != methodName && null != info) {
			System.out.println(String.format("%s:%s---->%s", className,
					methodName, info));
		}
	}

	public static void showToast(Context context, String text) {
		if (null != context && !TextUtils.isEmpty(text)) {
			Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	public static String getCurrentDateTime() {
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String currentTime = sdf.format(date);
		currentTime = currentTime.replace(" ", "T");
		return currentTime;
	}

	public static String getCurrentDate() {
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = sdf.format(date);
		return currentDate;
	}

    public static String getAmount(String qty, String price) {
        BigDecimal mQty = new BigDecimal(qty);
        BigDecimal mPrice = new BigDecimal(price);
        BigDecimal amount = mQty.multiply(mPrice);
        return amount.toString();
    }
}
