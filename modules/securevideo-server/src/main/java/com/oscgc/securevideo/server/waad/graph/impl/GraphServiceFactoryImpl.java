package com.oscgc.securevideo.server.waad.graph.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.oscgc.securevideo.server.waad.graph.GraphService;
import com.oscgc.securevideo.server.waad.graph.GraphServiceFactory;
import com.oscgc.securevideo.server.waad.graph.GraphProvider;
import com.oscgc.securevideo.server.tool.RestResponseErrorHandler;
import com.oscgc.securevideo.server.tool.Utils;

@Service
public class GraphServiceFactoryImpl implements GraphServiceFactory {
    
    private static final Map<String, GraphService> services = Maps.newHashMap();
    
    @Autowired
    private GraphProvider config;
    
    public GraphService getService(String tenantId) {
        GraphService service = services.get(tenantId);
        if (service == null) {
            service = initialService(tenantId);
            services.put(tenantId, service);
        }
        return service;
    }
    
    private GraphService initialService(String tenantId) {
        RestResponseErrorHandler errorHandler = new RestResponseErrorHandler();
        RestTemplate restTemplate = new RestTemplate();
        RestTemplate tokenRestTemplate = new RestTemplate();
        tokenRestTemplate.setErrorHandler(errorHandler);
        
        List<ClientHttpRequestInterceptor> tokenInterceptors = new ArrayList<>();
        tokenInterceptors.add(new TokenClientHttpRequestInterceptor());
        tokenRestTemplate.setInterceptors(tokenInterceptors);
        
        TokenGenerator tokenGenerator = new TokenGenerator(String.format(config.getTokenEndpoint(),
                                                                         tenantId),
                                                           initialOAuth2Data(tenantId),
                                                           tokenRestTemplate);
        
        List<ClientHttpRequestInterceptor> graphInterceptors = new ArrayList<>();
        graphInterceptors.add(new GraphClientHttpRequestInterceptor(tokenGenerator));
        restTemplate.setInterceptors(graphInterceptors);
        
        restTemplate.setErrorHandler(errorHandler);
        return new GraphServiceImpl(config, restTemplate, tenantId);
    }
    
    private String initialOAuth2Data(String tenantId) {
        StringBuilder sb = new StringBuilder();
        sb.append("grant_type=client_credentials");
        sb.append("&client_id=").append(config.getClientId());
        sb.append("&client_secret=")
          .append(Utils.encodeUrl(config.getClientSecret()));
        sb.append("&resource=").append(Utils.encodeUrl(config.getResource()));
        return sb.toString();
    }
    
    class TokenClientHttpRequestInterceptor implements
                                           ClientHttpRequestInterceptor {
        
        @Override
        public ClientHttpResponse intercept(HttpRequest request,
                                            byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders()
                   .setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            return execution.execute(request, body);
        }
    }
    
    class GraphClientHttpRequestInterceptor implements
                                           ClientHttpRequestInterceptor {
        
        private TokenGenerator tokenGenerator;
        
        public GraphClientHttpRequestInterceptor(TokenGenerator tokenGenerator) {
            super();
            this.tokenGenerator = tokenGenerator;
        }
        
        @Override
        public ClientHttpResponse intercept(HttpRequest request,
                                            byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().set("Authorization",
                                     "Bearer " + tokenGenerator.getToken());
            request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return execution.execute(request, body);
        }
    }
}
