package com.juanocampo.swipeable.swipeablelist.fragment.swipeable_implementation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juanocampo.swipeable.swipeablelist.R;
import com.juanocampo.swipeable.swipeablelist.swpeable.adapter.SwipeableAdapter;
import com.juanocampo.swipeable.swipeablelist.swpeable.fragment.SwipeableFragment;

/**
 * @author juan.ocampo
 */
public class RecyclerListFragment extends Fragment implements SwipeableFragment {

    private SwipeableAdapter adapter;

    public RecyclerListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View containerView = inflater.inflate(R.layout.recycler_view, container, false);
        RecyclerView recyclerView = (RecyclerView) containerView.findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        adapter = new RecyclerListAdapter(getActivity(), getSwipeableActionListener(), recyclerView, manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);


        return containerView;
    }

    @NonNull
    @Override
    public SwipeableAdapter getSwipeableAdapter() {
        return adapter;
    }

    @Override
    public SwipeableAdapter.SwipeAdapterActions getSwipeableActionListener() {
        return (SwipeableAdapter.SwipeAdapterActions) getParentFragment();
    }
}
