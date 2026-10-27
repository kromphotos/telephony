package com.kristina.telephony.server.dao;

import java.util.List;

import com.kristina.telephony.server.mapper.ApparatusMapper;
import com.kristina.telephony.shared.Apparatus;

public class ApparatusDaoImp implements ApparatusDao {
    private ApparatusMapper mapper;

    public ApparatusDaoImp(ApparatusMapper mapper) {
        this.mapper = mapper;
    }
    
    @Override
    public List<Apparatus> findByRoomId(Long roomId) {
       return mapper.findByRoomId(roomId);
    }

    @Override
    public Apparatus findByExtensionNumber(Long extensionNumber) {
        return mapper.findByExtensionNumber(extensionNumber);
    }
}
