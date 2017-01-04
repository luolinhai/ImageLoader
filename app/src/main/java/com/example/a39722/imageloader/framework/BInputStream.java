package com.example.a39722.imageloader.framework;

import java.io.InputStream;

/**
 * Created by 39722 on 2017/1/4.
 */
public interface BInputStream {
    public java.io.InputStream getFromNetwork(String path);
    public java.io.InputStream getFromLocal(String path);
}
