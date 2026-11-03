package com.kristina.telephony.client.events;

import com.google.gwt.event.shared.SimpleEventBus;

public class AppEventBus {
   private static final SimpleEventBus INSTANCE = new SimpleEventBus();
   public static SimpleEventBus get() {
       return INSTANCE;
   }
   
   private AppEventBus() {
       
   }
}
