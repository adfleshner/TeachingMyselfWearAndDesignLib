package com.flesh.understandingwear;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by afleshner on 6/11/2015.
 */
public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private ArrayList<String> items;
    private onItemClickListener mController;
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public interface onItemClickListener {
        void onClick(String listItemClicked);
    }

    public MyListAdapter(ArrayList items) {
        this.items = items;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.mController = listener;
    }

    public void add(String item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }
    public void add(String item) {
        items.add(items.size(), item);
        notifyItemInserted(items.size());
    }

    public void remove(String item) throws ArrayIndexOutOfBoundsException{
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell, parent, false));
    }

    private String getItem(int postion) {
        return items.get(postion);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String string = getItem(position);
        holder.title.setText(string);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.onClick(string);
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, subtitle;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listTitle);
            subtitle = (TextView) itemView.findViewById(R.id.listSubTitle);
        }
    }
}
