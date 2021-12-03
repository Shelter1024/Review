package com.shelter.review.retrofit;

/**
 * @author: Shelter
 * Create time: 2021/12/3, 11:40.
 */
public class FieldParameterHandler implements ParameterHandler {
    private String key;

    public FieldParameterHandler(String key) {
        this.key = key;
    }

    @Override
    public void apply(ServiceMethod serviceMethod, String value) {
        serviceMethod.addFieldParameter(key, value);
    }
}