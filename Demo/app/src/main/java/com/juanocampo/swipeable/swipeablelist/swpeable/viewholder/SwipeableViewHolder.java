package com.juanocampo.swipeable.swipeablelist.swpeable.viewholder;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.juanocampo.swipeable.swipeablelist.R;


/**
 * Created by juan.ocampo on 29/07/2016.
 */

public abstract class SwipeableViewHolder extends RecyclerView.ViewHolder{

    public final View swipeableViewLayout;
    public final View swipeableMainContainer;

    public SwipeableViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.swipeable_item_indicator, parent, false));

        swipeableViewLayout = itemView.findViewById(R.id.swipeable_layout);
        swipeableMainContainer = itemView.findViewById(R.id.swipeable_parent_container);

        FrameLayout parentContainer = (FrameLayout) itemView.findViewById(R.id.swipeable_container);
        View swipeableViewIndicatorParent = LayoutInflater.from(itemView.getContext()).inflate(getViewHolderType(), parentContainer, false);
        parentContainer.addView(swipeableViewIndicatorParent);
    }

    protected abstract @LayoutRes
    int getViewHolderType();

}
