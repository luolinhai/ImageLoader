package com.example.a39722.imageloader.framework.Impl;

import android.graphics.Bitmap;

import com.example.a39722.imageloader.framework.AsyncSetBitmap;
import com.example.a39722.imageloader.framework.DiskCache;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 39722 on 2017/1/4.
 */
public class CacheInDisk implements DiskCache {
    @Override
    public Bitmap getBitmap(int id) {
        return null;
    }

    public boolean download(BitmapIO files,String dir,String path,String key) {
        try {
            InputStream is = files.readNetwork(path);
            byte[] b = new byte[128];
            //拿到临时文件的输出流
            /*File f = new File(file);
            f.createNewFile(key);*/
            int len = 0;
            int l = 0;
            RandomAccessFile raf = new RandomAccessFile(dir+key, "rwd");
            raf.setLength(1024*2);
            while((l = is.read(b)) != -1){
                if(AsyncSetBitmap.isStop) {
                    return false;
                }
                raf.write(b, 0, l);
                len +=l;
            }
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean cacheBitmap(BitmapIO files,String file,Bitmap bitmap) {
        FileOutputStream out = null;
        try {
            out = (FileOutputStream) files.writeFile(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return true;
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
        return false;
    }
}
