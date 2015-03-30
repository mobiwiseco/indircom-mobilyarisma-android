package co.mobiwise.indircom.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.views.RobotoTextView;

public class AboutFragment extends Fragment {

    private RobotoTextView mTextViewAppVersion;
    private ImageView mImageViewBack;

    public AboutFragment() {
    }

    /**
     * Static factory method that returns the
     * new fragment to the client.
     */
    public static AboutFragment newInstance() {
        AboutFragment aboutFragment = new AboutFragment();
        return aboutFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_about, container, false);

        mTextViewAppVersion = (RobotoTextView) rootView.findViewById(R.id.app_version);
        mImageViewBack = (ImageView) rootView.findViewById(R.id.imageview_about_back);
        mTextViewAppVersion.setText(getAppVersion());

        /**
         * listener for back button
         */
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return rootView;
    }

    /**
     * finding app version number in code
     *
     * @return app version. Really.
     */
    private String getAppVersion() {
        String appVersion;
        try {
            String pkg = getActivity().getApplicationContext().getPackageName();
            appVersion = getActivity().getApplicationContext().getPackageManager().getPackageInfo(pkg, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            appVersion = "-";
        }
        return appVersion;
    }
}
