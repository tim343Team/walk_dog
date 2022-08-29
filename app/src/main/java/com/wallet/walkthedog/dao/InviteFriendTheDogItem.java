package com.wallet.walkthedog.dao;

public class InviteFriendTheDogItem {
	private String friendName;
	private String friendNickName;
	private int isDelete;
	private int isRead;
	private String memberName;
	private int updateTime;
	private int type;
	private int friendId;
	private String createTime;
	private String startTime;
	private int id;
	private String endTime;
	private int memberId;
	private int status;

	public String getFriendName(){
		return friendName;
	}

	public String getFriendNickName() {
		return friendNickName;
	}

	public int getIsDelete(){
		return isDelete;
	}

	public int getIsRead(){
		return isRead;
	}

	public String getMemberName(){
		return memberName;
	}

	public int getUpdateTime(){
		return updateTime;
	}

	public int getType(){
		return type;
	}

	public int getFriendId(){
		return friendId;
	}

	public String getCreateTime(){
		return createTime;
	}

	public String getStartTime(){
		return startTime;
	}

	public int getId(){
		return id;
	}

	public String getEndTime(){
		return endTime;
	}

	public int getMemberId(){
		return memberId;
	}

	public int getStatus(){
		return status;
	}
}
