package com.oscgc.securevideo.server.security.suburl;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class TenantAwareHttpServletRequest extends HttpServletRequestWrapper implements
        Serializable {

    private static final long serialVersionUID = 5708331847573211386L;

    private final String tenantNamespace;

    public TenantAwareHttpServletRequest(HttpServletRequest request, String tenantNamespace) {
        super(request);
        this.tenantNamespace = tenantNamespace;
    }

    @Override
    public String getServletPath() {
        String servletPath = super.getServletPath();
        int start = servletPath.indexOf(tenantNamespace);
        if (start < 0) {
            return servletPath;
        }
        int end = start + tenantNamespace.length();
        return servletPath.substring(end);
    }

    @Override
    public String getContextPath() {
        return super.getContextPath() + "/" + tenantNamespace;
    }
}
