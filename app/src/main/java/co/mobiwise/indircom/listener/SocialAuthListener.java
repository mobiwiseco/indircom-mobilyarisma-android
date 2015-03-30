package co.mobiwise.indircom.listener;

import co.mobiwise.indircom.model.User;

public interface SocialAuthListener {

    /**
     * Called when user data is fetched.
     *
     * @param user
     */
    public void onSocialUserFetched(User user);
}