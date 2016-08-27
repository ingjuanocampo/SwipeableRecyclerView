package com.juanocampo.swipeable.swipeablelist.fragment.swipeable_implementation;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.juanocampo.swipeable.swipeablelist.BlackActivity;
import com.juanocampo.swipeable.swipeablelist.swpeable.WrapperHolder;
import com.juanocampo.swipeable.swipeablelist.swpeable.WrrapperViewPagerHolder;
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
    protected Intent getSwipeableNextFragment(int lastSelectedPosition) {
        return new Intent(getContext(), BlackActivity.class);
    }
}
