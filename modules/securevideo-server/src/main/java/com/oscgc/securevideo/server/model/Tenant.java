package com.oscgc.securevideo.server.model;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Tenant implements com.oscgc.securevideo.server.multitenancy.Tenant {
    
    private String id;
    
    private String name;
    
    private String namespace;
    
    private List<String> domains;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<String> getDomains() {
        return domains;
    }
    
    public void setDomains(List<String> domains) {
        this.domains = domains;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public String getNamespace() {
        if (StringUtils.isBlank(namespace)) {
            return id;
        }
        return namespace;
    }
    
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
