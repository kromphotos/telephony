package com.kristina.telephony.server;

import java.util.List;

import com.kristina.telephony.shared.ActiveCall;
import com.kristina.telephony.shared.Apparatus;
import com.kristina.telephony.shared.Room;
import com.kristina.telephony.shared.TelephonyException;

public interface TelephonyService {
    //операция 3 - получение очереди
    List<String> getQueue() throws TelephonyException;
    //операция 4 - ответить на звонок
    void answerCall(Long extensionNumber, String phoneNumber) throws TelephonyException;
    //операция 5 - завершить звонок
    void endCall(Long extensionNumber) throws TelephonyException;
    //операция 6 - посмотреть активные звонки
    List<ActiveCall> getActiveCalls() throws  TelephonyException;
    //операция 7 - посмотреть комнаты
    List<Room> getRooms() throws TelephonyException;
    //операция 8 - статут аппаратов в комнате
    List<Apparatus> getApparatusInRoom(Long roomId) throws TelephonyException;
}
