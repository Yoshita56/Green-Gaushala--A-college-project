package com.example.refining_gaushala_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.refining_gaushala_app.models.Bioplant;
import com.example.refining_gaushala_app.network.RetrofitClient;
import com.example.refining_gaushala_app.network.ReportApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendEnquiryActivity extends AppCompatActivity {

    private TextView tvGaushalaName, tvPhoneNumber, tvEmail;
    private Spinner spinnerResourceType;
    private EditText etDungAmount;
    private Button btnSubmitEnquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_enquiry);

        // Initialize views
        tvGaushalaName = findViewById(R.id.tvGaushalaName);
        spinnerResourceType = findViewById(R.id.spinner_resource_type);
        etDungAmount = findViewById(R.id.etDungAmount);
        btnSubmitEnquiry = findViewById(R.id.btnSubmitEnquiry);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvEmail = findViewById(R.id.tvEmail);

        // Get data passed from the previous activity
        Intent intent = getIntent();
        String gaushalaName = intent.getStringExtra("GAUSHALA_NAME");
        String gaushalaPhone = intent.getStringExtra("PHONE");  // Updated key name
        String gaushalaEmail = intent.getStringExtra("EMAIL");  // Updated key name

        // Set the data to the respective views
        tvGaushalaName.setText(gaushalaName);
        String phoneText = (gaushalaPhone != null && !gaushalaPhone.isEmpty()) ? "Phone: " + gaushalaPhone : "Phone: Not Available";
        tvPhoneNumber.setText(phoneText);
        tvEmail.setText("Email: " + gaushalaEmail);
        // Set up the submit button listener
        btnSubmitEnquiry.setOnClickListener(v -> submitEnquiry());
    }

    private void submitEnquiry() {
        String amountString = etDungAmount.getText().toString().trim();

        if (amountString.isEmpty()) {
            Toast.makeText(this, "Please enter the amount.", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount entered.", Toast.LENGTH_SHORT).show();
            return;
        }

        String selectedResourceType = spinnerResourceType.getSelectedItem().toString();
        String dungType = selectedResourceType.equals("Fresh") ? "Fresh" : "Dry";  // Or match as needed

        // Retrieve Biogas plant ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        long bioplantId = sharedPreferences.getLong("bioplantId", -1L); // Default to -1L if not found

        Log.d("SendEnquiryActivity", "Bioplant ID from SharedPreferences: " + bioplantId);

        if (bioplantId == -1L) {
            Toast.makeText(this, "Invalid Biogas Plant ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Call the API to fetch bioplant data, then submit the enquiry
        fetchBioplantAndSubmitEnquiry(dungType, amount, bioplantId);
    }

    private void fetchBioplantAndSubmitEnquiry(String dungType, double dungAmount, long bioplantId) {
        // Get the Gaushala ID from the intent
        long gaushalaId = getIntent().getLongExtra("GAUSHALA_ID", 1);  // Default value is 1 if not found

        if (gaushalaId == -1) {
            // Handle the case where Gaushala ID is not passed correctly
            Toast.makeText(SendEnquiryActivity.this, "Gaushala ID not found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create Retrofit instance and API interface
        ReportApi reportApi = RetrofitClient.getRetrofitInstance().create(ReportApi.class);

        // Make the API call to fetch the Bioplant data first (GET request)
        Call<Bioplant> call = reportApi.getBioplant(bioplantId);  // Assuming GET request to fetch Bioplant
        call.enqueue(new Callback<Bioplant>() {
            @Override
            public void onResponse(Call<Bioplant> call, Response<Bioplant> response) {
                if (response.isSuccessful()) {
                    Bioplant bioplant = response.body();
                    if (bioplant != null) {
                        // Successfully fetched bioplant, now submit the enquiry
                        Log.d("Bioplant", "Fetched: " + bioplant.getName());  // Example

                        // Call the method to update Bioplant dung details with gaushalaId
                        updateBioplantDungDetails(dungType, dungAmount, bioplantId, gaushalaId);
                    } else {
                        // Handle the case where Bioplant data is not found
                        Toast.makeText(SendEnquiryActivity.this, "Bioplant not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle the case where the response was not successful
                    String errorMessage = "Failed to fetch Bioplant. Please try again later.";
                    if (response.errorBody() != null) {
                        // Optionally extract and log any error message from the response
                        errorMessage = "Error: " + response.message();
                    }
                    Toast.makeText(SendEnquiryActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Bioplant> call, Throwable t) {
                // Network or other failure
                Toast.makeText(SendEnquiryActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }






    private void updateBioplantDungDetails(String dungType, double dungAmount, long bioplantId, long gaushalaId) {
        // Get the current date in the desired format (e.g., "yyyy-MM-dd")
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Create Retrofit instance and API interface
        ReportApi bioplantApi = RetrofitClient.getRetrofitInstance().create(ReportApi.class);

        // Make the API call to update Bioplant dung details (POST request)
        Call<String> call = bioplantApi.updateBioplant(
                bioplantId,
                dungType,
                dungAmount,
                "pending",  // Assuming the status is 'pending', replace as needed
                currentDate,
                gaushalaId
        );

        // Enqueue the API call for asynchronous execution
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // Success: show success message
                    Toast.makeText(SendEnquiryActivity.this, "Enquiry submitted successfully", Toast.LENGTH_SHORT).show();

                    // Optionally, navigate to another screen if needed
                    // Uncomment if navigation is needed
                    // Intent intent = new Intent(SendEnquiryActivity.this, AvailableGaushalaActivity.class);
                    // startActivity(intent);
                } else {
                    // Failure: show error message based on response
                    String errorMessage = "Failed to submit enquiry.";
                    if (response.errorBody() != null) {
                        // Extract and log any detailed error message
                        errorMessage = "Error: " + response.message();
                    }
                    Toast.makeText(SendEnquiryActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Network or other failure
                Toast.makeText(SendEnquiryActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}
