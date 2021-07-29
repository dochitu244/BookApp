package com.example.project1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.Model.Book;
import com.example.project1.R;

import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private ArrayList<Book> listBook;

    public CartAdapter(ArrayList<Book> listBook) {
        this.listBook = listBook;
    }

    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_cart,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartAdapter.CartViewHolder holder, int position) {
        holder.ivBook.setImageResource(listBook.get(position).getResId());
        holder.tvTitle.setText(listBook.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return listBook.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivBook;
        private TextView tvTitle;
        public CartViewHolder( View itemView) {
            super(itemView);
            ivBook=itemView.findViewById(R.id.ivCart);
            tvTitle=itemView.findViewById(R.id.tvTitleCart);
        }
    }
}
