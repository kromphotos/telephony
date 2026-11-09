package com.kristina.telephony.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface AnswerCallEventHandler extends EventHandler {
    void onCallAnswered(AnswerCallEvent event);
}
