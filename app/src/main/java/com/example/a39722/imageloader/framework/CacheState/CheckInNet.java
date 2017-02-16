package com.example.a39722.imageloader.framework.CacheState;

import android.graphics.Bitmap;
import android.os.Environment;

import com.example.a39722.imageloader.framework.AsyncSetBitmap;
import com.example.a39722.imageloader.framework.DiskLruCache;
import com.example.a39722.imageloader.framework.Impl.BitmapIO;
import com.example.a39722.imageloader.framework.Impl.BitmapLoader;
import com.example.a39722.imageloader.framework.Impl.CacheInDisk;
import com.example.a39722.imageloader.framework.Impl.ImageDecoder;
import com.example.a39722.imageloader.framework.Math.CacheNode;
import com.example.a39722.imageloader.framework.utils.Cache;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by 39722 on 2017/1/6.
 */
public class CheckInNet extends GetBitmapState {
    BitmapIO bis;
    ImageDecoder imageDecoder;
    BitmapLoader bl;
    DiskLruCache diskLruCache;
    String dir;
    public CheckInNet(AsyncSetBitmap task,GetBitmapState nextState,GetBitmapState backState) {
        super(task,nextState,backState);
        bis = new BitmapIO(task.context);
        imageDecoder = new ImageDecoder();
        bl = new BitmapLoader(bis,imageDecoder);
        /*try {
            diskLruCache = DiskLruCache.open(Cache.getDiskCacheDir(task.context,"testcache"),Cache.getAppVersion(task.context),1,20*1024*1024);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try{
            dir = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator+"testcache"+File.separator;
            File cacheDir = new File(dir);
            if(!cacheDir.exists()){
                cacheDir.mkdirs();
            }
            diskLruCache = DiskLruCache.open(cacheDir,Cache.getAppVersion(task.context),1,600*1024*1024);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public Bitmap getBitmap(String path) {
            System.out.println("写入硬盘缓存......");
            CacheInDisk cacheInDisk = new CacheInDisk();
            String key = Cache.genHashKey(path);
            DiskLruCache.Editor editor =null;
            try {
                editor = diskLruCache.edit(key);
                if(editor!=null){
                    OutputStream outputStream = editor.newOutputStream(0);
                    if(cacheInDisk.download(bis,dir,path,key)){
                        editor.commit();
                        System.out.println("从网络上读取");
                    }else{
                        editor.abort();
                        task.flag = true;
                        System.out.println("网络错误");
                    }
                }
                diskLruCache.flush();
                task.setState(backState);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }
}
