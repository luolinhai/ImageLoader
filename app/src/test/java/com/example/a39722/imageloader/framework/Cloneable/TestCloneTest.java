package com.example.a39722.imageloader.framework.Cloneable;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by 39722 on 2017/1/7.
 */
public class TestCloneTest {
    TestNoCloneable noCloneable = new TestNoCloneable();
    TestClone clone = new TestClone();
    @Test
    public void testCassertlone(){
        noCloneable.i = 2;
        clone.i =2;
        TestNoCloneable noCloneable1 = noCloneable;
        TestClone clone1;
        try {
            clone1 = (TestClone) clone.clone();
            clone1.i = 1;
            noCloneable1.i =1;
            assert (noCloneable1.i==1);
            assert (clone.i!=1);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}