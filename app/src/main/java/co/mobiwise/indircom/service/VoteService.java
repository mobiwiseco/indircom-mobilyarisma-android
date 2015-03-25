package co.mobiwise.indircom.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.Queue;

import co.mobiwise.indircom.api.Api;
import co.mobiwise.indircom.listener.VoteListener;
import co.mobiwise.indircom.model.App;
import co.mobiwise.indircom.utils.ApplicationPreferences;
import co.mobiwise.indircom.utils.Utils;

/**
 * Created by mertsimsek on 22/03/15.
 */
public class VoteService extends Service implements VoteListener{

    /**
     * voted-nonsent apps queue
     */
    private Queue<App> appQueue;

    /**
     * Tag for log
     */
    private static String TAG = "VoteService.class";


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /**
         * Register this service to api to listen vote processes.
         */
        Api.getInstance(getApplicationContext()).registerVoteControllerListener(VoteService.this);

        /**
         * gets voted apps on preferences
         */
        ArrayList<App> votedAppList = ApplicationPreferences.getInstance(getApplicationContext()).getVoteRequestQueue();

        /**
         * convert arraylist to stack to pop sequently.
         */
        appQueue = Utils.convertToQueue(votedAppList);

        /**
         * send data on background
         */
        sendDataOnBackground();

        return START_NOT_STICKY;
    }

    /**
     * Send vote to Api class to request.
     */
    private void sendDataOnBackground(){

        Api api = Api.getInstance(getApplicationContext());

        App peekedApp = appQueue.peek();

        if(peekedApp!=null)
            api.voteApp(peekedApp);
        else
            stopSelf();

    }

    /**
     * If vote sent successfully @Api class notify here.
     * @param app
     */
    @Override
    public void onVoteCompleted(App app) {

        /**
         * Remove sent app and update preferences by updated queue
         */
        appQueue.remove(app);

        /**
         * Save updated list to shared preferences.
         */
        ApplicationPreferences.getInstance(getApplicationContext()).saveVoteRequestQueue(Utils.convertToArraylist(appQueue));

        /**
         * If not data to send, then stop service.
         */
        if(!appQueue.isEmpty())
            sendDataOnBackground();
        else
            stopSelf();

    }

    /**
     * If error occured while sending @Api class notify here.
     */
    @Override
    public void onErrorOccured() {
        /**
         * If still we hae internet connection try again to send. Otherwise stop service.
         */
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Api.getInstance(getApplicationContext()).unregisterVoteControllerListener();
    }
}
