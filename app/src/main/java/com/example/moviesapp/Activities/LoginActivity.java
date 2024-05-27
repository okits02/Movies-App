package com.example.moviesapp.Activities;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesapp.Constrain.RequesHandle;
import com.example.moviesapp.Constrain.SharepreManage;
import com.example.moviesapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    private EditText userEdit, passEdit;
    private Button loginBtn;
    private ProgressDialog progressDialog;
    private TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Anhxa();
        Login();
    }
    private void Anhxa()
    {
        userEdit=findViewById(R.id.editTextUserEmail);
        passEdit=findViewById(R.id.editTextPass);
        loginBtn=findViewById(R.id.loginBtn);
        register=findViewById(R.id.Resgister);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Pleas wait.....");
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    private void Login()
    {
        final String username=userEdit.getText().toString().trim();
        final String userpass=passEdit.getText().toString().trim();
        progressDialog.show();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(userpass)) {
            Toast.makeText(getApplicationContext(), "Please enter both username and password", Toast.LENGTH_LONG).show();
            return;
        }
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                Constrains.ROOT_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject object=new JSONObject(response);
                            if(!object.getBoolean("error"))
                            {
                                SharepreManage.getInstance(getApplicationContext()).userLogin(
                                        object.getInt("id"),
                                        object.getString("username"),
                                        object.getString("email")
                                );
                                Toast.makeText(getApplicationContext(),"User login Successful",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(LoginActivity.this, MainActivity2.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(),object.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("LoginActivity", "JSONException: "+e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramas = new HashMap<>();
                paramas.put("UserName", username);
                paramas.put("UserPassword", userpass);
                return paramas;
            }
        };
        RequesHandle.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void Register()
    {
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(progressDialog != null&& progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }
}