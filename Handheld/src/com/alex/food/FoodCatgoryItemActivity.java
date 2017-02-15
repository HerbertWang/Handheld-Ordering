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
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alex.food.adapter.CategoryAdapter;
import com.alex.food.adapter.FoodCategoryItemAdapter;
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
import com.alex.food.utils.HandleHttpRequestResult;
import com.alex.food.utils.HandlerOrderListCallBack;
import com.alex.food.utils.ItemMasterDtoToTxSalesDetailDto;
import com.alex.food.utils.OrderListUtils;
import com.alex.food.utils.SoapUtils;
import com.alex.food.view.Configure;
import com.alex.food.view.ScrollLayout;
import com.alex.food.view.ScrollLayout.PageListener;

/**
 * 
 * @author ALEX
 * 
 */
public class FoodCatgoryItemActivity extends Activity {
	private Button btnOverOperation = null;
	private Button btnBack = null;
	private GridView topGridView = null;
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
	private Intent intent = null;
	private String categoryId = null;
	private String isSmartCategory = null;
	private List<ItemMasterDto> topList = new ArrayList<ItemMasterDto>();
	private List<ItemMasterDto> bottomList = new ArrayList<ItemMasterDto>();

	private PopupWindow pop = null;
	private View proView = null;
	private TextView tvErrorTitle = null;
	private TextView tvErrorPrompt = null;
	private Button btnErrorConfirm = null;
	private Button btnErrorCancel = null;
	private RelativeLayout relativeLayout = null;

	private LinearLayout layoutModify = null;
	private GridView bottomGridView = null;
	private Button btnConfirm = null;

	private ListView listView = null;
	private OrderListAdapter mOrderListAdapter = null;

	private int seq = 0;

	private int currentAllCount;
	private boolean isClick = true;
	private int currentPosition;
	private int currentCount;
	private ItemMasterDto currentDto;
	private String autoRedirectToModifier = "false";
	private FoodCategoryItemAdapter bottomAdapter = null;
	private final String LOGICE = "logicE";
	private final String LOGICF = "logicF";
	private String parentItemId = "";
	private String logicStatus = "";
	private final String JIAOQI = "jiaoqi";
	private final String QICAI = "qicai";

	private CategoryDto orderParentCategoryDto = new CategoryDto();
	private List<CategoryDto> categoryList = new ArrayList<CategoryDto>();
	private List<List<CategoryDto>> categoryLists = new ArrayList<List<CategoryDto>>();
	private ScrollLayout scrollLayout = null;
	private int pageSize = 20;
	private RelativeLayout layoutGridView = null;

