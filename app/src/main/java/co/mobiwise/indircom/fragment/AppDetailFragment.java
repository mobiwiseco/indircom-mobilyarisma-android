package co.mobiwise.indircom.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import co.mobiwise.indircom.R;
import co.mobiwise.indircom.api.ApiConstants;
import co.mobiwise.indircom.model.App;
import co.mobiwise.indircom.utils.Connectivity;
import co.mobiwise.indircom.views.RobotoMediumTextView;
import co.mobiwise.indircom.views.RobotoTextView;


public class AppDetailFragment extends Fragment {

    /**
     * The argument keys
     */
    private static final String ARG_APP = "app";
    private static final String TAG = "AppDetailFragment";

    /**
     * Views
     */
    @InjectView(R.id.imageview_app_detail_image)
    RoundedImageView imageviewAppDetailImage;

    @InjectView(R.id.textview_app_name)
    RobotoMediumTextView textviewAppName;

    @InjectView(R.id.textview_app_description)
    RobotoTextView textviewAppDescription;

    @InjectView(R.id.layout_app_download)
    RelativeLayout layoutAppDownload;

    @InjectView(R.id.imageview_back)
    ImageView imageViewBack;

    @InjectView(R.id.icon_download)
    ImageView imageViewIconDownload;

    @InjectView(R.id.textview_app_download)
    TextView textviewAppDownload;

    /**
     * Passed app
     */
    private App app;

    public AppDetailFragment() {
    }

    /**
     * Static factory method that returns the
     * new fragment to the client.
     */
    public static AppDetailFragment newInstance(Parcelable parcelable) {
        AppDetailFragment appDetailFragment = new AppDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_APP, parcelable);
        appDetailFragment.setArguments(args);
        return appDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_app_detail, container, false);
        ButterKnife.inject(this, rootView);

        textviewAppName.setText(app.getAppName());
        textviewAppDescription.setText(app.getAppDescription());

        /**
         * if URL is not validated by method, all the views should be gone.
         */
        Log.v(TAG, String.valueOf(app.getAppDownloadUrl()));
        if (!validateDownloadURL(app.getAppDownloadUrl())) {
            layoutAppDownload.setVisibility(View.GONE);
            imageViewIconDownload.setVisibility(View.GONE);
            textviewAppDownload.setVisibility(View.GONE);
        }

        Picasso.with(getActivity().getApplicationContext()).load(app.getAppImageUrl()).into(imageviewAppDetailImage);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = getArguments().getParcelable("app");
    }

    /**
     * if downloadLink is null, that means it is only avaiable for iOS, then return false.
     * if downloadLink is not null, that means it is avaiable for Android. then return true.
     * All the structure build by information getting from api for this app
     *
     * @param downloadLink app Google Play Store or App Store download Link
     * @return
     */
    public boolean validateDownloadURL(String downloadLink) {
        return !TextUtils.isEmpty(downloadLink) && !ApiConstants.APP_DOWNLOAD_LINK_NULL_CONSTANT.equals(downloadLink);
    }

    @OnClick({R.id.layout_app_download, R.id.imageview_back})
    public void downloadApp(View v) {
        switch (v.getId()) {
            /**
             *  listener method for imageviewAppDetailImage. Opens Google Play App Page.
             */
            case R.id.layout_app_download:
                /**
                 * check the internet connection
                 */
                if (Connectivity.isConnected(getActivity().getApplicationContext())) {
                    /**
                     * startActivity
                     */
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getAppDownloadUrl())));
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
