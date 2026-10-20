package com.kristina.telephony.server.mapper;

import java.util.List;

import com.kristina.telephony.shared.Apparatus;

public interface ApparatusMapper {
    List<Apparatus> findByRoomId(Long roomId);
    
    Apparatus findByExtensionNumber(Long extensionNumber);
}
