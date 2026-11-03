package com.kristina.telephony.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kristina.telephony.client.activecalls.ActiveCallsPresenter;
import com.kristina.telephony.client.activecalls.ActiveCallsView;
import com.kristina.telephony.client.queue.QueuePresenter;
import com.kristina.telephony.client.queue.QueueView;

public class TelephonyEntryPoint implements EntryPoint {
    public void onModuleLoad() {
        //-----Active calls-----
        ActiveCallsView acvtiveCallsView = new ActiveCallsView();
        new ActiveCallsPresenter(acvtiveCallsView);
        
        //-----Queue-----
        QueueView queueView = new QueueView();
        new QueuePresenter(queueView);
        
        //-----Главный контейнер-----
        FlowPanel mainPanel = new FlowPanel();
        
        VerticalPanel leftColumn = new VerticalPanel();
        leftColumn.setWidth("300px");
        leftColumn.add(new Label("Очередь звонков:"));
        leftColumn.add(queueView);
        
        VerticalPanel rightColumn = new VerticalPanel();
        rightColumn.setWidth("400px");
        rightColumn.add(new Label("Текущие звонки:"));
        rightColumn.add(acvtiveCallsView);
        
        mainPanel.add(leftColumn);
        mainPanel.add(rightColumn);
        
        RootPanel.get("main-container").add(mainPanel);
    }
}
