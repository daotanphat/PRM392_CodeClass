package com.dtp.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dtp.myapplication.bean.UserBean;
import com.dtp.myapplication.service.UserService;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Method;

public class UserProfileActivity extends AppCompatActivity {
    private EditText edtFirstName, edtLastName, edtPhone, edtRole;
    private ImageView imageView;
    private Spinner spnAddress;
    private RadioGroup rdgGender;
    private Button btnSaveProfile;
    private UserService userService;
    private String userName;

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

    private ActivityResultLauncher<Intent> startImageSelection = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult activityResult) {
                    // Lấy Intent trả về từ Activity khác
                    Intent intent = activityResult.getData();
                    if (intent != null) {
                        imageView.setImageURI(intent.getData());
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
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        userName = sharedPreferences.getString("username", "");
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

        String url = "https://i2-prod.mirror.co.uk/incoming/article8235673.ece/ALTERNATES/s615b/Cristiano-Ronaldo-Memes.png";
        imageView.setImageResource(R.drawable.facebook_logo);
        Picasso.with(this)
                .load(url)
                .error(R.drawable.img)
                .placeholder(R.drawable.img_1)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUploadDialog();
            }
        });

        edtFirstName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu menu = new PopupMenu(UserProfileActivity.this, edtFirstName);
                getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.item1_action) {
                            Toast.makeText(UserProfileActivity.this, "item 1", Toast.LENGTH_SHORT).show();
                        } else if (item.getItemId() == R.id.item2_action) {
                            Toast.makeText(UserProfileActivity.this, "item 2", Toast.LENGTH_SHORT).show();
                        } else if (item.getItemId() == R.id.item3_action) {
                            Toast.makeText(UserProfileActivity.this, "item 3", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                menu.show();
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.context_action_edit) {
            Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.context_action_view) {
            Toast.makeText(this, "View", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.context_action_delete) {
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    // launch window for select image in local storage
    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startImageSelection.launch(intent);
    }

    private void showUploadDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Are you want to upload photo?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    selectImage();
                })
                .setNegativeButton("Dimis", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_option_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit_user_option_menu) {

        } else if (item.getItemId() == R.id.service_option_menu) {

        } else if (item.getItemId() == R.id.setting_option_menu) {

        } else if (item.getItemId() == R.id.favorite_option_menu) {

        } else if (item.getItemId() == R.id.logout_option_menu) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featuredId, Menu menu) {
        if (menu != null) {
            try {
                Method method = menu.getClass()
                        .getDeclaredMethod("setOptionalIconVisible", Boolean.TYPE);
                method.setAccessible(true);
                method.invoke(menu, true);
            } catch (Exception ex) {
                Log.d("ViewUserProfileActivity", "onMenuOpened", ex);
            }
        }
        return super.onMenuOpened(featuredId, menu);
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

        registerForContextMenu(edtFirstName);
        registerForContextMenu(edtLastName);
        registerForContextMenu(imageView);
        registerForContextMenu(spnAddress);
        registerForContextMenu(edtPhone);
        registerForContextMenu(edtRole);
    }

    private void initViews() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        imageView = findViewById(R.id.imageView);

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