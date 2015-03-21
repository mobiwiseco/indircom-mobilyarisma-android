package co.mobiwise.indircom.listener;

import co.mobiwise.indircom.model.User;

/**
 * Created by mac on 17/03/15.
 */
public interface SocialAuthListener {

    /**
     * Called when user data is fetched.
     * @param user
     */
    public void onSocialUserFetched(User user);


}
