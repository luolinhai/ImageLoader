package com.example.a39722.imageloader.framework.CacheState;

import android.graphics.Bitmap;

import com.example.a39722.imageloader.framework.AsyncSetBitmap;

/**
 * Created by 39722 on 2017/1/7.
 */
public class StopLoading extends GetBitmapState {
    public StopLoading(AsyncSetBitmap task,GetBitmapState nextState,GetBitmapState backState) {
        super(task,nextState,backState);
    }

    @Override
    public Bitmap getBitmap(String key) {
        GetBitmapState memory = new CheckInMemory(task,null,null);
        GetBitmapState local = new CheckLocalOnly(task,null,memory);
        memory.setNextState(local);
        task.setState(memory);
        return null;
    }
}
