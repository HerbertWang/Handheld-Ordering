package com.everyware.handheld;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.AttributeInfo;
import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.everyware.handheld.R;
import com.everyware.handheld.adapter.CategoryAdapter;
import com.everyware.handheld.adapter.MenuAdapter;
import com.everyware.handheld.adapter.OrderListAdapter;
import com.everyware.handheld.bean.CategoryDto;
import com.everyware.handheld.bean.ItemMasterDto;
import com.everyware.handheld.bean.MenuHeaderDto;
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
import com.everyware.handheld.utils.GetMenuDto;
import com.everyware.handheld.utils.GetReasonDto;
import com.everyware.handheld.utils.GetTxSalesDetailDto;
import com.everyware.handheld.utils.GetTxSalesHeaderDto;
import com.everyware.handheld.utils.HandleHttpRequestResult;
import com.everyware.handheld.utils.HandlerOrderListCallBack;
import com.everyware.handheld.utils.ItemMasterDtoToTxSalesDetailDto;
import com.everyware.handheld.utils.OrderListUtils;
import com.everyware.handheld.utils.SoapUtils;
import com.everyware.handheld.view.ScrollLayout;

/**
 *
 * @author ALEX
 *
 */
public class OrderExpand extends Activity {
   
    private RelativeLayout menulist = null;
    private GridView menuGridView =null;
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
    private ScrollLayout scrollLayout = null;
    private int pageSize = 20;
    private List<TxSalesDetailDto> salesList = new ArrayList<TxSalesDetailDto>();

    private ListView listView = null;
    private OrderListAdapter mOrderListAdapter = null;
    private final String JIAOQI = "jiaoqi";
    private final String QICAI = "qicai";
    private Context currentPageContext = OrderExpand.this;

