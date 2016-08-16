package com.juanocampo.swipeable.swipeablelist.swpeable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juanocampo.swipeable.swipeablelist.R;
import com.juanocampo.swipeable.swipeablelist.backlistener.OnBackListener;
import com.juanocampo.swipeable.swipeablelist.swpeable.fragment.SwipeableFragment;

/**
 * Created by juan.ocampo on 09/08/2016.
 */

public abstract class WrapperHolder extends Fragment implements SwipeableFragment.SwipeFragmentAction, OnBackListener {

    private static final int NO_POSITION_CACHED = -1;

    private int lastSelectedPosition = NO_POSITION_CACHED;
    private SwipeableFragment fragmentSwipeable;
    private Fragment fragmentNext;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.swipeable_viewswitcher, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentSwipeable = getSwipeableMainFragment();

        fragmentSwipeable.onAttachSwipeListener(this);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.swipeable_container, fragmentSwipeable)
                            .commitAllowingStateLoss();
    }

    protected abstract SwipeableFragment getSwipeableMainFragment();

    protected abstract Fragment getSwipeableNextFragment();

    @Override
    public void swiped(int position) {
        lastSelectedPosition = position;
        fragmentNext = getSwipeableNextFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.swipeable_container, fragmentNext)
                            .addToBackStack(fragmentNext.getClass().getSimpleName())
                            .commitAllowingStateLoss();
    }

    @Override
    public boolean onBackFragmentPressed() {
        boolean isHandleLocal = false;

        if (fragmentNext!= null && fragmentNext.isResumed() && fragmentNext.isVisible()
                && getFragmentManager().popBackStackImmediate()) {

            getView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragmentSwipeable.getAdapter().restoreSwipedItem(lastSelectedPosition);
                    lastSelectedPosition = NO_POSITION_CACHED;
                }
            }, getResources().getInteger(R.integer.sort_delaytime));

            isHandleLocal = true;
        }
        return isHandleLocal;
    }

}
