package com.wallet.walkthedog.dao;

public class AssetLogItem {
	private double amount;
	private int auditExtractId;
	private Object fee;
	private int type;
	private Object toAddress;
	private String memberEmail;
	private Object numberChain;
	private String createTime;
	private int dogOrProp;
	private int logId;
	private int id;
	private Object coinName;
	private Object hash;
	private int memberId;
	private int status;

	public double getAmount(){
		return amount;
	}

	public int getAuditExtractId(){
		return auditExtractId;
	}

	public Object getFee(){
		return fee;
	}

	public int getType(){
		return type;
	}

	public Object getToAddress(){
		return toAddress;
	}

	public String getMemberEmail(){
		return memberEmail;
	}

	public Object getNumberChain(){
		return numberChain;
	}

	public String getCreateTime(){
		return createTime;
	}

	public int getDogOrProp(){
		return dogOrProp;
	}

	public int getLogId(){
		return logId;
	}

	public int getId(){
		return id;
	}

	public Object getCoinName(){
		return coinName;
	}

	public Object getHash(){
		return hash;
	}

	public int getMemberId(){
		return memberId;
	}

	public int getStatus(){
		return status;
	}
}
