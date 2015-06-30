package com.oscgc.securevideo.server.multitenancy;


public interface TenancyContext extends TenancyNaming {

    void setCurrentTenancyNamespace(String value);

    String currentTenancyNamespace();

    String currentTenancyNamespaceOrDefault();

    void clearTenancyNamespace();

    Tenant currentTenancy();

    Tenant currentTenancyOrDefault();

    boolean isTenancyNamespaceValid(String value);

    boolean isDefaultTenancy();

    String getFullNameForCurrentTenant();

    String getFullNameForTenant(String tenantNamespace);

    String getSubNameForCurrentTenant();

    String getRootUrl();
}
