package com.wallet.walkthedog.dao.request;

public class InviteRequest {
    private String friendId;
    private String startTime;
    private String endTime;

    public InviteRequest(String friendId, String startTime, String endTime) {
        this.friendId = friendId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
