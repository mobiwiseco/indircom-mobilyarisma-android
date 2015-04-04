package co.mobiwise.indircom.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.model.App;
import co.mobiwise.indircom.utils.Connectivity;
import co.mobiwise.indircom.views.RobotoMediumTextView;
import co.mobiwise.indircom.views.RobotoTextView;


public class AppDetailFragment extends Fragment implements View.OnClickListener {

    /**
     * The argument keys
     */
    private static final String ARG_APP = "app";

    /**
     * Views
     */
    private RoundedImageView imageviewAppDetailImage;
    private RobotoMediumTextView textviewAppName;
    private RobotoTextView textviewAppCategory, textviewAppDescription;
    private RelativeLayout layoutAppDownload;
    private ImageView imageViewBack;
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
        initializeView(rootView);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = getArguments().getParcelable("app");
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

        textviewAppName.setText(app.getAppName());
        textviewAppDescription.setText(app.getAppDescription());

        Picasso.with(getActivity().getApplicationContext()).load(app.getAppImageUrl()).into(imageviewAppDetailImage);

        layoutAppDownload.setOnClickListener(this);
        imageViewBack.setOnClickListener(this);
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
        return !TextUtils.isEmpty(downloadLink);
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
                    if (validateDownloadURL(app.getAppDownloadUrl())) {
                        /**
                         * startActivity
                         */
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getAppDownloadUrl())));
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
