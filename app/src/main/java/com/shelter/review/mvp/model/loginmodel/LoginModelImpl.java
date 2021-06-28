package com.shelter.review.mvp.model.loginmodel;

import android.os.Handler;
import android.text.TextUtils;

import com.shelter.review.mvp.model.LoginModel;
import com.shelter.review.mvp.model.entities.User;
import com.shelter.review.mvp.presenter.OnLoginFinishedListener;

/**
 * @author: Shelter
 * Create time: 2021/6/28, 14:20.
 */
public class LoginModelImpl implements LoginModel {

    @Override
    public void login(User user, final OnLoginFinishedListener onLoginFinishedListener) {
        final String name = user.getName();
        final String password = user.getPassword();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean error = false;
                if (TextUtils.isEmpty(name)) {
                    error = true;
                    if (onLoginFinishedListener != null) {
                        onLoginFinishedListener.onUserNameError();
                    }
                }
                if (TextUtils.isEmpty(password)) {
                    error = true;
                    if (onLoginFinishedListener != null) {
                        onLoginFinishedListener.onUserPasswordError();
                    }
                }
                if (!error) {
                    if (onLoginFinishedListener != null) {
                        onLoginFinishedListener.onLoginSuccess();
                    }
                }
            }
        }, 2000);
    }

}