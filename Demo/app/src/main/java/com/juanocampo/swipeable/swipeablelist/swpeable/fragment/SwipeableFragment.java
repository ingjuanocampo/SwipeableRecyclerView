package com.juanocampo.swipeable.swipeablelist.swpeable.fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.juanocampo.swipeable.swipeablelist.fragment.swipeable_implementation.RecyclerListAdapter;
import com.juanocampo.swipeable.swipeablelist.swpeable.adapter.SwipeableAdapter;

/**
 * Created by juan.ocampo on 09/08/2016.
 */

public abstract class SwipeableFragment extends Fragment implements RecyclerListAdapter.SwipeAdapterActions{

    public interface SwipeFragmentAction {
        void swiped(int position);
    }

    private SwipeFragmentAction listener;


    protected abstract @NonNull
    SwipeableAdapter getSwipeableAdapter();

    public SwipeableAdapter getAdapter() {
        return getSwipeableAdapter();
    }

    public void onAttachSwipeListener(SwipeFragmentAction listener) {
        this.listener = listener;
    }

    @Override
    public void swiped(int position) {
        if (listener != null) {
            listener.swiped(position);
        }
    }
}
