package com.oscgc.securevideo.server.multitenancy;

public interface TenancyNamingAware {

    void setTenancyNaming(TenancyNaming tenancyNaming);

    TenancyNaming getTenancyNaming();

}
