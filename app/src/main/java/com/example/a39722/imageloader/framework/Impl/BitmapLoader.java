package com.example.a39722.imageloader.framework.Impl;

import android.graphics.Bitmap;

import com.example.a39722.imageloader.framework.Loader;

/**
 * Created by 39722 on 2017/1/4.
 */
public class BitmapLoader implements Loader<Bitmap> {
    BitmapInputStream bitmapInputStream;
    ImageDecoder imageDecoder;
    public BitmapLoader(BitmapInputStream bitmapInputStream,ImageDecoder imageDecoder){
        this.bitmapInputStream = bitmapInputStream;
        this.imageDecoder = imageDecoder;
    }
    @Override
    public Bitmap load(String path) {
        return imageDecoder.decoder(bitmapInputStream.getFromNetwork(path));
    }
}
