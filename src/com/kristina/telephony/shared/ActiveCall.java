package com.kristina.telephony.shared;

public class ActiveCall {
    Long extensionNumber;
    String phoneNumber;
    String operatorName;
    
    public ActiveCall() {
        
    }

    public ActiveCall(Long extensionNumber, String phoneNumber, String operatorName) {
        super();
        this.extensionNumber = extensionNumber;
        this.phoneNumber = phoneNumber;
        this.operatorName = operatorName;
    }

    public Long getExtensionNumber() {
        return extensionNumber;
    }

    public void setExtensionNumber(Long extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    @Override
    public String toString() {
        return "ActiveCall [extensionNumber=" + extensionNumber + ", phoneNumber=" + phoneNumber + ", operatorName=" + operatorName + "]";
    }
}
