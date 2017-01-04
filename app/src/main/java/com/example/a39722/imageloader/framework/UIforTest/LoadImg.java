package com.example.a39722.imageloader.framework.UIforTest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.a39722.imageloader.R;
import com.example.a39722.imageloader.framework.Impl.BitmapIO;
import com.example.a39722.imageloader.framework.Impl.BitmapLoader;
import com.example.a39722.imageloader.framework.Impl.CacheInDisk;
import com.example.a39722.imageloader.framework.Impl.ImageDecoder;

import java.io.IOException;

/**
 * Created by 39722 on 2017/1/3.
 */
public class LoadImg extends Activity {
    String url = "https://www.nasa.gov/sites/default/files/thumbnails/image/iss050e017076.jpg";
    public static Handler handler;
    ImageView imageView;
    ImageView readImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadimg);
        imageView = (ImageView) findViewById(R.id.img_test);
        readImage = (ImageView)findViewById(R.id.read_img);
        handler = new Handler();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    setImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        th.start();
    }

    public void setImage() throws IOException {
        BitmapIO bis = new BitmapIO(this);
        ImageDecoder imageDecoder = new ImageDecoder();
        BitmapLoader bl = new BitmapLoader(bis,imageDecoder);
        //final Bitmap bitmap = bl.loadFromNet(url);
        //CacheInDisk cacheInDisk = new CacheInDisk();
        //cacheInDisk.cacheBitmap(bis,"111",bitmap);
        final Bitmap readbmp = bl.loadFromDisk("111");
        handler.post(new Runnable() {
            @Override
            public void run() {
                //imageView.setImageBitmap(bitmap);
                readImage.setImageBitmap(readbmp);
            }
        });
    }
}
