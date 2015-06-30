package com.oscgc.securevideo.server.waad.sso;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

public class FederatedPrincipal implements Principal {
    
    private static final String ID_TYPE = "http://schemas.microsoft.com/identity/claims/objectidentifier";
    
    private static final String NAME_TYPE = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name";
    
    private static final String EMAIL_TYPE = "http://schemas.xmlsoap.org/claims/EmailAddress";
    
    private static final String TENANT_TYPE = "http://schemas.microsoft.com/identity/claims/tenantid";
    
    private static final String DISPLAY_NAME_TYPE = "http://schemas.microsoft.com/identity/claims/displayname";
    
    protected List<Claim> claims = null;
    
    public FederatedPrincipal(List<Claim> claims) {
        this.claims = claims;
    }
    
    public String getName() {
        String name = getClaimValue(NAME_TYPE);
        ;
        
        if (name == null || name.isEmpty()) {
            name = getClaimValue(EMAIL_TYPE);
        }
        
        return name;
    }
    
    public String getId() {
        return getClaimValue(ID_TYPE);
    }
    
    public String getTenantId() {
        return getClaimValue(TENANT_TYPE);
    }
    
    public String getDisplayName() {
        return getClaimValue(DISPLAY_NAME_TYPE);
    }
    
    private String getClaimValue(String claimType) {
        for (Claim claim : claims) {
            if (claim.getClaimType().equals(claimType))
                return claim.getClaimValue();
        }
        return null;
    }
    
    public List<Claim> getClaims() {
        return Collections.unmodifiableList(this.claims);
    }
}
