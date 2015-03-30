package co.mobiwise.indircom.listener;

import co.mobiwise.indircom.model.User;

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
