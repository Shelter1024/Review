package com.example.hostapp;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: Shelter
 * Create time: 2022/9/1, 12:14.
 */
public class HookUtil {
    public static final String ORIGIN_INTENT_KEY = "origin_intent_key";

    public static void hookAMS(Context context) {
        try {
            Field singletonField = ActivityManager.class.getDeclaredField("IActivityManagerSingleton");
            singletonField.setAccessible(true);
            Object singleton = singletonField.get(null);
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            final Object instance = mInstanceField.get(singleton);

            Class<?> iActivityManagerClass = Class.forName("android.app.IActivityManager");
            Object proxyInstance = Proxy.newProxyInstance(context.getClassLoader(), new Class[]{iActivityManagerClass}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if ("startActivity".equals(method.getName())) {
                        for (int i = 0; i < args.length; i++) {
                            if (args[i] instanceof Intent) {
                                Intent intent = (Intent) args[i];
                                if (intent.getComponent() != null) {
                                    String shortClassName = intent.getComponent().getShortClassName();
                                    Log.d("Shelter", "HookUtil invoke shortClassName = " + shortClassName);
                                    if (!TextUtils.isEmpty(shortClassName) && shortClassName.endsWith("PluginActivity")) {
                                        Intent newIntent = new Intent(context, ProxyActivity.class);
                                        newIntent.putExtra(ORIGIN_INTENT_KEY, (Intent) args[i]);
                                        args[i] = newIntent;
                                        Log.d("Shelter", "HookUtil invoke 替换intent成功");
                                    }
                                }
                                break;
                            }

                        }
                    }
                    return method.invoke(instance, args);
                }
            });
            mInstanceField.set(singleton, proxyInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void hookHandler(Context context) {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Field mhField = activityThreadClass.getDeclaredField("mH");
            mhField.setAccessible(true);
            Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            currentActivityThreadField.setAccessible(true);
            Object activityTread = currentActivityThreadField.get(null);
            Handler handler = (Handler) mhField.get(activityTread);
            Class<?> objClass = Class.forName("android.app.ActivityThread$ActivityClientRecord");
            Field intentField = objClass.getDeclaredField("intent");
            intentField.setAccessible(true);
            Handler.Callback callback = new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (msg.what == 100) {
                        if (msg.obj.getClass().isAssignableFrom(objClass)) {
                            try {
                                Intent intent = (Intent) intentField.get(msg.obj);
                                Parcelable parcelableExtra = intent.getParcelableExtra(ORIGIN_INTENT_KEY);
                                Log.d("Shelter", "HookUtil handleMessage 替换为原始intent");
                                intentField.set(msg.obj, parcelableExtra);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return false;
                }
            };
            Field mCallbackField = Handler.class.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);
            mCallbackField.set(handler, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


} 