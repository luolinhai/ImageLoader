package com.example.a39722.imageloader.framework.Observable;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by 39722 on 2017/1/8.
 */
public class TaskObservable extends Observable {
    public static TaskObservable singleton;
    private TaskObservable(){
    }

    public static TaskObservable getInstance(){
        if(singleton==null){
            singleton = new TaskObservable();
        }
        return singleton;
    }
}
