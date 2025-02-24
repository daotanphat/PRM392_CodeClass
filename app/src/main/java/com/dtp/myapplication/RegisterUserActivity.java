package com.dtp.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dtp.myapplication.bean.UserBean;
import com.dtp.myapplication.database.DatabaseSetHandler;

public class RegisterUserActivity extends AppCompatActivity {
    private EditText editUsername, editPass, editFirstName, editLastName;
    private Button btnLogin;
    private DatabaseSetHandler databaseSetHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseSetHandler = new DatabaseSetHandler(this);
        initViews();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserBean userBean = new UserBean();
                userBean.setUsername(editUsername.getText().toString());
                userBean.setPassword(editPass.getText().toString());
                userBean.setFirstName(editFirstName.getText().toString());
                userBean.setLastName(editLastName.getText().toString());

                databaseSetHandler.insert(userBean);
            }
        });
    }

    void initViews() {
        editUsername = findViewById(R.id.edit_username);
        editPass = findViewById(R.id.edit_pass);
        editFirstName = findViewById(R.id.edit_first_name);
        editLastName = findViewById(R.id.edit_last_name);
        btnLogin = findViewById(R.id.btnLogin);
    }
}