package com.oscgc.securevideo.server.waad.sso;

import java.io.Serializable;

public class Claim implements Serializable {

    private static final long serialVersionUID = -6595685426248469363L;
    private String claimType;
    private String claimValue;

    public Claim(String claimType, String claimValue) {
        super();
        this.claimType = claimType;
        this.claimValue = claimValue;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public String getClaimValue() {
        return claimValue;
    }

    public String[] getClaimValues() {
        return claimValue.split(",");
    }

    public void setClaimValue(String claimValue) {
        this.claimValue = claimValue;
    }

    @Override
    public String toString() {
        return "Claim [claimType=" + claimType + ", claimValue=" + claimValue + "]";
    }
}