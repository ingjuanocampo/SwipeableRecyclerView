package com.juanocampo.swipeable.swipeablelist.swpeable.fragment;

import android.gesture.Prediction;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.juanocampo.swipeable.swipeablelist.fragment.swipeable_implementation.RecyclerListAdapter;
import com.juanocampo.swipeable.swipeablelist.swpeable.adapter.SwipeableAdapter;

/**
 * Created by juan.ocampo on 09/08/2016.
 */

public interface SwipeableFragment {

    /**
     * Make sure to initialize this in the Fragment#onCreateView method
     * to ensure the correct behavior
     *
     * @return SwipeableAdapter
     */
    SwipeableAdapter getSwipeableAdapter();

    /**
     * Returns the
     * @return
     */
    SwipeableAdapter.SwipeAdapterActions getSwipeableActionListener();
}
