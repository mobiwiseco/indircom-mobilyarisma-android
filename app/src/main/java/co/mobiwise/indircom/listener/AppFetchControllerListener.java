package co.mobiwise.indircom.listener;

import java.util.ArrayList;

import co.mobiwise.indircom.model.App;

/**
 * Created by mertsimsek on 20/03/15.
 */
public interface AppFetchControllerListener {

    /**
     * Called when unvoted apps started fetching.
     */
    public void onAppsStartFetching();

    /**
     * Called when all unvoted apps fetched.
     */
    public void onAppsFetchCompleted(ArrayList<App> apps);

}
