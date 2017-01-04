package com.example.a39722.imageloader.framework.Impl;

import com.example.a39722.imageloader.framework.BInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 39722 on 2017/1/4.
 */
public class BitmapInputStream implements BInputStream {

    @Override
    public InputStream getFromNetwork(String path) {
        URL url;
        InputStream inputStream = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            inputStream = conn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    @Override
    public InputStream getFromLocal(String path) {
        return null;
    }
}
