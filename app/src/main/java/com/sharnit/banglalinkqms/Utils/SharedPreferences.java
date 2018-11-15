package com.sharnit.banglalinkqms.Utils;

import android.content.Context;
import android.net.Uri;

import com.sharnit.banglalinkqms.Adapter.ServiceType;

import java.util.ArrayList;

public class SharedPreferences {

    public static final String IS_LOGGED_IN = "is_logged_in";

    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PHONE = "user_phone";

    public static final String PREF_NAME = "qms_prefs";
    public static final String REG_ID = "reg_id";
    public static final String SESSION_TOKEN = "session_token";

    public static final ArrayList<ServiceType> SERVICE_TYPES = new ArrayList<>();

    public static Uri uri;

    public Context context;
    public static android.content.SharedPreferences prefs;

    public SharedPreferences(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

    }


    public String getUserPhone() {
        return prefs.getString(USER_PHONE, "");
    }

    public String getUserId() {return prefs.getString(USER_ID, "");}


    public void setUserId(String id) {
        prefs.edit().putString(USER_ID, id).commit();
    }

    public void setUserName(String name) {
        prefs.edit().putString(USER_NAME, name).commit();
    }

    public void setUserPhone(String phone) {
        prefs.edit().putString(USER_PHONE, phone).commit();
    }

    public String getUserName() {
        return prefs.getString(USER_NAME, "");
    }

    public void setIsLoggedIn(boolean value) {
        prefs.edit().putBoolean(IS_LOGGED_IN, value).commit();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(IS_LOGGED_IN, false);
    }


    public void setRegId(String id){
        prefs.edit().putString(REG_ID,id).commit();
    }
    public String getRegId(){
        return prefs.getString(REG_ID,"");
    }

    public void setSessionToken(String sessionToken){
        prefs.edit().putString(SESSION_TOKEN,sessionToken).commit();
    }
    public String getSessionToken(){
        return prefs.getString(SESSION_TOKEN,"");
    }



}
