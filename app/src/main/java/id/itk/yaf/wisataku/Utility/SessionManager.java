package id.itk.yaf.wisataku.Utility;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.json.JSONObject;

import id.itk.yaf.wisataku.Activity.Login;

public class SessionManager {
    SharedPreferences pref;

    Editor editor;

    Context mContext;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "WisataKuPref";
    private static final String IS_LOGIN ="IsLoggedIn";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_JSON = "user_json";

    @SuppressLint("CommitPrefEdits")
    public SessionManager (Context mContext){
        this.mContext = mContext;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String username){
        editor.putBoolean(IS_LOGIN,true);

        editor.putString(KEY_USERNAME, username);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(mContext, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            mContext.startActivity(i);
        }
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user username
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));

        // return user
        return user;
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(mContext, Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        mContext.startActivity(i);
    }
}
