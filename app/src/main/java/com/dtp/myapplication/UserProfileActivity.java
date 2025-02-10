package com.dtp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dtp.myapplication.bean.UserBean;
import com.dtp.myapplication.service.UserService;

public class UserProfileActivity extends AppCompatActivity {
    private EditText edtFirstName, edtLastName, edtPhone, edtRole;
    private Spinner spnAddress;
    private RadioGroup rdgGender;
    private Button btnSaveProfile;
    private UserService userService;

    private ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult activityResult) {
                    // Lấy Intent trả về từ Activity khác
                    Intent intent = activityResult.getData();
                    if (intent != null) {
                        String firstName = intent.getStringExtra(IntentKey.FIRST_NAME);
                        edtFirstName.setText(firstName);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        userService = new UserService(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        String userName = "";
        if (getIntent() != null) {
            userName = getIntent().getStringExtra(IntentKey.USERNAME);
        }
        UserBean userBean = userService.getUserProfile(userName);

        setData(userBean);
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, EditProfileActivity.class);
                intent.putExtra(IntentKey.FIRST_NAME, userBean.getFirstName());
                intent.putExtra(IntentKey.LAST_NAME, userBean.getLastName());
                intent.putExtra(IntentKey.CAMPUS, userBean.getCampus());
                intent.putExtra(IntentKey.ROLE, userBean.getRole());
                startForResult.launch(intent);
            }
        });
    }

    private void setData(UserBean userBean) {
        edtFirstName.setText(userBean.getFirstName());
        edtLastName.setText(userBean.getLastName());
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spnAddress.getAdapter();
        int position = adapter.getPosition(userBean.getCampus());
        if (position >= 0) {
            spnAddress.setSelection(position);
        }
        edtPhone.setText(userBean.getMobile());
        edtRole.setText(userBean.getRole());
    }

    private void initViews() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);

        spnAddress = findViewById(R.id.spnAddress);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter
                .createFromResource(UserProfileActivity.this, R.array.campus_spinner, android.R.layout.simple_spinner_item);
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
        Log.d("ViewUserProfileActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ViewUserProfileActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ViewUserProfileActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ViewUserProfileActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ViewUserProfileActivity", "onDestroy");
    }
}