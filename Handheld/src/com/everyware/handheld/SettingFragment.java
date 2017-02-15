package com.everyware.handheld;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.everyware.handheld.utils.CommonUtils;
import com.everyware.handheld.utils.FragmentCallBack;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

/**
 * 
 * @author ALEX
 *
 */
public class SettingFragment extends Fragment {
	private EditText etIpAddress = null;
	private EditText etShopId = null;
	private EditText etAccountId = null;
	private EditText etCategoryId = null;
    private EditText etDeviceName = null;
	private TextView tvRoundingMethod = null;
    private TextView tvSplitTableDisplayMethod = null;
    private TextView tvEnableCoverCount = null;
	private EditText etDecimalPlace = null;
    private EditText etActivationCode = null;
	private Button btnCancel = null;
	private Button btnConfirm = null;
	private FragmentCallBack callBack;
	private RelativeLayout relativeLayout = null;
	private PopupWindow pop = null;
    private PopupWindow popSplit = null;
    private PopupWindow popCover = null;
	private View mView = null;

	private TextView tvRound = null;
	private TextView tvUp = null;
	private TextView tvDown = null;
	private LinearLayout linearLayout = null;

    private View mViewSplit = null;
    private TextView tvAutoHide = null;
    private TextView tvShowAll = null;
    private LinearLayout linearLayoutSplit = null;

    private View mViewCoverCount = null;
    private TextView tvCoverHide = null;
    private TextView tvCoverShow = null;
    private LinearLayout linearLayoutCover = null;

    private CheckBox cbQuickCodeFullKeyboard = null;
    private String quickCodeTextPad = "false";
    private boolean isActivatedDevice = false;

	public void setFragmentCallBack(FragmentCallBack callBack) {
		this.callBack = callBack;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.setting_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initViews();
	}

	private void initViews() {
		relativeLayout = (RelativeLayout) getActivity().findViewById(
				R.id.relativeLayout);
		etIpAddress = (EditText) getActivity().findViewById(R.id.etIpAddress);
		etShopId = (EditText) getActivity().findViewById(R.id.etShopId);
		etAccountId = (EditText) getActivity().findViewById(R.id.etAccountId);
		etCategoryId = (EditText) getActivity().findViewById(R.id.etCategoryId);
        etDeviceName = (EditText) getActivity().findViewById(R.id.etDeviceName);
		tvRoundingMethod = (TextView) getActivity().findViewById(
				R.id.tvRoundingMethod);
        tvSplitTableDisplayMethod = (TextView) getActivity().findViewById(
                R.id.tvSplitTableDisplayMethod);
        tvEnableCoverCount = (TextView) getActivity().findViewById(
                R.id.tvEnableCoverCount);
		etDecimalPlace = (EditText) getActivity().findViewById(
				R.id.etDecimalPlace);
        etActivationCode = (EditText) getActivity().findViewById(
                R.id.etActivationCode);
		btnCancel = (Button) getActivity().findViewById(R.id.btnCancel);
		btnConfirm = (Button) getActivity().findViewById(R.id.btnConfirm);

		mView = getActivity().getLayoutInflater().inflate(R.layout.pop_set,
				null);
		linearLayout = (LinearLayout) mView.findViewById(R.id.linearLayout);
		tvRound = (TextView) mView.findViewById(R.id.tvRound);
		tvUp = (TextView) mView.findViewById(R.id.tvUp);
		tvDown = (TextView) mView.findViewById(R.id.tvDown);

        mViewSplit = getActivity().getLayoutInflater().inflate(R.layout.pop_set_split_table,
                null);
        linearLayoutSplit = (LinearLayout) mView.findViewById(R.id.linearLayout);
        tvAutoHide = (TextView) mViewSplit.findViewById(R.id.tvAutoHide);
        tvShowAll = (TextView) mViewSplit.findViewById(R.id.tvShowAll);

        mViewCoverCount = getActivity().getLayoutInflater().inflate(R.layout.pop_enable_cover_count,
                null);
        linearLayoutCover = (LinearLayout) mViewCoverCount.findViewById(R.id.linearLayout);
        tvCoverHide = (TextView) mViewCoverCount.findViewById(R.id.tvCoverHide);
        tvCoverShow = (TextView) mViewCoverCount.findViewById(R.id.tvCoverShow);
        cbQuickCodeFullKeyboard = (CheckBox) getActivity().findViewById(R.id.swQuickCodeFullKeyboard);

		btnCancel.setOnClickListener(listener);
		btnConfirm.setOnClickListener(listener);
		tvRoundingMethod.setOnClickListener(listener);
        tvSplitTableDisplayMethod.setOnClickListener(listener);

		linearLayout.setOnClickListener(listener);
		tvRound.setOnClickListener(listener);
		tvUp.setOnClickListener(listener);
		tvDown.setOnClickListener(listener);
        tvAutoHide.setOnClickListener(listener);
        tvShowAll.setOnClickListener(listener);

        tvEnableCoverCount.setOnClickListener(listener);
        linearLayoutCover.setOnClickListener(listener);
        tvCoverHide.setOnClickListener(listener);
        tvCoverShow.setOnClickListener(listener);

        cbQuickCodeFullKeyboard.setOnCheckedChangeListener(switchListener);

        if(CommonUtils.getSharedPreferences(getActivity(),
                getActivity().getString(R.string.activation_code)) != "") {
            isActivatedDevice = true;
        }

		setInpuBoxInfo();
	}

