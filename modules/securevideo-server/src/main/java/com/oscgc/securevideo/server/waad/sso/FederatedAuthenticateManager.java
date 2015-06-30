package com.oscgc.securevideo.server.waad.sso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.oscgc.securevideo.server.waad.graph.GraphProvider;

@Component
public class FederatedAuthenticateManager {
    
    protected static final Logger LOG = LoggerFactory.getLogger(FederatedAuthenticateManager.class);
    
    private static final DateTimeFormatter CHECKING_FORMAT = ISODateTimeFormat.dateTime()
                                                                              .withZone(DateTimeZone.UTC);
    
    @Autowired
    private FederatedProvider config;
    
    @Autowired
    private GraphProvider appConfig;
    
    public FederatedPrincipal authenticate(HttpServletRequest request,
                                           HttpServletResponse response) {
        String token = request.getParameter("wresult").toString();
        if (token == null) {
            throw new RuntimeException("Wrong callback parameter.");
        }
        
        List<Claim> claims = null;
        
        try {
            SamlTokenValidator validator = new SamlTokenValidator(config.getTrustedIssuers(),
                                                                  config.getAudienceUris(),
                                                                  config.getThumbprint());
            claims = validator.validate(token);
            FederatedPrincipal principal = new FederatedPrincipal(claims);
            return principal;
        }
        catch (FederationException e) {
            throw new RuntimeException(e);
        }
        catch (Throwable e) {
            throw new RuntimeException("Federated Login failed!", e);
        }
        finally {
            if (claims == null) {
                request.getSession().invalidate();
                throw new RuntimeException("Invalid Token");
            }
        }
    }
    
    public String createLoginUrl() {
        Calendar c = Calendar.getInstance();
        String encodedDate = CHECKING_FORMAT.print(c.getTimeInMillis());
        
        String encodedRealm = encodeUrl(config.getRealm());
        String encodedReply = encodeUrl(config.getLoginCallbackUrl());
        
        StringBuilder federatedLoginURL = new StringBuilder().append(config.getIssuer())
                                                             .append("?wa=wsignin1.0&wtrealm=")
                                                             .append(encodedRealm)
                                                             .append("&wctx=&id=passive&wct=")
                                                             .append(encodedDate)
                                                             .append("&wreply=")
                                                             .append(encodedReply);
        
        return federatedLoginURL.toString();
    }
    
    public String createLogoutUrl() {
        Calendar c = Calendar.getInstance();
        String encodedDate = CHECKING_FORMAT.print(c.getTimeInMillis());
        
        String encodedRealm = encodeUrl(config.getRealm());
        String encodedReply = encodeUrl(config.getLogoutCallbackUrl());
        
        StringBuilder federatedLogoutURL = new StringBuilder().append(config.getIssuer())
                                                              .append("?wa=wsignout1.0&wtrealm=")
                                                              .append(encodedRealm)
                                                              .append("&wctx=&id=passive&wct=")
                                                              .append(encodedDate)
                                                              .append("&wreply=")
                                                              .append(encodedReply);
        
        return federatedLogoutURL.toString();
    }
    
    public String createConsentUrl() {
        StringBuilder url = new StringBuilder("http://account.activedirectory.windowsazure.com/Consent.aspx?");
        url.append("ClientID=").append(appConfig.getClientId());
        url.append("&RequestedPermissions=DirectoryWriters");
        
        url.append("&ConsentReturnURL=");
        String callback = config.getConsentCallbackUrl();
        if (callback.startsWith("https")) {
            url.append(callback);
        }
        else {
            url.append(callback.replaceFirst("http", "https"));
        }
        
        return url.toString();
    }
    
    private static String encodeUrl(String url) {
        if (url == null || url.isEmpty()) {
            return "";
        }
        else {
            try {
                return URLEncoder.encode(url, "utf-8");
            }
            catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
