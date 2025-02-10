package com.dtp.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtp.myapplication.adapter.WorkListAdapter;
import com.dtp.myapplication.bean.WorkBean;

import java.util.ArrayList;
import java.util.List;

public class WorkListActivity extends AppCompatActivity {
    private List<WorkBean> workBeans = new ArrayList<>();

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

        initData();
        RecyclerView recyclerViewList = findViewById(R.id.recycleViewWorkList);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        WorkListAdapter adapter = new WorkListAdapter(this, workBeans);
        recyclerViewList.setAdapter(adapter);
    }

    private void initData() {
        for (int i = 1; i < 50; i++) {
            WorkBean workBean = new WorkBean();
            workBean.setId(i);
            workBean.setWork("Work " + i);
            workBeans.add(workBean);
        }
    }
}