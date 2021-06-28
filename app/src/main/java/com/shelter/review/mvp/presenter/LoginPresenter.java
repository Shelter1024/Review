package com.shelter.review.mvp.presenter;

import com.shelter.review.mvp.model.entities.User;

/**
 * @author: Shelter
 * Create time: 2021/6/28, 14:14.
 */
public interface LoginPresenter {
    void validateCredential(User user);

    void onDestroy();
}
