package com.kristina.telephony.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.kristina.telephony.client.activecalls.ActiveCallsPresenter;
import com.kristina.telephony.client.activecalls.ActiveCallsView;
import com.kristina.telephony.client.events.AppEventBus;
import com.kristina.telephony.client.events.RefreshEvent;
import com.kristina.telephony.client.tree.TreePresenter;
import com.kristina.telephony.client.tree.TreeView;

public class TelephonyEntryPoint implements EntryPoint {
    private static final String ACTIVE_CALLS_HEADER = "Active calls:";
    private static final String CALLS_QUEUE_HEADER = "Queue calls:";
    private static final int REFRESH_INTERVAL_MS = 5000;

    public void onModuleLoad() {
        TreeView treeView = new TreeView();
        TreePresenter treePresenter = new TreePresenter(treeView);

        ActiveCallsView acvtiveCallsView = new ActiveCallsView();
        new ActiveCallsPresenter(acvtiveCallsView);

        FlowPanel mainPanel = new FlowPanel();

        FlowPanel leftColumn = new FlowPanel();
        leftColumn.setWidth("400px");
        leftColumn.add(new Label(CALLS_QUEUE_HEADER));
        treePresenter.go(leftColumn);

        FlowPanel rightColumn = new FlowPanel();
        rightColumn.setWidth("400px");
        rightColumn.add(new Label(ACTIVE_CALLS_HEADER));
        rightColumn.add(acvtiveCallsView);

        mainPanel.add(leftColumn);
        mainPanel.add(rightColumn);

        RootPanel.get().add(mainPanel);

        final Timer refreshTimer = new Timer() {
            @Override
            //что будет выполняться через заданное кол-во секунд???
            public void run() {
                AppEventBus.get().fireEvent(new RefreshEvent());
            }
        };
        refreshTimer.scheduleRepeating(REFRESH_INTERVAL_MS);

        Window.addWindowClosingHandler(new Window.ClosingHandler() {
            @Override
            public void onWindowClosing(ClosingEvent event) {
                refreshTimer.cancel();
            }
        });
    }
}
