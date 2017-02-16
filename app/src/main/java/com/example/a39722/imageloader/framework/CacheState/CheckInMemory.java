package com.example.a39722.imageloader.framework.CacheState;

import android.graphics.Bitmap;

import com.example.a39722.imageloader.framework.AsyncSetBitmap;
import com.example.a39722.imageloader.framework.Math.BitmapCacheChain;
import com.example.a39722.imageloader.framework.utils.Cache;

/**
 * Created by 39722 on 2017/1/6.
 */
public class CheckInMemory extends GetBitmapState {

    public CheckInMemory(AsyncSetBitmap task, GetBitmapState nextState, GetBitmapState backState) {
        super(task, nextState, backState);
    }

    @Override
    public Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        String key = Cache.genHashKey(path);
        bitmap = cache.findCache(key);
        if(bitmap==null){
            //TODO 以后这里会先找硬盘
            System.out.println("内存里没有");
            task.setState(nextState);
        }else{
            System.out.print("从内存读取");
        }
        return bitmap;
    }
}
