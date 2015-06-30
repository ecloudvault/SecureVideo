package com.oscgc.securevideo.server.service.impl;

import com.oscgc.securevideo.server.model.UserKey;
import com.oscgc.securevideo.server.security.SecurityUtils;
import com.oscgc.securevideo.server.security.User;
import com.oscgc.securevideo.server.service.UserKeyService;
import com.oscgc.securevideo.server.tool.rsa.RsaKeyTools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cengruilin on 15/6/9.
 */
@Service
public class UserKeyServiceImpl implements UserKeyService {
    private Map<String, UserKey> datas = new HashMap<String, UserKey>();
    
    @Value("${rsa.keysize}")
    public int keySize;
    
    @Override
    public UserKey generate(User owner) {
        if (owner == null) {
            return null;
        }
        UserKey userKey = new UserKey();
        userKey.setOwner(owner);
        
        setUserKeys(userKey);
        
        datas.put(owner.getUsername(), userKey);
        return userKey;
    }
    
    @Override
    public UserKey findByUsername(String username) {
        UserKey userKey = datas.get(username);
        if (userKey == null) {
            userKey = generate(SecurityUtils.currentUser());
        }
        return userKey;
    }
    
    @Override
    public void reGenerate(UserKey userKey) {
        setUserKeys(userKey);
        datas.put(userKey.getOwner().getUsername(), userKey);
    }
    
    private void setUserKeys(UserKey userKey) {
        KeyPair keyPair = RsaKeyTools.generateRSAKeyPair(keySize);
        
        if (keyPair != null) {
            
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            
            userKey.setPrivateKey(privateKey.getEncoded());
            userKey.setPublicKey(publicKey.getEncoded());
            
            userKey.setPemPrivateKey(RsaKeyTools.convertToPemKey(null,
		            privateKey));
            userKey.setPemPublicKey(RsaKeyTools.convertToPemKey(publicKey,
		            null));
        }
    }
}
