package com.oscgc.securevideo.server.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GuestController {
    
    @RequestMapping("/")
    public String index() {
        return "index";
    }
    
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    
    @RequestMapping("/logout")
    public String logout() {
        return "logout";
    }
    
    @RequestMapping("/403")
    public String error403() {
        return "403";
    }
    
    @RequestMapping("/downloadClientCert")
    public String downloadClientCert() {
        return "downloadClientCert";
    }
    
    @RequestMapping("/test")
    public String test() {
        return "test";
    }
    
}
