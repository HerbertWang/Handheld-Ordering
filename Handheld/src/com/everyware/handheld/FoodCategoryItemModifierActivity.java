package com.everyware.handheld;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.AttributeInfo;
import org.ksoap2.serialization.SoapObject;


import com.everyware.handheld.adapter.FoodCategoryItemAdapter;
import com.everyware.handheld.adapter.OrderListAdapter;
import com.everyware.handheld.bean.CategoryDto;
import com.everyware.handheld.bean.ItemMasterDto;
import com.everyware.handheld.bean.OrderListBean;
import com.everyware.handheld.bean.ReasonDto;
import com.everyware.handheld.bean.TxSalesDetailDto;
import com.everyware.handheld.http.AsyncHttpRequest;
import com.everyware.handheld.http.HandlerCallBack;
import com.everyware.handheld.http.HttpHandler;
import com.everyware.handheld.utils.CommonUtils;
import com.everyware.handheld.utils.ComponentsManager;
import com.everyware.handheld.utils.ConstantUtils;
import com.everyware.handheld.utils.FormatCommitSoapObject;
import com.everyware.handheld.utils.GetCategoryDto;
import com.everyware.handheld.utils.GetItemMasterDto;
import com.everyware.handheld.utils.GetReasonDto;
import com.everyware.handheld.utils.HandleHttpRequestResult;
import com.everyware.handheld.utils.HandlerOrderListCallBack;
import com.everyware.handheld.utils.ItemMasterDtoToTxSalesDetailDto;
import com.everyware.handheld.utils.OrderListUtils;
import com.everyware.handheld.utils.SoapUtils;

