package com.oscgc.securevideo.server.multitenancy.impl;

import java.util.concurrent.ConcurrentHashMap;

import com.oscgc.securevideo.server.multitenancy.*;
import org.springframework.util.StringUtils;

public class DefaultTenancyContext implements
                                  TenancyContext,
                                  TenancyNamingAware {
    
    private final ConcurrentHashMap<String, Object> tenancyNamespaceCache = new ConcurrentHashMap<String, Object>();
    
    private final ThreadLocal<String> tenancyNamespaceHolder = new ThreadLocal<String>();

    private TenancyNaming tenancyNaming = new DefaultTenancyNaming();
    
    private final TenantProvider tenantProvider;
    
    public DefaultTenancyContext(TenantProvider tenantProvider) {
        this.tenantProvider = tenantProvider;
    }
    
    @Override
    public TenancyNaming getTenancyNaming() {
        return tenancyNaming;
    }
    
    @Override
    public void setTenancyNaming(TenancyNaming tenancyNaming) {
        this.tenancyNaming = tenancyNaming;
    }
    
    @Override
    public String getSubNameForTenant(String tenantNamespace) {
        return tenancyNaming.getSubNameForTenant(tenantNamespace);
    }
    
    @Override
    public String getTenantForSubName(String subName) {
        return tenancyNaming.getTenantForSubName(subName);
    }
    
    @Override
    public void setCurrentTenancyNamespace(String value) {
        tenancyNamespaceHolder.set(value);
    }
    
    @Override
    public String currentTenancyNamespace() {
        return tenancyNamespaceHolder.get();
    }
    
    @Override
    public String currentTenancyNamespaceOrDefault() {
        String namespace = currentTenancyNamespace();
        if (StringUtils.isEmpty(namespace)) {
            namespace = Tenant.DEFAULT_NAMESPACE;
        }
        return namespace;
    }
    
    @Override
    public void clearTenancyNamespace() {
        tenancyNamespaceHolder.remove();
    }
    
    @Override
    public boolean isTenancyNamespaceValid(String value) {
        if (tenancyNamespaceCache.get(value) != null) {
            return true;
        }
        
        Tenant object = tenantProvider.findByNamespace(value);
        
        if (object != null) {
            tenancyNamespaceCache.put(value, object);
        }
        
        return (tenancyNamespaceCache.get(value) != null);
    }
    
    @Override
    public Tenant currentTenancy() {
        if (tenancyNamespaceHolder.get() == null) {
            return null;
        }
        
        return tenantProvider.findByNamespace(currentTenancyNamespace());
    }
    
    @Override
    public Tenant currentTenancyOrDefault() {
        Tenant tenant = currentTenancy();
        if (tenant == null) {
            tenant = tenantProvider.findByNamespace(Tenant.DEFAULT_NAMESPACE);
        }
        
        return tenant;
    }
    
    @Override
    public boolean isDefaultTenancy() {
        return Tenant.DEFAULT_NAMESPACE.equals(currentTenancyNamespace());
    }
    
    @Override
    public String getFullNameForCurrentTenant() {
        return tenancyNaming.getFullNameForTenant(
                currentTenancyNamespaceOrDefault());
    }
    
    @Override
    public String getFullNameForTenant(String tenantNamespace) {
        return tenancyNaming.getFullNameForTenant(
                currentTenancyNamespaceOrDefault());
    }
    
    @Override
    public String getSubNameForCurrentTenant() {
        return tenancyNaming.getSubNameForTenant(currentTenancyNamespaceOrDefault());
    }
    
    @Override
    public String getRootUrl() {
        return tenancyNaming.getRootUrl();
    }
    
}
