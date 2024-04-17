package com.example.moviesapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviesapp.Adapter.CategoryListAdapter;
import com.example.moviesapp.Adapter.FilmListAdapter;
import com.example.moviesapp.Adapter.SliderAdapter;
import com.example.moviesapp.Domain.Genre;
import com.example.moviesapp.Domain.GenreItem;
import com.example.moviesapp.Domain.ListFilm;
import com.example.moviesapp.Domain.SliderItem;
import com.example.moviesapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView.Adapter adapterBestMovies, adapterUpcomming, adapterCategory;
    private RecyclerView recyclerViewBestMovies, recyclerViewUpcomming, recyclerViewCategory;
    private RequestQueue mrequestQueue;
    private StringRequest mstringRequest, mstringRequest1, mstringRequest2;
    private ProgressBar loading, loading1, loading2;
    private ViewPager2 viewPager2;
    private Handler sliderHandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        baners();
        sendRequestBestMovies();
        sendRequestCategoryMovies();
        sendRequestUpcommingMovies();
    }

    private void sendRequestBestMovies()
    {
        mrequestQueue=Volley.newRequestQueue(this);
        loading.setVisibility(View.VISIBLE);
        mstringRequest=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                loading.setVisibility(View.GONE);
                ListFilm items=gson.fromJson(response,ListFilm.class);
                adapterBestMovies=new FilmListAdapter(items);
                recyclerViewBestMovies.setAdapter(adapterBestMovies);
            }
        }, new Response.ErrorListener(){
        @Override
        public void onErrorResponse(VolleyError error) {
            loading.setVisibility(View.GONE);
            Log.i("Movies App", "ErrorResponse: "+ error.toString());
        }
    });
        mrequestQueue.add(mstringRequest);
    }

    private void sendRequestCategoryMovies(){
        mrequestQueue=Volley.newRequestQueue(this);
        loading1.setVisibility(View.VISIBLE);
        mstringRequest1=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/genres", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                loading1.setVisibility(View.GONE);
                ArrayList<GenreItem> catlist=gson.fromJson(response, new TypeToken<ArrayList<GenreItem>>() {
                }.getType());
                adapterCategory=new CategoryListAdapter(catlist);
                recyclerViewCategory.setAdapter(adapterCategory);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading1.setVisibility(View.GONE);
                Log.i("Movies App", "ErrorResponse: "+ error.toString());
            }
        });
        mrequestQueue.add(mstringRequest1);
    }

    private void sendRequestUpcommingMovies(){
        mrequestQueue=Volley.newRequestQueue(this);
        loading2.setVisibility(View.GONE);
        mstringRequest2=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=3", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                loading2.setVisibility(View.GONE);
                ListFilm items=gson.fromJson(response,ListFilm.class);
                adapterUpcomming=new FilmListAdapter(items);
                recyclerViewUpcomming.setAdapter(adapterUpcomming);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading2.setVisibility(View.GONE);
                Log.i("Movies App", "ErrorResponse: "+error.toString());
            }
        });
        mrequestQueue.add(mstringRequest2);
    }
    private void baners()
    {
        List<SliderItem> sliderItems=new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.wide));
        sliderItems.add(new SliderItem(R.drawable.wide1));
        sliderItems.add(new SliderItem(R.drawable.wide3));

        viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
             public void transformPage(@NonNull View page, float position) {
                float r=1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(SliderRunable);
            }
        });
    }

    private Runnable SliderRunable=new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(SliderRunable);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        sliderHandler.postDelayed(SliderRunable,2000);
    }

    private void initView()
    {
        viewPager2=findViewById(R.id.ViewPager1);
        recyclerViewBestMovies=findViewById(R.id.View1);
        recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory=findViewById(R.id.View2);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewUpcomming=findViewById(R.id.View3);
        recyclerViewUpcomming.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loading=findViewById(R.id.progressBar1);
        loading1=findViewById(R.id.progressBar2);
        loading2=findViewById(R.id.progressBar3);
        loading2=findViewById(R.id.progressBar3);
    }
}