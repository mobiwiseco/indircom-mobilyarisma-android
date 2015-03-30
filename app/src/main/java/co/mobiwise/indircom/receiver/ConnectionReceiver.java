package co.mobiwise.indircom.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import co.mobiwise.indircom.service.VoteService;
import co.mobiwise.indircom.utils.Connectivity;


public class ConnectionReceiver extends BroadcastReceiver {

    public ConnectionReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        /**
         * when BroadcastReceiver receives the connection change event
         * check internet connection. if there is no connection, then notify the app via connectionLost(),
         * or if there is internet connection notify the app via conectionFind()
         */
        if (Connectivity.isConnected(context))
            context.startService(new Intent(context, VoteService.class));
    }
}