package com.oscgc.securevideo.server.multitenancy;

public interface TenancyNaming {

    String getSubNameForTenant(String tenantNamespace);

    String getFullNameForTenant(String tenantNamespace);

    String getTenantForSubName(String subName);

    String getRootUrl();

}
