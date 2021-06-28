package com.shelter.review.mvp.view;

/**
 * @author: Shelter
 * Create time: 2021/6/28, 14:25.
 */
public interface LoginView {
    void onUserNameSetError();
    void onUserPasswordSetError();
    void showProgress();
    void hideProgress();
    void onLoginSuccess();
}
