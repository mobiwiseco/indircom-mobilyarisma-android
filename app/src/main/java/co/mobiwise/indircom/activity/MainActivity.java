package co.mobiwise.indircom.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.fragment.MainVotingPageFragment;
import co.mobiwise.indircom.listener.ConnectivityChangeListener;


public class MainActivity extends ActionBarActivity implements ConnectivityChangeListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, MainVotingPageFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onConnectionLost() {

    }

    @Override
    public void onConnectionFind() {

    }
}
