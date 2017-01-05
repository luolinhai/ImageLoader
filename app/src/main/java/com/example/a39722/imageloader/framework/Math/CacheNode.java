package com.example.a39722.imageloader.framework.Math;

/**
 * Created by 39722 on 2017/1/5.
 */
public class CacheNode<T> implements Node {
    public String preKey;
    public String nextKey;
    public String key;
    public Object value;
    public int size;
    

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public CacheNode(){

    }

    public CacheNode(String k,T v,int size){
    	key = k;
    	value = v;
    	this.size = size;
    }

    @Override
    public void join(Node preNode, Node nextNode) {
        /*if(next!=null){
            if(next.equals(preNode))
                next = preNode;
        }
        if(pre!=null){
            if(pre.equals(nextNode))
                pre = nextNode;
        }*/
    }

    @Override
    public boolean equals(Object obj) {
        return this.key==((CacheNode)obj).key;
    }
}
