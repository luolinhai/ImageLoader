package com.example.a39722.imageloader.framework;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.a39722.imageloader.framework.CacheState.CheckInMemory;
import com.example.a39722.imageloader.framework.CacheState.StopLoading;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by 39722 on 2017/1/6.
 */
public class AsyncTest extends AsyncSetBitmap implements Observer{
    public AsyncTest(Context context){
        super(context);
    }
    @Override
    public Bitmap getImage(String path) {
        Bitmap bitmap = null;
        while(true){
            if(AsyncSetBitmap.isStop){
               state = new StopLoading(this,null,null) ;
                System.out.println("暂停加载图片");
            }
            bitmap =state.getBitmap(path);
            if(bitmap!=null||flag)
                break;
        }
        return bitmap;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
