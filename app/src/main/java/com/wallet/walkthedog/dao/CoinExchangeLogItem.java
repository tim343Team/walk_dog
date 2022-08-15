package com.wallet.walkthedog.dao;

public class CoinExchangeLogItem{
	private Object realFee;
	private String symbol;
	private String amount;
	private String address;
	private int flag;
	private Object discountFee;
	private int fee;
	private int type;
	private String memberEmail;
	private String createTime;
	private int id;
	private Object airdropId;
	private int memberId;

	public Object getRealFee(){
		return realFee;
	}

	public String getSymbol(){
		return symbol;
	}

	public String getAmount(){
		return amount;
	}

	public String getAddress(){
		return address;
	}

	public int getFlag(){
		return flag;
	}

	public Object getDiscountFee(){
		return discountFee;
	}

	public int getFee(){
		return fee;
	}

	public int getType(){
		return type;
	}

	public String getMemberEmail(){
		return memberEmail;
	}

	public String getCreateTime(){
		return createTime;
	}

	public int getId(){
		return id;
	}

	public Object getAirdropId(){
		return airdropId;
	}

	public int getMemberId(){
		return memberId;
	}
}
