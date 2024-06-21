package com.example.moviesapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.widget.SearchView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviesapp.Adapter.SearchAdapter;
import com.example.moviesapp.Constrain.Constrains;
import com.example.moviesapp.Domain.FilmItems;
import com.example.moviesapp.Domain.SearchItems;
import com.example.moviesapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private RecyclerView recyclerView;
    private List<FilmItems> filmList = new ArrayList<>();

    private SearchAdapter adapterSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        sendRequestSearchItem();
    }

    private void sendRequestSearchItem()
    {
        requestQueue= Volley.newRequestQueue(this);
        stringRequest=new StringRequest(Request.Method.GET, Constrains.ROOT_SEARCH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                SearchItems items=gson.fromJson(response,SearchItems.class);
                filmList=items.getFilms();
                adapterSearch=new SearchAdapter(filmList);
                recyclerView.setAdapter(adapterSearch);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Movies App", "ErrorResponse: "+error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }
    private void initView(){
        searchView=findViewById(R.id.searchbar);
        recyclerView=findViewById(R.id.ListViewSearch);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return true;
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void fileList(String newText) {
    List<FilmItems>filteredList=new ArrayList<>();
    for(FilmItems item : filmList)
    {
        if(item.getTitle().toLowerCase().contains(newText.toLowerCase()))
        {
            filteredList.add(item);
        }
    }
    if(adapterSearch!=null)
    {
        adapterSearch.updateList(filteredList);
    }
    }
}
