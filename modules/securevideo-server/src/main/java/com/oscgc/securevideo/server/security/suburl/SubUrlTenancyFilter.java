package com.oscgc.securevideo.server.security.suburl;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oscgc.securevideo.server.multitenancy.TenancyContext;
import com.oscgc.securevideo.server.multitenancy.TenancyContextProvider;
import com.oscgc.securevideo.server.multitenancy.TenancyNamingAware;
import com.oscgc.securevideo.server.multitenancy.Tenant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

// http://www.infoq.com/presentations/Securing-OAuth2-Spring-Security
// http://www.developer.com/java/ent/extend-spring-security-to-protect-multi-tenant-saas-applications.html
// http://www.infoq.com/interviews/robert-winch-spring-security-multi-tenant-applications
public class SubUrlTenancyFilter extends OncePerRequestFilter {
    
    private static final Log logger = LogFactory.getLog(SubUrlTenancyFilter.class);
    
    private static final String KEY_CURRENT_TENANT = "_currentTenant";
    
    public static final RequestMatcher IGNORE_NAMESPACE_MATCHER = new RegexRequestMatcher("^/(what-we-do|social|consent|signup|signin|signout|connect|guest|download|error|maintenance|static|css|js|img|image|images|font|fonts|favicon|index|learn|user|login|logout|home|dashboard|api|my|video|privacyPolicy|termsOfService|access|robots|saml\\/|openid\\/|saml2\\/|oauth\\/|oauth2\\/|2factor\\/|2faas\\/|openid\\/|_backdoor_|_ah).*",
                                                                                          null);
    
    private TenancyContextProvider tenancyContextProvider;
    
    private String baseDomainName;
    
    private String rootUrl;
    
    @Override
    public void initFilterBean() throws ServletException {
        // baseDomainName = Configuration.getInstance().getBaseDomainName();
        baseDomainName = Tenant.DEFAULT_NAMESPACE; // TODO
        TenancyContext tenancyContext = tenancyContextProvider.getInstance();
        if (tenancyContext instanceof TenancyNamingAware) {
            ((TenancyNamingAware) tenancyContext).setTenancyNaming(new SubUrlTenancyNaming(rootUrl));
        }
    }
    
    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }
    
    public void setTenancyContextProvider(TenancyContextProvider tenancyContextProvider) {
        this.tenancyContextProvider = tenancyContextProvider;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException,
                                                            IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("Current request uri: " + request.getRequestURI());
        }
        
        try {
            if (StringUtils.isEmpty(request.getRequestURI()) || "/".equals(request.getServletPath())
                || IGNORE_NAMESPACE_MATCHER.matches(request)) {
                doFilterWithIgnorePath(request, response, filterChain);
            }
            else {
                doFilterWithTenant(request, response, filterChain);
            }
        }
        finally {
            clearTenancy();
        }
    }
    
    private String validateTenancyNamespace(String tenancyNamespace) {
        String result = tenancyNamespace;
        
        if (StringUtils.isEmpty(tenancyNamespace)) {
            throw new IllegalStateException("No tenant specified in the request.");
        }
        
        if (tenancyNamespace.equals(baseDomainName)) {
            result = Tenant.DEFAULT_NAMESPACE;
        }
        
        if (!tenancyContextProvider.getInstance()
                                   .isTenancyNamespaceValid(result)) {
            throw new IllegalStateException(String.format("The tenant %s is illegal.",
                                                          result));
        }
        
        return result;
    }
    
    private void doFilterWithIgnorePath(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain) throws IOException,
                                                                ServletException {
        setCurrentTenantToRequest(request);
        filterChain.doFilter(request, response);
    }
    
    private void doFilterWithTenant(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException,
                                                            ServletException {
        String orginalNamespace = getTenantId(request);
        String tenancyNamespace = orginalNamespace;
        tenancyNamespace = validateTenancyNamespace(tenancyNamespace);
        
        String requestUrl = currentUrl(request);
        if (requestUrl.equals("/" + orginalNamespace)) {
            setCurrentTenantToRequest(request);
            response.sendRedirect("/" + orginalNamespace + "/");
            return;
        }
        
        tenancyContextProvider.getInstance()
                              .setCurrentTenancyNamespace(tenancyNamespace);
        setCurrentTenantToRequest(request);
        filterChain.doFilter(new TenantAwareHttpServletRequest(request,
                                                               orginalNamespace),
                             response);
    }
    
    private void clearTenancy() {
        tenancyContextProvider.getInstance().clearTenancyNamespace();
    }
    
    private String getTenantId(HttpServletRequest request) {
        String tenantId = tenancyContextProvider.getInstance()
                                                .currentTenancyNamespace();
        if (tenantId != null) {
            return tenantId;
        }
        
        String requestUrl = currentUrl(request);
        if (!Pattern.matches("/?", requestUrl)) {
            StringTokenizer tokens = new StringTokenizer(requestUrl, "/");
            if (tokens.hasMoreTokens()) {
                return tokens.nextToken();
            }
        }
        
        return null;
    }
    
    private String currentUrl(HttpServletRequest request) {
        StringBuilder url = new StringBuilder();
        url.append(request.getServletPath());
        
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            url.append(pathInfo);
        }
        return url.toString();
    }
    
    private void setCurrentTenantToRequest(HttpServletRequest request) {
        request.setAttribute(KEY_CURRENT_TENANT,
                             tenancyContextProvider.getInstance()
                                                   .currentTenancyOrDefault());
    }
    
}
