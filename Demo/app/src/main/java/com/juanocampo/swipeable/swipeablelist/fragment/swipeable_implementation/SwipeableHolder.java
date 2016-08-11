package com.juanocampo.swipeable.swipeablelist.fragment.swipeable_implementation;

import android.support.v4.app.Fragment;

import com.juanocampo.swipeable.swipeablelist.swpeable.SwipeableWrapperHolder;
import com.juanocampo.swipeable.swipeablelist.swpeable.fragment.SwipeableFragment;

/**
 * Created by juan.ocampo on 09/08/2016.
 */

public class SwipeableHolder extends SwipeableWrapperHolder {

    @Override
    protected SwipeableFragment getSwipeableMainFragment() {
        return new RecyclerListFragment();
    }

    @Override
    protected Fragment getSwipeableNextFragment() {
        return new BlankFragment();
    }
}
