package com.wallet.walkthedog.dao;

public class PayInfo{
	private String realName;
	private BankCarInfo bankInfo;
	private Object alipay;
	private Object wechatPay;
	private Object swift;

	public String getRealName(){
		return realName;
	}

	public BankCarInfo getBankInfo(){
		return bankInfo;
	}

	public Object getAlipay(){
		return alipay;
	}

	public Object getWechatPay(){
		return wechatPay;
	}

	public Object getSwift(){
		return swift;
	}
}
