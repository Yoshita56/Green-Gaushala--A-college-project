package com.example.refining_gaushala_app;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.refining_gaushala_app.models.Gaushala;
import com.example.refining_gaushala_app.network.ReportApi;
import com.example.refining_gaushala_app.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class AvailableGaushalaActivity extends AppCompatActivity {
    private RecyclerView recyclerViewGaushalas;

    private GaushalaAdapter gaushalaAdapter;
    private List<Gaushala> gaushalaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_available_gaushala);

        recyclerViewGaushalas = findViewById(R.id.recyclerViewGaushalas);
        recyclerViewGaushalas.setLayoutManager(new LinearLayoutManager(this));

        // Load gaushalas from the database
        loadGaushalas();
    }

    private void loadGaushalas() {
        // Assuming you have a Retrofit client set up
        ReportApi gaushalaApi = RetrofitClient.getRetrofitInstance().create(ReportApi.class);
        gaushalaApi.getAllGaushalas().enqueue(new Callback<List<Gaushala>>() {
            @Override
            public void onResponse(Call<List<Gaushala>> call, Response<List<Gaushala>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    gaushalaList = response.body(); // Populate with fetched data
                    gaushalaAdapter = new GaushalaAdapter(gaushalaList, AvailableGaushalaActivity.this); // Pass the context
                    recyclerViewGaushalas.setAdapter(gaushalaAdapter);
                } else {
                    // Handle the error, e.g., show a Snackbar
                }
            }

            @Override
            public void onFailure(Call<List<Gaushala>> call, Throwable t) {
                // Handle the failure, e.g., show a Snackbar
            }
        });
    }
}
