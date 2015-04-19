package co.mobiwise.indircom.listener;

import co.mobiwise.indircom.model.App;

public interface VoteListener {

    /**
     * Called when vote process succeed
     *
     * @param app
     */
    public void onVoteCompleted(App app);

    /**
     * Called when vote process is not finished successfully
     */
    public void onErrorOccured();
}