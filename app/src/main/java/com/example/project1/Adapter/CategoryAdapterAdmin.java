package com.example.project1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.Model.Category;
import com.example.project1.R;

import java.util.ArrayList;

public class CategoryAdapterAdmin extends RecyclerView.Adapter<CategoryAdapterAdmin.CategoryHolder> {

    private ArrayList<Category> cate_list;

    public CategoryAdapterAdmin(ArrayList<Category> cate_list) {
        this.cate_list = cate_list;
        notifyDataSetChanged();
    }

    @Override
    public CategoryAdapterAdmin.CategoryHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_admin,parent,false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder( CategoryAdapterAdmin.CategoryHolder holder, int position) {
        holder.tvNameCate.setText(cate_list.get(position).getCategory());
        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), ""+cate_list.get(position).getCategory(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return cate_list.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        TextView tvNameCate;
        ImageView ivDel;
        public CategoryHolder( View itemView) {
            super(itemView);
            tvNameCate=itemView.findViewById(R.id.tvCateName_item);
            ivDel=itemView.findViewById(R.id.ivDel_item);
        }
    }
}
