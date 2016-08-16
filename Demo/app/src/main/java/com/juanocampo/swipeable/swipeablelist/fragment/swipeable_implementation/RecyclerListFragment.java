package com.juanocampo.swipeable.swipeablelist.fragment.swipeable_implementation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
public class RecyclerListFragment extends SwipeableFragment implements SwipeableAdapter.SwipeAdapterActions {

    private SwipeableAdapter adapter;

    public RecyclerListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        /*if (adapter == null) {
            adapter = new RecyclerListAdapter(getActivity(), this, recyclerView);
        } else {
            adapter.attachToRecycler(recyclerView);
        }*/

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        adapter = new RecyclerListAdapter(getActivity(), this, recyclerView, manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

    }


    @NonNull
    @Override
    protected SwipeableAdapter getSwipeableAdapter() {
        return adapter;
    }
}