    private OrderListBean selectedOrderListBean;
    private OrderListBean selectedParentOrderListBean;
    private  List<MenuHeaderDto> topList1 = new ArrayList<MenuHeaderDto>();
    private int seq = 0;
    private TextView tvChangeMenu = null;
    private Button btnCol=null;
    private CategoryDto orderParentCategoryDto = new CategoryDto();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.order_expand);
            ComponentsManager.getComponentManager().pushComponent(this);

            initViews();
            initOrderList();
        } catch (Exception e) {
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }
    }

    private void initViews() {
        try {
        	/*
            //menulist=(RelativeLayout) findViewById(R.id.menulist);
            //menuGridView=(GridView) findViewById(R.id.menuGridView);
            //calculateLayout = (LinearLayout) findViewById(R.id.calculateLayout);
            btnkeyPan = (Button) findViewById(R.id.btnkeyPan);
            btnSendOrder = (Button) findViewById(R.id.btnSendOrder);
            tvTotal = (TextView) findViewById(R.id.tvTotal);
            tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
            btnCol=(Button) findViewById(R.id.btn_coll);
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
            tvConfirm = (TextView) findViewById(R.id.Cancel);
            tvZero = (TextView) findViewById(R.id.tvZero);
            tvJiaoQi = (TextView) findViewById(R.id.tvJiaoQi);
            tvQiCai = (TextView) findViewById(R.id.tvQiCai);
            tvTableCode = (TextView) findViewById(R.id.tvTableCode);
            tvChangeTable = (TextView) findViewById(R.id.tvChangeTable);
            tvChangeMenu= (TextView) findViewById(R.id.tvChangeMenu);  
            tvPrintBill = (TextView) findViewById(R.id.tvPrintBill);
            
           
            btnkeyPan.setOnClickListener(listener);
            btnSendOrder.setOnClickListener(listener);
            btnCol.setOnClickListener(listener);
            
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
            tvChangeMenu.setOnClickListener(listener);
            tvPrintBill.setOnClickListener(listener);
            getCurrentTxSales(false);
            
*/


calculateLayout = (LinearLayout) findViewById(R.id.calculateLayout);
btnkeyPan = (Button) findViewById(R.id.btnkeyPan);
btnSendOrder = (Button) findViewById(R.id.btnSendOrder);
tvTotal = (TextView) findViewById(R.id.tvTotal);
tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
btnCol=(Button) findViewById(R.id.btn_coll);
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


btnkeyPan.setOnClickListener(listener);
//btnConfirm.setOnClickListener(listener);
btnSendOrder.setOnClickListener(listener);
btnCol.setOnClickListener(listener);
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

            
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Eroor2", 130).show();
        	//CommonUtils.showToast(OrderExpand.this,
                    //getString(R.string.remote_server_error));
        }
    }

    private OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
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
                case R.id.Cancel:
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
                case R.id.tvTableCode:
                    if(ConstantUtils.isCoverCountEnabled)
                    {
                        setCusCount();
                    }
                    break;
                case R.id.tvChangeTable:
                    changeTable();
                    break;
                case R.id.tvPrintBill:
                    // print bill
                    commitOrderListAndPrintBill();
                    break;
                case R.id.tvChangeMenu:
                	ChangeMenu();
                	break;
                case R.id.btn_exp:
                	OrderExpand();
            }
        }
    };


  
    
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
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }
    }

    private void changeStatus(boolean status) {
        try {
            isShow = status;
            //categoryGridView.setIsTouch(status);
            btnkeyPan.setEnabled(status);
            btnSendOrder.setEnabled(status);
            tvNumber.setText("");
        } catch (Exception e) {
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }
    }

    private void setViewGone() {
        try {
            calculateLayout.setVisibility(View.GONE);
            changeStatus(true);
        } catch (Exception e) {
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }
    }

  

    private void getCurrentTxSales(final boolean isShow) {
        /*try {
        	
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
                            OrderExpand.this, isShow,
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

                    if(ConstantUtils.isCoverCountEnabled &&
                            (ConstantUtils.mTxSalesHearderDto.getCusCount().toString().equals("")
                                    || ConstantUtils.mTxSalesHearderDto.getCusCount().toString().equals("0")))
                    {
                        setCusCount();
                    }

                    if(!ConstantUtils.isCoverCountEnabled)
                        tvTableCode.setText(ConstantUtils.mTableDto.getTableCode());
                    else
                        tvTableCode.setText(ConstantUtils.mTableDto.getTableCode().concat(" (").concat(ConstantUtils.mTxSalesHearderDto.getCusCount()).concat(")"));
                }

                @Override
                public void handleFailure() {
                }
            }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }*/
    }

    private void setCusCount() {
  /*
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
        }*/
    }

    private void parseList(SoapObject object) {
        /*try {
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
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }*/
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
    	/*
        OrderListUtils.setOrderListItemIsSelectFalse();
        mOrderListAdapter.notifyDataSetChanged();
        //setTotalPrice();
       
        */
    }

    
    
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
        	
            Toast.makeText(getApplicationContext(), "Error is here", 130).show();
        	//CommonUtils.showToast(OrderExpand.this,
                    //getString(R.string.remote_server_error));
        }
    }

    private void changeOrderListItem(int parentPosition, int position,
                                     int subPosition, String status) {
        /*try {
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
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }*/
    }

    // common function
    private void changeOrderListNormalSelect(int position) {
 /*
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
                        
                        } else {
                            setOrderListSelect(bean, i, true);
                        }

                    }

                    mOrderListAdapter.notifyDataSetChanged();


                    bean = null;
                }
            }


            // 20150503 - Michael
            //listViewScrollToSelectedItem();
        } catch (Exception e) {
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }
        */
    }
    // common function
    private void setNewOrderListCurrentItemFollowSetBgFalse(int position) {
       
    	/*
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
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }*/
    }
    // common function
    private void setNewOrderListOtherItemBgFalse(int position) {
        /*
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
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }*/
    }
    // common funciton
    private void changeOrderListSelectFalse() {
       
    	/*
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
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }*/
    }

    private void setOrderListSelect(OrderListBean bean, int position,
                                    boolean status) {
        
    	/*
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
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }*/
    }

    // common function
    private void changeOrderListModifierSelect(int parentPosition, int position) {
        
    	/*
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
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }*/
    }

    // common function
    private void changeOrderListFollowSetSelect(int parentPosition, int position) {
        /*
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
            //listViewScrollToSelectedItem();
        } catch (Exception e) {
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }*/
    }

    // common function
    private void changeOrderListFollowSetModifierSelect(int parentPosition,
                                                        int position, int subPosition) {
        /*
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
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }*/
    }

    
    private void setTotalPrice() {
        /*
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
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }*/
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

    /**
     * 删除订单
     */
    // common function
    private void parseReasonListData(SoapObject soapObject) {
        /*
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
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }*/
    }
    // common function
    private void setSelectedOrderListBeanDisabled(OrderListBean selectedOrderListBean, ReasonDto selectedReasonDto) {
        // 1. set the parent item disabled
        // 2. set the modifier disabled
        // 3. set the follow set disabled
        // 4. set the modifier's follow set disabled

        /*
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

        mOrderListAdapter.notifyDataSetChanged();*/
    }
    // common function
    private void setOrderListBeanDisabled(OrderListBean bean, ReasonDto selectedReasonDto) {
        /*
    	
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
        */
    }
    //common function
    private void showReasonListPopup() {
       
    	/*
    	
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
                // the user clicked on colors[which]
               

                ReasonDto selectedReasonDto = ConstantUtils.disableReasonList.get(which);

                setSelectedOrderListBeanDisabled(selectedParentOrderListBean, selectedReasonDto);

                // set the deleted qty to the inputted one
                alterOrderQty();

            }
        }).setNeutralButton(getString(R.string.cancel), null);
        reasonListDialogBuilder.show();*/
    }
    // common function
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
                            OrderExpand.this, isShow,
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
                                    OrderExpand.this,
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
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }

    }
    // common function
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
    // common function
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
    // common function
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
                            OrderExpand.this, isShow,
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
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }
    }
    // common function
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
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }
    }
    //common function
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

    // common function
    public static View getChildAtPosition(final AdapterView view, final int position) {
        final int index = position - view.getFirstVisiblePosition();
        if ((index >= 0) && (index < view.getChildCount())) {
            return view.getChildAt(index);
        } else {
            return null;
        }
    }
    // common function
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
            CommonUtils.showToast(OrderExpand.this,
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
            CommonUtils.showToast(OrderExpand.this,
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
            CommonUtils.showToast(OrderExpand.this,
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
            CommonUtils.showToast(OrderExpand.this,
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
            CommonUtils.showToast(OrderExpand.this,
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
            CommonUtils.showToast(OrderExpand.this,
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
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }
    }

    private void commitData(List<TxSalesDetailDto> dtoList, final boolean isShow, final boolean isPrintBill) {
        SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                SoapUtils.SAVE_TX_SALES);
        String isInvoicePrintPending = "false";

        try {
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
                for(int i = 0; i <dtoList.size(); i++ ) {
                    if("true".equals(dtoList.get(i).getIsLocalChangedItem())){
                        if("0".equals(dtoList.get(i).getTxSalesDetailId())) {
                            isInvoicePrintPending = "true";
                            break;
                        } else if("false".equals(dtoList.get(i).getEnabled())) {
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
                            OrderExpand.this, isShow,
                            SoapUtils.SAVE_TX_SALES, result)) {
                        return;
                    }
                    CommonUtils.showToast(OrderExpand.this,
                            getString(R.string.commit_order_success));
                    //ConstantUtils.isSubmit = true;

                    

                    ConstantUtils.isSplit = false;
                    CommonUtils.changeActivity(OrderExpand.this,
                            TableListFragmentActivity.class);
                }

                @Override
                public void handleFailure() {

                }
            }).execute();
        } catch (Exception e) {
            CommonUtils.showToast(OrderExpand.this,
                    getString(R.string.remote_server_error));
        }
    }

   

    private void changeTable() {

        // check if server need to reprint the checklist
        for(int i = 0; i <ConstantUtils.orderList.size(); i++ ) {
            if(ConstantUtils.orderList.get(i).getDetailDto() == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderExpand.this);
                builder.setMessage(getString(R.string.cannot_change_table_unsave_item)).setNeutralButton(getString(R.string.confirm), null).show();
                return;
            }else if("true".equals(ConstantUtils.orderList.get(i).getDetailDto().getIsLocalChangedItem())){
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderExpand.this);
                builder.setMessage(getString(R.string.cannot_change_table_unsave_item)).setNeutralButton(getString(R.string.confirm), null).show();
                return;
            }
        }

        Intent intent = new Intent(OrderExpand.this,
                TableListFragmentActivity.class);
        intent.putExtra("isTableSelectorMode", "true");
        intent.putExtra("sourceTableId", ConstantUtils.mTableDto.getTableId());
        startActivity(intent);
    }






