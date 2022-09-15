package com.shelter.javalib;

public class Test {

    public static void main(String[] args) {
        testThreadAlternantExe2();
    }

    static int count = 0;
    static final Object lock = new Object();
    //面试题：三个线程交替执行
    private static void testThreadAlternantExe2() {

        Thread thread1 = new Thread(){
            @Override
            public void run() {
                while (count <= 100) {
                    synchronized (lock) {
                        if (count % 3 == 0) {
                            System.out.println("thread: " + Thread.currentThread().getName() + ", count = " + count);
                            count++;
                        }
                        try {
                            lock.notifyAll();
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        Thread thread2 = new Thread(){
            @Override
            public void run() {
                while (count <= 100) {
                    synchronized (lock) {
                        if (count % 3 == 1) {
                            System.out.println("thread: " + Thread.currentThread().getName() + ", count = " + count);
                            count++;
                        }
                        try {
                            lock.notifyAll();
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        Thread thread3 = new Thread(){
            @Override
            public void run() {
                while (count <= 100) {
                    synchronized (lock) {
                        if (count % 3 == 2) {
                            System.out.println("thread: " + Thread.currentThread().getName() + ", count = " + count);
                            count++;
                        }
                        try {
                            lock.notifyAll();
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        thread1.start();
        thread2.start();
        thread3.start();
    }
}