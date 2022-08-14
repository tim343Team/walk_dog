package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class OtherAssetDao implements Serializable {
	private int isLock;
	private String balance;
	private double frozenBalance;
	private int id;
	private int version;
	private double releaseBalance;
	private int memberId;
	private OtherAssetCoinDao coin;

	public int getIsLock(){
		return isLock;
	}

	public String getBalance(){
		return balance;
	}

	public double getFrozenBalance(){
		return frozenBalance;
	}

	public int getId(){
		return id;
	}

	public int getVersion(){
		return version;
	}

	public double getReleaseBalance(){
		return releaseBalance;
	}

	public int getMemberId(){
		return memberId;
	}

	public OtherAssetCoinDao getCoin(){
		return coin;
	}
}
