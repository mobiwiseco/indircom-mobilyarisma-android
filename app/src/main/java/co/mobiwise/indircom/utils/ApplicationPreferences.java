package co.mobiwise.indircom.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by mac on 13/03/15.
 */
public class ApplicationPreferences {

    private Context context;
    private SharedPreferences local_shared_preferences;
    private SharedPreferences.Editor local_editor;
    private static ApplicationPreferences instance = null;


    private ApplicationPreferences(Context context) {
        this.context = context;
        local_shared_preferences = PreferenceManager.getDefaultSharedPreferences(context);
        local_editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    }

    public static ApplicationPreferences getInstance(Context context) {
        if (instance == null)
            instance = new ApplicationPreferences(context);
        return instance;
    }
}
