package com.kristina.telephony.server;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.google.gwt.core.client.GWT;
import com.kristina.telephony.server.dao.ApparatusDao;
import com.kristina.telephony.server.dao.ApparatusDaoImp;
import com.kristina.telephony.server.dao.RoomDao;
import com.kristina.telephony.server.dao.RoomDaoImp;
import com.kristina.telephony.server.mapper.ApparatusMapper;
import com.kristina.telephony.server.mapper.RoomMapper;
import com.kristina.telephony.shared.ActiveCall;
import com.kristina.telephony.shared.Apparatus;
import com.kristina.telephony.shared.Room;
import com.kristina.telephony.shared.TelephonyException;

public class TelephonyServiceImp implements TelephonyService {
    private static final String RESOURCE = "mybatis-config.xml";

    private static SqlSessionFactory sqlSessionFactory;

    private final CallService callService = CallService.getInstance();//хранилище данных в памяти

    static {
        try (InputStream inputStream = Resources.getResourceAsStream(RESOURCE)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
            GWT.log("Error connecton to database");
        }
    }

    private ApparatusDao getApparatusDao(SqlSession session) {
        ApparatusMapper mapper = session.getMapper(ApparatusMapper.class);
        return new ApparatusDaoImp(mapper);
    }

    private RoomDao getRoomDao(SqlSession session) {
        RoomMapper mapper = session.getMapper(RoomMapper.class);
        return new RoomDaoImp(mapper);
    }

    //операция 3 - посмотреть очередь 
    @Override
    public List<String> getQueue() throws TelephonyException {
        return callService.getQueue();
    }

    //операция 4 - ответ на звонок
    @Override
    public void answerCall(Long extensionNumber, String phoneNumber) throws TelephonyException {
        if (extensionNumber == null || phoneNumber == null) {
            throw new TelephonyException("Extension number or phone number is empty!");
        }

        Apparatus apparatus;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            apparatus = getApparatusDao(session).findByExtensionNumber(extensionNumber);
        } catch (Exception e) {
            throw new TelephonyException("Error searching apparatus!");
        }

        if (apparatus == null) {
            throw new TelephonyException("It's empty apparatus!");
        }

        callService.answerCall(extensionNumber, phoneNumber, apparatus);
    }

    //операция 5 - завершение звонка
    @Override
    public void endCall(Long extensionNumber) throws TelephonyException {
        if (extensionNumber == null) {
            throw new TelephonyException("Extension number is empty!");
        }

        callService.endCall(extensionNumber);
    }

    //операция 6 - просмотр активных звонков
    @Override
    public List<ActiveCall> getActiveCalls() throws TelephonyException {
        return callService.getActiveCalls();
    }

    //операция 7 - посмотреть комнаты
    @Override
    public List<Room> getRooms() throws TelephonyException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return getRoomDao(session).findAll();
        } catch (Exception e) {
            throw new TelephonyException("Error searching rooms!");
        }
    }

    //операция 8 - статус аппаратов в комнате 
    @Override
    public List<Apparatus> getApparatusInRoom(Long roomId) throws TelephonyException {
        if (roomId == null) {
            throw new TelephonyException("Room id is empty!");
        }
        
        //проверка - есть ли вообще такая комната айдишник которой ввели????
        Room room;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            room = getRoomDao(session).findById(roomId);
        } catch (Exception e) {
            throw new TelephonyException("Error searching room!");
        }
        if (room == null) {
            throw new TelephonyException("Room is null!");
        }


        List<Apparatus> apparatus;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            apparatus = getApparatusDao(session).findByRoomId(roomId);
        } catch (Exception e) {
            throw new TelephonyException("Error searching apparatus in room!");
        }

        for (Apparatus app : apparatus) {
            if (callService.getPhoneNumberByExtension(app.getExtensionNumber()) != null) {
                app.setPhoneNumber(callService.getPhoneNumberByExtension(app.getExtensionNumber()));
                app.setBusy(true);
            } else {
                app.setPhoneNumber(null);
                app.setBusy(false);
            }
        }
        
        return apparatus;
    }

}
