package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class Coin implements Serializable {
	private int coinScale;
	private String nameCn;
	private String updateTime;
	private int sort;
	private int maxVolume;
	private String unit;
	private int isPlatformCoin;
	private String createTime;
	private Object price;
	private String name;
	private int maxTradingTime;
	private int id;
	private double jyRate;
	private double sellMinAmount;
	private double buyMinAmount;
	private int status;

	public int getCoinScale(){
		return coinScale;
	}

	public String getNameCn(){
		return nameCn;
	}

	public String getUpdateTime(){
		return updateTime;
	}

	public int getSort(){
		return sort;
	}

	public int getMaxVolume(){
		return maxVolume;
	}

	public String getUnit(){
		return unit;
	}

	public int getIsPlatformCoin(){
		return isPlatformCoin;
	}

	public String getCreateTime(){
		return createTime;
	}

	public Object getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}

	public int getMaxTradingTime(){
		return maxTradingTime;
	}

	public int getId(){
		return id;
	}

	public double getJyRate(){
		return jyRate;
	}

	public double getSellMinAmount(){
		return sellMinAmount;
	}

	public double getBuyMinAmount(){
		return buyMinAmount;
	}

	public int getStatus(){
		return status;
	}
}
