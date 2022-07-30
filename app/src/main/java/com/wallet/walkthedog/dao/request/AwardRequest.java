package com.wallet.walkthedog.dao.request;

public class AwardRequest {
    private int logId;
    private int awardId;

    public AwardRequest(int logId, int awardId) {
        this.logId = logId;
        this.awardId = awardId;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getAwardId() {
        return awardId;
    }

    public void setAwardId(int awardId) {
        this.awardId = awardId;
    }
}
