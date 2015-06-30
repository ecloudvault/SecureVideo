package com.oscgc.securevideo.server.multitenancy;

public interface TenantProvider {

    Tenant findByNamespace(String namespace);
}
