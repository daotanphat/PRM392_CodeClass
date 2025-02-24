package com.dtp.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dtp.myapplication.database.DatabaseSetHandler;
import com.dtp.myapplication.service.UserService;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText editUsername, editPass;
    private Spinner spinnerCampus;
    private RadioGroup rdgRole;
    private RadioButton rdManager, rdStaff;
    private CheckBox cboRemember;
    private Button btnLogin;
    private UserService userService;
    private DatabaseSetHandler databaseSetHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        userService = new UserService(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseSetHandler = new DatabaseSetHandler(this);

        editUsername = findViewById(R.id.edit_username);
        editPass = findViewById(R.id.edit_pass);

        spinnerCampus = findViewById(R.id.spinnerCampus);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter
                .createFromResource(MainActivity.this, R.array.campus_spinner, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCampus.setAdapter(arrayAdapter);
        spinnerCampus.setSelection(0);
        spinnerCampus.setOnItemSelectedListener(this);

//        spinnerCampus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, "Selected campus: " + spinnerCampus.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        rdgRole = findViewById(R.id.rdgRole);
        rdManager = findViewById(R.id.rdManager);
        rdStaff = findViewById(R.id.rdStaff);
        cboRemember = findViewById(R.id.cboRemember);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                String password = editPass.getText().toString();
                String campus = spinnerCampus.getSelectedItem().toString();
                String role = rdManager.isChecked() ? "Manager" : "Staff";
                boolean remember = cboRemember.isChecked();

                Toast.makeText(MainActivity.this,
                                "Button Click",
                                Toast.LENGTH_SHORT)
                        .show();

                if (username.isEmpty() || password.isEmpty() || campus.isEmpty() || role.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                                    "Pls fill all fields",
                                    Toast.LENGTH_SHORT)
                            .show();

                    if (username.isEmpty()) {
                        editUsername.setError("Username is required");
                        editUsername.requestFocus();
                    }
                    if (password.isEmpty()) {
                        editPass.setError("Password is required");
                        editPass.requestFocus();
                    }
                } else {
                    if (userService.login(username, password)) {
                        Toast.makeText(MainActivity.this,
                                        "Login Success",
                                        Toast.LENGTH_SHORT)
                                .show();
                        Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                        intent.putExtra(IntentKey.USERNAME, username);
                        intent.putExtra(IntentKey.PASSWORD, password);
                        intent.putExtra(IntentKey.CAMPUS, campus);
                        intent.putExtra(IntentKey.ROLE, role);
                        intent.putExtra(IntentKey.REMEMBER, remember);

                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.putString("campus", campus);
                        editor.putString("role", role);
                        editor.putBoolean("remember", remember);
                        editor.apply();

                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this,
                                        "Login Failed",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this, "Selected campus: " + spinnerCampus.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}