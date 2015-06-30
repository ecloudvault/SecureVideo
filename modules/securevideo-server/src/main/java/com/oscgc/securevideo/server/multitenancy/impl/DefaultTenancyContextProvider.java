package com.oscgc.securevideo.server.multitenancy.impl;

import com.oscgc.securevideo.server.multitenancy.TenancyContext;
import com.oscgc.securevideo.server.multitenancy.TenancyContextProvider;
import com.oscgc.securevideo.server.multitenancy.TenantProvider;
import com.oscgc.securevideo.server.service.TenantService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultTenancyContextProvider implements TenancyContextProvider, InitializingBean {

    @Autowired
    private TenantProvider tenantProvider;

    private TenancyContext tenancyContext;

    @Override
    public TenancyContext getInstance() {
        return tenancyContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        tenancyContext = new DefaultTenancyContext(tenantProvider);
    }

}
