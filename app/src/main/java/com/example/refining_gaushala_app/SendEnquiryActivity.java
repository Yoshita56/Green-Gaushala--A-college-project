package com.example.refining_gaushala_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SendEnquiryActivity extends AppCompatActivity {

    private TextView tvGaushalaName;
    private TextView tvDungType;
    private EditText etDungAmount;
    private Button btnSubmitEnquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_enquiry);

        tvGaushalaName = findViewById(R.id.tvGaushalaName);
        tvDungType = findViewById(R.id.tvDungType);
        etDungAmount = findViewById(R.id.etDungAmount);
        btnSubmitEnquiry = findViewById(R.id.btnSubmitEnquiry);

        // Get data passed from the previous activity
        Intent intent = getIntent();
        String gaushalaName = intent.getStringExtra("GAUSHALA_NAME");
        String dungType = intent.getStringExtra("DUNG_TYPE");

        tvGaushalaName.setText(gaushalaName);
        tvDungType.setText(dungType);

        // Set up the submit button listener
        btnSubmitEnquiry.setOnClickListener(v -> submitEnquiry());
    }

    private void submitEnquiry() {
        String amountString = etDungAmount.getText().toString().trim();

        if (amountString.isEmpty()) {
            Toast.makeText(this, "Please enter the amount.", Toast.LENGTH_SHORT).show();
            return;
        }

        int amount = Integer.parseInt(amountString);
        // Here you would typically send the enquiry to the server or save it to the database
        // For demonstration, just showing a Toast
        Toast.makeText(this, "Enquiry submitted for " + amount + " kg.", Toast.LENGTH_SHORT).show();

        // Finish the activity and go back to the previous one (or handle as needed)
        finish();
    }
}
