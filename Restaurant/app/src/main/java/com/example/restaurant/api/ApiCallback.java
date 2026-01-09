package com.example.restaurant.api;

public interface ApiCallback<T> {
    void onSuccess(T result);
    void onError(String message);
}
