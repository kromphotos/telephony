package com.kristina.telephony.client.queue;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.telephony.client.AppGwtService;
import com.kristina.telephony.client.SelectionManager;
import com.kristina.telephony.client.queue.QueueInterface.QueueViewHandler;
import com.kristina.telephony.shared.TelephonyException;

public class QueuePresenter {
    private static final String ERROR_UNKNOWN = "An unknown error occurred";
    private QueueInterface view;
    
    private List<String> currentQueue = new ArrayList<>();
    private String selectedPhone;
    
    public QueuePresenter(QueueInterface view) {
        this.view = view;
        
        view.setHandler(new QueueViewHandler(){
            @Override
            public void onPhoneSelected(String phoneNumber) {
                selectedPhone = phoneNumber;
                SelectionManager.get().setSelectedPhone(phoneNumber);
                convertToData();
            }
        });
        loadQueue();
    }
    
    public void loadQueue() {
        AppGwtService.get().getQueue(new AsyncCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> queue) {
                currentQueue = queue;
                convertToData();
            }
            
            @Override
            public void onFailure(Throwable e) {
                if (e instanceof TelephonyException) {
                    view.showError(e.getMessage());
                } else {
                    Window.alert(ERROR_UNKNOWN);
                }
            }
        });
    }
    
    private void convertToData() {
        List<QueueViewData> viewData = new ArrayList<>();
        for (String phone : currentQueue) {
            viewData.add(new QueueViewData(phone));
        }
        view.showQueue(viewData, selectedPhone);
    }
}
