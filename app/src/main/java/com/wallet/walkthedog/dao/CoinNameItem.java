package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class CoinNameItem implements Serializable {
	private String unit;
	private String name;
	private String sellMinAmount;
	private String nameCn;
	private String id;
	private String sort;
	private String buyMinAmount;

	public String getUnit(){
		return unit;
	}

	public String getName(){
		return name;
	}

	public String getSellMinAmount(){
		return sellMinAmount;
	}

	public String getNameCn(){
		return nameCn;
	}

	public String getId(){
		return id;
	}

	public String getSort(){
		return sort;
	}

	public String getBuyMinAmount(){
		return buyMinAmount;
	}
}
