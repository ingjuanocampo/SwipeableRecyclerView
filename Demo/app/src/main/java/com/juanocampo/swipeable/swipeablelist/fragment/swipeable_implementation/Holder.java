package com.juanocampo.swipeable.swipeablelist.fragment.swipeable_implementation;

import android.support.v4.app.Fragment;

import com.juanocampo.swipeable.swipeablelist.swpeable.WrapperHolder;
import com.juanocampo.swipeable.swipeablelist.swpeable.fragment.SwipeableFragment;

/**
 * Created by juan.ocampo on 09/08/2016.
 */

public class Holder extends WrapperHolder {

    @Override
    protected Fragment getSwipeableMainFragment() {
        return new RecyclerListFragment();
    }

    @Override
    protected Fragment getSwipeableNextFragment(int lastSelectedPosition) {
        return new BlankFragment();
    }
}
