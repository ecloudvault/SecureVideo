package com.oscgc.securevideo.server.security;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.oscgc.securevideo.server.model.Tenant;
import com.oscgc.securevideo.server.service.TenantService;
import com.oscgc.securevideo.server.waad.graph.GraphService;
import com.oscgc.securevideo.server.waad.graph.GraphServiceFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

public class X509UserDetailService implements
                                  AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    
    protected static final Logger LOG = LoggerFactory.getLogger(X509UserDetailService.class);
    
    private UserDetailsService defualtUserDetailsService;
    
    private TenantService tenantService;
    
    private GraphServiceFactory graphServiceFactory;
    
    public UserDetailsService getDefualtUserDetailsService() {
        return defualtUserDetailsService;
    }
    
    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        String username = (String) token.getPrincipal();
        
        UserDetails userDetails = loadFromDefault(username);
        if (userDetails != null) {
            return new User(username,
                            userDetails.getPassword(),
                            username,
                            Tenant.DEFAULT_NAMESPACE,
                            createAuthorities());
        }
        
        // get from waad
        userDetails = loadFromWaad(username);
        if (userDetails != null) {
            return userDetails;
        }
        
        throw new UsernameNotFoundException(username);
    }
    
    private UserDetails loadFromDefault(String username) {
        if (defualtUserDetailsService == null) {
            return null;
        }
        
        try {
            return defualtUserDetailsService.loadUserByUsername(username);
        }
        catch (UsernameNotFoundException e) {
            LOG.warn(String.format("Can't find user[username=%s] from default UserDetailsService.",
                                   username),
                     e);
        }
        
        return null;
    }
    
    private UserDetails loadFromWaad(String username) {
        String domain = StringUtils.split(username, '@')[1];
        Tenant tenant = tenantService.findByDomain(domain);
        Preconditions.checkState(tenant != null,
                                 String.format("Tenant[domain=%s] not found.",
                                               domain));
        
        GraphService graphService = graphServiceFactory.getService(tenant.getId());
        com.oscgc.securevideo.server.model.User user = graphService.getUser(username);
        if (user != null) {
            return new User(username,
                            "",
                            user.getDisplayName(),
                            tenant.getId(),
                            createAuthorities());
        }
        
        return null;
    }
    
    private List<SimpleGrantedAuthority> createAuthorities() {
        List<SimpleGrantedAuthority> authorities = Lists.newArrayList();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_CERT"));
        return authorities;
    }
    
    public void setDefualtUserDetailsService(UserDetailsService defualtUserDetailsService) {
        this.defualtUserDetailsService = defualtUserDetailsService;
    }
    
    public TenantService getTenantService() {
        return tenantService;
    }
    
    public void setTenantService(TenantService tenantService) {
        this.tenantService = tenantService;
    }
    
    public GraphServiceFactory getGraphServiceFactory() {
        return graphServiceFactory;
    }
    
    public void setGraphServiceFactory(GraphServiceFactory graphServiceFactory) {
        this.graphServiceFactory = graphServiceFactory;
    }
    
    public X509UserDetailService(UserDetailsService defualtUserDetailsService) {
        this.defualtUserDetailsService = defualtUserDetailsService;
    }
}
