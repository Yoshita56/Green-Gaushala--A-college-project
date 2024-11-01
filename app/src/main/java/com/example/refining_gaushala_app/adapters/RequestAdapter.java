package com.example.refining_gaushala_app.adapters;
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

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.RequestViewHolder> {
    private List<Request> requests;

    public RequestsAdapter(List<Request> requests) {
        this.requests = requests;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bioplant_request, parent, false); // Use item_request_bioplant layout
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        Request request = requests.get(position);
        holder.tvBioplantName.setText("Bioplant Name: " + request.getBioplantName());
        holder.tvDungType.setText("Dung Type: " + request.getDungType()); // Assuming this method exists
        holder.tvDungAmount.setText("Requested Amount: " + request.getDungAmount() + " kg");
        holder.tvRequestDate.setText("Request Date: " + request.getRequestDate());

        // Optionally set listeners for accept/reject buttons
        holder.btnAccept.setOnClickListener(v -> {
            // Handle accept action
        });

        holder.btnReject.setOnClickListener(v -> {
            // Handle reject action
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView tvBioplantName;
        TextView tvDungType;
        TextView tvDungAmount;
        TextView tvRequestDate;
        Button btnAccept;
        Button btnReject;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBioplantName = itemView.findViewById(R.id.tvBioplantName);
            tvDungType = itemView.findViewById(R.id.tvDungType);
            tvDungAmount = itemView.findViewById(R.id.tvDungAmount);
            tvRequestDate = itemView.findViewById(R.id.tvRequestDate);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnReject = itemView.findViewById(R.id.btnReject);
        }
    }
}
