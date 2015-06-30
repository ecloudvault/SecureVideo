package com.oscgc.securevideo.server.service;

import com.oscgc.securevideo.server.model.UserKey;
import com.oscgc.securevideo.server.security.User;

/**
 * Created by cengruilin on 15/6/9.
 */
public interface UserKeyService {
    
    public UserKey generate(User owner);
    
    public UserKey findByUsername(String username);
    
    public void reGenerate(UserKey userKey);
}