private void getAvailableMenu(final boolean isShow) {
    try {
        SoapObject mSoapObject = new SoapObject(SoapUtils.TARGET_NAMESPACE,
                SoapUtils.GET_AVAILABLE_MENU_LIST);

        mSoapObject.addProperty(ConstantUtils.ACCOUNT_ID,
                ConstantUtils.userInfo.getAccountId());
        mSoapObject.addProperty(ConstantUtils.SHOP_ID,
                ConstantUtils.userInfo.getShopId());
       
        new AsyncHttpRequest(this, isShow, mSoapObject,
                SoapUtils.GET_AVAILABLE_MENU_LIST, new HandlerCallBack() {

                    @Override
                    public void handleFinish(HashMap<String, Object> result) {
                        try {
                            if (HandleHttpRequestResult.isError(
                                    OrderExpand.this, isShow,
                                    SoapUtils.GET_AVAILABLE_MENU_LIST, result)) {
                                return;
                            }

                            SoapObject object = HandleHttpRequestResult
                                    .getResultSoapObject(result);

                            if (null != object
                                    && 0 != object.getPropertyCount()) {
                                //parseCategoryData(object);
                            	parseMenuData(object);
                            } else {
                                // showPopupWindow(getString(R.string.no_data));
                                CommonUtils.showToast(
                                        OrderExpand.this,
                                        getString(R.string.no_data));
                            }
                        } catch (Exception e) {
                            // showPopupWindow(getString(R.string.remote_server_error));
                            CommonUtils.showToast(OrderExpand.this,
                                    getString(R.string.remote_server_error));
                        }
                    }

                    @Override
                    public void handleFailure() {
                        // showPopupWindow(getString(R.string.remote_server_error));
                        CommonUtils.showToast(OrderExpand.this,
                                getString(R.string.remote_server_error));
                    }
                }).execute();
    } catch (Exception e) {
        CommonUtils.showToast(OrderExpand.this,
                getString(R.string.remote_server_error));
    }
}

