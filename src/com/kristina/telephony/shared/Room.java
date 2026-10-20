package com.kristina.telephony.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Room implements IsSerializable {
    private Long id;
    private String name;
    
    public Room(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Room() {
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Room [id=" + id + ", name=" + name + "]";
    }
}
