package com.oscgc.securevideo.server.mvc.controller;

import com.oscgc.securevideo.server.model.UserKey;
import com.oscgc.securevideo.server.service.UserKeyService;
import com.oscgc.securevideo.server.service.VideoService;
import com.oscgc.securevideo.server.tool.rsa.RsaKeyTools;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by cengruilin on 15/5/26.
 */
@Controller
@RequestMapping("/api/v1")
public class VideoKeyController {
	Logger logger = LoggerFactory.getLogger(VideoKeyController.class);
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private UserKeyService keyService;
    
    @RequestMapping(value = "/videokey", method = RequestMethod.POST)
    @ResponseBody
    private String getKey4Base64( String videoId,
                                 String username,
                                 String content,
                                  String signValue) {
	    logger.debug("videoId:"+videoId+"--username:"+username+"--content:"+content+"--signValue:"+signValue);
        UserKey userKey = keyService.findByUsername(username);
        byte[] signbyte = RsaKeyTools.hexStringToByteArray(signValue);
        if (RsaKeyTools.verify(content, userKey.getPublicKey(), signbyte)) {
	        logger.debug("start encrypt videokey");
            String videokey = videoService.findVideoById(videoId)
                                          .getVideoKey()
                                          .getContent4Base64();
	        logger.debug("videokey--:"+videokey);
            byte[] encodeVidekeByte = RsaKeyTools.encryptByPublicKey(videokey,
                                                                     userKey.getPublicKey());
	        logger.debug("videokey encrypted value is :"+new String(Base64.encode(encodeVidekeByte)));
            return new String(Base64.encode(encodeVidekeByte));
        }
        return "";
    }
    
}
