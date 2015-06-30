package com.oscgc.securevideo.server.multitenancy;

public interface TenancyContextProvider {

    TenancyContext getInstance();

}
