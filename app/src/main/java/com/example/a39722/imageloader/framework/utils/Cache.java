package com.example.a39722.imageloader.framework.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 39722 on 2017/1/4.
 */
public class Cache {
    public static String genHashKey(String key) {
        String cacheKey;
        try{
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
            }catch(NoSuchAlgorithmException e){
                cacheKey = String.valueOf(key.hashCode());
            }
        return cacheKey;
    }

    public static String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < bytes.length;i++){
            String hex = Integer.toHexString(0xFF&bytes[i]);
            if(hex.length()==1){
                sb.append('0');
            }
            sb.append(hex);
        }
       return sb.toString();
    }
}
