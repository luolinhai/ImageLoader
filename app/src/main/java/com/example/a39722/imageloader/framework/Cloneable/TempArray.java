package com.example.a39722.imageloader.framework.Cloneable;

/**
 * Created by 39722 on 2017/1/6.
 */
public class TempArray<T> implements Cloneable {
    public T[] temp;
    public TempArray(T[] temp){
        this.temp = temp;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        Object o = null;
        try{
            o = (TempArray)super.clone();
            }catch(CloneNotSupportedException e){
                e.printStackTrace();
            }
        return o;

    }
}
