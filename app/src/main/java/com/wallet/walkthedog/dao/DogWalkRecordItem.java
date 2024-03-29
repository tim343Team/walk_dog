package com.wallet.walkthedog.dao;

import com.google.gson.annotations.SerializedName;

public class DogWalkRecordItem {

	@SerializedName("friendTime")
	private int friendTime;

	@SerializedName("walkTheDogCount")
	private int walkTheDogCount;

	@SerializedName("dogId")
	private int dogId;

	@SerializedName("dogLevel")
	private double dogLevel;

	@SerializedName("friendBox")
	private int friendBox;

	@SerializedName("friendDogNumberChain")
	private String friendDogNumberChain;

	@SerializedName("overTime")
	private String overTime;

	@SerializedName("box")
	private int box;

	@SerializedName("popularityArea")
	private double popularityArea;

	@SerializedName("friendKm")
	private double friendKm;

	@SerializedName("prop")
	private int prop;

	@SerializedName("id")
	private int id;

	@SerializedName("rubbish")
	private int rubbish;

	@SerializedName("dogFood")
	private int dogFood;

	@SerializedName("dogNumberChain")
	private String dogNumberChain;

	@SerializedName("middlArea")
	private double middlArea;

	@SerializedName("memberId")
	private int memberId;

	@SerializedName("count")
	private int count;

	@SerializedName("friendProp")
	private int friendProp;

	@SerializedName("updateTime")
	private String updateTime;

	@SerializedName("maxSpeed")
	private double maxSpeed;

	@SerializedName("friendDogFood")
	private int friendDogFood;

	@SerializedName("friendRubbish")
	private int friendRubbish;

	@SerializedName("friendId")
	private int friendId;

	@SerializedName("createTime")
	private String createTime;

	@SerializedName("unknownArea")
	private double unknownArea;

	@SerializedName("friendDogTypeName")
	private String friendDogTypeName;

	@SerializedName("friendDogId")
	private String friendDogId;

	@SerializedName("friendDogLevel")
	private int friendDogLevel;

	@SerializedName("walkTheDogKm")
	private double walkTheDogKm;

	@SerializedName("dogTypeName")
	private String dogTypeName;

	@SerializedName("time")
	private long time;

	public void setFriendTime(int friendTime){
		this.friendTime = friendTime;
	}

	public int getFriendTime(){
		return friendTime;
	}

	public void setWalkTheDogCount(int walkTheDogCount){
		this.walkTheDogCount = walkTheDogCount;
	}

	public int getWalkTheDogCount(){
		return walkTheDogCount;
	}

	public void setDogId(int dogId){
		this.dogId = dogId;
	}

	public int getDogId(){
		return dogId;
	}

	public double getDogLevel() {
		return dogLevel;
	}

	public void setDogLevel(double dogLevel) {
		this.dogLevel = dogLevel;
	}

	public void setFriendBox(int friendBox){
		this.friendBox = friendBox;
	}

	public int getFriendBox(){
		return friendBox;
	}

	public void setFriendDogNumberChain(String friendDogNumberChain){
		this.friendDogNumberChain = friendDogNumberChain;
	}

	public String getFriendDogNumberChain(){
		return friendDogNumberChain;
	}

	public void setOverTime(String overTime){
		this.overTime = overTime;
	}

	public String getOverTime(){
		return overTime;
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

	public void setProp(int prop){
		this.prop = prop;
	}

	public int getProp(){
		return prop;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
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

	public void setDogNumberChain(String dogNumberChain){
		this.dogNumberChain = dogNumberChain;
	}

	public String getDogNumberChain(){
		return dogNumberChain;
	}

	public void setMiddlArea(double middlArea){
		this.middlArea = middlArea;
	}

	public double getMiddlArea(){
		return middlArea;
	}

	public void setMemberId(int memberId){
		this.memberId = memberId;
	}

	public int getMemberId(){
		return memberId;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setFriendProp(int friendProp){
		this.friendProp = friendProp;
	}

	public int getFriendProp(){
		return friendProp;
	}

	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}

	public String getUpdateTime(){
		return updateTime;
	}

	public void setMaxSpeed(double maxSpeed){
		this.maxSpeed = maxSpeed;
	}

	public double getMaxSpeed(){
		return maxSpeed;
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

	public void setFriendId(int friendId){
		this.friendId = friendId;
	}

	public int getFriendId(){
		return friendId;
	}

	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}

	public String getCreateTime(){
		return createTime;
	}

	public void setUnknownArea(double unknownArea){
		this.unknownArea = unknownArea;
	}

	public double getUnknownArea(){
		return unknownArea;
	}

	public void setFriendDogTypeName(String friendDogTypeName){
		this.friendDogTypeName = friendDogTypeName;
	}

	public String getFriendDogTypeName(){
		return friendDogTypeName;
	}

	public void setFriendDogId(String friendDogId){
		this.friendDogId = friendDogId;
	}

	public String getFriendDogId(){
		return friendDogId;
	}

	public void setFriendDogLevel(int friendDogLevel){
		this.friendDogLevel = friendDogLevel;
	}

	public int getFriendDogLevel(){
		return friendDogLevel;
	}

	public void setWalkTheDogKm(double walkTheDogKm){
		this.walkTheDogKm = walkTheDogKm;
	}

	public double getWalkTheDogKm(){
		return walkTheDogKm;
	}

	public void setDogTypeName(String dogTypeName){
		this.dogTypeName = dogTypeName;
	}

	public String getDogTypeName(){
		return dogTypeName;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getTime() {
		return time;
	}
}