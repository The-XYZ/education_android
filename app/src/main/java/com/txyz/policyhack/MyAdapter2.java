package com.txyz.policyhack;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by naman on 18/04/15.
 */
public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {
    ArrayList<DistrictData> itemsData;
    private Context context;

    private int lastPosition = -1;

    public MyAdapter2(Context context, ArrayList<DistrictData> itemsData) {
        this.itemsData = itemsData;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_district, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        setAnimation(viewHolder.itemView, position);

        viewHolder.disname.setText(itemsData.get(position).getDistname());
        viewHolder.imgViewIcon.setImageResource(itemsData.get(position).getImageUrl());
        viewHolder.state.setText(itemsData.get(position).getStatename());
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView disname, state;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            disname = (TextView) itemLayoutView.findViewById(R.id.item_title);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
            state = (TextView) itemLayoutView.findViewById(R.id.item_block);
        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}

