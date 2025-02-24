package com.dtp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dtp.myapplication.entity.Product;
import com.dtp.myapplication.repository.ProductRepository;

public class AddProductActivity extends AppCompatActivity {
    private EditText edtProductName, edtProductPrice, edtProductDescription;
    private Button btnSaveProduct;
    private ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Product");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        productRepository = new ProductRepository(this);
        initViews();

        btnSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtProductName.getText().toString().isEmpty()) {
                    edtProductName.setError("Please enter product name");
                    edtProductName.requestFocus();
                    return;
                } else if (edtProductPrice.getText().toString().isEmpty()) {
                    edtProductPrice.setError("Please enter product price");
                    edtProductPrice.requestFocus();
                    return;
                } else if (edtProductDescription.getText().toString().isEmpty()) {
                    edtProductDescription.setError("Please enter product description");
                    edtProductDescription.requestFocus();
                    return;
                }

                productRepository.insert(
                        new Product(
                                edtProductName.getText().toString(),
                                Double.parseDouble(edtProductPrice.getText().toString()),
                                edtProductDescription.getText().toString()
                        )
                );
                Toast.makeText(AddProductActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void initViews() {
        edtProductName = findViewById(R.id.edtProductName);
        edtProductPrice = findViewById(R.id.edtProductPrice);
        edtProductDescription = findViewById(R.id.edtProductDescription);
        btnSaveProduct = findViewById(R.id.btnSaveProduct);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(AddProductActivity.this, WorkListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}