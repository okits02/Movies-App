package com.example.moviesapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.moviesapp.Constrain.SharepreManage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviesapp.R;

public class ProfileActivity extends AppCompatActivity {
    private  TextView NameofUser, EmailOfUser;
    private Button AppSetting, ChangePassWord, LogOut, BackToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Anhxa();
        UpdateProfileUser();
    }
    private void Anhxa()
    {
        NameofUser=findViewById(R.id.NameOfUser);
        EmailOfUser=findViewById(R.id.EmailOfUser);
        AppSetting=findViewById(R.id.AppSettingBTN);
        ChangePassWord=findViewById(R.id.AccountSettingBTN);
        LogOut=findViewById(R.id.LogoutBTN);
        BackToMain=findViewById(R.id.BackBTN);

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharepreManage.getInstance(ProfileActivity.this).logout();
                Intent intent=new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        BackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    private void UpdateProfileUser()
    {
        NameofUser.setText(SharepreManage.getInstance(this).getUserName());
        EmailOfUser.setText(SharepreManage.getInstance(this).getUserEmail());
    }
}