package com.example.assignment4;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences sessions;
    SharedPreferences.Editor editor;
    Context appcontext;
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String USERNAME = "username";
    public static final String TYPE = "type";

    public SessionManager(Context context){
        appcontext = context;
        sessions = appcontext.getSharedPreferences("Sessions",Context.MODE_PRIVATE);
        editor = sessions.edit();
    }
    public String getUsername(){
        return sessions.getString(USERNAME, null);
    }
    public String getType(){
        return sessions.getString(TYPE, null);
    }
    public void createSession(String username,String type)
    {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(USERNAME, username);
        editor.putString(TYPE, type);
        editor.commit();
    }
    public boolean IsLoggedIn(){
        if (sessions.getBoolean(IS_LOGIN, false))
            return true;
        else
            return false;
    }
    public void logout(){
        editor.clear();
        editor.commit();
    }
}
