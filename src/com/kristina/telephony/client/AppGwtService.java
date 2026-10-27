package com.kristina.telephony.client;

import com.google.gwt.core.client.GWT;

public class AppGwtService {
    private static final GwtServiceAsync INSTANCE = GWT.create(GwtService.class);
    
    public static GwtServiceAsync get() {
        return INSTANCE;
    }
    
    private AppGwtService() {
        
    }
}
