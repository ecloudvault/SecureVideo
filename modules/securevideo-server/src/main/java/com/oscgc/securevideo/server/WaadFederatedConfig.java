package com.oscgc.securevideo.server;

import java.net.URI;
import java.util.List;

import com.oscgc.securevideo.server.waad.sso.FederatedProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
@Configuration
public class WaadFederatedConfig implements InitializingBean, FederatedProvider {
    
    @Value("${waad.issuer}")
    private String issuer;
    
    @Value("${waad.thumbprint}")
    private String thumbprint;

    @Value("${waad.realm}")
    private String realm;

    @Value("${myweb.prefix}")
    private String webPrefix;

    private List<String> trustedIssuers;
    
    private List<URI> audienceUris;

    private String loginCallbackUrl;

    private String logoutCallbackUrl;

    private String consentCallbackUrl;
    
    @Override
    public String getIssuer() {
        return issuer;
    }
    
    @Override
    public String getThumbprint() {
        return thumbprint;
    }
    
    @Override
    public String getRealm() {
        return realm;
    }
    
    @Override
    public List<String> getTrustedIssuers() {
        return trustedIssuers;
    }
    
    @Override
    public List<URI> getAudienceUris() {
        return audienceUris;
    }

    @Override
    public String getLoginCallbackUrl() {
        return loginCallbackUrl;
    }

    @Override
    public String getLogoutCallbackUrl() {
        return logoutCallbackUrl;
    }

    @Override
    public String getConsentCallbackUrl() {
        return consentCallbackUrl;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        trustedIssuers = Lists.newArrayList(issuer);
        audienceUris = Lists.newArrayList(URI.create(realm));
        loginCallbackUrl = webPrefix + "/signin/waad";
        logoutCallbackUrl = webPrefix + "/logout";
        consentCallbackUrl = webPrefix + "/consent/callback";
    }
}
