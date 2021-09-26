package com.shelter.review;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private IBookManager bookManager;
    private OnBookArrivedListener onBookArrivedListener = new OnBookArrivedListener.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void onBookArrived(Book book) throws RemoteException {
            Log.d("Shelter", "MainActivity onBookArrived() bookId = " + book.getBookId() + ", bookName = " + book.getBookName());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
//        startService();
        List<? extends User> list = new ArrayList<VipUser>();
        List<Number> integers = new ArrayList<>();
        integers.add(1);
        List<Number> numbers = map3(integers, new Mapper<Number, Integer>() {
            @Override
            public Integer map(Number integer) {
                return null;
            }
        });
    }

    private static <T,R> List<R> map3(List<T> list, Mapper<? super T,? extends R> mapper) {
        List<R> l = new ArrayList<R>();
        for (T t : list) {
            R r = mapper.map(t);
            l.add(r);
        }
        return l;
    }


    private static interface Mapper<T,R> {
        R map(T t);
    }

    private void copyAll(List<? super VipUser> dest) {
        dest.add(new SuperVipUser());
        dest.add(new VipUser());
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("Shelter", "MainActivity onServiceConnected() service = " +service);
            bookManager = IBookManager.Stub.asInterface(service);
            try {
                Log.d("Shelter", "MainActivity onServiceConnected() onBookArrivedListener = " + onBookArrivedListener);
                bookManager.registerOnBookArrivedListener(onBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("Shelter", "MainActivity onServiceDisconnected()");
            try {
                bookManager.unregisterOnBookArrivedListener(onBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    private void startService() {
        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName("com.palmtop.app2", "com.palmtop.app2.MainActivity");
                intent.setComponent(cn);
                startActivity(intent);

//                try {
//                    bookManager.addBook(new Book(1, "Android开发艺术探索"));
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
                break;
            case R.id.btn2:
                try {
                    List<Book> bookList = bookManager.getBookList();
                    for (Book book : bookList) {
                        Log.d("Shelter", "MainActivity getBookList bookId = " + book.getBookId() + ", bookName = " + book.getBookName());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
