package com.oscgc.securevideo.server;

import com.oscgc.securevideo.server.waad.graph.GraphProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WaadGraphConfig implements GraphProvider {

    @Value("${oauth2.token.endpoint}")
    private String tokenEndpoint;

    @Value("${oauth2.client.id}")
    private String clientId;

    @Value("${oauth2.client.secret}")
    private String clientSecret;

    @Value("${oauth2.resource}")
    private String resource;

    @Value("${graph.endpoint}")
    private String graphEndpoint;

    @Value("${graph.version}")
    private String graphVersion;

    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    public void setTokenEndpoint(String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getGraphEndpoint() {
        return graphEndpoint;
    }

    public void setGraphTenant(String graphEndpoint) {
        this.graphEndpoint = graphEndpoint;
    }

    public String getGraphVersion() {
        return graphVersion;
    }

    public void setGraphVersion(String graphVersion) {
        this.graphVersion = graphVersion;
    }
}
