package co.mobiwise.indircom.listener;

import java.util.ArrayList;

import co.mobiwise.indircom.model.App;

/**
 * Created by mertsimsek on 20/03/15.
 */
public interface VoteControllerListener {

    /**
     * Called when vote process succeed.
     */
    public void onVoteCompleted();

    /**
     * Called when error occured on vote process.
     * @param error_code
     */
    public void onErrorOccured(String error_code);

    /**
     * Called when user vote start sending.
     */
    public void onVoteStartSending();

    /**
     * Called when unvoted apps started fetching.
     */
    public void onAppsStartFetching();

    /**
     * Called when all unvoted apps fetched.
     */
    public void onAppsFetchCompleted(ArrayList<App> apps);

}
