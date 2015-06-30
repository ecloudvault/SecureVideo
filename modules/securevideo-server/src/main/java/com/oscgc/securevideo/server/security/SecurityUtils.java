package com.oscgc.securevideo.server.security;

import com.google.common.base.Preconditions;
import com.oscgc.securevideo.server.multitenancy.Tenant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public final class SecurityUtils {
    
    protected static final Logger LOG = LoggerFactory.getLogger(SecurityUtils.class);
    
    private SecurityUtils() {
        // do nothing
    }
    
    public static User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                return (User) principal;
            }
            else if (principal instanceof org.springframework.security.core.userdetails.User) {
                org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) principal;
                return new User(userDetails.getUsername(),
                                userDetails.getUsername(),
                                userDetails.getUsername(),
                                Tenant.DEFAULT_NAMESPACE,
                                userDetails.getAuthorities());
            }
        }
        
        LOG.warn("Current User is not set, authentication: {}", authentication);
        return null;
    }
    
    public static boolean grantedAny(String... roleNames) {
        return grantedAny(currentUser(), roleNames);
    }
    
    public static boolean grantedAny(User user, String... roleNames) {
        if (user == null) {
            return false;
        }
        
        Collection<? extends GrantedAuthority> roles = user.getAuthorities();
        if (roles == null || roles.isEmpty()) {
            roles = user.getAuthorities();
        }
        
        return grantedAny(roles, roleNames);
    }
    
    private static boolean grantedAny(Collection<? extends GrantedAuthority> roles,
                                      String... roleNames) {
        if (ArrayUtils.isEmpty(roleNames) || CollectionUtils.isEmpty(roles)) {
            return false;
        }
        
        for (String roleName : roleNames) {
            if (granted(roles, roleName)) {
                return true;
            }
        }
        
        return false;
    }
    
    private static boolean granted(Collection<? extends GrantedAuthority> roles,
                                   String roleName) {
        Preconditions.checkNotNull(roleName);
        Preconditions.checkNotNull(roles);
        
        for (GrantedAuthority role : roles) {
            if (roleName.equals(role.getAuthority())) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Programmatically signs in the user with the given the user.
     */
    public static void signin(User user) {
        
        SecurityContextHolder.getContext()
                             .setAuthentication(new UsernamePasswordAuthenticationToken(user,
                                                                                        user,
                                                                                        new NullAuthoritiesMapper().mapAuthorities(user.getAuthorities())));
    }
    
}
