package co.mobiwise.indircom.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Queue;

import co.mobiwise.indircom.api.Api;
import co.mobiwise.indircom.listener.VoteListener;
import co.mobiwise.indircom.model.App;
import co.mobiwise.indircom.utils.ApplicationPreferences;
import co.mobiwise.indircom.utils.Connectivity;
import co.mobiwise.indircom.utils.Utils;

/**
 * Created by mertsimsek on 22/03/15.
 */
public class VoteService extends Service implements VoteListener{

    /**
     * voted-nonsent apps queue
     */
    private Queue<App> app_queue;

    /**
     * Tag for log
     */
    private static String TAG = "VoteService";

    /**
     * Delay time if some error occur while sending vote.
     */
    private int delay_ms = 1000 * 60 * 5;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /**
         * gets voted apps on preferences
         */
        ArrayList<App> voted_app_list = ApplicationPreferences.getInstance(getApplicationContext()).getVoteRequestQueue();

        /**
         * convert arraylist to stack to pop sequently.
         */
        app_queue = Utils.convertToStack(voted_app_list);

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

        Log.v(TAG,"sendDataOnBackground");
        Api api = Api.getInstance(getApplicationContext());
        App peeked_app = app_queue.peek();
        api.voteApp(peeked_app);

    }

    /**
     * If vote sent successfully @Api class notify here.
     * @param app
     */
    @Override
    public void onVoteCompleted(App app) {

        Log.v(TAG,"onVoteCompleted");
        /**
         * Remove sent app and update preferences by updated queue
         */
        app_queue.remove(app);
        ApplicationPreferences.getInstance(getApplicationContext()).saveVoteRequestQueue(Utils.convertToArraylist(app_queue));

        /**
         * If not data to send, then stop service.
         */
        if(!app_queue.isEmpty())
            sendDataOnBackground();
        else stopSelf();
    }

    /**
     * If error occured while sending @Api class notify here.
     */
    @Override
    public void onErrorOccured() {

        Log.v(TAG,"onErrorOccured");

        /**
         * If still we hae internet connection try again to send. Otherwise stop service.
         */
        if(Connectivity.isConnected(getApplicationContext()))
            sendDataOnBackground();
        else
            stopSelf();
    }

    @Override
    public void onDestroy() {
        /**
         * If service wants to stop because of error then set alarm to wake it up @delay_ms later.
         */
        if(!app_queue.isEmpty())
            setServiceAlarm();
    }

    private void setServiceAlarm(){

        Log.v(TAG,"setServiceAlarm");

        /**
         * Sets alarm to wake service up @delay_ms later.
         */
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, new Intent(this, VoteService.class), 0);
        alarm.set(alarm.RTC_WAKEUP, System.currentTimeMillis() + delay_ms, pendingIntent);
    }

}
