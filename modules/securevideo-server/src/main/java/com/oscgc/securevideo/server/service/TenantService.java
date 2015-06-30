package com.oscgc.securevideo.server.service;

import com.oscgc.securevideo.server.model.Tenant;
import com.oscgc.securevideo.server.multitenancy.TenantProvider;

import java.util.List;

public interface TenantService extends TenantProvider {

    public List<Tenant> listTenants();

    public Tenant findById(String id);

    public Tenant findByDomain(String domain);

    public Tenant create(Tenant tenant);
}
