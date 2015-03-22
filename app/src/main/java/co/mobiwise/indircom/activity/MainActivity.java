package co.mobiwise.indircom.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.fragment.MainVotingPageFragment;
import co.mobiwise.indircom.fragment.VotingActionFragment;
import co.mobiwise.indircom.listener.ConnectivityChangeListener;
import co.mobiwise.indircom.listener.PagerCurrentItemListener;


public class MainActivity extends ActionBarActivity implements ConnectivityChangeListener, PagerCurrentItemListener {

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

    @Override
    public void setCurrentPage(int position) {
        MainVotingPageFragment.setCurrentItem(position);
    }
}
