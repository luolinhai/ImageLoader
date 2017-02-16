package com.example.a39722.imageloader.framework;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.a39722.imageloader.R;
import com.example.a39722.imageloader.framework.CacheState.CheckInDisk;
import com.example.a39722.imageloader.framework.CacheState.CheckInMemory;
import com.example.a39722.imageloader.framework.CacheState.CheckInNet;
import com.example.a39722.imageloader.framework.CacheState.GetBitmapState;

/**
 * Created by 39722 on 2017/1/6.
 */
public abstract class AsyncSetBitmap {
    Async myTask;
    GetBitmapState state;
    public Context context;
    public boolean flag = false;
    public static boolean isStop;
    private String path;
    private ImageView imageView;
    public AsyncSetBitmap(Context context) {
        this.context = context;
        state = new CheckInMemory(this,null,null);
        GetBitmapState initNext = new CheckInDisk(this,null,state);
        GetBitmapState netState = new CheckInNet(this,null,initNext);
        initNext.setNextState(netState);
        state.setNextState(initNext);
        myTask = new Async();
    }

    /*public void resetState(){
        state = new CheckInMemory(this);
    }*/
    //外部拿图片用这个方法
    public void setBitmap(String path,ImageView imageView){
        myTask.execute(path,imageView);
    }

    public abstract Bitmap getImage(String path);

    public void setState(GetBitmapState state) {
        this.state = state;
    }

    class Async extends AsyncTask<Object,String,Bitmap>{
        @Override
        protected Bitmap doInBackground(Object... params) {
            path = (String)params[0];
            imageView = (ImageView)params[1];
            publishProgress();
            Bitmap bitmap = getImage(path);
            return bitmap;
        }

        @Override
        protected void onProgressUpdate(String... values) {

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            String bindPath = (String) imageView.getTag();
            if(result!=null&&bindPath.equals(path)){
                imageView.setImageBitmap(result);
                System.out.println("我拿到图片了");
            }else {
                System.out.println("读取错误");
            }
        }
    }
}
