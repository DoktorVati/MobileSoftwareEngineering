package com.zybooks.softwareengineeringexercise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Adapter class for displaying item data in the recyler view from activity_main
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<Integer> ids;
    private ArrayList<Integer> listIds;
    private ArrayList<String> names;

    // initializes the adapter with data
    public ItemAdapter(ArrayList<Integer> listIds, ArrayList<Integer> ids,  ArrayList<String> names) {
        this.listIds = listIds;
        this.ids = ids;
        this.names = names;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflates the item layout (item_row.xml) and creates a new ViewHolder for it

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(view);
    }

    // Binds the data to the ViewHolder
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        // Sets the text for each of the textviews to the values in the lists
        holder.idTextView.setText(String.valueOf(ids.get(position)));
        holder.listIdTextView.setText(String.valueOf(listIds.get(position)));
        holder.nameTextView.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

    // holds the references to the textviews
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView, listIdTextView, nameTextView;

        // Finds and stores the references to the textviews
        ItemViewHolder(View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idTextView);
            listIdTextView = itemView.findViewById(R.id.listIdTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }
}