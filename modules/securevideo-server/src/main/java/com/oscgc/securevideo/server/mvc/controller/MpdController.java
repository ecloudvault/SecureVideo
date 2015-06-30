package com.oscgc.securevideo.server.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * Created by cengruilin on 15/5/11.
 */
@Controller
@RequestMapping("/mpd")
public class MpdController {

    @RequestMapping("/{name:.+}")
    public void getMpd(@PathVariable("name") String name,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        InputStream is = MpdController.class.getClassLoader().getResourceAsStream(name);
        org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
    }
}
