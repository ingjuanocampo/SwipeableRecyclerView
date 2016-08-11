package com.juanocampo.swipeable.swipeablelist.fragment.swipeable_implementation;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juanocampo.swipeable.swipeablelist.R;
import com.juanocampo.swipeable.swipeablelist.swpeable.adapter.SwipeableHelperAdapter;
import com.juanocampo.swipeable.swipeablelist.swpeable.viewholder.SwipeableViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Simple RecyclerView.Adapter that implements {@link SwipeableHelperAdapter} to respond to move and
 * dismiss events from a {@link android.support.v7.widget.helper.ItemTouchHelper}.
 *
 * @author @juanocampo
 */
public class RecyclerListAdapter extends SwipeableHelperAdapter {



    private static final int PAR = 1;


    private final List<String> mItems = new ArrayList<>();

    private int IMPAR = 2;

    public RecyclerListAdapter(Context context, SwipeAdapterActions listener, RecyclerView recyclerView,LinearLayoutManager manager) {
        super(listener, recyclerView, manager);
        mItems.addAll(Arrays.asList(context.getResources().getStringArray(R.array.dummy_items)));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        RecyclerView.ViewHolder itemViewHolder = viewType == IMPAR ? new SwipeableItemViewHolder(parent) : new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        if (viewType == PAR) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.textView.setText(mItems.get(position));

        } else if (viewType == IMPAR) {
            SwipeableItemViewHolder swipeableViewHolder = (SwipeableItemViewHolder) holder;
            swipeableViewHolder.textView.setText(mItems.get(position));
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position%2==0 ? PAR : IMPAR ;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * Simple example of a view holder that implements {@link } and has a
     * "handle" view that initiates a drag event when touched.
     */
    public static class SwipeableItemViewHolder extends SwipeableViewHolder {

        public final TextView textView;
        public final ImageView handleView;

        public SwipeableItemViewHolder(ViewGroup viewGroup) {
            super(viewGroup);
            textView = (TextView) itemView.findViewById(R.id.text);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
        }

        @Override
        protected int getViewHolderType() {
            return R.layout.item_main;
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public final TextView textView;
        public final ImageView handleView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
        }
    }




}
