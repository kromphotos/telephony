package com.kristina.telephony.client.queue;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.telephony.client.AppGwtService;
import com.kristina.telephony.client.events.AnswerCallEvent;
import com.kristina.telephony.client.events.AnswerCallEventHandler;
import com.kristina.telephony.client.events.AppEventBus;
import com.kristina.telephony.client.events.CallEndedEvent;
import com.kristina.telephony.client.events.CallEndedEventHandler;
import com.kristina.telephony.client.queue.QueueInterface.QueueViewHandler;
import com.kristina.telephony.shared.TelephonyException;

public class QueuePresenter implements CallEndedEventHandler, AnswerCallEventHandler {
    private static final String ERROR_UNKNOWN = "An unknown error occurred";

    private QueueInterface view;
    private PhoneSelectionHandler selectionHandler;

    private List<String> currentQueue = new ArrayList<>();
    private String selectedPhone;
    
    public interface PhoneSelectionHandler {
        void onPhoneSelected(String phoneNumber);
    }

    public QueuePresenter(final QueueInterface view, PhoneSelectionHandler selectionHandler) {
        this.view = view;
        this.selectionHandler = selectionHandler;

        view.setHandler(new QueueViewHandler() {
            @Override
            public void onPhoneSelected(String phoneNumber) {
                selectedPhone = phoneNumber;
                convertToData();

                if (QueuePresenter.this.selectionHandler != null) {
                    QueuePresenter.this.selectionHandler.onPhoneSelected(phoneNumber);
                }
            }
        });

        AppEventBus.get().addHandler(CallEndedEvent.TYPE, this);//подписка на событие 
        AppEventBus.get().addHandler(AnswerCallEvent.TYPE, this);

        loadQueue();
    }

    public void loadQueue() {
        AppGwtService.get().getQueue(new AsyncCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> queue) {
                currentQueue = queue;
                //если выбранного звонка больше нет в очереди то сбрасываем выбор
                if (selectedPhone != null && !queue.contains(selectedPhone)) {
                    selectedPhone = null;
                }
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
    
    @Override
    public void onCallEnded(CallEndedEvent event) {
        loadQueue();
    }
    
    @Override
    public void onCallAnswered(AnswerCallEvent event) {
        loadQueue();
    }
}
