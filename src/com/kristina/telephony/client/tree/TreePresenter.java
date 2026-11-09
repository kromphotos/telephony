package com.kristina.telephony.client.tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.kristina.telephony.client.AppGwtService;
import com.kristina.telephony.client.events.AnswerCallEvent;
import com.kristina.telephony.client.events.AppEventBus;
import com.kristina.telephony.client.events.CallEndedEvent;
import com.kristina.telephony.client.events.CallEndedEventHandler;
import com.kristina.telephony.client.queue.QueuePresenter;
import com.kristina.telephony.client.queue.QueueView;
import com.kristina.telephony.shared.Apparatus;
import com.kristina.telephony.shared.Room;
import com.kristina.telephony.shared.TelephonyException;

public class TreePresenter implements CallEndedEventHandler, QueuePresenter.PhoneSelectionHandler {
    private static final String ERROR_UNKNOWN = "An unknown error occurred";
    private static final String ERROR_NO_SELECTION = "Please select apparatus and phone first";

    private TreeInterface view;
    private QueuePresenter queuePresenter;
    private QueueView queueView;

    private List<Room> currentRooms = new ArrayList<>();
    private Map<String, List<TreeViewData>> roomsMap = new LinkedHashMap();

    //то что нажато и то что мы будем проверять при нажатии на кнопку позвонить
    private Long selectedExtensionNumber;
    private String selectedPhone;//с очереди приходит через хэндлер

    public TreePresenter(final TreeInterface view) {
        this.view = view;

        queueView = new QueueView();
        queuePresenter = new QueuePresenter(queueView, this);

        AppEventBus.get().addHandler(CallEndedEvent.TYPE, this);//подписка на событие завершения звонка

        view.setHandler(new TreeInterface.TreeViewHandler() {
            @Override
            public void onApparatusSelected(Long extensionNumber) {
                selectedExtensionNumber = extensionNumber;
                renderTree();
                updateAnswerButtonState();
            }

            @Override
            public void onAnswerClicked() {
                answerCall();
            }
        });

        loadRooms();//загрузка комнат при старте
    }

    public void go(FlowPanel container) {
        container.add((com.google.gwt.user.client.ui.Widget) view); 
        container.add(queueView);
    }

    private void loadRooms() {
        AppGwtService.get().getRooms(new AsyncCallback<List<Room>>() {
            @Override
            public void onSuccess(List<Room> rooms) {
                currentRooms = rooms;
                loadApparatuses();//после загрузки комнаты грузим аппараты для каждой
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

    private void loadApparatuses() {
        roomsMap.clear();//очистка всех пар

        if (currentRooms == null || currentRooms.isEmpty()) {
            renderTree();
            return;
        }

        final int totalRooms = currentRooms.size();
        final int[] loadedCount = { 0 };//массив из 1 элемента чтобы работать с анонимными классами и иметь возможность менять переменную

        for (final Room room : currentRooms) {
            AppGwtService.get().getApparatusInRoom(room.getId(), new AsyncCallback<List<Apparatus>>() {
                @Override
                public void onSuccess(List<Apparatus> apparatuses) {
                    List<TreeViewData> treeDataList = new ArrayList<>();
                    for (Apparatus app : apparatuses) {
                        treeDataList.add(new TreeViewData(
                                app.getExtensionNumber(),
                                app.getOperatorName(),
                                app.isBusy(),
                                app.getPhoneNumber()));
                    }
                    roomsMap.put(room.getName(), treeDataList);

                    loadedCount[0]++;
                    if (loadedCount[0] == totalRooms) {
                        renderTree();
                        updateAnswerButtonState();
                    }
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
    }

    private void renderTree() {
        view.showTree(roomsMap, selectedExtensionNumber);
    }

    private void updateAnswerButtonState() {
        view.setAnswerButtonEnabled((selectedExtensionNumber != null && selectedPhone != null));
    }

    //ОБРАБОТКА НАЖАТИЯ НА КНОПКУ ОТВЕТИТЬ
    private void answerCall() {
        if (selectedExtensionNumber == null || selectedPhone == null) {
            view.showError(ERROR_NO_SELECTION);
            return;
        }

        AppGwtService.get().answerCall(selectedExtensionNumber, selectedPhone, new AsyncCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                //сброс выбора
                selectedExtensionNumber = null;
                selectedPhone = null;

                //перезагружаем очередь и дерево и активные звонки
                loadRooms();

                //уточнить как нам обновлять другие компоненты при ответе на звонок????
                AppEventBus.get().fireEvent(new AnswerCallEvent());
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
    
    @Override
    public void onPhoneSelected(String phoneNumber) {
        selectedPhone = phoneNumber;
        updateAnswerButtonState();
    }
    
    @Override
    public void onCallEnded(CallEndedEvent event) {
        loadRooms();
    }
}
