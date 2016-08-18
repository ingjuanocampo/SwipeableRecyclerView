package com.juanocampo.swipeable.swipeablelist.swpeable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    private View mainFragmentContainer;
    private final static int PARALLAX_VALUE = 10;

    private float downX = 0;
    private float currentXTranslation;

    private final View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    Log.e("downX", " "+ downX);

                    break;
                case MotionEvent.ACTION_UP:
                    if (downX != 0 && downX < event.getX()) {
                        downX = 0;
                        currentXTranslation = 0;
                        onBackFragmentPressed();
                        Log.e("event.getX()", " "+ event.getX());
                    }
                    break;
            }

            if (downX != 0 && downX < event.getX()) {
                Log.e("currentXTranslation", " "+ currentXTranslation);
                currentXTranslation = event.getX();
                mainFragmentContainer.setLeft((int) ((currentXTranslation - downX)/ PARALLAX_VALUE));
            }
            return true;
        }
    };


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

    protected abstract Fragment getSwipeableMainFragment();

    protected abstract Fragment getSwipeableNextFragment(int lastSelectedPosition);

    @Override
    public void swiped(int position) {
        lastSelectedPosition = position;
        fragmentNext = getSwipeableNextFragment(lastSelectedPosition);
        mainFragmentContainer.setOnTouchListener(touchListener);
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
            mainFragmentContainer.setOnTouchListener(null);

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
                    mainFragmentContainer.setLeft(0);

                    lastSelectedPosition = NO_POSITION_CACHED;
                }
            }, getResources().getInteger(R.integer.sort_delaytime));

            isHandleLocal = true;
        }
        return isHandleLocal;
    }

}
