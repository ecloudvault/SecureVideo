package com.oscgc.securevideo.server.waad.sso;

import java.net.URI;
import java.util.List;

public interface FederatedProvider {

    String getIssuer();

    String getThumbprint();

    String getRealm();

    List<String> getTrustedIssuers();

    List<URI> getAudienceUris();

    String getLoginCallbackUrl();

    String getLogoutCallbackUrl();

    String getConsentCallbackUrl();
}
