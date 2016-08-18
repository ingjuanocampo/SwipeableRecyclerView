package com.juanocampo.swipeable.swipeablelist.swpeable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.juanocampo.swipeable.swipeablelist.R;
import com.juanocampo.swipeable.swipeablelist.backlistener.OnBackListener;
import com.juanocampo.swipeable.swipeablelist.fragment.swipeable_implementation.RecyclerListAdapter;
import com.juanocampo.swipeable.swipeablelist.swpeable.adapter.SwipeableAdapter;
import com.juanocampo.swipeable.swipeablelist.swpeable.fragment.SwipeableFragment;


/**
 * Created by juanocampo on 8/18/16.
 */
public abstract class WrrapperViewPagerHolder extends Fragment implements RecyclerListAdapter.SwipeAdapterActions, OnBackListener {
    private static final int NO_POSITION_CACHED = -1;

    private int lastSelectedPosition = NO_POSITION_CACHED;

    private ViewPagerAdapter adapter;
    private ViewPager pager;
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

        pager = (ViewPager) getView().findViewById(R.id.swipeable_container);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
    }

    @Override
    public void swiped(int position) {
        Toast.makeText(getView().getContext(), "Swipe", Toast.LENGTH_SHORT).show();
        pager.setCurrentItem(1);
        lastSelectedPosition = position;
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Fragment fragment;
            switch(pos) {
                case 0:
                    fragment = getSwipeableMainFragment();
                    break;
                case 1:
                    fragment = getSwipeableNextFragment(0);
                    break;
                default:
                    fragment = getSwipeableMainFragment();
                    fragmentNext = fragment;
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    protected abstract Fragment getSwipeableMainFragment();

    protected abstract Fragment getSwipeableNextFragment(int lastSelectedPosition);

    @Override
    public boolean onBackFragmentPressed() {
        boolean isHandleLocal = false;

        if (pager.getCurrentItem() == 1) {
            pager.setCurrentItem(0);

            if (getChildFragmentManager().getFragments()!= null && !getChildFragmentManager().getFragments().isEmpty() &&
                    getChildFragmentManager().getFragments().get(0) instanceof SwipeableFragment) {
                SwipeableAdapter adapter = ((SwipeableFragment)getChildFragmentManager().getFragments().get(0)).getSwipeableAdapter();
                if (adapter != null) {
                    adapter.restoreSwipedItem(lastSelectedPosition);
                }
            }
            lastSelectedPosition = NO_POSITION_CACHED;

            isHandleLocal = true;
        }
        return isHandleLocal;
    }

}
