package com.shelter.review.retrofit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * @author: Shelter
 * Create time: 2021/11/18, 18:45.
 */
public class MyRetrofit {
    String httpUrl;
    Call.Factory callFactory;
    Map<Method, ServiceMethod> serviceMethodCache = new ConcurrentHashMap<>();

    public MyRetrofit(Builder builder) {
        this.httpUrl = builder.httpUrl;
        this.callFactory = builder.callFactory;
        if (callFactory == null) {
           callFactory = new OkHttpClient();
        }
    }


    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{serviceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //静态方法，直接返回
                if (Modifier.isStatic(method.getModifiers())) {
                    return method.invoke(null, args);
                }

                ServiceMethod serviceMethod = loadServiceMethod(method);

                return serviceMethod.invoke(args);
            }
        });
    }

    private ServiceMethod loadServiceMethod(Method method) {
        if (serviceMethodCache.get(method) != null) {
           return serviceMethodCache.get(method);
        }
        synchronized (MyRetrofit.class) {
            ServiceMethod serviceMethod = serviceMethodCache.get(method);
            if (serviceMethod == null) {
                serviceMethod = new ServiceMethod.Builder(this, method).build();
                serviceMethodCache.put(method, serviceMethod);
            }
            return serviceMethod;
        }
    }

    public static class Builder {
        private String httpUrl;
        private Call.Factory callFactory;

        public Builder baseUrl(String url) {
            this.httpUrl = url;
            return this;
        }

        public Builder callFactory(Call.Factory factory) {
            this.callFactory = factory;
            return this;
        }

        public MyRetrofit build() {
            return new MyRetrofit(this);
        }
    }
}