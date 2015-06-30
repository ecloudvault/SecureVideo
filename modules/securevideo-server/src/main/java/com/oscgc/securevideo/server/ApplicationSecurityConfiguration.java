package com.oscgc.securevideo.server;

import com.oscgc.securevideo.server.multitenancy.TenancyContextProvider;
import com.oscgc.securevideo.server.security.FormLoginAuthenticationEntryPoint;
import com.oscgc.securevideo.server.security.X509UserDetailService;
import com.oscgc.securevideo.server.security.suburl.*;
import com.oscgc.securevideo.server.service.TenantService;
import com.oscgc.securevideo.server.waad.graph.GraphServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebMvcSecurity
public class ApplicationSecurityConfiguration extends
                                             WebSecurityConfigurerAdapter {
    
    private X509UserDetailService x509UserDetailService;
    
    @Autowired
    private TenantService tenantService;
    
    @Autowired
    private GraphServiceFactory graphServiceFactory;
    
    @Autowired
    private TenancyContextProvider tenancyContextProvider;
    
    @Value("${myweb.prefix}")
    private String webPrefix;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("don@securevideo.com")
            .password("password")
            .roles("USER");
        
        UserDetailsService defualtUserDetailsService = auth.getDefaultUserDetailsService();
        x509UserDetailService = new X509UserDetailService(defualtUserDetailsService);
        x509UserDetailService.setTenantService(tenantService);
        x509UserDetailService.setGraphServiceFactory(graphServiceFactory);
    }
    
    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configLogin(http);
        configAccess(http);
        configFilters(http);
    }
    
    private void configAccess(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/", "/login", "/403", "/api/v1")
            .permitAll()
            .antMatchers("/video/*", "/play/", "/play/unencrypted","/key/*")
            .hasRole("USER")
            .antMatchers("/video/get/*",
                         "/video/detail/*",
                         "/play/encryptedwebm",
                         "/play/encrypted")
            .hasRole("USER");
    }
    
    private void configLogin(HttpSecurity http) throws Exception {
        SubUrlTenancyAuthenticationFailureHandler authenticationFailureHandler = new SubUrlTenancyAuthenticationFailureHandler();
        authenticationFailureHandler.setTenancyContextProvider(tenancyContextProvider);
        
        http.headers()
            .frameOptions()
            .disable()
            .csrf()
            .disable()
            
            .formLogin()
            .failureUrl("/login?error=true")
            .defaultSuccessUrl("/video/list")
            .loginPage("/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .failureHandler(authenticationFailureHandler)
            .permitAll()
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/")
            .logoutSuccessHandler(new SubUrlLogoutSuccessHandler())
            .permitAll()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(new FormLoginAuthenticationEntryPoint("/login"))
            
            .and()
            .x509()
            .subjectPrincipalRegex("EMAILADDRESS=(.*?)(?:,|$)")
            .authenticationUserDetailsService(x509UserDetailService);
    }
    
    private void configFilters(HttpSecurity http) {
        DefaultNamespaceRedirectFilter redirectFilter = new DefaultNamespaceRedirectFilter();
        http.addFilterAfter(redirectFilter, SessionManagementFilter.class);
        
        SubUrlTenancyFilter tenancyFilter = new SubUrlTenancyFilter();
        tenancyFilter.setTenancyContextProvider(tenancyContextProvider);
        tenancyFilter.setRootUrl(webPrefix);
        http.addFilterAfter(tenancyFilter, DefaultNamespaceRedirectFilter.class);
        
        SubUrlTenancyAccessDecisionFilter tenancyAccessFilter = new SubUrlTenancyAccessDecisionFilter();
        tenancyAccessFilter.setTenancyContextProvider(tenancyContextProvider);
        http.addFilterAfter(tenancyAccessFilter, SubUrlTenancyFilter.class);
    }
}
