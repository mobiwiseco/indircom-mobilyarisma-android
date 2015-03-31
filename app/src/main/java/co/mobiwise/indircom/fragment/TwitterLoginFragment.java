package co.mobiwise.indircom.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.activity.TwitterLoginActivity;
import co.mobiwise.indircom.listener.SocialAuthListener;
import co.mobiwise.indircom.utils.MaterialDesignDialog;
import co.mobiwise.indircom.utils.SocialConstants;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterLoginFragment extends Fragment {

    private SocialAuthListener socialAuthListener;
    private Twitter mTwitter;
    private RequestToken mRequestToken;


    public TwitterLoginFragment() {
    }

    /**
     * Static factory method that returns the
     * new fragment to the client.
     */
    public static TwitterLoginFragment newInstance() {
        TwitterLoginFragment twitterLoginFragment = new TwitterLoginFragment();
        return twitterLoginFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            socialAuthListener = (SocialAuthListener) getActivity();
        } catch (ClassCastException e) {
            throw new RuntimeException("The activity must implement TwitterAuthListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        twitterLogin(SocialConstants.TWITTER_CONSUMER_KEY, SocialConstants.TWITTER_CONSUMER_SECRET);
    }


    /**
     * method that handles twitter login
     *
     * @param consumerKey
     * @param consumerSecret
     */
    private void twitterLogin(String consumerKey, String consumerSecret) {
        new TwitterAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        final AccessToken[] accessToken = {null};
        final String[] oauthVerifier = new String[1];
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    oauthVerifier[0] = data.getExtras().getString(
                            SocialConstants.IEXTRA_OAUTH_VERIFIER);
                    try {
                        accessToken[0] = mTwitter.getOAuthAccessToken(mRequestToken,
                                oauthVerifier[0]);
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }

                    try {
                        //Gets twitter user info
                        User user_4j = mTwitter.showUser(mTwitter.getScreenName());

                        //Creates user data model
                        co.mobiwise.indircom.model.User user_model = new co.mobiwise.indircom.model.User();
                        user_model.setAuth_id(String.valueOf(user_4j.getId()));
                        user_model.setName(user_4j.getName().substring(0, user_4j.getName().indexOf(" ")));
                        user_model.setSurname(user_4j.getName().substring(user_4j.getName().indexOf(" ")));

                        //notify @TwitterAuthListener by user data model
                        socialAuthListener.onSocialUserFetched(user_model);

                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Task for opening twitter webview.
     */
    class TwitterAsyncTask extends AsyncTask<Void, Void, Void> {
        MaterialDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = MaterialDesignDialog.newInstance(getActivity()).createScanningDialog(getString(R.string.loading_page_message));
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            ConfigurationBuilder confbuilder = new ConfigurationBuilder();
            Configuration conf = confbuilder
                    .setOAuthConsumerKey(SocialConstants.TWITTER_CONSUMER_KEY)
                    .setOAuthConsumerSecret(SocialConstants.TWITTER_CONSUMER_SECRET).build();
            mTwitter = new TwitterFactory(conf).getInstance();
            mTwitter.setOAuthAccessToken(null);
            try {
                mRequestToken = mTwitter.getOAuthRequestToken(SocialConstants.CALLBACK_URL);
                Intent intent = new Intent(getActivity(), TwitterLoginActivity.class);
                intent.putExtra(SocialConstants.IEXTRA_AUTH_URL,
                        mRequestToken.getAuthorizationURL());
                if (dialog != null)
                    dialog.cancel();
                startActivityForResult(intent, 0);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}


