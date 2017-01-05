package com.example.a39722.imageloader.framework.Impl;

import android.graphics.Bitmap;

import com.example.a39722.imageloader.framework.DiskCache;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by 39722 on 2017/1/4.
 */
public class CacheInDisk implements DiskCache {
    @Override
    public Bitmap getBitmap(int id) {
        return null;
    }

    @Override
    public void cacheBitmap(BitmapIO files,String file,Bitmap bitmap) {
        FileOutputStream out = null;
        try {
            out = (FileOutputStream) files.writeFile(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
