package co.mobiwise.indircom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import co.mobiwise.indircom.activity.TwitterLoginActivity;
import co.mobiwise.indircom.listener.TwitterAuthListener;
import co.mobiwise.indircom.utils.Constants;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
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
        twitterLogin(Constants.TWITTER_CONSUMER_KEY, Constants.TWITTER_CONSUMER_SECRET);
    }


    /**
     * method that handles twitter login
     *
     * @param consumerKey
     * @param consumerSecret
     */
    private void twitterLogin(String consumerKey, String consumerSecret) {
        /*
        // Setup builder
        ConfigurationBuilder builder = new ConfigurationBuilder();
        // Get key and secret
        builder.setOAuthConsumerKey(consumerKey);
        builder.setOAuthConsumerSecret(consumerSecret);
        // Build
        Configuration configuration = builder.build();
        TwitterFactory factory = new TwitterFactory(configuration);
        twitter = factory.getInstance();


        // Start new thread for activity
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    requestToken = twitter
                            .getOAuthRequestToken(Constants.TWITTER_CALLBACK_URL);
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri
                            .parse(requestToken.getAuthenticationURL())));

                    getActivity().startActivityFromFragment(TwitterLoginFragment.this, new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL())), 100);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start(); */

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ConfigurationBuilder confbuilder = new ConfigurationBuilder();
        Configuration conf = confbuilder
                .setOAuthConsumerKey(Constants.TWITTER_CONSUMER_KEY)
                .setOAuthConsumerSecret(Constants.TWITTER_CONSUMER_SECRET).build();
        mTwitter = new TwitterFactory(conf).getInstance();
        mTwitter.setOAuthAccessToken(null);
        try {
            mRequestToken = mTwitter.getOAuthRequestToken(Constants.CALLBACK_URL);
            Intent intent = new Intent(getActivity(), TwitterLoginActivity.class);
            intent.putExtra(Constants.IEXTRA_AUTH_URL,
                    mRequestToken.getAuthorizationURL());

            getActivity().startActivityFromFragment(TwitterLoginFragment.this, intent, 0);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("indircom", "onActivityResult- frgment");

    }
}