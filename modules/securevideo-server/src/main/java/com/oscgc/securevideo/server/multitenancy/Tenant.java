package com.oscgc.securevideo.server.multitenancy;

public interface Tenant {

    public static final String DEFAULT_NAMESPACE = "_default_";

    String getNamespace();

}
