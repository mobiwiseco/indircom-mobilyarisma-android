package co.mobiwise.indircom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.Session;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.mobiwise.indircom.R;
import co.mobiwise.indircom.api.Api;
import co.mobiwise.indircom.controller.UserManager;
import co.mobiwise.indircom.fragment.FacebookLoginFragment;
import co.mobiwise.indircom.fragment.TwitterLoginFragment;
import co.mobiwise.indircom.listener.RegistrationListener;
import co.mobiwise.indircom.listener.SocialAuthListener;
import co.mobiwise.indircom.model.User;
import co.mobiwise.indircom.utils.Connectivity;
import co.mobiwise.indircom.utils.MaterialDesignDialog;
import co.mobiwise.indircom.utils.SocialConstants;

public class LoginActivity extends ActionBarActivity implements SocialAuthListener, RegistrationListener {

    private static final String TAG = "LoginActivity";

    @InjectView(R.id.layout_facebook_login)
    RelativeLayout facebook_login_layout;

    @InjectView(R.id.layout_twitter_login)
    RelativeLayout twitter_login_layout;

    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (UserManager.getInstance(getApplicationContext()).isLogin()) {
            Intent i = new Intent(this, MainActivity.class);
            finish();
            startActivity(i);
        }

        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        Api.getInstance(getApplicationContext()).registerRegistrationListener(this);
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

        if (requestCode == SocialConstants.TWITTER_REQUEST_CODE) {
            Log.v("indircom", "twitter_result");
        } else if (requestCode == Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE) {
            Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        }
    }

    /**
     * Notified when facebook or twitter user logged in.
     *
     * @param user
     */
    @Override
    public void onSocialUserFetched(User user) {
        Api.getInstance(getApplicationContext()).registerUser(user);
    }

    /**
     * Connection error
     */
    public void showConnectionError() {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
    }

    /**
     * Notified when user registration started after social login.
     */
    @Override
    public void onUserRegistrationStarted() {
        /**
         * start showing dialog when user registration starts
         */
        materialDialog = MaterialDesignDialog.newInstance(this)
                .createScanningDialog(getString(R.string.user_registration_loading_message));
        materialDialog.show();
    }

    /**
     * Will be notified when user registration completed.
     *
     * @param user
     */
    @Override
    public void onUserRegistered(User user) {

        //dismiss dialog when user registration completed
        if (materialDialog != null) {
            materialDialog.cancel();
        }

        //Register auth info to shared preferences
        UserManager.getInstance(getApplicationContext()).userLoggedIn(user.getToken(), user.getAuth_id());

        //start main activity.
        Intent i = new Intent(this, MainActivity.class);
        finish();
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        // Unregister listener no need anymore.
        Api.getInstance(getApplicationContext()).unregisterRegistrationListener();
        super.onDestroy();
    }
}