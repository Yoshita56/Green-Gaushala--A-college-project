package com.example.refining_gaushala_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SendEnquiryActivity extends AppCompatActivity {

    private TextView tvGaushalaName;
    private Spinner spinnerResourceType;
    private EditText etDungAmount;
    private Button btnSubmitEnquiry;
    private TextView tvPhoneNumber, tvEmail;

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
        tvGaushalaName.setText(gaushalaName);

        // Set up the submit button listener
        btnSubmitEnquiry.setOnClickListener(v -> submitEnquiry());
    }

    private void submitEnquiry() {
        String amountString = etDungAmount.getText().toString().trim();

        if (amountString.isEmpty()) {
            Toast.makeText(this, "Please enter the amount.", Toast.LENGTH_SHORT).show();
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(amountString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount entered.", Toast.LENGTH_SHORT).show();
            return;
        }

        String selectedResourceType = spinnerResourceType.getSelectedItem().toString();

        // Here you would typically send the enquiry to the server or save it to the database
        // For demonstration, just showing a Toast
        Toast.makeText(this, "Enquiry submitted for " + amount + " kg of " + selectedResourceType, Toast.LENGTH_SHORT).show();

        // Finish the activity and go back to the previous one (or handle as needed)
        finish();
    }
}
