package com.example.refining_gaushala_app;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.refining_gaushala_app.models.Gaushala;

public class GaushalaAdapter extends RecyclerView.Adapter<GaushalaAdapter.GaushalaViewHolder> {

    private List<Gaushala> gaushalaList;

    public GaushalaAdapter(List<Gaushala> gaushalaList) {
        this.gaushalaList = gaushalaList;
    }

    @NonNull
    @Override
    public GaushalaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gaushala, parent, false);
        return new GaushalaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GaushalaViewHolder holder, int position) {
        Gaushala gaushala = gaushalaList.get(position);
        holder.tvGaushalaName.setText(gaushala.getName());
        holder.tvGaushalaLocation.setText(gaushala.getLocation());

        // Update availability and price information for fresh and dry dung
        holder.tvFreshDungAvailability.setText("Fresh Dung: " + gaushala.getFreshDungAmount() + " kg available at " + gaushala.getFreshDungPrice() + " per kg");
        holder.tvDryDungAvailability.setText("Dry Dung: " + gaushala.getDryDungAmount() + " kg available at " + gaushala.getDryDungPrice() + " per kg");

        // Set up click listener for the "Order Dung" button
        holder.btnBuyDung.setOnClickListener(v -> {
            // Show a dialog to choose which dung to order
            showOrderDialog(holder.itemView.getContext(), gaushala);
        });
    }

    // Method to show the order dialog
    private void showOrderDialog(Context context, Gaushala gaushala) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Dung Type to Order");

        String[] options = {
                "Fresh Dung: " + gaushala.getFreshDungAmount() + " kg available at " + gaushala.getFreshDungPrice() + " per kg",
                "Dry Dung: " + gaushala.getDryDungAmount() + " kg available at " + gaushala.getDryDungPrice() + " per kg"
        };

        builder.setItems(options, (dialog, which) -> {
            String selectedDung = options[which];
            Toast.makeText(context, "You ordered: " + selectedDung, Toast.LENGTH_SHORT).show();
            // Logic to handle the order process can be added here
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return gaushalaList.size();
    }

    public static class GaushalaViewHolder extends RecyclerView.ViewHolder {
        TextView tvGaushalaName;
        TextView tvGaushalaLocation;
        TextView tvFreshDungAvailability; // Updated to include fresh dung availability
        TextView tvDryDungAvailability;   // Updated to include dry dung availability
        Button btnBuyDung;

        public GaushalaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGaushalaName = itemView.findViewById(R.id.tvGaushalaName);
            tvGaushalaLocation = itemView.findViewById(R.id.tvGaushalaLocation);
            tvFreshDungAvailability = itemView.findViewById(R.id.tvFreshDungAvailability); // Updated
            tvDryDungAvailability = itemView.findViewById(R.id.tvDryDungAvailability);       // Updated
            btnBuyDung = itemView.findViewById(R.id.btnBuyDung);
        }
    }
}
