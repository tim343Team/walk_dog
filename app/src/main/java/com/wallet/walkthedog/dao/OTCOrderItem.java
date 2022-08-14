package com.wallet.walkthedog.dao;

public class OTCOrderItem{
	private String amount;
	private String orderSn;
	private Object avatar;
	private int type;
	private String realName;
	private String unit;
	private String money;
	private String createTime;
	private double price;
	private String name;
	private String commission;
	private int status;
	private int memberId;

	public String getAmount(){
		return amount;
	}

	public String getOrderSn(){
		return orderSn;
	}

	public Object getAvatar(){
		return avatar;
	}

	public int getType(){
		return type;
	}

	public String getRealName(){
		return realName;
	}

	public String getUnit(){
		return unit;
	}

	public String getMoney(){
		return money;
	}

	public String getCreateTime(){
		return createTime;
	}

	public double getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}

	public String getCommission(){
		return commission;
	}

	public int getStatus(){
		return status;
	}

	public int getMemberId(){
		return memberId;
	}
}
