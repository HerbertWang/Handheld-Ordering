package com.everyware.handheld;

import java.util.HashMap;


import org.ksoap2.serialization.SoapObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.everyware.handheld.R;
import com.everyware.handheld.http.AsyncHttpRequest;
import com.everyware.handheld.http.HandlerCallBack;
import com.everyware.handheld.http.HttpHandler;
import com.everyware.handheld.utils.CommonUtils;
import com.everyware.handheld.utils.ConstantUtils;
import com.everyware.handheld.utils.SoapUtils;

/**
 * 
 * @author ALEX
 *
 */
public class LoginFragment extends Fragment {
	private TextView tvError = null;
	private EditText etStaffNum = null;
	private Button btnLogin = null;
	private String ip;
	private String shopId;
	private String accountId;
	private String categoryId;
	private String roundMethod;
	private String decimalPlace;
    private String activationCode;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.login_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initViews();
	}

	private void initViews() {
		tvError = (TextView) getActivity().findViewById(R.id.tvError);
		etStaffNum = (EditText) getActivity().findViewById(R.id.etStaffNum);
		btnLogin = (Button) getActivity().findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(listener);
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!isSystemSetup()) {
				tvError.setText(String.format(
						getActivity().getString(R.string.login_error),
						getActivity().getString(
								R.string.system_setup_no_complete)));
				return;
			}
			if (!TextUtils.isEmpty(etStaffNum.getText().toString().trim())) {
				checkNetWork();
			}
		}
	};

	private boolean isSystemSetup() {
        try {
            ip = CommonUtils.getSharedPreferences(getActivity(), getActivity()
                    .getString(R.string.ip));
            shopId = CommonUtils.getSharedPreferences(getActivity(), getActivity()
                    .getString(R.string.shop_id));
            accountId = CommonUtils.getSharedPreferences(getActivity(),
                    getActivity().getString(R.string.account_id));
            categoryId = CommonUtils.getSharedPreferences(getActivity(),
                    getActivity().getString(R.string.category_id));
            roundMethod = CommonUtils.getSharedPreferences(getActivity(),
                    getActivity().getString(R.string.round_method));
            decimalPlace = CommonUtils.getSharedPreferences(getActivity(),
                    getActivity().getString(R.string.decimal_place));
            //activationCode = CommonUtils.getSharedPreferences(getActivity(),
            //        getActivity().getString(R.string.activation_code));
            if (!TextUtils.isEmpty(ip) && !TextUtils.isEmpty(shopId)
                    && !TextUtils.isEmpty(accountId)
                    && !TextUtils.isEmpty(categoryId)
                    && !TextUtils.isEmpty(roundMethod)
                    && !TextUtils.isEmpty(decimalPlace)) {

                String quickCodeTextPad = CommonUtils.getSharedPreferences(getActivity(),
                        getActivity().getString(R.string.quick_code_text_pad));

                if(!TextUtils.isEmpty(quickCodeTextPad))
                {
                    if("true".equals(quickCodeTextPad)) {
                        ConstantUtils.isQuickCodeTextPad = true;
                    } else {
                        ConstantUtils.isQuickCodeTextPad = false;
                    }
                } else {
                    ConstantUtils.isQuickCodeTextPad = false;
                }

                return true;
            }
        }catch (Exception e) {
            setErrorPrompt(getActivity().getString(
                    R.string.remote_server_error));
        }
		return false;
	}

	private void checkNetWork() {
		if (CommonUtils.networkIsAvaiable(getActivity())) {
			login();
		} else {
			tvError.setText(String.format(
					getActivity().getString(R.string.login_error),
					getActivity().getString(R.string.network_error)));
		}
	}

	private void login() {
        try {
            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.GET_LOGIN_USER_INFORMATION);
            mSoapObject.addProperty(ConstantUtils.ACCOUNT_ID, CommonUtils
                    .getSharedPreferences(getActivity(),
                            getActivity().getString(R.string.account_id)));
            mSoapObject.addProperty(ConstantUtils.SHOP_ID, CommonUtils
                    .getSharedPreferences(getActivity(),
                            getActivity().getString(R.string.shop_id)));
            mSoapObject.addProperty("staffCode", etStaffNum.getText().toString()
                    .trim());

            new AsyncHttpRequest(getActivity(), true, mSoapObject,
                    SoapUtils.GET_LOGIN_USER_INFORMATION, new HandlerCallBack() {

                @Override
                public void handleFinish(HashMap<String, Object> result) {
                    try {
                        if (null != result && !result.isEmpty()) {
                            if (!TextUtils.isEmpty(String.valueOf(result
                                    .get(HttpHandler.STATUS)))) {
                                if (HttpHandler.RESULT_SUCCESS
                                        .equals(String.valueOf(result
                                                .get(HttpHandler.STATUS)))) {
                                    Object object = result
                                            .get(HttpHandler.RESULT);
                                    if (null != object) {
                                        SoapObject soapObject = (SoapObject) object;
                                        String resultState = String.valueOf(soapObject.getProperty(String
                                                .format(SoapUtils.REQUEST_METHOD_RESULT,
                                                        SoapUtils.GET_LOGIN_USER_INFORMATION)));
                                        if (ConstantUtils.IS_OK
                                                .equals(resultState)) {
                                            SoapObject user = (SoapObject) soapObject
                                                    .getProperty("userDto");
                                            if (null != user
                                                    && !"null".equals(user)) {
                                                tvError.setText("");
                                                setUserInformation(user);
                                            } else {
                                                setErrorPrompt(getActivity()
                                                        .getString(
                                                                R.string.result_is_error));
                                            }
                                        } else if (ConstantUtils.IS_Error
                                                .equals(resultState)) {
                                            setErrorPrompt(getActivity()
                                                    .getString(
                                                            R.string.result_is_error));
                                        } else {
                                            setErrorPrompt(getActivity()
                                                    .getString(
                                                            R.string.result_is_invalid));
                                        }
                                    }
                                } else {
                                    setErrorPrompt(String.valueOf(result
                                            .get(HttpHandler.RESULT)));
                                }
                            } else {
                                setErrorPrompt(getActivity().getString(
                                        R.string.remote_server_error));
                            }
                        } else {
                            setErrorPrompt(getActivity().getString(
                                    R.string.remote_server_error));
                        }
                    } catch (Exception e) {
                        setErrorPrompt(getActivity().getString(
                                R.string.remote_server_error));
                    }
                }

                @Override
                public void handleFailure() {
                    setErrorPrompt(getActivity().getString(
                            R.string.remote_server_error));
                }
            }).execute();
        }catch (Exception e) {
            setErrorPrompt(getActivity().getString(
                    R.string.remote_server_error));
        }
	}

	private void setErrorPrompt(String text) {
		tvError.setText(String.format(
				getActivity().getString(R.string.login_error), text));
	}

	private void setUserInformation(SoapObject user) {
        try {
            if (null != user.getProperty("AccountId")) {
                ConstantUtils.userInfo.setAccountId(String.valueOf(user
                        .getProperty("AccountId")));
            }
            if (null != user.getProperty("CardNo")) {
                ConstantUtils.userInfo.setCardNo(String.valueOf(user
                        .getProperty("CardNo")));
            }
            if (null != user.getProperty("CreatedBy")) {
                ConstantUtils.userInfo.setCreatedBy(String.valueOf(user
                        .getProperty("CreatedBy")));
            }
            if (null != user.getProperty("CreatedDate")) {
                ConstantUtils.userInfo.setCreatedDate(String.valueOf(user
                        .getProperty("CreatedDate")));
            }
            if (null != user.getProperty("EffectiveDateFrom")) {
                ConstantUtils.userInfo.setEffectiveDateFrom(String.valueOf(user
                        .getProperty("EffectiveDateFrom")));
            }
            if (null != user.getProperty("EffectiveDateTo")) {
                ConstantUtils.userInfo.setEffectiveDateTo(String.valueOf(user
                        .getProperty("EffectiveDateTo")));
            }
            if (null != user.getProperty("EnableCardNoLogin")) {
                ConstantUtils.userInfo.setEnableCardNoLogin(String.valueOf(user
                        .getProperty("EnableCardNoLogin")));
            }
            if (null != user.getProperty("EnableStaffCodeLogin")) {
                ConstantUtils.userInfo.setEnableStaffCodeLogin(String.valueOf(user
                        .getProperty("EnableStaffCodeLogin")));
            }
            if (null != user.getProperty("EnableUserIdLogin")) {
                ConstantUtils.userInfo.setEnableUserIdLogin(String.valueOf(user
                        .getProperty("EnableUserIdLogin")));
            }
            if (null != user.getProperty("Enabled")) {
                ConstantUtils.userInfo.setEnabled(String.valueOf(user
                        .getProperty("Enabled")));
            }
            if (null != user.getProperty("ModifiedBy")) {
                ConstantUtils.userInfo.setModifiedBy(String.valueOf(user
                        .getProperty("ModifiedBy")));
            }
            if (null != user.getProperty("ModifiedDate")) {
                ConstantUtils.userInfo.setModifiedDate(String.valueOf(user
                        .getProperty("ModifiedDate")));
            }
            if (null != user.getProperty("ShopId")) {
                ConstantUtils.userInfo.setShopId(String.valueOf(user
                        .getProperty("ShopId")));
            }
            if (null != user.getProperty("StaffCode")) {
                ConstantUtils.userInfo.setStaffCode(String.valueOf(user
                        .getProperty("StaffCode")));
            }
            if (null != user.getProperty("UserAltName")) {
                ConstantUtils.userInfo.setUserAltName(String.valueOf(user
                        .getProperty("UserAltName")));
            }
            if (null != user.getProperty("UserId")) {
                ConstantUtils.userInfo.setUserId(String.valueOf(user
                        .getProperty("UserId")));
            }
            if (null != user.getProperty("UserName")) {
                ConstantUtils.userInfo.setUserName(String.valueOf(user
                        .getProperty("UserName")));
            }

            CommonUtils.changeActivity(getActivity(),
                    TableListFragmentActivity.class);
        } catch (Exception e) {
            setErrorPrompt(getActivity().getString(
                    R.string.remote_server_error));
        }
    }
}
