package com.oscgc.securevideo.server.waad.graph;

public interface GraphServiceFactory {
    
    public GraphService getService(String tenantId);
    
}
