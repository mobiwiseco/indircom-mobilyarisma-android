package co.mobiwise.indircom.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

import java.util.ArrayList;
import java.util.List;

import co.mobiwise.indircom.listener.SocialAuthListener;
import co.mobiwise.indircom.model.User;
import co.mobiwise.indircom.utils.SocialConstants;

public class FacebookLoginFragment extends Fragment implements Session.StatusCallback {

    private static final String TAG = "FacebookLoginFragment";
    private SocialAuthListener socialAuthListener;

    public FacebookLoginFragment() {
    }

    /**
     * Static factory method that returns the
     * new fragment to the client.
     */
    public static FacebookLoginFragment newInstance() {
        FacebookLoginFragment facebookLoginFragment = new FacebookLoginFragment();
        return facebookLoginFragment;
    }

    @Override
    public void call(Session session, SessionState sessionState, Exception e) {
        Log.d(TAG, "Session opened ? " + session.isOpened());
        //&& facebookAuthListener.onFacebookAuthentication(session)
        if (session.isOpened()) {
            getUserData(session);
        }
    }

    /**
     * get user data from GraphApi
     *
     * @param session
     */
    private void getUserData(final Session session) {

        Request.newMeRequest(session, new Request.GraphUserCallback() {
            @Override
            public void onCompleted(GraphUser graphUser, Response response) {
                //initialize user
                User user = new User();
                user.setAuth_id(graphUser.getId());
                if (TextUtils.isEmpty(graphUser.getMiddleName()))
                    user.setName(graphUser.getFirstName());
                else
                    user.setName(graphUser.getFirstName() + " " + graphUser.getMiddleName());
                user.setSurname(graphUser.getLastName());
                /**
                 * if user refuses to give mail adress :(
                 */
                if (graphUser.asMap().get(SocialConstants.FACEBOOK_EMAIL) != null)
                    user.setEmail(graphUser.asMap().get(SocialConstants.FACEBOOK_EMAIL).toString());

                //Notify social auth listener by user.
                socialAuthListener.onSocialUserFetched(user);
            }
        }).executeAsync();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * we need to get permission to have user' s infos
         */
        List<String> permissions = new ArrayList<>();
        /**
         * add email permission
         */
        permissions.add("email");
        /**
         * open active session with permissions
         */
        Session.openActiveSession(getActivity(), true, permissions, this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            socialAuthListener = (SocialAuthListener) getActivity();
        } catch (ClassCastException e) {
            throw new RuntimeException("The activity that contains FacebookLoginFragment must implement FacebookLoginFragment.FacebookAuthListener");
        }
    }
}