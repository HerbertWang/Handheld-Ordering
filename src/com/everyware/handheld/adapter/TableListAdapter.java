package com.everyware.handheld.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.AttributeInfo;
import org.ksoap2.serialization.SoapObject;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.everyware.handheld.R;
import com.everyware.handheld.bean.TableDto;
import com.everyware.handheld.bean.TableSectionList;
import com.everyware.handheld.http.AsyncHttpRequest;
import com.everyware.handheld.http.HandlerCallBack;
import com.everyware.handheld.http.HttpHandler;
import com.everyware.handheld.utils.CommonUtils;
import com.everyware.handheld.utils.ConstantUtils;
import com.everyware.handheld.utils.HandleHttpRequestResult;
import com.everyware.handheld.utils.SoapUtils;

/**
 * 
 * @author ALEX
 *
 */
public class TableListAdapter extends PagerAdapter {
	private List<TableSectionList> list;
	private Context mContext;
	private ViewPagerAdapterCallBack callBack;
	private List<TableDto> mTableList = new ArrayList<TableDto>();
	private int mPosition;

	public TableListAdapter(Context context, List<TableSectionList> list,
			ViewPagerAdapterCallBack callBack) {
		this.list = list;
		mContext = context;
		this.callBack = callBack;
	}

	public interface ViewPagerAdapterCallBack {
		void adapterCallBack(int parentPosition, int position);
	}

	public void setPosition(int position) {
		mPosition = position;
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		GridView gridView = new GridView(mContext);
		gridView.setNumColumns(4);
		gridView.setSelector(mContext.getResources().getDrawable(
				R.drawable.selector_null));
		gridView.setCacheColorHint(Color.TRANSPARENT);
		GridViewAdapter adapter = new GridViewAdapter(mContext, mTableList);
		gridView.setAdapter(adapter);
		((ViewPager) container).addView(gridView);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int arg2,
					long id) {
				callBack.adapterCallBack(position, arg2);
			}
		});

		if (null != list && list.size() > 0) {
			getTableList(false, list.get(position).getSectionId(), adapter,
					mTableList);
		}

		return gridView;
	}

	private void getTableList(final boolean isShow, String sectionId,
			final GridViewAdapter adapter, final List<TableDto> tabeList) {
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
		mSoapObject.addSoapObject(pro);

		mSoapObject.addSoapObject(getSoapObject());

		mSoapObject.addProperty("tem:IsAppearOnFloorPlan", false);

		new AsyncHttpRequest(mContext, isShow, mSoapObject,
				SoapUtils.GET_AVAILABLE_TABLE_LIST, new HandlerCallBack() {

					@Override
					public void handleFinish(HashMap<String, Object> result) {
						try {
							if (HandleHttpRequestResult.isError(mContext, isShow,
									SoapUtils.GET_AVAILABLE_TABLE_LIST, result)) {
								return;
							}
							SoapObject object = (SoapObject) result
									.get(HttpHandler.RESULT);
							SoapObject soapObject = (SoapObject) object
									.getProperty(SoapUtils.RESULT_LIST);
							if (null != soapObject
									&& 0 != soapObject.getPropertyCount()) {
								parseTableListData(soapObject, adapter,
										tabeList);
							} else {
								if (isShow) {
									CommonUtils.showToast(mContext, mContext
                                            .getString(R.string.no_data));
								}
							}
						} catch (Exception e) {
							if (isShow) {
								CommonUtils.showToast(
										mContext,
										mContext.getString(R.string.remote_server_error));
							}
						}
					}

					@Override
					public void handleFailure() {
						if (isShow) {
							CommonUtils.showToast(mContext, mContext
									.getString(R.string.remote_server_error));
						}
					}
				}).execute();
	}

	private SoapObject getSoapObject() {
		SoapObject object = new SoapObject(null, "tem:loginUser");

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

		return object;
	}

	private void parseTableListData(SoapObject soapObject,
			GridViewAdapter adapter, List<TableDto> tableList) {
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
			adapter.setListData(tableList);
		}
	}
}
