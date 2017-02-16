package com.example.a39722.imageloader.framework.UIforTest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a39722.imageloader.R;
import com.example.a39722.imageloader.framework.AsyncSetBitmap;
import com.example.a39722.imageloader.framework.AsyncTest;
import com.example.a39722.imageloader.framework.Math.BitmapCacheChain;
import com.example.a39722.imageloader.framework.Math.CacheNode;
import com.example.a39722.imageloader.framework.Math.Node;
import com.example.a39722.imageloader.framework.Math.Pool;

import java.io.File;

/**
 * Created by 39722 on 2017/1/6.
 */
public class ImageListTest extends Activity {
    ListView listView;
    BitmapCacheChain cache;
    MyAdapter myAdapter;
    String[] urls = {
            "https://www.nasa.gov/sites/default/files/thumbnails/image/potw1652a.jpg",
            "https://www.nasa.gov/sites/default/files/thumbnails/image/pia20513_0.jpg"
    };
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_image);
        cache = BitmapCacheChain.getInstance();
        Pool.PoolObjectFactory<Node> factory = new Pool.PoolObjectFactory<Node>() {
            @Override
            public CacheNode createObject() {
                return new CacheNode() ;
            }
        };
        Pool<Node> pool = new Pool<Node>(factory,20);
        long maxCache = (Runtime.getRuntime().maxMemory())/4;
        System.out.println("最大缓存为："+maxCache);
        cache.init(pool,maxCache);
        listView = (ListView) findViewById(R.id.imagelist);
        myAdapter = new MyAdapter(this);
        listView.setAdapter(myAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            boolean isUpdate = false;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch(scrollState){
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        AsyncSetBitmap.isStop = false;
                        if(isUpdate){
                            myAdapter.notifyDataSetChanged();
                            isUpdate = false;
                            myAdapter.isSrolling = false;
                        }
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        AsyncSetBitmap.isStop = true;
                        isUpdate = true;
                        myAdapter.isSrolling = true;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                System.out.println("第一个见的是第 "+firstVisibleItem+"item");
                System.out.println("可见item数为： "+visibleItemCount);
                System.out.println("总共item数为: "+totalItemCount);
            }
        });
    }

    class MyAdapter extends BaseAdapter{
        Context context;
        public boolean isSrolling = false;
        public MyAdapter(Context context){
            this.context = context;
        }

        @Override
        public int getCount() {
            return urls.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item,null);
                holder.image = (ImageView)convertView.findViewById(R.id.list_image_item);
                holder.text = (TextView)convertView.findViewById(R.id.description) ;
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.image.setImageResource(R.drawable.default_pic);
            if(!isSrolling){
                holder.image.setTag(urls[position]);
                new AsyncTest(context).setBitmap(urls[position],holder.image);
            }
            return convertView;
        }

        private final class ViewHolder{
            ImageView image;
            TextView text;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO 这个是暂时的；
        /*String dir = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator+"testcache"+File.separator;
        boolean success = deleteDir(new File(dir));
        if (success) {
            System.out.println("Successfully deleted populated directory: " + dir);
        } else {
            System.out.println("Failed to delete populated directory: " + dir);
        }*/
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

}
