package com.juanocampo.swipeable.swipeablelist.swpeable.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.juanocampo.swipeable.swipeablelist.R;
import com.juanocampo.swipeable.swipeablelist.swpeable.SwipeableHelperCallback;
import com.juanocampo.swipeable.swipeablelist.swpeable.viewholder.SwipeableViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan.ocampo on 05/08/2016.
 */

public abstract class SwipeableAdapter extends RecyclerView.Adapter {

    public interface SwipeAdapterActions {
        void swiped(int position);
    }

    private final SwipeableHelperCallback callback;
    private SwipeAdapterActions listener;
    private final LinearLayoutManager manager;
    private final RecyclerView recycler;

    public SwipeableAdapter(SwipeAdapterActions listener, RecyclerView recyclerView, LinearLayoutManager manager) {
        callback = new SwipeableHelperCallback(this, recyclerView);
        this.recycler = recyclerView;
        this.listener = listener;
        this.manager = manager;
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * Called when an item has been dismissed by a swipe.<br/>
     * <br/>
     * Implementations should call {@link RecyclerView.Adapter#notifyItemRemoved(int)} after
     * adjusting the underlying data to reflect this removal.
     *
     * @param position The position of the item dismissed.
     *
     * @see RecyclerView#getAdapterPositionFor(RecyclerView.ViewHolder)
     * @see RecyclerView.ViewHolder#getAdapterPosition()
     */
    public void onItemDismiss(int position) {
        //int visiblePositionSelected = position - manager.findFirstVisibleItemPosition();
        if (listener != null) {
            listener.swiped(position);
        }
    }

    public void restoreSwipedItem(int restorePosition) {

        final RecyclerView.ViewHolder viewHolder = recycler.findViewHolderForAdapterPosition(restorePosition);
        if (viewHolder != null && viewHolder instanceof SwipeableViewHolder) {
            SwipeableViewHolder swipeableViewHolder = (SwipeableViewHolder) viewHolder;

            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.slide_back_swipeable_card);
            swipeableViewHolder.swipeableMainContainer.startAnimation(animation);
        }
        callback.clearSwipedValues();
    }

    public RecyclerView getRecycler() {
        return recycler;
    }

    public LinearLayoutManager getManager() {
        return manager;
    }
}
