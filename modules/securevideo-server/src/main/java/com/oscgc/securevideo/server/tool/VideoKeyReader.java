package com.oscgc.securevideo.server.tool;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by zz on 15-5-29.
 */
public class VideoKeyReader {
    public static void main(String args[]) throws IOException {

        byte[] data = readVideoKey("06187fd79031466ba34ed2dea10f385f.key");
        byte[] newData = new byte[data.length];
        int counter = 0;
        for (byte b : data) {
            System.out.print(b & (0xff));
            System.out.print(",");
            newData[counter++] = (byte)(b & (0xff));
        }
        System.out.println("");
        System.out.println(convertBytesToBase64Url(data));
    }

    public static byte[] readVideoKey(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        return Files.readAllBytes(path);
    }

    public static String convertBytesToBase64Url(byte[] content){
        return Base64.encodeBase64URLSafeString(content);
//        String result = Base64.encodeBase64String(content);
//        return result.replaceAll("\\+","-").replace("=","").replaceAll("\\/","_");
    }
}
