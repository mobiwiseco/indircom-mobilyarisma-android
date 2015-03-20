package co.mobiwise.indircom.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.activity.TwitterLoginActivity;
import co.mobiwise.indircom.listener.TwitterAuthListener;
import co.mobiwise.indircom.utils.SocialConstants;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by mac on 17/03/15.
 */
public class TwitterLoginFragment extends Fragment {

    private TwitterAuthListener twitterAuthListener;
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
            twitterAuthListener = (TwitterAuthListener) getActivity();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("indircom", "onActivityResult- frgment" + requestCode);
        AccessToken accessToken = null;
        try {
            String oauthVerifier = data.getExtras().getString(
                    SocialConstants.IEXTRA_OAUTH_VERIFIER);
            accessToken = mTwitter.getOAuthAccessToken(mRequestToken,
                    oauthVerifier);
            //TODO: save access token!
            twitterAuthListener.onTwitterUserFetched(mTwitter);
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
            dialog = co.mobiwise.indircom.utils.MaterialDialog.newInstance(getActivity()).createScanningDialog(getActivity().getResources().getString(R.string.loading_page_message), getActivity());
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