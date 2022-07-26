package com.wallet.walkthedog.dao.request;

public class FriendRequest {
    private String id;
    private String name;
    private String friendMemberId;

    public FriendRequest(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public FriendRequest(String friendMemberId) {
        this.friendMemberId = friendMemberId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFriendMemberId() {
        return friendMemberId;
    }

    public void setFriendMemberId(String friendMemberId) {
        this.friendMemberId = friendMemberId;
    }
}