private void parseMenuData(SoapObject soapObject) {
    try {
    	SoapObject mSoapObject = null;
        for (int i = 0; i < soapObject.getPropertyCount(); i++) {
            mSoapObject = (SoapObject) soapObject.getProperty(i);
            if (null != mSoapObject) {
                topList1.add(GetMenuDto.getMenuDto(mSoapObject));
             }
         }
       
        setMenuView(); 
        
    } catch (Exception e) {
        CommonUtils.showToast(OrderExpand.this,
                getString(R.string.remote_server_error));
    }
}

private GridView setMenuView() {
    try {
    	calculateLayout.setVisibility(View.GONE);
        changeStatus(true);
        MenuAdapter adapter = new MenuAdapter(this,topList1);
        menuGridView.setAdapter(adapter);
      
        menuGridView.setOnItemClickListener(new OnItemClickListener() {
        	 
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                if (!isShow) {
                    return;
                }
               
            }
        });
    }
        
        catch (Exception e) {
        CommonUtils.showToast(OrderExpand.this,
                getString(R.string.remote_server_error));
    }
    return  menuGridView;

}

private  void ChangeMenu()
{
	//Toast.makeText(getApplicationContext(), "lalal", 24).show();
	getAvailableMenu(true);
	menulist.setVisibility(View.VISIBLE);
	
	
}

private void OrderExpand()
{
	Intent intent=new Intent(OrderExpand.this,FoodCatgoryItemActivity.class);
	//intent.putExtra("PreviousClass", "OrderExpand");
    startActivity(intent);
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
        CommonUtils.showToast(OrderExpand.this,
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
                                    OrderExpand.this, isShow,
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
                                        OrderExpand.this,
                                        getString(R.string.no_data));
                            }
                        } catch (Exception e) {
                            CommonUtils.showToast(OrderExpand.this,
                                    getString(R.string.remote_server_error));
                        }
                    }

                    @Override
                    public void handleFailure() {
                        CommonUtils.showToast(OrderExpand.this,
                                getString(R.string.remote_server_error));
                    }
                }).execute();
    } catch (Exception e) {
        CommonUtils.showToast(OrderExpand.this,
                getString(R.string.remote_server_error));
    }
}

}





