package com.alex.food;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alex.food.utils.CommonUtils;
import com.alex.food.utils.FragmentCallBack;

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
	private TextView tvRoundingMethod = null;
	private EditText etDecimalPlace = null;
	private Button btnCancel = null;
	private Button btnConfirm = null;
	private FragmentCallBack callBack;
	private RelativeLayout relativeLayout = null;
	private PopupWindow pop = null;
	private View mView = null;
	private TextView tvRound = null;
	private TextView tvUp = null;
	private TextView tvDown = null;
	private LinearLayout linearLayout = null;

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
		tvRoundingMethod = (TextView) getActivity().findViewById(
				R.id.tvRoundingMethod);
		etDecimalPlace = (EditText) getActivity().findViewById(
				R.id.etDecimalPlace);
		btnCancel = (Button) getActivity().findViewById(R.id.btnCancel);
		btnConfirm = (Button) getActivity().findViewById(R.id.btnConfirm);

		mView = getActivity().getLayoutInflater().inflate(R.layout.pop_set,
				null);
		linearLayout = (LinearLayout) mView.findViewById(R.id.linearLayout);
		tvRound = (TextView) mView.findViewById(R.id.tvRound);
		tvUp = (TextView) mView.findViewById(R.id.tvUp);
		tvDown = (TextView) mView.findViewById(R.id.tvDown);

		btnCancel.setOnClickListener(listener);
		btnConfirm.setOnClickListener(listener);
		tvRoundingMethod.setOnClickListener(listener);
		linearLayout.setOnClickListener(listener);
		tvRound.setOnClickListener(listener);
		tvUp.setOnClickListener(listener);
		tvDown.setOnClickListener(listener);

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
		String decimalPlace = CommonUtils.getSharedPreferences(getActivity(),
				getActivity().getString(R.string.decimal_place));
		if (!TextUtils.isEmpty(ip)) {
			etIpAddress.setText(ip);
		}
		if (!TextUtils.isEmpty(shopId)) {
			etShopId.setText(shopId);
		}
		if (!TextUtils.isEmpty(accountId)) {
			etAccountId.setText(accountId);
		}
		if (!TextUtils.isEmpty(categoryId)) {
			etCategoryId.setText(categoryId);
		}
		if (!TextUtils.isEmpty(roundMethod)) {
			tvRoundingMethod.setText(roundMethod);
		} else {
			tvRoundingMethod.setText("Round");
		}
		if (!TextUtils.isEmpty(decimalPlace)) {
			etDecimalPlace.setText(decimalPlace);
		}
	}

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
			}
		}
	};

	private void closePopupWindow() {
		if (null != pop) {
			pop.dismiss();
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
				.getString(R.string.round_method), tvRoundingMethod.getText()
				.toString().trim());
		CommonUtils.setSharedPreferences(getActivity(), getActivity()
				.getString(R.string.decimal_place), etDecimalPlace.getText()
				.toString().trim());

		callBack.mFragmentClallBack();
	}
}
