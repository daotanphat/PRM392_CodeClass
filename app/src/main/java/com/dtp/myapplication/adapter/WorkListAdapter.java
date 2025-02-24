package com.dtp.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dtp.myapplication.R;
import com.dtp.myapplication.bean.WorkBean;
import com.dtp.myapplication.entity.Product;

import java.util.List;

public class WorkListAdapter extends RecyclerView.Adapter<WorkListAdapter.WorkViewHolder> {
    private Context context;
    private List<Product> products;

    public WorkListAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.work_item_layout, parent, false);
        return new WorkViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvId.setText("" + product.getId());
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getPrice() + "");
        holder.tvDescription.setText(product.getDescription());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class WorkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvId, tvName, tvPrice, tvDescription, tvAction;
        private WorkListAdapter workListAdapter;

        public WorkViewHolder(@NonNull View itemView, WorkListAdapter workListAdapter) {
            super(itemView);
            this.tvId = itemView.findViewById(R.id.tvId);
            this.tvName = itemView.findViewById(R.id.tvName);
            this.tvPrice = itemView.findViewById(R.id.tvPrice);
            this.tvDescription = itemView.findViewById(R.id.tvDescription);
            this.tvAction = itemView.findViewById(R.id.tvAction);
            this.workListAdapter = workListAdapter;
            this.tvAction.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Product");
            builder.setMessage("Are you sure you want to delete this work?");
            builder.setPositiveButton("Yes", ((dialog, which) -> {
                int position = getAdapterPosition();
                workListAdapter.products.remove(getAdapterPosition());
                workListAdapter.notifyItemRemoved(position);
            }));

            builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            builder.show();

        }
    }
}
