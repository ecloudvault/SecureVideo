package com.oscgc.securevideo.server.mvc.controller;

import com.oscgc.securevideo.server.model.Tenant;
import com.oscgc.securevideo.server.service.TenantService;
import com.oscgc.securevideo.server.waad.graph.GraphServiceFactory;
import com.oscgc.securevideo.server.waad.sso.FederatedAuthenticateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by LiangBin on 6/5/15.
 */
@Controller
@RequestMapping("/consent")
public class WaadConsentController {
    
    @Autowired
    private FederatedAuthenticateManager authManager;
    
    @Autowired
    private GraphServiceFactory graphServiceFactory;
    
    @Autowired
    private TenantService tenantService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String consentRedirect() {
        return "redirect:" + authManager.createConsentUrl();
    }
    
    @RequestMapping("/callback")
    public String consentCallback(HttpServletRequest request, ModelMap map) {
        String consent = request.getParameter("Consent");
        
        if ("Granted".equals(consent)) {
            Tenant tenant = new Tenant();
            tenant.setId(request.getParameter("TenantId"));
            tenant.setName("New Tenant");
            tenant = tenantService.create(tenant);

            map.put("tenant", tenant);
            return "consent/granted";
        }
        else {
            return "consent/denied";
        }
    }
    
}
