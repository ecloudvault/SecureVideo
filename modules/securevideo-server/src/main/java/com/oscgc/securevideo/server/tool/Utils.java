package com.oscgc.securevideo.server.tool;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Utils {

    public static String encodeUrl(String url) {
        try {
            return URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

}
