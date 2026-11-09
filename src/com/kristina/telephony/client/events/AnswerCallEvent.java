package com.kristina.telephony.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class AnswerCallEvent extends GwtEvent<AnswerCallEventHandler> {
    public static final Type<AnswerCallEventHandler> TYPE = new Type<AnswerCallEventHandler>();

    @Override
    public Type<AnswerCallEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AnswerCallEventHandler handler) {
        handler.onCallAnswered(this);
    }
}
