package com.kristina.telephony.client.activecalls;

import java.util.List;

public interface ActiveCallsInterface {
    void showActiveCalls(List<ActiveCallsViewData> calls, Long selectedExtensionNumber);

    void showError(String message);
    
    void setHandler(ActiveCallsViewHandler handler);
    
    interface ActiveCallsViewHandler {
        void onActiveCallSelected(Long extensionNumber);
        void onEndCall();
    }
}
