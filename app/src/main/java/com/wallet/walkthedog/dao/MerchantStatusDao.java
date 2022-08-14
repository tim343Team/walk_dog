package com.wallet.walkthedog.dao;

public class MerchantStatusDao {
    private int memberLevel;
    private int certifiedBusinessStatus;//0未认证   认证1等待审核  2认证审核成功   3认证审核失败  4保证金不足  5退保待审核 6退保审核失败 7退保审核成功
    private String email;
    private String detail;
    private String reason;

    public int getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(int memberLevel) {
        this.memberLevel = memberLevel;
    }

    public int getCertifiedBusinessStatus() {
        return certifiedBusinessStatus;
    }

    public void setCertifiedBusinessStatus(int certifiedBusinessStatus) {
        this.certifiedBusinessStatus = certifiedBusinessStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