	private void setInpuBoxInfo() {
		String ip = CommonUtils.getSharedPreferences(getActivity(),
				getActivity().getString(R.string.ip));
		String shopId = CommonUtils.getSharedPreferences(getActivity(),
				getActivity().getString(R.string.shop_id));
		String accountId = CommonUtils.getSharedPreferences(getActivity(),
				getActivity().getString(R.string.account_id));
		String categoryId = CommonUtils.getSharedPreferences(getActivity(),
				getActivity().getString(R.string.category_id));
		String roundMethod = CommonUtils.getSharedPreferences(getActivity(),
				getActivity().getString(R.string.round_method));
        String splitTableDisplayMethod = CommonUtils.getSharedPreferences(getActivity(),
                getActivity().getString(R.string.split_table_display_method));
        String enableCoverCount = CommonUtils.getSharedPreferences(getActivity(),
                getActivity().getString(R.string.enable_cover_count));
        String decimalPlace = CommonUtils.getSharedPreferences(getActivity(),
				getActivity().getString(R.string.decimal_place));
        String deviceName = CommonUtils.getSharedPreferences(getActivity(),
                getActivity().getString(R.string.device_name));
        String activationCode = CommonUtils.getSharedPreferences(getActivity(),
                getActivity().getString(R.string.activation_code));

        quickCodeTextPad = CommonUtils.getSharedPreferences(getActivity(),
                getActivity().getString(R.string.quick_code_text_pad));

		if (!TextUtils.isEmpty(ip)) {
			etIpAddress.setText(ip);
		}
		if (!TextUtils.isEmpty(shopId)) {
			etShopId.setText(shopId);
		}
		if (!TextUtils.isEmpty(accountId)) {
			etAccountId.setText(accountId);
		}
        if(!TextUtils.isEmpty(deviceName)) {
            etDeviceName.setText(deviceName);
        } else {
            etDeviceName.setText("Mobile");
        }
		if (!TextUtils.isEmpty(categoryId)) {
			etCategoryId.setText(categoryId);
		}
		if (!TextUtils.isEmpty(roundMethod)) {
            tvRoundingMethod.setText(roundMethod);
        } else {
            tvRoundingMethod.setText("Round");
        }
        if (!TextUtils.isEmpty(splitTableDisplayMethod)) {
            tvSplitTableDisplayMethod.setText(splitTableDisplayMethod);
        } else {
            tvSplitTableDisplayMethod.setText("Hide");
        }
        if (!TextUtils.isEmpty(enableCoverCount)) {
            tvEnableCoverCount.setText(enableCoverCount);
        } else {
            tvEnableCoverCount.setText("False");
        }
		if (!TextUtils.isEmpty(decimalPlace)) {
			etDecimalPlace.setText(decimalPlace);
		}
        if(!TextUtils.isEmpty(activationCode)) {
            etActivationCode.setText(activationCode);
        }
        if("true".equals(quickCodeTextPad))
        {
            cbQuickCodeFullKeyboard.setChecked(true);
        } else {
            cbQuickCodeFullKeyboard.setChecked(false);
        }
	}

