package com.shelter.review.mvp.presenter.impl;

import com.shelter.review.mvp.model.LoginModel;
import com.shelter.review.mvp.model.entities.User;
import com.shelter.review.mvp.model.loginmodel.LoginModelImpl;
import com.shelter.review.mvp.presenter.LoginPresenter;
import com.shelter.review.mvp.presenter.OnLoginFinishedListener;
import com.shelter.review.mvp.view.LoginView;

/**
 * @author: Shelter
 * Create time: 2021/6/28, 14:24.
 */
public class LoginPresenterImpl implements LoginPresenter {
    LoginView loginView;
    LoginModel loginModel;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        loginModel = new LoginModelImpl();
    }

    @Override
    public void validateCredential(User user) {
        if (loginView != null) {
            loginView.showProgress();
        }
        loginModel.login(user, new OnLoginFinishedListener() {
            @Override
            public void onUserNameError() {
                if (loginView != null) {
                    loginView.onUserNameSetError();
                    if (loginView != null) {
                        loginView.hideProgress();
                    }
                }
            }

            @Override
            public void onUserPasswordError() {
                if (loginView != null) {
                    loginView.onUserPasswordSetError();
                    loginView.hideProgress();
                }
            }

            @Override
            public void onLoginSuccess() {
                if (loginView != null) {
                    loginView.hideProgress();
                    loginView.onLoginSuccess();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }
}