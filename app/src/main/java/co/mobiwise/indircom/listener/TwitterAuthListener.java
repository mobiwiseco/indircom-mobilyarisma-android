package co.mobiwise.indircom.listener;

import twitter4j.Twitter;

/**
 * Created by mac on 17/03/15.
 */
public interface TwitterAuthListener {

    /**
     * Called when user data is fetched.
     *
     * @param mTwitter Twitter user object
     */
    public void onTwitterUserFetched(Twitter mTwitter);


}
