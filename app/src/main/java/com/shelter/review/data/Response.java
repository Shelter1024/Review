package com.shelter.review.data;

/**
 * @author: Shelter
 * Create time: 2021/11/17, 11:53.
 */
public class Response <T>{
    private T data;

    public Response(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "data=" + data +
                '}';
    }

    public T getData() {
        return data;
    }
}