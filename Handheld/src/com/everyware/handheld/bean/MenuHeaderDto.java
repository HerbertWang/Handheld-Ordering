package com.everyware.handheld.bean;

public class MenuHeaderDto {
	private String AccountId;
	private String DisplayOrder;
	private String IsBuiltIn;
	private String MenuId;
	private String MenuName;
	private String MenuNameAlt;
	
	


    @Override
    public  MenuHeaderDto  clone() {
    	 MenuHeaderDto  newMenuHeaderDto  = new  MenuHeaderDto ();

        newMenuHeaderDto.AccountId             = this.AccountId               ;
        newMenuHeaderDto.DisplayOrder          = this.DisplayOrder            ;
        newMenuHeaderDto.IsBuiltIn             = this.IsBuiltIn               ;
        newMenuHeaderDto.MenuId                = this.MenuId                  ;
        newMenuHeaderDto.MenuName              = this.MenuName                ;
        newMenuHeaderDto.MenuNameAlt           = this.MenuNameAlt             ;

        return newMenuHeaderDto;
    }

	public String getAccountId() {
		return AccountId;
	}

	public void setAccountId(String accountid) {
		AccountId = accountid;
	}

	public String getDisplayOrder() {
		return DisplayOrder;
	}

	public void setDisplayOrder(String displayorder) {
		DisplayOrder = displayorder;
	}

	public String getIsBuiltIn() {
		return IsBuiltIn  ;
	}

	public void setIsBuiltIn (String isbuiltin) {
		IsBuiltIn = isbuiltin;
	}

	public String getmenuid() {
		return MenuId;
	}

	public void setMenuId(String menuid) {
		MenuId = menuid;
	}

	public String getMenuName() {
		return MenuName;
	}

	public void setMenuName(String menuname) {
		MenuName = menuname;
	}

	public String getMenuNameAlt() {
		return MenuNameAlt;
	}

	public void setMenuNameAlt(String menunamealt) {
		MenuNameAlt= menunamealt;
	}

}