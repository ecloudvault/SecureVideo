package com.oscgc.securevideo.server.waad.graph.impl;

import com.oscgc.securevideo.server.model.User;
import com.oscgc.securevideo.server.waad.graph.GraphService;
import com.oscgc.securevideo.server.waad.graph.GraphProvider;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class GraphServiceImpl implements GraphService {
    
    private GraphProvider config;
    
    private RestTemplate restTemplate;
    
    private String graphEndpoint;
    
    public GraphServiceImpl(GraphProvider config,
                            RestTemplate restTemplate,
                            String tenantId) {
        this.config = config;
        this.restTemplate = restTemplate;
        graphEndpoint = String.format(config.getGraphEndpoint(), tenantId);
    }
    
    @Override
    public List<User> listUsers() {
        String url = generateUrl("/users");
        
        UserList list = restTemplate.getForObject(url, UserList.class);
        return list.getValue();
    }
    
    @Override
    public User getUser(String id) {
        String url = generateUrl("/users/" + id);
        User user = restTemplate.getForObject(url, User.class);
        
        return user;
    }
    
    private String generateUrl(String keyParams) {
        StringBuilder sb = new StringBuilder(graphEndpoint);
        sb.append(keyParams);
        if (keyParams.indexOf("?") > 0) {
            sb.append("&");
        }
        else {
            sb.append("?");
        }
        sb.append("api-version=").append(config.getGraphVersion());
        
        return sb.toString();
    }
    
}
