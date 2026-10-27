package com.kristina.telephony.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.kristina.telephony.shared.ActiveCall;
import com.kristina.telephony.shared.Apparatus;
import com.kristina.telephony.shared.Room;
import com.kristina.telephony.shared.TelephonyException;

@RemoteServiceRelativePath("telephony")
public interface GwtService extends RemoteService {
    List<String> getQueue() throws TelephonyException;

    void answerCall(Long extensionNumber, String phoneNumber) throws TelephonyException;

    void endCall(Long extensionNumber) throws TelephonyException;

    List<ActiveCall> getActiveCalls() throws TelephonyException;

    List<Room> getRooms() throws TelephonyException;

    List<Apparatus> getApparatusInRoom(Long roomId) throws TelephonyException;
}
