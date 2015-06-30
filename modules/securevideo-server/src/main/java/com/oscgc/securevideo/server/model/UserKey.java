package com.oscgc.securevideo.server.model;

import com.oscgc.securevideo.server.security.User;

/**
 * Created by cengruilin on 15/6/9.
 */
public class UserKey {
    public final static String KEY_FORMAT_RSA = "RSA";
    
    private com.oscgc.securevideo.server.security.User owner;
    
    private String keyFormat = KEY_FORMAT_RSA;
    
    private byte[] privateKey;
    
    private byte[] publicKey;
    
    private String pemPrivateKey;
    
    private String pemPublicKey;
    
    public byte[] getPrivateKey() {
        return privateKey;
    }
    
    public void setPrivateKey(byte[] privateKey) {
        this.privateKey = privateKey;
    }
    
    public byte[] getPublicKey() {
        return publicKey;
    }
    
    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }
    
    public String getPemPrivateKey() {
        return pemPrivateKey;
    }
    
    public void setPemPrivateKey(String pemPrivateKey) {
        this.pemPrivateKey = pemPrivateKey;
    }
    
    public String getPemPublicKey() {
        return pemPublicKey;
    }
    
    public void setPemPublicKey(String pemPublicKey) {
        this.pemPublicKey = pemPublicKey;
    }
    
    public User getOwner() {
        return owner;
    }
    
    public void setOwner(User owner) {
        this.owner = owner;
    }
    
}
