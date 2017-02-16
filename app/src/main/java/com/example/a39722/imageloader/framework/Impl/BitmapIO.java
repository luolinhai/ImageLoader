package com.example.a39722.imageloader.framework.Impl;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;

import com.example.a39722.imageloader.framework.FileIO;
import com.example.a39722.imageloader.framework.NetIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 39722 on 2017/1/4.
 */
public class BitmapIO implements NetIO,FileIO {
    AssetManager assets;
    String externalStoragePath;

    public BitmapIO(Context context){
        this.assets = context.getAssets();
        this.externalStoragePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
    }

    @Override
    public InputStream readNetwork(String path) {
        URL url;
        //InputStream inputStream = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            /*conn.setConnectTimeout(5*1000);
            conn.setRequestMethod("GET");
            if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                return conn.getInputStream();
            }*/
            return conn.getInputStream();
            //inputStream = conn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public InputStream readAsset(String fileName) throws IOException {
        return assets.open(fileName);
    }

    @Override
    public InputStream readFile(String fileName) throws IOException {
        System.out.println(externalStoragePath + fileName);
        return new FileInputStream(externalStoragePath + fileName);
    }

    @Override
    public OutputStream writeFile(String fileName) throws IOException {
        return new FileOutputStream(externalStoragePath + fileName);
    }
    //String格式，最后不要带/
    public void setCustomizedPath (String customizedPath){
        externalStoragePath = externalStoragePath+customizedPath+File.separator;
    }

}
