package com.dtp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dtp.myapplication.bean.UserBean;
import com.dtp.myapplication.service.UserService;

public class EditProfileActivity extends AppCompatActivity {
    private EditText edtFirstName, edtLastName, edtPhone, edtRole;
    private Spinner spnAddress;
    private RadioGroup rdgGender;
    private Button btnSaveProfile;
    private UserService userService;
    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userService = new UserService(this);
        initViews();

        userBean = new UserBean();

        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtFirstName.getText().toString().isEmpty()) {
                    edtFirstName.setError("First name is required");
                    return;
                }
                if (edtLastName.getText().toString().isEmpty()) {
                    edtLastName.setError("Last name is required");
                    return;
                }
                if (edtPhone.getText().toString().isEmpty()) {
                    edtPhone.setError("Phone is required");
                    return;
                }
                if (edtRole.getText().toString().isEmpty()) {
                    edtRole.setError("Role is required");
                    return;
                }
                userBean.setFirstName(edtFirstName.getText().toString());
                userBean.setLastName(edtLastName.getText().toString());
                userBean.setCampus(spnAddress.getSelectedItem().toString());
                userBean.setMobile(edtPhone.getText().toString());
                userBean.setRole(edtRole.getText().toString());

                Intent intent = new Intent();
                intent.putExtra(IntentKey.FIRST_NAME, userBean.getFirstName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Log.d("EditUserProfileActivity", "handleOnBackPressed(): Device back button");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d("EditUserProfileActivity", "backIconOnHeaderPressed");
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);

        spnAddress = findViewById(R.id.spnAddress);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter
                .createFromResource(EditProfileActivity.this, R.array.campus_spinner, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAddress.setAdapter(arrayAdapter);
        edtPhone = findViewById(R.id.edtPhone);
        edtRole = findViewById(R.id.edtRole);

        rdgGender = findViewById(R.id.rdgGender);

        btnSaveProfile = findViewById(R.id.btnSaveProfile);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("EditUserProfileActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("EditUserProfileActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("EditUserProfileActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("EditUserProfileActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("EditUserProfileActivity", "onDestroy");
    }
}