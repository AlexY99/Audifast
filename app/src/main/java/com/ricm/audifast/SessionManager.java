package com.ricm.audifast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ricm.audifast.ui.MainActivity;

public class SessionManager {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;
    int num=10;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "reg";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String USER_EMAIL = "Email";


    public SessionManager(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = prefs.edit();
    }

    public void createLoginSession(String Email){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(USER_EMAIL, Email);
        editor.commit();
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public String getUserEmail(){
        return prefs.getString(USER_EMAIL, null);
    }


    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return prefs.getBoolean(IS_LOGIN, false);
    }
}