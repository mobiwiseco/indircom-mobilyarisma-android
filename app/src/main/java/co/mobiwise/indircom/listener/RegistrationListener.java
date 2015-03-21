package co.mobiwise.indircom.listener;

import co.mobiwise.indircom.model.User;

/**
 * Created by mertsimsek on 21/03/15.
 */
public interface RegistrationListener {

    /**
     * Called when social user informations registered.
     */
    public void onUserRegistered(User user);

    /**
     * Called when social user registration started.
     */
    public void onUserRegistrationStarted();
}
