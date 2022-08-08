package com.wallet.walkthedog.dao;

import java.util.List;

public class AdvertiseUnitPage{
	private int totalElement;
	private int pageNumber;
	private int totalPage;
	private List<AdvertiseUnitItem> context;
	private int currentPage;

	public int getTotalElement(){
		return totalElement;
	}

	public int getPageNumber(){
		return pageNumber;
	}

	public int getTotalPage(){
		return totalPage;
	}

	public List<AdvertiseUnitItem> getContext(){
		return context;
	}

	public int getCurrentPage(){
		return currentPage;
	}
}