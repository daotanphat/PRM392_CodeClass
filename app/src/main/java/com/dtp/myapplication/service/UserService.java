package com.dtp.myapplication.service;

import android.content.Context;

import com.dtp.myapplication.bean.UserBean;

public class UserService {
    public UserService(Context context) {

    }

    public boolean login(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean logout() {
        return true;
    }

    public UserBean getUserProfile(String username) {
        return new UserBean(username,
                "",
                "Hanoi",
                "Staff",
                true,
                "Phat",
                "Dao",
                "0909090909");
    }
}
