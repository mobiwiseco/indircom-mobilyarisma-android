package co.mobiwise.indircom.api;

import android.content.Context;

/**
 * Created by mac on 13/03/15.
 */
public class Api {

    private static Api instance = null;
    private Context context;

    private Api(Context context) {
        this.context = context;
    }

    public static Api getInstance(Context context) {

        if (instance == null)
            instance = new Api(context);
        return instance;
    }
}
