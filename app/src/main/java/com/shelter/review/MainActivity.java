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


import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.shelter.review.data.Response;
import com.shelter.review.retrofit.MyRetrofit;
import com.shelter.review.retrofit.WeatherService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;


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
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
//        startService();

//        testGeneric();

    }

    private void testGeneric() {
        List<? extends User> list = new ArrayList<VipUser>();
        List<Number> integers = new ArrayList<>();
        integers.add(1);
        List<Number> numbers = map3(integers, new Mapper<Number, Integer>() {
            @Override
            public Integer map(Number integer) {
                return null;
            }
        });

        Gson gson = new Gson();
        Data data = new Data("1", new Date().toString());
        Response<Data> response = new Response<>(data);
        String json = gson.toJson(response);
        Log.d("Shelter", "MainActivity json = " + json);

        Response<LinkedTreeMap> dataResponse = gson.fromJson(json, Response.class);
        /**
         * getClass会导致ClassCastException
         * Caused by: java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to com.shelter.review.MainActivity$Data
         * LINENUMBER 68 L14
         *     LDC "Shelter"
         *     NEW java/lang/StringBuilder
         *     DUP
         *     INVOKESPECIAL java/lang/StringBuilder.<init> ()V
         *     LDC "MainActivity dataResponse = "
         *     INVOKEVIRTUAL java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         *     ALOAD 9
         *     INVOKEVIRTUAL java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
         *     LDC ", data class = "
         *     INVOKEVIRTUAL java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         *     ALOAD 9
         *     INVOKEVIRTUAL com/shelter/review/data/Response.getData ()Ljava/lang/Object;
         *     CHECKCAST com/shelter/review/MainActivity$Data
         *     INVOKEVIRTUAL java/lang/Object.getClass ()Ljava/lang/Class;
         *     INVOKEVIRTUAL java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
         *     INVOKEVIRTUAL java/lang/StringBuilder.toString ()Ljava/lang/String;
         *     INVOKESTATIC android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
         */
        Log.d("Shelter", "MainActivity dataResponse = " + dataResponse + ", data class = " + dataResponse.getData().getClass());
        Type testType = new TypeReference<Response<Data>>(){}.getType();
        Log.d("Shelter", "MainActivity testType = " + testType);
        Type type = new TypeToken<Response<Data>>() {
        }.getType();
        Log.d("Shelter", "MainActivity type = " + type);
        Response<Data> dataResponse2 = gson.fromJson(json, type);
        Log.d("Shelter", "MainActivity dataResponse2 = " + dataResponse2);
    }

    private void testGet() {
        MyRetrofit myRetrofit = new MyRetrofit.Builder().baseUrl("https://restapi.amap.com").build();
        WeatherService weatherService = myRetrofit.create(WeatherService.class);
        Call call = weatherService.getWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Shelter", "MainActivity onFailure() error = " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Log.d("Shelter", "MainActivity onResponse() body = " + response.body().string());
            }
        });
    }

    private void testPost() {
        MyRetrofit myRetrofit = new MyRetrofit.Builder().baseUrl("https://restapi.amap.com").build();
        WeatherService weatherService = myRetrofit.create(WeatherService.class);
        Call call = weatherService.postWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Shelter", "MainActivity onFailure() error = " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Log.d("Shelter", "MainActivity onResponse() body = " + response.body().string());
            }
        });
    }

    static class TypeReference<T> {

        public TypeReference() {

        }

        public Type getType() {
            return getClass().getGenericSuperclass();
        }
    }


    static class Data {
        String id;
        String time;

        public Data(String id, String time) {
            this.id = id;
            this.time = time;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
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
//                startApp2Activity();

//                addBook();
                break;
            case R.id.btn2:
                getBookList();
                break;

            case R.id.btn3:
                testGet();
                break;
            case R.id.btn4:
                testPost();
                break;
            default:
                break;
        }
    }

    private void getBookList() {
        try {
            List<Book> bookList = bookManager.getBookList();
            for (Book book : bookList) {
                Log.d("Shelter", "MainActivity getBookList bookId = " + book.getBookId() + ", bookName = " + book.getBookName());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void startApp2Activity() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName("com.palmtop.app2", "com.palmtop.app2.MainActivity");
        intent.setComponent(cn);
        startActivity(intent);
    }

    private void addBook() {
        try {
            bookManager.addBook(new Book(1, "Android开发艺术探索"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
