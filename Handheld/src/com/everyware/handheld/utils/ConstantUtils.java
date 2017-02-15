package com.everyware.handheld.utils;

import java.util.ArrayList;
import java.util.List;

import com.everyware.handheld.bean.ItemMasterDto;
import com.everyware.handheld.bean.OrderListBean;
import com.everyware.handheld.bean.ReasonDto;
import com.everyware.handheld.bean.TableDto;
import com.everyware.handheld.bean.TxPaymentDto;
import com.everyware.handheld.bean.TxSalesDetailDto;
import com.everyware.handheld.bean.TxSalesDetailDtoNormal;
import com.everyware.handheld.bean.TxSalesHeaderDto;
import com.everyware.handheld.bean.UserInfo;

/**
 * 
 * @author ALEX
 * 
 */
public class ConstantUtils {
	/**
	 * Connect Server Information
	 */
	public static final String HTTP_HEAD = "http://";
	public static final String HTTP_PORT = ":8080";
	public static final String WEB_SERVICE_NAME = "/CommonWebService.svc?wsdl";
	/**
	 * Screen Pixel
	 */
	public static int screenWidth;
	public static int screenHeight;
	/**
	 * System Setup
	 */
	public static String shop_id;
	public static String ip;
	public static String account_id;
	public static String category_id;
	public static String round_method;
	public static String decimal_place;
	/**
	 * Result State Code
	 */
	public static final String IS_OK = "IsOk";
	public static final String IS_Error = "IsError";
	public static final String IS_Not_Vaild = "IsNotValid";
	public static final String UNKNOWN = "Unknown";
	/**
	 * UserInfo
	 */
	public static UserInfo userInfo = new UserInfo();
	/**
	 * Constants Name
	 */
	public static final String SHOP_ID = "shopId";
	public static final String ACCOUNT_ID = "accountId";
	public static final String TABLE_CODE = "tableCode";
	public static final String TABLE_ID = "tableId";
    public static final String TABLE_STATUS_ID = "tableStatusId";
    public static final String POS_CODE = "posCode";
	public static final String CATEGORY_ID = "categoryId";
	public static final String IS_SMART_CATEGORY = "isSmartCategory";
    public static final String IS_INVOICE_PRINT_PENDING = "isInvoicePrintPending";
    public static final String IS_MODIFIER_INCLUDED = "isModifierIncluded";
    public static final String STAFF_CODE = "staffCode";
    public static final String USER_ID = "userId";
    public static final String GROUP_RIGHT_CODE = "groupRightCode";
    public static final String REASON_GROUP_CODE = "reasonGroupCode";
    public static final String IS_SHOW_QUICK_CODE_PANEL = "isQuickCodePanel";
    public static final String ITEM_CODE = "itemCode";
    public static final String ITEM_NAME = "itemName";

    /**
     * GROUP_RIGHT_CODE
     */
    public static final String GR_DISABLE_DETAIL = "DISABLE_DETAIL";


    /**
     * REASON_GROUP_CODE
     */
    public static final String RGC_TX_DISABLE = "TX_DISABLE";

    /**
     * REASON LIST
     */
    public static List<ReasonDto> disableReasonList = new ArrayList<ReasonDto>();

    /**
	 * 
	 */
	public static final String MODIFIER = "modifier";
	public static final String FOLLOWSET = "followset";
    public static final String POSCODE = "Mobile";
	/**
	 * Split Table List
	 */
	public static boolean isSplit = false;
    public static boolean isAutoHideSplitTable = false;

    /**
     * Cover count enabled
     */
    public static boolean isCoverCountEnabled = false;

	/**
	 * TableDto
	 */
	public static TableDto mTableDto = new TableDto();
    public static int originalTableStatusId;
    /**
     * Table Status
     */
    public static final int TABLE_STATUS_TABLE_OPEN = 1;
    public static final int TABLE_STATUS_ORDER_WAIT = 2;
    public static final int TABLE_STATUS_ORDER_LOCK = 3;
    public static final int TABLE_STATUS_ORDER_COMPLETE = 4;
    public static final int TABLE_STATUS_ORDER_CHECK = 5;
    public static final int TABLE_STATUS_ORDER_CLOSE = 6;
    public static final int TABLE_STATUS_TABLE_CLEAN = 7;
    public static final int TABLE_STATUS_TABLE_CLOSE = 8;


	/**
	 * 
	 */
	public static TxSalesHeaderDto mTxSalesHearderDto = new TxSalesHeaderDto();
	public static TxPaymentDto mTxPaymentDto = new TxPaymentDto();
	public static TxSalesDetailDto mTxSalesDetailDto = new TxSalesDetailDto();
	public static TxSalesDetailDtoNormal mTxSalesDetailDtoNormal = new TxSalesDetailDtoNormal();
	/**
	 * 订单
	 */
	public static List<OrderListBean> orderList = new ArrayList<OrderListBean>();

	public static String sectionName;
	public static String sectionId;

	/**
	 * is submited
	 */
	public static boolean isSubmit = false;
	
	/**
	 * is current order
	 */
	public static boolean isEnabled = false;

    // 20150331
    public static int maxSeqNumber = 0;
    public static int maxItemSetRunningIndex = 0;
    public static int maxItemOrderRunningIndex = 0;

    // for quick code search
    public static List<ItemMasterDto> allAvailableItemMasterDtoList = new ArrayList<ItemMasterDto>();
    public static boolean isQuickCodeTextPad = false;

}
