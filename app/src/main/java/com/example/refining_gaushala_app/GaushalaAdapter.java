package com.example.refining_gaushala_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.refining_gaushala_app.models.Gaushala;

import java.util.List;

public class GaushalaAdapter extends RecyclerView.Adapter<GaushalaAdapter.GaushalaViewHolder> {

    private List<Gaushala> gaushalaList;
    private Context context;

    public GaushalaAdapter(List<Gaushala> gaushalaList, Context context) {
        this.gaushalaList = gaushalaList;
        this.context = context;
    }

    @NonNull
    @Override
    public GaushalaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gaushala, parent, false);
        return new GaushalaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GaushalaViewHolder holder, int position) {
        Gaushala gaushala = gaushalaList.get(position);
        holder.tvGaushalaName.setText(gaushala.getName());
        holder.tvGaushalaLocation.setText(gaushala.getLocation());

        // Check if fresh dung amount and price are greater than 0
        String freshDungText = gaushala.getFreshDungAmount() > 0
                ? "Fresh Dung: " + gaushala.getFreshDungAmount() + " kg at " + gaushala.getFreshDungPrice() + " per kg"
                : "Fresh Dung: Not Available";

        // Check if dry dung amount and price are greater than 0
        String dryDungText = gaushala.getDryDungAmount() > 0
                ? "Dry Dung: " + gaushala.getDryDungAmount() + " kg at " + gaushala.getDryDungPrice() + " per kg"
                : "Dry Dung: Not Available";

        holder.tvFreshDungAvailability.setText(freshDungText);
        holder.tvDryDungAvailability.setText(dryDungText);

        // Set up button click listener for buying dung
        holder.btnBuyDung.setOnClickListener(v -> {
            // Intent to navigate to the SendEnquiryActivity
            Intent intent = new Intent(context, SendEnquiryActivity.class);
            intent.putExtra("GAUSHALA_NAME", gaushala.getName()); // Pass gaushala name
            intent.putExtra("GAUSHALA_LOCATION", gaushala.getLocation()); // Pass gaushala location
            intent.putExtra("PHONE_NUMBER", gaushala.getPhone()); // Pass phone number
            intent.putExtra("EMAIL", gaushala.getEmail()); // Pass email
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return gaushalaList.size();
    }

    public static class GaushalaViewHolder extends RecyclerView.ViewHolder {
        TextView tvGaushalaName;
        TextView tvGaushalaLocation;
        TextView tvFreshDungAvailability;
        TextView tvDryDungAvailability;
        Button btnBuyDung;

        public GaushalaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGaushalaName = itemView.findViewById(R.id.tvGaushalaName);
            tvGaushalaLocation = itemView.findViewById(R.id.tvGaushalaLocation);
            tvFreshDungAvailability = itemView.findViewById(R.id.tvFreshDungAvailability);
            tvDryDungAvailability = itemView.findViewById(R.id.tvDryDungAvailability);
            btnBuyDung = itemView.findViewById(R.id.btnBuyDung);
        }
    }
}
