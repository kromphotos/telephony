package com.kristina.telephony.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Apparatus implements IsSerializable {
    private Long id;
    private Long extensionNumber;
    private String operatorName;
    private boolean busy;
    private String phoneNumber;//входящий номер??? может не надо
    
    public Apparatus() {
        
    }
    
    public Apparatus(Long id, Long extensionNumber, String operatorName, boolean busy, String phoneNumber) {
        this.id = id;
        this.extensionNumber = extensionNumber;
        this.operatorName = operatorName;
        this.busy = busy;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Apparatus [id=" + id + ", extensionNumber=" + extensionNumber + ", operatorName=" + operatorName + ", busy=" + busy + ", phoneNumber="
                + phoneNumber + "]";
    }
    
    
}
