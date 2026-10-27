package com.kristina.telephony.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.telephony.shared.ActiveCall;
import com.kristina.telephony.shared.Apparatus;
import com.kristina.telephony.shared.Room;

public interface GwtServiceAsync {
    void getQueue(AsyncCallback<List<String>> callback);

    void answerCall(Long extensionNumber, String phoneNumber, AsyncCallback<Void> callback);

    void endCall(Long extensionNumber, AsyncCallback<Void> callback);

    void getActiveCalls(AsyncCallback<List<ActiveCall>> callback);

    void getRooms(AsyncCallback<List<Room>> callback);

    void getApparatusInRoom(Long roomId, AsyncCallback<List<Apparatus>> callback);
}
