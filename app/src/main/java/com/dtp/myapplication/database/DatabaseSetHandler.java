package com.dtp.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.dtp.myapplication.bean.UserBean;

public class DatabaseSetHandler extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "MyDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String USER_TABLE = "User";
    private static final String USER_ID = "id";
    private static final String FIRST_NAME = "name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + USER_TABLE + "("
                    + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + FIRST_NAME + " TEXT,"
                    + LAST_NAME + " TEXT,"
                    + EMAIL + " TEXT NOT NULL,"
                    + PASSWORD + " TEXT NOT NULL" + ")";

    public DatabaseSetHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (db.isOpen()) {
            db.execSQL(CREATE_USER_TABLE);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            if (db.isOpen()) {
                db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
//                onCreate(db);
            }
        }
    }

    public void insert(UserBean userBean) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FIRST_NAME, userBean.getFirstName());
            values.put(LAST_NAME, userBean.getLastName());
            values.put(EMAIL, userBean.getUsername());
            values.put(PASSWORD, userBean.getPassword());
            db.insert(USER_TABLE, null, values);

            Log.d("DatabaseHandler", "User inserted successfully");
        } catch (Exception ex) {
            Log.d("DatabaseHandler", "Error while inserting new user", ex);
        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }
        }
    }

    public UserBean getUser(String username) {
        SQLiteDatabase db = null;
        UserBean userBean = null;
        try {
            db = getWritableDatabase();
            String query = "SELECT * FROM " + USER_TABLE + " WHERE " + EMAIL + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{username});
            if (cursor.moveToFirst()) {
                userBean = new UserBean();
                int index = cursor.getColumnIndex(USER_ID);
                int id = cursor.getInt(index);
                userBean.setId(id);

                index = cursor.getColumnIndex(FIRST_NAME);
                String firstName = cursor.getString(index);
                userBean.setFirstName(firstName);

                index = cursor.getColumnIndex(LAST_NAME);
                String lastName = cursor.getString(index);
                userBean.setLastName(lastName);

                index = cursor.getColumnIndex(PASSWORD);
                String password = cursor.getString(index);
                userBean.setPassword(password);

                index = cursor.getColumnIndex(EMAIL);
                String email = cursor.getString(index);
                userBean.setUsername(email);
            }
            cursor.close();
            return userBean;
        } catch (Exception ex) {
            Log.d("DatabaseHandler", "Error while inserting new user", ex);
            throw ex;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }
}
