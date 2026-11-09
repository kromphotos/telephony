package com.kristina.telephony.client.activecalls;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.telephony.client.AppGwtService;
import com.kristina.telephony.client.activecalls.ActiveCallsInterface.ActiveCallsViewHandler;
import com.kristina.telephony.client.events.AnswerCallEvent;
import com.kristina.telephony.client.events.AnswerCallEventHandler;
import com.kristina.telephony.client.events.AppEventBus;
import com.kristina.telephony.client.events.CallEndedEvent;
import com.kristina.telephony.shared.ActiveCall;
import com.kristina.telephony.shared.TelephonyException;

public class ActiveCallsPresenter implements AnswerCallEventHandler {
    private static final String ERROR_UNKNOWN = "An unknown error occurred";
    private static final String ERROR_NO_SELECTION = "Please select a call first";
    private ActiveCallsInterface view;

    private Long selectedExtension;
    private List<ActiveCall> currentCalls = new ArrayList<>();

    public ActiveCallsPresenter(final ActiveCallsInterface view) {
        this.view = view;

        view.setHandler(new ActiveCallsViewHandler() {
            @Override
            public void onActiveCallSelected(Long extensionNumber) {
                selectedExtension = extensionNumber;
                convertToData();
            }

            @Override
            public void onEndCall() {
                if (selectedExtension == null) {
                    view.showError(ERROR_NO_SELECTION);
                    return;
                }

                AppGwtService.get().endCall(selectedExtension, new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        selectedExtension = null;
                        loadActiveCalls();
                        AppEventBus.get().fireEvent(new CallEndedEvent());
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
        });
        
        AppEventBus.get().addHandler(AnswerCallEvent.TYPE, this);
        loadActiveCalls();
    }

    public void loadActiveCalls() {
        AppGwtService.get().getActiveCalls(new AsyncCallback<List<ActiveCall>>() {
            @Override
            public void onSuccess(List<ActiveCall> calls) {
                currentCalls = calls;

                if (selectedExtension != null) {
                    boolean stillExists = false;
                    for (ActiveCall call : calls) {
                        if (call.getExtensionNumber().equals(selectedExtension)) {
                            stillExists = true;
                            break;
                        }
                    }
                    if (!stillExists) {
                        selectedExtension = null;
                    }
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
        List<ActiveCallsViewData> viewData = new ArrayList<>();
        for (ActiveCall call : currentCalls) {
            viewData.add(new ActiveCallsViewData(
                    call.getExtensionNumber(),
                    call.getOperatorName(),
                    call.getPhoneNumber()));
        }
        view.showActiveCalls(viewData, selectedExtension);
    }
    
    @Override
    public void onCallAnswered(AnswerCallEvent event) {
        loadActiveCalls();//обновление очереди при завершении звонка
    }
}
