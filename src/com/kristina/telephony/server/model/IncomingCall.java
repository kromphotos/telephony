package com.kristina.telephony.server.model;
import java.util.Date;

public class IncomingCall {
    private String phoneNumber;
    private Date createdTime;
    
    public IncomingCall() {
        
    }

    public IncomingCall(String phoneNumber, Date createdTime) {
        this.phoneNumber = phoneNumber;
        this.createdTime = createdTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "IncomingCall [phoneNumber=" + phoneNumber + "]";
    }
}
