package co.mobiwise.indircom.listener;

import com.facebook.Session;
import com.facebook.model.GraphUser;

/**
 * Created by mac on 13/03/15.
 */
public interface FacebookAuthListener {

    /**
     * Called when user data is fetched.
     *
     * @param session   Facebook session
     * @param graphUser Facebook user object
     */
    public void onFacebookUserFetched(Session session, GraphUser graphUser);
}
