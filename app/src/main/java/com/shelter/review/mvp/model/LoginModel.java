package com.shelter.review.mvp.model;

import com.shelter.review.mvp.model.entities.User;
import com.shelter.review.mvp.presenter.OnLoginFinishedListener;

/**
 * @author: Shelter
 * Create time: 2021/6/28, 14:11.
 */
public interface LoginModel {
    void login(User user, OnLoginFinishedListener onLoginFinishedListener);
} 