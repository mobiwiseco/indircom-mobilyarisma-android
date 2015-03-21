package co.mobiwise.indircom.controller;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mac on 17/03/15.
 */
public class UserManager {

    // Shared Preferences reference
    private SharedPreferences pref;

    // Editor reference for Shared preferences
    private SharedPreferences.Editor editor;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "indir_com_user_manager";

    /**
     * All Shared Preferences Keys
     */
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    private static final String IS_DEVICE_SAVED = "IsDeviceSaved";


    public static UserManager instance = null;
    private Context context;

    private UserManager(Context context) {

        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static synchronized UserManager getInstance(Context context) {
        if (instance == null)
            instance = new UserManager(context);
        return instance;
    }

    public void userLogin() {
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.commit();
    }

    public void userLogout() {
        editor.clear();
        editor.commit();
    }
    public boolean isLogin(){
        return pref.getBoolean(IS_USER_LOGIN,false);
    }

}
