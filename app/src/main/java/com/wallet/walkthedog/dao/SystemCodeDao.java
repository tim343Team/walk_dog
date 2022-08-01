package com.wallet.walkthedog.dao;

public class SystemCodeDao{
	private String code;
	private String data;
	private String createTime;
	private String updateTime;
	private int id;
	private int type;
	private String value;

	public String getCode(){
		return code;
	}

	public String getData(){
		return data;
	}

	public String getCreateTime(){
		return createTime;
	}

	public String getUpdateTime(){
		return updateTime;
	}

	public int getId(){
		return id;
	}

	public int getType(){
		return type;
	}

	public String getValue(){
		return value;
	}
}
