package com.kristina.telephony.client.queue;

import java.util.List;

public interface QueueInterface {
    void showQueue(List<QueueViewData> queue, String selectedPhone);

    void showError(String message);
    
    void setHandler(QueueViewHandler handler);
    
    interface QueueViewHandler {
        void onPhoneSelected(String phoneNumber);
    }
}
