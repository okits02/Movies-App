package com.example.moviesapp.Constrain;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SharepreManage {
    private static SharepreManage instance;
    private static Context ctx;
    private static final String SHARE_PREF_NAME="mysharedref";
    private static final String KEY_USER_NAME="username";
    private static final String KEY_USER_EMAIL="useremail";
    private static final String KEY_USER_ID="userid";

    private SharepreManage(Context context) {
        ctx = context;
    }

    public static synchronized SharepreManage getInstance(Context context) {
        if (instance == null) {
            instance = new SharepreManage(context);
        }
        return instance;
    }

    public boolean userLogin(int ID, String UserName, String Email)
    {
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(KEY_USER_ID,ID);
        editor.putString(KEY_USER_NAME, UserName);
        editor.putString(KEY_USER_EMAIL, Email);
        editor.apply();
        return false;
    }
    public boolean isLogin()
    {
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_NAME, null) != null)
        {
            return true;
        }
        return false;
    }
    public boolean logout()
    {
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
    public String getUserName() {
    SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString(KEY_USER_NAME, null);
    }

    public String getUserEmail(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }
}
