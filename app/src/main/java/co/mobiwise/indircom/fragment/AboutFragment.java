package co.mobiwise.indircom.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.mobiwise.indircom.R;

/**
 * Created by mac on 14/03/15.
 */
public class AboutFragment extends Fragment {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        return rootView;
    }
}
