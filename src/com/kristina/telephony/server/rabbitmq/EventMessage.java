package com.kristina.telephony.server.rabbitmq;

public class EventMessage {
    private EventType type;
    private String phoneNumber;
    private Long extensionNumber;
    private String operatorName;
    private String time;
    
    public EventMessage() {
        
    }

    public EventMessage(EventType type, String phoneNumber, Long extensionNumber, String operatorName, String time) {
        super();
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.extensionNumber = extensionNumber;
        this.operatorName = operatorName;
        this.time = time;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getExtensionNumber() {
        return extensionNumber;
    }

    public void setExtensionNumber(Long extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
