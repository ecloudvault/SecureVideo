package com.oscgc.securevideo.server.multitenancy.impl;


import com.oscgc.securevideo.server.multitenancy.TenancyNaming;

public class DefaultTenancyNaming implements TenancyNaming {

    @Override
    public String getSubNameForTenant(String tenantNamespace) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getFullNameForTenant(String tenantNamespace) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getTenantForSubName(String subName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getRootUrl() {
        throw new UnsupportedOperationException();
    }

}
