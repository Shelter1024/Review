package com.shelter.review.mvp.presenter;

/**
 * @author: Shelter
 * Create time: 2021/6/28, 14:12.
 */
public interface OnLoginFinishedListener {
    void onUserNameError();
    void onUserPasswordError();
    void onLoginSuccess();
}
