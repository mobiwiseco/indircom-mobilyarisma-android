package co.mobiwise.indircom.listener;

import com.facebook.Session;
import com.facebook.model.GraphUser;

/**
 * Created by mac on 13/03/15.
 */
public interface FacebookAuthListener {
    /**
     * Called when Facebook Authentication is successful. Returns a boolean whether fetching
     * user data is required or not. If true, Facebook user data will be sent
     * via 'onFacebookUserFetched()' method.
     *
     * @param session Facebook session
     * @return
     */
    public boolean onFacebookAuthentication(Session session);

    /**
     * Called when user data is fetched.
     *
     * @param session   Facebook session
     * @param graphUser Facebook user object
     */
    public void onFacebookUserFetched(Session session, GraphUser graphUser);
}
