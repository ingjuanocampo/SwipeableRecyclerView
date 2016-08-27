package com.juanocampo.swipeable.swipeablelist.swpeable;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.juanocampo.swipeable.swipeablelist.swpeable.adapter.SwipeableAdapter;
import com.juanocampo.swipeable.swipeablelist.swpeable.viewholder.SwipeableViewHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by juan.ocampo on 05/08/2016.
 */

public class SwipeableHelperCallback extends ItemTouchHelper.Callback {


    private final RecyclerView recycler;
    private boolean isSwiped;
    private RecyclerView.ViewHolder lastSelectedViewHolder;
    private final SwipeableAdapter adapter;
    //private boolean isDragInLeftDirection;

    public SwipeableHelperCallback(SwipeableAdapter adapter, RecyclerView recyclerView) {
        this.adapter = adapter;
        this.recycler = recyclerView;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.LEFT, ItemTouchHelper.LEFT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //no-op
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //this.isDragInLeftDirection = dX < 0;
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && viewHolder instanceof SwipeableViewHolder /*&& isDragInLeftDirection*/) {
            SwipeableViewHolder swipeableViewHolder = (SwipeableViewHolder) viewHolder;
            swipeableViewHolder.getSwipeableMainContainer().setTranslationX(getSwipeXTranslation(dX));
            makeParallaxAnimation(dX, viewHolder);
        }
    }

    private void makeParallaxAnimation(float dX, RecyclerView.ViewHolder viewHolderDragging) {
        if (dX < 0) {
            translateOtherVisiblesViewHolders(dX/10, viewHolderDragging);
        }
    }

    private void translateOtherVisiblesViewHolders(float dX, RecyclerView.ViewHolder viewHolderDragging) {
        for (int i = adapter.getManager().findFirstVisibleItemPosition() ; i <= adapter.getManager().findLastVisibleItemPosition();  i ++) {
            RecyclerView.ViewHolder viewHolderAnimate = adapter.getRecycler().findViewHolderForAdapterPosition(i);
            if (viewHolderAnimate != null && viewHolderAnimate != viewHolderDragging) {
                viewHolderAnimate.itemView.setTranslationX(dX);
            }
        }
    }

    private void restoreViewHolders() {
        for (int i = 0 ; i <= adapter.getManager().getItemCount();  i ++) {
            RecyclerView.ViewHolder viewHolderAnimate = adapter.getRecycler().findViewHolderForAdapterPosition(i);
            if (viewHolderAnimate != null) {
                viewHolderAnimate.itemView.setTranslationX(0);
                if (viewHolderAnimate instanceof SwipeableViewHolder) {
                    SwipeableViewHolder swipeableViewHolder = (SwipeableViewHolder) viewHolderAnimate;
                    swipeableViewHolder.getSwipeableMainContainer().setTranslationX(0);
                }
            }
        }
    }

    public void restoreParrlaxAnimation(RecyclerView.ViewHolder viewHolderDragging) {
        translateOtherVisiblesViewHolders(0, viewHolderDragging);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // We only want the active item to change
        if (actionState == ItemTouchHelper.ACTION_STATE_IDLE /*&& isDragInLeftDirection*/) {
            if (lastSelectedViewHolder instanceof SwipeableViewHolder && !isSwiped) {
                // Let the view holder know that this item is being moved or dragged

                adapter.notifyItemChanged(lastSelectedViewHolder.getAdapterPosition());
                restoreViewHolders();
                isSwiped = true;
                adapter.onItemDismiss(lastSelectedViewHolder.getAdapterPosition());
            }
        }
        this.lastSelectedViewHolder = viewHolder;

        super.onSelectedChanged(viewHolder, actionState);
    }

    private float getSwipeXTranslation(float dX) {
        return dX < 0 ? dX/5 : 0;
    }

    public void clearSwipedValues() {
        this.isSwiped = false;
    }
}
