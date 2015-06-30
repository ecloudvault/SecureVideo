package com.oscgc.securevideo.server.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <p>
 * This interceptor puts the currently logged username from Spring Security into
 * user session.
 * </p>
 *
 * @author Gary
 */
public class UserAuthenticationInterceptor extends HandlerInterceptorAdapter {
    
    protected static final Logger logger = LoggerFactory.getLogger(UserAuthenticationInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        
        if (logger.isDebugEnabled()) {
            logger.debug("preHandle: " + request.getRequestURL());
            logger.debug("request.authType: " + request.getAuthType());
            if (request.getUserPrincipal() != null) {
                logger.debug("request.userPrincipal.name(): " + request.getUserPrincipal()
                                                                       .getName());
            }
        }
        
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            System.out.println("spring.context.authentication.name: " + SecurityContextHolder.getContext()
                                                                                             .getAuthentication()
                                                                                             .getName());
        }
        
        return super.preHandle(request, response, handler);
    }
    
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("preHandle: " + request.getRequestURL());
            logger.debug("request.authType: " + request.getAuthType());
            if (request.getUserPrincipal() != null) {
                logger.debug("request.userPrincipal.name(): " + request.getUserPrincipal()
                                                                       .getName());
            }
        }
        
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            System.out.println("spring.context.authentication.name: " + SecurityContextHolder.getContext()
                                                                                             .getAuthentication()
                                                                                             .getName());
        }
        
        super.postHandle(request, response, handler, modelAndView);
    }
}
