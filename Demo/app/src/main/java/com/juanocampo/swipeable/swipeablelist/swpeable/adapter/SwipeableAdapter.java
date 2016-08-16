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
    private List<RecyclerView.ViewHolder> swipeableViewHolders;
    private final LinearLayoutManager manager;
    private final RecyclerView recycler;

    public SwipeableAdapter(SwipeAdapterActions listener, RecyclerView recyclerView, LinearLayoutManager manager) {
        callback = new SwipeableHelperCallback(this, recyclerView);
        this.recycler = recyclerView;
        this.listener = listener;
        this.swipeableViewHolders = new ArrayList<>();
        this.manager = manager;
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder itemViewHolder = onCreateSwipeViewHolder(parent, viewType);
        swipeableViewHolders.add(itemViewHolder);
        return itemViewHolder;
    }

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
        int visiblePositionSelected = position - manager.findFirstVisibleItemPosition();
        if (listener != null) {
            listener.swiped(visiblePositionSelected);
        }
    }

    public void restoreSwipedItem(int restorePosition) {

        //recycler.findViewHolderForAdapterPosition(restorePosition);

        if (swipeableViewHolders != null && !swipeableViewHolders.isEmpty() && restorePosition < swipeableViewHolders.size()) {
            final RecyclerView.ViewHolder viewHolder = swipeableViewHolders.get(restorePosition);

            if (viewHolder != null && viewHolder instanceof SwipeableViewHolder) {
                SwipeableViewHolder swipeableViewHolder = (SwipeableViewHolder) viewHolder;

                Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.slide_back_swipeable_card);
                swipeableViewHolder.swipeableMainContainer.startAnimation(animation);
            }
        }
        callback.clearSwipedValues();
    }
}
