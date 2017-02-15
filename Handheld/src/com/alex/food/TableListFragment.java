package com.alex.food;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.AttributeInfo;
import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.alex.food.adapter.GridViewAdapter;
import com.alex.food.bean.TableDto;
import com.alex.food.http.AsyncHttpRequest;
import com.alex.food.http.HandlerCallBack;
import com.alex.food.utils.CommonUtils;
import com.alex.food.utils.ConstantUtils;
import com.alex.food.utils.HandleHttpRequestResult;
import com.alex.food.utils.OnArticleSelectedListener;
import com.alex.food.utils.SoapUtils;

/**
 * 
 * @author ALEX
 *
 */
public class TableListFragment extends Fragment implements
		OnArticleSelectedListener {
	private static final String TAG = "com.alex.food.TableListFragment";
	private GridView gridView = null;
	private GridViewAdapter adapter = null;
	private List<TableDto> tableList = new ArrayList<TableDto>();
	private String mSectionId = "";
	private OnArticleSelectedListener mListener = null;
    private Handler updateTableListHandler = null;
    private String lastTableUpdateDatetimeString = null;
    private Runnable updateTableListRunnable = new Runnable()
    {
        private long time = 0;

        @Override
        public void run()
        {
            // do stuff then
            // can call updateTableListHandler again after work!
            time += 3000;
            //Log.d("TimerExample", "Going for... " + time);
            updateTableListHandler.postDelayed(this, 3000);
            getUpdatedTableList(false, mSectionId);
        }
    };

	static TableListFragment newInstance(String sectionId) {
		TableListFragment fragment = new TableListFragment();
		Bundle bundle = new Bundle();
		bundle.putString("sectionId", sectionId);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getArguments();
		mSectionId = bundle.getString("sectionId");
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnArticleSelectedListener) activity;
		} catch (Exception e) {
			CommonUtils.printLog(TAG, "onAttach", e.getMessage());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tablelist_gridview, container,
				false);
		gridView = (GridView) view.findViewById(R.id.gridView);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initViews();
	}

    @Override
    public  void onStop() {
        super.onStop();
        try {
            updateTableListHandler.removeCallbacks(updateTableListRunnable);
        } catch (Exception e) {
            CommonUtils.printLog(TAG, "onStop", e.getMessage());
        }
    }

    private void initViews() {

        try {
            if (CommonUtils.networkIsAvaiable(getActivity())) {
                getTableList(false, mSectionId);

// also setup the timer for getting the update table list
initTableUpdateTimer();

            } else {
                CommonUtils.showToast(getActivity(),
                        getActivity().getString(R.string.network_error));
            }
        } catch (Exception e) {
            CommonUtils.showToast(
                    getActivity(),
                    getActivity().getString(
                            R.string.remote_server_error));
        }
    }
    private void initTableUpdateTimer() {

        try {
            if(null == updateTableListHandler)
            {
                updateTableListHandler = new Handler();
                updateTableListHandler.postDelayed(updateTableListRunnable, 3000); // 1 second delay (takes millis)
            } else {
                updateTableListHandler = null;
                updateTableListHandler = new Handler();
                updateTableListHandler.postDelayed(updateTableListRunnable, 3000);
            }
        } catch (Exception e) {
            CommonUtils.showToast(
                    getActivity(),
                    getActivity().getString(
                            R.string.remote_server_error));
        }

    }

	private void getTableList(final boolean isShow, String sectionId) {
        try {
            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.GET_AVAILABLE_TABLE_LIST);

            AttributeInfo tem = new AttributeInfo();
            tem.setName("xmlns:tem");
            tem.setValue(SoapUtils.TARGET_NAMESPACE);
            mSoapObject.addAttribute(tem);

            AttributeInfo arr = new AttributeInfo();
            arr.setName("xmlns:arr");
            arr.setValue(SoapUtils.ARRAYS_NAMESPACE);
            mSoapObject.addAttribute(arr);

            AttributeInfo pos = new AttributeInfo();
            pos.setName("xmlns:pos");
            pos.setValue(SoapUtils.POS_NAMESPACE);
            mSoapObject.addAttribute(pos);

            if (!TextUtils.isEmpty(ConstantUtils.userInfo.getAccountId())) {
                mSoapObject.addProperty("tem:" + ConstantUtils.ACCOUNT_ID,
                        ConstantUtils.userInfo.getAccountId());
            }
            if (!TextUtils.isEmpty(ConstantUtils.userInfo.getShopId())) {
                mSoapObject.addProperty("tem:" + ConstantUtils.SHOP_ID,
                        ConstantUtils.userInfo.getShopId());
            }
            mSoapObject.addProperty("tem:sectionId", sectionId);

            SoapObject pro = new SoapObject(null, "tem:tableTypeIdList");
            pro.addProperty("arr:int", 1);
            pro.addProperty("arr:int", 2);
            pro.addProperty("arr:int", 3);
            mSoapObject.addSoapObject(pro);

            mSoapObject.addSoapObject(getSoapObject());

            mSoapObject.addProperty("tem:IsAppearOnFloorPlan", null);

            new AsyncHttpRequest(getActivity(), isShow, mSoapObject,
                    SoapUtils.GET_AVAILABLE_TABLE_LIST, new HandlerCallBack() {

                        @Override
                        public void handleFinish(HashMap<String, Object> result) {
                            try {
                                if (HandleHttpRequestResult.isError(getActivity(),
                                        isShow, SoapUtils.GET_AVAILABLE_TABLE_LIST,
                                        result)) {
                                    return;
                                }
                                SoapObject object = HandleHttpRequestResult
                                        .getResultSoapObject(result);
                                if (null != object
                                        && 0 != object.getPropertyCount()) {
                                    parseTableListData(object);
                                } else {
                                    if (isShow) {
                                        CommonUtils.showToast(
                                                getActivity(),
                                                getActivity().getString(
                                                        R.string.no_data));
                                    }
                                }
                            } catch (Exception e) {
                                if (isShow) {
                                    CommonUtils.showToast(
                                            getActivity(),
                                            getActivity().getString(
                                                    R.string.remote_server_error));
                                }
                            }
                        }

                        @Override
                        public void handleFailure() {
                            if (isShow) {
                                CommonUtils.showToast(getActivity(), getActivity()
                                        .getString(R.string.remote_server_error));
                            }
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(
                    getActivity(),
                    getActivity().getString(
                            R.string.remote_server_error));
        }
    }

    private void getUpdatedTableList(final boolean isShow, String sectionId) {
        //final String requestSendDatetimeString = CommonUtils.getCurrentDateTime();

        try {
            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.GET_UPDATE_TABLE_LIST);

            AttributeInfo tem = new AttributeInfo();
            tem.setName("xmlns:tem");
            tem.setValue(SoapUtils.TARGET_NAMESPACE);
            mSoapObject.addAttribute(tem);

            AttributeInfo arr = new AttributeInfo();
            arr.setName("xmlns:arr");
            arr.setValue(SoapUtils.ARRAYS_NAMESPACE);
            mSoapObject.addAttribute(arr);

            AttributeInfo pos = new AttributeInfo();
            pos.setName("xmlns:pos");
            pos.setValue(SoapUtils.POS_NAMESPACE);
            mSoapObject.addAttribute(pos);

            if (!TextUtils.isEmpty(ConstantUtils.userInfo.getAccountId())) {
                mSoapObject.addProperty("tem:" + ConstantUtils.ACCOUNT_ID,
                        ConstantUtils.userInfo.getAccountId());
            }
            if (!TextUtils.isEmpty(ConstantUtils.userInfo.getShopId())) {
                mSoapObject.addProperty("tem:" + ConstantUtils.SHOP_ID,
                        ConstantUtils.userInfo.getShopId());
            }
            mSoapObject.addProperty("tem:sectionId", sectionId);


            SoapObject pro = new SoapObject(null, "tem:tableTypeIdList");
            pro.addProperty("arr:int", 1);
            pro.addProperty("arr:int", 2);
            pro.addProperty("arr:int", 3);
            mSoapObject.addSoapObject(pro);

            if(null != lastTableUpdateDatetimeString)
            {
                mSoapObject.addProperty("tem:lastUpdateTime", lastTableUpdateDatetimeString);
            }


            mSoapObject.addSoapObject(getSoapObject());

            mSoapObject.addProperty("tem:IsAppearOnFloorPlan", null);

            new AsyncHttpRequest(getActivity(), isShow, mSoapObject,
                    SoapUtils.GET_UPDATE_TABLE_LIST, new HandlerCallBack() {

                @Override
                public void handleFinish(HashMap<String, Object> result) {
                    try {
                        if (HandleHttpRequestResult.isError(getActivity(),
                                isShow, SoapUtils.GET_UPDATE_TABLE_LIST,
                                result)) {
                            return;
                        }
                        SoapObject object = HandleHttpRequestResult
                                .getResultSoapObject(result);
                        if (null != object
                                && 0 != object.getPropertyCount()) {
                            parseUpdatedTableListData(object);
                        } else {
                            if (isShow) {
                                CommonUtils.showToast(
                                        getActivity(),
                                        getActivity().getString(
                                                R.string.no_data));
                            }
                        }

                    } catch (Exception e) {
                        if (isShow) {
                            CommonUtils.showToast(
                                    getActivity(),
                                    getActivity().getString(
                                            R.string.remote_server_error));
                        }
                    }
                }

                @Override
                public void handleFailure() {
                    if (isShow) {
                        CommonUtils.showToast(getActivity(), getActivity()
                                .getString(R.string.remote_server_error));
                    }
                }
            }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(
                    getActivity(),
                    getActivity().getString(
                            R.string.remote_server_error));
        }
    }

	private SoapObject getSoapObject() {
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
                    getActivity(),
                    getActivity().getString(
                            R.string.remote_server_error));
        }

        return object;
	}

	private void parseTableListData(SoapObject soapObject) {
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

// everything is fine, so the requested data date time will
// be the list update datetime
if((null == lastTableUpdateDatetimeString) ||  (lastTableUpdateDatetimeString.compareTo(bean.getModifiedDate())) < 0)
{
lastTableUpdateDatetimeString = bean.getModifiedDate();
}
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
            if (null != tableList) {
                setAdapterData();
                // adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            CommonUtils.showToast(
                    getActivity(),
                    getActivity().getString(
                            R.string.remote_server_error));
        }
    }

    private void parseUpdatedTableListData(SoapObject soapObject) {
        try {
            SoapObject mSoapObject = null;
            Log.d("update table count", String.valueOf(soapObject.getPropertyCount()));
            for (int i = 0; i < soapObject.getPropertyCount(); i++) {
                mSoapObject = (SoapObject) soapObject.getProperty(i);
                if( null != mSoapObject) {
                    for (TableDto tableDtoObj : tableList) {
                        if (tableDtoObj.getTableId().equals(String.valueOf(mSoapObject
                                .getProperty("TableId")))) {

                            if (null != mSoapObject.getProperty("BackgroundColor")) {
                                tableDtoObj.setBackgroundColor(String.valueOf(mSoapObject
                                        .getProperty("BackgroundColor")));
                            }
                            if (null != mSoapObject.getProperty("CashierPrinterName")) {
                                tableDtoObj.setCashierPrinterName(String.valueOf(mSoapObject
                                        .getProperty("CashierPrinterName")));
                            }
                            if (null != mSoapObject.getProperty("CheckinDatetime")) {
                                tableDtoObj.setCheckinDatetime(String.valueOf(mSoapObject
                                        .getProperty("CheckinDatetime")));
                            }
                            if (null != mSoapObject.getProperty("CusCount")) {
                                tableDtoObj.setCusCount(String.valueOf(mSoapObject
                                        .getProperty("CusCount")));
                            }
                            if (null != mSoapObject.getProperty("Description")) {
                                tableDtoObj.setDescription(String.valueOf(mSoapObject
                                        .getProperty("Description")));
                            }
                            if (null != mSoapObject.getProperty("DescriptionAlt")) {
                                tableDtoObj.setDescriptionAlt(String.valueOf(mSoapObject
                                        .getProperty("DescriptionAlt")));
                            }
                            if (null != mSoapObject.getProperty("DisplayIndex")) {
                                tableDtoObj.setDisplayIndex(String.valueOf(mSoapObject
                                        .getProperty("DisplayIndex")));
                            }
                            if (null != mSoapObject.getProperty("IsAppearOnFloorPlan")) {
                                tableDtoObj.setIsAppearOnFloorPlan(String.valueOf(mSoapObject
                                        .getProperty("IsAppearOnFloorPlan")));
                            }
                            if (null != mSoapObject.getProperty("IsBypassServiceCharge")) {
                                tableDtoObj.setIsBypassServiceCharge(String.valueOf(mSoapObject
                                        .getProperty("IsBypassServiceCharge")));
                            }
                            if (null != mSoapObject.getProperty("IsMinChargeEnabled")) {
                                tableDtoObj.setIsMinChargeEnabled(String.valueOf(mSoapObject
                                        .getProperty("IsMinChargeEnabled")));
                            }
                            if (null != mSoapObject.getProperty("IsMinChargePerHead")) {
                                tableDtoObj.setIsMinChargePerHead(String.valueOf(mSoapObject
                                        .getProperty("IsMinChargePerHead")));
                            }
                            if (null != mSoapObject.getProperty("IsTakeAway")) {
                                tableDtoObj.setIsTakeAway(String.valueOf(mSoapObject
                                        .getProperty("IsTakeAway")));
                            }
                            if (null != mSoapObject.getProperty("IsTempTable")) {
                                tableDtoObj.setIsTempTable(String.valueOf(mSoapObject
                                        .getProperty("IsTempTable")));
                            }
                            if (null != mSoapObject.getProperty("IsTimeLimited")) {
                                tableDtoObj.setIsTempTable(String.valueOf(mSoapObject
                                        .getProperty("IsTimeLimited")));
                            }
                            if (null != mSoapObject.getProperty("IsVisible")) {
                                tableDtoObj.setIsVisible(String.valueOf(mSoapObject
                                        .getProperty("IsVisible")));
                            }
                            if (null != mSoapObject.getProperty("MinChargeAmount")) {
                                tableDtoObj.setMinChargeAmount(String.valueOf(mSoapObject
                                        .getProperty("MinChargeAmount")));
                            }
                            if (null != mSoapObject.getProperty("MinChargeMemberAmount")) {
                                tableDtoObj.setMinChargeMemberAmount(String.valueOf(mSoapObject
                                        .getProperty("MinChargeMemberAmount")));
                            }
                            if (null != mSoapObject.getProperty("ModifiedBy")) {
                                tableDtoObj.setModifiedBy(String.valueOf(mSoapObject
                                        .getProperty("ModifiedBy")));
                            }
                            if (null != mSoapObject.getProperty("ModifiedDate")) {
                                tableDtoObj.setModifiedDate(String.valueOf(mSoapObject
                                        .getProperty("ModifiedDate")));

                                // everything is fine, so the requested data date time will
                                // be the list update datetime
                                if((null == lastTableUpdateDatetimeString) ||  (lastTableUpdateDatetimeString.compareTo(tableDtoObj.getModifiedDate())) < 0)
                                {
                                    lastTableUpdateDatetimeString = tableDtoObj.getModifiedDate();
                                }
                            }
                            if (null != mSoapObject.getProperty("OpenedChildTableCount")) {
                                tableDtoObj.setOpenedChildTableCount(String.valueOf(mSoapObject
                                        .getProperty("OpenedChildTableCount")));
                            }
                            if (null != mSoapObject.getProperty("ParentTableId")) {
                                tableDtoObj.setParentTableId(String.valueOf(mSoapObject
                                        .getProperty("ParentTableId")));
                            }
                            if (null != mSoapObject.getProperty("PosCode")) {
                                tableDtoObj.setPosCode(String.valueOf(mSoapObject
                                        .getProperty("PosCode")));
                            }
                            if (null != mSoapObject.getProperty("PositionX")) {
                                tableDtoObj.setPositionX(String.valueOf(mSoapObject
                                        .getProperty("PositionX")));
                            }
                            if (null != mSoapObject.getProperty("PositionY")) {
                                tableDtoObj.setPositionY(String.valueOf(mSoapObject
                                        .getProperty("PositionY")));
                            }
                            if (null != mSoapObject.getProperty("ResourceStyleName")) {
                                tableDtoObj.setResourceStyleName(String.valueOf(mSoapObject
                                        .getProperty("ResourceStyleName")));
                            }
                            if (null != mSoapObject.getProperty("SectionId")) {
                                tableDtoObj.setSectionId(String.valueOf(mSoapObject
                                        .getProperty("SectionId")));
                            }
                            if (null != mSoapObject.getProperty("ShopId")) {
                                tableDtoObj.setShopId(String.valueOf(mSoapObject
                                        .getProperty("ShopId")));
                            }
                            if (null != mSoapObject.getProperty("ShowPosCode")) {
                                tableDtoObj.setShowPosCode(String.valueOf(mSoapObject
                                        .getProperty("ShowPosCode")));
                            }
                            if (null != mSoapObject.getProperty("TableCode")) {
                                tableDtoObj.setTableCode(String.valueOf(mSoapObject
                                        .getProperty("TableCode")));
                            }
                            if (null != mSoapObject.getProperty("TableIconTypeId")) {
                                tableDtoObj.setTableIconTypeId(String.valueOf(mSoapObject
                                        .getProperty("TableIconTypeId")));
                            }
                            if (null != mSoapObject.getProperty("TableId")) {
                                tableDtoObj.setTableId(String.valueOf(mSoapObject
                                        .getProperty("TableId")));
                            }
                            if (null != mSoapObject.getProperty("TableStatusId")) {
                                tableDtoObj.setTableStatusId(String.valueOf(mSoapObject
                                        .getProperty("TableStatusId")));
                            }
                            if (null != mSoapObject.getProperty("TableStatusName")) {
                                tableDtoObj.setTableStatusName(String.valueOf(mSoapObject
                                        .getProperty("TableStatusName")));
                            }
                            if (null != mSoapObject.getProperty("TableTypeId")) {
                                tableDtoObj.setTableTypeId(String.valueOf(mSoapObject
                                        .getProperty("TableTypeId")));
                            }
                            if (null != mSoapObject.getProperty("TimeLimitedMinutes")) {
                                tableDtoObj.setTimeLimitedMinutes(String.valueOf(mSoapObject
                                        .getProperty("TimeLimitedMinutes")));
                            }

                            break;
                        }
                    }
                }

            }
            if (null != tableList) {
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            CommonUtils.showToast(
                    getActivity(),
                    getActivity().getString(
                            R.string.remote_server_error));
        }
    }

	private void setAdapterData() {
        try {
            adapter = new GridViewAdapter(getActivity(), tableList);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    if (ConstantUtils.isSplit) {
                        mListener.onArticleSelectedListener(tableList.get(arg2)
                                        .getAccountId(), tableList.get(arg2).getShopId(),
                                tableList.get(arg2).getTableCode(), "isSplit",
                                tableList.get(arg2).getTableId(), tableList.get(arg2));
                    } else {
                        mListener.onArticleSelectedListener(tableList.get(arg2)
                                        .getAccountId(), tableList.get(arg2).getShopId(),
                                tableList.get(arg2).getTableCode(), tableList.get(arg2).getTableStatusId(),
                                tableList.get(arg2).getTableId(), tableList.get(arg2));
                    }
                }
            });
        } catch (Exception e) {
            CommonUtils.showToast(
                    getActivity(),
                    getActivity().getString(
                            R.string.remote_server_error));
        }
    }

	@Override
	public void onArticleSelectedListener(String accountId, String shopId, String tableCode,
			String tableStatus, String tableId, TableDto tableDto) {

	}

	@Override
	public void onArticleClearListener(boolean isClear) {
		if (isClear) {
			getTableList(true, mSectionId);
		}
	}
}
