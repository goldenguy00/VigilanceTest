package com.example.wilsongolden.vigilancetest;

/**
 * Created by wilsongolden on 11/9/16.
 */
public class MyThread extends Thread {
    private volatile Thread loop;

    public void endThread() {
        loop.interrupt();
        loop = null;
    }

    public void run() {
        int count = 0;
        loop = Thread.currentThread();
        Thread thisThread = Thread.currentThread();
        while (loop == thisThread) {
            try {
                thisThread.sleep(1000);
            } catch (InterruptedException e){
            }
            System.out.println("loop");
            count++;
        }
    }
}
