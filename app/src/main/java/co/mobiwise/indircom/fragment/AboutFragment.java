package co.mobiwise.indircom.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.mobiwise.indircom.R;

/**
 * Created by mac on 27/03/15.
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_about, container, false);

        initializeView(rootView);

        return rootView;
    }


    /**
     * initialize all view
     *
     * @param rootView rootView from ViewGroup
     */
    private void initializeView(ViewGroup rootView) {
        //TODO after getting UI details, fill this method
    }
}
