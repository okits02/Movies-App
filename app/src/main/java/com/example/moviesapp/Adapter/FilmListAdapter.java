package com.example.moviesapp.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.moviesapp.Activities.DetailsActivity;
import com.example.moviesapp.Domain.ListFilm;
import com.example.moviesapp.R;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.viewHolder> {
    ListFilm items;
    Context context;

    public FilmListAdapter(ListFilm items) {
        this.items = items;
    }

    @NonNull
    @Override
    public FilmListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_film,parent,false);
        return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmListAdapter.viewHolder holder, int position) {
        holder.titletxt.setText(items.getData().get(position).getTitle());
        RequestOptions requestOptions=new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));

        Glide.with(context)
                .load(items.getData().get(position).getPoster())
                .apply(requestOptions)
                .into(holder.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), DetailsActivity.class);
                intent.putExtra("id", items.getData().get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.getData().size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView titletxt;
        ImageView pic;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            titletxt=itemView.findViewById(R.id.titleTxt);
            pic=itemView.findViewById(R.id.pic);
        }
    }
}
