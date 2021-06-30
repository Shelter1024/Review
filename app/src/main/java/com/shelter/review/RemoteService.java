package com.shelter.review;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @author: Shelter
 * Create time: 2021/6/28, 17:05.
 */
public class RemoteService extends Service {
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<OnBookArrivedListener> callbackList = new RemoteCallbackList<>();
    private AtomicBoolean serviceDestroyed = new AtomicBoolean();

    IBookManager.Stub stub = new IBookManager.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.d("Shelter", "RemoteService addBook() bookId = " + book.getBookId() + ", bookName = " + book.getBookName());
            bookList.add(book);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.d("Shelter", "RemoteService getBookList()");
            return bookList;
        }

        @Override
        public void registerOnBookArrivedListener(OnBookArrivedListener onBookArrivedListener) throws RemoteException {
            Log.d("Shelter", "RemoteService registerOnBookArrivedListener() onBookArrivedListener = " + onBookArrivedListener);
            callbackList.register(onBookArrivedListener);
        }

        @Override
        public void unregisterOnBookArrivedListener(OnBookArrivedListener onBookArrivedListener) throws RemoteException {
            Log.d("Shelter", "RemoteService unregisterOnBookArrivedListener() onBookArrivedListener = " + onBookArrivedListener);
            callbackList.unregister(onBookArrivedListener);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Shelter", "RemoteService onCreate()");
        new Thread(new ServiceWorker()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceDestroyed.set(true);
    }

    private class ServiceWorker implements Runnable {
        int index = 0;

        @Override
        public void run() {
            while (!serviceDestroyed.get()) {
                Book book = new Book(index, "name: " + index);
                bookList.add(book);
                index++;
                int count = callbackList.beginBroadcast();
                for (int i = 0; i < count; i++) {
                    OnBookArrivedListener broadcastItem = callbackList.getBroadcastItem(i);
                    try {
                        broadcastItem.onBookArrived(book);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                callbackList.finishBroadcast();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}