package com.oscgc.securevideo.server.tool;

import java.util.UUID;

/**
 */
public class UuidGenerator implements IdGenerator {
    
    @Override
    public String generateId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
}
