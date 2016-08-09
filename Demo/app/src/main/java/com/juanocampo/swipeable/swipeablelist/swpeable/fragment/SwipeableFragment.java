package com.juanocampo.swipeable.swipeablelist.swpeable.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.juanocampo.swipeable.swipeablelist.fragment.swipeable_implementation.RecyclerListAdapter;
import com.juanocampo.swipeable.swipeablelist.swpeable.adapter.SwipeableHelperAdapter;

/**
 * Created by juan.ocampo on 09/08/2016.
 */

public abstract class SwipeableFragment extends Fragment implements RecyclerListAdapter.SwipeAdapterActions{

    public interface SwipeFragmentAction {
        void swiped(int position);
    }

    private SwipeFragmentAction listener;
    private SwipeableHelperAdapter adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = getSwipeableAdapter();
        if (adapter == null) {
            throw new NullPointerException("Swipeable adapter MUST not be null");
        }
    }

    protected abstract @NonNull SwipeableHelperAdapter getSwipeableAdapter();

    public SwipeableHelperAdapter getAdapter() {
        return adapter;
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
