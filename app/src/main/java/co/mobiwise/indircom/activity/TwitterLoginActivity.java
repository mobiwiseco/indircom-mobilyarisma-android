package co.mobiwise.indircom.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.utils.SocialConstants;

/**
 * Created by mac on 19/03/15.
 */
public class TwitterLoginActivity extends ActionBarActivity {

    WebView webView;
    WebSettings webSettings;
    public static final String TAG = TwitterLoginActivity.class.getSimpleName();


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_twitter_login);

        webView = (WebView) findViewById(R.id.twitter);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                boolean result = true;
                if (url != null && url.startsWith(SocialConstants.CALLBACK_URL)) {
                    Uri uri = Uri.parse(url);
                    if (uri.getQueryParameter("denied") != null) {
                        setResult(RESULT_CANCELED);
                        finish();
                    } else {
                        String oauthToken = uri.getQueryParameter("oauth_token");
                        String oauthVerifier = uri.getQueryParameter("oauth_verifier");

                        Intent intent = getIntent();
                        intent.putExtra(SocialConstants.IEXTRA_OAUTH_TOKEN, oauthToken);
                        intent.putExtra(SocialConstants.IEXTRA_OAUTH_VERIFIER, oauthVerifier);

                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else {
                    result = super.shouldOverrideUrlLoading(view, url);
                }
                return result;
            }
        });
        webView.loadUrl(this.getIntent().getExtras().getString("auth_url"));
    }
}
