package com.juanocampo.swipeable.swipeablelist.swpeable_adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.juanocampo.swipeable.swipeablelist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan.ocampo on 05/08/2016.
 */

public abstract class SwipeableHelperAdapter extends RecyclerView.Adapter {

    public interface SwipeAdapterActions {
        void swiped(int position);
    }

    private final SwipeableHelperCallback callback;
    private SwipeAdapterActions listener;
    private List<RecyclerView.ViewHolder> swipeableViewHolders;


    public SwipeableHelperAdapter(SwipeAdapterActions listener, RecyclerView recyclerView) {
        callback = new SwipeableHelperCallback(this);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        this.listener = listener;
        this.swipeableViewHolders = new ArrayList<>();
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder itemViewHolder = onCreateSwipeViewHolder(parent, viewType);
        swipeableViewHolders.add(itemViewHolder);
        return itemViewHolder;
    }

    @Override
    public final void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        onBindSwipeViewHolder(holder, position);
        if (position == callback.getLastSelectedPosition()) {
            restoreSwipedItem();
        }
    }

    protected abstract void onBindSwipeViewHolder(RecyclerView.ViewHolder holder, int position);

    /**
     * @param parent
     * @param viewType
     *
     * @return
     */
    protected abstract RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup parent, int viewType);

    public List<RecyclerView.ViewHolder> getSwipeableViewHolders() {
        return swipeableViewHolders;
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
        listener.swiped(position);
    }

    private void restoreSwipedItem() {
        final RecyclerView.ViewHolder viewHolder = swipeableViewHolders.get(callback.getLastSelectedPosition());

        if (viewHolder != null && viewHolder instanceof SwipeableViewHolder) {
            SwipeableViewHolder swipeableViewHolder = (SwipeableViewHolder) viewHolder;

            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.slide_back_swipeable_card);
            swipeableViewHolder.swipeableMainContainer.startAnimation(animation);
            callback.clearCached();
        }
    }
}
