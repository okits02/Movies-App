package com.example.moviesapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.Domain.GenreItem;
import com.example.moviesapp.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryEarchListAdapter extends RecyclerView.Adapter<CategoryEarchListAdapter.viewHolder> {
    List<String> items;
    Context context;

    public CategoryEarchListAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryEarchListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryEarchListAdapter.viewHolder holder, int position) {
        holder.titletxt.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView titletxt;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            titletxt=itemView.findViewById(R.id.TitleTxtCate);
        }
    }
}
