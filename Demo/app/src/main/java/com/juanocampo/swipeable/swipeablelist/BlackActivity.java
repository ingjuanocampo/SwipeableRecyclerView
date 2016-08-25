package com.juanocampo.swipeable.swipeablelist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.juanocampo.swipeable.swipeablelist.fragment.swipeable_implementation.BlankFragment;

/**
 * Created by juan.ocampo on 25/08/2016.
 */

public class BlackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            BlankFragment fragment = new BlankFragment();
            getSupportFragmentManager().beginTransaction()
                                       .add(R.id.content, fragment)
                                       .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);


    }
}
