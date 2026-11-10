package com.kristina.telephony.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.kristina.telephony.client.activecalls.ActiveCallsPresenter;
import com.kristina.telephony.client.activecalls.ActiveCallsView;
import com.kristina.telephony.client.tree.TreePresenter;
import com.kristina.telephony.client.tree.TreeView;

public class TelephonyEntryPoint implements EntryPoint {
    private static final String ACTIVE_CALLS_HEADER = "Текущие звонки:";
    private static final String CALLS_QUEUE_HEADER = "Очередь звонков:";

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
    }
}
