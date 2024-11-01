package com.example.refining_gaushala_app.ui.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.refining_gaushala_app.R;
import com.example.refining_gaushala_app.models.Request;

import java.util.List;

public class AcceptedRequestAdapter extends RecyclerView.Adapter<AcceptedRequestAdapter.AcceptedRequestViewHolder> {

    private List<Request> acceptedRequests;
    private OnRequestCompleteListener listener;

    public interface OnRequestCompleteListener {
        void onRequestComplete(Request request);
    }

    public AcceptedRequestAdapter(List<Request> acceptedRequests, OnRequestCompleteListener listener) {
        this.acceptedRequests = acceptedRequests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AcceptedRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_accepted_request, parent, false); // Updated layout reference
        return new AcceptedRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptedRequestViewHolder holder, int position) {
        Request request = acceptedRequests.get(position);
        holder.tvBioplantName.setText(request.getBioplantName());
        holder.tvDungAmount.setText("Requested Amount: " + request.getDungAmount() + " kg");
        holder.tvDungType.setText("Dung Type: " + request.getDungType()); // Bind dung type
        holder.tvRequestDate.setText("Request Date: " + request.getRequestDate());

        holder.btnComplete.setOnClickListener(v -> {
            listener.onRequestComplete(request); // Notify listener when complete is clicked
        });
    }

    @Override
    public int getItemCount() {
        return acceptedRequests.size();
    }

    static class AcceptedRequestViewHolder extends RecyclerView.ViewHolder {
        TextView tvBioplantName, tvDungAmount, tvDungType, tvRequestDate; // Added tvDungType
        Button btnComplete;

        AcceptedRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBioplantName = itemView.findViewById(R.id.tvBioplantName);
            tvDungAmount = itemView.findViewById(R.id.tvDungAmount);
            tvDungType = itemView.findViewById(R.id.tvDungType); // Initialize tvDungType
            tvRequestDate = itemView.findViewById(R.id.tvRequestDate);
            btnComplete = itemView.findViewById(R.id.btnComplete);
        }
    }
}
