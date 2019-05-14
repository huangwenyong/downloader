package com.hwy.downloader.code.core;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class Container {
    private final ConcurrentMap<Integer, Chapter> container;
    private final Lock lock;
    private final Condition condition;
    private int waiteIndex = -1;
    private int capacity;
    public Container(int capacity){
        this.capacity = capacity;
        this.container = new ConcurrentHashMap<>(capacity);
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
    }

    public  Chapter get(int index) throws InterruptedException {
        lock.lock();
        try {
            while (container.get(index) == null) {
                waiteIndex = index;
                condition.await();
            }
            return container.get(index);
        }finally {
            lock.unlock();
        }

    }

    public void put(int index, Chapter chapter){
        lock.lock();
        try {
            container.put(index, chapter);
            if(index == waiteIndex){
                condition.signalAll();
            }
        }finally {
            lock.unlock();
        }

    }

    public void remove(int index){
        container.remove(index);
    }

    public int size(){
        return  container.size();
    }

    public int capacity(){
        return this.capacity;
    }
}
