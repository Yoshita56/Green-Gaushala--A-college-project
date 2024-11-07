package com.example.refining_gaushala_app.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.refining_gaushala_app.R;
import com.example.refining_gaushala_app.models.Bioplant;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.OrderViewHolder> {

    private List<Bioplant> bioplantList;
    private final OnStatusChangeListener onStatusChangeListener;  // Listener for status change events

    // Constructor to accept the list and the listener
    public GalleryAdapter(List<Bioplant> bioplantList, OnStatusChangeListener onStatusChangeListener) {
        this.bioplantList = bioplantList;
        this.onStatusChangeListener = onStatusChangeListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bioplant_request, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Bioplant bioplant = bioplantList.get(position);
        holder.tvBioplantName.setText(bioplant.getName());
        holder.tvDungAmount.setText("Requested Amount: " + bioplant.getDungRequested() + " kg");
        holder.tvDungType.setText("Dung Type: " + bioplant.getDungType());
        holder.tvRequestDate.setText("Request Date: " + bioplant.getDate());

        // Set click listeners for the accept and reject buttons
        holder.btnAccept.setOnClickListener(v ->
                onStatusChangeListener.onStatusChange(bioplant, "accepted") // Notify the listener for accept
        );

        holder.btnReject.setOnClickListener(v ->
                onStatusChangeListener.onStatusChange(bioplant, "rejected") // Notify the listener for reject
        );
    }

    @Override
    public int getItemCount() {
        return bioplantList.size();
    }

    // Method to update the list of Bioplants
    public void updateData(List<Bioplant> newBioplantList) {
        this.bioplantList = newBioplantList;
        notifyDataSetChanged();
    }

    // Method to remove Bioplant by position
    public void removeBioplant(int position) {
        if (position != -1) {
            bioplantList.remove(position);
            notifyItemRemoved(position);
        }
    }

    // ViewHolder for the RecyclerView
    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView tvBioplantName, tvDungAmount, tvDungType, tvRequestDate;
        Button btnAccept, btnReject;

        public OrderViewHolder(View itemView) {
            super(itemView);
            tvBioplantName = itemView.findViewById(R.id.tvBioplantName);
            tvDungAmount = itemView.findViewById(R.id.tvDungAmount);
            tvDungType = itemView.findViewById(R.id.tvDungType);
            tvRequestDate = itemView.findViewById(R.id.tvRequestDate);
            btnAccept = itemView.findViewById(R.id.btnAccept);  // Button to accept the request
            btnReject = itemView.findViewById(R.id.btnReject);  // Button to reject the request
        }
    }

    // Interface for handling button clicks to update status
    public interface OnStatusChangeListener {
        void onStatusChange(Bioplant bioplant, String status); // Callback method to be implemented in the Fragment/Activity
    }
}
