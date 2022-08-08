package com.wallet.walkthedog.dao;

public class OTCOrderDetailDao{
	private double amount;
	private String otherSide;
	private String orderSn;
	private Object payTime;
	private int hisId;
	private String remark;
	private int type;
	private int timeLimit;
	private String unit;
	private double money;
	private int myId;
	private String createTime;
	private String referenceNumber;
	private double price;
	private double commission;
	private PayInfo payInfo;
	private int status;

	public double getAmount(){
		return amount;
	}

	public String getOtherSide(){
		return otherSide;
	}

	public String getOrderSn(){
		return orderSn;
	}

	public Object getPayTime(){
		return payTime;
	}

	public int getHisId(){
		return hisId;
	}

	public String getRemark(){
		return remark;
	}

	public int getType(){
		return type;
	}

	public int getTimeLimit(){
		return timeLimit;
	}

	public String getUnit(){
		return unit;
	}

	public double getMoney(){
		return money;
	}

	public int getMyId(){
		return myId;
	}

	public String getCreateTime(){
		return createTime;
	}

	public String getReferenceNumber(){
		return referenceNumber;
	}

	public double getPrice(){
		return price;
	}

	public double getCommission(){
		return commission;
	}

	public PayInfo getPayInfo(){
		return payInfo;
	}

	public int getStatus(){
		return status;
	}
}
