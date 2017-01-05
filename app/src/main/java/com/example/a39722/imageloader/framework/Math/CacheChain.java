package com.example.a39722.imageloader.framework.Math;

import java.util.ArrayList;

/**
 * Created by 39722 on 2017/1/5.
 */
@SuppressWarnings("rawtypes")
public class CacheChain<T> implements Chain{
    CacheNode[] nodes;
    Pool<Node> pool;
    ArrayList nodeBuffer;
    int maxCache;
    int size;
    public int getSize(){
    	return size;
    }
    public CacheChain(Pool<Node> pool,int maxCache){
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
    public void addNode(String key, Object value,int size) {
        synchronized (this){
            Node node = pool.newObject();
            ((CacheNode)node).key = key;
            ((CacheNode)node).value = (T)value;
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
    public void trim(int newSize) {
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
            nodes = new CacheNode[temp.length];
            System.arraycopy(temp, 0, nodes, 0, temp.length);
            System.out.println("完成修剪");
        }
    }

    @SuppressWarnings({ "unchecked" })
	public CacheNode[] getCacheNodes(){
        synchronized (this){
            int len = nodeBuffer.size();
            CacheNode[] temp = null;
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
            ArrayList tempBuffer = new ArrayList();
            tempBuffer.addAll(nodeBuffer);
            for(int i = 0;i<len;i++){
                /*Object node1 = nodeBuffer.get(i);
                Object node2 = nodeBuffer.get(i+1);
                temp[originalLen+i] = (CacheNode)node1;
                temp[originalLen+i].preKey = temPreKey;
                temp[originalLen+i].nextKey = ((CacheNode)node2).getKey();
                temp[originalLen+i+1] = (CacheNode)node2;
                temp[originalLen+i+1].preKey = ((CacheNode)node1).getKey();
                temp[originalLen+i+1].nextKey = null;
                temPreKey = ((CacheNode)node1).getKey();*/
                pool.free((Node)nodeBuffer.get(i));
            }
            temp = setNodes(temp,tempBuffer,temPreKey,originalLen);
            
            nodeBuffer.clear();
            nodes = new CacheNode[temp.length];
            System.arraycopy(temp, 0, nodes, 0, temp.length);
            return nodes;
        }
    }
    
    @SuppressWarnings("unchecked")
	public CacheNode[] setNodes(
			CacheNode[] temp,
			ArrayList nodeBuffer,
			String temPreKey,
    		int originalLen){
    	int len = nodeBuffer.size();
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
    public T findCache(String key) {
        synchronized (this){
            getCacheNodes();
            int len = nodes.length;
            for(int i = 0;i<len;i++){
                if(key.equals(nodes[i].key)){
                    pushToTop(i);
                    return (T)nodes[len-1].value;
                }
            }
        }
        return null;
    }
}
