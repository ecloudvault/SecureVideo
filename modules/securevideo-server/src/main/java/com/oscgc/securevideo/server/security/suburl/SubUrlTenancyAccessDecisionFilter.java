package com.oscgc.securevideo.server.security.suburl;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oscgc.securevideo.server.multitenancy.TenantUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.oscgc.securevideo.server.multitenancy.TenancyContextProvider;


public class SubUrlTenancyAccessDecisionFilter extends OncePerRequestFilter {

    private static final Log logger = LogFactory.getLog(SubUrlTenancyAccessDecisionFilter.class);

    @Autowired
    private TenancyContextProvider tenancyContextProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String currentTenencyNamespace =
                tenancyContextProvider.getInstance().currentTenancyNamespace();
        if (logger.isDebugEnabled()) {
            logger.debug("Current tenancy domain:" + currentTenencyNamespace);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (StringUtils.isEmpty(currentTenencyNamespace) ||
            request.getRequestURI().contains("/login") ||
            request.getRequestURI().contains("/logout") ||
            authentication == null ||
            !(authentication.getPrincipal() instanceof TenantUser) ||
            ((TenantUser) authentication.getPrincipal()).getTenantNamespace()
                    .equals(currentTenencyNamespace)) {
            filterChain.doFilter(request, response);
        } else {
            if (!((TenantUser) authentication.getPrincipal()).getTenantNamespace()
                    .equals(currentTenencyNamespace)) {
                throw new AccessDeniedException("The user can't access this tenant.");
            }
            filterChain.doFilter(request, response);
        }

    }

    public void setTenancyContextProvider(TenancyContextProvider tenancyContextProvider) {
        this.tenancyContextProvider = tenancyContextProvider;
    }
}
