package com.oscgc.securevideo.server.security.suburl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SubUrlLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler
        implements LogoutSuccessHandler {

    public SubUrlLogoutSuccessHandler() {
        super();
    }

    /**
     * Constructor which sets the <tt>defaultTargetUrl</tt> property of the base
     * class.
     *
     * @param defaultTargetUrl the URL to which the user should be redirected on
     *            successful authentication.
     */
    public SubUrlLogoutSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        handle(determineTenantAwareHttpServletRequest(request), response, authentication);
        clearAuthenticationAttributes(request);
    }

    private HttpServletRequest determineTenantAwareHttpServletRequest(HttpServletRequest request) {
        HttpServletRequest retRequest = request;
        if (request instanceof TenantAwareHttpServletRequest) {
            retRequest =
                    (HttpServletRequest) ((TenantAwareHttpServletRequest) request).getRequest();
        }
        return retRequest;
    }

    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process.
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
