package com.dtp.myapplication.service;

import android.content.Context;

import com.dtp.myapplication.bean.UserBean;
import com.dtp.myapplication.database.DatabaseSetHandler;

public class UserService {
    private DatabaseSetHandler databaseSetHandler;

    public UserService(Context context) {
        databaseSetHandler = new DatabaseSetHandler(context);
    }

    public boolean login(String username, String password) {
        UserBean userBean = databaseSetHandler.getUser(username);
        return username.equals(userBean.getUsername()) && password.equals(userBean.getPassword());
    }

    public boolean logout() {
        return true;
    }

    public UserBean getUserProfile(String username) {
        return databaseSetHandler.getUser(username);
    }
}
