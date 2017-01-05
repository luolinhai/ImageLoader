package com.example.a39722.imageloader.framework.Math;

/**
 * Created by 39722 on 2017/1/5.
 */
public interface Chain<T> {
    public void rebuild();
    public void addNode(String key, Object value, int size);
    public void removeNode();
    public T findCache(String key);
    public void trim(int newSize);
}
