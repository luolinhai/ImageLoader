package com.example.a39722.imageloader.framework.CacheState;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.a39722.imageloader.framework.AsyncSetBitmap;
import com.example.a39722.imageloader.framework.DiskLruCache;
import com.example.a39722.imageloader.framework.Impl.BitmapIO;
import com.example.a39722.imageloader.framework.Impl.BitmapLoader;
import com.example.a39722.imageloader.framework.Impl.ImageDecoder;
import com.example.a39722.imageloader.framework.utils.Cache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 39722 on 2017/1/7.
 */
public class CheckInDisk extends GetBitmapState {
    BitmapIO bis;
    ImageDecoder imageDecoder;
    BitmapLoader bl;
    DiskLruCache diskLruCache;
    public CheckInDisk(AsyncSetBitmap task,GetBitmapState nextState,GetBitmapState backState) {
        super(task,nextState,backState);
        bis = new BitmapIO(task.context);
        imageDecoder = new ImageDecoder();
        bl = new BitmapLoader(bis,imageDecoder);
        try{
            File cacheDir = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator+"testcache"+File.separator);
            if(!cacheDir.exists()){
                cacheDir.mkdirs();
            }
            diskLruCache = DiskLruCache.open(cacheDir, Cache.getAppVersion(task.context),1,600*1024*1024);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        try {

            System.out.print("从硬盘里读取图片中...");
            String key = Cache.genHashKey(path);
            /*DiskLruCache.Snapshot snapShot= diskLruCache.get(key);
            if(snapShot!=null){
                InputStream is = snapShot.getInputStream(0);
                bitmap = BitmapFactory.decodeStream(is);
            }*/
            bitmap = bl.loadFromDisk("testcache"+File.separator+key);
            if(bitmap!=null){
                long size = bitmap.getRowBytes()*bitmap.getHeight();
                System.out.println("图片的大小是： "+size);
                System.out.println("写入内存缓存......");
                cache.addNode(key,bitmap,size);
                task.setState(backState);
            }else{
                task.setState(nextState);
            }
        } catch (IOException e) {
            e.printStackTrace();
            task.setState(nextState);
        }
        return null;
    }
}
