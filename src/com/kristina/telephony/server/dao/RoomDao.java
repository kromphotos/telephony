package com.kristina.telephony.server.dao;

import java.util.List;

import com.kristina.telephony.shared.Room;

public interface RoomDao {
    List<Room> findAll();
    
    Room findById(Long id);
}