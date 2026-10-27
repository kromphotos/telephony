package com.kristina.telephony.server.dao;

import java.util.List;

import com.kristina.telephony.shared.Apparatus;

public interface ApparatusDao {
    List<Apparatus> findByRoomId(Long roomId);
    
    Apparatus findByExtensionNumber(Long extensionNumber);
}
