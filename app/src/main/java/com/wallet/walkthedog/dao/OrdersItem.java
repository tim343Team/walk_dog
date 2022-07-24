package com.wallet.walkthedog.dao;

import com.google.gson.annotations.SerializedName;

public class OrdersItem{

	@SerializedName("asc")
	private boolean asc;

	@SerializedName("column")
	private String column;

	public void setAsc(boolean asc){
		this.asc = asc;
	}

	public boolean isAsc(){
		return asc;
	}

	public void setColumn(String column){
		this.column = column;
	}

	public String getColumn(){
		return column;
	}
}