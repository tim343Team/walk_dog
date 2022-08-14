package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class ContentItem implements Serializable {
	private Country country;
	private int auto;
	private Object marketPrice;
	private double remainCommission;
	private String remark;
	private double number;
	private int advertiseType;
	private double premiseRate;
	private double remainAmount;
	private Object price;
	private Member member;
	private double commission;
	private int id;
	private int level;
	private String payMode;
	private double dealAmount;
	private int priceType;
	private Object autoword;
	private String updateTime;
	private double minLimit;
	private int version;
	private int timeLimit;
	private String createTime;
	private double maxLimit;
	private Object limitMoney;
	private Object coinUnit;
	private CoinDao coin;
	private int status;
	private Object username;

	public Country getCountry(){
		return country;
	}

	public int getAuto(){
		return auto;
	}

	public Object getMarketPrice(){
		return marketPrice;
	}

	public double getRemainCommission(){
		return remainCommission;
	}

	public String getRemark(){
		return remark;
	}

	public double getNumber(){
		return number;
	}

	public int getAdvertiseType(){
		return advertiseType;
	}

	public double getPremiseRate(){
		return premiseRate;
	}

	public double getRemainAmount(){
		return remainAmount;
	}

	public Object getPrice(){
		return price;
	}

	public Member getMember(){
		return member;
	}

	public double getCommission(){
		return commission;
	}

	public int getId(){
		return id;
	}

	public int getLevel(){
		return level;
	}

	public String getPayMode(){
		return payMode;
	}

	public double getDealAmount(){
		return dealAmount;
	}

	public int getPriceType(){
		return priceType;
	}

	public Object getAutoword(){
		return autoword;
	}

	public String getUpdateTime(){
		return updateTime;
	}

	public double getMinLimit(){
		return minLimit;
	}

	public int getVersion(){
		return version;
	}

	public int getTimeLimit(){
		return timeLimit;
	}

	public String getCreateTime(){
		return createTime;
	}

	public double getMaxLimit(){
		return maxLimit;
	}

	public Object getLimitMoney(){
		return limitMoney;
	}

	public Object getCoinUnit(){
		return coinUnit;
	}

	public CoinDao getCoin(){
		return coin;
	}

	public int getStatus(){
		return status;
	}

	public Object getUsername(){
		return username;
	}
}
