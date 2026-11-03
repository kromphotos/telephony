package com.kristina.telephony.client.activecalls;

public class ActiveCallsViewData {
    private Long extensionNumber;
    private String operatorName;
    private String phoneNumber;
    
    public ActiveCallsViewData(Long extensionNumber, String operatorName, String phoneNumber) {
        this.extensionNumber = extensionNumber;
        this.operatorName = operatorName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "ActiveCallsViewData [extensionNumber=" + extensionNumber + ", operatorName=" + operatorName + ", phoneNumber=" + phoneNumber + "]";
    }
}
