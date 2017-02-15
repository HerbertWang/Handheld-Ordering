package com.everyware.handheld.utils;
/**
 * 
 * @author ALEX
 *
 */
public class StringUtils {

	public static String getTableListPostString() {
		return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\""
				+ "xmlns:arr=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" xmlns:pos=\"http://schemas.datacontract.org/2004/07/POS.WebService.Common\">"
				+ "<soapenv:Header/>"
				+ "<soapenv:Body>"
				+ "<tem:GetAvailableTableList>"
				+ "<tem:accountId>79</tem:accountId><tem:shopId>1</tem:shopId><tem:sectionId>1</tem:sectionId>"
				+ "<tem:tableTypeIdList>"
				+ "<arr:int>1</arr:int>"
				+ "</tem:tableTypeIdList>"
				+ "<tem:loginUser>"
				+ "<pos:AccountId>79</pos:AccountId><pos:CardNo>8888</pos:CardNo>"
				+ "<pos:CreatedBy>everyware079</pos:CreatedBy>"
				+ "<pos:CreatedDate>2014-08-08T14:29:05.933</pos:CreatedDate>"
				+ "<pos:EffectiveDateFrom xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>"
				+ "<pos:EffectiveDateTo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>"
				+ "<pos:EnableCardNoLogin>true</pos:EnableCardNoLogin>"
				+ "<pos:EnableStaffCodeLogin>true</pos:EnableStaffCodeLogin>"
				+ "<pos:EnableUserIdLogin>false</pos:EnableUserIdLogin>"
				+ "<pos:Enabled>true</pos:Enabled>"
				+ "<pos:ModifiedBy>everyware079</pos:ModifiedBy>"
				+ "<pos:ModifiedDate>2014-08-08T14:29:05.933</pos:ModifiedDate><pos:ShopId>1</pos:ShopId><pos:StaffCode>8888</pos:StaffCode>"
				+ "<pos:UserAltName>Boss</pos:UserAltName>"
				+ "<pos:UserId>3</pos:UserId><pos:UserName>Boss</pos:UserName>"
				+ "</tem:loginUser>"
				+ "<tem:IsAppearOnFloorPlan>false</tem:IsAppearOnFloorPlan>"
				+ "</tem:GetAvailableTableList>"
				+ "</soapenv:Body>"
				+ "</soapenv:Envelope>";
	}
}
