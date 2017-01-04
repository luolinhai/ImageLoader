package com.example.a39722.imageloader.framework;

import android.graphics.Bitmap;

import com.example.a39722.imageloader.framework.Impl.BitmapIO;

/**
 * Created by 39722 on 2017/1/3.
 */
public interface DiskCache {
    public Bitmap getBitmap(int id);
    public void cacheBitmap(BitmapIO files,String file,Bitmap bitmap);
}
