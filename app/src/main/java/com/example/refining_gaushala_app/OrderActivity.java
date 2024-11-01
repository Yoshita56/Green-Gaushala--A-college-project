package com.example.refining_gaushala_app;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.refining_gaushala_app.databinding.ActivityOrderBinding;

public class OrderActivity extends AppCompatActivity {

    private TextView tvOrderStatus, tvOrderDate, tvOrderTime, tvDungAmount, tvRemarks;
    private Button btnBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Initialize UI components
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvOrderTime = findViewById(R.id.tvOrderTime);
        tvDungAmount = findViewById(R.id.tvDungAmount);
        tvRemarks = findViewById(R.id.tvRemarks);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        // Get data passed from the previous activity
        Intent intent = getIntent();
        String orderStatus = intent.getStringExtra("ORDER_STATUS");
        String orderDate = intent.getStringExtra("ORDER_DATE");
        String orderTime = intent.getStringExtra("ORDER_TIME");
        String dungAmount = intent.getStringExtra("DUNG_AMOUNT");
        String remarks = intent.getStringExtra("REMARKS");

        // Set the data to TextViews
        tvOrderStatus.setText("Order Status: " + orderStatus);
        tvOrderDate.setText("Order Date: " + orderDate);
        tvOrderTime.setText("Order Time: " + orderTime);
        tvDungAmount.setText("Requested Amount: " + dungAmount);
        tvRemarks.setText("Remarks: " + remarks);

        // Set back button listener
        btnBackToHome.setOnClickListener(v -> {
            // Navigate back to the previous activity or home
            finish(); // This will close the current activity
        });
    }

}