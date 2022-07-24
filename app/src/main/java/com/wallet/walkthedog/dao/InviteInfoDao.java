package com.wallet.walkthedog.dao;

import java.util.List;

public class InviteInfoDao{
	private int current;
	private int total;
	private boolean hitCount;
	private int pages;
	private int size;
	private List<InviteInfoItem> records;
	private boolean searchCount;
	private List<OrdersItem> orders;

	public int getCurrent(){
		return current;
	}

	public int getTotal(){
		return total;
	}

	public boolean isHitCount(){
		return hitCount;
	}

	public int getPages(){
		return pages;
	}

	public int getSize(){
		return size;
	}

	public List<InviteInfoItem> getRecords(){
		return records;
	}

	public boolean isSearchCount(){
		return searchCount;
	}

	public List<OrdersItem> getOrders(){
		return orders;
	}
}