package com.wallet.walkthedog.dao;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TrainRecordDao{

	@SerializedName("current")
	private int current;

	@SerializedName("total")
	private int total;

	@SerializedName("hitCount")
	private boolean hitCount;

	@SerializedName("pages")
	private int pages;

	@SerializedName("size")
	private int size;

	@SerializedName("records")
	private List<TrainRecordItem> records;

	@SerializedName("searchCount")
	private boolean searchCount;

	@SerializedName("orders")
	private List<OrdersItem> orders;

	public void setCurrent(int current){
		this.current = current;
	}

	public int getCurrent(){
		return current;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setHitCount(boolean hitCount){
		this.hitCount = hitCount;
	}

	public boolean isHitCount(){
		return hitCount;
	}

	public void setPages(int pages){
		this.pages = pages;
	}

	public int getPages(){
		return pages;
	}

	public void setSize(int size){
		this.size = size;
	}

	public int getSize(){
		return size;
	}

	public void setRecords(List<TrainRecordItem> records){
		this.records = records;
	}

	public List<TrainRecordItem> getRecords(){
		return records;
	}

	public void setSearchCount(boolean searchCount){
		this.searchCount = searchCount;
	}

	public boolean isSearchCount(){
		return searchCount;
	}

	public void setOrders(List<OrdersItem> orders){
		this.orders = orders;
	}

	public List<OrdersItem> getOrders(){
		return orders;
	}
}