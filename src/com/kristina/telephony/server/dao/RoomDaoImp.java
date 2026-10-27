package com.kristina.telephony.server.dao;

import java.util.List;

import com.kristina.telephony.server.mapper.RoomMapper;
import com.kristina.telephony.shared.Room;

public class RoomDaoImp implements RoomDao {
    private RoomMapper mapper;

    public RoomDaoImp(RoomMapper mapper) {
        this.mapper = mapper;
    }
    
    @Override
    public List<Room> findAll() {
        return mapper.findAll();
    }

    @Override
    public Room findById(Long id) {
        return mapper.findById(id);
    }
}
