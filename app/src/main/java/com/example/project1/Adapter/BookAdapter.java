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

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private ArrayList<Book> listBook;

    public BookAdapter(ArrayList<Book> listBook) {
        this.listBook = listBook;
        notifyDataSetChanged();
    }



    @Override
    public BookViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_home_mid,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.ivBook.setImageResource(listBook.get(position).getResId());
        holder.tvTitle.setText(listBook.get(position).getTitle());
        holder.tvAuthor.setText(listBook.get(position).getAuthor());

    }

    @Override
    public int getItemCount() {
        return listBook.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivBook;
        private TextView tvTitle,tvAuthor;
        public BookViewHolder( View itemView) {
            super(itemView);
            ivBook=itemView.findViewById(R.id.ivBook);
            tvTitle=itemView.findViewById(R.id.tvBookName);
            tvAuthor=itemView.findViewById(R.id.tvBookAuthor);
        }
    }
}
