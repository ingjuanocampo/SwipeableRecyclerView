package com.juanocampo.swipeable.swipeablelist.swpeable;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juanocampo.swipeable.swipeablelist.R;
import com.juanocampo.swipeable.swipeablelist.backlistener.OnBackListener;
import com.juanocampo.swipeable.swipeablelist.fragment.swipeable_implementation.RecyclerListAdapter;
import com.juanocampo.swipeable.swipeablelist.swpeable.adapter.SwipeableAdapter;
import com.juanocampo.swipeable.swipeablelist.swpeable.fragment.SwipeableFragment;

/**
 * Created by juan.ocampo on 09/08/2016.
 */

public abstract class WrapperHolder extends Fragment implements RecyclerListAdapter.SwipeAdapterActions,  OnBackListener {

    private static final int NO_POSITION_CACHED = -1;
    private boolean isPendingBackTransition;

    private int lastSelectedPosition = NO_POSITION_CACHED;
    //private Fragment fragmentNext;
    private View mainFragmentContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.swipeable_viewswitcher, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!(getSwipeableMainFragment() instanceof SwipeableFragment)) {
            throw new ClassCastException("The main child fragment MUST implement SwipeableFragment");
        }

        getChildFragmentManager().beginTransaction().replace(R.id.swipeable_container, getSwipeableMainFragment())
                            .commitAllowingStateLoss();

        mainFragmentContainer = getView().findViewById(R.id.swipeable_container);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkForPendingAnimation();
    }

    private void checkForPendingAnimation() {
        if (isPendingBackTransition ) {

            if (getChildFragmentManager().getFragments()!= null && !getChildFragmentManager().getFragments().isEmpty() &&
                    getChildFragmentManager().getFragments().get(0) instanceof SwipeableFragment) {
                SwipeableAdapter adapter = ((SwipeableFragment)getChildFragmentManager().getFragments().get(0)).getSwipeableAdapter();
                if (adapter != null) {
                    adapter.restoreSwipedItem(lastSelectedPosition);
                }
            }

            lastSelectedPosition = NO_POSITION_CACHED;

        }
    }

    protected abstract Fragment getSwipeableMainFragment();

    protected abstract Intent getSwipeableNextFragment(int lastSelectedPosition);

    @Override
    public void swiped(int position) {
        lastSelectedPosition = position;

        isPendingBackTransition = true;
        startActivityForResult(getSwipeableNextFragment(lastSelectedPosition), 0);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public boolean onBackFragmentPressed() {
        boolean isHandleLocal = false;

        return isHandleLocal;
    }

}
