package com.alex.food;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.AttributeInfo;
import org.ksoap2.serialization.SoapObject;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alex.food.adapter.FragmentAdapter;
import com.alex.food.adapter.SplitTableListAdapter;
import com.alex.food.bean.TableDto;
import com.alex.food.bean.TableSectionList;
import com.alex.food.http.AsyncHttpRequest;
import com.alex.food.http.HandlerCallBack;
import com.alex.food.http.HttpHandler;
import com.alex.food.utils.CommonUtils;
import com.alex.food.utils.ComponentsManager;
import com.alex.food.utils.ConstantUtils;
import com.alex.food.utils.HandleHttpRequestResult;
import com.alex.food.utils.OnArticleSelectedListener;
import com.alex.food.utils.SoapUtils;

/**
 * 
 * @author ALEX
 * 
 */
public class TableListFragmentActivity extends FragmentActivity implements
		OnArticleSelectedListener {
	private static final String TAG = "com.alex.food.TableListFragmentActivity";
	private Button btnLogout = null;
	private Button btnSplit = null;
	private ViewPager viewPager = null;
	private List<TableSectionList> list = new ArrayList<TableSectionList>();
	private List<TableDto> tableList = new ArrayList<TableDto>();
	private FragmentAdapter adapter = null;
//	private List<Fragment> fragments = new ArrayList<Fragment>();
	private TextView tvShadow = null;
	private TextView tvName = null;
	private PopupWindow pop = null;
	private View tabView = null;
	private GridView gridView = null;
	private SplitTableListAdapter splitAdapter = null;
	private Button btnTabCancel = null;
	private View proView = null;
	private TextView tvTitle = null;
	private TextView tvPrompt = null;
	private Button btnYes = null;
	private Button btnNo = null;
	private OnArticleSelectedListener mListener = null;
	private LinearLayout linearLayout = null;
	private boolean isClear = false;
	private String mSectionId = "";
	private String mSectionName = "";
	private String mTableId = "";

	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);

		try {
			mListener = (OnArticleSelectedListener) fragment;
		} catch (Exception e) {
			CommonUtils.printLog(TAG, "onAttachFragment", e.getMessage());
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablelist);
		ComponentsManager.getComponentManager().pushComponent(this);

		initViews();
	}

	private void initViews() {
        try {
            linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
            btnLogout = (Button) findViewById(R.id.btnLogout);
            btnSplit = (Button) findViewById(R.id.btnSplit);
            viewPager = (ViewPager) findViewById(R.id.viewPager);
            tvShadow = (TextView) findViewById(R.id.tvShadow);
            tvName = (TextView) findViewById(R.id.tvName);

            btnLogout.setOnClickListener(listener);
            btnSplit.setOnClickListener(listener);

            initViewPagerListener();
            initTabPopupWindow();
            initProPopupWindow();
            getData(true);

            ConstantUtils.decimal_place = CommonUtils.getSharedPreferences(this,
                    getString(R.string.decimal_place));
            ConstantUtils.ip = CommonUtils.getSharedPreferences(this,
                    getString(R.string.ip));
            ConstantUtils.shop_id = CommonUtils.getSharedPreferences(this,
                    getString(R.string.shop_id));
            ConstantUtils.account_id = CommonUtils.getSharedPreferences(this,
                    getString(R.string.account_id));
            ConstantUtils.round_method = CommonUtils.getSharedPreferences(this,
                    getString(R.string.round_method));
            ConstantUtils.category_id = CommonUtils.getSharedPreferences(this,
                    getString(R.string.category_id));
        } catch (Exception e) {
            CommonUtils.showToast(
                    TableListFragmentActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void initTabPopupWindow() {
        try {
            tabView = getLayoutInflater().inflate(R.layout.pop_split_table, null);
            gridView = (GridView) tabView.findViewById(R.id.gridView);
            btnTabCancel = (Button) tabView.findViewById(R.id.btnTabCancel);
            btnTabCancel.setOnClickListener(listener);
            splitAdapter = new SplitTableListAdapter(this, tableList);
            gridView.setAdapter(splitAdapter);
            gridView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
                    ConstantUtils.mTableDto = tableList.get(position);
                    CommonUtils.changeActivity(TableListFragmentActivity.this,
                            FoodCategoryActivity.class);
                }
            });
        } catch (Exception e) {
            CommonUtils.showToast(
                    TableListFragmentActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void initProPopupWindow() {
        try {
            proView = getLayoutInflater().inflate(R.layout.pop_prompt, null);
            tvTitle = (TextView) proView.findViewById(R.id.tvTitle);
            tvPrompt = (TextView) proView.findViewById(R.id.tvPrompt);
            btnYes = (Button) proView.findViewById(R.id.btnYes);
            btnNo = (Button) proView.findViewById(R.id.btnNo);
            btnNo.setVisibility(View.GONE);

            btnYes.setOnClickListener(listener);
            btnNo.setOnClickListener(listener);
        } catch (Exception e) {
            CommonUtils.showToast(
                    TableListFragmentActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void showPopupWindow(View view) {
        try {
            closePop();
            pop = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT, true);
            pop.setOutsideTouchable(true);
            pop.setBackgroundDrawable(new BitmapDrawable());
            pop.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            CommonUtils.showToast(
                    TableListFragmentActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void closePop() {
		if (null != pop && pop.isShowing()) {
			pop.dismiss();
		}
	}

	private void initViewPagerListener() {
        try {
            viewPager.setOnPageChangeListener(new OnPageChangeListener() {

                @Override
                public void onPageSelected(int arg0) {
                    if (!TextUtils.isEmpty(list.get(arg0).getSectionName())) {
                        mSectionName = list.get(arg0).getSectionName();
                        tvName.setText(mSectionName);
                        mSectionId = list.get(arg0).getSectionId();
                        ConstantUtils.sectionName = mSectionName;
                        ConstantUtils.sectionId = mSectionId;
                    }
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int arg0) {

                }
            });
        } catch (Exception e) {
            CommonUtils.showToast(
                    TableListFragmentActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
        try {
            switch (v.getId()) {
            case R.id.btnLogout:
                CommonUtils.changeActivity(TableListFragmentActivity.this,
                        MainFragmentActivity.class);
                finish();
                break;
            case R.id.btnSplit:
                if (null != list && list.size() > 0) {
                    tvShadow.setVisibility(View.VISIBLE);
                    btnLogout.setEnabled(false);
                    btnSplit.setEnabled(false);
                    ConstantUtils.isSplit = true;
                }
                break;
            case R.id.btnTabCancel:
                closePop();
                cancelSplit();
                break;
            case R.id.btnYes:
                closePop();
                if (isClear) {
                    clearTable(true);
                }
                break;
            case R.id.btnNo:
                closePop();
                break;
            }
        } catch (Exception e) {
            CommonUtils.showToast(
                    TableListFragmentActivity.this,
                    getString(R.string.remote_server_error));
        }
        }
	};

	private void getData(final boolean isShow) {
        try {
            Time requestSendDatetime = new Time();
            requestSendDatetime.setToNow();

            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.GET_AVAILABLE_TABLE_SECTION_LIST);
            if (!TextUtils.isEmpty(ConstantUtils.userInfo.getAccountId())) {
                mSoapObject.addProperty(ConstantUtils.ACCOUNT_ID,
                        ConstantUtils.userInfo.getAccountId());
            }
            if (!TextUtils.isEmpty(ConstantUtils.userInfo.getShopId())) {
                mSoapObject.addProperty(ConstantUtils.SHOP_ID,
                        ConstantUtils.userInfo.getShopId());
            }

            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.GET_AVAILABLE_TABLE_SECTION_LIST,
                    new HandlerCallBack() {

                        @Override
                        public void handleFinish(HashMap<String, Object> result) {
                            try {
                                if (HandleHttpRequestResult.isError(
                                        TableListFragmentActivity.this, isShow,
                                        SoapUtils.GET_AVAILABLE_TABLE_SECTION_LIST,
                                        result)) {
                                    return;
                                }
                                SoapObject object = HandleHttpRequestResult
                                        .getResultSoapObject(result);
                                if (null != object
                                        && 0 != object.getPropertyCount()) {
                                    if (null != list) {
                                        list.clear();
                                    }
                                    SoapObject mSoapObject = null;
                                    TableSectionList bean = null;
                                    for (int i = 0; i < object.getPropertyCount(); i++) {
                                        mSoapObject = (SoapObject) object
                                                .getProperty(i);
                                        if (null != mSoapObject) {
                                            bean = new TableSectionList();
                                            if (null != mSoapObject
                                                    .getProperty("SectionId")) {
                                                bean.setSectionId(String.valueOf(mSoapObject
                                                        .getProperty("SectionId")));
                                            }
                                            if (null != mSoapObject
                                                    .getProperty("SectionName")) {
                                                bean.setSectionName(String.valueOf(mSoapObject
                                                        .getProperty("SectionName")));
                                            }
                                            if (null != mSoapObject
                                                    .getProperty("SectionNameAlt")) {
                                                bean.setSectionNameAlt(String.valueOf(mSoapObject
                                                        .getProperty("SectionNameAlt")));
                                            }
                                            list.add(bean);
                                            bean = null;
                                        }
                                    }
                                    if (null != list && list.size() > 0) {
                                        addFragments();
                                    }
                                } else {
                                    CommonUtils.showToast(
                                            TableListFragmentActivity.this,
                                            getString(R.string.no_data));
                                }
                            } catch (Exception e) {
                                CommonUtils.showToast(
                                        TableListFragmentActivity.this,
                                        getString(R.string.remote_server_error));
                            }
                        }

                        @Override
                        public void handleFailure() {
                            CommonUtils.showToast(TableListFragmentActivity.this,
                                    getString(R.string.remote_server_error));
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(
                    TableListFragmentActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void addFragments() {
        try {
            if (!TextUtils.isEmpty(list.get(0).getSectionName())) {
                mSectionName = list.get(0).getSectionName();
                tvName.setText(mSectionName);
                mSectionId = list.get(0).getSectionId();
                ConstantUtils.sectionId = mSectionId;
                ConstantUtils.sectionName = mSectionName;
            }
            List<Fragment> fragments = new ArrayList<Fragment>();
            for (int i = 0; i < list.size(); i++) {
                Fragment fragment = TableListFragment.newInstance(list.get(i)
                        .getSectionId());
                fragments.add(fragment);
            }
            adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
            viewPager.setAdapter(adapter);
        } catch (Exception e) {
            CommonUtils.showToast(
                    TableListFragmentActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void getSplitTableList(final boolean isShow, String accountId,
                                   String shopId, String tableCode) {
        try {
            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.GET_AVAILABLE_SPLIT_TABLE_LIST);

            mSoapObject.addProperty(ConstantUtils.ACCOUNT_ID, accountId);
            mSoapObject.addProperty(ConstantUtils.SHOP_ID, shopId);
            mSoapObject.addProperty(ConstantUtils.TABLE_CODE, tableCode);

            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.GET_AVAILABLE_SPLIT_TABLE_LIST,
                    new HandlerCallBack() {

                        @Override
                        public void handleFinish(HashMap<String, Object> result) {
                            try {
                                if (HandleHttpRequestResult.isError(
                                        TableListFragmentActivity.this, isShow,
                                        SoapUtils.GET_AVAILABLE_SPLIT_TABLE_LIST,
                                        result)) {
                                    return;
                                }
                                SoapObject object = HandleHttpRequestResult
                                        .getResultSoapObject(result);
                                if (null != object
                                        && 0 != object.getPropertyCount()) {
                                    parseSplitTableList(object);
                                } else {
                                    if (isShow) {
                                        CommonUtils.showToast(
                                                TableListFragmentActivity.this,
                                                getString(R.string.no_data));
                                    }
                                }
                            } catch (Exception e) {
                                if (isShow) {
                                    CommonUtils
                                            .showToast(
                                                    TableListFragmentActivity.this,
                                                    getString(R.string.remote_server_error));
                                }
                            }
                        }

                        @Override
                        public void handleFailure() {
                            if (isShow) {
                                CommonUtils.showToast(
                                        TableListFragmentActivity.this,
                                        getString(R.string.remote_server_error));
                            }
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(
                    TableListFragmentActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void parseSplitTableList(SoapObject soapObject) {
        try {
            if (null != tableList) {
                tableList.clear();
            }
            SoapObject mSoapObject = null;
            TableDto bean = null;
            for (int i = 0; i < soapObject.getPropertyCount(); i++) {
                mSoapObject = (SoapObject) soapObject.getProperty(i);
                if (null != mSoapObject) {
                    bean = new TableDto();
                    if (null != mSoapObject.getProperty("BackgroundColor")) {
                        bean.setBackgroundColor(String.valueOf(mSoapObject
                                .getProperty("BackgroundColor")));
                    }
                    if (null != mSoapObject.getProperty("CashierPrinterName")) {
                        bean.setCashierPrinterName(String.valueOf(mSoapObject
                                .getProperty("CashierPrinterName")));
                    }
                    if (null != mSoapObject.getProperty("CheckinDatetime")) {
                        bean.setCheckinDatetime(String.valueOf(mSoapObject
                                .getProperty("CheckinDatetime")));
                    }
                    if (null != mSoapObject.getProperty("CusCount")) {
                        bean.setCusCount(String.valueOf(mSoapObject
                                .getProperty("CusCount")));
                    }
                    if (null != mSoapObject.getProperty("Description")) {
                        bean.setDescription(String.valueOf(mSoapObject
                                .getProperty("Description")));
                    }
                    if (null != mSoapObject.getProperty("DescriptionAlt")) {
                        bean.setDescriptionAlt(String.valueOf(mSoapObject
                                .getProperty("DescriptionAlt")));
                    }
                    if (null != mSoapObject.getProperty("DisplayIndex")) {
                        bean.setDisplayIndex(String.valueOf(mSoapObject
                                .getProperty("DisplayIndex")));
                    }
                    if (null != mSoapObject.getProperty("IsAppearOnFloorPlan")) {
                        bean.setIsAppearOnFloorPlan(String.valueOf(mSoapObject
                                .getProperty("IsAppearOnFloorPlan")));
                    }
                    if (null != mSoapObject.getProperty("IsBypassServiceCharge")) {
                        bean.setIsBypassServiceCharge(String.valueOf(mSoapObject
                                .getProperty("IsBypassServiceCharge")));
                    }
                    if (null != mSoapObject.getProperty("IsMinChargeEnabled")) {
                        bean.setIsMinChargeEnabled(String.valueOf(mSoapObject
                                .getProperty("IsMinChargeEnabled")));
                    }
                    if (null != mSoapObject.getProperty("IsMinChargePerHead")) {
                        bean.setIsMinChargePerHead(String.valueOf(mSoapObject
                                .getProperty("IsMinChargePerHead")));
                    }
                    if (null != mSoapObject.getProperty("IsTakeAway")) {
                        bean.setIsTakeAway(String.valueOf(mSoapObject
                                .getProperty("IsTakeAway")));
                    }
                    if (null != mSoapObject.getProperty("IsTempTable")) {
                        bean.setIsTempTable(String.valueOf(mSoapObject
                                .getProperty("IsTempTable")));
                    }
                    if (null != mSoapObject.getProperty("IsTimeLimited")) {
                        bean.setIsTempTable(String.valueOf(mSoapObject
                                .getProperty("IsTimeLimited")));
                    }
                    if (null != mSoapObject.getProperty("IsVisible")) {
                        bean.setIsVisible(String.valueOf(mSoapObject
                                .getProperty("IsVisible")));
                    }
                    if (null != mSoapObject.getProperty("MinChargeAmount")) {
                        bean.setMinChargeAmount(String.valueOf(mSoapObject
                                .getProperty("MinChargeAmount")));
                    }
                    if (null != mSoapObject.getProperty("MinChargeMemberAmount")) {
                        bean.setMinChargeMemberAmount(String.valueOf(mSoapObject
                                .getProperty("MinChargeMemberAmount")));
                    }
                    if (null != mSoapObject.getProperty("ModifiedBy")) {
                        bean.setModifiedBy(String.valueOf(mSoapObject
                                .getProperty("ModifiedBy")));
                    }
                    if (null != mSoapObject.getProperty("ModifiedDate")) {
                        bean.setModifiedDate(String.valueOf(mSoapObject
                                .getProperty("ModifiedDate")));
                    }
                    if (null != mSoapObject.getProperty("OpenedChildTableCount")) {
                        bean.setOpenedChildTableCount(String.valueOf(mSoapObject
                                .getProperty("OpenedChildTableCount")));
                    }
                    if (null != mSoapObject.getProperty("ParentTableId")) {
                        bean.setParentTableId(String.valueOf(mSoapObject
                                .getProperty("ParentTableId")));
                    }
                    if (null != mSoapObject.getProperty("PosCode")) {
                        bean.setPosCode(String.valueOf(mSoapObject
                                .getProperty("PosCode")));
                    }
                    if (null != mSoapObject.getProperty("PositionX")) {
                        bean.setPositionX(String.valueOf(mSoapObject
                                .getProperty("PositionX")));
                    }
                    if (null != mSoapObject.getProperty("PositionY")) {
                        bean.setPositionY(String.valueOf(mSoapObject
                                .getProperty("PositionY")));
                    }
                    if (null != mSoapObject.getProperty("ResourceStyleName")) {
                        bean.setResourceStyleName(String.valueOf(mSoapObject
                                .getProperty("ResourceStyleName")));
                    }
                    if (null != mSoapObject.getProperty("SectionId")) {
                        bean.setSectionId(String.valueOf(mSoapObject
                                .getProperty("SectionId")));
                    }
                    if (null != mSoapObject.getProperty("ShopId")) {
                        bean.setShopId(String.valueOf(mSoapObject
                                .getProperty("ShopId")));
                    }
                    if (null != mSoapObject.getProperty("ShowPosCode")) {
                        bean.setShowPosCode(String.valueOf(mSoapObject
                                .getProperty("ShowPosCode")));
                    }
                    if (null != mSoapObject.getProperty("TableCode")) {
                        bean.setTableCode(String.valueOf(mSoapObject
                                .getProperty("TableCode")));
                    }
                    if (null != mSoapObject.getProperty("TableIconTypeId")) {
                        bean.setTableIconTypeId(String.valueOf(mSoapObject
                                .getProperty("TableIconTypeId")));
                    }
                    if (null != mSoapObject.getProperty("TableId")) {
                        bean.setTableId(String.valueOf(mSoapObject
                                .getProperty("TableId")));
                    }
                    if (null != mSoapObject.getProperty("TableStatusId")) {
                        bean.setTableStatusId(String.valueOf(mSoapObject
                                .getProperty("TableStatusId")));
                    }
                    if (null != mSoapObject.getProperty("TableStatusName")) {
                        bean.setTableStatusName(String.valueOf(mSoapObject
                                .getProperty("TableStatusName")));
                    }
                    if (null != mSoapObject.getProperty("TableTypeId")) {
                        bean.setTableTypeId(String.valueOf(mSoapObject
                                .getProperty("TableTypeId")));
                    }
                    if (null != mSoapObject.getProperty("TimeLimitedMinutes")) {
                        bean.setTimeLimitedMinutes(String.valueOf(mSoapObject
                                .getProperty("TimeLimitedMinutes")));
                    }
                    tableList.add(bean);
                    bean = null;
                }
            }
            if (null != tableList && tableList.size() > 0) {
                showPopupWindow(tabView);
                splitAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            CommonUtils.showToast(
                    TableListFragmentActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	@Override
	protected void onRestart() {
		super.onRestart();
        try {
            getData(true);
        } catch (Exception e) {
            CommonUtils.showToast(
                    TableListFragmentActivity.this,
                    getString(R.string.remote_server_error));
        }
    }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (View.VISIBLE == tvShadow.getVisibility()) {
				cancelSplit();
				return true;
			} else {
				CommonUtils.exitSystem(this);
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void cancelSplit() {
        try {
            tvShadow.setVisibility(View.GONE);
            btnLogout.setEnabled(true);
            btnSplit.setEnabled(true);
            ConstantUtils.isSplit = false;
        } catch (Exception e) {
            CommonUtils.showToast(
                    TableListFragmentActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	@Override
	public void onArticleSelectedListener(String accountId, String shopId, String tableCode,
			String tableStatus, String tableId, TableDto tableDto) {

        try {
            mTableId = tableId;
            isClear = false;

            if (TextUtils.isEmpty(shopId) || TextUtils.isEmpty(tableCode)) {
                return;
            }

            if (!TextUtils.isEmpty(tableStatus) && "isSplit".equals(tableStatus)) {
                getSplitTableList(true, accountId, shopId, tableCode);
            } else {
                if (TextUtils.isEmpty(tableStatus)) {
                    return;
                }

                if ("1".equals(tableStatus) || "4".equals(tableStatus)
                        || "5".equals(tableStatus)) {
                    ConstantUtils.mTableDto = tableDto;
                    CommonUtils.changeActivity(this, FoodCategoryActivity.class);
                    return;
                }

                if ("3".equals(tableStatus)) {
                    showPopupWindow(proView);
                    tvPrompt.setText(getString(R.string.locked));
                    return;
                }

                if (!TextUtils.isEmpty(tableId) && "7".equals(tableStatus)) {
                    isClear = true;
                    showPopupWindow(proView);
                    tvTitle.setText(String.format(getString(R.string.table_code),
                            tableCode));
                    tvPrompt.setText(getString(R.string.isClear));
                    return;
                }
            }
        } catch (Exception e) {
            CommonUtils.showToast(
                    TableListFragmentActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	@Override
	public void onArticleClearListener(boolean isClear) {

	}

	private void clearTable(final boolean isShow) {
        try {
            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.CLEAN_TABLE_DONE);

            AttributeInfo tem = new AttributeInfo();
            tem.setName("xmlns:tem");
            tem.setValue(SoapUtils.TARGET_NAMESPACE);
            mSoapObject.addAttribute(tem);

            AttributeInfo pos = new AttributeInfo();
            pos.setName("xmlns:pos");
            pos.setValue(SoapUtils.POS_NAMESPACE);
            mSoapObject.addAttribute(pos);

            mSoapObject.addProperty("tem:tableId", mTableId);
            mSoapObject.addSoapObject(getLoginUserSoapObject());

            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.CLEAN_TABLE_DONE, new HandlerCallBack() {

                        @Override
                        public void handleFinish(HashMap<String, Object> result) {
                            try {
                                if (HandleHttpRequestResult.isError(
                                        TableListFragmentActivity.this, isShow,
                                        SoapUtils.GET_AVAILABLE_TABLE_SECTION_LIST,
                                        result)) {
                                    return;
                                }
                                SoapObject object = (SoapObject) result
                                        .get(HttpHandler.RESULT);
                                String isOk = String.valueOf(object
                                        .getProperty(String.format(
                                                SoapUtils.REQUEST_METHOD_RESULT,
                                                SoapUtils.CLEAN_TABLE_DONE)));
                                if (!TextUtils.isEmpty(isOk)
                                        && ConstantUtils.IS_OK.equals(isOk)) {
                                    mListener.onArticleClearListener(isClear);
                                }
                            } catch (Exception e) {
                                CommonUtils.showToast(
                                        TableListFragmentActivity.this,
                                        getString(R.string.remote_server_error));
                            }
                        }

                        @Override
                        public void handleFailure() {
                            CommonUtils.showToast(TableListFragmentActivity.this,
                                    getString(R.string.remote_server_error));
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(
                    TableListFragmentActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private SoapObject getLoginUserSoapObject() {
		SoapObject object = new SoapObject(null, "tem:loginUser");

        try {
            object.addProperty("pos:AccountId",
                    ConstantUtils.userInfo.getAccountId());
            object.addProperty("pos:CardNo", ConstantUtils.userInfo.getCardNo());
            object.addProperty("pos:CreatedBy",
                    ConstantUtils.userInfo.getCreatedBy());
            object.addProperty("pos:CreatedDate",
                    ConstantUtils.userInfo.getCreatedDate());

            object.addProperty("pos:EffectiveDateFrom",
                    ConstantUtils.userInfo.getEffectiveDateFrom());
            object.addProperty("pos:EffectiveDateTo",
                    ConstantUtils.userInfo.getEffectiveDateTo());

            object.addProperty("pos:EnableCardNoLogin",
                    ConstantUtils.userInfo.getEnableCardNoLogin());
            object.addProperty("pos:EnableStaffCodeLogin",
                    ConstantUtils.userInfo.getEnableStaffCodeLogin());
            object.addProperty("pos:EnableUserIdLogin",
                    ConstantUtils.userInfo.getEnableUserIdLogin());
            object.addProperty("pos:Enabled", ConstantUtils.userInfo.getEnabled());
            object.addProperty("pos:ModifiedBy",
                    ConstantUtils.userInfo.getModifiedBy());
            object.addProperty("pos:ModifiedDate",
                    ConstantUtils.userInfo.getModifiedDate());
            object.addProperty("pos:ShopId", ConstantUtils.userInfo.getShopId());
            object.addProperty("pos:StaffCode",
                    ConstantUtils.userInfo.getStaffCode());
            object.addProperty("pos:UserAltName",
                    ConstantUtils.userInfo.getUserAltName());
            object.addProperty("pos:UserId", ConstantUtils.userInfo.getUserId());
            object.addProperty("pos:UserName", ConstantUtils.userInfo.getUserName());
        } catch (Exception e) {
            CommonUtils.showToast(
                    TableListFragmentActivity.this,
                    getString(R.string.remote_server_error));
        }

        return object;
	}
}
