package com.juanocampo.swipeable.swipeablelist.swpeable;

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

    private int lastSelectedPosition = NO_POSITION_CACHED;
    private Fragment fragmentNext;

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
    }

    protected abstract Fragment getSwipeableMainFragment();

    protected abstract Fragment getSwipeableNextFragment(int lastSelectedPosition);

    @Override
    public void swiped(int position) {
        lastSelectedPosition = position;
        fragmentNext = getSwipeableNextFragment(lastSelectedPosition);
        getChildFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.swipeable_container, fragmentNext)
                            .addToBackStack(fragmentNext.getClass().getSimpleName())
                            .commitAllowingStateLoss();
    }

    @Override
    public boolean onBackFragmentPressed() {
        boolean isHandleLocal = false;

        if (fragmentNext!= null && fragmentNext.isResumed() && fragmentNext.isVisible()
                && getChildFragmentManager().popBackStackImmediate()) {

            getView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getChildFragmentManager().getFragments()!= null && !getChildFragmentManager().getFragments().isEmpty() &&
                            getChildFragmentManager().getFragments().get(0) instanceof SwipeableFragment) {
                        SwipeableAdapter adapter = ((SwipeableFragment)getChildFragmentManager().getFragments().get(0)).getSwipeableAdapter();
                        if (adapter != null) {
                            adapter.restoreSwipedItem(lastSelectedPosition);
                        }
                    }
                    lastSelectedPosition = NO_POSITION_CACHED;
                }
            }, getResources().getInteger(R.integer.sort_delaytime));

            isHandleLocal = true;
        }
        return isHandleLocal;
    }

}
