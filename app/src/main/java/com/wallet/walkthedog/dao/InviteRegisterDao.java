package com.wallet.walkthedog.dao;

import java.util.List;

public class InviteRegisterDao{
	private int total;
	private int current;
	private boolean hitCount;
	private int pages;
	private int size;
	private List<InviteRegisterItem> records;
	private boolean searchCount;
	private List<Object> orders;

	public int getTotal(){
		return total;
	}

	public int getCurrent(){
		return current;
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

	public List<InviteRegisterItem> getRecords(){
		return records;
	}

	public boolean isSearchCount(){
		return searchCount;
	}

	public List<Object> getOrders(){
		return orders;
	}
}