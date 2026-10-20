package com.kristina.telephony.server.mapper;

import java.util.List;

import com.kristina.telephony.shared.Room;

public interface RoomMapper {
    List<Room> findAll();
    
    Room findById(Long id);
}
