package com.oscgc.securevideo.server.security.suburl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oscgc.securevideo.server.multitenancy.TenantUser;
import com.oscgc.securevideo.server.security.FormLoginAuthenticationEntryPoint;
import com.oscgc.securevideo.server.security.SecurityUtils;
import com.oscgc.securevideo.server.security.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SubUrlTenancyAuthenticationSuccessHandler implements
                                                      AuthenticationSuccessHandler {
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException,
                                                                      ServletException {
        String returnUrl = (String) request.getSession()
                                           .getAttribute(FormLoginAuthenticationEntryPoint.RETURN_URL);
        if (!StringUtils.isEmpty(returnUrl)) {
            request.getSession()
                   .removeAttribute(FormLoginAuthenticationEntryPoint.RETURN_URL);
            response.sendRedirect(returnUrl);
            return;
        }
        
        User user = SecurityUtils.currentUser();
        // if (SecurityUtils.grantedAny(user,
        // PlatformRole.ROLE_SUPER_ADMIN.name(),
        // PlatformRole.ROLE_TENANT_ADMIN.name())) {
        // response.sendRedirect(createSuccessUrl(request, user,
        // "/dashboard/"));
        // return;
        // }
        
        response.sendRedirect(createSuccessUrl(request, user, "/video/list"));
    }
    
    private String createSuccessUrl(HttpServletRequest request,
                                    TenantUser user,
                                    String urlSuffix) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getContextPath());
        if (!request.getContextPath().endsWith(user.getTenantNamespace())) {
            sb.append("/").append(user.getTenantNamespace());
        }
        sb.append(urlSuffix);
        return sb.toString();
    }
}
