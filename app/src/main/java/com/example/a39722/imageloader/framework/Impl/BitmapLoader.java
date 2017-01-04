package com.example.a39722.imageloader.framework.Impl;

import android.graphics.Bitmap;

import com.example.a39722.imageloader.framework.Loader;

import java.io.IOException;

/**
 * Created by 39722 on 2017/1/4.
 */
public class BitmapLoader implements Loader<Bitmap> {
    BitmapIO bitmapInputStream;
    ImageDecoder imageDecoder;
    public BitmapLoader(BitmapIO bitmapInputStream, ImageDecoder imageDecoder){
        this.bitmapInputStream = bitmapInputStream;
        this.imageDecoder = imageDecoder;
    }
    @Override
    public Bitmap loadFromNet(String path) {
        return imageDecoder.decoder(bitmapInputStream.readNetwork(path));
    }

    @Override
    public Bitmap loadFromDisk(String file) throws IOException {
        return imageDecoder.decoder(bitmapInputStream.readFile(file));
    }
}
