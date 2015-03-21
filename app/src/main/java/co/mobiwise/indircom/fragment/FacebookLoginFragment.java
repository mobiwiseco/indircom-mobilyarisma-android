package co.mobiwise.indircom.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

import co.mobiwise.indircom.listener.FacebookAuthListener;

/**
 * Created by mac on 17/03/15.
 */
public class FacebookLoginFragment extends Fragment implements Session.StatusCallback {

    private static final String TAG = "FacebookLoginFragment";
    private FacebookAuthListener facebookAuthListener;

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
        if (session.isOpened() ) {
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
                Log.d(TAG, "User info is fetched from Facebook.");
                facebookAuthListener.onFacebookUserFetched(session, graphUser);
            }
        }).executeAsync();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session.openActiveSession(getActivity(), true, this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            facebookAuthListener = (FacebookAuthListener) getActivity();
        } catch (ClassCastException e) {
            throw new RuntimeException("The activity that contains FacebookLoginFragment must implement FacebookLoginFragment.FacebookAuthListener");
        }
    }
}