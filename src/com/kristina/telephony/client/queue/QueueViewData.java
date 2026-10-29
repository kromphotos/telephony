package com.kristina.telephony.client.queue;

public class QueueViewData {
    private String phoneNumber;
    
    public QueueViewData(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
