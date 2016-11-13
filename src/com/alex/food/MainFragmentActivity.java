package com.alex.food;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.alex.food.utils.CommonUtils;
import com.alex.food.utils.ComponentsManager;
import com.alex.food.utils.ConstantUtils;
import com.alex.food.utils.FragmentCallBack;

/**
 * 
 * @author ALEX
 *
 */
public class MainFragmentActivity extends FragmentActivity {
	private Button btnSetting = null;
	private FragmentTransaction mFragmentTransaction = null;

	private LoginFragment mLoginFragment = null;
	private SettingFragment mSettingFragment = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_fragment_activity);
		ComponentsManager.getComponentManager().pushComponent(this);
		
		initScreenDisplay();
		initViews();
	}

	private void initScreenDisplay() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		ConstantUtils.screenWidth = dm.widthPixels;
		ConstantUtils.screenHeight = dm.heightPixels;
	}

	private void initViews() {
		btnSetting = (Button) findViewById(R.id.btnSetting);
		btnSetting.setOnClickListener(listener);

		initLoginFragment();
	}

	private void initLoginFragment() {
		mLoginFragment = new LoginFragment();
		showFragment(mLoginFragment);
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnSetting:
				mSettingFragment = new SettingFragment();
				mSettingFragment.setFragmentCallBack(new FragmentCallBack() {

					@Override
					public void mFragmentClallBack() {
						mLoginFragment = new LoginFragment();
						showFragment(mLoginFragment);
					}
				});
				showFragment(mSettingFragment);
				break;
			}
		}
	};

	public void showFragment(Fragment fragment) {
		mFragmentTransaction = getSupportFragmentManager().beginTransaction();
		mFragmentTransaction.replace(R.id.container, fragment).commit();
		if (null != mLoginFragment && mLoginFragment.isVisible()) {
			btnSetting.setVisibility(View.GONE);
		} else {
			btnSetting.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (null != mLoginFragment && mLoginFragment.isVisible()) {
				CommonUtils.exitSystem(this);
				return true;
			}
			if (null != mSettingFragment && mSettingFragment.isVisible()) {
				initLoginFragment();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
