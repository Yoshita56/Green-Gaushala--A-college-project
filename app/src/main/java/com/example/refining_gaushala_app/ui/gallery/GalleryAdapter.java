package com.example.refining_gaushala_app.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView; // Import TextView here

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.refining_gaushala_app.R;
import com.example.refining_gaushala_app.models.Bioplant;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.OrderViewHolder> {
    private List<Bioplant> bioplantList;

    public GalleryAdapter(List<Bioplant> bioplantList) {
        this.bioplantList = bioplantList;
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
    }

    @Override
    public int getItemCount() {
        return bioplantList.size();
    }

    public void updateData(List<Bioplant> newBioplantList) {
        this.bioplantList = newBioplantList;
        notifyDataSetChanged();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView tvBioplantName, tvDungAmount, tvDungType, tvRequestDate;

        public OrderViewHolder(View itemView) {
            super(itemView);
            tvBioplantName = itemView.findViewById(R.id.tvBioplantName);
            tvDungAmount = itemView.findViewById(R.id.tvDungAmount);
            tvDungType = itemView.findViewById(R.id.tvDungType);
            tvRequestDate = itemView.findViewById(R.id.tvRequestDate);
        }
    }
}