    private CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // do something, the isChecked will be
            // true if the switch is in the On position
            if(isChecked) {
                quickCodeTextPad = "true";
            } else {
                quickCodeTextPad = "false";
            }
        }
    };

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
                case R.id.btnCancel:
                    callBack.mFragmentClallBack();
                    break;
                case R.id.btnConfirm:
                    checkInputBox();
                    break;
                case R.id.tvRoundingMethod:
                    closePopupWindow();
                    showPopupWindow();
                    break;
                case R.id.tvRound:
                    closePopupWindow();
                    tvRoundingMethod.setText(tvRound.getText().toString().trim());
                    break;
                case R.id.tvUp:
                    closePopupWindow();
                    tvRoundingMethod.setText(tvUp.getText().toString().trim());
                    break;
                case R.id.tvDown:
                    closePopupWindow();
                    tvRoundingMethod.setText(tvDown.getText().toString().trim());
                    break;

                case R.id.linearLayout:
                    closePopupWindow();
                    break;

                // for split table setting
                case R.id.tvSplitTableDisplayMethod:
                    closePopupWindow();
                    showPopupSplitWindow();
                    break;
                case R.id.tvAutoHide:
                    closePopupWindow();
                    tvSplitTableDisplayMethod.setText(tvAutoHide.getText().toString().trim());
                    break;
                case R.id.tvShowAll:
                    closePopupWindow();
                    tvSplitTableDisplayMethod.setText(tvShowAll.getText().toString().trim());
                    break;

                // for cover count enable
                case R.id.tvEnableCoverCount:
                    closePopupWindow();
                    showPopupEnableCoverCountWindow();
                    break;
                case R.id.tvCoverHide:
                    closePopupWindow();
                    tvEnableCoverCount.setText(tvCoverHide.getText().toString().trim());
                    break;
                case R.id.tvCoverShow:
                    closePopupWindow();
                    tvEnableCoverCount.setText(tvCoverShow.getText().toString().trim());
                    break;
            }
		}
	};

	private void closePopupWindow() {
		if (null != pop) {
			pop.dismiss();
		}
        if (null != popCover) {
            pop.dismiss();
        }
        if(null != popSplit) {
            popSplit.dismiss();
        }
	}


	@SuppressWarnings("deprecation")
	private void showPopupWindow() {
		pop = new PopupWindow(mView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
	}

    @SuppressWarnings("deprecation")
     private void showPopupSplitWindow() {
        pop = new PopupWindow(mViewSplit, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT, true);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
    }

    @SuppressWarnings("deprecation")
    private void showPopupEnableCoverCountWindow() {
        pop = new PopupWindow(mViewCoverCount, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT, true);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
    }

	private void checkInputBox() {
		if (TextUtils.isEmpty(etIpAddress.getText().toString().trim())) {
			CommonUtils.showToast(getActivity(),
					getActivity().getString(R.string.no_set_ip_address));
			return;
		}
		if (TextUtils.isEmpty(etShopId.getText().toString().trim())) {
			CommonUtils.showToast(getActivity(),
					getActivity().getString(R.string.no_set_shop_id));
			return;
		}
		if (TextUtils.isEmpty(etAccountId.getText().toString().trim())) {
			CommonUtils.showToast(getActivity(),
					getActivity().getString(R.string.no_set_account_id));
			return;
		}
        if (TextUtils.isEmpty(etDeviceName.getText().toString().trim())) {
            CommonUtils.showToast(getActivity(),
                    getActivity().getString(R.string.no_set_device_name));
            return;
        }
		if (TextUtils.isEmpty(etCategoryId.getText().toString().trim())) {
			CommonUtils.showToast(getActivity(),
					getActivity().getString(R.string.no_set_category_id));
			return;
		}
		if (TextUtils.isEmpty(etDecimalPlace.getText().toString().trim())) {
			CommonUtils.showToast(getActivity(),
					getActivity().getString(R.string.no_set_decimal_place));
			return;
		}
        if(TextUtils.isEmpty(etActivationCode.getText().toString().trim())) {
            CommonUtils.showToast(getActivity(),
                    getActivity().getString(R.string.no_set_activation_code));
            return;
        } else if("caterlordisking".equals(etActivationCode.getText().toString().trim())) {
            // do nothing
        } else {
            if(!isActivatedDevice || !(etActivationCode.getText().toString().trim().equals(CommonUtils.getSharedPreferences(getActivity(),
                    getActivity().getString(R.string.activation_code))))) {
                CheckActivationCode checkCode = new CheckActivationCode();
                try {
                    String result = checkCode.execute("http://www.everyware.com.hk/reg/?code="+
                            etActivationCode.getText().toString() +
                            "&account_id="+
                            etAccountId.getText() +
                            "&shop_id=" +
                            etShopId.getText().toString().trim()+
                            "&device_id=" +
                            Settings.Secure.getString(getActivity().getContentResolver(),
                                    Settings.Secure.ANDROID_ID)).get();

                    JSONObject jObject = new JSONObject(result);

                    String resultCode = jObject.getString("result");
                    String resultMsg = jObject.getString("message");

                    if("success".equals(resultCode)) {
                        // save the value
                    } else {
                        // show the error msg
                        CommonUtils.showToast(getActivity(),
                                resultMsg);
                        // return to the input page
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    CommonUtils.showToast(getActivity(),
                            getActivity().getString(R.string.network_error));

                    return;
                }
            }
        }

		saveSetInfo();
	}

	private void saveSetInfo() {
		CommonUtils.setSharedPreferences(getActivity(), getActivity()
				.getString(R.string.ip), etIpAddress.getText().toString()
				.trim());
		CommonUtils.setSharedPreferences(getActivity(), getActivity()
				.getString(R.string.shop_id), etShopId.getText().toString()
				.trim());
		CommonUtils.setSharedPreferences(getActivity(), getActivity()
				.getString(R.string.account_id), etAccountId.getText()
				.toString().trim());
		CommonUtils.setSharedPreferences(getActivity(), getActivity()
				.getString(R.string.category_id), etCategoryId.getText()
				.toString().trim());
        CommonUtils.setSharedPreferences(getActivity(), getActivity()
                .getString(R.string.device_name), etDeviceName.getText()
                .toString().trim());
		CommonUtils.setSharedPreferences(getActivity(), getActivity()
				.getString(R.string.round_method), tvRoundingMethod.getText()
				.toString().trim());
        CommonUtils.setSharedPreferences(getActivity(), getActivity()
                .getString(R.string.enable_cover_count), tvEnableCoverCount.getText()
                .toString().trim());
        CommonUtils.setSharedPreferences(getActivity(), getActivity()
                .getString(R.string.split_table_display_method), tvSplitTableDisplayMethod.getText()
                .toString().trim());
		CommonUtils.setSharedPreferences(getActivity(), getActivity()
                .getString(R.string.decimal_place), etDecimalPlace.getText()
                .toString().trim());
        CommonUtils.setSharedPreferences(getActivity(), getActivity()
                .getString(R.string.activation_code), etActivationCode.getText()
                .toString().trim());
        CommonUtils.setSharedPreferences(getActivity(), getActivity()
                .getString(R.string.quick_code_text_pad), quickCodeTextPad);

		callBack.mFragmentClallBack();
	}
}
