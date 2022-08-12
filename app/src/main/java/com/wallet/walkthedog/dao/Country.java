package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class Country implements Serializable {
	private String areaCode;
	private String zhName;
	private String enName;
	private String language;
	private String localCurrency;
	private int sort;

	public String getAreaCode(){
		return areaCode;
	}

	public String getZhName(){
		return zhName;
	}

	public String getEnName(){
		return enName;
	}

	public String getLanguage(){
		return language;
	}

	public String getLocalCurrency(){
		return localCurrency;
	}

	public int getSort(){
		return sort;
	}
}
