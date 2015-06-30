package com.oscgc.securevideo.server.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/play")
public class PlayController {
    
    @RequestMapping("/")
    public String index() {
        return "redirect:/play/unencrypted";
    }
    
    @RequestMapping("/unencrypted")
    public String unencrypted() {
        return "play/unencrypted";
    }
    
    @RequestMapping("/encryptedwebm")
    public String encryptedwebm() {
        return "play/encryptedwebm";
    }
    
    @RequestMapping("/encrypted")
    public String encrypted() {
        return "play/encrypted";
    }
    
}
