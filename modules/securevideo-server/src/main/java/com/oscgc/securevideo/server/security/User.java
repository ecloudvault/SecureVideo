package com.oscgc.securevideo.server.security;

import java.util.Collection;

import com.oscgc.securevideo.server.multitenancy.TenantUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.collect.Lists;

public class User extends TenantUser {
    
    private static final long serialVersionUID = -6980446053346492863L;
    
    private String displayName;
    
    public User(String username,
                String password,
                String displayName,
                String tenantNamespace,
                Collection<? extends GrantedAuthority> authorities) {
        super(username, password, tenantNamespace, authorities);
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
}
