package com.oscgc.securevideo.server.mvc.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oscgc.securevideo.server.model.Tenant;
import com.oscgc.securevideo.server.model.Video;
import com.oscgc.securevideo.server.multitenancy.TenancyContextProvider;
import com.oscgc.securevideo.server.security.SecurityUtils;
import com.oscgc.securevideo.server.security.User;
import com.oscgc.securevideo.server.service.VideoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oscgc.securevideo.server.tool.MultipartFileSender;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by cengruilin on 15/5/7.
 */
@Controller
@RequestMapping("/video")
public class VideoController {
    Logger logger = LoggerFactory.getLogger(VideoController.class);
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private TenancyContextProvider tenancyContextProvider;
    
    @RequestMapping("/{name:.+}")
    public void getVideo(@PathVariable("name") String name,
                         HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        sendMultipartFile(name, request, response);
    }
    
    // @RequestMapping("/plain")
    // public void getPlainVideo(HttpServletRequest request, HttpServletResponse
    // response) throws Exception {
    // sendMultipartFile("BigBuckBunny_320x180.mp4", request, response);
    // }
    //
    // @RequestMapping("/encrypted")
    // public void getEncryptedVideo(HttpServletRequest request,
    // HttpServletResponse response) throws Exception {
    // sendMultipartFile("BigBuckBunny_320x180_enc.mp4", request, response);
    // }
    
    private void sendMultipartFile(String filePath,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        
        String videoFilePath = VideoController.class.getClassLoader()
                                                    .getResource("")
                                                    .toURI()
                                                    .getPath() + "/upload/"
                               + filePath;
        
        videoFilePath = System.getProperty("os.name").contains("indow") ? videoFilePath.substring(0)
                                                                       : videoFilePath;
        
        MultipartFileSender.fromFile(new File(videoFilePath))
                           .with(request)
                           .with(response)
                           .serveResource();
    }
    
    @RequestMapping("/list")
    public String videoList(ModelMap map) {
        User user = SecurityUtils.currentUser();
        Tenant tenant = (Tenant) tenancyContextProvider.getInstance()
                                                       .currentTenancy();
        List<Video> videos = videoService.findByTenant(tenant.getId());
        
        map.put("videos", videos);
        return "/video/list";
    }
    
    @RequestMapping("/detail/{id}")
    public String videoList(@PathVariable("id") String id, ModelMap map) {
        Video video = videoService.findVideoById(id);
        map.put("video", video);
        return "/video/detail";
    }
    
    @RequestMapping("/isEncrypted/{id}")
    @ResponseBody
    public String isEncrypted(@PathVariable("id") String id) {
        String result = "false";
        Video video = videoService.findVideoById(id);
        if (video.isEncrypted()) {
            result = "true";
        }
        return result;
    }
    
}
