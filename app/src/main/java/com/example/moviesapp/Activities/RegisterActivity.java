package com.example.moviesapp.Activities;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.moviesapp.Constrain.Constrains;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.moviesapp.Constrain.RequesHandle;
import com.example.moviesapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText userEmail, userPass, userName,userPhone;
    private Button RegisterBtn;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }
    private void initView()
    {
        userEmail=findViewById(R.id.editTextUserEmail);
        userPass=findViewById(R.id.editTextPass);
        userName=findViewById(R.id.editTextUserName);
        userPhone=findViewById(R.id.editTextUserPhone);
        RegisterBtn=findViewById(R.id.RegisterBtn);
        progressDialog = new ProgressDialog(this);
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });
    }
    private void RegisterUser()
    {
        final String email, pass, name, phone;
        email=userEmail.getText().toString();
        pass=userPass.getText().toString();
        name=userName.getText().toString();
        phone=userPhone.getText().toString();
        progressDialog.setMessage("Register user...");
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                Constrains.ROOT_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Nullable
            @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("username", name);
                params.put("password", pass);
                params.put("phone", phone);
                params.put("email", email);
                return params;
            }
        };
        RequesHandle.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(progressDialog!=null&&progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }
}