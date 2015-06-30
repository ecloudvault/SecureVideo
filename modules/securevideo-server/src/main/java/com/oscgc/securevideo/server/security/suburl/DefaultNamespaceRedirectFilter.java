package com.oscgc.securevideo.server.security.suburl;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oscgc.securevideo.server.multitenancy.Tenant;
import com.oscgc.securevideo.server.security.SecurityUtils;
import com.oscgc.securevideo.server.security.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

public class DefaultNamespaceRedirectFilter extends OncePerRequestFilter {
    
    private static final RequestMatcher NON_REDIRECT_MATCHER = new RegexRequestMatcher("^/(signin|signout|consent|signup|api).*",
                                                                                       null);
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException,
                                                            IOException {
        if (org.springframework.util.StringUtils.isEmpty(request.getRequestURI()) || "/".equals(request.getServletPath())
            || NON_REDIRECT_MATCHER.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        if (SubUrlTenancyFilter.IGNORE_NAMESPACE_MATCHER.matches(request)) {
            String url = createRedirectUrl(request);
            response.sendRedirect(url);
            return;
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String createRedirectUrl(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getContextPath()).append("/");
        
        User user = SecurityUtils.currentUser();
        if (user == null) {
            sb.append(Tenant.DEFAULT_NAMESPACE);
        }
        else {
            sb.append(user.getTenantNamespace());
        }
        
        sb.append(request.getRequestURI());
        if (StringUtils.isNotEmpty(request.getQueryString())) {
            sb.append("?").append(request.getQueryString());
        }
        
        return sb.toString();
    }
}
