package com.alex.food;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.AttributeInfo;
import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alex.food.adapter.CategoryAdapter;
import com.alex.food.adapter.OrderListAdapter;
import com.alex.food.bean.CategoryDto;
import com.alex.food.bean.ItemMasterDto;
import com.alex.food.bean.OrderListBean;
import com.alex.food.bean.TxSalesDetailDto;
import com.alex.food.http.AsyncHttpRequest;
import com.alex.food.http.HandlerCallBack;
import com.alex.food.http.HttpHandler;
import com.alex.food.utils.CommonUtils;
import com.alex.food.utils.ComponentsManager;
import com.alex.food.utils.ConstantUtils;
import com.alex.food.utils.FormatCommitSoapObject;
import com.alex.food.utils.GetCategoryDto;
import com.alex.food.utils.GetItemMasterDto;
import com.alex.food.utils.GetTxSalesDetailDto;
import com.alex.food.utils.GetTxSalesHeaderDto;
import com.alex.food.utils.HandleHttpRequestResult;
import com.alex.food.utils.HandlerOrderListCallBack;
import com.alex.food.utils.ItemMasterDtoToTxSalesDetailDto;
import com.alex.food.utils.OrderListUtils;
import com.alex.food.utils.SoapUtils;
import com.alex.food.view.Configure;
import com.alex.food.view.ScrollLayout.PageListener;

/**
 * 
 * @author ALEX
 * 
 */
public class FoodCategoryActivity extends Activity {
	private Button btnOverOperation = null;
	private Button btnBack = null;
	private GridView categoryGridView = null;
	private LinearLayout calculateLayout = null;
	private Button btnkeyPan = null;
	private Button btnSendOrder = null;
	private TextView tvTotal = null;
	private TextView tvTotalPrice = null;
	private boolean isShow = true;
	private LinearLayout linearlayoutKeyPan = null;
	private TextView tvNumber = null;
	private TextView tvSeven = null;
	private TextView tvEight = null;
	private TextView tvNine = null;
	private TextView tvGaiMa = null;
	private TextView tvFour = null;
	private TextView tvFive = null;
	private TextView tvSix = null;
	private TextView tvDelete = null;
	private TextView tvOne = null;
	private TextView tvTwo = null;
	private TextView tvThree = null;
	private TextView tvConfirm = null;
	private TextView tvZero = null;
	private TextView tvJiaoQi = null;
	private TextView tvQiCai = null;
	private List<CategoryDto> list = new ArrayList<CategoryDto>();
	//private List<List<CategoryDto>> lists = new ArrayList<List<CategoryDto>>();
	private List<HashMap<String, Object>> mapList = new ArrayList<HashMap<String, Object>>();
	private CategoryDto parentCategoryDto = new CategoryDto();
	private int pageSize = 20;
	private String parentCategoryId = "";
	private int level = 0;
	//private GridView gridView = null;
	private List<TxSalesDetailDto> salesList = new ArrayList<TxSalesDetailDto>();

