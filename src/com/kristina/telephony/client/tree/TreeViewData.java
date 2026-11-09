package com.kristina.telephony.client.tree;

public class TreeViewData {
    private String phoneNumber;
    private Long extensionNumber;
    private String operatorName;
    private boolean busy;
    
    public TreeViewData(Long extensionNumber, String operatorName, boolean busy, String phoneNumber) {
        this.extensionNumber = extensionNumber;
        this.operatorName = operatorName;
        this.busy = busy;
        this.phoneNumber = phoneNumber;
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
    
    public boolean isBusy() {
        return busy;
    }
    
    @Override
    public String toString() {
        return "TreeViewData [extensionNumber=" + extensionNumber + ", operatorName=" + operatorName + "]";
    }
}