    private OrderListBean selectedOrderListBean;
    private OrderListBean selectedParentOrderListBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food_category_item_activity);
		ComponentsManager.getComponentManager().pushComponent(this);

		initOrderList();
		initViews();
	}

	/**
	 * 鍒濆鍖栫粍浠�
	 */
	private void initViews() {
		layoutGridView = (RelativeLayout) findViewById(R.id.layoutGridView);
		scrollLayout = (ScrollLayout) findViewById(R.id.scrollLayout);
		topGridView = (GridView) findViewById(R.id.topGridView);

		btnOverOperation = (Button) findViewById(R.id.btnOverOperation);
		btnBack = (Button) findViewById(R.id.btnBack);
		bottomGridView = (GridView) findViewById(R.id.bottomGridView);
		btnConfirm = (Button) findViewById(R.id.btnConfirm);
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
		btnConfirm.setOnClickListener(listener);
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

		intent = getIntent();
		if (null != intent) {
			categoryId = intent.getStringExtra("categoryId");
			isSmartCategory = intent.getStringExtra("isSmartCategory");
		}
		if (!TextUtils.isEmpty(categoryId)) {
			getAvailableItemList(true, categoryId, isSmartCategory);
		}

		initProPopupWindow();
		// 20150328 - no need to calculate the total
		// setTotalPrice();

		OrderListUtils.setOrderListItemIsSelectFalse();
	}

	/**
	 * 鍒濆鍖栬鍗�
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
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 鍒濆鍖栨彁绀哄脊鍑烘
	 */
	private void initProPopupWindow() {
        try {
            relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
            proView = getLayoutInflater().inflate(R.layout.pop_prompt, null);
            tvErrorTitle = (TextView) proView.findViewById(R.id.tvTitle);
            tvErrorPrompt = (TextView) proView.findViewById(R.id.tvPrompt);
            btnErrorConfirm = (Button) proView.findViewById(R.id.btnYes);
            btnErrorCancel = (Button) proView.findViewById(R.id.btnNo);

            btnErrorConfirm.setOnClickListener(listener);
            btnErrorCancel.setOnClickListener(listener);
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 鏄剧ず寮瑰嚭妗�
	 * 
	 * @param text
	 */
	private void showPopupWindow(String text) {
        try {
            closePop();
            pop = new PopupWindow(proView, LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT, true);
            pop.setOutsideTouchable(true);
            pop.setBackgroundDrawable(new BitmapDrawable());
            pop.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

            tvErrorPrompt.setText(text);
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 鍏抽棴寮瑰嚭妗�
	 */
	private void closePop() {
		if (null != pop && pop.isShowing()) {
			pop.dismiss();
		}
	}

	/**
	 * 鐐瑰嚮浜嬩欢
	 */
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
            try {
                switch (v.getId()) {
                case R.id.btnOverOperation:
setTableStatus(ConstantUtils.originalTableStatusId);
CommonUtils.changeActivity(FoodCatgoryItemActivity.this,
TableListFragmentActivity.class);
                    // 20150331 Michael - Directly exit to the table list
                    //finish();
                    break;
                case R.id.btnBack:
                    finish();
                    break;
                case R.id.btnConfirm:
                    confirmChoose();
                    break;
                case R.id.btnkeyPan:
                    if (isShow) {
                        calculateLayout.setVisibility(View.VISIBLE);
                        changeStatus(false);
                    } else {
                        setViewGone();
                    }
                    break;
                case R.id.btnSendOrder:
                    commitOrderList();
                    break;
                case R.id.linearlayoutKeyPan:
                    setViewGone();
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
                case R.id.btnYes:
                    closePop();
                    break;
                case R.id.btnNo:
                    closePop();
                    break;
                }
            } catch (Exception e) {
                CommonUtils.showToast(FoodCatgoryItemActivity.this,
                        getString(R.string.remote_server_error));
            }
        }
	};

	/**
	 * 纭鎸夐挳(闈炲脊鍑烘鐨勭‘璁ゆ寜閽�)
	 */
	private void confirmChoose() {
        try {
            if (null == bottomList || bottomList.size() <= 0) {
                return;
            }
            bottomList.get(currentPosition).setClick(false);
            currentDto = bottomList.get(currentPosition);
            if(null != currentDto) {
                if (LOGICE.equals(logicStatus)) {
                    getNextLevelFollowSetItemList(true, parentItemId,
                            String.valueOf(++seq), "", "", logicStatus, "");
                } else if (LOGICF.equals(logicStatus)) {
                    if (TextUtils.isEmpty(currentDto.getLinkedGroupHeaderId())) {
                        return;
                    }
                    getAvailableModifierItemList(true,
                            currentDto.getLinkedGroupHeaderId(), logicStatus,
                            logicStatus);
                }
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 寮瑰嚭妗嗘樉绀烘垨鑰呴殣钘忓悗鏀瑰彉鍏朵粬鎺т欢鐨勭劍鐐�
	 * 
	 * @param status
	 */
	private void changeStatus(boolean status) {
		isShow = status;
		btnOverOperation.setEnabled(status);
		btnBack.setEnabled(status);
		btnkeyPan.setEnabled(status);
		btnSendOrder.setEnabled(status);
		tvNumber.setText("");
	}

	/**
	 * 璁剧疆寮瑰嚭妗嗘暟瀛�
	 * 
	 * @param number
	 */
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
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 璇锋眰AvailableItemList鏁版嵁
	 * 
	 * @param isShow
	 */
	private void getAvailableItemList(final boolean isShow, String categoryId,
			String isSmartCategory) {
        try {
            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.GET_AVAILABLE_ITEM_LIST);

            mSoapObject.addProperty(ConstantUtils.ACCOUNT_ID,
                    ConstantUtils.userInfo.getAccountId());
            mSoapObject.addProperty(ConstantUtils.SHOP_ID,
                    ConstantUtils.userInfo.getShopId());
            mSoapObject.addProperty(ConstantUtils.CATEGORY_ID, categoryId);
            mSoapObject.addProperty(ConstantUtils.IS_SMART_CATEGORY,
                    isSmartCategory);

            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.GET_AVAILABLE_ITEM_LIST, new HandlerCallBack() {

                        @Override
                        public void handleFinish(HashMap<String, Object> result) {
                            try {
                                if (HandleHttpRequestResult.isError(
                                        FoodCatgoryItemActivity.this, isShow,
                                        SoapUtils.GET_AVAILABLE_ITEM_LIST, result)) {
                                    return;
                                }

                                SoapObject object = HandleHttpRequestResult
                                        .getResultSoapObject(result);

                                if (null != object
                                        && 0 != object.getPropertyCount()) {
                                    parseCategoryData(object);
                                } else {
                                    // showPopupWindow(getString(R.string.no_data));
                                    CommonUtils.showToast(
                                            FoodCatgoryItemActivity.this,
                                            getString(R.string.no_data));
                                }
                            } catch (Exception e) {
                                // showPopupWindow(getString(R.string.remote_server_error));
                                CommonUtils.showToast(FoodCatgoryItemActivity.this,
                                        getString(R.string.remote_server_error));
                            }
                        }

                        @Override
                        public void handleFailure() {
                            // showPopupWindow(getString(R.string.remote_server_error));
                            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                                    getString(R.string.remote_server_error));
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 瑙ｆ瀽杩斿洖鍙傛暟
	 * 
	 * @param soapObject
	 */
	private void parseCategoryData(SoapObject soapObject) {
        try {
            if (null != topList) {
                topList = null;
                topList = new ArrayList<ItemMasterDto>();
            }
            SoapObject mSoapObject = null;
            for (int i = 0; i < soapObject.getPropertyCount(); i++) {
                mSoapObject = (SoapObject) soapObject.getProperty(i);
                if (null != mSoapObject) {
                    topList.add(GetItemMasterDto.getItemMasterDto(mSoapObject));
                }
            }
            setTopGridView();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 鏄剧ず绗竴琛�
	 * 
	 * @param
	 */
	private void setTopGridView() {
        try {
            FoodCategoryItemAdapter adapter = new FoodCategoryItemAdapter(this,
                    topList);
            topGridView.setAdapter(adapter);
            topGridView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                        int position, long arg3) {
                    if (!isShow) {
                        return;
                    }
                    //changeOrderListSelectFalse();
                    seq = 0;
                    if (!TextUtils.isEmpty(topList.get(position).getIsOutOfStock())) {
                        if ("true".equals(topList.get(position).getIsOutOfStock())) {
                            showPopupWindow(getString(R.string.already_clean));
                        } else if ("false".equals(topList.get(position)
                                .getIsOutOfStock())) {
                            currentCount = 0;
                            autoRedirectToModifier = topList.get(position)
                                    .getAutoRedirectToModifier();
                            parentItemId = topList.get(position).getItemId();

                            if (!TextUtils.isEmpty(topList.get(position)
                                    .getIsFollowSet())) {
                                if ("true".equals(topList.get(position)
                                        .getIsFollowSet())) {
                                    isLogicC(null, topList.get(position));
                                    return;
                                }
                            }
                            if (!TextUtils.isEmpty(topList.get(position)
                                    .getIsModifier())) {
                                if ("true".equals(topList.get(position)
                                        .getIsModifier())) {
                                    isLogicD(topList.get(position));
                                    return;
                                }
                            }

                            if (!TextUtils.isEmpty(topList.get(position)
                                    .getIsFollowSet())
                                    && !TextUtils.isEmpty(topList.get(position)
                                            .getIsModifier())) {
                                if ("false".equals(topList.get(position)
                                        .getIsFollowSet())
                                        && "false".equals(topList.get(position)
                                                .getIsModifier())) {
                                    isLogicB(topList.get(position));
                                    return;
                                }
                            }
                        }
                    }
                }
            });
            layoutModify.setVisibility(View.GONE);
            if (null != bottomList) {
                bottomList.clear();
            }
            if (null != bottomAdapter) {
                bottomAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 閫昏緫B
	 * 
	 * @param dto
	 */
	private void isLogicB(ItemMasterDto dto) {
        try {
            layoutModify.setVisibility(View.GONE);
            addDataToOrderList(dto);

            if (!TextUtils.isEmpty(dto.getAutoRedirectToModifier())) {
                if ("true".equals(dto.getAutoRedirectToModifier())) {
                    logicStatus = LOGICF;
                    getAvailableModifierItemList(true,
                            dto.getModifierGroupHeaderId(), logicStatus, "");
                } else if ("false".equals(dto.getAutoRedirectToModifier())) {
                    logicStatus = LOGICE;
                    getNextLevelFollowSetItemList(true, dto.getItemId(),
                            String.valueOf(seq), dto.getIsFollowSetDynamic(),
                            dto.getIsFollowSetStandard(), logicStatus, "");
                }
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 閫昏緫C
	 * 
	 * @param dto
	 */
	private void isLogicC(List<ItemMasterDto> dtoList, ItemMasterDto dto) {
        try {
            layoutModify.setVisibility(View.GONE);
            if (null != dtoList && dtoList.size() > 0) {
                for (int i = 0; i < dtoList.size(); i++) {
                    addItemMasterDtoToOrderList(dtoList.get(i),
                            ConstantUtils.FOLLOWSET);
                }
            } else if (null != dto) {
                addItemMasterDtoToOrderList(dto, ConstantUtils.FOLLOWSET);
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 閫昏緫D
	 * 
	 * @param dto
	 */
	private void isLogicD(ItemMasterDto dto) {
        try {
            layoutModify.setVisibility(View.GONE);
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                return;
            }
            addItemMasterDtoToOrderList(dto, ConstantUtils.MODIFIER);
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void addItemMasterDtoToOrderList(ItemMasterDto dto, String status) {
        try {
            OrderListBean bean = selectedOrderListBean;
            if (null == bean || !bean.isBg()) {
                return;
            }
            if (bean.isSelect()) {
                if (ConstantUtils.MODIFIER.equals(status)) {
                    setItemModifier(bean, dto);
                } else if (ConstantUtils.FOLLOWSET.equals(status)) {
                    setItemFollowSet(bean, dto);
                }
            } else {
                if (ConstantUtils.MODIFIER.equals(status)) {
                    addModifierToOrderList(bean, dto);
                } else if (ConstantUtils.FOLLOWSET.equals(status)) {
                    setItemFollowSet(bean, dto);
                }
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 鍒ゆ柇楂樹寒鐨勬槸Modifier杩樻槸FollowSet,濡傛灉鏄疢odifier灏辩洿鎺ュ皢Modifier娣诲姞鍒扮埗绫婚噷闈�,
	 * 濡傛灉鏄疐ollowSet灏卞皢Modifier娣诲姞鍒癋ollowSet閲岄潰
	 * 
	 * @param bean
	 * @param dto
	 */
	private void addModifierToOrderList(OrderListBean bean, ItemMasterDto dto) {
        // 20150424 - Michael - Only add modifier to newly added item
        try {
            if (bean.isBg()) {
                setItemModifier(bean, dto);
            } else {
                // 20150424 - Michael - Do nothing
                /*boolean isLight = false;
                List<OrderListBean> modifier = null;
                OrderListBean modifierBean = null;
                if (null != bean.getModifier() && bean.getModifier().size() > 0) {
                    modifier = bean.getModifier();
                    for (int i = 0; i < modifier.size(); i++) {
                        modifierBean = modifier.get(i);
                        if (null != modifierBean) {
                            if (modifierBean.isLight() && modifierBean.isBg()) {
                                isLight = true;
                                break;
                            }
                            modifierBean = null;
                        }
                    }
                    modifier = null;
                    if (isLight) {
                        setItemModifierToOrderList(bean, dto);
                    }
                } else if (null != bean.getFollowSet()
                        && bean.getFollowSet().size() > 0) {
                    addModifierToFollowSet(bean, dto);
                }*/
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 娣诲姞Modifier鍒癋ollowSet涓嬮潰
	 * 
	 * @param bean
	 * @param dto
	 */
	private void addModifierToFollowSet(OrderListBean bean, ItemMasterDto dto) {
        try {
            boolean isLight = false;
            List<OrderListBean> followSet = null;
            OrderListBean followSetBean = null;
            if (null != bean.getFollowSet() && bean.getFollowSet().size() > 0) {
                followSet = bean.getFollowSet();
                for (int i = 0; i < followSet.size(); i++) {
                    followSetBean = followSet.get(i);
                    if (null != followSetBean) {
                        if (followSetBean.isLight() && followSetBean.isBg()) {
                            isLight = true;
                            break;
                        }
                        followSetBean = null;
                    }
                }
                followSet = null;
            }
            if (isLight) {
                if (null != bean.getFollowSet() && bean.getFollowSet().size() > 0) {
                    followSet = bean.getFollowSet();
                    followSetBean = followSet.get(0);
                    List<OrderListBean> followSetModifier = null;
                    OrderListBean followSetModifierBean = null;
                    if (null != followSetBean) {
                        followSetModifier = followSetBean.getModifier();
                        if (null != followSetModifier
                                && followSetModifier.size() > 0) {
                            boolean isTrue = false;
                            int hasPosition = -1;
                            String qty = "1";
                            for (int i = 0; i < followSetModifier.size(); i++) {
                                if (null != followSetModifier.get(i)
                                        && null != followSetModifier.get(i)
                                                .getDto()) {
                                    if (dto.getItemId().equals(
                                            followSetModifier.get(i).getDto()
                                                    .getItemId())) {
                                        isTrue = true;
                                        hasPosition = i;
                                        qty = followSetModifier.get(i).getDto()
                                                .getItemQty();
                                        break;
                                    }
                                }
                            }
                            if (isTrue) {
                                followSetModifier.remove(hasPosition);
                                dto.setItemQty(String.valueOf(Integer.valueOf(qty) + 1));
                            } else {
                                dto.setItemQty("1");
                            }
                            followSetModifierBean = new OrderListBean();
                            followSetModifierBean.setNumber(1);
                            followSetModifierBean.setBg(true);
                            followSetModifierBean.setLight(true);
                            followSetModifierBean.setDto(dto);
                            followSetModifier.add(0, followSetModifierBean);
                            followSetModifierBean = null;
                            for (int i = 1; i < followSetModifier.size(); i++) {
                                followSetModifierBean = followSetModifier.get(i);
                                if (null != followSetModifierBean) {
                                    followSetModifierBean.setNumber(i + 1);
                                    followSetModifierBean.setLight(false);
                                    followSetModifierBean.setBg(false);
                                    followSetModifier.set(i, followSetModifierBean);
                                    followSetModifierBean = null;
                                }
                            }
                            followSetBean.setBg(false);
                            followSetBean.setLight(true);
                            followSetBean.setModifier(followSetModifier);
                            followSet.set(0, followSetBean);
                            bean.setFollowSet(followSet);
                            ConstantUtils.orderList.set(0, bean);
                            followSetModifier = null;
                            followSetBean = null;
                            followSet = null;
                            bean = null;
                            mOrderListAdapter.notifyDataSetChanged();
                            // 20150503 - Michael
                            listViewScrollToSelectedItem();
                            setTotalPrice();
                        } else {
                            followSetModifier = new ArrayList<OrderListBean>();
                            followSetModifierBean = getBean(dto, "1");
                            followSetModifier.add(followSetModifierBean);
                            followSetBean.setModifier(followSetModifier);
                            followSetBean.setBg(false);
                            followSetBean.setLight(true);
                            followSet.set(0, followSetBean);
                            bean.setFollowSet(followSet);
                            ConstantUtils.orderList.set(0, bean);
                            followSetModifierBean = null;
                            followSetModifier = null;
                            followSetBean = null;
                            followSet = null;
                            bean = null;
                            mOrderListAdapter.notifyDataSetChanged();
                            // 20150503 - Michael
                            listViewScrollToSelectedItem();
                            setTotalPrice();
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 璁剧疆绗竴椤圭殑Modifier
	 * 
	 * @param bean
	 *            璁㈠崟鍒楄〃鐨勭涓�椤�
	 * @param dto
	 *            闇�瑕佹坊鍔犵殑鍊�
	 */
	private void setItemModifier(OrderListBean bean, ItemMasterDto dto) {
        try {
            if (null != bean.getModifier() && bean.getModifier().size() > 0) {
                setItemModifierToOrderList(bean, dto);
            } else {
                List<OrderListBean> mList = new ArrayList<OrderListBean>();
                OrderListBean mBean = getBean(dto, String.valueOf(bean.getDto().getItemQty()));
                mBean.setBg(true);
                mBean.setSelect(true);
                mList.add(mBean);
                bean.setModifier(mList);

                //bean.setBg(false);
                // 20150329 Michael - bug, the modified bean may not be the first one
                //ConstantUtils.orderList.set(0, bean);
                mOrderListAdapter.notifyDataSetChanged();
                // 20150503 - Michael
                listViewScrollToSelectedItem();

                //setTotalPrice();
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void setItemModifierToOrderList(OrderListBean bean,
			ItemMasterDto dto) {
        try {
            List<OrderListBean> mList = bean.getModifier();
            if (null != mList && mList.size() > 0) {
                boolean isTrue = false;
                int hasPosition = -1;
                /*for (int i = 0; i < mList.size(); i++) {
                    if (null != mList.get(i) && null != mList.get(i).getDto()) {
                        if (dto.getItemId().equals(
                                mList.get(i).getDto().getItemId())) {
                            isTrue = true;
                            hasPosition = i;
                            qty = mList.get(i).getDto().getItemQty();
                            break;
                        }
                    }
                }
                if (isTrue) {
                    mList.remove(hasPosition);
                    dto.setItemQty(String.valueOf(Integer.valueOf(qty) + 1));
                } else {
                    dto.setItemQty("1");
                }*/
                dto.setItemQty(String.valueOf(bean.getDto().getItemQty()));
                OrderListBean firstBean = new OrderListBean();
                firstBean.setNumber(1);
                firstBean.setBg(true);
                //firstBean.setLight(true);
                firstBean.setSelect(true);
                firstBean.setDto(dto);
// 20150330 Michael
                //mList.add(0, firstBean);
                int nextModifierPosition = mList.size();
                mList.add(nextModifierPosition, firstBean);
                firstBean = null;
                OrderListBean mBean = null;
                for (int i = 0; i < mList.size(); i++) {
                    if (i != nextModifierPosition) {
                        mBean = mList.get(i);
                        if (null != mBean) {
                            mBean.setNumber(i + 1);
//mBean.setLight(false);
//mBean.setBg(false);
                            mBean.setSelect(false);
                            mList.set(i, mBean);
                            mBean = null;
                        }
                    }

                }
                bean.setSelect(false);
                bean.setModifier(mList);
// 20150329 Michael - bug, the selected bean may not be the first one
                //ConstantUtils.orderList.set(0, bean);
                mOrderListAdapter.notifyDataSetChanged();
                // 20150503 - Michael
                listViewScrollToSelectedItem();
                //setTotalPrice();
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 璁剧疆绗竴椤圭殑FollowSet
	 * 
	 * @param bean
	 *            璁㈠崟鍒楄〃鐨勭涓�椤�
	 * @param dto
	 *            闇�瑕佹坊鍔犵殑Item
	 */
	private void setItemFollowSet(OrderListBean bean, ItemMasterDto dto) {
        try {
            if (null != bean.getFollowSet() && bean.getFollowSet().size() > 0) {
                List<OrderListBean> mList = bean.getFollowSet();
                if (null != mList && mList.size() > 0) {
                    boolean isTrue = false;
                    int hasPosition = -1;
                    String qty = "1";
// 20150330 - Michael - disabled
                    /*for (int i = 0; i < mList.size(); i++) {
                        if (null != mList.get(i).getDto()
                                && (null == mList.get(i).getModifier() || mList
                                        .get(i).getModifier().size() <= 0)) {
                            if (dto.getItemId().equals(
                                    mList.get(i).getDto().getItemId())) {
                                isTrue = true;
                                hasPosition = i;
                                qty = mList.get(i).getDto().getItemQty();
                                break;
                            }
                        }
                    }
                    if (isTrue) {
                        mList.remove(hasPosition);
                        dto.setItemQty(String.valueOf(Integer.valueOf(qty) + 1));
                    } else {
                        dto.setItemQty("1");
                    }*/
                    dto.setItemQty(bean.getDto().getItemQty());
                    OrderListBean newBean = new OrderListBean();
                    newBean.setNumber(mList.size());
                    //newBean.setBg(true);
                    //newBean.setLight(true);
                    newBean.setSelect(true);
                    newBean.setDto(dto);
                    mList.add(mList.size(), newBean);
                    selectedOrderListBean = newBean;
                    selectedParentOrderListBean = bean;
                    newBean = null;
                    OrderListBean mBean = null;
                    for (int i = 0; i < mList.size()-1; i++) {
                        mBean = mList.get(i);
                        if (null != mBean) {
                            //mBean.setNumber(i + 1);
                            //mBean.setLight(false);
                            //mBean.setBg(false);
                            mBean.setSelect(false);
                            mList.set(i, mBean);
                            mBean = null;
                        }
                    }
                    //bean.setBg(false);
                    bean.setSelect(false);
                    bean.setFollowSet(mList);
// 20150330 - Michael - disabled
                    //ConstantUtils.orderList.set(0, bean);
                    mOrderListAdapter.notifyDataSetChanged();
                    // 20150503 - Michael
                    listViewScrollToSelectedItem();
                    //setTotalPrice();
                }
            } else {
                List<OrderListBean> mList = new ArrayList<OrderListBean>();
                OrderListBean mBean = getBean(dto, "1");
                mList.add(mBean);
                bean.setFollowSet(mList);
                //bean.setBg(false);
                bean.setSelect(false);
                mBean.setSelect(true);
//ConstantUtils.orderList.set(0, bean);
                //ConstantUtils.orderList.set(0, bean);
                mOrderListAdapter.notifyDataSetChanged();
                // 20150503 - Michael
                listViewScrollToSelectedItem();
                //setTotalPrice();
                selectedOrderListBean = mBean;
                selectedParentOrderListBean = bean;
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 鑾峰彇Modifier鏁版嵁
	 * 
	 * @param isShow
	 *            鏄惁鏄剧ず鍔犺浇妗�
	 * @param modifierGroupHeaderId
	 *            modifierGroupHeaderId
	 * @param mothed
	 *            浠庡摢涓�昏緫鏉�
	 * @param goTo
	 *            鍒板摢涓�昏緫鍘�
	 * 
	 */
	private void getAvailableModifierItemList(final boolean isShow,
			String modifierGroupHeaderId, final String mothed, final String goTo) {
        try {
            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.GET_AVAILABLE_MODIFIER_ITEM_LIST);

            mSoapObject.addProperty(ConstantUtils.ACCOUNT_ID,
                    ConstantUtils.userInfo.getAccountId());
            mSoapObject.addProperty(ConstantUtils.SHOP_ID,
                    ConstantUtils.userInfo.getShopId());
            mSoapObject.addProperty("modifierGroupHeaderId", modifierGroupHeaderId);

            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.GET_AVAILABLE_MODIFIER_ITEM_LIST,
                    new HandlerCallBack() {

                        @Override
                        public void handleFinish(HashMap<String, Object> result) {
                            if (HandleHttpRequestResult.isError(
                                    FoodCatgoryItemActivity.this, isShow,
                                    SoapUtils.GET_AVAILABLE_MODIFIER_ITEM_LIST,
                                    result)) {
                                return;
                            }
                            SoapObject object = HandleHttpRequestResult
                                    .getResultSoapObject(result);
                            if (null != object && 0 != object.getPropertyCount()) {
                                parseSubCategoryData(object, "", "", mothed, goTo);
                            }

// 20150415 Michael - reset the currentcount for modifier
                            currentCount = 0;
                        }

                        @Override
                        public void handleFailure() {
                            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                                    getString(R.string.remote_server_error));
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 鑾峰彇FollowSet鐨勫��
	 * 
	 * @param isShow
	 *            鏄惁鏄剧ず鍔犺浇妗�
	 * @param itemId
	 * @param seq
	 * @param isFollowSetDynamic
	 * @param isFollowSetStandard
	 * @param mothed
	 *            浠庡摢涓�昏緫鏉�
	 * @param goTo
	 *            鍒板摢涓�昏緫鍘�
	 */
	private void getNextLevelFollowSetItemList(final boolean isShow,
			String itemId, String seq, final String isFollowSetDynamic,
			final String isFollowSetStandard, final String mothed,
			final String goTo) {
        try {
            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.GET_NEXT_LEVEL_FOLLOW_SET_ITEM_LIST);

            mSoapObject.addProperty(ConstantUtils.ACCOUNT_ID,
                    ConstantUtils.userInfo.getAccountId());
            mSoapObject.addProperty(ConstantUtils.SHOP_ID,
                    ConstantUtils.userInfo.getShopId());
            mSoapObject.addProperty("itemId", itemId);
            mSoapObject.addProperty("seq", seq);

            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.GET_NEXT_LEVEL_FOLLOW_SET_ITEM_LIST,
                    new HandlerCallBack() {

                        @Override
                        public void handleFinish(HashMap<String, Object> result) {
                            if (HandleHttpRequestResult.isError(
                                    FoodCatgoryItemActivity.this, isShow,
                                    SoapUtils.GET_NEXT_LEVEL_FOLLOW_SET_ITEM_LIST,
                                    result)) {
                                return;
                            }
                            SoapObject object = HandleHttpRequestResult
                                    .getResultSoapObject(result);
                            if (null != object && 0 != object.getPropertyCount()) {
                                parseSubCategoryData(object, isFollowSetDynamic,
                                        isFollowSetStandard, mothed, goTo);
                            }
                        }

                        @Override
                        public void handleFailure() {
                            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                                    getString(R.string.remote_server_error));
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 瑙ｆ瀽鍙傛暟
	 * 
	 * @param soapObject
	 * @param isFollowSetDynamic
	 * @param isFollowSetStandard
	 * @param mothed
	 * @param goTo
	 */
	private void parseSubCategoryData(SoapObject soapObject,
			String isFollowSetDynamic, String isFollowSetStandard,
			String mothed, String goTo) {
        try {
            if (null != bottomList) {
                bottomList = null;
                bottomList = new ArrayList<ItemMasterDto>();
            }
            SoapObject mSoapObject = null;
            for (int i = 0; i < soapObject.getPropertyCount(); i++) {
                mSoapObject = (SoapObject) soapObject.getProperty(i);
                if (null != mSoapObject) {
                    bottomList.add(GetItemMasterDto.getItemMasterDto(mSoapObject));
                }
            }
            if (TextUtils.isEmpty(goTo)) {
                if (null != bottomList && bottomList.size() > 0) {
                    if ("false".equals(autoRedirectToModifier)) {
                        if ("true".equals(isFollowSetStandard)) {
                            addFollowSetToOrderList(bottomList);
                            // isLogicC(bottomList, null);
                        } else if ("true".equals(isFollowSetDynamic)) {
                            setBottomGridView(mothed);
                        }
                    } else if ("true".equals(autoRedirectToModifier)) {
                        setBottomGridView(mothed);
                    }
                }
            } else {
                setBottomGridView(goTo);
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 鏄剧ず绗簩琛�
	 * 
	 * @param mothed
	 */
	private void setBottomGridView(final String mothed) {
        try {
            layoutModify.setVisibility(View.VISIBLE);
            bottomAdapter = new FoodCategoryItemAdapter(this, bottomList);
            bottomGridView.setAdapter(bottomAdapter);
            bottomGridView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {

                    changeOrderListSelectFalse();

                    if (SoapUtils.Get_AVAILABLE_ITEM_CATEGORY_LIST.equals(mothed)) {
                        addGetAvailableItemCategoryListToOrderList(position);
                    } else {
                        currentPosition = position;
                        currentAllCount = Integer.valueOf(bottomList.get(position)
                                .getMaxModifierSelectCount());
// 20150415 Michael DO NOT reset the current count
                        // currentCount = bottomList.get(position).getCurrentCount();
                        isClick = bottomList.get(position).isClick();
                        currentDto = bottomList.get(position);
                        if (isClick) {
                            if (0 == currentAllCount) {
                                // unlimited
                                currentCount++;
                                logicEF(bottomList.get(position), mothed,
                                        currentAllCount, currentCount);
                            } else if (1 == currentAllCount) {
                                // one
                                currentCount++;
                                currentDto.setCurrentCount(1);
                                currentDto.setClick(false);
                                bottomList.set(position, currentDto);
                                logicEF(bottomList.get(position), mothed,
                                        currentAllCount, currentCount);
                                bottomAdapter.notifyDataSetChanged();
                            } else {
                                // max count
                                if (currentCount <= currentAllCount) {
                                    currentCount++;
                                    currentDto.setCurrentCount(currentCount);
                                    currentDto.setClick(true);
                                    bottomList.set(position, currentDto);
                                    logicEF(bottomList.get(position), mothed,
                                            currentAllCount, currentCount);
                                    bottomAdapter.notifyDataSetChanged();
                                } else {
                                    currentDto.setCurrentCount(currentAllCount);
                                    currentDto.setClick(false);
                                    bottomList.set(position, currentDto);
                                    bottomAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void logicEF(ItemMasterDto dto, String mothed, int maxCount,
			int currentCount) {

        try {
            if ("true".equals(dto.getIsFollowSet())) {
                isLogicE(dto, maxCount, currentCount);
            } else if ("true".equals(dto.getIsModifier())) {
                isLogicF(dto, maxCount, currentCount);
            }
//		if (LOGICE.equals(mothed)) {
//			isLogicE(dto, maxCount, currentCount);
//		} else if (LOGICF.equals(mothed)) {
//			isLogicF(dto, maxCount, currentCount);
//		}
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 閫昏緫E
	 * 
	 * @param dto
	 *            姣忎竴椤圭殑鍊�
	 * @param maxCount
	 *            鏈�澶ф暟鍊�
	 * @param currentCount
	 *            褰撳墠鏁板��
	 */
	private void isLogicE(ItemMasterDto dto, int maxCount, int currentCount) {
        try {
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                return;
            }
            setItemFollowSet(selectedOrderListBean, dto);
            if (1 == maxCount) {
                if ("true".equals(dto.getAutoRedirectToModifier())) {
                    logicStatus = LOGICF;
                    getAvailableModifierItemList(true,
                            dto.getModifierGroupHeaderId(), logicStatus,
                            logicStatus);
                } else if ("false".equals(dto.getAutoRedirectToModifier())) {
                    logicStatus = LOGICE;
                    getNextLevelFollowSetItemList(true, parentItemId,
                            String.valueOf(++seq), "", "", logicStatus, "");
                }
            } else if (0 != maxCount) {
                if (currentCount == maxCount) {
                    logicStatus = LOGICE;
                    getNextLevelFollowSetItemList(true, parentItemId,
                            String.valueOf(++seq), "", "", logicStatus, "");
                }
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 閫昏緫F
	 * 
	 * @param dto
	 *            姣忎竴椤圭殑鍊�
	 * @param maxCount
	 *            鏈�澶ф暟鍊�
	 * @param currentCount
	 *            褰撳墠鏁板��
	 */
	private void isLogicF(ItemMasterDto dto, int maxCount, int currentCount) {
        try {
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                return;
            }
            // addModifierToOrderList(ConstantUtils.orderList.get(0), dto);
            addModifierToOrderList(selectedOrderListBean, dto);
            if (0 != maxCount && currentCount == maxCount) {
                if (null != dto.getLinkedGroupHeaderId()) {
                    logicStatus = LOGICF;
                    getAvailableModifierItemList(true, dto.getLinkedGroupHeaderId(),
                            LOGICF, LOGICF);
                } else {
                    bottomList.clear();
// 20150330 Michael - if this is a dynamic follow set and the first level modifier is full, load the follow set for user to choose
                    if ("true".equals(selectedOrderListBean.getDto().getIsFollowSetDynamic())) {
                        getNextLevelFollowSetItemList(true, selectedOrderListBean.getDto().getItemId(),
                                String.valueOf(seq), dto.getIsFollowSetDynamic(),
                                dto.getIsFollowSetStandard(), logicStatus, "");
                    }
                }

            }
            currentCount++;
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 娣诲姞涓�涓狥ollowSet鍒板凡鏈夌殑璁㈠崟鍒楄〃
	 * 
	 * @param dtoList
	 */
	private void addFollowSetToOrderList(List<ItemMasterDto> dtoList) {

        try {
            if (null == dtoList || dtoList.size() <= 0) {
                return;
            }
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                return;
            }
            OrderListBean bean = new OrderListBean();
            List<OrderListBean> beanList = new ArrayList<OrderListBean>();
            OrderListBean mBean = null;
            bean = selectedOrderListBean;

            for (int i = 0; i < dtoList.size(); i++) {
                mBean = new OrderListBean();
                mBean.setNumber(i + 1);
                mBean.setDto(dtoList.get(i));
                mBean.setBg(true);
                mBean.getDto().setItemQty(bean.getDto().getItemQty());
//mBean.setLight(true);

                if (i == dtoList.size() - 1) {
                    mBean.setSelect(true);
                } else {
                    mBean.setSelect(false);
                }
                mBean.setModifier(null);
                beanList.add(mBean);
                mBean = null;
            }
            // 20150330 Michael - get the selected bean instead of the first bean
            //bean = ConstantUtils.orderList.get(0);

            //bean.setBg(false);
            //bean.setLight(true);
            bean.setSelect(false);
            bean.setFollowSet(beanList);
            //ConstantUtils.orderList.set(0, bean);
            bean = null;
            mOrderListAdapter.notifyDataSetChanged();
            // 20150503 - Michael
            listViewScrollToSelectedItem();
            //setTotalPrice();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 娣诲姞涓�涓狽ormal鍒拌鍗曞垪琛�
	 * 
	 * @param dto
	 */
	private void addDataToOrderList(ItemMasterDto dto) {
        try {
            if (null != ConstantUtils.orderList) {
                int orderListNextPosition = ConstantUtils.orderList.size();
                if (ConstantUtils.orderList.size() <= 0) {
                    selectedOrderListBean = getBean(dto, "1");
                    ConstantUtils.orderList.add(orderListNextPosition, selectedOrderListBean);
                    selectedParentOrderListBean = selectedOrderListBean;
                    selectedParentOrderListBean.setBg(true);
                    selectedParentOrderListBean.setSelect(true);
                    //ConstantUtils.orderList.add(getBean(dto, "1"));
                } else {
                    boolean isTrue = false;
                    int hasPosition = -1;
                    String qty = "1";
                    /*for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                        if (null != ConstantUtils.orderList.get(i)
                                && null == ConstantUtils.orderList.get(i)
                                        .getDetailDto()) {
                            if ((null == ConstantUtils.orderList.get(i)
                                    .getModifier() || ConstantUtils.orderList
                                    .get(i).getModifier().size() <= 0)
                                    && (null == ConstantUtils.orderList.get(i)
                                            .getFollowSet() || ConstantUtils.orderList
                                            .get(i).getFollowSet().size() <= 0)) {
                                *//*if (dto.getItemId().equals(
                                        ConstantUtils.orderList.get(i).getDto()
                                                .getItemId())) {
                                    isTrue = true;
                                    hasPosition = i;
                                    qty = ConstantUtils.orderList.get(i).getDto()
                                            .getItemQty();
                                    break;
                                }*//*
                            }
                        }
                    }*/
                    if (isTrue) {
                        ConstantUtils.orderList.remove(hasPosition);
                        ConstantUtils.orderList.add(
                                0,
                                getBean(dto,
                                        String.valueOf(Integer.valueOf(qty) + 1)));
                    } else {
                        selectedOrderListBean = getBean(dto, "1");
                        ConstantUtils.orderList.add(orderListNextPosition, selectedOrderListBean);
                        selectedParentOrderListBean = selectedOrderListBean;
                        selectedParentOrderListBean.setBg(true);
                        selectedParentOrderListBean.setSelect(true);
                    }
// 20150423 - Michael - The below unselect all in list logic should be called from the common function

                    /*OrderListBean mBean = new OrderListBean();
                    List<OrderListBean> modifierList = null;
                    OrderListBean modifierBean = null;
                    List<OrderListBean> followSetList = null;
                    OrderListBean followSetBean = null;
                    for (int i = 1; i < ConstantUtils.orderList.size(); i++) {
if(orderListNextPosition != i) {
mBean = ConstantUtils.orderList.get(i);
mBean.setNumber(i + 1);
mBean.setBg(false);
// 20150423 - Really no idea about what "light" means, comment all of them
//mBean.setLight(false);
mBean.setSelect(false);
if (null != mBean.getModifier()
&& mBean.getModifier().size() > 0) {
modifierList = mBean.getModifier();
if (null != modifierList && modifierList.size() > 0) {
for (int j = 0; j < modifierList.size(); j++) {
modifierBean = modifierList.get(j);
if (null != modifierBean) {
//modifierBean.setLight(false);
modifierBean.setBg(false);
modifierList.set(j, modifierBean);
modifierBean = null;
}
}
mBean.setModifier(modifierList);
modifierList = null;
}

}
if (null != mBean.getFollowSet()
&& mBean.getFollowSet().size() > 0) {
followSetList = mBean.getFollowSet();
if (null != followSetList && followSetList.size() > 0) {
for (int j = 0; j < followSetList.size(); j++) {
followSetBean = followSetList.get(j);
if (null != followSetBean) {
followSetBean.setLight(false);
followSetBean.setBg(false);
followSetList.set(j, followSetBean);
followSetBean = null;
}
}
mBean.setFollowSet(followSetList);
followSetList = null;
}
}

ConstantUtils.orderList.set(i, mBean);
mBean = null;
}
                    }*/

// highlight the newly added bean
                    setNewOrderListOtherItemBgFalse(orderListNextPosition);
                    setNewOrderListCurrentItemFollowSetBgFalse(orderListNextPosition);


                }

                mOrderListAdapter.notifyDataSetChanged();
                // 20150503 - Michael
                listView.post( new Runnable() {
                    @Override
                    public void run() {
                        //call smooth scroll
                        listViewScrollToSelectedItem();
                    }
                });

                //setTotalPrice();
            }
        } catch (NumberFormatException e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 璁剧疆璁㈠崟绗竴椤圭殑鍊�
	 * 
	 * @param dto
	 * @return
	 */
	private OrderListBean getBean(ItemMasterDto dto, String qty) {
		dto.setItemQty(qty);
		OrderListBean bean = new OrderListBean();
		bean.setLight(true);
		bean.setNumber(1);
		bean.setDto(dto);
		bean.setBg(true);
		bean.setModifier(null);
		bean.setFollowSet(null);
		return bean;
	}

	/**
	 * 璁＄畻鎬讳环
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
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 璁＄畻姣忎釜鍟嗗搧鐨勪环鏍�
	 * 
	 * @param strPrice
	 *            鍗曚环
	 * @param strCount
	 *            鏁伴噺
	 * @return
	 */
	private BigDecimal getTotalPrice(String strPrice, String strCount) {
		BigDecimal price = new BigDecimal(strPrice);
		BigDecimal count = new BigDecimal(strCount);
		BigDecimal total = price.multiply(count);
		return total;
	}

	private void changeOrderListItem(int parentPosition, int position,
			int subPosition, String status) {
        try {
            //listView.setSelectionFromTop(0,0);
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

            // set the selected order list bean
            //selectedOrderListBean.setBg(true);
            //selectedOrderListBean.setLight(true);
            selectedOrderListBean = ConstantUtils.orderList.get(parentPosition);
            selectedParentOrderListBean = selectedOrderListBean;
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void changeOrderListNormalSelect(int position) {
		OrderListBean bean = null;
        try {
            for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                bean = ConstantUtils.orderList.get(i);
                if (null != bean) {
                    if (i != position) {
                        setOrderListSelect(bean, i, false);
                    } else {
                        setNewOrderListOtherItemBgFalse(i);
                        setNewOrderListCurrentItemFollowSetBgFalse(i);
                        bean.setBg(true);
                        if (bean.isSelect()) {
// 20150329 michael keep that be selected even it's already selected
                            /*bean.setSelect(false);
                            ConstantUtils.orderList.set(i, bean);*/
                        } else {
                            setOrderListSelect(bean, i, true);
                        }
                    }
                    bean = null;
                }
            }

            mOrderListAdapter.notifyDataSetChanged();
            // 20150503 - Michael
            listViewScrollToSelectedItem();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void changeOrderListSelectFalse() {
        try {
            OrderListBean bean = null;
            for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                bean = ConstantUtils.orderList.get(i);
                if (null != bean) {
                    setOrderListSelect(bean, i, false);
                    bean = null;
                }
            }
            mOrderListAdapter.notifyDataSetChanged();
            // 20150503 - Michael
            //listViewScrollToSelectedItem();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
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
            //mOrderListAdapter.notifyDataSetChanged();
            // 20150503 - Michael
            //listViewScrollToSelectedItem();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
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
                    setOrderListSelect(bean, i, false);
// 20150423 - Michael - unused code
                    /*if (i != parentPosition) {
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
                    }*/
                    bean = null;
                }

// only highlight the clicked modifier
                bean = ConstantUtils.orderList.get(parentPosition);
                if (null != bean) {
// then get the clicked modifier
                    modifierBean = bean.getModifier().get(position);
                    if (null != modifierBean) {
                        modifierBean.setSelect(true);
                    }
                }

            }

            mOrderListAdapter.notifyDataSetChanged();
            // 20150503 - Michael
            listViewScrollToSelectedItem();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
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
                                                //followSetListBean.setSelect(false);
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
                                                //followSetBean.setSelect(false);
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
            mOrderListAdapter.notifyDataSetChanged();
            // 20150503 - Michael
            listViewScrollToSelectedItem();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
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
                                                            //followSetModifierListBean.setSelect(false);
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
                                                            //followSetModifierBean.setSelect(false);
                                                        } else {
                                                            followSetModifierBean
                                                                    .setSelect(true);
                                                        }
                                                    } else {
                                                        followSetModifierBean
                                                                .setSelect(false);
                                                    }
                                                    //followSetModifierBean.setSelect(false);
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
            // 20150503 - Michael
            listViewScrollToSelectedItem();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 鍒犻櫎璁㈠崟
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
            mOrderListAdapter.notifyDataSetChanged();
            // 20150503 - Michael
            listViewScrollToSelectedItem();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void reloadOrderList() {
        try {
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                mOrderListAdapter.notifyDataSetChanged();
                // 20150503 - Michael
                //listViewScrollToSelectedItem();
                return;
            }
            for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                if (null != ConstantUtils.orderList.get(i)) {
                    ConstantUtils.orderList.get(i).setNumber(i + 1);
                }
            }
            mOrderListAdapter.notifyDataSetChanged();
            // 20150503 - Michael
            //listViewScrollToSelectedItem();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
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
            mOrderListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
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

            mOrderListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 淇敼璁㈠崟鏁伴噺
	 */
	private void alterOrderQty() {
        try {
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                return;
            }

            String number = tvNumber.getText().toString().trim();
            if("".equals(number))
            {
                return;
            }
            int intNumber = Integer.parseInt(number);
            if (TextUtils.isEmpty(number) || "0".equals(number)) {
                return;
            }

            // reassign the seq number to the current order list first
            //reassignSeqIndxToOrderList();

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

            // locate the parent of the selected bean
            //int selectedItemOrderRunningIndex = selectedOrderListBean.getDto().get
            bean = selectedParentOrderListBean;
            if (null != bean) {
                if (bean.getDetailDto() != null && !"0".equals(bean.getDetailDto().getTxSalesDetailId())) {
                    CommonUtils.showToast(this,
                            getString(R.string.submitted_not_change));
                }
                else {
                    // change the parent qty
                    bean.getDto().setItemQty(number);
                    //bean.setNumber(intNumber);

                    // change all modifier list qty
                    modifier = bean.getModifier();
                    if (null != modifier && modifier.size() > 0) {
                        for (int j = 0; j < modifier.size(); j++) {
                            modifierBean = modifier.get(j);
                            //modifierBean.setNumber(intNumber);
                            modifierBean.getDto().setItemQty(number);
                            if (null != modifierBean) {
                                modifierBean = null;
                            }
                        }
                        modifier = null;
                    }

                    // change all follow set and its modifier
                    followSet = bean.getFollowSet();
                    if (null != followSet && followSet.size() > 0) {
                        for (int j = 0; j < followSet.size(); j++) {
                            followSetBean = followSet.get(j);
                            if (null != followSetBean) {
                                //followSetBean.setNumber(intNumber);
                                followSetBean.getDto().setItemQty(number);

                                followSetModifier = followSetBean
                                        .getModifier();
                                if (null != followSetModifier
                                        && followSetModifier.size() > 0) {
                                    for (int k = 0; k < followSetModifier
                                            .size(); k++) {
                                        followSetModifierBean = followSetModifier
                                                .get(k);
                                        if (null != followSetModifierBean) {
                                            //followSetModifierBean.setNumber(intNumber);
                                            followSetModifierBean.getDto().setItemQty(number);

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
            mOrderListAdapter.notifyDataSetChanged();

            //int selectedItemOrderRunningIndex = ConstantUtils.orderList.get

		/*for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
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
		}*/
        } catch (NumberFormatException e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
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

            mSoapObject.addProperty(ConstantUtils.ACCOUNT_ID,
                    ConstantUtils.userInfo.getAccountId());
            mSoapObject.addProperty(ConstantUtils.SHOP_ID,
                    ConstantUtils.userInfo.getShopId());
            mSoapObject.addProperty("itemId", itemId);

            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.GET_ITEM_BY_ITEM_ID, new HandlerCallBack() {

                        @Override
                        public void handleFinish(HashMap<String, Object> result) {
                            try {
                                if (HandleHttpRequestResult.isError(
                                        FoodCatgoryItemActivity.this, isShow,
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
                                            FoodCatgoryItemActivity.this,
                                            getString(R.string.no_data));
                                }
                            } catch (Exception e) {
                                CommonUtils.showToast(FoodCatgoryItemActivity.this,
                                        getString(R.string.remote_server_error));
                            }
                        }

                        @Override
                        public void handleFailure() {
                            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                                    getString(R.string.remote_server_error));
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
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
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
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
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
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
                                        FoodCatgoryItemActivity.this, isShow,
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
                                    parseGetAvailableItemCategoryList(categorySoapObject);
                                } else {
                                    CommonUtils.showToast(
                                            FoodCatgoryItemActivity.this,
                                            getString(R.string.no_data));
                                }
                            } catch (Exception e) {
                                CommonUtils.showToast(FoodCatgoryItemActivity.this,
                                        getString(R.string.remote_server_error));
                            }
                        }

                        @Override
                        public void handleFailure() {
                            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                                    getString(R.string.remote_server_error));
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void parseGetAvailableItemCategoryList(SoapObject soapObject) {
        try {
            if (null != categoryList) {
                categoryList = null;
                categoryList = new ArrayList<CategoryDto>();
            }
            SoapObject mSoapObject = null;
            for (int i = 0; i < soapObject.getPropertyCount(); i++) {
                mSoapObject = (SoapObject) soapObject.getProperty(i);
                if (null != mSoapObject) {
                    categoryList.add(GetCategoryDto.getCategoryDto(mSoapObject));
                }
            }

            addViewToScrollLayout();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void addViewToScrollLayout() {
        try {
            if (null != categoryList && categoryList.size() > 0) {

                scrollLayout.setVisibility(View.VISIBLE);
                layoutGridView.setVisibility(View.GONE);

                if (categoryList.size() % pageSize == 0) {
                    Configure.curentPage = categoryList.size() / pageSize;
                } else {
                    Configure.curentPage = categoryList.size() / pageSize + 1;
                }
                if (null != categoryLists) {
                    categoryLists.clear();
                }
                for (int i = 0; i < Configure.curentPage; i++) {
                    categoryLists.add(new ArrayList<CategoryDto>());
                }
                for (int i = 0; i < categoryList.size(); i++) {
                    int page = 0;
                    page = i / pageSize;
                    categoryLists.get(page).add(categoryList.get(i));
                }
                if (null != scrollLayout) {
                    scrollLayout.removeAllViews();
                }
                for (int i = 0; i < Configure.curentPage; i++) {
                    scrollLayout.addView(getGridView(i));
                }
                scrollLayout.setPageListener(new PageListener() {

                    @Override
                    public void page(int page) {
                    }
                });
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private GridView getGridView(int i) {
		CategoryAdapter adapter = new CategoryAdapter(this,
				categoryLists.get(i));
		GridView gridView = new GridView(this);
        try {
            gridView.setAdapter(adapter);
            gridView.setNumColumns(4);
            gridView.setTag(i);
            gridView.setSelector(R.drawable.selector_null);
            gridView.setCacheColorHint(Color.TRANSPARENT);
            gridView.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        return true;
                    }
                    return false;
                }
            });
            gridView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                        int position, long arg3) {
                    if (!isShow) {
                        return;
                    }
                    int index = (Integer) arg0.getTag();
                    List<CategoryDto> temList = categoryLists.get(index);
                    if (TextUtils.isEmpty(temList.get(position).getIsTerminal())) {
                        return;
                    }
                    if ("false".equals(temList.get(position).getIsTerminal())) {
                        getGaiMaData(true, temList.get(position).getCategoryId(),
                                orderParentCategoryDto.getIsSmartCategory());
                    } else if ("true".equals(temList.get(position).getIsTerminal())) {
                        scrollLayout.setVisibility(View.GONE);
                        layoutGridView.setVisibility(View.VISIBLE);
                        getAvailableItemList(true, temList.get(position)
                                .getCategoryId(), temList.get(position)
                                .getIsSmartCategory());
                    }
                }
            });
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
        return gridView;
    }

	private void addGetAvailableItemCategoryListToOrderList(int position) {
        try {
            if (null == bottomList || null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0
                    || bottomList.size() <= 0) {
                return;
            }
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
                            modifier = bean.getModifier();
                            OrderListBean mBean = getBean(bottomList.get(position),
                                    "1");
                            if (null != modifier && modifier.size() > 0) {
                                modifier.add(0, mBean);
                                for (int j = 1; j < modifier.size(); j++) {
                                    mBean = modifier.get(j);
                                    if (null != mBean) {
                                        mBean.setNumber(i + 1);
                                        mBean.setBg(false);
                                        mBean.setLight(false);
                                        modifier.set(j, mBean);
                                        mBean = null;
                                    }
                                }
                                bean.setBg(false);
                                bean.setModifier(modifier);
                                modifier = null;
                            } else {
                                List<OrderListBean> mList = new ArrayList<OrderListBean>();
                                mList.add(mBean);
                                bean.setBg(false);
                                bean.setModifier(mList);
                            }
                            ConstantUtils.orderList.set(i, bean);
                            mOrderListAdapter.notifyDataSetChanged();
                            setTotalPrice();
                            setNewOrderListOtherItemBgFalse(i);
                            setNewOrderListCurrentItemFollowSetBgFalse(i);
                            break;
                        } else {
                            if (null != bean.getModifier()
                                    && bean.getModifier().size() > 0) {
                                modifier = bean.getModifier();
                                for (int j = 0; j < modifier.size(); j++) {
                                    modifierBean = modifier.get(j);
                                    if (null != modifierBean) {
                                        if (modifierBean.isSelect()) {
                                            OrderListBean mBean = getBean(
                                                    bottomList.get(position), "1");
                                            modifier.add(0, mBean);
                                            for (int k = 1; k < modifier.size(); k++) {
                                                mBean = modifier.get(k);
                                                if (null != mBean) {
                                                    mBean.setNumber(i + 1);
                                                    mBean.setBg(false);
                                                    mBean.setLight(false);
                                                    modifier.set(k, mBean);
                                                    mBean = null;
                                                }
                                            }
                                            bean.setBg(false);
                                            bean.setModifier(modifier);
                                            ConstantUtils.orderList.set(i, bean);
                                            mOrderListAdapter
                                                    .notifyDataSetChanged();
                                            setTotalPrice();
                                            setNewOrderListOtherItemBgFalse(i);
                                            setNewOrderListCurrentItemFollowSetBgFalse(i);
                                            break;
                                        }
                                        modifierBean = null;
                                    }
                                }
                                modifier = null;
                            }
                            if (null != bean.getFollowSet()
                                    && bean.getFollowSet().size() > 0) {
                                followSet = bean.getFollowSet();
                                for (int j = 0; j < followSet.size(); j++) {
                                    followSetBean = followSet.get(j);
                                    if (null != followSetBean) {
                                        if (followSetBean.isSelect()) {
                                            followSetModifier = followSetBean
                                                    .getModifier();
                                            if (null != followSetModifier
                                                    && followSetModifier.size() > 0) {
                                                OrderListBean mBean = getBean(
                                                        bottomList.get(position),
                                                        "1");
                                                followSet.add(0, mBean);
                                                for (int k = 1; k < followSet
                                                        .size(); k++) {
                                                    mBean = followSet.get(k);
                                                    if (null != mBean) {
                                                        mBean.setNumber(k + 1);
                                                        mBean.setBg(false);
                                                        mBean.setLight(true);
                                                        followSet.set(k, mBean);
                                                        mBean = null;
                                                    }
                                                }
                                                bean.setBg(false);
                                                bean.setFollowSet(followSet);
                                                ConstantUtils.orderList
                                                        .set(i, bean);
                                                mOrderListAdapter.notifyDataSetChanged();
                                            } else {
                                                List<OrderListBean> mList = new ArrayList<OrderListBean>();
                                                OrderListBean mBean = getBean(
                                                        bottomList.get(position),
                                                        "1");
                                                mList.add(mBean);
                                                followSetBean.setModifier(mList);
                                                followSet.set(j, followSetBean);
                                                bean.setFollowSet(followSet);
                                                ConstantUtils.orderList
                                                        .set(i, bean);
                                                mBean = null;
                                                mList = null;
                                                mOrderListAdapter.notifyDataSetChanged();
                                            }
                                            mOrderListAdapter
                                                    .notifyDataSetChanged();
                                            setTotalPrice();
                                            setNewOrderListOtherItemBgFalse(i);
                                            setNewOrderListCurrentItemModifierBgFalse(i);
                                            followSetModifier = null;
                                            followSetBean = null;
                                            break;
                                        } else {
                                            followSetModifier = followSetBean
                                                    .getModifier();
                                            boolean isSelect = false;
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
                                                if (isSelect) {
                                                    if (null != followSetModifier
                                                            && followSetModifier
                                                            .size() > 0) {
                                                        OrderListBean mBean = getBean(
                                                                bottomList
                                                                        .get(position),
                                                                "1");
                                                        followSet.add(0, mBean);
                                                        for (int k = 1; k < followSet
                                                                .size(); k++) {
                                                            mBean = followSet
                                                                    .get(k);
                                                            if (null != mBean) {
                                                                mBean.setNumber(k + 1);
                                                                mBean.setBg(false);
                                                                mBean.setLight(true);
                                                                followSet.set(k,
                                                                        mBean);
                                                                mBean = null;
                                                            }
                                                        }
                                                        bean.setBg(false);
                                                        bean.setFollowSet(followSet);
                                                        ConstantUtils.orderList
                                                                .set(i, bean);
                                                        mOrderListAdapter
                                                                .notifyDataSetChanged();
                                                        setTotalPrice();
                                                        setNewOrderListOtherItemBgFalse(i);
                                                        setNewOrderListCurrentItemModifierBgFalse(i);
                                                    }
                                                }
                                            }
                                        }
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
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void setNewOrderListOtherItemBgFalse(int position) {
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
            for (int i = 0; i < ConstantUtils.orderList.size(); i++) {
                bean = ConstantUtils.orderList.get(i);
                if (null != bean) {
//			if (null != bean && null == bean.getDetailDto()) {
                    if (i != position) {
                        //bean.setBg(false);
                        //bean.setLight(false);
                        bean.setSelect(false);
                        modifier = bean.getModifier();
                        if (null != modifier && modifier.size() > 0) {
                            for (int j = 0; j < modifier.size(); j++) {
                                modifierBean = modifier.get(j);
                                if (null != modifierBean) {
                                    //modifierBean.setLight(false);
                                    //modifierBean.setBg(false);
                                    modifierBean.setSelect(false);
                                    modifier.set(j, modifierBean);
                                    modifierBean = null;
                                }
                            }
                            bean.setModifier(modifier);
                            ConstantUtils.orderList.set(i, bean);
                            modifier = null;
                        }
                        followSet = bean.getFollowSet();
                        if (null != followSet && followSet.size() > 0) {
                            for (int j = 0; j < followSet.size(); j++) {
                                followSetBean = followSet.get(j);
                                //followSetBean.setLight(false);
                                //followSetBean.setBg(false);
                                followSetBean.setSelect(false);
                                followSetModifier = followSetBean.getModifier();
                                if (null != followSetModifier
                                        && followSetModifier.size() > 0) {
                                    for (int k = 0; k < followSetModifier.size(); k++) {
                                        followSetModifierBean = followSetModifier
                                                .get(k);
                                        //followSetModifierBean.setLight(false);
                                        //followSetModifierBean.setBg(false);
                                        followSetModifierBean.setSelect(false);
                                        followSetModifier.set(k,
                                                followSetModifierBean);
                                        followSetModifierBean = null;
                                    }
                                    followSetBean.setModifier(followSetModifier);
                                    followSetModifier = null;
                                }
                                followSet.set(j, followSetBean);
                                followSetBean = null;
                            }
                            bean.setFollowSet(followSet);
                            ConstantUtils.orderList.set(i, bean);
                            followSet = null;
                        }
                    }
                    bean = null;
                }
            }
            mOrderListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void setNewOrderListCurrentItemModifierBgFalse(int position) {
        try {
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                return;
            }
            OrderListBean bean = ConstantUtils.orderList.get(position);
            List<OrderListBean> modifier = null;
            OrderListBean modifierBean = null;
            if (null != bean) {
                modifier = bean.getModifier();
                if (null != modifier && modifier.size() > 0) {
                    for (int i = 0; i < modifier.size(); i++) {
                        modifierBean = modifier.get(i);
                        if (null != modifierBean) {
                            modifierBean.setBg(false);
                            modifierBean.setLight(false);
                            modifier.set(i, modifierBean);
                            modifierBean = null;
                        }
                    }
                    bean.setModifier(modifier);
                    modifier = null;
                    ConstantUtils.orderList.set(position, bean);
                    mOrderListAdapter.notifyDataSetChanged();
                }
                bean = null;
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void setNewOrderListCurrentItemFollowSetBgFalse(int position) {
        try {
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                return;
            }
            OrderListBean bean = ConstantUtils.orderList.get(position);
            List<OrderListBean> followSet = null;
            OrderListBean followSetBean = null;
            List<OrderListBean> followSetModifier = null;
            OrderListBean followSetModifierBean = null;
            if (null != bean) {
                followSet = bean.getFollowSet();
                if (null != followSet && followSet.size() > 0) {
                    for (int j = 0; j < followSet.size(); j++) {
                        followSetBean = followSet.get(j);
                        //followSetBean.setLight(false);
                        //followSetBean.setBg(false);
                        followSetBean.setSelect(false);
                        followSetModifier = followSetBean.getModifier();
                        if (null != followSetModifier
                                && followSetModifier.size() > 0) {
                            for (int k = 0; k < followSetModifier.size(); k++) {
                                followSetModifierBean = followSetModifier.get(k);
                                //followSetModifierBean.setLight(false);
                                //followSetModifierBean.setBg(false);
                                followSetModifierBean.setSelect(false);
                                followSetModifier.set(k, followSetModifierBean);
                                followSetModifierBean = null;
                            }
                            followSetBean.setModifier(followSetModifier);
                            followSetModifier = null;
                        }
                        followSet.set(j, followSetBean);
                        followSetBean = null;
                    }
                    bean.setFollowSet(followSet);
                    ConstantUtils.orderList.set(position, bean);
                    followSet = null;
                }
                bean = null;
                mOrderListAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

    private List<TxSalesDetailDto> reassignSeqIndxToOrderList(){
        if (null == ConstantUtils.orderList
                || ConstantUtils.orderList.size() <= 0) {
            return null;
        }
        List<TxSalesDetailDto> dtoList = new ArrayList<TxSalesDetailDto>();
        try {
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
            mOrderListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
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
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void commitData(List<TxSalesDetailDto> dtoList, final boolean isShow) {
        try {
            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.SAVE_TX_SALES);

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
                            FoodCatgoryItemActivity.this, isShow,
                            SoapUtils.SAVE_TX_SALES, result)) {
                        return;
                    }
                    CommonUtils.showToast(FoodCatgoryItemActivity.this,
                            getString(R.string.commit_order_success));
                    //ConstantUtils.isSubmit = true;

//finish();
                    setTableStatus(ConstantUtils.TABLE_STATUS_ORDER_COMPLETE);
                    CommonUtils.changeActivity(FoodCatgoryItemActivity.this,
                            TableListFragmentActivity.class);
                }

                @Override
                public void handleFailure() {

                }
            }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

    private void setTableStatus(int tableStatusId) {
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

        try {
            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.SET_TABLE_STATUS_BY_TABLE_ID,
                    new HandlerCallBack() {

                        @Override
                        public void handleFinish(HashMap<String, Object> result) {
                            try {
                                if (HandleHttpRequestResult.isError(
                                        FoodCatgoryItemActivity.this, isShow,
                                        SoapUtils.SET_TABLE_STATUS_BY_TABLE_ID,
                                        result)) {
                                    return;
                                }

                                SoapObject object = (SoapObject) result
                                        .get(HttpHandler.RESULT);

                            } catch (Exception e) {
                                CommonUtils.showToast(FoodCatgoryItemActivity.this,
                                        getString(R.string.remote_server_error));
                            }
                        }

                        @Override
                        public void handleFailure() {
                            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                                    getString(R.string.remote_server_error));
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCatgoryItemActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (View.VISIBLE == calculateLayout.getVisibility()) {
				setViewGone();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private void setViewGone() {
		calculateLayout.setVisibility(View.GONE);
		changeStatus(true);
	}

    private void listViewScrollToSelectedItem()
    {
        int selectedLevelOneItemPosition = 0;
        int selectedLevelTwoItemPosition = 0;
        int skippedItemCount = 0;
        int extraPaddingTop = 0;
        Boolean isSelectedItemFound = false;

        for(int i = ConstantUtils.orderList.size() - 1; i >= 0; i--) {
            selectedLevelOneItemPosition = i;
            skippedItemCount = 0;
            if(ConstantUtils.orderList.get(i).isSelect()) {
                break;
            } else {
                // search all the modifier list within this item to check if it has selected flog
                if(null != ConstantUtils.orderList.get(i).getModifier()) {
                    for(int j = 0 ; j < ConstantUtils.orderList.get(i).getModifier().size(); j++) {
                        skippedItemCount ++;
                        if(ConstantUtils.orderList.get(i).getModifier().get(j).isSelect()) {
                            selectedLevelTwoItemPosition = j;
                            isSelectedItemFound = true;
                            break;
                        }
                    }

                    if(isSelectedItemFound) {
                        break;
                    }
                }
                // search all the modifier list within this item to check if it has selected flog
                if(null != ConstantUtils.orderList.get(i).getFollowSet()) {
                    for(int j = 0; j < ConstantUtils.orderList.get(i).getFollowSet().size() ; j++) {
                        skippedItemCount ++;
                        if(ConstantUtils.orderList.get(i).getFollowSet().get(j).isSelect()) {
                            selectedLevelTwoItemPosition = j;
                            isSelectedItemFound = true;
                            break;
                        } else {
                            // search all the modifier list within this follow set to check if it has selected flog
                            if(null != ConstantUtils.orderList.get(i).getFollowSet().get(j).getModifier()) {
                                for(int k = 0; k < ConstantUtils.orderList.get(i).getFollowSet().get(j).getModifier().size() ; k++) {
                                    skippedItemCount ++;
                                    if(ConstantUtils.orderList.get(i).getFollowSet().get(j).getModifier().get(k).isSelect()) {
                                        selectedLevelTwoItemPosition = k;
                                        isSelectedItemFound = true;
                                        break;
                                    }
                                }

                                if(isSelectedItemFound) {
                                    break;
                                }
                            }
                        }
                    }

                    if(isSelectedItemFound) {
                        break;
                    }
                }
            }
        }

        // 20150503 - Michael
        if(skippedItemCount > 0) {
            extraPaddingTop = 10;
            //listView.smoothScrollToPositionFromTop(selectedLevelOneItemPosition,(extraPaddingTop + skippedItemCount*63)*-1);
            smoothScrollToPositionFromTop(listView, selectedLevelOneItemPosition,(extraPaddingTop + skippedItemCount*63)*-1);
        } else {
            listView.setSelection(selectedLevelOneItemPosition);
        }
    }

    public static void smoothScrollToPositionFromTop(final AbsListView view, final int position, final int offset) {
        View child = getChildAtPosition(view, position);
        // There's no need to scroll if child is already at top or view is already scrolled to its end
        /*if ((child != null) && ((child.getTop() == 0) || ((child.getTop() > 0) && !view.canScrollVertically(1)))) {
            return;
        }*/

        view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final AbsListView view, final int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    view.setOnScrollListener(null);

                    // Fix for scrolling bug
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            view.smoothScrollToPositionFromTop(position, offset);
                        }
                    });
                }
            }

            @Override
            public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount,
                                 final int totalItemCount) { }
        });

        // Perform scrolling to position
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                view.smoothScrollToPositionFromTop(position, offset);
            }
        });
    }

    public static View getChildAtPosition(final AdapterView view, final int position) {
        final int index = position - view.getFirstVisiblePosition();
        if ((index >= 0) && (index < view.getChildCount())) {
            return view.getChildAt(index);
        } else {
            return null;
        }
    }
}
