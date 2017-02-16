package com.example.a39722.imageloader.framework.CacheState;

import android.graphics.Bitmap;

import com.example.a39722.imageloader.framework.AsyncSetBitmap;

/**
 * Created by 39722 on 2017/1/7.
 */
public class NullState extends GetBitmapState {
    public NullState(AsyncSetBitmap task, GetBitmapState nextState, GetBitmapState backState) {
        super(task, nextState, backState);
    }

    @Override
    public Bitmap getBitmap(String key) {
        task.flag = true;
        return null;
    }
}
