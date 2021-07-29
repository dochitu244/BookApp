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



public class TopAdapterHome extends RecyclerView.Adapter<TopAdapterHome.MyViewHolder> {
    private ArrayList<Book> listBook;

    public TopAdapterHome(ArrayList<Book> listBook) {
        this.listBook = listBook;
        notifyDataSetChanged();
    }

    @Override
    public TopAdapterHome.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_home_top,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopAdapterHome.MyViewHolder holder, int position) {
        holder.ivBook.setImageResource(listBook.get(position).getResId());
        holder.tvTitle.setText(listBook.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return listBook.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivBook;
        private TextView tvTitle,tvAuthor;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivBook=itemView.findViewById(R.id.imgBook1);
            tvTitle=itemView.findViewById(R.id.tvTitle);


        }
    }
}
