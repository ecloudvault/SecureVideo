package com.oscgc.securevideo.server.multitenancy;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class TenantUser extends User {
    
    private String tenantNamespace;
    
    public TenantUser(String username,
                      String password,
                      String tenantNamespace,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        initTenantNamespace(tenantNamespace);
    }
    
    public TenantUser(String username,
                      String password,
                      String tenantNamespace,
                      boolean enabled,
                      boolean accountNonExpired,
                      boolean credentialsNonExpired,
                      boolean accountNonLocked,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username,
              password,
              enabled,
              accountNonExpired,
              credentialsNonExpired,
              accountNonLocked,
              authorities);
        initTenantNamespace(tenantNamespace);
    }
    
    private void initTenantNamespace(String tenantNamespace) {
        this.tenantNamespace = tenantNamespace;
        if (StringUtils.isBlank(tenantNamespace)) {
            this.tenantNamespace = Tenant.DEFAULT_NAMESPACE;
        }
    }
    
    public String getTenantNamespace() {
        return tenantNamespace;
    }
}
