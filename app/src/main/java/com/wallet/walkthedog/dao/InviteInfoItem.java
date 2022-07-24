package com.wallet.walkthedog.dao;

public class InviteInfoItem {
	private String friendName;
	private int friendId;
	private String createTime;
	private int isDelete;
	private int isRead;
	private String memberName;
	private String startTime;
	private String endTime;
	private int id;
	private int memberId;
	private int status;

	public String getFriendName(){
		return friendName;
	}

	public int getFriendId(){
		return friendId;
	}

	public String getCreateTime(){
		return createTime;
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

	public String getStartTime(){
		return startTime;
	}

	public String getEndTime(){
		return endTime;
	}

	public int getId(){
		return id;
	}

	public int getMemberId(){
		return memberId;
	}

	public int getStatus(){
		return status;
	}
}
