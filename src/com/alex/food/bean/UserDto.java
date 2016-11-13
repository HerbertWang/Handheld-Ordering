package com.alex.food.bean;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * 
 * @author ALEX
 *
 */
public class UserDto implements KvmSerializable {
	private String AccountId;
	private String CardNo;
	private String CreatedBy;
	private String CreatedDate;
	private String EffectiveDateFrom;
	private String EffectiveDateTo;
	private String EnableCardNoLogin;
	private String EnableStaffCodeLogin;
	private String EnableUserIdLogin;
	private String Enabled;
	private String ModifiedBy;
	private String ModifiedDate;
	private String ShopId;
	private String StaffCode;
	private String UserAltName;
	private String UserId;
	private String UserName;

	public String getAccountId() {
		return AccountId;
	}

	public void setAccountId(String accountId) {
		AccountId = accountId;
	}

	public String getCardNo() {
		return CardNo;
	}

	public void setCardNo(String cardNo) {
		CardNo = cardNo;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}

	public String getEffectiveDateFrom() {
		return EffectiveDateFrom;
	}

	public void setEffectiveDateFrom(String effectiveDateFrom) {
		EffectiveDateFrom = effectiveDateFrom;
	}

	public String getEffectiveDateTo() {
		return EffectiveDateTo;
	}

	public void setEffectiveDateTo(String effectiveDateTo) {
		EffectiveDateTo = effectiveDateTo;
	}

	public String getEnableCardNoLogin() {
		return EnableCardNoLogin;
	}

	public void setEnableCardNoLogin(String enableCardNoLogin) {
		EnableCardNoLogin = enableCardNoLogin;
	}

	public String getEnableStaffCodeLogin() {
		return EnableStaffCodeLogin;
	}

	public void setEnableStaffCodeLogin(String enableStaffCodeLogin) {
		EnableStaffCodeLogin = enableStaffCodeLogin;
	}

	public String getEnableUserIdLogin() {
		return EnableUserIdLogin;
	}

	public void setEnableUserIdLogin(String enableUserIdLogin) {
		EnableUserIdLogin = enableUserIdLogin;
	}

	public String getEnabled() {
		return Enabled;
	}

	public void setEnabled(String enabled) {
		Enabled = enabled;
	}

	public String getModifiedBy() {
		return ModifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}

	public String getModifiedDate() {
		return ModifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		ModifiedDate = modifiedDate;
	}

	public String getShopId() {
		return ShopId;
	}

	public void setShopId(String shopId) {
		ShopId = shopId;
	}

	public String getStaffCode() {
		return StaffCode;
	}

	public void setStaffCode(String staffCode) {
		StaffCode = staffCode;
	}

	public String getUserAltName() {
		return UserAltName;
	}

	public void setUserAltName(String userAltName) {
		UserAltName = userAltName;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	@Override
	public Object getProperty(int arg0) {
		Object obj = null;
		switch (arg0) {
		case 0:
			obj = this.AccountId;
			break;
		case 1:
			obj = this.CardNo;
			break;
		case 2:
			obj = this.CreatedBy;
			break;
		case 3:
			obj = this.CreatedDate;
			break;
		case 4:
			obj = this.EffectiveDateFrom;
			break;
		case 5:
			obj = this.EffectiveDateTo;
			break;
		case 6:
			obj = this.EnableCardNoLogin;
			break;
		case 7:
			obj = this.EnableStaffCodeLogin;
			break;
		case 8:
			obj = this.EnableUserIdLogin;
			break;
		case 9:
			obj = this.Enabled;
			break;
		case 10:
			obj = this.ModifiedBy;
			break;
		case 11:
			obj = this.ModifiedDate;
			break;
		case 12:
			obj = this.ShopId;
			break;
		case 13:
			obj = this.StaffCode;
			break;
		case 14:
			obj = this.UserAltName;
			break;
		case 15:
			obj = this.UserId;
			break;
		case 16:
			obj = this.UserName;
			break;
		}
		return obj;
	}

	@Override
	public int getPropertyCount() {
		return 17;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo info) {
		switch (arg0) {
		case 0:
			info.name = "AccountId";
			// info.type = PropertyInfo.INTEGER_CLASS;
			break;
		case 1:
			info.name = "CardNo";
			// info.type = PropertyInfo.STRING_CLASS;
			break;
		case 2:
			info.name = "CreatedBy";
			// info.type = PropertyInfo.STRING_CLASS;
			break;
		case 3:
			info.name = "CreatedDate";
			// info.type = PropertyInfo.OBJECT_CLASS;
			break;
		case 4:
			info.name = "EffectiveDateFrom";
			// info.type = PropertyInfo.OBJECT_CLASS;
			break;
		case 5:
			info.name = "EffectiveDateTo";
			// info.type = PropertyInfo.OBJECT_CLASS;
			break;
		case 6:
			info.name = "EnableCardNoLogin";
			// info.type = PropertyInfo.BOOLEAN_CLASS;
			break;
		case 7:
			info.name = "EnableStaffCodeLogin";
			// info.type = PropertyInfo.BOOLEAN_CLASS;
			break;
		case 8:
			info.name = "EnableUserIdLogin";
			// info.type = PropertyInfo.BOOLEAN_CLASS;
			break;
		case 9:
			info.name = "Enabled";
			// info.type = PropertyInfo.BOOLEAN_CLASS;
			break;
		case 10:
			info.name = "ModifiedBy";
			// info.type = PropertyInfo.STRING_CLASS;
			break;
		case 11:
			info.name = "ModifiedDate";
			// info.type = PropertyInfo.OBJECT_CLASS;
			break;
		case 12:
			info.name = "ShopId";
			// info.type = PropertyInfo.INTEGER_CLASS;
			break;
		case 13:
			info.name = "StaffCode";
			// info.type = PropertyInfo.STRING_CLASS;
			break;
		case 14:
			info.name = "UserAltName";
			// info.type = PropertyInfo.STRING_CLASS;
			break;
		case 15:
			info.name = "UserId";
			// info.type = PropertyInfo.INTEGER_CLASS;
			break;
		case 16:
			info.name = "UserName";
			// info.type = PropertyInfo.STRING_CLASS;
			break;
		}
	}

	@Override
	public void setProperty(int arg0, Object object) {
		switch (arg0) {
		case 0:
			this.AccountId = object.toString();
			break;
		case 1:
			this.CardNo = object.toString();
			break;
		case 2:
			this.CreatedBy = object.toString();
			break;
		case 3:
			this.CreatedDate = object.toString();
			break;
		case 4:
			this.EffectiveDateFrom = object.toString();
			break;
		case 5:
			this.EffectiveDateTo = object.toString();
			break;
		case 6:
			this.EnableCardNoLogin = object.toString();
			break;
		case 7:
			this.EnableStaffCodeLogin = object.toString();
			break;
		case 8:
			this.EnableUserIdLogin = object.toString();
			break;
		case 9:
			this.Enabled = object.toString();
			break;
		case 10:
			this.ModifiedBy = object.toString();
			break;
		case 11:
			this.ModifiedDate = object.toString();
			break;
		case 12:
			this.ShopId = object.toString();
			break;
		case 13:
			this.StaffCode = object.toString();
			break;
		case 14:
			this.UserAltName = object.toString();
			break;
		case 15:
			this.UserId = object.toString();
			break;
		case 16:
			this.UserName = object.toString();
			break;
		}
	}
}
