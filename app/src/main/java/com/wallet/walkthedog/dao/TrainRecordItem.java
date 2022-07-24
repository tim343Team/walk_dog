package com.wallet.walkthedog.dao;

import com.google.gson.annotations.SerializedName;

public class TrainRecordItem {

	@SerializedName("dogId")
	private int dogId;

	@SerializedName("trainingId")
	private int trainingId;

	@SerializedName("createTime")
	private String createTime;

	@SerializedName("trainingName")
	private String trainingName;

	@SerializedName("count")
	private int count;

	@SerializedName("updateTime")
	private String updateTime;

	@SerializedName("id")
	private int id;

	@SerializedName("addMuscle")
	private int addMuscle;

	@SerializedName("dogNumberChain")
	private String dogNumberChain;

	@SerializedName("dogTypeName")
	private String dogTypeName;

	@SerializedName("memberId")
	private int memberId;

	@SerializedName("status")
	private int status;

	public void setDogId(int dogId){
		this.dogId = dogId;
	}

	public int getDogId(){
		return dogId;
	}

	public void setTrainingId(int trainingId){
		this.trainingId = trainingId;
	}

	public int getTrainingId(){
		return trainingId;
	}

	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}

	public String getCreateTime(){
		return createTime;
	}

	public void setTrainingName(String trainingName){
		this.trainingName = trainingName;
	}

	public String getTrainingName(){
		return trainingName;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}

	public String getUpdateTime(){
		return updateTime;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setAddMuscle(int addMuscle){
		this.addMuscle = addMuscle;
	}

	public int getAddMuscle(){
		return addMuscle;
	}

	public void setDogNumberChain(String dogNumberChain){
		this.dogNumberChain = dogNumberChain;
	}

	public String getDogNumberChain(){
		return dogNumberChain;
	}

	public void setDogTypeName(String dogTypeName){
		this.dogTypeName = dogTypeName;
	}

	public String getDogTypeName(){
		return dogTypeName;
	}

	public void setMemberId(int memberId){
		this.memberId = memberId;
	}

	public int getMemberId(){
		return memberId;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}