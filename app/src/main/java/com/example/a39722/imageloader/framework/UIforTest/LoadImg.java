package com.example.a39722.imageloader.framework.UIforTest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.widget.ImageView;

import com.example.a39722.imageloader.R;
import com.example.a39722.imageloader.framework.Impl.BitmapInputStream;
import com.example.a39722.imageloader.framework.Impl.BitmapLoader;
import com.example.a39722.imageloader.framework.Impl.ImageDecoder;

/**
 * Created by 39722 on 2017/1/3.
 */
public class LoadImg extends Activity {
    String url = "https://www.nasa.gov/sites/default/files/thumbnails/image/iss050e017076.jpg";
    public static Handler handler;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadimg);
        imageView = (ImageView) findViewById(R.id.img_test);
        handler = new Handler();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                setImage();
            }
        });
        th.start();
    }

    public void setImage(){
        BitmapInputStream bis = new BitmapInputStream();
        ImageDecoder imageDecoder = new ImageDecoder();
        BitmapLoader bl = new BitmapLoader(bis,imageDecoder);
        final Bitmap bitmap = bl.load(url);
        handler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
