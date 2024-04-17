package com.example.moviesapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.NestedScrollingChild;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.moviesapp.Adapter.ActorListAdapter;
import com.example.moviesapp.Adapter.CategoryEarchListAdapter;
import com.example.moviesapp.Domain.FilmItems;
import com.example.moviesapp.R;
import com.google.gson.Gson;

public class DetailsActivity extends AppCompatActivity {
    private RequestQueue mrequestQueue;
    private StringRequest mstringRequest;
    private ProgressBar progressBar;
    private TextView titleTxt, movieRateTxt, movieTimeTxt, MovieSummaryInfo, movieActorInfo;
    private int idFilm;
    private ImageView pic2, backImg;
    private RecyclerView.Adapter adapterActorList, adapterCategory;
    private RecyclerView recyclerViewActors, recyclerViewCategory;

    private NestedScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        idFilm=getIntent().getIntExtra("id", 0);
        initView();
        sendRequest();
    }

    private void sendRequest(){
        mrequestQueue= Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        mstringRequest= new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/" + idFilm, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);

                FilmItems items= gson.fromJson(response, FilmItems.class);
                Glide.with(DetailsActivity.this)
                        .load(items.getPoster())
                        .into(pic2);
                titleTxt.setText(items.getTitle());
                movieTimeTxt.setText(items.getRuntime());
                movieRateTxt.setText(items.getImdbRating());
                MovieSummaryInfo.setText(items.getPlot());
                movieActorInfo.setText(items.getActors());
                if(items.getImages()!=null)
                {
                    adapterActorList=new ActorListAdapter(items.getImages());
                    recyclerViewActors.setAdapter(adapterActorList);
                }
                if(items.getGenres()!=null)
                {
                    adapterCategory= new CategoryEarchListAdapter(items.getGenres());
                    recyclerViewCategory.setAdapter(adapterCategory);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
        mrequestQueue.add(mstringRequest);
    }
    private void initView()
    {
        titleTxt=findViewById(R.id.MovieNametxt);
        progressBar=findViewById(R.id.progressBarDetail);
        scrollView=findViewById(R.id.scrollView4);
        pic2=findViewById(R.id.picDetail);
        movieRateTxt=findViewById(R.id.movieStar);
        movieTimeTxt=findViewById(R.id.movieTime);
        MovieSummaryInfo=findViewById(R.id.movieSummary);
        movieActorInfo=findViewById(R.id.movieActorInfo);
        backImg=findViewById(R.id.backimg);
        recyclerViewCategory=findViewById(R.id.genreView);
        recyclerViewActors=findViewById(R.id.imageRecycler);
        recyclerViewActors.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}