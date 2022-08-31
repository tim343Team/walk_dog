package com.wallet.walkthedog.dao;

public class BankCarInfo{
	private String bank;
	private String branch;
	private String cardNo;
	private String realName;

	public String getBank(){
		return bank;
	}

	public String getBranch(){
		return branch;
	}

	public String getCardNo(){
		return cardNo;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}
