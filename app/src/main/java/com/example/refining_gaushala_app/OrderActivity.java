package com.example.refining_gaushala_app;

import static com.example.refining_gaushala_app.bioplantLogin.BIOPLANT_ID_KEY;
import static com.example.refining_gaushala_app.bioplantLogin.PREF_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.refining_gaushala_app.adapters.OrderAdapter;
import com.example.refining_gaushala_app.models.Bioplant;
import com.example.refining_gaushala_app.network.ReportApi;
import com.example.refining_gaushala_app.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView rvOrders;
    private OrderAdapter orderAdapter;
    private Button btnBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Initialize views
        initializeViews();

        // Set up RecyclerView
        setUpRecyclerView();

        // Get Bioplant ID from Intent
        // Retrieve the Bioplant ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        long bioplantId = sharedPreferences.getLong(BIOPLANT_ID_KEY, 1);  // Default to 2 if not found
        Log.d("Bioplant", "Bioplant ID retrieved: " + bioplantId);
        // Fetch data only if the Bioplant ID is valid
        if (bioplantId != -1) {
            fetchBioplantData(bioplantId);
        } else {
            Toast.makeText(this, "Invalid Bioplant ID", Toast.LENGTH_SHORT).show();
        }

        // Set up the "Back to Home" button click listener
        btnBackToHome.setOnClickListener(v -> onBackPressed());
    }

    /**
     * Initializes views for this activity.
     */
    private void initializeViews() {
        rvOrders = findViewById(R.id.rvOrders);
        btnBackToHome = findViewById(R.id.btnBackToHome);
    }

    /**
     * Sets up the RecyclerView with an adapter and layout manager.
     */
    private void setUpRecyclerView() {
        // Initialize the RecyclerView with an empty list of orders
        orderAdapter = new OrderAdapter(new ArrayList<>());
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        rvOrders.setAdapter(orderAdapter);
    }

    /**
     * Fetches Bioplant data.
     *
     * @param bioplantId The Bioplant ID to fetch data for.
     */
    private void fetchBioplantData(long bioplantId) {
        // Create Retrofit instance and API interface
        ReportApi reportApi = RetrofitClient.getRetrofitInstance().create(ReportApi.class);

        // Call the API to fetch a single Bioplant
        Call<Bioplant> call = reportApi.getBioplant(bioplantId);  // Adjusted to get a single Bioplant object
        call.enqueue(new Callback<Bioplant>() {
            @Override
            public void onResponse(Call<Bioplant> call, Response<Bioplant> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Bioplant bioplant = response.body();
                    Log.d("OrderActivity", "Fetched Bioplant Data: " + bioplant.toString());

                    // Check if bioplant data is available and update RecyclerView
                    if (bioplant != null) {
                        List<Bioplant> bioplantList = new ArrayList<>();
                        bioplantList.add(bioplant); // Add the single bioplant to a list

                        orderAdapter = new OrderAdapter(bioplantList);  // Pass the single bioplant in a list to the adapter
                        rvOrders.setAdapter(orderAdapter);
                    } else {
                        Toast.makeText(OrderActivity.this, "No Bioplant details found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("OrderActivity", "API call failed. Response: " + response.code());
                    Toast.makeText(OrderActivity.this, "Failed to fetch Bioplant details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Bioplant> call, Throwable t) {
                Log.e("OrderActivity", "Network request failed", t);
                Toast.makeText(OrderActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
