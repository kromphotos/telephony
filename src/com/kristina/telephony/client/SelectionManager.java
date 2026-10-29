package com.kristina.telephony.client;

public class SelectionManager {
    private static final SelectionManager INSTANCE = new SelectionManager();
    
    public static SelectionManager get() {
        return INSTANCE;
    }
    
    private SelectionManager() {
        
    }
    
    private String selectedPhone;
    private Long selectedExtensionNumber;

    public String getSelectedPhone() {
        return selectedPhone;
    }

    public void setSelectedPhone(String selectedPhone) {
        this.selectedPhone = selectedPhone;
    }

    public Long getSelectedExtensionNumber() {
        return selectedExtensionNumber;
    }

    public void setSelectedExtensionNumber(Long selectedExtensionNumber) {
        this.selectedExtensionNumber = selectedExtensionNumber;
    }
    
    public boolean isReadyToAnswer() {
        return selectedPhone != null && selectedExtensionNumber != null;
    }
    
    public void clear() {
        selectedPhone = null;
        selectedExtensionNumber = null;
    }
    
}
