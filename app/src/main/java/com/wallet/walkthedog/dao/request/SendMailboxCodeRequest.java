package com.wallet.walkthedog.dao.request;

public class SendMailboxCodeRequest {
    private String email;

    private String checkCode;

    public SendMailboxCodeRequest(String email) {
        this.email = email;
    }

    public SendMailboxCodeRequest(String email, String checkCode) {
        this.email = email;
        this.checkCode = checkCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
}