	private ListView listView = null;
	private OrderListAdapter mOrderListAdapter = null;
	private final String JIAOQI = "jiaoqi";
	private final String QICAI = "qicai";
	private CategoryDto orderParentCategoryDto = new CategoryDto();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.food_category_fragment_activity);

            ComponentsManager.getComponentManager().pushComponent(this);

            initViews();
            initOrderList();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
	}

	private void initViews() {
        try {
            ConstantUtils.isEnabled = false;

            ConstantUtils.orderList = new ArrayList<OrderListBean>();

            btnOverOperation = (Button) findViewById(R.id.btnOverOperation);
            btnBack = (Button) findViewById(R.id.btnBack);
            categoryGridView = (GridView) findViewById(R.id.categoryGridView);
            calculateLayout = (LinearLayout) findViewById(R.id.calculateLayout);
            btnkeyPan = (Button) findViewById(R.id.btnkeyPan);
            btnSendOrder = (Button) findViewById(R.id.btnSendOrder);
            tvTotal = (TextView) findViewById(R.id.tvTotal);
            tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);

            linearlayoutKeyPan = (LinearLayout) findViewById(R.id.linearlayoutKeyPan);
            tvNumber = (TextView) findViewById(R.id.tvNumber);
            tvSeven = (TextView) findViewById(R.id.tvSeven);
            tvEight = (TextView) findViewById(R.id.tvEight);
            tvNine = (TextView) findViewById(R.id.tvNine);
            tvGaiMa = (TextView) findViewById(R.id.tvGaiMa);
            tvFour = (TextView) findViewById(R.id.tvFour);
            tvFive = (TextView) findViewById(R.id.tvFive);
            tvSix = (TextView) findViewById(R.id.tvSix);
            tvDelete = (TextView) findViewById(R.id.tvDelete);
            tvOne = (TextView) findViewById(R.id.tvOne);
            tvTwo = (TextView) findViewById(R.id.tvTwo);
            tvThree = (TextView) findViewById(R.id.tvThree);
            tvConfirm = (TextView) findViewById(R.id.tvConfirm);
            tvZero = (TextView) findViewById(R.id.tvZero);
            tvJiaoQi = (TextView) findViewById(R.id.tvJiaoQi);
            tvQiCai = (TextView) findViewById(R.id.tvQiCai);

            btnOverOperation.setOnClickListener(listener);
            btnBack.setOnClickListener(listener);
            btnkeyPan.setOnClickListener(listener);
            btnSendOrder.setOnClickListener(listener);

            linearlayoutKeyPan.setOnClickListener(listener);
            tvNumber.setOnClickListener(listener);
            tvSeven.setOnClickListener(listener);
            tvEight.setOnClickListener(listener);
            tvNine.setOnClickListener(listener);
            tvGaiMa.setOnClickListener(listener);
            tvFour.setOnClickListener(listener);
            tvFive.setOnClickListener(listener);
            tvSix.setOnClickListener(listener);
            tvDelete.setOnClickListener(listener);
            tvOne.setOnClickListener(listener);
            tvTwo.setOnClickListener(listener);
            tvThree.setOnClickListener(listener);
            tvConfirm.setOnClickListener(listener);
            tvZero.setOnClickListener(listener);
            tvJiaoQi.setOnClickListener(listener);
            tvQiCai.setOnClickListener(listener);

            ConstantUtils.originalTableStatusId = Integer.parseInt(ConstantUtils.mTableDto.getTableStatusId());
            setTableStatus(ConstantUtils.TABLE_STATUS_ORDER_LOCK);

            getData(true, parentCategoryId, "false");
            getCurrentTxSales(false);
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnOverOperation:
				onClickOverOperationButton();
				break;
			case R.id.btnBack:
				onClickBackButton();
				break;
			case R.id.btnkeyPan:
				if (isShow) {
					calculateLayout.setVisibility(View.VISIBLE);
					changeStatus(false);
				} else {
					calculateLayout.setVisibility(View.GONE);
					changeStatus(true);
				}
				break;
			case R.id.btnSendOrder:
				commitOrderList();
				break;
			case R.id.linearlayoutKeyPan:
				calculateLayout.setVisibility(View.GONE);
				changeStatus(true);
				break;
			case R.id.tvNumber:
				break;
			case R.id.tvSeven:
				setTextView("7");
				break;
			case R.id.tvEight:
				setTextView("8");
				break;
			case R.id.tvNine:
				setTextView("9");
				break;
			case R.id.tvGaiMa:
				gaiMa(true);
				setViewGone();
				break;
			case R.id.tvFour:
				setTextView("4");
				break;
			case R.id.tvFive:
				setTextView("5");
				break;
			case R.id.tvSix:
				setTextView("6");
				break;
			case R.id.tvDelete:
				deleteOrderListItem();
				setViewGone();
				break;
			case R.id.tvOne:
				setTextView("1");
				break;
			case R.id.tvTwo:
				setTextView("2");
				break;
			case R.id.tvThree:
				setTextView("3");
				break;
			case R.id.tvConfirm:
				alterOrderQty();
				setViewGone();
				break;
			case R.id.tvZero:
				setTextView("0");
				break;
			case R.id.tvJiaoQi:
				jiaoQi();
				setViewGone();
				break;
			case R.id.tvQiCai:
				qiCai();
				setViewGone();
				break;
			}
		}
	};

    private void onClickOverOperationButton() {
        try {
            // reset back the table status to the original one
            setTableStatus(ConstantUtils.originalTableStatusId);
            finish();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void onClickBackButton() {
        try {
            if (null == mapList || mapList.size() <= 0 || 1 == mapList.size()) {
                return;
            }
            if (null != list) {
                list.clear();
            }
            level--;
            mapList.remove(level);
            level--;
            parentCategoryId = (String) mapList.get(level).get("parentCatgoryId");
            parentCategoryDto = (CategoryDto) mapList.get(level).get(
                    "parentCategoryDto");
            list = (List<CategoryDto>) mapList.get(level).get("list");
            level++;
            //addViewToScrollLayout();
            setGridView();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
	}

	private void setTextView(String number) {
        try {
            String text = tvNumber.getText().toString().trim() + number;
            if (!TextUtils.isEmpty(text) && text.length() > 1) {
                String temp = text.substring(0, 1);
                if ("0".equals(temp)) {
                    text = text.substring(1, text.length());
                }
            }
            tvNumber.setText(text);
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
	}

	private void changeStatus(boolean status) {
        try {
            isShow = status;
            btnOverOperation.setEnabled(status);
            btnBack.setEnabled(status);
            //categoryGridView.setIsTouch(status);
            btnkeyPan.setEnabled(status);
            btnSendOrder.setEnabled(status);
            tvNumber.setText("");
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void setViewGone() {
        try {
            calculateLayout.setVisibility(View.GONE);
            changeStatus(true);
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

    private void setTableStatus(int tableStatusId) {
        try {
            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.SET_TABLE_STATUS_BY_TABLE_ID);

            AttributeInfo tem = new AttributeInfo();
            tem.setName("xmlns:tem");
            tem.setValue(SoapUtils.TARGET_NAMESPACE);
            mSoapObject.addAttribute(tem);

            AttributeInfo pos = new AttributeInfo();
            pos.setName("xmlns:pos");
            pos.setValue(SoapUtils.POS_NAMESPACE);
            mSoapObject.addAttribute(pos);

            mSoapObject.addProperty("tem:" + ConstantUtils.TABLE_ID,
                    ConstantUtils.mTableDto.getTableId());
            mSoapObject.addProperty("tem:" + ConstantUtils.TABLE_STATUS_ID, tableStatusId);
            mSoapObject.addProperty("tem:" + ConstantUtils.POS_CODE, ConstantUtils.POSCODE);
            mSoapObject.addSoapObject(FormatCommitSoapObject.getFormatUserInfo());


            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.SET_TABLE_STATUS_BY_TABLE_ID,
                    new HandlerCallBack() {

                        @Override
                        public void handleFinish(HashMap<String, Object> result) {
                            try {
                                if (HandleHttpRequestResult.isError(
                                        FoodCategoryActivity.this, isShow,
                                        SoapUtils.SET_TABLE_STATUS_BY_TABLE_ID,
                                        result)) {
                                    return;
                                }

                                SoapObject object = (SoapObject) result
                                        .get(HttpHandler.RESULT);

                            } catch (Exception e) {
                                CommonUtils.showToast(FoodCategoryActivity.this,
                                        getString(R.string.remote_server_error));
                            }
                        }

                        @Override
                        public void handleFailure() {
                            CommonUtils.showToast(FoodCategoryActivity.this,
                                    getString(R.string.remote_server_error));
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void getData(final boolean isShow, String parentCategoryId,
			String isSmartCategory) {
		SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
				SoapUtils.Get_AVAILABLE_ITEM_CATEGORY_LIST);

		mSoapObject.addProperty(ConstantUtils.ACCOUNT_ID,
				ConstantUtils.userInfo.getAccountId());
		mSoapObject.addProperty(ConstantUtils.SHOP_ID,
				ConstantUtils.userInfo.getShopId());
		if (!TextUtils.isEmpty(parentCategoryId)) {
			mSoapObject.addProperty("parentCategoryId", parentCategoryId);
		}
		mSoapObject.addProperty("isSmartCategory", isSmartCategory);

		new AsyncHttpRequest(this, isShow, mSoapObject,
				SoapUtils.Get_AVAILABLE_ITEM_CATEGORY_LIST,
				new HandlerCallBack() {

					@Override
					public void handleFinish(HashMap<String, Object> result) {
						try {
							if (HandleHttpRequestResult.isError(
									FoodCategoryActivity.this, isShow,
									SoapUtils.Get_AVAILABLE_ITEM_CATEGORY_LIST,
									result)) {
								return;
							}

							SoapObject object = (SoapObject) result
									.get(HttpHandler.RESULT);

							SoapObject parentSoapObject = (SoapObject) object
									.getProperty("parentItemCategoryDto");

							SoapObject categorySoapObject = (SoapObject) object
									.getProperty(SoapUtils.RESULT_LIST);

							if (null != parentSoapObject) {
								parentCategoryDto = GetCategoryDto
										.getCategoryDto(parentSoapObject);
							}

							if (null != categorySoapObject
									&& 0 != categorySoapObject
											.getPropertyCount()) {
								parseCategoryData(categorySoapObject);
							} else {
								CommonUtils.showToast(
										FoodCategoryActivity.this,
										getString(R.string.no_data));
							}
						} catch (Exception e) {
							CommonUtils.showToast(FoodCategoryActivity.this,
									getString(R.string.remote_server_error));
						}
					}

					@Override
					public void handleFailure() {
						CommonUtils.showToast(FoodCategoryActivity.this,
								getString(R.string.remote_server_error));
					}
				}).execute();
	}

	private void parseCategoryData(SoapObject soapObject) {
        try {
            if (null != list) {
                list = null;
                list = new ArrayList<CategoryDto>();
            }
            SoapObject mSoapObject = null;
            for (int i = 0; i < soapObject.getPropertyCount(); i++) {
                mSoapObject = (SoapObject) soapObject.getProperty(i);
                if (null != mSoapObject) {
                    list.add(GetCategoryDto.getCategoryDto(mSoapObject));
                }
            }

            //addViewToScrollLayout();
            setGridView();

            if (null != list && list.size() > 0) {
                List<CategoryDto> mList = new ArrayList<CategoryDto>();
                mList.addAll(list);
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("parentCatgoryId", parentCategoryId);
                map.put("parentCategoryDto", parentCategoryDto);
                map.put("list", mList);
                mapList.add(level, map);
                map = null;
                level++;
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/*private void addViewToScrollLayout() {
        try {
            if (null != list && list.size() > 0) {
                if (list.size() % pageSize == 0) {
                    Configure.curentPage = list.size() / pageSize;
                } else {
                    Configure.curentPage = list.size() / pageSize + 1;
                }
                if (null != lists) {
                    lists.clear();
                }
                for (int i = 0; i < Configure.curentPage; i++) {
                    lists.add(new ArrayList<CategoryDto>());
                }
                for (int i = 0; i < list.size(); i++) {
                    int page = 0;
                    page = i / pageSize;
                    lists.get(page).add(list.get(i));
                }
                if (null != categoryGridView) {
                    categoryGridView.removeAllViews();
                }
                for (int i = 0; i < Configure.curentPage; i++) {
                    categoryGridView.addView(getGridView(i));
                }
                categoryGridView.setPageListener(new PageListener() {

                    @Override
                    public void page(int page) {
                    }
                });
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }*/

	private GridView setGridView() {
		CategoryAdapter adapter = new CategoryAdapter(this, list);
		//gridView = new GridView(this);
        try {
            categoryGridView.setAdapter(adapter);
            //gridView.setNumColumns(4);
            //gridView.setTag(i);
            //gridView.setSelector(R.drawable.selector_null);
            //gridView.setCacheColorHint(Color.TRANSPARENT);
            /*gridView.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        return true;
                    }
                    return false;
                }
            });*/
            categoryGridView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                        int position, long arg3) {
                    if (!isShow) {
                        return;
                    }
                    //int index = (Integer) arg0.getTag();
                    //List<CategoryDto> temList = lists.get(index);
                    if (TextUtils.isEmpty(list.get(position).getIsTerminal())) {
                        return;
                    }
                    if ("false".equals(list.get(position).getIsTerminal())) {
                        parentCategoryId = list.get(position).getCategoryId();
                        getData(true, list.get(position).getCategoryId(),
                                parentCategoryDto.getIsSmartCategory());
                    } else if ("true".equals(list.get(position).getIsTerminal())) {
                        Intent intent = new Intent(FoodCategoryActivity.this,
                                FoodCatgoryItemActivity.class);
                        intent.putExtra(ConstantUtils.CATEGORY_ID,
                                list.get(position).getCategoryId());
                        intent.putExtra(ConstantUtils.IS_SMART_CATEGORY, list
                                .get(position).getIsSmartCategory());
                        startActivity(intent);
                    }
                }
            });
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
        return categoryGridView;
	}

	private void getCurrentTxSales(final boolean isShow) {
        try {
            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.GET_CURRENT_TX_SALEX);

            mSoapObject.addProperty(ConstantUtils.ACCOUNT_ID,
                    ConstantUtils.userInfo.getAccountId());
            mSoapObject.addProperty(ConstantUtils.SHOP_ID,
                    ConstantUtils.userInfo.getShopId());
            mSoapObject.addProperty(ConstantUtils.TABLE_ID,
                    ConstantUtils.mTableDto.getTableId());

            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.GET_CURRENT_TX_SALEX, new HandlerCallBack() {

                        @Override
                        public void handleFinish(HashMap<String, Object> result) {
                            if (HandleHttpRequestResult.isError(
                                    FoodCategoryActivity.this, isShow,
                                    SoapUtils.GET_CURRENT_TX_SALEX, result)) {
                                return;
                            }
                            SoapObject object = (SoapObject) result
                                    .get(HttpHandler.RESULT);

                            SoapObject headerSoapObject = (SoapObject) object
                                    .getProperty("txSalesHeaderDto");

                            SoapObject listSoapObject = (SoapObject) object
                                    .getProperty("txSalesDetailDtoList");
                            if (null != headerSoapObject) {
                                ConstantUtils.mTxSalesHearderDto = GetTxSalesHeaderDto
                                        .parseHeader(headerSoapObject);
                            }
                            if (null != listSoapObject) {
                                parseList(listSoapObject);
                            }
                        }

                        @Override
                        public void handleFailure() {
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void parseList(SoapObject object) {
        try {
            if (null != salesList) {
                salesList = null;
                salesList = new ArrayList<TxSalesDetailDto>();
            }
            SoapObject mSoapObject = null;
            for (int i = 0; i < object.getPropertyCount(); i++) {
                mSoapObject = (SoapObject) object.getProperty(i);
                if (null != mSoapObject) {
                    salesList.add(GetTxSalesDetailDto
                            .parseTxSalesDetailDto(mSoapObject));
                }
            }

            if (null != salesList && salesList.size() > 0) {
                ConstantUtils.isEnabled = true;

                OrderListBean bean = null;
                List<OrderListBean> modifierDto = null;
                OrderListBean modifierBean = null;
                List<OrderListBean> followSetDto = null;
                OrderListBean followSetBean = null;
                List<OrderListBean> followSetModifierDto = null;
                OrderListBean followSetModifierBean = null;
                int number = 0;
                for (int i = 0; i < salesList.size(); i++) {
                    // set the common value for later use
                    if (Integer.parseInt(salesList.get(i).getSeqNo()) > ConstantUtils.maxSeqNumber) {
                        ConstantUtils.maxSeqNumber = Integer.parseInt(salesList.get(i).getSeqNo());
                    }
                    if (Integer.parseInt(salesList.get(i).getItemSetRunningIndex()) > ConstantUtils.maxItemSetRunningIndex) {
                        ConstantUtils.maxItemSetRunningIndex = Integer.parseInt(salesList.get(i).getItemSetRunningIndex());
                    }
                    if (Integer.parseInt(salesList.get(i).getItemOrderRunningIndex()) > ConstantUtils.maxItemOrderRunningIndex) {
                        ConstantUtils.maxItemOrderRunningIndex = Integer.parseInt(salesList.get(i).getItemOrderRunningIndex());
                    }
                    if ("0".equals(salesList.get(i).getSubItemLevel())) {
                        if (null != bean) {
                            ConstantUtils.orderList.add(bean);
                        }
                        bean = new OrderListBean();
                        bean.setNumber(++number);
                        bean.setSubmitted(true);
                        bean.setDetailDto(salesList.get(i));
                    } else if ("1".equals(salesList.get(i).getSubItemLevel())) {
                        if (null == bean.getModifierDto()) {
                            modifierDto = new ArrayList<OrderListBean>();
                        }
                        modifierBean = new OrderListBean();
                        modifierBean.setSubmitted(true);
                        modifierBean.setDetailDto(salesList.get(i));
                        modifierDto.add(modifierBean);
                        if (null != bean) {
                            bean.setModifierDto(modifierDto);
                        }
                    } else if ("2".equals(salesList.get(i).getSubItemLevel())) {
                        if (null == bean.getFollowSetDto()) {
                            followSetDto = new ArrayList<OrderListBean>();
                        }
                        followSetBean = new OrderListBean();
                        followSetBean.setSubmitted(true);
                        followSetBean.setDetailDto(salesList.get(i));
                        followSetDto.add(followSetBean);
                        if (null != bean) {
                            bean.setFollowSetDto(followSetDto);
                        }
                    } else if ("3".equals(salesList.get(i).getSubItemLevel())) {
                        if (null == followSetBean.getModifierDto()) {
                            followSetModifierDto = new ArrayList<OrderListBean>();
                        }
                        followSetModifierBean = new OrderListBean();
                        followSetModifierBean.setSubmitted(true);
                        followSetModifierBean.setDetailDto(salesList.get(i));
                        followSetModifierDto.add(followSetModifierBean);
                        if (null != followSetBean) {
                            followSetBean.setModifierDto(followSetModifierDto);
                            followSetBean.setModifier(followSetModifierDto);
                            //followSetDto.add(followSetBean);
                            if (null != bean) {
                                bean.setFollowSetDto(followSetDto);
                            }

                        }
                    }
                }
                if (null != bean) {
                    ConstantUtils.orderList.add(bean);
                }
                if (null != salesList) {
                    salesList = null;
                }
                if (null != bean) {
                    bean = null;
                }
                if (null != modifierDto) {
                    modifierDto = null;
                }
                if (null != modifierBean) {
                    modifierBean = null;
                }
                if (null != followSetDto) {
                    followSetDto = null;
                }
                if (null != followSetBean) {
                    followSetBean = null;
                }
                if (null != followSetModifierDto) {
                    followSetModifierDto = null;
                }
                if (null != followSetModifierBean) {
                    followSetModifierBean = null;
                }
                mOrderListAdapter.notifyDataSetChanged();
                setTotalPrice();
            }
        } catch (NumberFormatException e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (View.VISIBLE == calculateLayout.getVisibility()) {
				calculateLayout.setVisibility(View.GONE);
				changeStatus(true);
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		onloadOrder();
	}

	private void onloadOrder() {
        // 20150423 - Michael - This case will not be happened
		/*if (ConstantUtils.isSubmit) {
			if (null != ConstantUtils.orderList) {
				ConstantUtils.isSubmit = false;
				ConstantUtils.orderList.clear();
				mOrderListAdapter.notifyDataSetChanged();
			}
			getCurrentTxSales(true);
		} else {
			OrderListUtils.setOrderListItemIsSelectFalse();
            mOrderListAdapter.notifyDataSetChanged();
		}*/
        OrderListUtils.setOrderListItemIsSelectFalse();
        mOrderListAdapter.notifyDataSetChanged();
		//setTotalPrice();
	}

	/**
	 * 初始化订单
	 */
	private void initOrderList() {
        try {
            listView = (ListView) findViewById(R.id.listView);
            mOrderListAdapter = new OrderListAdapter(this, ConstantUtils.orderList,
                    new HandlerOrderListCallBack() {

                        @Override
                        public void handlerCallBack(int parentPosition,
                                int position, int subPosition, String status) {
                            changeOrderListItem(parentPosition, position,
                                    subPosition, status);
                        }
                    });
            listView.setAdapter(mOrderListAdapter);
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void changeOrderListItem(int parentPosition, int position,
			int subPosition, String status) {
        try {
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                return;
            }
            if (OrderListAdapter.NORMAL.equals(status)) {
                changeOrderListNormalSelect(parentPosition);
            } else if (OrderListAdapter.MODIFIER.equals(status)) {
                changeOrderListModifierSelect(parentPosition, position);
            } else if (OrderListAdapter.FOLLOWSET.equals(status)) {
                changeOrderListFollowSetSelect(parentPosition, position);
            } else if (OrderListAdapter.FOLLOWSET_MODIFIER.equals(status)) {
                changeOrderListFollowSetModifierSelect(parentPosition, position,
                        subPosition);
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void changeOrderListNormalSelect(int position) {
        try {
            OrderListBean bean = null;
            for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                bean = ConstantUtils.orderList.get(i);
                if (null != bean) {
                    if (i != position) {
                        setOrderListSelect(bean, i, false);
                    } else {
                        if (bean.isSelect()) {
                            bean.setSelect(false);
                            ConstantUtils.orderList.set(i, bean);
                        } else {
                            setOrderListSelect(bean, i, true);
                        }
                    }
                    bean = null;
                }
            }

            mOrderListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void setOrderListSelect(OrderListBean bean, int position,
			boolean status) {
        try {
            List<OrderListBean> modifier = null;
            OrderListBean modifierBean = null;
            List<OrderListBean> followSet = null;
            OrderListBean followSetBean = null;
            List<OrderListBean> followSetModifier = null;
            OrderListBean followSetModifierBean = null;

            List<OrderListBean> modifierList = null;
            OrderListBean modifierListBean = null;
            List<OrderListBean> followSetList = null;
            OrderListBean followSetListBean = null;
            List<OrderListBean> followSetModifierList = null;
            OrderListBean followSetModifierListBean = null;

            bean.setSelect(status);
            if (null != bean.getDetailDto()) {
                modifierList = bean.getModifierDto();
                if (null != modifierList && modifierList.size() > 0) {
                    for (int j = 0; j < modifierList.size(); j++) {
                        modifierListBean = modifierList.get(j);
                        if (null != modifierListBean) {
                            modifierListBean.setSelect(false);
                            modifierList.set(j, modifierListBean);
                            modifierListBean = null;
                        }
                    }
                    bean.setModifierDto(modifierList);
                    modifierList = null;
                }
                followSetList = bean.getFollowSetDto();
                if (null != followSetList && followSetList.size() > 0) {
                    for (int j = 0; j < followSetList.size(); j++) {
                        followSetListBean = followSetList.get(j);
                        if (null != followSetListBean) {
                            followSetListBean.setSelect(false);
                            followSetModifierList = followSetListBean
                                    .getModifierDto();
                            if (null != followSetModifierList
                                    && followSetModifierList.size() > 0) {
                                for (int k = 0; k < followSetModifierList.size(); k++) {
                                    followSetModifierListBean = followSetModifierList
                                            .get(k);
                                    if (null != followSetModifierListBean) {
                                        followSetModifierListBean.setSelect(false);
                                        followSetModifierList.set(k,
                                                followSetModifierListBean);
                                        followSetModifierListBean = null;
                                    }
                                }
                                followSetListBean
                                        .setModifier(followSetModifierList);
                                followSetModifierList = null;
                            }
                            followSetList.set(j, followSetListBean);
                            followSetListBean = null;
                        }
                    }
                    bean.setFollowSet(followSetList);
                    followSetList = null;
                }
            } else {
                modifier = bean.getModifier();
                if (null != modifier && modifier.size() > 0) {
                    for (int j = 0; j < modifier.size(); j++) {
                        modifierBean = modifier.get(j);
                        if (null != modifierBean) {
                            modifierBean.setSelect(false);
                            modifier.set(j, modifierBean);
                            modifierBean = null;
                        }
                    }
                    bean.setModifier(modifier);
                    modifier = null;
                }
                followSet = bean.getFollowSet();
                if (null != followSet && followSet.size() > 0) {
                    for (int j = 0; j < followSet.size(); j++) {
                        followSetBean = followSet.get(j);
                        if (null != followSetBean) {
                            followSetBean.setSelect(false);
                            followSetModifier = followSetBean.getModifier();
                            if (null != followSetModifier
                                    && followSetModifier.size() > 0) {
                                for (int k = 0; k < followSetModifier.size(); k++) {
                                    followSetModifierBean = followSetModifier
                                            .get(k);
                                    if (null != followSetModifierBean) {
                                        followSetModifierBean.setSelect(false);
                                        followSetModifier.set(k,
                                                followSetModifierBean);
                                        followSetModifierBean = null;
                                    }
                                }
                                followSetBean.setModifier(followSetModifier);
                                followSetModifier = null;
                            }
                            followSet.set(j, followSetBean);
                            followSetBean = null;
                        }
                    }
                    bean.setFollowSet(followSet);
                    followSet = null;
                }
            }
            ConstantUtils.orderList.set(position, bean);
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void changeOrderListModifierSelect(int parentPosition, int position) {
        try {
            OrderListBean bean = null;
            List<OrderListBean> modifier = null;
            OrderListBean modifierBean = null;
            List<OrderListBean> followSet = null;
            OrderListBean followSetBean = null;
            List<OrderListBean> followSetModifier = null;
            OrderListBean followSetModifierBean = null;

            List<OrderListBean> modifierList = null;
            OrderListBean modifierListBean = null;
            List<OrderListBean> followSetList = null;
            OrderListBean followSetListBean = null;
            List<OrderListBean> followSetModifierList = null;
            OrderListBean followSetModifierListBean = null;
            for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                bean = ConstantUtils.orderList.get(i);
                if (null != bean) {
                    if (i != parentPosition) {
                        setOrderListSelect(bean, i, false);
                    } else {
                        bean.setSelect(false);
                        if (null != bean.getDetailDto()) {
                            modifierList = bean.getModifierDto();
                            if (null != modifierList && modifierList.size() > 0) {
                                for (int j = 0; j < modifierList.size(); j++) {
                                    modifierListBean = modifierList.get(j);
                                    if (null != modifierListBean) {
                                        if (j == position) {
                                            if (modifierListBean.isSelect()) {
                                                modifierListBean.setSelect(false);
                                            } else {
                                                modifierListBean.setSelect(true);
                                            }
                                        } else {
                                            modifierListBean.setSelect(false);
                                        }
                                        modifierList.set(j, modifierListBean);
                                        modifierListBean = null;
                                    }
                                }
                                bean.setModifierDto(modifierList);
                                modifierList = null;
                            }
                            followSetList = bean.getFollowSetDto();
                            if (null != followSetList && followSetList.size() > 0) {
                                for (int j = 0; j < followSetList.size(); j++) {
                                    followSetListBean = followSetList.get(j);
                                    if (null != followSetListBean) {
                                        followSetListBean.setSelect(false);
                                        followSetModifierList = followSetListBean
                                                .getModifierDto();
                                        if (null != followSetModifierList
                                                && followSetModifierList.size() > 0) {
                                            for (int k = 0; k < followSetModifierList
                                                    .size(); k++) {
                                                followSetModifierListBean = followSetModifierList
                                                        .get(k);
                                                if (null != followSetModifierListBean) {
                                                    followSetModifierListBean
                                                            .setSelect(false);
                                                    followSetModifierList
                                                            .set(k,
                                                                    followSetModifierListBean);
                                                    followSetModifierListBean = null;
                                                }
                                            }
                                            followSetListBean
                                                    .setModifier(followSetModifierList);
                                            followSetModifierList = null;
                                        }
                                        followSetList.set(j, followSetListBean);
                                        followSetListBean = null;
                                    }
                                }
                                bean.setFollowSet(followSetList);
                                followSetList = null;
                            }
                        } else {
                            modifier = bean.getModifier();
                            if (null != modifier && modifier.size() > 0) {
                                for (int j = 0; j < modifier.size(); j++) {
                                    modifierBean = modifier.get(j);
                                    if (null != modifierBean) {
                                        if (j == position) {
                                            if (modifierBean.isSelect()) {
                                                modifierBean.setSelect(false);
                                            } else {
                                                modifierBean.setSelect(true);
                                            }
                                        } else {
                                            modifierBean.setSelect(false);
                                        }
                                        modifier.set(j, modifierBean);
                                        modifierBean = null;
                                    }
                                }
                                bean.setModifier(modifier);
                                modifier = null;
                            }
                            followSet = bean.getFollowSet();
                            if (null != followSet && followSet.size() > 0) {
                                for (int j = 0; j < followSet.size(); j++) {
                                    followSetBean = followSet.get(j);
                                    if (null != followSetBean) {
                                        followSetBean.setSelect(false);
                                        followSetModifier = followSetBean
                                                .getModifier();
                                        if (null != followSetModifier
                                                && followSetModifier.size() > 0) {
                                            for (int k = 0; k < followSetModifier
                                                    .size(); k++) {
                                                followSetModifierBean = followSetModifier
                                                        .get(k);
                                                if (null != followSetModifierBean) {
                                                    followSetModifierBean
                                                            .setSelect(false);
                                                    followSetModifier.set(k,
                                                            followSetModifierBean);
                                                    followSetModifierBean = null;
                                                }
                                            }
                                            followSetBean
                                                    .setModifier(followSetModifier);
                                            followSetModifier = null;
                                        }
                                        followSet.set(j, followSetBean);
                                        followSetBean = null;
                                    }
                                }
                                bean.setFollowSet(followSet);
                                followSet = null;
                            }
                        }
                        ConstantUtils.orderList.set(i, bean);
                    }
                    bean = null;
                }
            }

            mOrderListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void changeOrderListFollowSetSelect(int parentPosition, int position) {
        try {
            OrderListBean bean = null;
            List<OrderListBean> modifier = null;
            OrderListBean modifierBean = null;
            List<OrderListBean> followSet = null;
            OrderListBean followSetBean = null;
            List<OrderListBean> followSetModifier = null;
            OrderListBean followSetModifierBean = null;

            List<OrderListBean> modifierList = null;
            OrderListBean modifierListBean = null;
            List<OrderListBean> followSetList = null;
            OrderListBean followSetListBean = null;
            List<OrderListBean> followSetModifierList = null;
            OrderListBean followSetModifierListBean = null;
            for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                bean = ConstantUtils.orderList.get(i);
                if (null != bean) {
                    if (i != parentPosition) {
                        setOrderListSelect(bean, i, false);
                    } else {
                        bean.setSelect(false);
                        if (null != bean.getDetailDto()) {
                            modifierList = bean.getModifierDto();
                            if (null != modifierList && modifierList.size() > 0) {
                                for (int j = 0; j < modifierList.size(); j++) {
                                    modifierListBean = modifierList.get(j);
                                    if (null != modifierListBean) {
                                        modifierListBean.setSelect(false);
                                        modifierList.set(j, modifierListBean);
                                        modifierListBean = null;
                                    }
                                }
                                bean.setModifierDto(modifierList);
                                modifierList = null;
                            }
                            followSetList = bean.getFollowSetDto();
                            if (null != followSetList && followSetList.size() > 0) {
                                for (int j = 0; j < followSetList.size(); j++) {
                                    followSetListBean = followSetList.get(j);
                                    if (null != followSetListBean) {
                                        if (j == position) {
                                            if (followSetListBean.isSelect()) {
                                                followSetListBean.setSelect(false);
                                            } else {
                                                followSetListBean.setSelect(true);
                                            }
                                        } else {
                                            followSetListBean.setSelect(false);
                                        }
                                        followSetModifierList = followSetListBean
                                                .getModifierDto();
                                        if (null != followSetModifierList
                                                && followSetModifierList.size() > 0) {
                                            for (int k = 0; k < followSetModifierList
                                                    .size(); k++) {
                                                followSetModifierListBean = followSetModifierList
                                                        .get(k);
                                                if (null != followSetModifierListBean) {
                                                    followSetModifierListBean
                                                            .setSelect(false);
                                                    followSetModifierList
                                                            .set(k,
                                                                    followSetModifierListBean);
                                                    followSetModifierListBean = null;
                                                }
                                            }
                                            followSetListBean
                                                    .setModifier(followSetModifierList);
                                            followSetModifierList = null;
                                        }
                                        followSetList.set(j, followSetListBean);
                                        followSetListBean = null;
                                    }
                                }
                                bean.setFollowSet(followSetList);
                                followSetList = null;
                            }
                        } else {
                            modifier = bean.getModifier();
                            if (null != modifier && modifier.size() > 0) {
                                for (int j = 0; j < modifier.size(); j++) {
                                    modifierBean = modifier.get(j);
                                    if (null != modifierBean) {
                                        modifierBean.setSelect(false);
                                        modifier.set(j, modifierBean);
                                        modifierBean = null;
                                    }
                                }
                                bean.setModifier(modifier);
                                modifier = null;
                            }
                            followSet = bean.getFollowSet();
                            if (null != followSet && followSet.size() > 0) {
                                for (int j = 0; j < followSet.size(); j++) {
                                    followSetBean = followSet.get(j);
                                    if (null != followSetBean) {
                                        if (j == position) {
                                            if (followSetBean.isSelect()) {
                                                followSetBean.setSelect(false);
                                            } else {
                                                followSetBean.setSelect(true);
                                            }
                                        } else {
                                            followSetBean.setSelect(false);
                                        }
                                        followSetModifier = followSetBean
                                                .getModifier();
                                        if (null != followSetModifier
                                                && followSetModifier.size() > 0) {
                                            for (int k = 0; k < followSetModifier
                                                    .size(); k++) {
                                                followSetModifierBean = followSetModifier
                                                        .get(k);
                                                if (null != followSetModifierBean) {
                                                    followSetModifierBean
                                                            .setSelect(false);
                                                    followSetModifier.set(k,
                                                            followSetModifierBean);
                                                    followSetModifierBean = null;
                                                }
                                            }
                                            followSetBean
                                                    .setModifier(followSetModifier);
                                            followSetModifier = null;
                                        }
                                        followSet.set(j, followSetBean);
                                        followSetBean = null;
                                    }
                                }
                                bean.setFollowSet(followSet);
                                followSet = null;
                            }
                        }
                        ConstantUtils.orderList.set(i, bean);
                    }
                    bean = null;
                }
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void changeOrderListFollowSetModifierSelect(int parentPosition,
			int position, int subPosition) {
        try {
            OrderListBean bean = null;
            List<OrderListBean> modifier = null;
            OrderListBean modifierBean = null;
            List<OrderListBean> followSet = null;
            OrderListBean followSetBean = null;
            List<OrderListBean> followSetModifier = null;
            OrderListBean followSetModifierBean = null;

            List<OrderListBean> modifierList = null;
            OrderListBean modifierListBean = null;
            List<OrderListBean> followSetList = null;
            OrderListBean followSetListBean = null;
            List<OrderListBean> followSetModifierList = null;
            OrderListBean followSetModifierListBean = null;
            for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                bean = ConstantUtils.orderList.get(i);
                if (null != bean) {
                    if (i != parentPosition) {
                        setOrderListSelect(bean, i, false);
                    } else {
                        bean.setSelect(false);
                        if (null != bean.getDetailDto()) {
                            modifierList = bean.getModifierDto();
                            if (null != modifierList && modifierList.size() > 0) {
                                for (int j = 0; j < modifierList.size(); j++) {
                                    modifierListBean = modifierList.get(j);
                                    if (null != modifierListBean) {
                                        modifierListBean.setSelect(false);
                                        modifierList.set(j, modifierListBean);
                                        modifierListBean = null;
                                    }
                                }
                                bean.setModifierDto(modifierList);
                                modifierList = null;
                            }
                            followSetList = bean.getFollowSetDto();
                            if (null != followSetList && followSetList.size() > 0) {
                                for (int j = 0; j < followSetList.size(); j++) {
                                    followSetListBean = followSetList.get(j);
                                    if (null != followSetListBean) {
                                        followSetListBean.setSelect(false);
                                        followSetModifierList = followSetListBean
                                                .getModifierDto();
                                        if (null != followSetModifierList
                                                && followSetModifierList.size() > 0) {
                                            for (int k = 0; k < followSetModifierList
                                                    .size(); k++) {
                                                followSetModifierListBean = followSetModifierList
                                                        .get(k);
                                                if (null != followSetModifierListBean) {
                                                    if (k == subPosition) {
                                                        if (followSetModifierListBean
                                                                .isSelect()) {
                                                            followSetModifierListBean
                                                                    .setSelect(false);
                                                        } else {
                                                            followSetModifierListBean
                                                                    .setSelect(true);
                                                        }
                                                    } else {
                                                        followSetModifierListBean
                                                                .setSelect(false);
                                                    }
                                                    followSetModifierList
                                                            .set(k,
                                                                    followSetModifierListBean);
                                                    followSetModifierListBean = null;
                                                }
                                            }
                                            followSetListBean
                                                    .setModifier(followSetModifierList);
                                            followSetModifierList = null;
                                        }
                                        followSetList.set(j, followSetListBean);
                                        followSetListBean = null;
                                    }
                                }
                                bean.setFollowSet(followSetList);
                                followSetList = null;
                            }
                        } else {
                            modifier = bean.getModifier();
                            if (null != modifier && modifier.size() > 0) {
                                for (int j = 0; j < modifier.size(); j++) {
                                    modifierBean = modifier.get(j);
                                    if (null != modifierBean) {
                                        modifierBean.setSelect(false);
                                        modifier.set(j, modifierBean);
                                        modifierBean = null;
                                    }
                                }
                                bean.setModifier(modifier);
                                modifier = null;
                            }
                            followSet = bean.getFollowSet();
                            if (null != followSet && followSet.size() > 0) {
                                for (int j = 0; j < followSet.size(); j++) {
                                    followSetBean = followSet.get(j);
                                    if (null != followSetBean) {
                                        followSetBean.setSelect(false);
                                        followSetModifier = followSetBean
                                                .getModifier();
                                        if (null != followSetModifier
                                                && followSetModifier.size() > 0) {
                                            for (int k = 0; k < followSetModifier
                                                    .size(); k++) {
                                                followSetModifierBean = followSetModifier
                                                        .get(k);
                                                if (null != followSetModifierBean) {
                                                    if (k == subPosition) {
                                                        if (followSetModifierBean
                                                                .isSelect()) {
                                                            followSetModifierBean
                                                                    .setSelect(false);
                                                        } else {
                                                            followSetModifierBean
                                                                    .setSelect(true);
                                                        }
                                                    } else {
                                                        followSetModifierBean
                                                                .setSelect(false);
                                                    }
                                                    followSetModifierBean
                                                            .setSelect(false);
                                                    followSetModifier.set(k,
                                                            followSetModifierBean);
                                                    followSetModifierBean = null;
                                                }
                                            }
                                            followSetBean
                                                    .setModifier(followSetModifier);
                                            followSetModifier = null;
                                        }
                                        followSet.set(j, followSetBean);
                                        followSetBean = null;
                                    }
                                }
                                bean.setFollowSet(followSet);
                                followSet = null;
                            }
                        }
                        ConstantUtils.orderList.set(i, bean);
                    }
                    bean = null;
                }
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 计算总价
	 */
	private void setTotalPrice() {
        try {
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                tvTotal.setText(String.format(getString(R.string.money_format),
                        "0.00"));
                tvTotalPrice.setText(String.format(
                        getString(R.string.money_format), "0.00"));
                return;
            }
            BigDecimal price = new BigDecimal("0.00");
            BigDecimal total = new BigDecimal("0.00");
            OrderListBean bean = null;
            ItemMasterDto dto = null;
            List<OrderListBean> modifier = null;
            OrderListBean modifierBean = null;
            ItemMasterDto modifierDto = null;
            List<OrderListBean> followSet = null;
            OrderListBean followSetBean = null;
            ItemMasterDto followSetDto = null;
            List<OrderListBean> followSetModifier = null;
            OrderListBean followSetModifierBean = null;
            ItemMasterDto followSetModifierDto = null;

            TxSalesDetailDto detailDto = null;
            List<OrderListBean> modifierList = null;
            OrderListBean modifierListBean = null;
            TxSalesDetailDto modifierDetailDto = null;
            List<OrderListBean> followSetList = null;
            OrderListBean followSetListBean = null;
            TxSalesDetailDto followSetDetailDto = null;
            List<OrderListBean> followSetModifierList = null;
            OrderListBean followSetModifierListBean = null;
            TxSalesDetailDto followSetModifierDetailDto = null;

            for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                bean = ConstantUtils.orderList.get(i);
                if (null != bean) {
                    detailDto = bean.getDetailDto();
                    if (null != detailDto
                            && !TextUtils.isEmpty(detailDto.getAmount())) {
                        price = new BigDecimal(detailDto.getAmount());
                        total = price.add(total);
                        detailDto = null;
                    }
                    modifierList = bean.getModifierDto();
                    if (null != modifierList && modifierList.size() > 0) {
                        for (int j = 0; j < modifierList.size(); j++) {
                            modifierListBean = modifierList.get(j);
                            if (null != modifierListBean) {
                                modifierDetailDto = modifierListBean.getDetailDto();
                                if (null != modifierDetailDto
                                        && !TextUtils.isEmpty(modifierDetailDto
                                                .getAmount())) {
                                    price = new BigDecimal(
                                            modifierDetailDto.getAmount());
                                    total = total.add(price);
                                    modifierDetailDto = null;
                                }
                                modifierListBean = null;
                            }
                        }
                        modifierList = null;
                    }
                    followSetList = bean.getFollowSetDto();
                    if (null != followSetList && followSetList.size() > 0) {
                        for (int j = 0; j < followSetList.size(); j++) {
                            followSetListBean = followSetList.get(j);
                            if (null != followSetListBean) {
                                followSetDetailDto = followSetListBean
                                        .getDetailDto();
                                if (null != followSetDetailDto
                                        && !TextUtils.isEmpty(followSetDetailDto
                                                .getAmount())) {
                                    price = new BigDecimal(
                                            followSetDetailDto.getAmount());
                                    total = total.add(price);
                                    followSetDetailDto = null;
                                }
                                followSetModifierList = followSetListBean
                                        .getModifierDto();
                                if (null != followSetModifierList
                                        && followSetModifierList.size() > 0) {
                                    for (int k = 0; k < followSetModifierList
                                            .size(); k++) {
                                        followSetModifierListBean = followSetModifierList
                                                .get(k);
                                        if (null != followSetModifierListBean) {
                                            followSetModifierDetailDto = followSetModifierListBean
                                                    .getDetailDto();
                                            if (null != followSetModifierDetailDto
                                                    && !TextUtils
                                                            .isEmpty(followSetModifierDetailDto
                                                                    .getAmount())) {
                                                price = new BigDecimal(
                                                        followSetModifierDetailDto
                                                                .getAmount());
                                                total = total.add(price);
                                                followSetModifierDetailDto = null;
                                            }
                                            followSetModifierListBean = null;
                                        }
                                    }
                                    followSetModifierList = null;
                                }
                            }
                        }
                        followSetList = null;
                    }

                    dto = bean.getDto();
                    if (null != dto) {
                        if (!TextUtils.isEmpty(dto.getItemPrice())
                                && !TextUtils.isEmpty(dto.getItemQty())) {
                            price = getTotalPrice(dto.getItemPrice(),
                                    dto.getItemQty());
                            total = price.add(total);
                        }
                        dto = null;
                    }
                    modifier = bean.getModifier();
                    if (null != modifier && modifier.size() > 0) {
                        for (int j = 0; j < modifier.size(); j++) {
                            modifierBean = modifier.get(j);
                            if (null != modifierBean) {
                                modifierDto = modifierBean.getDto();
                                if (null != modifierDto) {
                                    if (!TextUtils.isEmpty(modifierDto
                                            .getItemPrice())
                                            && !TextUtils.isEmpty(modifierDto
                                                    .getItemQty())) {
                                        price = getTotalPrice(
                                                modifierDto.getItemPrice(),
                                                modifierDto.getItemQty());
                                        total = total.add(price);
                                    }
                                    modifierDto = null;
                                }
                                modifierBean = null;
                            }
                        }
                        modifier = null;
                    }
                    followSet = bean.getFollowSet();
                    if (null != followSet && followSet.size() > 0) {
                        for (int j = 0; j < followSet.size(); j++) {
                            followSetBean = followSet.get(j);
                            if (null != followSetBean) {
                                followSetDto = followSetBean.getDto();
                                if (null != followSetDto) {
                                    if (!TextUtils.isEmpty(followSetDto
                                            .getItemPrice())
                                            && !TextUtils.isEmpty(followSetDto
                                                    .getItemQty())) {
                                        price = getTotalPrice(
                                                followSetDto.getItemPrice(),
                                                followSetDto.getItemQty());
                                        total = total.add(price);
                                    }
                                    followSetDto = null;
                                }
                                followSetModifier = followSetBean.getFollowSet();
                                if (null != followSetModifier
                                        && followSetModifier.size() > 0) {
                                    for (int k = 0; k < followSetModifier.size(); k++) {
                                        followSetModifierBean = followSetModifier
                                                .get(k);
                                        if (null != followSetModifierBean) {
                                            followSetModifierDto = followSetModifierBean
                                                    .getDto();
                                            if (null != followSetModifierDto) {
                                                if (!TextUtils
                                                        .isEmpty(followSetModifierDto
                                                                .getItemPrice())
                                                        && !TextUtils
                                                                .isEmpty(followSetModifierDto
                                                                        .getItemQty())) {
                                                    price = getTotalPrice(
                                                            followSetModifierDto
                                                                    .getItemPrice(),
                                                            followSetModifierDto
                                                                    .getItemQty());
                                                    total = total.add(price);
                                                }
                                                followSetModifierDto = null;
                                            }
                                            followSetModifierBean = null;
                                        }
                                    }
                                    followSetModifier = null;
                                }
                                followSetBean = null;
                            }
                        }
                        followSet = null;
                    }
                    bean = null;
                }
            }

            tvTotal.setText(String.format(getString(R.string.money_format),
                    total.toString()));
            tvTotalPrice.setText(String.format(getString(R.string.money_format),
                    total.toString()));
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 计算每个商品的价格
	 * 
	 * @param strPrice
	 *            单价
	 * @param strCount
	 *            数量
	 * @return
	 */
	private BigDecimal getTotalPrice(String strPrice, String strCount) {
        BigDecimal price = new BigDecimal(strPrice);
        BigDecimal count = new BigDecimal(strCount);
        BigDecimal total = price.multiply(count);
        return total;
    }

	/**
	 * 删除订单
	 */
	private void deleteOrderListItem() {
        try {
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                setTotalPrice();
                return;
            }
            OrderListBean bean = null;
            List<OrderListBean> modifier = null;
            OrderListBean modifierBean = null;
            List<OrderListBean> followSet = null;
            OrderListBean followSetBean = null;
            List<OrderListBean> followSetModifier = null;
            OrderListBean followSetModifierBean = null;

            List<OrderListBean> modifierList = null;
            OrderListBean modifierListBean = null;
            List<OrderListBean> followSetList = null;
            OrderListBean followSetListBean = null;
            List<OrderListBean> followSetModifierList = null;
            OrderListBean followSetModifierListBean = null;
            for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                bean = ConstantUtils.orderList.get(i);
                if (null != bean) {
                    if (bean.isSelect()) {
                        if (null != bean.getDetailDto()) {
                            CommonUtils.showToast(this,
                                    getString(R.string.submitted));
                        } else {
                            ConstantUtils.orderList.remove(i);
                            reloadOrderList();
                            setTotalPrice();
                        }
                        break;
                    } else {
                        if (null != bean.getDetailDto()) {
                            modifierList = bean.getModifierDto();
                            if (null != modifierList && modifierList.size() > 0) {
                                for (int j = 0; j < modifierList.size(); j++) {
                                    modifierListBean = modifierList.get(j);
                                    if (null != modifierListBean) {
                                        if (modifierListBean.isSelect()) {
                                            CommonUtils.showToast(this,
                                                    getString(R.string.submitted));
                                            break;
                                        }
                                        modifierListBean = null;
                                    }
                                }
                                modifierList = null;
                            }
                            followSetList = bean.getFollowSetDto();
                            if (null != followSetList && followSetList.size() > 0) {
                                for (int j = 0; j < followSetList.size(); j++) {
                                    followSetListBean = followSetList.get(j);
                                    if (null != followSetListBean) {
                                        if (followSetListBean.isSelect()) {
                                            CommonUtils.showToast(this,
                                                    getString(R.string.submitted));
                                            break;
                                        }
                                        followSetModifierList = followSetListBean
                                                .getModifierDto();
                                        if (null != followSetModifierList
                                                && followSetModifierList.size() > 0) {
                                            for (int k = 0; k < followSetModifierList
                                                    .size(); k++) {
                                                followSetModifierListBean = followSetModifierList
                                                        .get(k);
                                                if (null != followSetModifierListBean) {
                                                    if (followSetModifierListBean
                                                            .isSelect()) {
                                                        CommonUtils
                                                                .showToast(
                                                                        this,
                                                                        getString(R.string.submitted));
                                                        break;
                                                    }
                                                    followSetModifierListBean = null;
                                                }
                                            }
                                            followSetModifierList = null;
                                        }
                                        followSetListBean = null;
                                    }
                                }
                                followSetList = null;
                            }
                        } else {
                            modifier = bean.getModifier();
                            if (null != modifier && modifier.size() > 0) {
                                for (int j = 0; j < modifier.size(); j++) {
                                    modifierBean = modifier.get(j);
                                    if (null != modifierBean) {
                                        if (modifierBean.isSelect()) {
                                            modifier.remove(j);
                                            bean.setModifier(modifier);
                                            ConstantUtils.orderList.set(i, bean);
                                            mOrderListAdapter
                                                    .notifyDataSetChanged();
                                            setTotalPrice();
                                            break;
                                        }
                                        modifierBean = null;
                                    }
                                }
                                modifier = null;
                            }
                            followSet = bean.getFollowSet();
                            if (null != followSet && followSet.size() > 0) {
                                for (int j = 0; j < followSet.size(); j++) {
                                    followSetBean = followSet.get(j);
                                    if (null != followSetBean) {
                                        if (followSetBean.isSelect()) {
                                            followSet.remove(j);
                                            bean.setFollowSet(followSet);
                                            ConstantUtils.orderList.set(i, bean);
                                            mOrderListAdapter
                                                    .notifyDataSetChanged();
                                            setTotalPrice();
                                            break;
                                        }
                                        followSetModifier = followSetBean
                                                .getModifier();
                                        if (null != followSetModifier
                                                && followSetModifier.size() > 0) {
                                            for (int k = 0; k < followSetModifier
                                                    .size(); k++) {
                                                followSetModifierBean = followSetModifier
                                                        .get(k);
                                                if (null != followSetModifierBean) {
                                                    if (followSetModifierBean
                                                            .isSelect()) {
                                                        followSetModifier.remove(k);
                                                        followSetBean
                                                                .setModifier(followSetModifier);
                                                        followSet.set(j,
                                                                followSetBean);
                                                        bean.setFollowSet(followSet);
                                                        ConstantUtils.orderList
                                                                .set(i, bean);
                                                        mOrderListAdapter
                                                                .notifyDataSetChanged();
                                                        setTotalPrice();
                                                        break;
                                                    }
                                                    followSetModifierBean = null;
                                                }
                                            }
                                            followSetModifier = null;
                                        }
                                        followSetBean = null;
                                    }
                                }
                                followSet = null;
                            }
                        }
                    }
                    bean = null;
                }
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void reloadOrderList() {
        try {
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                mOrderListAdapter.notifyDataSetChanged();
                return;
            }
            for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                if (null != ConstantUtils.orderList.get(i)) {
                    ConstantUtils.orderList.get(i).setNumber(i + 1);
                }
            }
            mOrderListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void jiaoQi() {
        try {
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                return;
            }
            OrderListBean bean = null;
            List<OrderListBean> modifier = null;
            OrderListBean modifierBean = null;
            List<OrderListBean> followSet = null;
            OrderListBean followSetBean = null;
            List<OrderListBean> followSetModifier = null;
            OrderListBean followSetModifierBean = null;

            List<OrderListBean> modifierList = null;
            OrderListBean modifierListBean = null;
            List<OrderListBean> followSetList = null;
            OrderListBean followSetListBean = null;
            List<OrderListBean> followSetModifierList = null;
            OrderListBean followSetModifierListBean = null;
            for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                bean = ConstantUtils.orderList.get(i);
                if (null != bean) {
                    if (bean.isSelect()) {
                        if (null != bean.getDetailDto()) {
                            CommonUtils.showToast(this,
                                    getString(R.string.alreay_send_not_jiaoqi));
                        } else {
                            if (bean.isItemOnHold()) {
                                ConstantUtils.orderList.get(i).setItemOnHold(false);
                                ConstantUtils.orderList.get(i).setJiaoQi("");
                            } else {
                                ConstantUtils.orderList.get(i).setItemOnHold(true);
                                ConstantUtils.orderList.get(i).setJiaoQi(JIAOQI);
                            }
                            mOrderListAdapter.notifyDataSetChanged();
                        }
                        break;
                    } else {
                        if (null != bean.getDetailDto()) {
                            modifierList = bean.getModifierDto();
                            if (null != modifierList && modifierList.size() > 0) {
                                for (int j = 0; j < modifierList.size(); j++) {
                                    modifierListBean = modifierList.get(j);
                                    if (null != modifierListBean) {
                                        if (modifierListBean.isSelect()) {
                                            CommonUtils
                                                    .showToast(
                                                            this,
                                                            getString(R.string.alreay_send_not_jiaoqi));
                                            break;
                                        }
                                        modifierListBean = null;
                                    }
                                }
                                modifierList = null;
                            }
                            followSetList = bean.getFollowSetDto();
                            if (null != followSetList && followSetList.size() > 0) {
                                for (int j = 0; j < followSetList.size(); j++) {
                                    followSetListBean = followSetList.get(j);
                                    if (null != followSetListBean) {
                                        if (followSetListBean.isSelect()) {
                                            CommonUtils
                                                    .showToast(
                                                            this,
                                                            getString(R.string.alreay_send_not_jiaoqi));
                                            break;
                                        }
                                        followSetModifierList = followSetListBean
                                                .getModifierDto();
                                        if (null != followSetModifierList
                                                && followSetModifierList.size() > 0) {
                                            for (int k = 0; k < followSetModifierList
                                                    .size(); k++) {
                                                followSetModifierListBean = followSetModifierList
                                                        .get(k);
                                                if (null != followSetModifierListBean) {
                                                    if (followSetModifierListBean
                                                            .isSelect()) {
                                                        CommonUtils
                                                                .showToast(
                                                                        this,
                                                                        getString(R.string.alreay_send_not_jiaoqi));
                                                        break;
                                                    }
                                                    followSetModifierListBean = null;
                                                }
                                            }
                                            followSetModifierList = null;
                                        }
                                        followSetListBean = null;
                                    }
                                }
                                followSetList = null;
                            }
                        } else {
                            modifier = bean.getModifier();
                            if (null != modifier && modifier.size() > 0) {
                                for (int j = 0; j < modifier.size(); j++) {
                                    modifierBean = modifier.get(j);
                                    if (null != modifierBean) {
                                        if (modifierBean.isSelect()) {
                                            CommonUtils
                                                    .showToast(
                                                            this,
                                                            getString(R.string.gaima_no_jiaoqi));
                                            break;
                                        }
                                        modifierBean = null;
                                    }
                                }
                                modifier = null;
                            }
                            followSet = bean.getFollowSet();
                            if (null != followSet && followSet.size() > 0) {
                                for (int j = 0; j < followSet.size(); j++) {
                                    followSetBean = followSet.get(j);
                                    if (null != followSetBean) {
                                        if (followSetBean.isSelect()) {
                                            if (followSetBean.isItemOnHold()) {
                                                followSetBean.setItemOnHold(false);
                                                followSetBean.setJiaoQi("");
                                            } else {
                                                followSetBean.setItemOnHold(true);
                                                followSetBean.setJiaoQi(JIAOQI);
                                            }
                                            followSet.set(j, followSetBean);
                                            bean.setFollowSet(followSet);
                                            ConstantUtils.orderList.set(i, bean);
                                            mOrderListAdapter
                                                    .notifyDataSetChanged();
                                            break;
                                        }
                                        followSetModifier = followSetBean
                                                .getModifier();
                                        if (null != followSetModifier
                                                && followSetModifier.size() > 0) {
                                            for (int k = 0; k < followSetModifier
                                                    .size(); k++) {
                                                followSetModifierBean = followSetModifier
                                                        .get(k);
                                                if (null != followSetModifierBean) {
                                                    if (followSetModifierBean
                                                            .isSelect()) {
                                                        CommonUtils
                                                                .showToast(
                                                                        this,
                                                                        getString(R.string.gaima_no_jiaoqi));
                                                        break;
                                                    }
                                                    followSetModifierBean = null;
                                                }
                                            }
                                            followSetModifier = null;
                                        }
                                        followSetBean = null;
                                    }
                                }
                                followSet = null;
                            }
                        }
                    }
                    bean = null;
                }
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void qiCai() {
        try {
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                return;
            }
            OrderListBean bean = null;
            List<OrderListBean> modifier = null;
            OrderListBean modifierBean = null;
            List<OrderListBean> followSet = null;
            OrderListBean followSetBean = null;
            List<OrderListBean> followSetModifier = null;
            OrderListBean followSetModifierBean = null;

            List<OrderListBean> modifierList = null;
            OrderListBean modifierListBean = null;
            List<OrderListBean> followSetList = null;
            OrderListBean followSetListBean = null;
            List<OrderListBean> followSetModifierList = null;
            OrderListBean followSetModifierListBean = null;

            String isItemOnHold = null;
            for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                bean = ConstantUtils.orderList.get(i);
                if (null != bean) {
                    if (bean.isSelect()) {
                        if (null != bean.getDetailDto()) {
                            isItemOnHold = bean.getDetailDto().getIsItemOnHold();
                            if (!TextUtils.isEmpty(isItemOnHold)) {
                                if ("true".equals(isItemOnHold)) {
                                    if (!TextUtils.isEmpty(bean.getDetailDto()
                                            .getIsItemFired())
                                            && "true".equals(bean.getDetailDto()
                                                    .getIsItemFired())) {
                                        if (!TextUtils.isEmpty(bean.getDetailDto()
                                                .getIsLocalChangedItem())
                                                && "true".equals(bean
                                                        .getDetailDto()
                                                        .getIsLocalChangedItem())) {
                                            bean.setQiCai("");
                                            bean.setJiaoQi(JIAOQI);
                                            bean.getDetailDto().setIsItemFired(
                                                    "false");
                                            bean.getDetailDto()
                                                    .setIsLocalChangedItem("false");
                                            bean.getDetailDto().setPrintedKitchen(
                                                    "true");
                                        } else {
                                            bean.setQiCai(QICAI);
                                            bean.setJiaoQi("");
                                            CommonUtils
                                                    .showToast(
                                                            this,
                                                            getString(R.string.already_qicai_no_again_qicai));
                                        }
                                        ConstantUtils.orderList.set(i, bean);
                                        mOrderListAdapter.notifyDataSetChanged();
                                    } else {
                                        bean.setQiCai(QICAI);
                                        bean.getDetailDto().setIsItemFired("true");
                                        bean.getDetailDto().setIsLocalChangedItem(
                                                "true");
                                        bean.getDetailDto().setPrintedKitchen(
                                                "false");
                                        ConstantUtils.orderList.set(i, bean);
                                        mOrderListAdapter.notifyDataSetChanged();
                                    }
                                } else if ("false".equals(isItemOnHold)) {
                                    bean.setQiCai("");
                                    bean.setJiaoQi("");
                                    ConstantUtils.orderList.set(i, bean);
                                    mOrderListAdapter.notifyDataSetChanged();
                                    CommonUtils
                                            .showToast(
                                                    this,
                                                    getString(R.string.no_jiaoqi_not_qicai));
                                }
                                isItemOnHold = null;
                            }
                        } else {
                            CommonUtils.showToast(this,
                                    getString(R.string.no_send_not_qicai));
                        }
                        break;
                    } else {
                        if (null != bean.getDetailDto()) {
                            modifierList = bean.getModifierDto();
                            if (null != modifierList && modifierList.size() > 0) {
                                for (int j = 0; j < modifierList.size(); j++) {
                                    modifierListBean = modifierList.get(j);
                                    if (null != modifierListBean) {
                                        if (modifierListBean.isSelect()) {
                                            if (null != modifierListBean
                                                    .getDetailDto()) {
                                                isItemOnHold = modifierListBean
                                                        .getDetailDto()
                                                        .getIsItemOnHold();
                                                if (!TextUtils
                                                        .isEmpty(isItemOnHold)) {
                                                    if ("true".equals(isItemOnHold)) {
                                                        if (!TextUtils
                                                                .isEmpty(modifierListBean
                                                                        .getDetailDto()
                                                                        .getIsItemFired())
                                                                && "true"
                                                                        .equals(modifierListBean
                                                                                .getDetailDto()
                                                                                .getIsItemFired())) {
                                                            if (!TextUtils
                                                                    .isEmpty(modifierListBean
                                                                            .getDetailDto()
                                                                            .getIsLocalChangedItem())
                                                                    && "true"
                                                                            .equals(modifierListBean
                                                                                    .getDetailDto()
                                                                                    .getIsLocalChangedItem())) {
                                                                modifierListBean
                                                                        .setJiaoQi(JIAOQI);
                                                                modifierListBean
                                                                        .setQiCai("");
                                                                modifierListBean
                                                                        .getDetailDto()
                                                                        .setIsItemFired(
                                                                                "false");
                                                                modifierListBean
                                                                        .getDetailDto()
                                                                        .setIsLocalChangedItem(
                                                                                "false");
                                                                modifierListBean
                                                                        .getDetailDto()
                                                                        .setPrintedKitchen(
                                                                                "true");
                                                                modifierList
                                                                        .set(j,
                                                                                modifierListBean);
                                                                bean.setModifierDto(modifierList);
                                                                ConstantUtils.orderList
                                                                        .set(i,
                                                                                bean);
                                                                mOrderListAdapter
                                                                        .notifyDataSetChanged();
                                                            } else {
                                                                modifierListBean
                                                                        .setQiCai(QICAI);
                                                                modifierListBean
                                                                        .setJiaoQi("");
                                                                modifierList
                                                                        .set(j,
                                                                                modifierListBean);
                                                                bean.setModifierDto(modifierList);
                                                                ConstantUtils.orderList
                                                                        .set(i,
                                                                                bean);
                                                                mOrderListAdapter
                                                                        .notifyDataSetChanged();
                                                                CommonUtils
                                                                        .showToast(
                                                                                this,
                                                                                getString(R.string.already_qicai_no_again_qicai));
                                                            }
                                                        } else {
                                                            modifierListBean
                                                                    .setQiCai(QICAI);
                                                            modifierListBean
                                                                    .setJiaoQi("");
                                                            modifierListBean
                                                                    .getDetailDto()
                                                                    .setIsItemFired(
                                                                            "true");
                                                            modifierListBean
                                                                    .getDetailDto()
                                                                    .setIsLocalChangedItem(
                                                                            "true");
                                                            modifierListBean
                                                                    .getDetailDto()
                                                                    .setPrintedKitchen(
                                                                            "false");
                                                            modifierList
                                                                    .set(j,
                                                                            modifierListBean);
                                                            bean.setModifierDto(modifierList);
                                                            ConstantUtils.orderList
                                                                    .set(i, bean);
                                                            mOrderListAdapter
                                                                    .notifyDataSetChanged();
                                                        }
                                                    } else if ("false"
                                                            .equals(isItemOnHold)) {
                                                        modifierListBean
                                                                .setQiCai("");
                                                        modifierListBean
                                                                .setJiaoQi("");
                                                        modifierList.set(j,
                                                                modifierListBean);
                                                        bean.setModifierDto(modifierList);
                                                        ConstantUtils.orderList
                                                                .set(i, bean);
                                                        mOrderListAdapter
                                                                .notifyDataSetChanged();
                                                        CommonUtils
                                                                .showToast(
                                                                        this,
                                                                        getString(R.string.no_jiaoqi_not_qicai));
                                                    }
                                                    isItemOnHold = null;
                                                }
                                            }
                                            break;
                                        }
                                        modifierListBean = null;
                                    }
                                }
                                modifierList = null;
                            }
                            followSetList = bean.getFollowSetDto();
                            if (null != followSetList && followSetList.size() > 0) {
                                for (int j = 0; j < followSetList.size(); j++) {
                                    followSetListBean = followSetList.get(j);
                                    if (null != followSetListBean) {
                                        if (followSetListBean.isSelect()) {
                                            if (null != followSetListBean
                                                    .getDetailDto()) {
                                                isItemOnHold = followSetListBean
                                                        .getDetailDto()
                                                        .getIsItemOnHold();
                                                if (!TextUtils
                                                        .isEmpty(isItemOnHold)) {
                                                    if ("true".equals(isItemOnHold)) {
                                                        if (!TextUtils
                                                                .isEmpty(followSetListBean
                                                                        .getDetailDto()
                                                                        .getIsItemFired())
                                                                && "true"
                                                                        .equals(followSetListBean
                                                                                .getDetailDto()
                                                                                .getIsItemFired())) {
                                                            if (!TextUtils
                                                                    .isEmpty(followSetListBean
                                                                            .getDetailDto()
                                                                            .getIsLocalChangedItem())
                                                                    && "true"
                                                                            .equals(followSetListBean
                                                                                    .getDetailDto()
                                                                                    .getIsLocalChangedItem())) {
                                                                followSetListBean
                                                                        .setJiaoQi(JIAOQI);
                                                                followSetListBean
                                                                        .setQiCai("");
                                                                followSetListBean
                                                                        .getDetailDto()
                                                                        .setIsItemFired(
                                                                                "false");
                                                                followSetListBean
                                                                        .getDetailDto()
                                                                        .setIsLocalChangedItem(
                                                                                "false");
                                                                followSetListBean
                                                                        .getDetailDto()
                                                                        .setPrintedKitchen(
                                                                                "true");
                                                                followSetList
                                                                        .set(j,
                                                                                followSetListBean);
                                                                bean.setFollowSetDto(followSetList);
                                                                ConstantUtils.orderList
                                                                        .set(i,
                                                                                bean);
                                                                mOrderListAdapter
                                                                        .notifyDataSetChanged();
                                                            } else {
                                                                followSetListBean
                                                                        .setQiCai(QICAI);
                                                                followSetListBean
                                                                        .setJiaoQi("");
                                                                followSetList
                                                                        .set(j,
                                                                                followSetListBean);
                                                                bean.setFollowSetDto(followSetList);
                                                                ConstantUtils.orderList
                                                                        .set(i,
                                                                                bean);
                                                                mOrderListAdapter
                                                                        .notifyDataSetChanged();
                                                                CommonUtils
                                                                        .showToast(
                                                                                this,
                                                                                getString(R.string.already_qicai_no_again_qicai));
                                                            }
                                                        } else {
                                                            followSetListBean
                                                                    .setQiCai(QICAI);
                                                            followSetListBean
                                                                    .setJiaoQi("");
                                                            followSetListBean
                                                                    .getDetailDto()
                                                                    .setIsItemFired(
                                                                            "true");
                                                            followSetListBean
                                                                    .getDetailDto()
                                                                    .setIsLocalChangedItem(
                                                                            "true");
                                                            followSetListBean
                                                                    .getDetailDto()
                                                                    .setPrintedKitchen(
                                                                            "false");
                                                            followSetList
                                                                    .set(j,
                                                                            followSetListBean);
                                                            bean.setFollowSetDto(followSetList);
                                                            ConstantUtils.orderList
                                                                    .set(i, bean);
                                                            mOrderListAdapter
                                                                    .notifyDataSetChanged();
                                                        }
                                                    } else if ("false"
                                                            .equals(isItemOnHold)) {
                                                        followSetListBean
                                                                .setQiCai("");
                                                        followSetListBean
                                                                .setJiaoQi("");
                                                        followSetList.set(j,
                                                                followSetListBean);
                                                        bean.setFollowSetDto(followSetList);
                                                        ConstantUtils.orderList
                                                                .set(i, bean);
                                                        mOrderListAdapter
                                                                .notifyDataSetChanged();
                                                        CommonUtils
                                                                .showToast(
                                                                        this,
                                                                        getString(R.string.no_jiaoqi_not_qicai));
                                                    }
                                                    isItemOnHold = null;
                                                }
                                            }
                                            break;
                                        }
                                        followSetModifierList = followSetListBean
                                                .getModifierDto();
                                        if (null != followSetModifierList
                                                && followSetModifierList.size() > 0) {
                                            for (int k = 0; k < followSetModifierList
                                                    .size(); k++) {
                                                followSetModifierListBean = followSetModifierList
                                                        .get(k);
                                                if (null != followSetModifierListBean) {
                                                    if (followSetModifierListBean
                                                            .isSelect()) {
                                                        isItemOnHold = followSetModifierListBean
                                                                .getDetailDto()
                                                                .getIsItemOnHold();
                                                        if (!TextUtils
                                                                .isEmpty(isItemOnHold)) {
                                                            if ("true"
                                                                    .equals(isItemOnHold)) {
                                                                if (!TextUtils
                                                                        .isEmpty(followSetModifierListBean
                                                                                .getDetailDto()
                                                                                .getIsItemFired())
                                                                        && "true"
                                                                                .equals(followSetModifierListBean
                                                                                        .getDetailDto()
                                                                                        .getIsItemFired())) {
                                                                    if (!TextUtils
                                                                            .isEmpty(followSetModifierListBean
                                                                                    .getDetailDto()
                                                                                    .getIsLocalChangedItem())
                                                                            && "true"
                                                                                    .equals(followSetModifierListBean
                                                                                            .getDetailDto()
                                                                                            .getIsLocalChangedItem())) {
                                                                        followSetModifierListBean
                                                                                .setJiaoQi(JIAOQI);
                                                                        followSetModifierListBean
                                                                                .setQiCai("");
                                                                        followSetModifierListBean
                                                                                .getDetailDto()
                                                                                .setIsItemFired(
                                                                                        "false");
                                                                        followSetModifierListBean
                                                                                .getDetailDto()
                                                                                .setIsLocalChangedItem(
                                                                                        "false");
                                                                        followSetModifierListBean
                                                                                .getDetailDto()
                                                                                .setPrintedKitchen(
                                                                                        "true");
                                                                        followSetModifierList
                                                                                .set(k,
                                                                                        followSetModifierListBean);
                                                                        followSetListBean
                                                                                .setModifierDto(followSetModifierList);
                                                                        followSetList
                                                                                .set(j,
                                                                                        followSetListBean);
                                                                        bean.setModifierDto(followSetList);
                                                                        ConstantUtils.orderList
                                                                                .set(i,
                                                                                        bean);
                                                                        mOrderListAdapter
                                                                                .notifyDataSetChanged();
                                                                    } else {
                                                                        followSetModifierListBean
                                                                                .setQiCai(QICAI);
                                                                        followSetModifierListBean
                                                                                .setJiaoQi("");
                                                                        followSetModifierList
                                                                                .set(k,
                                                                                        followSetModifierListBean);
                                                                        followSetListBean
                                                                                .setModifierDto(followSetModifierList);
                                                                        followSetList
                                                                                .set(j,
                                                                                        followSetListBean);
                                                                        bean.setModifierDto(followSetList);
                                                                        ConstantUtils.orderList
                                                                                .set(i,
                                                                                        bean);
                                                                        mOrderListAdapter
                                                                                .notifyDataSetChanged();
                                                                        CommonUtils
                                                                                .showToast(
                                                                                        this,
                                                                                        getString(R.string.already_qicai_no_again_qicai));
                                                                    }
                                                                } else {
                                                                    followSetModifierListBean
                                                                            .setQiCai(QICAI);
                                                                    followSetModifierListBean
                                                                            .setJiaoQi("");
                                                                    followSetModifierListBean
                                                                            .getDetailDto()
                                                                            .setIsItemFired(
                                                                                    "true");
                                                                    followSetModifierListBean
                                                                            .getDetailDto()
                                                                            .setIsLocalChangedItem(
                                                                                    "true");
                                                                    followSetModifierListBean
                                                                            .getDetailDto()
                                                                            .setPrintedKitchen(
                                                                                    "false");
                                                                    followSetModifierList
                                                                            .set(k,
                                                                                    followSetModifierListBean);
                                                                    followSetListBean
                                                                            .setModifierDto(followSetModifierList);
                                                                    followSetList
                                                                            .set(j,
                                                                                    followSetListBean);
                                                                    bean.setModifierDto(followSetList);
                                                                    ConstantUtils.orderList
                                                                            .set(i,
                                                                                    bean);
                                                                    mOrderListAdapter
                                                                            .notifyDataSetChanged();
                                                                }
                                                            } else if ("false"
                                                                    .equals(isItemOnHold)) {
                                                                followSetModifierListBean
                                                                        .setQiCai("");
                                                                followSetModifierListBean
                                                                        .setJiaoQi("");
                                                                followSetModifierList
                                                                        .set(k,
                                                                                followSetModifierListBean);
                                                                followSetListBean
                                                                        .setModifierDto(followSetModifierList);
                                                                followSetList
                                                                        .set(j,
                                                                                followSetListBean);
                                                                bean.setModifierDto(followSetList);
                                                                ConstantUtils.orderList
                                                                        .set(i,
                                                                                bean);
                                                                mOrderListAdapter
                                                                        .notifyDataSetChanged();
                                                                CommonUtils
                                                                        .showToast(
                                                                                this,
                                                                                getString(R.string.no_jiaoqi_not_qicai));
                                                            }
                                                            isItemOnHold = null;
                                                        }
                                                        break;
                                                    }
                                                    followSetModifierListBean = null;
                                                }
                                            }
                                            followSetModifierList = null;
                                        }
                                        followSetListBean = null;
                                    }
                                }
                                followSetList = null;
                            }
                        } else {
                            modifier = bean.getModifier();
                            if (null != modifier && modifier.size() > 0) {
                                for (int j = 0; j < modifier.size(); j++) {
                                    modifierBean = modifier.get(j);
                                    if (null != modifierBean) {
                                        if (modifierBean.isSelect()) {
                                            CommonUtils
                                                    .showToast(
                                                            this,
                                                            getString(R.string.no_send_not_qicai));
                                            break;
                                        }
                                        modifierBean = null;
                                    }
                                }
                                modifier = null;
                            }
                            followSet = bean.getFollowSet();
                            if (null != followSet && followSet.size() > 0) {
                                for (int j = 0; j < followSet.size(); j++) {
                                    followSetBean = followSet.get(j);
                                    if (null != followSetBean) {
                                        if (followSetBean.isSelect()) {
                                            CommonUtils
                                                    .showToast(
                                                            this,
                                                            getString(R.string.no_send_not_qicai));
                                            break;
                                        }
                                        followSetModifier = followSetBean
                                                .getModifier();
                                        if (null != followSetModifier
                                                && followSetModifier.size() > 0) {
                                            for (int k = 0; k < followSetModifier
                                                    .size(); k++) {
                                                followSetModifierBean = followSetModifier
                                                        .get(k);
                                                if (null != followSetModifierBean) {
                                                    if (followSetModifierBean
                                                            .isSelect()) {
                                                        CommonUtils
                                                                .showToast(
                                                                        this,
                                                                        getString(R.string.no_send_not_qicai));
                                                        break;
                                                    }
                                                    followSetModifierBean = null;
                                                }
                                            }
                                            followSetModifier = null;
                                        }
                                        followSetBean = null;
                                    }
                                }
                                followSet = null;
                            }
                        }
                    }
                    bean = null;
                }
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 修改订单数量
	 */
	private void alterOrderQty() {
        try {
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                return;
            }
            String number = tvNumber.getText().toString().trim();
            if (TextUtils.isEmpty(number) || "0".equals(number)) {
                return;
            }
            OrderListBean bean = null;
            List<OrderListBean> modifier = null;
            OrderListBean modifierBean = null;
            List<OrderListBean> followSet = null;
            OrderListBean followSetBean = null;
            List<OrderListBean> followSetModifier = null;
            OrderListBean followSetModifierBean = null;

            List<OrderListBean> modifierList = null;
            OrderListBean modifierListBean = null;
            List<OrderListBean> followSetList = null;
            OrderListBean followSetListBean = null;
            List<OrderListBean> followSetModifierList = null;
            OrderListBean followSetModifierListBean = null;

            for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                bean = ConstantUtils.orderList.get(i);
                if (null != bean) {
                    if (bean.isSelect()) {
                        if (null != bean.getDetailDto()) {
                            CommonUtils.showToast(this,
                                    getString(R.string.submitted_not_change));
                        } else {
                            getItemByItemId(true, i, OrderListAdapter.VOID,
                                    OrderListAdapter.VOID, OrderListAdapter.NORMAL,
                                    bean.getDto().getItemId(), number);
                        }
                        break;
                    } else {
                        if (null != bean.getDetailDto()) {
                            modifierList = bean.getModifierDto();
                            if (null != modifierList && modifierList.size() > 0) {
                                for (int j = 0; j < modifierList.size(); j++) {
                                    modifierListBean = modifierList.get(j);
                                    if (null != modifierListBean) {
                                        if (modifierListBean.isSelect()) {
                                            if (null != modifierListBean
                                                    .getDetailDto()) {
                                                CommonUtils
                                                        .showToast(
                                                                this,
                                                                getString(R.string.submitted_not_change));
                                            }
                                            break;
                                        }
                                        modifierListBean = null;
                                    }
                                }
                                modifierList = null;
                            }
                            followSetList = bean.getFollowSetDto();
                            if (null != followSetList && followSetList.size() > 0) {
                                for (int j = 0; j < followSetList.size(); j++) {
                                    followSetListBean = followSetList.get(j);
                                    if (null != followSetListBean) {
                                        if (followSetListBean.isSelect()) {
                                            if (null != followSetListBean
                                                    .getDetailDto()) {
                                                CommonUtils
                                                        .showToast(
                                                                this,
                                                                getString(R.string.submitted_not_change));
                                            }
                                            break;
                                        }
                                        followSetModifierList = followSetListBean
                                                .getModifierDto();
                                        if (null != followSetModifierList
                                                && followSetModifierList.size() > 0) {
                                            for (int k = 0; k < followSetModifierList
                                                    .size(); k++) {
                                                followSetModifierListBean = followSetModifierList
                                                        .get(k);
                                                if (null != followSetModifierListBean) {
                                                    if (followSetModifierListBean
                                                            .isSelect()) {
                                                        CommonUtils
                                                                .showToast(
                                                                        this,
                                                                        getString(R.string.submitted_not_change));
                                                    }
                                                    followSetModifierListBean = null;
                                                }
                                            }
                                            followSetModifierList = null;
                                        }
                                        followSetListBean = null;
                                    }
                                }
                                followSetList = null;
                            }
                        } else {
                            modifier = bean.getModifier();
                            if (null != modifier && modifier.size() > 0) {
                                for (int j = 0; j < modifier.size(); j++) {
                                    modifierBean = modifier.get(j);
                                    if (null != modifierBean) {
                                        if (modifierBean.isSelect()) {
                                            getItemByItemId(true, i, j,
                                                    OrderListAdapter.VOID,
                                                    OrderListAdapter.MODIFIER,
                                                    modifierBean.getDto()
                                                            .getItemId(), number);
                                            break;
                                        }
                                        modifierBean = null;
                                    }
                                }
                                modifier = null;
                            }
                            followSet = bean.getFollowSet();
                            if (null != followSet && followSet.size() > 0) {
                                for (int j = 0; j < followSet.size(); j++) {
                                    followSetBean = followSet.get(j);
                                    if (null != followSetBean) {
                                        if (followSetBean.isSelect()) {
                                            getItemByItemId(true, i, j,
                                                    OrderListAdapter.VOID,
                                                    OrderListAdapter.FOLLOWSET,
                                                    followSetBean.getDto()
                                                            .getItemId(), number);
                                            break;
                                        }
                                        followSetModifier = followSetBean
                                                .getModifier();
                                        if (null != followSetModifier
                                                && followSetModifier.size() > 0) {
                                            for (int k = 0; k < followSetModifier
                                                    .size(); k++) {
                                                followSetModifierBean = followSetModifier
                                                        .get(k);
                                                if (null != followSetModifierBean) {
                                                    if (followSetModifierBean
                                                            .isSelect()) {
                                                        getItemByItemId(
                                                                true,
                                                                i,
                                                                j,
                                                                k,
                                                                OrderListAdapter.FOLLOWSET_MODIFIER,
                                                                followSetModifierBean
                                                                        .getDto()
                                                                        .getItemId(),
                                                                number);
                                                        break;
                                                    }
                                                    followSetModifierBean = null;
                                                }
                                            }
                                            followSetModifier = null;
                                        }
                                        followSetBean = null;
                                    }
                                }
                                followSet = null;
                            }
                        }
                    }
                    bean = null;
                }
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void getItemByItemId(final boolean isShow,
			final int parentPosition, final int position,
			final int subPosition, final String status, String itemId,
			final String number) {
        try {
            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.GET_ITEM_BY_ITEM_ID);

            mSoapObject.addProperty(ConstantUtils.SHOP_ID,
                    ConstantUtils.userInfo.getShopId());
            mSoapObject.addProperty("itemId", itemId);

            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.GET_ITEM_BY_ITEM_ID, new HandlerCallBack() {

                        @Override
                        public void handleFinish(HashMap<String, Object> result) {
                            try {
                                if (HandleHttpRequestResult.isError(
                                        FoodCategoryActivity.this, isShow,
                                        SoapUtils.GET_ITEM_BY_ITEM_ID, result)) {
                                    return;
                                }

                                SoapObject object = (SoapObject) result
                                        .get(HttpHandler.RESULT);

                                SoapObject mObject = (SoapObject) object
                                        .getProperty("itemMasterDto");

                                if (null != mObject) {
                                    parseGetItemByItemId(mObject, parentPosition,
                                            position, subPosition, status, number);
                                } else {
                                    CommonUtils.showToast(
                                            FoodCategoryActivity.this,
                                            getString(R.string.no_data));
                                }
                            } catch (Exception e) {
                                CommonUtils.showToast(FoodCategoryActivity.this,
                                        getString(R.string.remote_server_error));
                            }
                        }

                        @Override
                        public void handleFailure() {
                            CommonUtils.showToast(FoodCategoryActivity.this,
                                    getString(R.string.remote_server_error));
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void parseGetItemByItemId(SoapObject soapObject,
			int parentPosition, int position, int subPosition, String status,
			String text) {
        try {
            ItemMasterDto dto = new ItemMasterDto();
            dto = GetItemMasterDto.getItemMasterDto(soapObject);

            if (null != dto) {
                if (null != dto.getIsLimitedItem()) {
                    if ("true".equals(dto.getIsLimitedItem())) {
                        OrderListBean bean = null;
                        String count = null;
                        bean = ConstantUtils.orderList.get(parentPosition);
                        if (null != bean && null != bean.getDto()) {
                            Integer number = Integer.valueOf(text);
                            if (OrderListAdapter.NORMAL.equals(status)) {
                                count = bean.getDto().getItemCount();
                                if (!TextUtils.isEmpty(count)) {
                                    if (number <= Integer.valueOf(count)) {
                                        bean.getDto().setItemQty(
                                                String.valueOf(number));
                                        ConstantUtils.orderList.set(parentPosition,
                                                bean);
                                        mOrderListAdapter.notifyDataSetChanged();
                                        setTotalPrice();
                                    } else {
                                        CommonUtils
                                                .showToast(
                                                        this,
                                                        getString(R.string.more_than_number));
                                    }
                                    count = null;
                                    bean = null;
                                }

                            } else if (OrderListAdapter.MODIFIER.equals(status)) {
                                List<OrderListBean> modifier = bean.getModifier();
                                if (null != modifier && modifier.size() > 0) {
                                    OrderListBean modifierBean = modifier
                                            .get(position);
                                    if (null != modifierBean
                                            && null != modifierBean.getDto()) {
                                        count = modifierBean.getDto()
                                                .getItemCount();
                                        if (!TextUtils.isEmpty(count)) {
                                            if (number <= Integer.valueOf(count)) {
                                                modifierBean.getDto().setItemQty(
                                                        String.valueOf(number));
                                                modifier.set(position, modifierBean);
                                                bean.setModifier(modifier);
                                                ConstantUtils.orderList.set(
                                                        parentPosition, bean);
                                                mOrderListAdapter
                                                        .notifyDataSetChanged();
                                                setTotalPrice();
                                            } else {
                                                CommonUtils
                                                        .showToast(
                                                                this,
                                                                getString(R.string.more_than_number));
                                            }
                                            count = null;
                                            modifierBean = null;
                                            modifier = null;
                                            bean = null;
                                        }
                                    }
                                }
                            } else if (OrderListAdapter.FOLLOWSET.equals(status)) {
                                List<OrderListBean> followSet = bean.getFollowSet();
                                if (null != followSet && followSet.size() > 0) {
                                    OrderListBean followSetBean = followSet
                                            .get(position);
                                    if (null != followSetBean
                                            && null != followSetBean.getDto()) {
                                        count = followSetBean.getDto().getItemQty();
                                        if (!TextUtils.isEmpty(count)) {
                                            if (number <= Integer.getInteger(count)) {
                                                followSetBean.getDto().setItemQty(
                                                        String.valueOf(number));
                                                followSet.set(position,
                                                        followSetBean);
                                                bean.setFollowSet(followSet);
                                                ConstantUtils.orderList.set(
                                                        parentPosition, bean);
                                                mOrderListAdapter
                                                        .notifyDataSetChanged();
                                                setTotalPrice();
                                            } else {
                                                CommonUtils
                                                        .showToast(
                                                                this,
                                                                getString(R.string.more_than_number));
                                            }
                                            count = null;
                                            followSetBean = null;
                                            followSet = null;
                                            bean = null;
                                        }
                                    }
                                }
                            } else if (OrderListAdapter.FOLLOWSET_MODIFIER
                                    .equals(status)) {
                                List<OrderListBean> followSet = bean.getFollowSet();
                                if (null != followSet && followSet.size() > 0) {
                                    OrderListBean followSetBean = followSet
                                            .get(position);
                                    if (null != followSetBean) {
                                        List<OrderListBean> followSetModifier = followSetBean
                                                .getModifier();
                                        if (null != followSetModifier
                                                && followSetModifier.size() > 0) {
                                            OrderListBean followSetModifierBean = followSetModifier
                                                    .get(subPosition);
                                            if (null != followSetModifierBean
                                                    && null != followSetModifierBean
                                                            .getDto()) {
                                                count = followSetModifierBean
                                                        .getDto().getItemQty();
                                                if (!TextUtils.isEmpty(count)) {
                                                    if (number <= Integer
                                                            .getInteger(count)) {
                                                        followSetModifierBean
                                                                .getDto()
                                                                .setItemQty(
                                                                        String.valueOf(number));
                                                        followSetModifier
                                                                .set(subPosition,
                                                                        followSetModifierBean);
                                                        followSetBean
                                                                .setModifier(followSetModifier);
                                                        followSet.set(position,
                                                                followSetBean);
                                                        bean.setFollowSet(followSet);
                                                        ConstantUtils.orderList
                                                                .set(parentPosition,
                                                                        bean);
                                                        mOrderListAdapter
                                                                .notifyDataSetChanged();
                                                        setTotalPrice();
                                                    } else {
                                                        CommonUtils
                                                                .showToast(
                                                                        this,
                                                                        getString(R.string.more_than_number));
                                                    }
                                                    count = null;
                                                    followSetModifierBean = null;
                                                    followSetModifier = null;
                                                    followSetBean = null;
                                                    followSet = null;
                                                    bean = null;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if ("false".equals(dto.getIsLimitedItem())) {
                        OrderListBean bean = null;
                        bean = ConstantUtils.orderList.get(parentPosition);
                        if (null != bean && null != bean.getDto()) {
                            Integer number = Integer.valueOf(text);
                            if (OrderListAdapter.NORMAL.equals(status)) {
                                bean.getDto().setItemQty(String.valueOf(number));
                                ConstantUtils.orderList.set(parentPosition, bean);
                                mOrderListAdapter.notifyDataSetChanged();
                                setTotalPrice();

                                bean = null;

                            } else if (OrderListAdapter.MODIFIER.equals(status)) {
                                List<OrderListBean> modifier = bean.getModifier();
                                if (null != modifier && modifier.size() > 0) {
                                    OrderListBean modifierBean = modifier
                                            .get(position);
                                    if (null != modifierBean
                                            && null != modifierBean.getDto()) {
                                        modifierBean.getDto().setItemQty(
                                                String.valueOf(number));
                                        modifier.set(position, modifierBean);
                                        bean.setModifier(modifier);
                                        ConstantUtils.orderList.set(parentPosition,
                                                bean);
                                        mOrderListAdapter.notifyDataSetChanged();
                                        setTotalPrice();

                                        modifierBean = null;
                                        modifier = null;
                                        bean = null;
                                    }
                                }
                            } else if (OrderListAdapter.FOLLOWSET.equals(status)) {
                                List<OrderListBean> followSet = bean.getFollowSet();
                                if (null != followSet && followSet.size() > 0) {
                                    OrderListBean followSetBean = followSet
                                            .get(position);
                                    if (null != followSetBean
                                            && null != followSetBean.getDto()) {
                                        followSetBean.getDto().setItemQty(
                                                String.valueOf(number));
                                        followSet.set(position, followSetBean);
                                        bean.setFollowSet(followSet);
                                        ConstantUtils.orderList.set(parentPosition,
                                                bean);
                                        mOrderListAdapter.notifyDataSetChanged();
                                        setTotalPrice();

                                        followSetBean = null;
                                        followSet = null;
                                        bean = null;
                                    }
                                }
                            } else if (OrderListAdapter.FOLLOWSET_MODIFIER
                                    .equals(status)) {
                                List<OrderListBean> followSet = bean.getFollowSet();
                                if (null != followSet && followSet.size() > 0) {
                                    OrderListBean followSetBean = followSet
                                            .get(position);
                                    if (null != followSetBean) {
                                        List<OrderListBean> followSetModifier = followSetBean
                                                .getModifier();
                                        if (null != followSetModifier
                                                && followSetModifier.size() > 0) {
                                            OrderListBean followSetModifierBean = followSetModifier
                                                    .get(subPosition);
                                            if (null != followSetModifierBean
                                                    && null != followSetModifierBean
                                                            .getDto()) {
                                                followSetModifierBean
                                                        .getDto()
                                                        .setItemQty(
                                                                String.valueOf(number));
                                                followSetModifier.set(subPosition,
                                                        followSetModifierBean);
                                                followSetBean
                                                        .setModifier(followSetModifier);
                                                followSet.set(position,
                                                        followSetBean);
                                                bean.setFollowSet(followSet);
                                                ConstantUtils.orderList.set(
                                                        parentPosition, bean);
                                                mOrderListAdapter
                                                        .notifyDataSetChanged();
                                                setTotalPrice();

                                                followSetModifierBean = null;
                                                followSetModifier = null;
                                                followSetBean = null;
                                                followSet = null;
                                                bean = null;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void gaiMa(final boolean isShow) {
        try {
            if (View.VISIBLE == calculateLayout.getVisibility()) {
                setViewGone();
            }
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                return;
            }
            boolean isSelect = false;
            OrderListBean bean = null;
            List<OrderListBean> modifier = null;
            OrderListBean modifierBean = null;
            List<OrderListBean> followSet = null;
            OrderListBean followSetBean = null;
            List<OrderListBean> followSetModifier = null;
            OrderListBean followSetModifierBean = null;
            for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                bean = ConstantUtils.orderList.get(i);
                if (null != bean) {
                    if (null == bean.getDetailDto()) {
                        if (bean.isSelect()) {
                            isSelect = true;
                            break;
                        } else {
                            modifier = bean.getModifier();
                            if (null != modifier && modifier.size() > 0) {
                                for (int j = 0; j < modifier.size(); j++) {
                                    modifierBean = modifier.get(j);
                                    if (null != modifierBean) {
                                        if (modifierBean.isSelect()) {
                                            isSelect = true;
                                            break;
                                        }
                                        modifierBean = null;
                                    }
                                }
                                modifier = null;
                            }
                            followSet = bean.getFollowSet();
                            if (null != followSet && followSet.size() > 0) {
                                for (int j = 0; j < followSet.size(); j++) {
                                    followSetBean = followSet.get(j);
                                    if (null != followSetBean) {
                                        if (followSetBean.isSelect()) {
                                            isSelect = true;
                                            break;
                                        }
                                        followSetModifier = followSetBean
                                                .getModifier();
                                        if (null != followSetModifier
                                                && followSetModifier.size() > 0) {
                                            for (int k = 0; k < followSetModifier
                                                    .size(); k++) {
                                                followSetModifierBean = followSetModifier
                                                        .get(k);
                                                if (null != followSetModifierBean) {
                                                    if (followSetModifierBean
                                                            .isSelect()) {
                                                        isSelect = true;
                                                        break;
                                                    }
                                                    followSetModifierBean = null;
                                                }
                                            }
                                            followSetModifier = null;
                                        }
                                        followSetBean = null;
                                    }
                                }
                                followSet = null;
                            }
                        }
                    }
                    bean = null;
                }
            }

            if (!isSelect) {
                return;
            }

            getGaiMaData(true, ConstantUtils.category_id, "false");
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void getGaiMaData(final boolean isShow, String parentCategoryId,
			String isSmartCategory) {
        try {
            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.Get_AVAILABLE_ITEM_CATEGORY_LIST);

            mSoapObject.addProperty(ConstantUtils.ACCOUNT_ID,
                    ConstantUtils.userInfo.getAccountId());
            mSoapObject.addProperty(ConstantUtils.SHOP_ID,
                    ConstantUtils.userInfo.getShopId());
            mSoapObject.addProperty("parentCategoryId", ConstantUtils.category_id);
            mSoapObject.addProperty("isSmartCategory", isSmartCategory);

            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.Get_AVAILABLE_ITEM_CATEGORY_LIST,
                    new HandlerCallBack() {

                        @Override
                        public void handleFinish(HashMap<String, Object> result) {
                            try {
                                if (HandleHttpRequestResult.isError(
                                        FoodCategoryActivity.this, isShow,
                                        SoapUtils.Get_AVAILABLE_ITEM_CATEGORY_LIST,
                                        result)) {
                                    return;
                                }

                                SoapObject object = (SoapObject) result
                                        .get(HttpHandler.RESULT);

                                SoapObject parentSoapObject = (SoapObject) object
                                        .getProperty("parentItemCategoryDto");

                                SoapObject categorySoapObject = (SoapObject) object
                                        .getProperty(SoapUtils.RESULT_LIST);

                                if (null != parentSoapObject) {
                                    orderParentCategoryDto = GetCategoryDto
                                            .getCategoryDto(parentSoapObject);
                                }

                                if (null != categorySoapObject
                                        && 0 != categorySoapObject
                                                .getPropertyCount()) {
                                    // parseGetAvailableItemCategoryList(categorySoapObject);
                                } else {
                                    CommonUtils.showToast(
                                            FoodCategoryActivity.this,
                                            getString(R.string.no_data));
                                }
                            } catch (Exception e) {
                                CommonUtils.showToast(FoodCategoryActivity.this,
                                        getString(R.string.remote_server_error));
                            }
                        }

                        @Override
                        public void handleFailure() {
                            CommonUtils.showToast(FoodCategoryActivity.this,
                                    getString(R.string.remote_server_error));
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

    private List<TxSalesDetailDto> reassignSeqIndxToOrderList(){
        if (null == ConstantUtils.orderList
                || ConstantUtils.orderList.size() <= 0) {
            return null;
        }
        List<TxSalesDetailDto> dtoList = new ArrayList<TxSalesDetailDto>();
        OrderListBean bean = null;
        List<OrderListBean> modifierList = null;
        OrderListBean modifierListBean = null;
        List<OrderListBean> followSetList = null;
        OrderListBean followSetListBean = null;
        List<OrderListBean> followSetModifierList = null;
        OrderListBean followSetModifierListBean = null;
        List<OrderListBean> modifier = null;
        OrderListBean modifierBean = null;
        List<OrderListBean> followSet = null;
        OrderListBean followSetBean = null;
        List<OrderListBean> followSetModifier = null;
        OrderListBean followSetModifierBean = null;
        try {
            int seqNo = ConstantUtils.maxSeqNumber;
            int itemOrderRunningIndex = ConstantUtils.maxItemOrderRunningIndex;
            int itemSetRunningIndex = ConstantUtils.maxItemSetRunningIndex;
            for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                bean = ConstantUtils.orderList.get(i);
                if (null != bean) {
                    if (null != bean.getDetailDto()) {
                        dtoList.add(bean.getDetailDto());
                        modifierList = bean.getModifierDto();
                        if (null != modifierList && modifierList.size() > 0) {
                            for (int j = 0; j < modifierList.size(); j++) {
                                modifierListBean = modifierList.get(j);
                                if (null != modifierListBean) {
                                    if (null != modifierListBean.getDetailDto()) {
                                        dtoList.add(modifierListBean.getDetailDto());
                                    }
                                    modifierListBean = null;
                                }
                            }
                            modifierList = null;
                        }
                        followSetList = bean.getFollowSetDto();
                        if (null != followSetList && followSetList.size() > 0) {
                            for (int j = 0; j < followSetList.size(); j++) {
                                followSetListBean = followSetList.get(j);
                                if (null != followSetListBean) {
                                    if (null != followSetListBean.getDetailDto()) {
                                        dtoList.add(followSetListBean
                                                .getDetailDto());
                                    }
                                    followSetModifierList = followSetListBean
                                            .getModifierDto();
                                    if (null != followSetModifierList
                                            && followSetModifierList.size() > 0) {
                                        for (int k = 0; k < followSetModifierList
                                                .size(); k++) {
                                            followSetModifierListBean = followSetModifierList
                                                    .get(k);
                                            if (null != followSetModifierListBean) {
                                                if (null != followSetModifierListBean
                                                        .getDetailDto()) {
                                                    dtoList.add(followSetModifierListBean
                                                            .getDetailDto());
                                                }
                                                followSetModifierListBean = null;
                                            }
                                        }
                                        followSetModifierList = null;
                                    }
                                    followSetListBean = null;
                                }
                            }
                            followSetList = null;
                        }
                    } else {
                        if (null != bean.getDto()) {
                            seqNo++;
                            itemOrderRunningIndex++;
                            itemSetRunningIndex++;
                            dtoList.add(ItemMasterDtoToTxSalesDetailDto
                                    .getTxSalesDetailDto(bean.getDto(),
                                            bean.isItemOnHold(), seqNo,
                                            itemOrderRunningIndex,
                                            itemSetRunningIndex, "0"));
                            modifier = bean.getModifier();
                            if (null != modifier && modifier.size() > 0) {
                                for (int j = 0; j < modifier.size(); j++) {
                                    modifierBean = modifier.get(j);
                                    if (null != modifierBean) {
                                        if (null != modifierBean.getDto()) {
                                            seqNo++;
                                            dtoList.add(ItemMasterDtoToTxSalesDetailDto.getTxSalesDetailDto(
                                                    modifierBean.getDto(),
                                                    modifierBean.isItemOnHold(),
                                                    seqNo, itemOrderRunningIndex,
                                                    itemSetRunningIndex, "1"));
                                        }
                                        modifierBean = null;
                                    }
                                }
                                modifier = null;
                            }
                            followSet = bean.getFollowSet();
                            if (null != followSet && followSet.size() > 0) {
                                for (int j = 0; j < followSet.size(); j++) {
                                    followSetBean = followSet.get(j);
                                    if (null != followSetBean) {
                                        if (null != followSetBean.getDto()) {
                                            seqNo++;
                                            itemSetRunningIndex++;
                                            dtoList.add(ItemMasterDtoToTxSalesDetailDto.getTxSalesDetailDto(
                                                    followSetBean.getDto(),
                                                    followSetBean.isItemOnHold(),
                                                    seqNo, itemOrderRunningIndex,
                                                    itemSetRunningIndex, "2"));
                                            followSetModifier = followSetBean
                                                    .getModifier();
                                            if (null != followSetModifier
                                                    && followSetModifier.size() > 0) {
                                                for (int k = 0; k < followSetModifier
                                                        .size(); k++) {
                                                    followSetModifierBean = followSetModifier
                                                            .get(k);
                                                    if (null != followSetModifierBean) {
                                                        if (null != followSetModifierBean
                                                                .getDto()) {
                                                            seqNo++;
                                                            dtoList.add(ItemMasterDtoToTxSalesDetailDto
                                                                    .getTxSalesDetailDto(
                                                                            followSetModifierBean
                                                                                    .getDto(),
                                                                            followSetModifierBean
                                                                                    .isItemOnHold(),
                                                                            seqNo,
                                                                            itemOrderRunningIndex,
                                                                            itemSetRunningIndex,
                                                                            "3"));
                                                        }
                                                        followSetModifierBean = null;
                                                    }
                                                }
                                                followSetModifier = null;
                                            }
                                        }
                                        followSetBean = null;
                                    }
                                }
                                followSet = null;
                            }
                        }
                    }
                    bean = null;
                }
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }

        return dtoList;
    }

	private void commitOrderList() {

        try {
            List<TxSalesDetailDto> dtoList = reassignSeqIndxToOrderList();

            if (null == dtoList || dtoList.size() <= 0) {
                return;
            } else {
                commitData(dtoList, true);
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void commitData(List<TxSalesDetailDto> dtoList, final boolean isShow) {
		SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
				SoapUtils.SAVE_TX_SALES);

        try {
            AttributeInfo tem = new AttributeInfo();
            tem.setName("xmlns:tem");
            tem.setValue(SoapUtils.TARGET_NAMESPACE);
            mSoapObject.addAttribute(tem);

            AttributeInfo pos = new AttributeInfo();
            pos.setName("xmlns:pos");
            pos.setValue(SoapUtils.POS_NAMESPACE);
            mSoapObject.addAttribute(pos);

            mSoapObject.addSoapObject(FormatCommitSoapObject
                    .getFormatTxSalesHeaderDto(ConstantUtils.mTxSalesHearderDto,
                            ConstantUtils.mTxPaymentDto,
                            ConstantUtils.mTxSalesDetailDtoNormal));
            mSoapObject.addSoapObject(FormatCommitSoapObject
                    .getFormatTxSalesDetailDtoList(dtoList, true));

            mSoapObject.addSoapObject(FormatCommitSoapObject.getFormatUserInfo());

            mSoapObject.addProperty("tem:" + ConstantUtils.IS_INVOICE_PRINT_PENDING, "true");


            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.SAVE_TX_SALES, new HandlerCallBack() {

                @Override
                public void handleFinish(HashMap<String, Object> result) {
                    if (HandleHttpRequestResult.isError(
                            FoodCategoryActivity.this, isShow,
                            SoapUtils.SAVE_TX_SALES, result)) {
                        return;
                    }
                    CommonUtils.showToast(FoodCategoryActivity.this,
                            getString(R.string.commit_order_success));
                    //ConstantUtils.isSubmit = true;

                    //finish();
                    setTableStatus(ConstantUtils.TABLE_STATUS_ORDER_COMPLETE);
                    CommonUtils.changeActivity(FoodCategoryActivity.this,
                            TableListFragmentActivity.class);
                }

                @Override
                public void handleFailure() {

                }
            }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        setTableStatus(ConstantUtils.originalTableStatusId);
        finish();
    }
}
