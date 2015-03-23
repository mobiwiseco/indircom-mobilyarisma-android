package co.mobiwise.indircom.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.mobiwise.indircom.controller.UserManager;
import co.mobiwise.indircom.listener.AppFetchControllerListener;
import co.mobiwise.indircom.listener.RegistrationListener;
import co.mobiwise.indircom.listener.VoteListener;
import co.mobiwise.indircom.model.App;
import co.mobiwise.indircom.model.User;

/**
 * Created by mac on 13/03/15.
 */
public class Api{

    /**
     * Application context to use get instance of volley object.
     */
    private Context context;

    /**
     * Volley request to put requests.
     */
    private RequestQueue requestQueue;

    /**
     * RegistrationListener methods will be notified when user registration process.
     */
    private RegistrationListener registrationListener;

    /**
     * Vote listener to notify when vote completed.
     */
    private VoteListener voteListener;

    /**
     * VoteControllerListener methods will be notified while user vote process
     */
    private AppFetchControllerListener appFetchingControllerListener;

    /**
     * Api instance. Seriously.
     */
    private static Api instance = null;

    /**
     * Used on Logs.
     */
    private static String TAG = "ApiClass";

    /**
     * Private constructor for @Api. Also creates new instance of @RequestQueue.
     * @param context
     */
    private Api(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
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

        //Notify listener registration started
        if(registrationListener !=null)
            registrationListener.onUserRegistrationStarted();

        //create webservice url
        String registerUrl = ApiConstants.BASE_URL + ApiConstants.WEBSERVICE_URL + ApiConstants.VERSION + ApiConstants.METHOD_REGISTER;

        //initialize post request object for registration
        StringRequest registerRequest = new StringRequest(Request.Method.POST, registerUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject responseJson = new JSONObject(response.toString()).getJSONArray(ApiConstants.JSON_NAME_USER).getJSONObject(0);
                    User user = new User();
                    user.setAuth_id(responseJson.getString(ApiConstants.USER_AUTH_ID));
                    user.setName(responseJson.getString(ApiConstants.NAME));
                    user.setSurname(responseJson.getString(ApiConstants.SURNAME));
                    user.setToken(responseJson.getString(ApiConstants.TOKEN));

                    if(registrationListener !=null)
                        registrationListener.onUserRegistered(user);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //TODO Handle Error. Jokin' who cares.

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

        //adds request to request queue
        requestQueue.add(registerRequest);

    }

    /**
     * Gets all unvoted apps by user. @VoteControllerListener will be
     * notified when apps are fetched.
     */
    public void getApps(final String token, final String user_auth_id){

        /**
         * Notify listener with information that unvoted apps started fetching.
         */
        if(appFetchingControllerListener !=null){
                appFetchingControllerListener.onAppsStartFetching();
        }

        final String get_apps_url = ApiConstants.BASE_URL + ApiConstants.WEBSERVICE_URL + ApiConstants.VERSION +
                                "/" + String.valueOf(user_auth_id) + ApiConstants.METHOD_UNRATED;

        StringRequest unvotedAppsRequest = new StringRequest(Request.Method.POST, get_apps_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    ArrayList<App> appList = new ArrayList<>();
                    JSONObject appsJsonAll = new JSONObject(response);

                    /**
                     * If response 200 then OK.
                     */
                    if(appsJsonAll.getString(ApiConstants.CODE).equals(ApiConstants.OK)){

                        JSONArray appJsonArray = appsJsonAll.getJSONArray(ApiConstants.ARRAY_NAME_APPS);

                        /**
                         * Loop for all unvoted apps on json array.
                         */
                        for(int i = 0 ; i < appJsonArray.length() ; i++){

                            JSONObject appJsonObject = appJsonArray.getJSONObject(i);

                            App app = new App();
                            app.setApp_id(Integer.parseInt(appJsonObject.getString(ApiConstants.APP_ID)));
                            app.setApp_name(appJsonObject.getString(ApiConstants.APP_NAME));
                            app.setApp_description(appJsonObject.getString(ApiConstants.APP_DESCRIPTION));
                            app.setApp_download_url(appJsonObject.getString(ApiConstants.APP_DOWNLOAD_URL));
                            app.setApp_image_url(appJsonObject.getString(ApiConstants.APP_IMAGE_URL));

                            appList.add(app);
                        }


                        /**
                         * Notify listener when apps fetched completed.
                         */
                        if(appFetchingControllerListener !=null){
                                appFetchingControllerListener.onAppsFetchCompleted(appList);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //TODO Handle Error. Jokin' who cares.
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Put required params to maps
                Map<String, String> maps = new HashMap<String, String>();
                maps.put(ApiConstants.TOKEN, token);

                //return maps. Seriously.
                return maps;
            }
        };

        requestQueue.add(unvotedAppsRequest);
    }

    /**
     * Request user vote about related apps
     * @param app
     */
    public void voteApp(final App app){

        Log.v(TAG, app.toString());

        String voteUrl = ApiConstants.BASE_URL + ApiConstants.WEBSERVICE_URL + ApiConstants.VERSION +
                "/" + UserManager.getInstance(context).getUser().getAuth_id() + ApiConstants.METHOD_RATE + "/" + String.valueOf(app.getUserVote());

        StringRequest voteRequest = new StringRequest(Request.Method.POST, voteUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    Log.v(TAG,response);

                    /**
                     * If response 200 then OK.
                     */
                    if(jsonResponse.getString(ApiConstants.CODE).equals(ApiConstants.OK)){

                        /**
                         * Notify listener when app vote completed.
                         */
                        if(appFetchingControllerListener !=null){
                                voteListener.onVoteCompleted(app);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                /**
                 * Notify listener when app got error.
                 */
                if(appFetchingControllerListener !=null){
                    voteListener.onErrorOccured();
                }

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Put required params to maps
                Map<String, String> maps = new HashMap<String, String>();
                maps.put(ApiConstants.TOKEN, UserManager.getInstance(context).getUser().getToken());
                maps.put(ApiConstants.RATE, String.valueOf(app.getUserVote()));

                //return maps. Seriously.
                return maps;
            }
        };

        requestQueue.add(voteRequest);

    }

    /**
     * Register registration listener to notify registration process information.
     * @param registrationListener
     */
    public void registerRegistrationListener(RegistrationListener registrationListener){
        this.registrationListener = registrationListener;
    }

    /**
     * Unregister register no needed use.
     */
    public void unregisterRegistrationListener(){
        this.registrationListener = null;
    }

    /**
     * Register @VoteControllerListener to notify voting process informations.
     * @param appFetchingControllerListener
     */
    public void registerAppsFetchListener(AppFetchControllerListener appFetchingControllerListener){
        this.appFetchingControllerListener = appFetchingControllerListener;
    }

    /**
     * Unregister receiver no needed use.
     */
    public void unregisterAppsFetchListener(){
        this.appFetchingControllerListener = null;
    }

    public void registerVoteControllerListener(VoteListener voteListener){
        this.voteListener = voteListener;
    }

    public void unregisterVoteControllerListener(){
        this.voteListener = null;
    }
}
