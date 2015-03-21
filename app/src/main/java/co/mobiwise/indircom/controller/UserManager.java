package co.mobiwise.indircom.controller;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mac on 17/03/15.
 */
public class UserManager {

    /**
     * Shared Preferences reference
     */
    private SharedPreferences pref;

    /**
     * Editor reference for Shared preferences
     */
    private SharedPreferences.Editor editor;

    /**
     * Shared pref mode
     */
    int PRIVATE_MODE = 0;

    /**
     * Sharedpref file name
     */
    private static final String PREFER_NAME = "indir_com_user_manager";

    /**
     * All Shared Preferences Keys
     */
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    private static final String USER_TOKEN = "user_token";
    private static final String IS_DEVICE_SAVED = "IsDeviceSaved";

    private static UserManager instance = null;

    private UserManager(Context context) {
        pref = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Singleton instance
     * @param context
     * @return
     */
    public static synchronized UserManager getInstance(Context context) {
        if (instance == null)
            instance = new UserManager(context);
        return instance;
    }

    /**
     * Save login status and token when user logged in.
     * @param user_token
     */
    public void userLoggedIn(String user_token) {
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(USER_TOKEN,user_token);
        editor.commit();
    }

    /**
     * Clear user informations when user logged out.
     */
    public void userLogout() {
        editor.clear();
        editor.commit();
    }

    /**
     * Check if user logged in.
     * @return
     */
    public boolean isLogin(){
        return pref.getBoolean(IS_USER_LOGIN,false);
    }

    /**
     * Gets token for user. 0 if no token saved.
     * @return
     */
    public String getUserToken(){
        return pref.getString(USER_TOKEN,"");
    }

}
