package co.mobiwise.indircom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.model.GraphUser;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.fragment.FacebookLoginFragment;
import co.mobiwise.indircom.fragment.TwitterLoginFragment;
import co.mobiwise.indircom.listener.FacebookAuthListener;
import co.mobiwise.indircom.listener.TwitterAuthListener;
import co.mobiwise.indircom.utils.Connectivity;
import co.mobiwise.indircom.utils.SocialConstants;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by mac on 13/03/15.
 */
public class LoginActivity extends ActionBarActivity implements FacebookAuthListener, TwitterAuthListener {

    private static final String TAG = "LoginActivity";
    private Button facebook_login_button, twitter_login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
    }

    private void initializeViews() {
        facebook_login_button = (Button) findViewById(R.id.facebook_login);
        twitter_login_button = (Button) findViewById(R.id.twitter_login);

    }

    /**
     * Opens FacebookLoginFragment. This method is bound from activity_login.xml.
     *
     * @param view
     */
    public void openFacebookLoginFragment(View view) {
        if (Connectivity.isConnected(getApplicationContext())) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, FacebookLoginFragment.newInstance())
                    .addToBackStack("FacebookLoginFragment")
                    .commit();
        } else
            showConnectionError();

    }

    /**
     * Opens TwitterLoginFragment. This method is bound from activity_login.xml.
     *
     * @param view
     */
    public void openTwitterLoginFragment(View view) {
        if (Connectivity.isConnected(getApplicationContext())) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, TwitterLoginFragment.newInstance())
                    .addToBackStack("TwitterLoginFragment")
                    .commit();
        } else
            showConnectionError();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("indircom", String.valueOf(requestCode));

        if (requestCode == SocialConstants.TWITTER_REQUEST_CODE) {
            Log.v("indircom", "twitter_result");
        } else if (requestCode == Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE) {
            Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        }
    }

    @Override
    public void onFacebookUserFetched(Session session, GraphUser graphUser) {
        Log.v("onFacebookUserFetched", graphUser.getId() + "-" + graphUser.getName());
        //TODO: save shared and do api request.
    }


    @Override
    public void onTwitterUserFetched(Twitter mTwitter) {
        //TODO: save shared and do api request.
        try {
            Log.v("LoginActivity", mTwitter.getScreenName() + "");
        } catch (TwitterException e) {
            Log.v("LoginActivity", "err");
            e.printStackTrace();
        }
    }

    public void showConnectionError() {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
    }
}
