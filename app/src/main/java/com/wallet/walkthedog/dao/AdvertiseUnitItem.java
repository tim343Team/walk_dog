package com.wallet.walkthedog.dao;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdvertiseUnitItem implements Serializable {
	private String level;
	private String payMode;
	private String memberName;
	private double minLimit;
	private int advType;
	private String avatar;
	private int transactions;
	private int advertiseId;
	private int coinId;
	private String unit;
	private int advertiseType;
	private String legalCurrency;
	private double maxLimit;
	@SerializedName("remainAmount")
	private double remainQuantity;
	private String createTime;
	private Object premiseRate;
	private double price;
	private String coinNameCn;
	private String coinName;

	public String getLevel(){
		return level;
	}

	public String getPayMode(){
		return payMode;
	}

	public String getMemberName(){
		return memberName;
	}

	public double getMinLimit(){
		return minLimit;
	}

	public int getAdvType(){
		return advType;
	}

	public String getAvatar(){
		return avatar;
	}

	public int getTransactions(){
		return transactions;
	}

	public int getAdvertiseId(){
		return advertiseId;
	}

	public int getCoinId(){
		return coinId;
	}

	public String getUnit(){
		return unit;
	}

	public int getAdvertiseType(){
		return advertiseType;
	}

	public String getLegalCurrency(){
		return legalCurrency;
	}

	public double getMaxLimit(){
		return maxLimit;
	}

	public double getRemainQuantity(){
		return remainQuantity;
	}

	public String getCreateTime(){
		return createTime;
	}

	public Object getPremiseRate(){
		return premiseRate;
	}

	public double getPrice(){
		return price;
	}

	public String getCoinNameCn(){
		return coinNameCn;
	}

	public String getCoinName(){
		return coinName;
	}
}
