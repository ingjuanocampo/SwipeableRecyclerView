package com.juanocampo.swipeable.swipeablelist.fragment.swipeable_implementation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juanocampo.swipeable.swipeablelist.swpeable.adapter.SwipeableHelperAdapter;
import com.juanocampo.swipeable.swipeablelist.swpeable.fragment.SwipeableFragment;

/**
 * @author juan.ocampo
 */
public class RecyclerListFragment extends SwipeableFragment implements SwipeableHelperAdapter.SwipeAdapterActions {

    private SwipeableHelperAdapter adapter;

    public RecyclerListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new RecyclerView(container.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view;
        adapter = new RecyclerListAdapter(getActivity(), this, recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @NonNull
    @Override
    protected SwipeableHelperAdapter getSwipeableAdapter() {
        return adapter;
    }
}
