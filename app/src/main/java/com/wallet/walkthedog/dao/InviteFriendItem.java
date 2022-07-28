package com.wallet.walkthedog.dao;

public class InviteFriendItem {
	private Object memberNote;
	private String friendEmail;
	private String friendId;
	private String createTime;
	private String friendNote;
	private int id;
	private int type;
	private int memberId;
	private int status;
	private String memberEmail;

	public Object getMemberNote(){
		return memberNote;
	}

	public String getFriendEmail(){
		return friendEmail;
	}

	public String getFriendId(){
		return friendId;
	}

	public String getCreateTime(){
		return createTime;
	}

	public String getFriendNote(){
		return friendNote;
	}

	public int getId(){
		return id;
	}

	public int getType(){
		return type;
	}

	public int getMemberId(){
		return memberId;
	}

	public int getStatus(){
		return status;
	}

	public String getMemberEmail(){
		return memberEmail;
	}
}
