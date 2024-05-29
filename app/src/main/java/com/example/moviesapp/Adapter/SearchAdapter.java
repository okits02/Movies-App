package com.example.moviesapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviesapp.Domain.FilmItems;
import com.example.moviesapp.Domain.SearchItems;
import com.example.moviesapp.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    List<FilmItems> items;
    Context context;

    public SearchAdapter(List<FilmItems> items) {
        this.items = items;
    }

    public void updateList(List<FilmItems> newList) {
        this.items=newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_search, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        holder.titleSearch.setText(items.get(position).getTitle());
        RequestOptions requestOptions=new RequestOptions();
        requestOptions=requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
        Glide.with(context)
                .load(items.get(position).getPosterPath())
                .apply(requestOptions)
                .into(holder.picSearch);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleSearch;
        ImageView picSearch;
        public ViewHolder(View inflate) {
            super(inflate);
            titleSearch=inflate.findViewById(R.id.nameSearch);
            picSearch=inflate.findViewById(R.id.picSearch);
        }
    }
}
