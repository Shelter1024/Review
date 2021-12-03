package com.shelter.review.retrofit;

/**
 * @author: Shelter
 * Create time: 2021/12/3, 11:40.
 */
public class QueryParameterHandler implements ParameterHandler {
    private final String key;

    public QueryParameterHandler(String key) {
        this.key = key;
    }


    @Override
    public void apply(ServiceMethod serviceMethod, String value) {
        serviceMethod.addQueryParameter(key, value);
    }
}