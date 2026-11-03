package com.kristina.telephony.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface CallEndedEventHandler extends EventHandler{
    void onCallEnded(CallEndedEvent event);
}
