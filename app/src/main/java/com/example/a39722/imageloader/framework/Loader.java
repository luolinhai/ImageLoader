package com.example.a39722.imageloader.framework;

import android.graphics.Bitmap;

import java.io.InputStream;

/**
 * Created by 39722 on 2017/1/3.
 */
public interface Loader<T> {
    public T load(String path);
}
