package com.juanocampo.swipeable.swipeablelist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.juanocampo.swipeable.swipeablelist.backlistener.OnBackListener;
import com.juanocampo.swipeable.swipeablelist.fragment.MainFragment;
import com.juanocampo.swipeable.swipeablelist.fragment.swipeable_implementation.Holder;

import java.util.List;

/**
 * @author @juan.ocampo
 */
public class MainActivity extends AppCompatActivity implements MainFragment.OnListItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            MainFragment fragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, fragment)
                    .commit();
        }
    }

    @Override
    public void onListItemClick(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Holder();
                break;
        }

        getSupportFragmentManager().beginTransaction()
                                   .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                                   .replace(R.id.content, fragment)
                                   .addToBackStack(null)
                                   .commit();
    }


    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments!= null && !fragments.isEmpty()) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof OnBackListener) {
                    if (!((OnBackListener) fragment).onBackFragmentPressed()) {
                        super.onBackPressed();
                    }
                }
            }

        } else {
            super.onBackPressed();
        }

    }
}
