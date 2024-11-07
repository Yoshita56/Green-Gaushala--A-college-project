package com.example.refining_gaushala_app;

import static com.example.refining_gaushala_app.bioplantLogin.BIOPLANT_ID_KEY;
import static com.example.refining_gaushala_app.bioplantLogin.PREF_NAME;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.refining_gaushala_app.models.Bioplant;
import com.example.refining_gaushala_app.network.RetrofitClient;
import com.example.refining_gaushala_app.network.ReportApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
//Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        // Ensure the layout resizes to fit insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Fetch Bioplant ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        long bioplantId = sharedPreferences.getLong(BIOPLANT_ID_KEY, 1);  // Default to 2 if not found
        Log.d("Bioplant", "Bioplant ID retrieved: " + bioplantId); // Default to -1 if not found

        if (bioplantId != -1) {
            fetchBioplantDetails(bioplantId);
        } else {
            Toast.makeText(this, "Bioplant ID not found", Toast.LENGTH_SHORT).show();
        }

        // Button to handle updates, for now shows a toast
        Button logoutButton = findViewById(R.id.btnLogOut);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
//        btnUpdate.setOnClickListener(v -> {
//            Toast.makeText(ProfileActivity.this, "Update button clicked, Network Error", Toast.LENGTH_SHORT).show();
//
//            // Uncomment below to open a new Activity to allow the user to update their profile
//            // Intent intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
//            // startActivity(intent);
//        });
    }



    private void fetchBioplantDetails(long bioplantId) {
        // Initialize Retrofit API using RetrofitClient
        ReportApi apiService = RetrofitClient.getReportApi();  // Use RetrofitClient.getReportApi() instead of getInstance()

        // Call the API to fetch bioplant details
        Call<Bioplant> call = apiService.getBioplant(bioplantId);

        call.enqueue(new Callback<Bioplant>() {
            @Override
            public void onResponse(Call<Bioplant> call, Response<Bioplant> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Bioplant bioplant = response.body();
                    updateUI(bioplant);  // Pass data to UI update method
                } else {
                    Toast.makeText(ProfileActivity.this, "Error fetching Bioplant details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Bioplant> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void logout() {
        // Clear user session data (if using SharedPreferences for session management)
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Clear all session data
        editor.apply();

        // Redirect to LoginActivity
        Intent intent = new Intent(ProfileActivity.this, bioplantLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the activity stack
        startActivity(intent);
        finish(); // Close ProfileActivity
    }
    private void updateUI(Bioplant bioplant) {
        // Update the UI with Bioplant details
        TextView tvPlantNameValue = findViewById(R.id.tvPlantNameValue);
        TextView tvLocationValue = findViewById(R.id.tvLocationValue);
        TextView tvRegistrationNumberValue = findViewById(R.id.tvRegistrationNumberValue);
        TextView tvPhoneNumberValue = findViewById(R.id.tvPhoneNumberValue);
      //  TextView tvEmailValue = findViewById(R.id.tvEmailValue);

        // Ensure bioplant data is not null and update UI elements
        if (bioplant != null) {
            tvPlantNameValue.setText(bioplant.getName() != null ? bioplant.getName() : "N/A");
            tvLocationValue.setText(bioplant.getLocation() != null ? bioplant.getLocation() : "N/A");
            tvRegistrationNumberValue.setText(bioplant.getRegistrationNumber() != null ? bioplant.getRegistrationNumber() : "N/A");
            tvPhoneNumberValue.setText(bioplant.getPhoneNumber() != null ? bioplant.getPhoneNumber() : "N/A");
        //    tvEmailValue.setText(bioplant.getEmail() != null ? bioplant.getEmail() : "N/A");

        }
    }
    }


