package com.example.a39722.imageloader.framework.Math;

import android.graphics.Bitmap;

import com.example.a39722.imageloader.framework.Cloneable.TempArray;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by 39722 on 2017/1/5.
 * 内存缓存图片的地方
 */
@SuppressWarnings("rawtypes")
/**
 * T指的是储存的主要数据类型
 *
 * */
public class BitmapCacheChain implements Chain{

    private static BitmapCacheChain singleton;
    LinkedHashMap map = null;
    CacheNode[] nodes;
    Pool<Node> pool;
    ArrayList nodeBuffer;
    long maxCache;
    long size;
    public long getSize(){
    	return size;
    }
    /*public BitmapCacheChain(Pool<Node> pool,int maxCache){
        this.maxCache = maxCache;
        nodeBuffer = new ArrayList<>();
        this.pool = pool;
    }*/
    private BitmapCacheChain(){
    }

    public static BitmapCacheChain getInstance(){
        if(singleton==null){
            singleton = new BitmapCacheChain();
        }
        return singleton;
    }

    public void init(Pool<Node> pool,long maxCache){
        this.maxCache = maxCache;
        nodeBuffer = new ArrayList<>();
        this.pool = pool;
    }

    @Override
    public void rebuild() {
        //TODO 还没想到做什么
    }

    @SuppressWarnings({ "unchecked" })
	@Override
    public void addNode(String key, Object value,long size) {
        synchronized (this){
            Node node = pool.newObject();
            ((CacheNode)node).key = key;
            ((CacheNode)node).value = (Bitmap)value;
            ((CacheNode)node).size = size;
            if((this.size+size)<=maxCache){
                this.size +=size;
                nodeBuffer.add(node);
                System.out.println("内存里buffer加一个");
            }else{
            	trim(size);
                int len = nodeBuffer.size();
                if(len>0){
                    while (this.size+size>maxCache){
                    	this.size -= ((CacheNode)nodeBuffer.get(0)).size;
                        nodeBuffer.remove(0);
                        System.out.println("内存里buffer移除一个");                       
                        if(nodeBuffer.isEmpty()){
                        	System.out.println("内存里buffer没有了");
                        	break;
                        }
                    }
                }
                if(this.size+size<=maxCache){
                    nodeBuffer.add(node);
                    this.size +=size;
                    System.out.println("内存里buffer加一个");
                }else{
                	throw new OutOfMemoryError();
                    /*trim(size);
                    nodeBuffer.add(node);
                    this.size +=size;
                    System.out.println("内存里buffer加一个");*/
                }

            }
        }
    }

    @Override
    public void trim(long newSize) {
        int count = 0;
        int len = 0;
        if(nodes!=null&&nodes.length>0){
        	len = nodes.length;
        	int j = 0;
            while(this.size+newSize>maxCache){
                this.size -= nodes[j].size;
                System.out.println("这个可以从内存里移除");
                count++;
                if(j+1<len-1){
                    j++;
                }else{
                    System.out.println("内存里没有了");
                    break;
                }
            }
            CacheNode[] temp = new CacheNode[len-count];
            System.arraycopy(nodes,count,temp,0,len-count);
            for(int i = 0;i<count;i++){
                nodes[i].value=null;
                pool.free(nodes[i]);
            }
            nodes = new CacheNode[temp.length];
            System.arraycopy(temp, 0, nodes, 0, temp.length);
            System.out.println("完成修剪");
        }
    }

