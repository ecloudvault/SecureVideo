package com.oscgc.securevideo.server.security.suburl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oscgc.securevideo.server.multitenancy.TenancyContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.StringUtils;

public class SubUrlTenancyAuthenticationFailureHandler extends
        SimpleUrlAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private TenancyContextProvider tenancyContextProvider;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException,
            ServletException {
        saveException(request, exception);

        String currentTenancyNamespace =
                tenancyContextProvider.getInstance().currentTenancyNamespace();
        if (StringUtils.isEmpty(currentTenancyNamespace)) {
            response.sendRedirect("/login?error=true");
        } else {
            response.sendRedirect("/" +
                                  tenancyContextProvider.getInstance()
                                          .getSubNameForTenant(currentTenancyNamespace) +
                                  "/login?error=true");
        }
    }

    public void setTenancyContextProvider(TenancyContextProvider tenancyContextProvider) {
        this.tenancyContextProvider = tenancyContextProvider;
    }

}
