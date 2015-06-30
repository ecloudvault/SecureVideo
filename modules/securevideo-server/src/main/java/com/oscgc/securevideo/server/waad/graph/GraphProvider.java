package com.oscgc.securevideo.server.waad.graph;

public interface GraphProvider {
    
    public String getTokenEndpoint();
    
    public String getClientId();
    
    public String getClientSecret();
    
    public String getResource();
    
    public String getGraphEndpoint();
    
    public String getGraphVersion();
    
}
