package com.everyware.handheld.utils;

/**
 * 
 * @author ALEX
 * 
 */
public class SoapUtils {
	/**
	 * target_namespace
	 */
	public static final String TARGET_NAMESPACE = "http://tempuri.org/";
	/**
	 * soap_ation_head
	 */
	public static final String SOAP_ACTION_HEAD = "http://tempuri.org/ICommonWebService/";
	/**
	 * response result
	 */
	public static String REQUEST_METHOD_RESULT = "%sResult";
	public static final String RESULT_LIST = "resultList";
    public static final String ITEM_MASTER_DTO_LIST = "itemMasterDtoList";
	/**
	 * NameSpace
	 */
	public static final String ARRAYS_NAMESPACE = "http://schemas.microsoft.com/2003/10/Serialization/Arrays";
	public static final String POS_NAMESPACE = "http://schemas.datacontract.org/2004/07/POS.WebService.Common";
	/**
	 * method_name
	 */
	public static final String GET_AVAILABLE_MENU_LIST="GetAvailableMenuList";
    public static final String GET_LOGIN_USER_INFORMATION = "GetLoginUserInformation";
	public static final String GET_AVAILABLE_TABLE_SECTION_LIST = "GetAvailableTableSectionList";
	public static final String GET_AVAILABLE_TABLE_LIST = "GetAvailableTableList";
    public static final String GET_UPDATE_TABLE_LIST = "GetUpdatedTableList";
	public static final String GET_AVAILABLE_SPLIT_TABLE_LIST = "GetAvailableSplitTableList";
	public static final String GET_UPDATED_TABLE_LIST = "GetUpdatedTableList";
	public static final String Get_AVAILABLE_ITEM_CATEGORY_LIST = "GetAvailableItemCategoryList";
	public static final String CLEAN_TABLE_DONE = "CleanTableDone";
	public static final String GET_AVAILABLE_ITEM_LIST = "GetAvailableItemList";
    public static final String GET_ALL_AVAILABLE_ITEM_LIST = "GetAllAvailableItemList";
    public static final String GET_ITEM_LIST_BY_ITEM_CODE_ITEM_NAME = "GetItemListByItemCodeItemName";
	public static final String GET_AVAILABLE_MODIFIER_ITEM_LIST = "GetAvailableModifierItemList";
	public static final String GET_NEXT_LEVEL_FOLLOW_SET_ITEM_LIST = "GetNextLevelFollowSetItemList";
	public static final String GET_CURRENT_TX_SALEX = "GetCurrentTxSales";
	public static final String GET_ITEM_BY_ITEM_ID = "GetItemByItemId";
	public static final String SAVE_TX_SALES = "SaveTxSales";
    public static final String SET_TABLE_STATUS_BY_TABLE_ID = "SetTableStatusByTableId";
    public static final String CHECK_USER_GROUP_RIGHT = "CheckUserGroupRight";
    public static final String GET_AVAILABLE_REASON_LIST = "GetAvailableReasonList";
    public static final String CHANGE_TX_TABLE_MOBILE = "ChangeTxTableMobile";
}
