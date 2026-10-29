package com.kristina.telephony.client.queue;

import java.util.List;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class QueueView extends Composite implements QueueInterface {
    private QueueViewHandler handler;

    private VerticalPanel queuePanel;
    private Label errorLabel;

    public QueueView() {
        VerticalPanel mainPanel = new VerticalPanel();

        queuePanel = new VerticalPanel();
        ScrollPanel scrollPanel = new ScrollPanel(queuePanel);
        scrollPanel.setHeight("300px");

        errorLabel = new Label();
        errorLabel.getElement().getStyle().setColor("red");

        mainPanel.add(scrollPanel);
        mainPanel.add(errorLabel);

        initWidget(mainPanel);
    }

    @Override
    public void showQueue(List<QueueViewData> queue, String selectedPhone) {
        queuePanel.clear();
        errorLabel.setText("");

        if (queue == null || queue.isEmpty()) {
            queuePanel.add(new Label("Queue is empty!"));
            return;
        }

        for (final QueueViewData data : queue) {
            final Label phoneLabel = new Label(data.getPhoneNumber());

            phoneLabel.getElement().getStyle().setCursor(Cursor.POINTER);
            phoneLabel.getElement().getStyle().setPadding(4, Unit.PX);
            phoneLabel.getElement().getStyle().setProperty("borderBottom", "1px solid #eeeeee");

            if (data.getPhoneNumber().equals(selectedPhone)) {
                phoneLabel.getElement().getStyle().setBackgroundColor("#b3d4fc");
                phoneLabel.getElement().getStyle().setFontWeight(FontWeight.BOLD);
            }

            phoneLabel.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (handler != null) {
                        handler.onPhoneSelected(data.getPhoneNumber());
                    }
                }
            });
            queuePanel.add(phoneLabel);
        }

    }

    @Override
    public void showError(String message) {
        errorLabel.setText(message);
    }

    @Override
    public void setHandler(QueueViewHandler handler) {
        this.handler = handler;
    }

}
