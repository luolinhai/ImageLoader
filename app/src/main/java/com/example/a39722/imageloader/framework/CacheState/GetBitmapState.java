package com.example.a39722.imageloader.framework.CacheState;

import android.graphics.Bitmap;

import com.example.a39722.imageloader.framework.AsyncSetBitmap;
import com.example.a39722.imageloader.framework.Math.BitmapCacheChain;

/**
 * Created by 39722 on 2017/1/6.
 */
public abstract class GetBitmapState {
    AsyncSetBitmap task;
    BitmapCacheChain cache;
    GetBitmapState nextState;
    GetBitmapState backState;

    public void setBackState(GetBitmapState backState) {
        this.backState = backState;
    }

    public GetBitmapState(AsyncSetBitmap task, GetBitmapState nextState,GetBitmapState backState) {
        this.task = task;
        this.nextState = nextState;
        this.backState = backState;
        cache = BitmapCacheChain.getInstance();
    }
    public abstract Bitmap getBitmap(String key);

    public void setNextState(GetBitmapState nextState) {
        this.nextState = nextState;
    }
}