    @SuppressWarnings({ "unchecked" })
	public CacheNode[] getCacheNodes(){
        CacheNode[] temp = null;
        synchronized (this){
            int len = nodeBuffer.size();
            temp = null;
            int originalLen = 0;
            String temPreKey = null;
            if(nodes!=null&&nodes.length>0){
                originalLen = nodes.length;
                temp = new CacheNode[originalLen+len];
                System.arraycopy(nodes,0,temp,0,originalLen);
                temPreKey = nodes[originalLen-1].getKey();
            }else{
                temp = new CacheNode[len];
            }
            temp = setNodes(temp,nodeBuffer,temPreKey,originalLen);
        }
           /* for(int i = 0;i<len;i++){
                *//*Object node1 = nodeBuffer.get(i);
                Object node2 = nodeBuffer.get(i+1);
                temp[originalLen+i] = (CacheNode)node1;
                temp[originalLen+i].preKey = temPreKey;
                temp[originalLen+i].nextKey = ((CacheNode)node2).getKey();
                temp[originalLen+i+1] = (CacheNode)node2;
                temp[originalLen+i+1].preKey = ((CacheNode)node1).getKey();
                temp[originalLen+i+1].nextKey = null;
                temPreKey = ((CacheNode)node1).getKey();*//*
                //((CacheNode)nodeBuffer.get(i)).value = null;
                pool.free((Node)nodeBuffer.get(i));
            }*/
            nodeBuffer.clear();
            nodes = new CacheNode[temp.length];
            System.arraycopy(temp, 0, nodes, 0, temp.length);
            /*nodes = null;
            nodes = temp.clone();*/
            return nodes;
    }
    
    @SuppressWarnings("unchecked")
	public CacheNode[] setNodes(
			CacheNode[] temp,
			ArrayList nodeBuffer,
			String temPreKey,
    		int originalLen){
    	int len = nodeBuffer.size();
        if(len>1){
            for(int i = 0;i<len-1;i++){
                Object node1 = nodeBuffer.get(i);
                Object node2 = nodeBuffer.get(i+1);
                temp[originalLen+i] = (CacheNode)node1;
                temp[originalLen+i].preKey = temPreKey;
                temp[originalLen+i].nextKey = ((CacheNode)node2).getKey();
                temp[originalLen+i+1] = (CacheNode)node2;
                temp[originalLen+i+1].preKey = ((CacheNode)node1).getKey();
                temp[originalLen+i+1].nextKey = null;
                temPreKey = ((CacheNode)node1).getKey();
            }
        }else if(len==1){
            Object node1 = nodeBuffer.get(0);
            temp[originalLen] = (CacheNode)node1;
            temp[originalLen].preKey = temPreKey;
            temp[originalLen].nextKey = null;

    	}    	
    	return temp;
    }

    @Override
    public void removeNode() {

    }

    @SuppressWarnings("unchecked")
	public void pushToTop(int pos){
        int len = nodes.length;
        if(pos-1>=0){
            if(pos+1<=len-1){
                nodes[pos-1].nextKey = nodes[pos+1].getKey();
                nodes[pos+1].preKey = nodes[pos-1].getKey();
                nodes[len-1].nextKey = nodes[pos].getKey();
                nodes[pos].nextKey=null;
                nodes[pos].preKey = nodes[len-1].getKey();
            }else{
                return;
            }
        }else{
            if(pos+1<=len-1){
                nodes[pos+1].preKey = null;
                nodes[len-1].nextKey = nodes[pos].getKey();
                nodes[pos].nextKey=null;
                nodes[pos].preKey = nodes[len-1].getKey();
            }else{
                return;
            }
        }
        CacheNode[] temp = new CacheNode[len];
        System.arraycopy(nodes,0,temp,0,pos);
        if(pos+1<=len-1){
            System.arraycopy(nodes,pos+1,temp,pos,len-pos-1);
        }
        temp[len-1] = nodes[pos];
        nodes = new CacheNode[temp.length];
        System.arraycopy(temp, 0, nodes, 0, temp.length);
        System.out.println("最后一个元素的KEY是："+nodes[len-1].key);
    }

    @SuppressWarnings("unchecked")
	@Override
    public Bitmap findCache(String key){
        getCacheNodes();
            if(nodes!=null){
                int len = nodes.length;
                for(int i = 0;i<len;i++){
                    if(key.equals(nodes[i].key)){
                        pushToTop(i);
                        return (Bitmap)nodes[len-1].value;
                    }
                }
            }/*else if(nodeBuffer.size()>0){
                int len = nodeBuffer.size();
                for(int i = 0;i<len;i++){
                    if(key.equals(((CacheNode)nodeBuffer.get(i)).key)){
                        return (Bitmap) ((CacheNode)nodeBuffer.get(i)).value;
                    }
                }
                getCacheNodes();
            }*/
        return null;
    }
}
