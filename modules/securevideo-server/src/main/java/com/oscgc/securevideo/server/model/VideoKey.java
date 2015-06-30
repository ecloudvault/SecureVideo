package com.oscgc.securevideo.server.model;

/**
 * Created by cengruilin on 15/5/27.
 */
public class VideoKey {
    private String id;

    private byte[] content;

    private String content4Base64;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContent4Base64() {
        return content4Base64;
    }

    public void setContent4Base64(String content4Base64) {
        this.content4Base64 = content4Base64;
    }
}
