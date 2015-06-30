package com.oscgc.securevideo.server.waad.graph.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

class TokenGenerator {

    private final static Logger LOG = LoggerFactory.getLogger(TokenGenerator.class);

    private final String endpoint;

    private final String data;

    private final RestTemplate restTemplate;

    private AccessToken token;

    public TokenGenerator(String endpoint, String data, RestTemplate restTemplate) {
        this.endpoint = endpoint;
        this.data = data;
        this.restTemplate = restTemplate;
    }

    public String getToken() {
        if (token == null) {
            LOG.info("Token is null, requesting.");
            fetchToken();
        }

        // check if token expires
        long expiresOn = Long.valueOf(token.getExpiresOn());
        if (expiresOn < System.currentTimeMillis()) {
            LOG.info("Token is expire, requesting for a new one.");
            fetchToken();
        }

        return token.getAccessToken();
    }

    private void fetchToken() {
        token = restTemplate.postForObject(endpoint, data, AccessToken.class);
        if (StringUtils.isBlank(token.getExpiresOn())) {
            token.setExpiresOn(String.valueOf(System.currentTimeMillis() +
                                              Long.valueOf(token.getExpiresIn()) * 1000));
        }
    }
}
