package com.kristina.telephony.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kristina.telephony.client.GwtService;
import com.kristina.telephony.shared.ActiveCall;
import com.kristina.telephony.shared.Apparatus;
import com.kristina.telephony.shared.Room;
import com.kristina.telephony.shared.TelephonyException;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class TelephonyServiceImpl extends RemoteServiceServlet implements GwtService {
    private final TelephonyService service = new TelephonyServiceImp();

    @Override
    public List<String> getQueue() throws TelephonyException {
        return service.getQueue();
    }

    @Override
    public void answerCall(Long extensionNumber, String phoneNumber) throws TelephonyException {
        service.answerCall(extensionNumber, phoneNumber);
    }

    @Override
    public void endCall(Long extensionNumber) throws TelephonyException {
        service.endCall(extensionNumber);
    }

    @Override
    public List<ActiveCall> getActiveCalls() throws TelephonyException {
        return service.getActiveCalls();
    }

    @Override
    public List<Room> getRooms() throws TelephonyException {
        return service.getRooms();
    }

    @Override
    public List<Apparatus> getApparatusInRoom(Long roomId) throws TelephonyException {
        return service.getApparatusInRoom(roomId);
    }
}
