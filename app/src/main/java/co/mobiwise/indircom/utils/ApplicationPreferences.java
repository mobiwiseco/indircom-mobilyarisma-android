package co.mobiwise.indircom.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.ArrayList;

import co.mobiwise.indircom.model.App;

public class ApplicationPreferences {

    private static ApplicationPreferences instance = null;
    private String KEY_QUEUE_LIST = "queue_list";
    private Context context;
    private SharedPreferences local_shared_preferences;
    private SharedPreferences.Editor local_editor;

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

    public void saveVoteRequestQueue(ArrayList<App> list) {
        try {
            String encoded_list = Utils.toString(list);
            local_editor.putString(KEY_QUEUE_LIST, encoded_list);
            local_editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToRequestQueue(App app) {
        String decoded_list = local_shared_preferences.getString(KEY_QUEUE_LIST, "-");
        String encoded_string = "";

        if (decoded_list.equals("-")) {
            ArrayList<App> app_list = new ArrayList<>();
            app_list.add(app);

            try {
                encoded_string = Utils.toString(app_list);
            } catch (IOException e) {
                e.printStackTrace();
            }
            local_editor.putString(KEY_QUEUE_LIST, encoded_string);
            local_editor.commit();
        } else {
            try {
                ArrayList<App> app_list = (ArrayList<App>) Utils.fromString(decoded_list);
                app_list.add(app);

                try {
                    encoded_string = Utils.toString(app_list);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                local_editor.putString(KEY_QUEUE_LIST, encoded_string);
                local_editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<App> getVoteRequestQueue() {
        ArrayList<App> app_list = new ArrayList<>();
        String decoded_list = local_shared_preferences.getString(KEY_QUEUE_LIST, "-");
        String encoded_string = "";

        if (decoded_list.equals("-"))
            return app_list;

        try {
            app_list = (ArrayList<App>) Utils.fromString(decoded_list);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return app_list;

    }

    public void registerSharedPreferencesChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        local_shared_preferences.registerOnSharedPreferenceChangeListener(listener);
    }
}