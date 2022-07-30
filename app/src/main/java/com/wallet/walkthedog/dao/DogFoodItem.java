package com.wallet.walkthedog.dao;

public class DogFoodItem {
	private int amount;
	private int dogId;
	private int dogCataId;
	private int type;
	private Object addMuscle;
	private String dogName;
	private double addWeight;
	private String numberChain;
	private String createTime;
	private Object price;
	private double foodConsume;
	private int id;
	private Object email;
	private int memberId;
	private int status;

	public int getAmount(){
		return amount;
	}

	public int getDogId(){
		return dogId;
	}

	public int getDogCataId(){
		return dogCataId;
	}

	public int getType(){
		return type;
	}

	public Object getAddMuscle(){
		return addMuscle;
	}

	public String getDogName(){
		return dogName;
	}

	public double getAddWeight(){
		return addWeight;
	}

	public String getNumberChain(){
		return numberChain;
	}

	public String getCreateTime(){
		return createTime;
	}

	public Object getPrice(){
		return price;
	}

	public double getFoodConsume(){
		return foodConsume;
	}

	public int getId(){
		return id;
	}

	public Object getEmail(){
		return email;
	}

	public int getMemberId(){
		return memberId;
	}

	public int getStatus(){
		return status;
	}
}
