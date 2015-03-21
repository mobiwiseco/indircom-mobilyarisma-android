package co.mobiwise.indircom.api;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.mobiwise.indircom.listener.VoteControllerListener;
import co.mobiwise.indircom.model.App;
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
    public void registerUser(final User user){

        String register_url = ApiConstants.BASE_URL + ApiConstants.WEBSERVICE_URL + ApiConstants.VERSION + ApiConstants.METHOD_REGISTER;

        StringRequest register_request = new StringRequest(Request.Method.POST, register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //TODO Handle Response.

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //TODO Handle Error.

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Creates maps for all post parameters
                Map<String, String> maps = new HashMap<String, String>();
                maps.put(ApiConstants.NAME, user.getName());
                maps.put(ApiConstants.SURNAME, user.getSurname());
                maps.put(ApiConstants.API_KEY, ApiConstants.SECRET_API_KEY);
                maps.put(ApiConstants.USER_AUTH_ID, String.valueOf(user.getAuth_id()));

                //return maps. Seriously.
                return maps;
            }
        };

        request_queue.add(register_request);

    }

    /**
     * Gets all unvoted apps by user. @VoteControllerListener will be
     * notified when apps are fetched.
     */
    public void getApps(final int user_auth_id){

        String get_apps_url = ApiConstants.BASE_URL + ApiConstants.WEBSERVICE_URL + ApiConstants.VERSION +
                                "/" + String.valueOf(user_auth_id) + ApiConstants.METHOD_UNRATED;

        StringRequest unvoted_apps_request = new StringRequest(Request.Method.POST, get_apps_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //TODO Handle Response.

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //TODO Handle Error.
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Put required params to maps
                Map<String, String> maps = new HashMap<String, String>();
                maps.put(ApiConstants.TOKEN, String.valueOf(user_auth_id));

                //return maps. Seriously.
                return maps;
            }
        };

        request_queue.add(unvoted_apps_request);
    }

    /**
     * Request user vote about related apps
     * @param app_id
     */
    public void voteApp(final int user_auth_id, int app_id, final int vote){

        String vote_url = ApiConstants.BASE_URL + ApiConstants.WEBSERVICE_URL + ApiConstants.VERSION +
                "/" + String.valueOf(user_auth_id) + ApiConstants.METHOD_RATE + "/" + String.valueOf(app_id);

        StringRequest vote_request = new StringRequest(Request.Method.POST, vote_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //TODO Handle Response.
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //TODO Handle Error.
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Put required params to maps
                Map<String, String> maps = new HashMap<String, String>();
                maps.put(ApiConstants.TOKEN, String.valueOf(user_auth_id));
                maps.put(ApiConstants.RATE, String.valueOf(vote));

                //return maps. Seriously.
                return maps;
            }
        };

        request_queue.add(vote_request);

    }

    private ArrayList<App> convertJsonToApps(JSONArray jsonArray){
        //TODO convert json to unvoted app list.
        return null;
    }


}
