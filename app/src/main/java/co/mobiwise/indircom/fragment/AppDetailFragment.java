package co.mobiwise.indircom.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.utils.Connectivity;
import co.mobiwise.indircom.views.RobotoMediumTextView;
import co.mobiwise.indircom.views.RobotoTextView;


public class AppDetailFragment extends Fragment implements View.OnClickListener {

    /**
     * Views
     */
    private RoundedImageView imageviewAppDetailImage;
    private RobotoMediumTextView textviewAppName;
    private RobotoTextView textviewAppCategory, textviewAppDescription;
    private RelativeLayout layoutAppDownload;
    private ImageView imageViewBack;

    /**
     * The argument keys
     */
    public static final String APP_NAME = "appName";
    public static final String APP_DESCRIPTION = "appDescription";
    public static final String APP_DOWNLOAD_LINK = "appDownloadLink";
    public static final String APP_IMAGE_DOWNLOAD_LINK = "appImageDownloadLink";

    /**
     * The argument values
     */
    private String mAppName;
    private String mAppDescription;
    private String mAppDownloadLink;
    private String mAppImageDownloadLink;

    public AppDetailFragment() {
    }

    /**
     * Static factory method that returns the
     * new fragment to the client.
     */
    public static AppDetailFragment newInstance(String appName, String mAppDescription, String appDownloadLink, String appImageDownloadURL) {
        AppDetailFragment appDetailFragment = new AppDetailFragment();
        Bundle args = new Bundle();
        args.putString(APP_NAME, appName);
        args.putString(APP_DESCRIPTION, mAppDescription);
        args.putString(APP_DOWNLOAD_LINK, appDownloadLink);
        args.putString(APP_IMAGE_DOWNLOAD_LINK, appImageDownloadURL);
        appDetailFragment.setArguments(args);
        return appDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_app_detail, container, false);
        initializeView(rootView);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppName = getArguments().getString(APP_NAME);
        mAppDescription = getArguments().getString(APP_DESCRIPTION);
        mAppDownloadLink = getArguments().getString(APP_DOWNLOAD_LINK);
        mAppImageDownloadLink = getArguments().getString(APP_IMAGE_DOWNLOAD_LINK);
    }

    /**
     * initialize views
     *
     * @param view
     */
    private void initializeView(View view) {

        imageviewAppDetailImage = (RoundedImageView) view.findViewById(R.id.imageview_app_detail_image);
        textviewAppName = (RobotoMediumTextView) view.findViewById(R.id.textview_app_name);
        textviewAppCategory = (RobotoTextView) view.findViewById(R.id.textview_app_category);
        textviewAppDescription = (RobotoTextView) view.findViewById(R.id.textview_app_description);
        layoutAppDownload = (RelativeLayout) view.findViewById(R.id.layout_app_download);
        imageViewBack = (ImageView) view.findViewById(R.id.imageview_back);

        textviewAppName.setText(mAppName);
        textviewAppDescription.setText(mAppDescription);

        Picasso.with(getActivity().getApplicationContext()).load(mAppImageDownloadLink).into(imageviewAppDetailImage);

        layoutAppDownload.setOnClickListener(this);
        imageViewBack.setOnClickListener(this);

    }

    /**
     * @param downloadLink app Google Play Store or App Store download Link
     * @return
     */
    public boolean validateDownloadURL(String downloadLink) {
        //TODO should check the URL
        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            /**
             *  listener method for imageviewAppDetailImage. Opens Google Play App Page.
             */
            case R.id.layout_app_download:
                /**
                 * check the internet connection
                 */
                if (Connectivity.isConnected(getActivity().getApplicationContext())) {
                    if (validateDownloadURL(mAppDownloadLink)) {
                        /**
                         * startActivity
                         */
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + mAppDownloadLink)));
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.cannot_download_app), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                }
                break;
            /**
             * listener method for imageview_back. Back the previous fragment
             */

            case R.id.imageview_back:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
