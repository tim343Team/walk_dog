package com.wallet.walkthedog.dao;

import java.util.List;

public class ADRecordDao{
	private int number;
	private boolean last;
	private int numberOfElements;
	private int size;
	private int totalPages;
	private List<ContentItem> content;
	private boolean first;
	private int totalElements;

	public int getNumber(){
		return number;
	}

	public boolean isLast(){
		return last;
	}

	public int getNumberOfElements(){
		return numberOfElements;
	}

	public int getSize(){
		return size;
	}

	public int getTotalPages(){
		return totalPages;
	}


	public List<ContentItem> getContent(){
		return content;
	}

	public boolean isFirst(){
		return first;
	}

	public int getTotalElements(){
		return totalElements;
	}
}