package com.example.refining_gaushala_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.refining_gaushala_app.R;
import com.example.refining_gaushala_app.models.Bioplant;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Bioplant> bioplantList;  // List of Bioplant objects

    // Constructor to initialize the bioplantList
    public OrderAdapter(List<Bioplant> bioplantList) {
        this.bioplantList = bioplantList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Bioplant bioplant = bioplantList.get(position);

        // Check if bioplant is not null before accessing its fields
        if (bioplant != null) {
            // Set the order status
            holder.tvOrderStatus.setText("Status: " + (bioplant.getStatus() != null ? bioplant.getStatus() : "N/A"));

            // Set the enquiry date
            holder.tvOrderDate.setText("Enquiry Date: " + (bioplant.getDate() != null ? bioplant.getDate() : "N/A"));

            // Set the requested dung amount
            holder.tvDungAmount.setText("Requested Amount: " + (bioplant.getDungRequested() != null ? bioplant.getDungRequested() : "0") + " kg");

            // Set the dung type (fresh or dry)
            holder.tvDungType.setText("Dung Type: " + (bioplant.getDungType() != null ? bioplant.getDungType() : "N/A"));

            // Get the Gaushala name from the Bioplant object, handle null values
            String gaushalaName = bioplant.getGaushala() != null ? bioplant.getGaushala().getName() : "Unknown Gaushala";
            holder.tvRequestedFrom.setText("Gaushala Name: " + gaushalaName);

            // Set remarks (using status here for simplicity, but you can change this logic)
            holder.tvRemarks.setText("Remarks: " + (bioplant.getStatus() != null ? bioplant.getStatus() : "N/A"));
        }
    }

    @Override
    public int getItemCount() {
        return bioplantList != null ? bioplantList.size() : 0;
    }

    // ViewHolder class to hold the view references
    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderStatus, tvOrderDate, tvDungAmount, tvDungType, tvRequestedFrom, tvRemarks;

        // Constructor to initialize the view references
        public OrderViewHolder(View itemView) {
            super(itemView);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvDungAmount = itemView.findViewById(R.id.tvDungAmount);
            tvDungType = itemView.findViewById(R.id.tvDungType);
            tvRequestedFrom = itemView.findViewById(R.id.tvRequestedFrom);
            tvRemarks = itemView.findViewById(R.id.tvOrderStatus); // Reusing the same TextView for Remarks
        }
    }
}
