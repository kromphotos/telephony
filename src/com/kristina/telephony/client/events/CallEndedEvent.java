package com.kristina.telephony.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class CallEndedEvent extends GwtEvent<CallEndedEventHandler> {
    public static final Type<CallEndedEventHandler> TYPE = new Type<CallEndedEventHandler>();
    
    @Override
    public Type<CallEndedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CallEndedEventHandler handler) {
        handler.onCallEnded(this);
    }
}
