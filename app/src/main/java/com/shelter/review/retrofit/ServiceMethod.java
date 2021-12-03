package com.shelter.review.retrofit;

import android.os.Build;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * @author: Shelter
 * Create time: 2021/12/3, 10:58.
 */
public class ServiceMethod {
    ParameterHandler[] parameterHandlers;
    String httpMethod;
    HttpUrl baseUrl;
    HttpUrl.Builder urlBuilder;
    FormBody.Builder formBuilder;
    Call.Factory callFactory;


    private ServiceMethod(Builder builder) {
        this.httpMethod = builder.httpMethod;
        this.baseUrl = HttpUrl.get(builder.myRetrofit.httpUrl);
        this.urlBuilder = baseUrl.newBuilder(builder.urlPath);
        this.parameterHandlers = builder.parameterHandlers;
        this.callFactory = builder.myRetrofit.callFactory;;
        if ("POST".equals(httpMethod)) {
            formBuilder = new FormBody.Builder();
        }
    }

    public void addQueryParameter(String key, String value) {
        urlBuilder.addQueryParameter(key, value);
    }

    public void addFieldParameter(String key, String value) {
        formBuilder.add(key, value);
    }

    public Object invoke(Object[] args) {
        for (int i = 0; i < args.length; i++) {
            parameterHandlers[i].apply(this, args[i].toString());
        }

        HttpUrl httpUrl = urlBuilder.build();
        FormBody formBody = null;
        if (formBuilder != null) {
          formBody = formBuilder.build();
        }

        Request build = new Request.Builder().url(httpUrl).method(httpMethod, formBody).build();
        return callFactory.newCall(build);
    }


    static class Builder {
        ParameterHandler[] parameterHandlers;

        String httpMethod;
        String urlPath;
        MyRetrofit myRetrofit;

        public Builder(MyRetrofit myRetrofit, Method method) {
            this.myRetrofit = myRetrofit;
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            parameterHandlers = new ParameterHandler[parameterAnnotations.length];
            if (method.isAnnotationPresent(GET.class)) {
                //get请求
                for (int i = 0; i < parameterAnnotations.length; i++) {
                    Annotation[] annotations = parameterAnnotations[i];
                    for (int j = 0; j < annotations.length; j++) {
                        Annotation annotation = annotations[j];
                        if (annotation instanceof Query ) {
                            parameterHandlers[i] = new QueryParameterHandler(((Query) annotation).value());
                        }
                    }
                }
                httpMethod = "GET";
                GET getAnnotation = method.getAnnotation(GET.class);
                urlPath = getAnnotation.value();
            } else if (method.isAnnotationPresent(POST.class)) {
                //post请求
                for (int i = 0; i < parameterAnnotations.length; i++) {
                    Annotation[] annotations = parameterAnnotations[i];
                    for (int j = 0; j < annotations.length; j++) {
                        Annotation annotation = annotations[j];
                        if (annotation instanceof Field ) {
                            parameterHandlers[i] = new FieldParameterHandler(((Field) annotation).value());
                        }
                    }
                }
                httpMethod = "POST";
                POST postAnnotation = method.getAnnotation(POST.class);
                urlPath = postAnnotation.value();
            } else {
                throw new IllegalArgumentException("Method must be annotated with Get or Post");
            }

        }

        public ServiceMethod build() {
            return new ServiceMethod(this);
        }

    }
}