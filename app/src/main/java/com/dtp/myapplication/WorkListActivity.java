package com.dtp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtp.myapplication.adapter.WorkListAdapter;
import com.dtp.myapplication.bean.WorkBean;
import com.dtp.myapplication.entity.Product;
import com.dtp.myapplication.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class WorkListActivity extends AppCompatActivity {
    private ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_work_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        productRepository = new ProductRepository(this);
        List<Product> products = productRepository.getAllProducts();

        initData();
        RecyclerView recyclerViewList = findViewById(R.id.recycleViewWorkList);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        WorkListAdapter adapter = new WorkListAdapter(this, products);
        recyclerViewList.setAdapter(adapter);

        Button btnAddProduct = findViewById(R.id.btnAddProduct);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkListActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        productRepository.getAllProducts();
    }
}