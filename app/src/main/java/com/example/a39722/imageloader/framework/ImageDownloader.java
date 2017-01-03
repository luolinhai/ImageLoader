package com.example.a39722.imageloader.framework;

import android.graphics.Bitmap;

/**
 * Created by 39722 on 2017/1/3.
 */
public interface ImageDownloader {
    public Bitmap downloadFromNetwork(String url);
    public Bitmap downloadFromLocal(String path);
}
