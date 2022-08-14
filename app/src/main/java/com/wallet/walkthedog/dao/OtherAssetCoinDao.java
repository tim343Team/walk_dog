package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class OtherAssetCoinDao implements Serializable {
	private Object masterAddress;
	private double txFeeRatio;
	private int canTransfer;
	private int canWithdraw;
	private int type;
	private double maxDailyWithdrawRate;
	private String circulatingSupply;
	private double maxWithdrawAmount;
	private double usdRate;
	private Object coinInfo;
	private String coinType;
	private Object publishTime;
	private Object whitepaperUrl;
	private int txFeeType;
	private int isSpotWallet;
	private int withdrawScale;
	private int sort;
	private Object nameEn;
	private String rpcName;
	private String unit;
	private int isPlatformCoin;
	private int isCoinType;
	private String name;
	private double withdrawThreshold;
	private Object parentCoin;
	private int enableRpc;
	private int status;
	private double minerFee;
	private int canWithdrawHasAddress;
	private Object sgdRate;
	private Object allBalance;
	private int isContractWallet;
	private Object hotAllBalance;
	private int isOtcWallet;
	private Object isGift;
	private int canAutoWithdraw;
	private Object blockExplore;
	private Object icoPrice;
	private double minWithdrawAmount;
	private Object website;
	private double cnyRate;
	private String totalSupply;
	private String nameCn;
	private boolean hasLegal;
	private int canRecharge;
	private double minRechargeAmount;
	private double txFee;
	private String coldWalletAddress;
	private String exchangeUnit;

	public Object getMasterAddress(){
		return masterAddress;
	}

	public double getTxFeeRatio(){
		return txFeeRatio;
	}

	public int getCanTransfer(){
		return canTransfer;
	}

	public int getCanWithdraw(){
		return canWithdraw;
	}

	public int getType(){
		return type;
	}

	public double getMaxDailyWithdrawRate(){
		return maxDailyWithdrawRate;
	}

	public String getCirculatingSupply(){
		return circulatingSupply;
	}

	public double getMaxWithdrawAmount(){
		return maxWithdrawAmount;
	}

	public double getUsdRate(){
		return usdRate;
	}

	public Object getCoinInfo(){
		return coinInfo;
	}

	public String getCoinType(){
		return coinType;
	}

	public Object getPublishTime(){
		return publishTime;
	}

	public Object getWhitepaperUrl(){
		return whitepaperUrl;
	}

	public int getTxFeeType(){
		return txFeeType;
	}

	public int getIsSpotWallet(){
		return isSpotWallet;
	}

	public int getWithdrawScale(){
		return withdrawScale;
	}

	public int getSort(){
		return sort;
	}

	public Object getNameEn(){
		return nameEn;
	}

	public String getRpcName(){
		return rpcName;
	}

	public String getUnit(){
		return unit;
	}

	public int getIsPlatformCoin(){
		return isPlatformCoin;
	}

	public int getIsCoinType(){
		return isCoinType;
	}

	public String getName(){
		return name;
	}

	public double getWithdrawThreshold(){
		return withdrawThreshold;
	}

	public Object getParentCoin(){
		return parentCoin;
	}

	public int getEnableRpc(){
		return enableRpc;
	}

	public int getStatus(){
		return status;
	}

	public double getMinerFee(){
		return minerFee;
	}

	public int getCanWithdrawHasAddress(){
		return canWithdrawHasAddress;
	}

	public Object getSgdRate(){
		return sgdRate;
	}

	public Object getAllBalance(){
		return allBalance;
	}

	public int getIsContractWallet(){
		return isContractWallet;
	}

	public Object getHotAllBalance(){
		return hotAllBalance;
	}

	public int getIsOtcWallet(){
		return isOtcWallet;
	}

	public Object getIsGift(){
		return isGift;
	}

	public int getCanAutoWithdraw(){
		return canAutoWithdraw;
	}

	public Object getBlockExplore(){
		return blockExplore;
	}

	public Object getIcoPrice(){
		return icoPrice;
	}

	public double getMinWithdrawAmount(){
		return minWithdrawAmount;
	}

	public Object getWebsite(){
		return website;
	}

	public double getCnyRate(){
		return cnyRate;
	}

	public String getTotalSupply(){
		return totalSupply;
	}

	public String getNameCn(){
		return nameCn;
	}

	public boolean isHasLegal(){
		return hasLegal;
	}

	public int getCanRecharge(){
		return canRecharge;
	}

	public double getMinRechargeAmount(){
		return minRechargeAmount;
	}

	public double getTxFee(){
		return txFee;
	}

	public String getColdWalletAddress(){
		return coldWalletAddress;
	}

	public String getExchangeUnit(){
		return exchangeUnit;
	}
}
