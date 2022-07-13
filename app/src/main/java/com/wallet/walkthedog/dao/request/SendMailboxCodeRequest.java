package com.wallet.walkthedog.dao.request;

public class SendMailboxCodeRequest {
    private String email;

    public SendMailboxCodeRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