import com.everyware.handheld.view.ScrollLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class FoodCategoryItemModifierActivity extends Activity {
  
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
    private TextView tvTableCode = null;
    private TextView tvChangeTable = null;
    private TextView tvPrintBill = null;
	private Intent intent = null;
	private String method=null;
	private List<ItemMasterDto> topList = new ArrayList<ItemMasterDto>();
	private boolean firstadd=true;
   
	private PopupWindow pop = null;
	private View proView = null;
	private TextView tvErrorTitle = null;
	private TextView tvErrorPrompt = null;
	private Button btnErrorConfirm = null;
	private Button btnErrorCancel = null;
	private RelativeLayout relativeLayout = null;

	

	private ListView listView = null;
	private OrderListAdapter mOrderListAdapter = null;

	private int seq = 0;

	private int currentAllCount;
	private boolean isClick = true;
	private int currentPosition;
	private int currentCount;
	private ItemMasterDto currentDto;
	private ItemMasterDto selDto;

	private String autoRedirectToModifier = "false";
	private FoodCategoryItemAdapter topAdapter = null;
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
    private Context currentPageContext = FoodCategoryItemModifierActivity.this;

    private OrderListBean selectedOrderListBean;
    private OrderListBean selectedParentOrderListBean;

   
    private AutoCompleteTextView quickCodetextView;
    //private QuickCodeAutoCompleteAdapter quickCodeAdapter;
    private ArrayAdapter<ItemMasterDto> quickCodeAdapter;
    private Filter quickCodeAdapterFilter;
    private CheckBox cbItemName;
    private boolean isQuickCodeItemName;
    private boolean isQuickCodeMode;
    
   
    
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food_category_item_modifier_activity);
		ComponentsManager.getComponentManager().pushComponent(this);

		initOrderList();
		initViews();
	}

	
    

	private void initViews() {
		layoutGridView = (RelativeLayout) findViewById(R.id.layoutGridView);
		scrollLayout = (ScrollLayout) findViewById(R.id.scrollLayout);
		topGridView = (GridView) findViewById(R.id.topGridView);
        btnOverOperation = (Button) findViewById(R.id.btnOverOperation);
		btnBack = (Button) findViewById(R.id.btnBack);
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
        tvTableCode = (TextView) findViewById(R.id.tvTableCode);
        tvChangeTable = (TextView) findViewById(R.id.tvChangeTable);
        tvPrintBill = (TextView) findViewById(R.id.tvPrintBill);
        quickCodetextView = (AutoCompleteTextView) findViewById(R.id.etSearchInput);
        cbItemName = (CheckBox) findViewById(R.id.swItemName);

		btnOverOperation.setOnClickListener(listener);
		btnBack.setOnClickListener(listener);
		btnkeyPan.setOnClickListener(listener);
		//btnConfirm.setOnClickListener(listener);
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
        tvTableCode.setOnClickListener(listener);
        tvChangeTable.setOnClickListener(listener);
        tvPrintBill.setOnClickListener(listener);

		intent = getIntent();
		if (null != intent) {
			//String method = intent.getStringExtra("FinalMethod");
			Bundle bundle = intent.getExtras();
            topList=(List<ItemMasterDto>) bundle.getSerializable("List");
            method=bundle.getString("FinalMethod");
			selDto=(ItemMasterDto)bundle.getSerializable("selectobj");
			//method=intent.getStringExtra("FinalMethod");
		
		}
		
		
		setTopGridView(method);
		initProPopupWindow();
		

		OrderListUtils.setOrderListItemIsSelectFalse();
        if(!ConstantUtils.isCoverCountEnabled)
            tvTableCode.setText(ConstantUtils.mTableDto.getTableCode());
        else
            tvTableCode.setText(ConstantUtils.mTableDto.getTableCode().concat(" (").concat(ConstantUtils.mTxSalesHearderDto.getCusCount()).concat(")"));
	}

	/**
	 * 初始化订�?
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 初始化提示弹出框
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 显示弹出�?
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 关闭弹出�?
	 */
	private void closePop() {
		if (null != pop && pop.isShowing()) {
			pop.dismiss();
		}
	}

	/**
	 * 点击事件
	 */
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
            try {
                switch (v.getId()) {
                case R.id.btnOverOperation:
                setTableStatus(ConstantUtils.originalTableStatusId);
                ConstantUtils.isSplit = false;
                CommonUtils.changeActivity(FoodCategoryItemModifierActivity.this,
                TableListFragmentActivity.class);
                    // 20150331 Michael - Directly exit to the table list
                    //finish();
                    break;
                case R.id.btnBack:
                    finish();
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
                case R.id.tvTableCode:
                    if(ConstantUtils.isCoverCountEnabled)
                    {
                        setCusCount();
                    }
                case R.id.tvChangeTable:
                    changeTable();
                    break;
                case R.id.tvPrintBill:
                    // print bill
                    commitOrderListAndPrintBill();
                    break;
                }
            } catch (Exception e) {
                CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                        getString(R.string.remote_server_error));
            }
        }
	};

	
	/**
	 * 弹出框显示或者隐藏后改变其他控件的焦�?
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
	 * 设置弹出框数�?
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	

 

    /**
     * Show reason list for user to choose
     */
    private void showReasonListPopup() {
        ArrayList<String> listing = new ArrayList<String>();
        ReasonDto item;
        for(int i=0;i< ConstantUtils.disableReasonList.size();i++)
        {
            item = ConstantUtils.disableReasonList.get(i);
            listing.add(item.getReasonDesc());
            //getPath is a method in the customtype class which will return value in string format

        }
        final CharSequence[] reason_list = listing.toArray(new CharSequence[listing.size()]);

        AlertDialog.Builder reasonListDialogBuilder = new AlertDialog.Builder(this);
        reasonListDialogBuilder.setTitle(getString(R.string.pop_title_disable_reason));
        reasonListDialogBuilder.setItems(reason_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               
                ReasonDto selectedReasonDto = ConstantUtils.disableReasonList.get(which);

                setSelectedOrderListBeanDisabled(selectedParentOrderListBean, selectedReasonDto);

                
                alterOrderQty();

            }
        }).setNeutralButton(getString(R.string.cancel), null);
        reasonListDialogBuilder.show();
    }
    /**
     * Mark selected parent detail dto to be disabled
     */
    private void setSelectedOrderListBeanDisabled(OrderListBean selectedOrderListBean, ReasonDto selectedReasonDto) {
        // 1. set the parent item disabled
        // 2. set the modifier disabled
        // 3. set the follow set disabled
        // 4. set the modifier's follow set disabled

        setOrderListBeanDisabled(selectedOrderListBean, selectedReasonDto);

        if(null != selectedOrderListBean.getModifierDto()) {
            for (int i = 0; i < selectedOrderListBean.getModifierDto().size(); i++) {
                setOrderListBeanDisabled(selectedOrderListBean.getModifierDto().get(i), selectedReasonDto);
            }
        }

        if(null != selectedOrderListBean.getFollowSetDto()) {
            for (int i= 0; i < selectedOrderListBean.getFollowSetDto().size(); i++) {
                setOrderListBeanDisabled(selectedOrderListBean.getFollowSetDto().get(i), selectedReasonDto);

                if( null != selectedOrderListBean.getFollowSetDto().get(i).getModifierDto()) {
                    for(int j = 0; j < selectedOrderListBean.getFollowSetDto().get(i).getModifierDto().size(); j ++) {
                        setOrderListBeanDisabled(selectedOrderListBean.getFollowSetDto().get(i).getModifierDto().get(j), selectedReasonDto);
                    }
                }
            }
        }

        mOrderListAdapter.notifyDataSetChanged();
    }

    private void setOrderListBeanDisabled(OrderListBean bean, ReasonDto selectedReasonDto) {
        bean.getDetailDto().setEnabled("false");
        bean.getDetailDto().setDisabledReasonId(selectedReasonDto.getReasonId());
        bean.getDetailDto().setDisabledReasonDesc(selectedReasonDto.getReasonDesc());
        bean.getDetailDto().setDisabledByUserName(ConstantUtils.userInfo.getUserName());
        bean.getDetailDto().setDisabledByUserId(ConstantUtils.userInfo.getUserId());
        bean.getDetailDto().setDisabledDateTime(CommonUtils.getCurrentDateTime());
        bean.getDetailDto().setPrintedKitchen("false");
        bean.getDetailDto().setPrintedKitchenByUserId(null);
        bean.getDetailDto().setPrintedKitchenByUserName(null);
        bean.getDetailDto().setPrintedKitchenDateTime(null);

        bean.getDetailDto().setIsLocalChangedItem("true");
    }

    /**
     * Parse reason list
     */
    private void parseReasonListData(SoapObject soapObject) {
        try {
            if(null != ConstantUtils.disableReasonList) {
                ConstantUtils.disableReasonList = null;
                ConstantUtils.disableReasonList = new ArrayList<ReasonDto>();
            }
            SoapObject mSoapObject = null;
            for(int i =0; i< soapObject.getPropertyCount(); i++) {
                mSoapObject = (SoapObject) soapObject.getProperty(i);
                if(null != mSoapObject) {
                    ConstantUtils.disableReasonList.add(GetReasonDto.getReasonDto(mSoapObject));
                }
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	

 
	
	

	/**
	 * 判断高亮的是Modifier还是FollowSet,如果是Modifier就直接将Modifier添加到父类里�?,
	 * 如果是FollowSet就将Modifier添加到FollowSet里面
	 * 
	 * @param bean
	 * @param dto
	 */
	private void addModifierToOrderList(OrderListBean bean, ItemMasterDto dto) {
        // 20150424 - Michael - Only add modifier to newly added item

        try {
        	
            if(null == bean.getDetailDto()) {
                setItemModifier(bean, dto);
            }
        } catch (Exception e) {
        	
        	Toast.makeText(getApplicationContext(), "lalala"+bean.getDetailDto(), 120).show();
        	
        	//CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                   // getString(R.string.remote_server_error));
        }
    }

	
	/**
	 * 设置第一项的Modifier
	 * 
	 * @param bean
	 *            订单列表的第�?�?
	 * @param dto
	 *            �?要添加的�?
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
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
                int mListSize = mList.size() - 1;
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
                    } else {
                        if( i == mListSize) {
                            mBean = mList.get(i);
                            if (null != mBean) {
                                mBean.setDto(dto.clone());
                                setManualName(mBean);
                                setManualPrice(mBean);
                            }
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 设置第一项的FollowSet
	 * 
	 * @param bean
	 *            订单列表的第�?�?
	 * @param dto
	 *            �?要添加的Item
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
                    // 20151204 - Michael
                    newBean.setBg(true);
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 获取Modifier数据
	 * 
	 * @param isShow
	 *            是否显示加载�?
	 * @param modifierGroupHeaderId
	 *            modifierGroupHeaderId
	 * @param mothed
	 *            从哪个�?�辑�?
	 * @param goTo
	 *            到哪个�?�辑�?
	 * 
	 */
	

                     

	/**
	 * 获取FollowSet的�??
	 * 
	 * @param isShow
	 *            是否显示加载�?
	 * @param itemId
	 * @param seq
	 * @param isFollowSetDynamic
	 * @param isFollowSetStandard
	 * @param mothed
	 *            从哪个�?�辑�?
	 * @param goTo
	 *            到哪个�?�辑�?
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
                                    FoodCategoryItemModifierActivity.this, isShow,
                                    SoapUtils.GET_NEXT_LEVEL_FOLLOW_SET_ITEM_LIST,
                                    result)) {
                                return;
                            }
                            // reset the current count for this level
                            currentCount = 0;
                            SoapObject object = HandleHttpRequestResult
                                    .getResultSoapObject(result);
                            if (null != object && 0 != object.getPropertyCount()) {
                                //parseSubCategoryData(object, isFollowSetDynamic,
                                        //isFollowSetStandard, mothed, goTo);
                            } else {
                                // 20151204 - Clear the bottom list
                               
                            }
                        }

                        @Override
                        public void handleFailure() {
                            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                                    getString(R.string.remote_server_error));
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	/**
	 * 解析参数
	 * 
	 * @param soapObject
	 * @param isFollowSetDynamic
	 * @param isFollowSetStandard
	 * @param mothed
	 * @param goTo
	 */
	

	/**
	 * 显示第二�?
	 * 
	 * @param method
	 */
	private void setupListBean(ItemMasterDto dto)
	{
		try{
			 if (null != ConstantUtils.orderList) {
			                int orderListNextPosition = ConstantUtils.orderList.size()-1;
			boolean isTrue = false;
			int hasPosition = -1;
			String qty = "1";
			
			 if (isTrue) {
			                        ConstantUtils.orderList.remove(hasPosition);
			                        ConstantUtils.orderList.add(
			                                0,
			                                getBean(dto,
			                                        String.valueOf(Integer.valueOf(qty) + 1)));
			                    } else {
			                    	
			                    	
			                        selectedOrderListBean = getBean(dto, "1");
			                        if(firstadd)
			                        {			                        	
			                        ConstantUtils.orderList.set(orderListNextPosition, selectedOrderListBean);
			                        firstadd=false;
			                        }
			                        else 
			                        {  int position=ConstantUtils.orderList.size();
			                        	
			                        	ConstantUtils.orderList.add(position, selectedOrderListBean);
			                        }
			                       
			                        selectedParentOrderListBean = selectedOrderListBean;
			                        selectedParentOrderListBean.setBg(true);
			                        selectedParentOrderListBean.setSelect(true);
			                    }

			setNewOrderListOtherItemBgFalse(orderListNextPosition);
			                    setNewOrderListCurrentItemFollowSetBgFalse(orderListNextPosition);


			                }

			                
			     
			            
			        } catch (NumberFormatException e) {
			            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
			                    getString(R.string.remote_server_error));
			        }
		
	}
	
	
	
	private void setTopGridView(final String method) {
        try {
        	
            //layoutModify.setVisibility(View.VISIBLE);
            topAdapter = new FoodCategoryItemAdapter(this, topList);
            topGridView.setAdapter(topAdapter);
            topGridView.setVisibility(View.VISIBLE);
            topGridView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {

                    changeOrderListSelectFalse();

                   currentPosition = position;
                   currentDto = topList.get(currentPosition);
                   currentAllCount = Integer.valueOf(topList.size());
                    
                    
                    
                        // check if the clicked item already sold out
                        if ("true".equals(topList.get(position).getIsOutOfStock())) {
                            //showPopupWindow(getString(R.string.already_clean));
                            AlertDialog.Builder builder = new AlertDialog.Builder(currentPageContext);
                            builder.setMessage(getString(R.string.already_clean)).setNeutralButton(getString(R.string.confirm), null).show();
                            return;
                        }
                        // currentCount = bottomList.get(position).getCurrentCount();
                        isClick = topList.get(position).isClick();
                       
                       
                        if (isClick) {
                            if (0 == currentAllCount) {
                            	Toast.makeText(getApplicationContext(), "1", 120).show();
                                // unlimited
                                currentCount++;
                                logicEF(topList.get(position), method,
                                        currentAllCount, currentCount);
                            } else if (1 == currentAllCount) {
                                // one
                            	Toast.makeText(getApplicationContext(), "2", 120).show();
                                currentCount++;
                                currentDto.setCurrentCount(1);
                                currentDto.setClick(false);
                                topList.set(position, currentDto);
                                logicEF(topList.get(position), method,
                                        currentAllCount, currentCount);
                            } else {
                            	Toast.makeText(getApplicationContext(), "3", 120).show();
                                // max count
                                if (currentCount <= currentAllCount) {
                                    currentCount++;
                                    currentDto.setCurrentCount(currentCount);
                                    currentDto.setClick(true);
                                    topList.set(position, currentDto);
                                    setupListBean(selDto);
                                    logicEF(topList.get(position), method,
                                            currentAllCount, currentCount);
                                   
                                } else {
                                    currentDto.setCurrentCount(currentAllCount);
                                    currentDto.setClick(false);
                                    topList.set(position, currentDto);
                                   
                                }
                            }
                        }
                   
                   
                }
            });
        } catch (Exception e) {
        	Toast.makeText(getApplicationContext(), "I am here 2", 120).show();
            //CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    //getString(R.string.remote_server_error));
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
        	Toast.makeText(getApplicationContext(), "I am here 4", 120).show();
        	//CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                  //  getString(R.string.remote_server_error));
        }
    }

	/**
	 * 逻辑E
	 * 
	 * @param dto
	 *            每一项的�?
	 * @param maxCount
	 *            �?大数�?
	 * @param currentCount
	 *            当前数�??
	 */
	private void isLogicE(ItemMasterDto dto, int maxCount, int currentCount) {
        try {
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                return;
            }
            setItemFollowSet(selectedParentOrderListBean, dto);
            if (1 == maxCount) {
                if ("true".equals(dto.getAutoRedirectToModifier())) {
                    logicStatus = LOGICF;
                   
                } else if ("false".equals(dto.getAutoRedirectToModifier())) {
                    logicStatus = LOGICE;
                    getNextLevelFollowSetItemList(true, parentItemId,
                            String.valueOf(++seq), "true", "false", logicStatus, "");
                }
            } else if (0 != maxCount) {
                if (currentCount == maxCount) {
                    logicStatus = LOGICE;
                    getNextLevelFollowSetItemList(true, parentItemId,
                            String.valueOf(++seq), "true", "false", logicStatus, "");
                }
            }
        } catch (Exception e) {
        	Toast.makeText(getApplicationContext(), "I am here 5", 120).show();
        	//CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                   // getString(R.string.remote_server_error));
        }
    }

	/**
	 * 逻辑F
	 * 
	 * @param dto
	 *            每一项的�?
	 * @param maxCount
	 *            �?大数�?
	 * @param currentCount
	 *            当前数�??
	 */
	private void isLogicF(ItemMasterDto dto, int maxCount, int currentCount) {
        try {
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                return;
            }
            // addModifierToOrderList(ConstantUtils.orderList.get(0), dto);
            addModifierToOrderList(selectedOrderListBean, dto);
           /*if (0 != maxCount && currentCount == maxCount) {
                if (null != dto.getLinkedGroupHeaderId()) {
                    logicStatus = LOGICF;
                    
                } else {
                    topList.clear();
// 20150330 Michael - if this is a dynamic follow set and the first level modifier is full, load the follow set for user to choose
                    if ("true".equals(selectedOrderListBean.getDto().getIsFollowSetDynamic())) {
                        getNextLevelFollowSetItemList(true, selectedOrderListBean.getDto().getItemId(),
                                String.valueOf(seq), dto.getIsFollowSetDynamic(),
                                dto.getIsFollowSetStandard(), logicStatus, "");
                    }
                }

            }
            currentCount++;*/
        } catch (Exception e) {
        	Toast.makeText(getApplicationContext(), "I am here 3", 120).show();
            //CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    //getString(R.string.remote_server_error));
        }
    }




	/**
	 * 设置订单第一项的�?
	 * 
	 * @param dto
	 * @return
	 */
	private OrderListBean getBean(ItemMasterDto dto, String qty) {
		dto.setItemQty(qty);

        // check if the selected item is manual name
		OrderListBean bean = new OrderListBean();
		bean.setLight(true);
		bean.setNumber(1);
		bean.setDto(dto.clone());
		bean.setBg(true);
		bean.setModifier(null);
		bean.setFollowSet(null);

        setManualName(bean);
        setManualPrice(bean);

		return bean;
	}

    private void setManualPrice(OrderListBean inputOrderListBean) {
        final ItemMasterDto tempDto = inputOrderListBean.getDto();
        // check if the selected item is manual price
        if("true".equals(tempDto.getIsManualPrice())) {
            AlertDialog.Builder alert = new AlertDialog.Builder(currentPageContext);

            alert.setTitle(getString(R.string.pop_title_input_price));
            //alert.setMessage("Message");

            // Set an EditText view to get user input
            final EditText input = new EditText(currentPageContext);
            // Digits only & use numeric soft-keyboard.
            input.setKeyListener(DigitsKeyListener.getInstance(true, true));
            alert.setView(input);

            alert.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String newPrice = input.getText().toString();
                    tempDto.setItemPrice(newPrice);
                    mOrderListAdapter.notifyDataSetChanged();
                }
            });

            alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                    // nothing to do
                }
            });

            alert.show();
        }
    }

    private void setManualName(OrderListBean inputOrderListBean) {
        final ItemMasterDto tempDto = inputOrderListBean.getDto();
        // check if the selected item is manual price
        if("true".equals(tempDto.getIsManualName())) {
            AlertDialog.Builder alert = new AlertDialog.Builder(currentPageContext);

            alert.setTitle(getString(R.string.pop_title_input_name));
            //alert.setMessage("Message");

            // Set an EditText view to get user input
            final EditText input = new EditText(currentPageContext);
            // Digits only & use numeric soft-keyboard.
            input.setKeyListener(TextKeyListener.getInstance());
            alert.setView(input);

            alert.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String newName = input.getText().toString();
                    tempDto.setItemName(newName);
                    mOrderListAdapter.notifyDataSetChanged();
                }
            });

            alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                    // nothing to do
                }
            });

            alert.show();
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
        	Toast.makeText(getApplicationContext(), "I am here 8", 120).show();
        	//CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                   // getString(R.string.remote_server_error));
        }
    }

	/**
	 * 计算每个商品的价�?
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
            selectedParentOrderListBean = ConstantUtils.orderList.get(parentPosition);

            if (OrderListAdapter.NORMAL.equals(status)) {
                selectedOrderListBean = selectedParentOrderListBean;
            } else if (OrderListAdapter.MODIFIER.equals(status)) {
                selectedOrderListBean = selectedParentOrderListBean;
            } else if (OrderListAdapter.FOLLOWSET.equals(status)) {
                if(null != ConstantUtils.orderList.get(parentPosition).getFollowSet()) {
                    selectedOrderListBean = ConstantUtils.orderList.get(parentPosition).getFollowSet().get(position);
                } else if(null != ConstantUtils.orderList.get(parentPosition).getFollowSetDto()) {
                    selectedOrderListBean = ConstantUtils.orderList.get(parentPosition).getFollowSetDto().get(position);
                } else {
                    selectedOrderListBean = selectedParentOrderListBean;
                }
            } else if (OrderListAdapter.FOLLOWSET_MODIFIER.equals(status)) {
                if(null != ConstantUtils.orderList.get(parentPosition).getFollowSet()) {
                    selectedOrderListBean = ConstantUtils.orderList.get(parentPosition).getFollowSet().get(position);
                } else if(null != ConstantUtils.orderList.get(parentPosition).getFollowSetDto()) {
                    selectedOrderListBean = ConstantUtils.orderList.get(parentPosition).getFollowSetDto().get(position);
                } else {
                    selectedOrderListBean = selectedParentOrderListBean;
                }
            }

        } catch (Exception e) {
        	Toast.makeText(getApplicationContext(), "I am here 9", 120).show();
        	//CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    //getString(R.string.remote_server_error));
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
                        // 20150508 - Michael - Select bug
                        //bean.setBg(true);
                        if (bean.isSelect()) {
// 20150329 michael keep that be selected even it's already selected
                            /*bean.setSelect(false);
                            ConstantUtils.orderList.set(i, bean);*/
                        } else {
                            setOrderListSelect(bean, i, true);
                        }

                        if(null != bean.getDto()) {
                            if (TextUtils.isEmpty(bean.getDto().getModifierGroupHeaderId())) {
                                return;
                            }
                            autoRedirectToModifier = "true";

                           
                        }
                    }

                    mOrderListAdapter.notifyDataSetChanged();


                    bean = null;
                }
            }


            // 20150503 - Michael
            //listViewScrollToSelectedItem();
        } catch (Exception e) {
        	Toast.makeText(getApplicationContext(), "I am here 9", 120).show();
        	//CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    //getString(R.string.remote_server_error));
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
        	Toast.makeText(getApplicationContext(), "I am here 10", 120).show();
            //CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    //getString(R.string.remote_server_error));
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
        	Toast.makeText(getApplicationContext(), "I am here 11", 120).show();
        	//CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
            //        getString(R.string.remote_server_error));
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
                    if(null != bean.getDetailDto()) {
                        modifierBean = bean.getModifierDto().get(position);
                    } else {
                        modifierBean = bean.getModifier().get(position);
                    }

                    if (null != modifierBean) {
                        modifierBean.setSelect(true);
                    }
                }

            }

            mOrderListAdapter.notifyDataSetChanged();
            // 20150503 - Michael
            //listViewScrollToSelectedItem();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
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

                                            if(null != followSetBean.getDto()) {
                                                if (TextUtils.isEmpty(followSetBean.getDto().getModifierGroupHeaderId())) {
                                                    return;
                                                }
                                                autoRedirectToModifier = "true";
                                               
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
            //listViewScrollToSelectedItem();
        } catch (Exception e) {
        	Toast.makeText(getApplicationContext(), "I am here 12", 120).show();
        	//CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                   // getString(R.string.remote_server_error));
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
            //listViewScrollToSelectedItem();
        } catch (Exception e) {
        	Toast.makeText(getApplicationContext(), "I am here 13", 120).show();
        	///CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    //getString(R.string.remote_server_error));
        }
    }

	/**
	 * 删除订单
	 */
    private void deleteSentOrderListItemPromptReason() {
        // 2. check if the item qty is 1
        // 3. popup qty window to ask for the qty
        // 4. ad-hoc create new item if the delete qty is less than original item qty
        try {
            if(null != ConstantUtils.disableReasonList && ConstantUtils.disableReasonList.size() > 0) {
                showReasonListPopup();
                return;
            }
            // 1. check user right for delete saved item
            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.GET_AVAILABLE_REASON_LIST);

            mSoapObject.addProperty(ConstantUtils.REASON_GROUP_CODE,
                    ConstantUtils.RGC_TX_DISABLE);
            mSoapObject.addProperty(ConstantUtils.ACCOUNT_ID,
                    ConstantUtils.userInfo.getAccountId());

            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.GET_AVAILABLE_REASON_LIST, new HandlerCallBack() {
                @Override
                public void handleFinish(HashMap<String, Object> result) {
                    if (HandleHttpRequestResult.isError(
                            FoodCategoryItemModifierActivity.this, isShow,
                            SoapUtils.GET_AVAILABLE_REASON_LIST, result)) {
                        return;
                    }
                    SoapObject object = (SoapObject) result
                            .get(HttpHandler.RESULT);

                    SoapObject soapObject = (SoapObject) object
                            .getProperty(SoapUtils.RESULT_LIST);
                    if (null != soapObject
                            && 0 != soapObject.getPropertyCount()) {
                        parseReasonListData(soapObject);

                        // pop up the reason list to choose
                        showReasonListPopup();
                    } else {
                        if (isShow) {
                            CommonUtils.showToast(
                                    FoodCategoryItemModifierActivity.this,
                                    getString(R.string.no_data));
                        }
                    }
                    /*Boolean canRead = false;
                    Boolean canWrite = false;

                    SoapObject resultUserGroupRightDtoSoapObject = (SoapObject) object
                            .getProperty("resultUserGroupRightDto");

                    if (null != resultUserGroupRightDtoSoapObject) {
                        if (null != resultUserGroupRightDtoSoapObject.getProperty("CanRead")) {
                            canRead = ("true".equals(String.valueOf(resultUserGroupRightDtoSoapObject.getProperty("CanRead"))));
                        }
                        if (null != resultUserGroupRightDtoSoapObject.getProperty("CanWrite")) {
                            canWrite = ("true".equals(String.valueOf(resultUserGroupRightDtoSoapObject.getProperty("CanWrite"))));
                        }
                    }

                    if(canRead && canWrite) {
                        // pop the reason list for user to choose
                        deleteSentOrderListItemPromptReason();
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(currentPageContext);
                        builder.setMessage(getString(R.string.gr_access_denied)).setNeutralButton(getString(R.string.confirm), null).show();
                        return;
                    }*/

                    // clear the current reason list and fill the new one


                }

                @Override
                public void handleFailure() {
                }
            }).execute();
            // 2. check if the item qty is 1
            // 3. popup qty window to ask for the qty
            // 4. ad-hoc create new item if the delete qty is less than original item qty

            // 5. remove the below dummy code

            /*CommonUtils.showToast(this,
                    getString(R.string.submitted));*/
        } catch (Exception e) {
        	Toast.makeText(getApplicationContext(), "I am here 14", 120).show();
        	//CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                   // getString(R.string.remote_server_error));
        }

    }
    private void deleteSentOrderListItemCheckRight() {
        try {
            // 1. check user right for delete saved item
            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.CHECK_USER_GROUP_RIGHT);

            mSoapObject.addProperty(ConstantUtils.ACCOUNT_ID,
                    ConstantUtils.userInfo.getAccountId());
            mSoapObject.addProperty(ConstantUtils.SHOP_ID,
                    ConstantUtils.userInfo.getShopId());
            mSoapObject.addProperty(ConstantUtils.STAFF_CODE,
                    ConstantUtils.userInfo.getStaffCode());
            mSoapObject.addProperty(ConstantUtils.USER_ID,
                    ConstantUtils.userInfo.getUserId());
            mSoapObject.addProperty(ConstantUtils.GROUP_RIGHT_CODE,
                    ConstantUtils.GR_DISABLE_DETAIL);

            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.CHECK_USER_GROUP_RIGHT, new HandlerCallBack() {
                @Override
                public void handleFinish(HashMap<String, Object> result) {
                    if (HandleHttpRequestResult.isError(
                            FoodCategoryItemModifierActivity.this, isShow,
                            SoapUtils.CHECK_USER_GROUP_RIGHT, result)) {
                        return;
                    }
                    SoapObject object = (SoapObject) result
                            .get(HttpHandler.RESULT);
                    Boolean canRead = false;
                    Boolean canWrite = false;

                    SoapObject resultUserGroupRightDtoSoapObject = (SoapObject) object
                            .getProperty("resultUserGroupRightDto");

                    if (null != resultUserGroupRightDtoSoapObject) {
                        if (null != resultUserGroupRightDtoSoapObject.getProperty("CanRead")) {
                            canRead = ("true".equals(String.valueOf(resultUserGroupRightDtoSoapObject.getProperty("CanRead"))));
                        }
                        if (null != resultUserGroupRightDtoSoapObject.getProperty("CanWrite")) {
                            canWrite = ("true".equals(String.valueOf(resultUserGroupRightDtoSoapObject.getProperty("CanWrite"))));
                        }
                    }

                    if(canRead && canWrite) {
                        // pop the qty window for user to choose if qty is more than 1
                        if(Float.parseFloat(selectedOrderListBean.getDetailDto().getQty()) == 1) {
                            // pop the reason list for user to choose if qty equal to 1
                            deleteSentOrderListItemPromptReason();
                        } else {
                            deleteSentOrderListItemInputQty();
                        }

                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(currentPageContext);
                        builder.setMessage(getString(R.string.gr_access_denied)).setNeutralButton(getString(R.string.confirm), null).show();
                        return;
                    }
                }

                @Override
                public void handleFailure() {
                }
            }).execute();
            // 2. check if the item qty is 1
            // 3. popup qty window to ask for the qty
            // 4. ad-hoc create new item if the delete qty is less than original item qty

            // 5. remove the below dummy code

            /*CommonUtils.showToast(this,
                    getString(R.string.submitted));*/
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    getString(R.string.remote_server_error));
        }
    }
    private void deleteSentOrderListItemInputQty () {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(getString(R.string.pop_title_disable_qty));
        //alert.setMessage("Message");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        // Digits only & use numeric soft-keyboard.
        input.setKeyListener(DigitsKeyListener.getInstance());
        alert.setView(input);

        alert.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String deleteQtyStr = input.getText().toString();
                String originalQtyStr = selectedParentOrderListBean.getDetailDto().getQty();
                // 20151028
                float newOriginalQty = Float.parseFloat(originalQtyStr) - Float.parseFloat(deleteQtyStr);

                if(newOriginalQty < 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(currentPageContext);
                    builder.setMessage(getString(R.string.pop_title_disable_qty_invalid)).setNeutralButton(getString(R.string.confirm), null).show();
                    return;
                }

                if(newOriginalQty == 0) {
                    deleteSentOrderListItemPromptReason();
                    return;
                }

                OrderListBean newlyDeletedOrderListBean = selectedParentOrderListBean.clone();
                alterOrderListBeanQty(newlyDeletedOrderListBean, Float.parseFloat(deleteQtyStr));
                alterOrderListBeanQty(selectedParentOrderListBean, newOriginalQty);
                ConstantUtils.orderList.add(newlyDeletedOrderListBean);
                selectedParentOrderListBean = newlyDeletedOrderListBean;
                deleteSentOrderListItemPromptReason();
            }
        });

        alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                // nothing to do
            }
        });

        alert.show();
    }
	private void deleteOrderListItem() {
        try {
            if (null == ConstantUtils.orderList
                    || ConstantUtils.orderList.size() <= 0) {
                setTotalPrice();
                return;
            }

            // check if this is a saved item
            if(!selectedParentOrderListBean.isBg()) {
                deleteSentOrderListItemCheckRight();
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

    /**
     * Alter order qty
     */
    private void alterOrderListBeanQty(OrderListBean inputtedBean, float inputtedQty) {
        // reassign the seq number to the current order list first
        //reassignSeqIndxToOrderList();

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

        String number = Float.toString(inputtedQty);

        // locate the parent of the selected bean
        //int selectedItemOrderRunningIndex = selectedOrderListBean.getDto().get
        if (null != inputtedBean) {
            if(null != inputtedBean.getDto()) {
                // change the parent qty
                inputtedBean.getDto().setItemQty(number);
                //bean.setNumber(intNumber);

                // change all modifier list qty
                modifier = inputtedBean.getModifier();
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
                followSet = inputtedBean.getFollowSet();
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

            if(null != inputtedBean.getDetailDto()) {
                // change the parent qty
                inputtedBean.getDetailDto().setQty(number);
                inputtedBean.getDetailDto().setAmount(CommonUtils.getAmount(inputtedBean.getDetailDto().getQty(), inputtedBean.getDetailDto().getPrice()));
                inputtedBean.getDetailDto().setIsLocalChangedItem("true");
                //bean.setNumber(intNumber);

                // change all modifier list qty
                modifier = inputtedBean.getModifierDto();
                if (null != modifier && modifier.size() > 0) {
                    for (int j = 0; j < modifier.size(); j++) {
                        modifierBean = modifier.get(j);
                        //modifierBean.setNumber(intNumber);
                        modifierBean.getDetailDto().setQty(number);
                        modifierBean.getDetailDto().setAmount(CommonUtils.getAmount(modifierBean.getDetailDto().getQty(), modifierBean.getDetailDto().getPrice()));
                        modifierBean.getDetailDto().setIsLocalChangedItem("true");
                        if (null != modifierBean) {
                            modifierBean = null;
                        }
                    }
                    modifier = null;
                }

                // change all follow set and its modifier
                followSet = inputtedBean.getFollowSetDto();
                if (null != followSet && followSet.size() > 0) {
                    for (int j = 0; j < followSet.size(); j++) {
                        followSetBean = followSet.get(j);
                        if (null != followSetBean) {
                            //followSetBean.setNumber(intNumber);
                            followSetBean.getDetailDto().setQty(number);
                            followSetBean.getDetailDto().setAmount(CommonUtils.getAmount(followSetBean.getDetailDto().getQty(), followSetBean.getDetailDto().getPrice()));
                            followSetBean.getDetailDto().setIsLocalChangedItem("true");
                            followSetModifier = followSetBean
                                    .getModifierDto();
                            if (null != followSetModifier
                                    && followSetModifier.size() > 0) {
                                for (int k = 0; k < followSetModifier
                                        .size(); k++) {
                                    followSetModifierBean = followSetModifier
                                            .get(k);
                                    if (null != followSetModifierBean) {
                                        //followSetModifierBean.setNumber(intNumber);
                                        followSetModifierBean.getDetailDto().setQty(number);
                                        followSetModifierBean.getDetailDto().setAmount(CommonUtils.getAmount(followSetModifierBean.getDetailDto().getQty(), followSetModifierBean.getDetailDto().getPrice()));
                                        followSetModifierBean.getDetailDto().setIsLocalChangedItem("true");
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

        mOrderListAdapter.notifyDataSetChanged();
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
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
                                        FoodCategoryItemModifierActivity.this, isShow,
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
                                            FoodCategoryItemModifierActivity.this,
                                            getString(R.string.no_data));
                                }
                            } catch (Exception e) {
                                CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                                        getString(R.string.remote_server_error));
                            }
                        }

                        @Override
                        public void handleFailure() {
                            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                                    getString(R.string.remote_server_error));
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void gaiMa(final boolean isShow) {
        try {
            if (View.VISIBLE == calculateLayout.getVisibility()) {
                setViewGone();
            }
            // show the modifier panel instead of search panel
            if(isQuickCodeMode) {
                if(quickCodetextView.getVisibility() == View.VISIBLE) {
                    topGridView.setVisibility(View.VISIBLE);
                    quickCodetextView.setVisibility(View.GONE);
                   //layoutModify.setVisibility(View.VISIBLE);
                } else {
                    if(scrollLayout.getVisibility() == View.VISIBLE) {
                        layoutGridView.setVisibility(View.VISIBLE);
                        topGridView.setVisibility(View.GONE);
                        quickCodetextView.setVisibility(View.VISIBLE);
                        //layoutModify.setVisibility(View.GONE);
                        scrollLayout.setVisibility(View.GONE);
                        return;
                    }
                }
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
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
                                        FoodCategoryItemModifierActivity.this, isShow,
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
                                    //parseGetAvailableItemCategoryList(categorySoapObject);
                                } else {
                                    CommonUtils.showToast(
                                            FoodCategoryItemModifierActivity.this,
                                            getString(R.string.no_data));
                                }
                            } catch (Exception e) {
                                CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                                        getString(R.string.remote_server_error));
                            }
                        }

                        @Override
                        public void handleFailure() {
                            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                                    getString(R.string.remote_server_error));
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    getString(R.string.remote_server_error));
        }
    }



	
	

	private void addGetAvailableItemCategoryListToOrderList(int position) {
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
                    if (null == bean.getDetailDto()) {
                        if (bean.isSelect()) {
                            modifier = bean.getModifier();
                            OrderListBean mBean = getBean(topList.get(position),
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
                                                    topList.get(position), "1");
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
                                                        topList.get(position),
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
                                                        topList.get(position),
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
                                                                topList
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
        	Toast.makeText(getApplicationContext(), "I am here 1", 120).show();
            //CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                   // getString(R.string.remote_server_error));
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
                        if(null != bean.getDetailDto()) {
                            modifier = bean.getModifierDto();
                        } else {
                            modifier = bean.getModifier();
                        }

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
                            if(null != bean.getDetailDto()) {
                                bean.setModifierDto(modifier);
                            } else {
                                bean.setModifier(modifier);
                            }

                            ConstantUtils.orderList.set(i, bean);
                            modifier = null;
                        }
                        if(null != bean.getDetailDto()) {
                            followSet = bean.getFollowSetDto();
                        } else {
                            followSet = bean.getFollowSet();
                        }
                        if (null != followSet && followSet.size() > 0) {
                            for (int j = 0; j < followSet.size(); j++) {
                                followSetBean = followSet.get(j);
                                //followSetBean.setLight(false);
                                //followSetBean.setBg(false);
                                followSetBean.setSelect(false);
                                if(null != bean.getDetailDto()) {
                                    followSetModifier = followSetBean.getModifierDto();
                                } else {
                                    followSetModifier = followSetBean.getModifier();
                                }
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
                                    if(null != bean.getDetailDto()) {
                                        followSetBean.setModifierDto(followSetModifier);
                                    } else {
                                        followSetBean.setModifier(followSetModifier);
                                    }

                                    followSetModifier = null;
                                }
                                followSet.set(j, followSetBean);
                                followSetBean = null;
                            }
                            if(null != bean.getDetailDto()) {
                                bean.setFollowSetDto(followSet);
                            } else {
                                bean.setFollowSet(followSet);
                            }

                            ConstantUtils.orderList.set(i, bean);
                            followSet = null;
                        }
                    }
                    bean = null;
                }
            }
            mOrderListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
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
                    if (null != bean.getDetailDto() && "true".equals(bean.getDetailDto().getIsLocalChangedItem())) {
                        if(bean.isReassignNeeded()) {
                            seqNo++;
                            itemOrderRunningIndex++;
                            itemSetRunningIndex++;
                            bean.getDetailDto().setSeqNo(Integer.toString(seqNo));
                            bean.getDetailDto().setItemOrderRunningIndex(Integer.toString(itemOrderRunningIndex));
                            bean.getDetailDto().setItemSetRunningIndex(Integer.toString(itemSetRunningIndex));
                        }
                        dtoList.add(bean.getDetailDto());
                        modifierList = bean.getModifierDto();
                        if (null != modifierList && modifierList.size() > 0) {
                            for (int j = 0; j < modifierList.size(); j++) {
                                modifierListBean = modifierList.get(j);
                                if (null != modifierListBean) {
                                    if (null != modifierListBean.getDetailDto()) {
                                        if(bean.isReassignNeeded()) {
                                            seqNo++;
                                            modifierListBean.getDetailDto().setSeqNo(Integer.toString(seqNo));
                                            modifierListBean.getDetailDto().setItemOrderRunningIndex(Integer.toString(itemOrderRunningIndex));
                                            modifierListBean.getDetailDto().setItemSetRunningIndex(Integer.toString(itemSetRunningIndex));
                                        }
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
                                        if(bean.isReassignNeeded()) {
                                            seqNo++;
                                            itemSetRunningIndex++;
                                            followSetListBean.getDetailDto().setSeqNo(Integer.toString(seqNo));
                                            followSetListBean.getDetailDto().setItemOrderRunningIndex(Integer.toString(itemOrderRunningIndex));
                                            followSetListBean.getDetailDto().setItemSetRunningIndex(Integer.toString(itemSetRunningIndex));
                                        }

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
                                                    if(bean.isReassignNeeded()) {
                                                        seqNo++;
                                                        followSetModifierListBean.getDetailDto().setSeqNo(Integer.toString(seqNo));
                                                        followSetModifierListBean.getDetailDto().setItemOrderRunningIndex(Integer.toString(itemOrderRunningIndex));
                                                        followSetModifierListBean.getDetailDto().setItemSetRunningIndex(Integer.toString(itemSetRunningIndex));
                                                    }
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
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
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
                commitData(dtoList, true, false);
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

    private void commitOrderListAndPrintBill() {
        try {
            List<TxSalesDetailDto> dtoList = reassignSeqIndxToOrderList();

            if (null == dtoList || dtoList.size() <= 0) {
                return;
            } else {
                commitData(dtoList, true, true);
            }
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                    getString(R.string.remote_server_error));
        }
    }

	private void commitData(List<TxSalesDetailDto> dtoList, final boolean isShow, final boolean isPrintBill) {
        try {
            SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                    SoapUtils.SAVE_TX_SALES);

            String isInvoicePrintPending = "false";

            AttributeInfo tem = new AttributeInfo();
            tem.setName("xmlns:tem");
            tem.setValue(SoapUtils.TARGET_NAMESPACE);
            mSoapObject.addAttribute(tem);

            AttributeInfo pos = new AttributeInfo();
            pos.setName("xmlns:pos");
            pos.setValue(SoapUtils.POS_NAMESPACE);
            mSoapObject.addAttribute(pos);

            if(isPrintBill) {
                ConstantUtils.mTxSalesHearderDto.setTxChecked("true");
            }

            mSoapObject.addSoapObject(FormatCommitSoapObject
                    .getFormatTxSalesHeaderDto(ConstantUtils.mTxSalesHearderDto,
                            ConstantUtils.mTxPaymentDto,
                            ConstantUtils.mTxSalesDetailDtoNormal));
            mSoapObject.addSoapObject(FormatCommitSoapObject
                    .getFormatTxSalesDetailDtoList(dtoList, true));

            mSoapObject.addSoapObject(FormatCommitSoapObject.getFormatUserInfo());

            // check if server need to reprint the checklist
            if(isPrintBill) {
                isInvoicePrintPending = "true";
            } else {
                for (int i = 0; i < dtoList.size(); i++) {
                    if ("true".equals(dtoList.get(i).getIsLocalChangedItem())) {
                        if ("0".equals(dtoList.get(i).getTxSalesDetailId())) {
                            isInvoicePrintPending = "true";
                            break;
                        } else if ("false".equals(dtoList.get(i).getEnabled())) {
                            isInvoicePrintPending = "true";
                            break;
                        }
                    }
                }
            }

            mSoapObject.addProperty("tem:" + ConstantUtils.IS_INVOICE_PRINT_PENDING, isInvoicePrintPending);


            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.SAVE_TX_SALES, new HandlerCallBack() {

                @Override
                public void handleFinish(HashMap<String, Object> result) {
                    if (HandleHttpRequestResult.isError(
                            FoodCategoryItemModifierActivity.this, isShow,
                            SoapUtils.SAVE_TX_SALES, result)) {
                        return;
                    }
                    CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                            getString(R.string.commit_order_success));
                    //ConstantUtils.isSubmit = true;

//finish();
                    if(isPrintBill) {
                        setTableStatus(ConstantUtils.TABLE_STATUS_ORDER_CHECK);
                    } else {
                        setTableStatus(ConstantUtils.TABLE_STATUS_ORDER_COMPLETE);
                    }

                    ConstantUtils.isSplit = false;
                    CommonUtils.changeActivity(FoodCategoryItemModifierActivity.this,
                            TableListFragmentActivity.class);
                }

                @Override
                public void handleFailure() {

                }
            }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
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
        mSoapObject.addProperty("tem:" + ConstantUtils.POS_CODE, CommonUtils.getSharedPreferences(this,
                this.getString(R.string.device_name)));
        mSoapObject.addSoapObject(FormatCommitSoapObject.getFormatUserInfo());

        try {
            new AsyncHttpRequest(this, isShow, mSoapObject,
                    SoapUtils.SET_TABLE_STATUS_BY_TABLE_ID,
                    new HandlerCallBack() {

                        @Override
                        public void handleFinish(HashMap<String, Object> result) {
                            try {
                                if (HandleHttpRequestResult.isError(
                                        FoodCategoryItemModifierActivity.this, isShow,
                                        SoapUtils.SET_TABLE_STATUS_BY_TABLE_ID,
                                        result)) {
                                    return;
                                }

                                SoapObject object = (SoapObject) result
                                        .get(HttpHandler.RESULT);

                            } catch (Exception e) {
                                CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                                        getString(R.string.remote_server_error));
                            }
                        }

                        @Override
                        public void handleFailure() {
                            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
                                    getString(R.string.remote_server_error));
                        }
                    }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(FoodCategoryItemModifierActivity.this,
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

    private void setCusCount() {

        // check if the selected item is manual price
        if(ConstantUtils.isCoverCountEnabled) {
            AlertDialog.Builder alert = new AlertDialog.Builder(currentPageContext);

            alert.setTitle(getString(R.string.pop_title_conver_count));
            //alert.setMessage("Message");

            // Set an EditText view to get user input
            final EditText input = new EditText(currentPageContext);
            // Digits only & use numeric soft-keyboard.
            input.setKeyListener(DigitsKeyListener.getInstance(true, true));
            alert.setView(input);

            alert.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String coverCount = input.getText().toString();
                    ConstantUtils.mTxSalesHearderDto.setCusCount(coverCount);

                    if(!ConstantUtils.isCoverCountEnabled)
                        tvTableCode.setText(ConstantUtils.mTableDto.getTableCode());
                    else
                        tvTableCode.setText(ConstantUtils.mTableDto.getTableCode().concat(" (").concat(ConstantUtils.mTxSalesHearderDto.getCusCount()).concat(")"));
                }
            });

            alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                    // nothing to do
                }
            });

            alert.show();
        }
    }

    private void changeTable() {
        // check if server need to reprint the checklist
        for(int i = 0; i <ConstantUtils.orderList.size(); i++ ) {
            if(ConstantUtils.orderList.get(i).getDetailDto() == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FoodCategoryItemModifierActivity.this);
                builder.setMessage(getString(R.string.cannot_change_table_unsave_item)).setNeutralButton(getString(R.string.confirm), null).show();
                return;
            }else if("true".equals(ConstantUtils.orderList.get(i).getDetailDto().getIsLocalChangedItem())){
                AlertDialog.Builder builder = new AlertDialog.Builder(FoodCategoryItemModifierActivity.this);
                builder.setMessage(getString(R.string.cannot_change_table_unsave_item)).setNeutralButton(getString(R.string.confirm), null).show();
                return;
            }
        }

        Intent intent = new Intent(FoodCategoryItemModifierActivity.this,
                TableListFragmentActivity.class);
        intent.putExtra("isTableSelectorMode", "true");
        intent.putExtra("sourceTableId", ConstantUtils.mTableDto.getTableId());
        startActivity(intent);
    }
	
}