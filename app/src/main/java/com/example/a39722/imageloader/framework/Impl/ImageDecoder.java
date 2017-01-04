package com.example.a39722.imageloader.framework.Impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.a39722.imageloader.framework.Decoder;

import java.io.InputStream;

/**
 * Created by 39722 on 2017/1/4.
 */
public class ImageDecoder implements Decoder<Bitmap> {
    @Override
    public Bitmap decoder(InputStream is) {
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }
}
