package co.mobiwise.indircom.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import co.mobiwise.indircom.model.App;
import co.mobiwise.indircom.service.VoteService;
import co.mobiwise.indircom.utils.ApplicationPreferences;

public class VoteRequestQueue implements SharedPreferences.OnSharedPreferenceChangeListener {

    /**
     * Instance for @VoteRequestQueue class
     */
    private static VoteRequestQueue instance = null;

    /**
     * Context to use preferences and service call.
     */
    private Context context;

    private String TAG = "VoteRequestQueue";

    private VoteRequestQueue(Context context) {
        this.context = context;
        ApplicationPreferences.getInstance(context).registerSharedPreferencesChangeListener(VoteRequestQueue.this);
    }

    /**
     * Singleton method
     *
     * @param context
     * @return
     */
    public static VoteRequestQueue getInstance(Context context) {
        if (instance == null)
            instance = new VoteRequestQueue(context);
        return instance;
    }

    /**
     * Adds app to request queue to send vote.
     *
     * @param app
     */
    public void addVote(App app) {
        Log.v(TAG, "addVote");
        ApplicationPreferences.getInstance(context).addToRequestQueue(app);
    }

    /**
     * Notified when app added to preferences request queue.
     * Starts @VoteService when new item added to request queue.
     *
     * @param sharedPreferences
     * @param key
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.v(TAG, "onSharedPreferenceChanged");
        context.startService(new Intent(context.getApplicationContext(), VoteService.class));
    }
}