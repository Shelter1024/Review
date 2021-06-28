package com.shelter.review.mvp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shelter.review.R;
import com.shelter.review.mvp.model.entities.User;
import com.shelter.review.mvp.presenter.LoginPresenter;
import com.shelter.review.mvp.presenter.impl.LoginPresenterImpl;
import com.shelter.review.mvp.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private LoginPresenter loginPresenter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.progressBar);
        loginPresenter = new LoginPresenterImpl(this);
    }

    public void login(View view) {
        EditText etUserName = findViewById(R.id.etUserName);
        EditText etUserPassword = findViewById(R.id.etUserPassword);
        User user = new User(etUserName.getEditableText().toString(), etUserPassword.getEditableText().toString());
        loginPresenter.validateCredential(user);
    }

    @Override
    public void onUserNameSetError() {
        Toast.makeText(LoginActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserPasswordSetError() {
        Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
    }
}