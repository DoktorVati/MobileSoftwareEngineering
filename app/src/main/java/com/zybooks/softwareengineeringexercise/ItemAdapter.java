package com.zybooks.softwareengineeringexercise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<Integer> ids;
    private ArrayList<Integer> listIds;
    private ArrayList<String> names;

    public ItemAdapter(ArrayList<Integer> listIds, ArrayList<Integer> ids,  ArrayList<String> names) {
        this.listIds = listIds;
        this.ids = ids;
        this.names = names;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.idTextView.setText(String.valueOf(ids.get(position)));
        holder.listIdTextView.setText(String.valueOf(listIds.get(position)));
        holder.nameTextView.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView, listIdTextView, nameTextView;

        ItemViewHolder(View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idTextView);
            listIdTextView = itemView.findViewById(R.id.listIdTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }
}