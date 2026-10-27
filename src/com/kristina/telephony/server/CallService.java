package com.kristina.telephony.server;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.kristina.telephony.server.model.IncomingCall;
import com.kristina.telephony.shared.ActiveCall;
import com.kristina.telephony.shared.Apparatus;
import com.kristina.telephony.shared.TelephonyException;

public class CallService {
    //входящие звонки(ключом является номер телефона)
    private final Map<String, IncomingCall> incomingCalls = new ConcurrentHashMap();
    //активные разговоры(ключом явл добавочный номер оператора)
    private final Map<Long, ActiveCall> activeCalls = new ConcurrentHashMap();
    //занятые аппараты(ключом явл добавочный номер оператора. значение - номер телефона)
    private final Map<Long, String> busyApparatus = new ConcurrentHashMap();

    private static final CallService INSTANCE = new CallService();

    public static CallService getInstance() {
        return INSTANCE;
    }

    private CallService() {
    }

    //1 запрос от АТС - добавление входящего звонка
    public void addIncomingCall(String phoneNumber) throws TelephonyException {
        if (incomingCalls.containsKey(phoneNumber)) {
            throw new TelephonyException("Call is already in queue!");
        }
        IncomingCall call = new IncomingCall(phoneNumber, new Date());
        incomingCalls.put(phoneNumber, call);
    }

    //2 запрос от АТС - удалить звонок из очереди
    //когда пользователь передумал звонить и сбросил(оператор еще не ответил)
    public void cancelIncomingCall(String phoneNumber) throws TelephonyException {
        IncomingCall removedCall = incomingCalls.remove(phoneNumber);

        if (removedCall == null) {
            throw new TelephonyException("Call not in queue!");
        }
    }
    
    //3 запрос - посмотреть очередь(список номеров + отсортировано по времени)
    public List<String> getQueue() {
        return incomingCalls.values().stream()
                .sorted(Comparator.comparing(IncomingCall::getCreatedTime))
                .map(IncomingCall::getPhoneNumber)
                .collect(Collectors.toList());
    }
    
    //4 запрос - ответить на звонок 
    //параметры (внутренний номер + входящий номер)
    public void answerCall(Long extensionNumber, String phoneNumber, Apparatus apparatus) throws TelephonyException {
        if (busyApparatus.containsKey(extensionNumber)) {
            throw new TelephonyException("Apparatus is busy");
        }

        IncomingCall incomingCall = incomingCalls.remove(phoneNumber);
        if (incomingCall == null) {
            throw new TelephonyException("Call not found in queue");
        }

        ActiveCall activeCall = new ActiveCall();
        activeCall.setExtensionNumber(extensionNumber);
        activeCall.setOperatorName(apparatus.getOperatorName());
        activeCall.setPhoneNumber(phoneNumber);

        activeCalls.put(extensionNumber, activeCall);
        busyApparatus.put(extensionNumber, phoneNumber);
        
    }
    
    //5 запрос - завершить звонок
    //параметр - внутренний номер
    public void endCall(Long extensionNumber) throws TelephonyException{
        ActiveCall activeCall = activeCalls.remove(extensionNumber);
        if (activeCall == null) {
            throw new TelephonyException("No active calls!");
        }
        busyApparatus.remove(extensionNumber);
    }
    //6 запрос - посмотреть активные звонки(вывод - список разговоров)
    public List<ActiveCall> getActiveCalls() {
        return new ArrayList<>(activeCalls.values());
    }
    //по добавочному номеру возвращет - занят ли аппарат
    //если не нашел такого в занятых - вернет налл
    public String getPhoneNumberByExtension(Long extensionNumber) {
        return busyApparatus.get(extensionNumber);
    }
}
