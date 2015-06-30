package com.oscgc.securevideo.server.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.oscgc.securevideo.server.model.Tenant;
import com.oscgc.securevideo.server.service.TenantService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantServiceImpl implements TenantService, InitializingBean {
    
    private static List<Tenant> tenants = Lists.newArrayList();
    
    @Override
    public List<Tenant> listTenants() {
        return tenants;
    }
    
    @Override
    public Tenant findById(String id) {
        for (Tenant tenant : tenants) {
            if (tenant.getId().equals(id)) {
                return tenant;
            }
        }
        return null;
    }
    
    @Override
    public Tenant findByDomain(String domain) {
        for (Tenant tenant : tenants) {
            if (tenant.getDomains() != null) {
                for (String dd : tenant.getDomains()) {
                    if (dd.equalsIgnoreCase(domain)) {
                        return tenant;
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public Tenant create(Tenant tenant) {
        Preconditions.checkArgument(tenant != null && StringUtils.isNotBlank(tenant.getId()));
        
        Tenant exists = findById(tenant.getId());
        if (exists != null) {
            return exists;
        }

        tenants.add(tenant);
        return tenant;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        Tenant td = new Tenant();
        td.setId(Tenant.DEFAULT_NAMESPACE);
        td.setName("Default Tenant");
        tenants.add(td);
        
        Tenant t1 = new Tenant();
        t1.setId("bb4ca82e-8908-4eed-8fe3-9271cfc25d09");
        t1.setName("Secure Video");
        t1.setDomains(Lists.newArrayList("securevideo.onmicrosoft.com"));
        tenants.add(t1);
    }
    
    @Override
    public Tenant findByNamespace(String namespace) {
        return findById(namespace);
    }
}
