package co.mobiwise.indircom.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.utils.Connectivity;
import co.mobiwise.indircom.views.RobotoMediumTextView;
import co.mobiwise.indircom.views.RobotoTextView;

/**
 * Created by mac on 14/03/15.
 */
public class AppDetailFragment extends Fragment {

    private ImageView imageview_app_detail_image;
    private RobotoMediumTextView textview_app_name;
    private RobotoTextView textview_app_category, textview_app_description;
    private RelativeLayout layout_app_download;

    public AppDetailFragment() {
    }

    /**
     * Static factory method that returns the
     * new fragment to the client.
     */
    public static AppDetailFragment newInstance() {
        AppDetailFragment appDetailFragment = new AppDetailFragment();
        return appDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_app_detail, container, false);
        initializeView(rootView);
        return rootView;
    }


    /**
     * @param view
     */
    private void initializeView(View view) {

        imageview_app_detail_image = (ImageView) view.findViewById(R.id.imageview_app_detail_image);
        textview_app_name = (RobotoMediumTextView) view.findViewById(R.id.textview_app_name);
        textview_app_category = (RobotoTextView) view.findViewById(R.id.textview_app_category);
        textview_app_description = (RobotoTextView) view.findViewById(R.id.textview_app_description);
        layout_app_download = (RelativeLayout) view.findViewById(R.id.layout_app_download);

    }


    /**
     * Opens Google Play App Page. This method is bound from fragment_app_detail.xml.
     *
     * @param view
     */
    public void openGooglePlayPage(View view) {
        if (Connectivity.isConnected(getActivity().getApplicationContext())) {

        }
    }


}
