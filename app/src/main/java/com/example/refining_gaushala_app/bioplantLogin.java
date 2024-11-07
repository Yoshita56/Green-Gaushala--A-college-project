package com.example.refining_gaushala_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.refining_gaushala_app.models.Bioplant;
import com.example.refining_gaushala_app.network.AuthApi;
import com.example.refining_gaushala_app.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class bioplantLogin extends AppCompatActivity {

    EditText userIdEditText, passwordEditText;
    Button loginButton;
    TextView bioplantSignUp;

    public static final String PREF_NAME = "MyPrefs"; // SharedPreferences name
    public static final String BIOPLANT_ID_KEY = "bioplantId"; // Key for Bioplant ID
    private static final String TAG = "bioplantLogin"; // Log tag for debugging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bioplant_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        userIdEditText = findViewById(R.id.login_userId);
        passwordEditText = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.biobtnlogin);
        bioplantSignUp = findViewById(R.id.bioplant_SignUp);

        // Handle Sign Up button click
        bioplantSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(bioplantLogin.this, bioplantReg.class);
            startActivity(intent);
        });

        // Handle Login button click
        loginButton.setOnClickListener(v -> {
            String userId = userIdEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Validate input
            if (userId.isEmpty() || password.isEmpty()) {
                Toast.makeText(bioplantLogin.this, "Please enter both user ID and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create Bioplant object for login (modify if your API expects a different payload)
            Bioplant bioplant = new Bioplant();
            bioplant.setUserId(userId);
            bioplant.setPassword(password);

            // Make API call to authenticate Bioplant
            AuthApi authApi = RetrofitClient.getAuthApi();
            Call<Bioplant> call = authApi.loginBioplant(bioplant);

            call.enqueue(new Callback<Bioplant>() {
                @Override
                public void onResponse(Call<Bioplant> call, Response<Bioplant> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Retrieve bioplantId from response
                        long bioplantId = response.body().getId();

                        // Log the bioplantId for debugging
                        Log.d(TAG, "Bioplant ID from response: " + bioplantId);

                        // Save Bioplant ID to SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putLong(BIOPLANT_ID_KEY, bioplantId);
                        editor.apply();

                        // Log confirmation of saving to SharedPreferences
                        Log.d(TAG, "Bioplant ID saved to SharedPreferences: " + bioplantId);

                        Toast.makeText(bioplantLogin.this, "Login successful!", Toast.LENGTH_SHORT).show();

                        // Navigate to another activity or update UI as needed
                        Intent intent = new Intent(bioplantLogin.this, bioplant.class);
                        startActivity(intent);

                    } else {
                        Log.d(TAG, "Login failed: " + response.message());
                        Toast.makeText(bioplantLogin.this, "Login failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Bioplant> call, Throwable t) {
                    Log.d(TAG, "Error in API call: " + t.getMessage());
                    Toast.makeText(bioplantLogin.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
