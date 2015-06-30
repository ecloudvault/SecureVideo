package com.oscgc.securevideo.server.mvc.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.oscgc.securevideo.server.security.SecurityUtils;
import com.oscgc.securevideo.server.security.User;
import com.oscgc.securevideo.server.service.TenantService;
import com.oscgc.securevideo.server.waad.graph.GraphServiceFactory;
import com.oscgc.securevideo.server.waad.sso.FederatedAuthenticateManager;
import com.oscgc.securevideo.server.waad.sso.FederatedPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WaadSignController {
    
    protected static final Logger LOG = LoggerFactory.getLogger(WaadSignController.class);
    
    @Autowired
    private FederatedAuthenticateManager authManager;
    
    @Autowired
    private GraphServiceFactory graphServiceFactory;
    
    @Autowired
    private TenantService tenantService;
    
    @RequestMapping(value = "/signin/waad", method = RequestMethod.POST)
    public String signinCallback(HttpServletRequest request,
                                 HttpServletResponse response) {
        FederatedPrincipal principal = authManager.authenticate(request,
                                                                response);
        if (principal == null) {
            throw new RuntimeException("Authenticate failed.");
        }
        
        String id = principal.getId();
        if (id == null || id.isEmpty()) {
            throw new RuntimeException("Authenticate failed.");
        }
        
        // com.brivolabs.birdwatcher.service.User azureUser =
        // graphServiceFactory.getService(principal.getTenantId()).getUser(id);
        //
        // String countryCode = null, mobile = null;
        // if (StringUtils.isNotBlank(azureUser.getMobile())) {
        // String[] phones = StringUtils.split(azureUser.getMobile(), " ");
        // if (phones.length == 2) {
        // countryCode = phones[0];
        // if (countryCode.startsWith("+")) {
        // countryCode.substring(1);
        // }
        // mobile = phones[1];
        // }
        // }
        //
        // SocialUtils.signin(new User(principal.getName(),
        // principal.getTenantId(), countryCode,
        // mobile));
        List<SimpleGrantedAuthority> authorities = Lists.newArrayList(new SimpleGrantedAuthority("ROLE_USER"),
                                                                      new SimpleGrantedAuthority("ROLE_WAAD"));
        SecurityUtils.signin(new User(principal.getName(),
                                      "",
                                      principal.getName(),
                                      principal.getTenantId(),
                                      authorities));
        
        return "redirect:/video/list";
    }
    
    @RequestMapping(value = "/signin/waad", method = RequestMethod.GET)
    public String signin(HttpServletResponse response) throws IOException {
        response.sendRedirect(authManager.createLoginUrl());
        return null;
    }
    
    @RequestMapping(value = "/signout/waad", method = RequestMethod.GET)
    public String signout(HttpServletResponse response) throws IOException {
        response.sendRedirect(authManager.createLogoutUrl());
        return null;
    }
    
}
