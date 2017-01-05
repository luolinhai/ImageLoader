package com.example.a39722.imageloader.framework;

import java.io.IOException;

/**
 * Created by 39722 on 2017/1/3.
 */
public interface Loader<T> {
    public T loadFromNet(String path);
    public T loadFromDisk(String file) throws IOException;
}
