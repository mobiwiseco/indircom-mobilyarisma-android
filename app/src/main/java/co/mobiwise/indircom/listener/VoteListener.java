package co.mobiwise.indircom.listener;

import co.mobiwise.indircom.model.App;

/**
 * Created by mertsimsek on 23/03/15.
 */
public interface VoteListener {

    /**
     * Called when vote process succeed
     * @param app
     */
    public void onVoteCompleted(App app);

    /**
     * Called when vote process is not finished successfully
     */
    public void onErrorOccured();


}
