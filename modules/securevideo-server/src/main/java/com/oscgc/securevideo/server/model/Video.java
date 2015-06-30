package com.oscgc.securevideo.server.model;

/**
 * Created by cengruilin on 15/5/27.
 */
public class Video {
    
    private VideoKey videoKey;
    
    private String id;
    
    private boolean encrypted = false;
    
    private String formart;
    
    private String orgiFileName;
    
    private String encrptedFileName;
    
    private String name;
    
    private String tenantId;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public VideoKey getVideoKey() {
        return videoKey;
    }
    
    public void setVideoKey(VideoKey videoKey) {
        this.videoKey = videoKey;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public boolean isEncrypted() {
        return encrypted;
    }
    
    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }
    
    public String getFormart() {
        return formart;
    }
    
    public void setFormart(String formart) {
        this.formart = formart;
    }
    
    public String getOrgiFileName() {
        return orgiFileName;
    }
    
    public void setOrgiFileName(String orgiFileName) {
        this.orgiFileName = orgiFileName;
    }
    
    public String getEncrptedFileName() {
        return encrptedFileName;
    }
    
    public void setEncrptedFileName(String encrptedFileName) {
        this.encrptedFileName = encrptedFileName;
    }
    
    public String getTenantId() {
        return tenantId;
    }
    
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
