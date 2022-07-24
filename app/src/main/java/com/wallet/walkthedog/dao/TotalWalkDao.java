package com.wallet.walkthedog.dao;

import com.google.gson.annotations.SerializedName;

public class TotalWalkDao{

	@SerializedName("friendTime")
	private int friendTime;

	@SerializedName("friendBox")
	private int friendBox;

	@SerializedName("friendCount")
	private int friendCount;

	@SerializedName("count")
	private String count;

	@SerializedName("friendProp")
	private int friendProp;

	@SerializedName("walkTheDogTime")
	private String walkTheDogTime;

	@SerializedName("box")
	private int box;

	@SerializedName("popularityArea")
	private int popularityArea;

	@SerializedName("friendKm")
	private int friendKm;

	@SerializedName("friendDogFood")
	private int friendDogFood;

	@SerializedName("friendRubbish")
	private int friendRubbish;

	@SerializedName("unknownArea")
	private int unknownArea;

	@SerializedName("friendWalkDogCount")
	private int friendWalkDogCount;

	@SerializedName("prop")
	private int prop;

	@SerializedName("rubbish")
	private int rubbish;

	@SerializedName("dogFood")
	private int dogFood;

	@SerializedName("middlArea")
	private int middlArea;

	@SerializedName("walkTheDogKm")
	private String walkTheDogKm;

	public void setFriendTime(int friendTime){
		this.friendTime = friendTime;
	}

	public int getFriendTime(){
		return friendTime;
	}

	public void setFriendBox(int friendBox){
		this.friendBox = friendBox;
	}

	public int getFriendBox(){
		return friendBox;
	}

	public void setFriendCount(int friendCount){
		this.friendCount = friendCount;
	}

	public int getFriendCount(){
		return friendCount;
	}

	public void setCount(String count){
		this.count = count;
	}

	public String getCount(){
		return count;
	}

	public void setFriendProp(int friendProp){
		this.friendProp = friendProp;
	}

	public int getFriendProp(){
		return friendProp;
	}

	public void setWalkTheDogTime(String walkTheDogTime){
		this.walkTheDogTime = walkTheDogTime;
	}

	public String getWalkTheDogTime(){
		return walkTheDogTime;
	}

	public void setBox(int box){
		this.box = box;
	}

	public int getBox(){
		return box;
	}

	public void setPopularityArea(int popularityArea){
		this.popularityArea = popularityArea;
	}

	public int getPopularityArea(){
		return popularityArea;
	}

	public void setFriendKm(int friendKm){
		this.friendKm = friendKm;
	}

	public int getFriendKm(){
		return friendKm;
	}

	public void setFriendDogFood(int friendDogFood){
		this.friendDogFood = friendDogFood;
	}

	public int getFriendDogFood(){
		return friendDogFood;
	}

	public void setFriendRubbish(int friendRubbish){
		this.friendRubbish = friendRubbish;
	}

	public int getFriendRubbish(){
		return friendRubbish;
	}

	public void setUnknownArea(int unknownArea){
		this.unknownArea = unknownArea;
	}

	public int getUnknownArea(){
		return unknownArea;
	}

	public void setFriendWalkDogCount(int friendWalkDogCount){
		this.friendWalkDogCount = friendWalkDogCount;
	}

	public int getFriendWalkDogCount(){
		return friendWalkDogCount;
	}

	public void setProp(int prop){
		this.prop = prop;
	}

	public int getProp(){
		return prop;
	}

	public void setRubbish(int rubbish){
		this.rubbish = rubbish;
	}

	public int getRubbish(){
		return rubbish;
	}

	public void setDogFood(int dogFood){
		this.dogFood = dogFood;
	}

	public int getDogFood(){
		return dogFood;
	}

	public void setMiddlArea(int middlArea){
		this.middlArea = middlArea;
	}

	public int getMiddlArea(){
		return middlArea;
	}

	public void setWalkTheDogKm(String walkTheDogKm){
		this.walkTheDogKm = walkTheDogKm;
	}

	public String getWalkTheDogKm(){
		return walkTheDogKm;
	}
}