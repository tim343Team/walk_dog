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
	private long walkTheDogTime;

	@SerializedName("box")
	private int box;

	@SerializedName("popularityArea")
	private double popularityArea;

	@SerializedName("friendKm")
	private double friendKm;

	@SerializedName("friendDogFood")
	private String friendDogFood;

	@SerializedName("friendRubbish")
	private int friendRubbish;

	@SerializedName("unknownArea")
	private double unknownArea;

	@SerializedName("friendWalkDogCount")
	private int friendWalkDogCount;

	@SerializedName("prop")
	private int prop;

	@SerializedName("rubbish")
	private int rubbish;

	@SerializedName("dogFood")
	private String dogFood;

	@SerializedName("middlArea")
	private double middlArea;

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

	public void setWalkTheDogTime(long walkTheDogTime){
		this.walkTheDogTime = walkTheDogTime;
	}

	public long getWalkTheDogTime(){
		return walkTheDogTime;
	}

	public void setBox(int box){
		this.box = box;
	}

	public int getBox(){
		return box;
	}

	public void setPopularityArea(double popularityArea){
		this.popularityArea = popularityArea;
	}

	public double getPopularityArea(){
		return popularityArea;
	}

	public void setFriendKm(double friendKm){
		this.friendKm = friendKm;
	}

	public double getFriendKm(){
		return friendKm;
	}

	public String getFriendDogFood() {
		return friendDogFood;
	}

	public void setFriendDogFood(String friendDogFood) {
		this.friendDogFood = friendDogFood;
	}

	public void setFriendRubbish(int friendRubbish){
		this.friendRubbish = friendRubbish;
	}

	public int getFriendRubbish(){
		return friendRubbish;
	}

	public void setUnknownArea(double unknownArea){
		this.unknownArea = unknownArea;
	}

	public double getUnknownArea(){
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

	public String getDogFood() {
		return dogFood;
	}

	public void setDogFood(String dogFood) {
		this.dogFood = dogFood;
	}

	public void setMiddlArea(double middlArea){
		this.middlArea = middlArea;
	}

	public double getMiddlArea(){
		return middlArea;
	}

	public void setWalkTheDogKm(String walkTheDogKm){
		this.walkTheDogKm = walkTheDogKm;
	}

	public String getWalkTheDogKm(){
		return walkTheDogKm;
	}
}