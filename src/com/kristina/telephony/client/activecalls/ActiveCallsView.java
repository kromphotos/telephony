package com.kristina.telephony.client.activecalls;

import java.util.List;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ActiveCallsView extends Composite implements ActiveCallsInterface {
    private static final String EMPTY_STRING = " ";
    private static final String NO_ACTIVE_CALLS_ERROR = "No active calls!!!";
    private static final String END_CALL_BUTTON = "Окончить";
    
    private ActiveCallsViewHandler handler;
    
    private VerticalPanel callsPanel;
    private Label errorLabel;
    private Button endButton;

    public ActiveCallsView() {
        VerticalPanel mainPanel = new VerticalPanel();

        callsPanel = new VerticalPanel();
        ScrollPanel scrollPanel = new ScrollPanel(callsPanel);
        scrollPanel.setHeight("300px");

        errorLabel = new Label();
        errorLabel.getElement().getStyle().setColor("red");

        endButton = new Button(END_CALL_BUTTON);
        endButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (handler != null) {
                    handler.onEndCall();
                }
            }
        });

        mainPanel.add(scrollPanel);
        mainPanel.add(endButton);
        mainPanel.add(errorLabel);

        initWidget(mainPanel);
    }

    @Override
    public void showActiveCalls(List<ActiveCallsViewData> calls, Long selectedExtensionNumber) {
        callsPanel.clear();
        errorLabel.setText(EMPTY_STRING);

        if (calls == null || calls.isEmpty()) {
            callsPanel.add(new Label(NO_ACTIVE_CALLS_ERROR));
            return;
        }

        for (final ActiveCallsViewData data : calls) {
            String text = data.getExtensionNumber() + EMPTY_STRING + data.getOperatorName() + EMPTY_STRING + data.getPhoneNumber();
            final Label callLabel = new Label(text);

            callLabel.getElement().getStyle().setCursor(Cursor.POINTER);
            callLabel.getElement().getStyle().setPadding(4, Unit.PX);
            callLabel.getElement().getStyle().setProperty("borderBottom", "1px solid #eeeeee");

            if (data.getExtensionNumber().equals(selectedExtensionNumber)) {
                callLabel.getElement().getStyle().setBackgroundColor("#b3d4fc");
                callLabel.getElement().getStyle().setFontWeight(FontWeight.BOLD);
            }

            callLabel.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (handler != null) {
                        handler.onActiveCallSelected(data.getExtensionNumber());
                    }
                }
            });
            callsPanel.add(callLabel);
        }
    }

    @Override
    public void showError(String message) {
        errorLabel.setText(message);
    }

    @Override
    public void setHandler(ActiveCallsViewHandler handler) {
        this.handler = handler;
    }
}
