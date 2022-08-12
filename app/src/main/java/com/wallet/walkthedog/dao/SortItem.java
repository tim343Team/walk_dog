package com.wallet.walkthedog.dao;

public class SortItem{
	private String nullHandling;
	private boolean ignoreCase;
	private String property;
	private boolean ascending;
	private boolean descending;
	private String direction;

	public String getNullHandling(){
		return nullHandling;
	}

	public boolean isIgnoreCase(){
		return ignoreCase;
	}

	public String getProperty(){
		return property;
	}

	public boolean isAscending(){
		return ascending;
	}

	public boolean isDescending(){
		return descending;
	}

	public String getDirection(){
		return direction;
	}
}
