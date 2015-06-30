package com.oscgc.securevideo.server.tool;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class RestResponseErrorHandler implements ResponseErrorHandler {
    
    protected static final Logger LOG = LoggerFactory.getLogger(RestResponseErrorHandler.class);
    
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getRawStatusCode() >= 400;
    }
    
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        LOG.error(IOUtils.toString(response.getBody()));
        throw new IllegalStateException("Error occured while accessing remote server.");
    }
    
}
