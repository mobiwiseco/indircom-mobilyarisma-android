package co.mobiwise.indircom.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.fragment.MainVotingPageFragment;


public class MainActivity extends ActionBarActivity {

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

}
