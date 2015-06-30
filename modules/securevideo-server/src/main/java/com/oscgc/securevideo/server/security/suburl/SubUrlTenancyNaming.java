package com.oscgc.securevideo.server.security.suburl;

import com.oscgc.securevideo.server.multitenancy.TenancyNaming;
import com.oscgc.securevideo.server.multitenancy.Tenant;
import org.springframework.util.StringUtils;

public class SubUrlTenancyNaming implements TenancyNaming {

    private final String rootUrl;

    public SubUrlTenancyNaming(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    @Override
    public String getSubNameForTenant(String tenancyNamespace) {
        if (Tenant.DEFAULT_NAMESPACE.equals(tenancyNamespace)) {
            // return Configuration.getInstance().getBaseDomainName();
            return Tenant.DEFAULT_NAMESPACE; // TODO
        }

        return tenancyNamespace;
    }

    @Override
    public String getFullNameForTenant(String tenancyNamespace) {
        StringBuilder sb = new StringBuilder(getRootUrl());
        if (!StringUtils.isEmpty(tenancyNamespace)) {
            sb.append("/");
        }
        if (Tenant.DEFAULT_NAMESPACE.equals(tenancyNamespace)) {
            // sb.append(Configuration.getInstance().getBaseDomainName());
            sb.append(Tenant.DEFAULT_NAMESPACE); // TODO
        } else {
            sb.append(tenancyNamespace);
        }
        return sb.toString();
    }

    @Override
    public String getTenantForSubName(String subName) {
        // if (Configuration.getInstance().getBaseDomainName().equals(subName))
        // {
        // return Tenant.DEFAULT_NAMESPACE;
        // }
        if (Tenant.DEFAULT_NAMESPACE.equals(subName)) {
            return Tenant.DEFAULT_NAMESPACE;
        } // TODO

        return subName;
    }

    @Override
    public String getRootUrl() {
        return rootUrl;
    }

}
