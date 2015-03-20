package co.mobiwise.indircom.api;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import co.mobiwise.indircom.listener.VoteControllerListener;
import co.mobiwise.indircom.model.User;

/**
 * Created by mac on 13/03/15.
 */
public class Api{

    private Context context;

    private RequestQueue request_queue;

    private VoteControllerListener vote_controller_listener;

    private static Api instance = null;

    /**
     * Private constructor for @Api. Also creates new instance of @RequestQueue.
     * @param context
     */
    private Api(Context context) {
        this.context = context;
        this.request_queue = Volley.newRequestQueue(context);
    }

    /**
     * Singleton instance method for network operations
     * @param context
     * @return
     */
    public static Api getInstance(Context context) {

        if (instance == null)
            instance = new Api(context);
        return instance;
    }

    /**
     * Registers user to DB. Called whenever user logged in social media.
     * It is handled by server if user is already registered.
     * @param user
     */
    private void registerUser(User user){

    }

    /**
     * Gets all unvoted apps by user. @VoteControllerListener will be
     * notified when apps are fetched.
     */
    private void getApps(int user_auth_id){

    }

    /**
     * Request user vote about related apps
     * @param app_id
     */
    private void voteApp(int app_id){

    }

}
