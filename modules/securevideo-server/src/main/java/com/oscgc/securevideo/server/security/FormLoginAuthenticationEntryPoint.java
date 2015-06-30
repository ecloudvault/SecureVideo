package com.oscgc.securevideo.server.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.RedirectUrlBuilder;

public class FormLoginAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public static final String RETURN_URL = "_login_return_url_";

    public FormLoginAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException,
            ServletException {
        String returnUrl = buildHttpReturnUrlForRequest(request);
        request.getSession().setAttribute(RETURN_URL, returnUrl);
        super.commence(request, response, authException);
    }

    protected String buildHttpReturnUrlForRequest(HttpServletRequest request) throws IOException,
            ServletException {

        RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
        urlBuilder.setScheme(request.getScheme());
        urlBuilder.setServerName(request.getServerName());
        urlBuilder.setPort(request.getServerPort());
        urlBuilder.setContextPath(request.getContextPath());
        urlBuilder.setServletPath(request.getServletPath());
        urlBuilder.setPathInfo(request.getPathInfo());
        urlBuilder.setQuery(request.getQueryString());

        return urlBuilder.getUrl();
    }

}
