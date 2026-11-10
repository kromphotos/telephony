package com.kristina.telephony.client.tree;

import java.util.List;
import java.util.Map;

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

public class TreeView extends Composite implements TreeInterface {
    private static final String EMPTY_STRING = " ";
    private static final String ISBUSY_TEXT = "(занят: ";
    private static final String ROOM_IS_EMPTY_ERROR = "Room is empty!";
    private static final String ANSWER_BUTTON = "Ответить";

    private TreeViewHandler handler;

    private VerticalPanel treePanel;
    private Button answerButton;
    private Label errorLabel;

    public TreeView() {
        VerticalPanel mainPanel = new VerticalPanel();

        answerButton = new Button(ANSWER_BUTTON);
        answerButton.setEnabled(false);
        answerButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (handler != null) {
                    handler.onAnswerClicked();
                }
            }
        });

        treePanel = new VerticalPanel();
        ScrollPanel scrollPanel = new ScrollPanel(treePanel);
        scrollPanel.setHeight("400px");

        errorLabel = new Label();
        errorLabel.getElement().getStyle().setColor("red");

        mainPanel.add(answerButton);
        mainPanel.add(scrollPanel);
        mainPanel.add(errorLabel);

        initWidget(mainPanel);
    }

    @Override
    public void showTree(Map<String, List<TreeViewData>> rooms, Long selectedExtensionNumber) {
        treePanel.clear();
        errorLabel.setText(EMPTY_STRING);

        if (rooms == null || rooms.isEmpty()) {
            treePanel.add(new Label(ROOM_IS_EMPTY_ERROR));
            return;
        }

        for (Map.Entry<String, List<TreeViewData>> entry : rooms.entrySet()) {
            String roomName = entry.getKey();
            List<TreeViewData> apparatuses = entry.getValue();

            Label roomLabel = new Label(roomName + ":");
            roomLabel.getElement().getStyle().setFontWeight(FontWeight.BOLD);
            roomLabel.getElement().getStyle().setPaddingTop(8, Unit.PX);
            roomLabel.getElement().getStyle().setPaddingBottom(4, Unit.PX);
            treePanel.add(roomLabel);

            //список аппаратов у комнаты
            if (apparatuses != null && !apparatuses.isEmpty()) {
                for (final TreeViewData app : apparatuses) {
                    String text = app.getExtensionNumber() + EMPTY_STRING + app.getOperatorName();
                    
                    if (app.isBusy() && app.getPhoneNumber() != null) {
                        text += ISBUSY_TEXT + app.getPhoneNumber() + ")";
                    }
                    
                    final Label appLabel = new Label(text);
                    
                    appLabel.getElement().getStyle().setPaddingLeft(15, Unit.PX);
                    appLabel.getElement().getStyle().setPadding(4, Unit.PX);
                    appLabel.getElement().getStyle().setCursor(Cursor.POINTER);
                    appLabel.getElement().getStyle().setProperty("borderBottom", "1px solid #eeeeee");
                    
                    if (app.getExtensionNumber().equals(selectedExtensionNumber)) {
                        appLabel.getElement().getStyle().setBackgroundColor("#b3d4fc");
                        appLabel.getElement().getStyle().setFontWeight(FontWeight.BOLD);
                    }
                    
                    appLabel.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            if (handler != null) {
                                handler.onApparatusSelected(app.getExtensionNumber());
                            }
                        }
                    });
                    
                    treePanel.add(appLabel);
                }
            }
        }

    }

    @Override
    public void setAnswerButtonEnabled(boolean enabled) {
        answerButton.setEnabled(enabled);
    }

    @Override
    public void showError(String message) {
        errorLabel.setText(message);
    }

    @Override
    public void setHandler(TreeViewHandler handler) {
        this.handler = handler;
    }
}
