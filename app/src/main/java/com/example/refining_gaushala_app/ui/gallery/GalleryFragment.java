package com.example.refining_gaushala_app.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.refining_gaushala_app.R;
import com.example.refining_gaushala_app.models.Bioplant;
import com.example.refining_gaushala_app.network.RetrofitClient;
import com.example.refining_gaushala_app.network.ReportApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends Fragment {

    private RecyclerView recyclerViewRequests;
    private GalleryAdapter galleryAdapter;
    private static final String PREF_NAME = "MyPrefs";  // SharedPreferences name
    private static final String GAUSHALA_ID_KEY = "gaushalaId"; // Key for Gaushala ID
    private long gaushalaId;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        // Initialize RecyclerView
        recyclerViewRequests = rootView.findViewById(R.id.recyclerViewBioplants);
        recyclerViewRequests.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set the adapter for the RecyclerView
        galleryAdapter = new GalleryAdapter(new ArrayList<>(), this::updateStatus); // Pass updateStatus method as listener
        recyclerViewRequests.setAdapter(galleryAdapter);

        // Retrieve the Gaushala ID from SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gaushalaId = sharedPreferences.getLong(GAUSHALA_ID_KEY, -1);  // Default value is -1 if not found

        if (gaushalaId != -1) {
            // Fetch the Bioplant data and display the report for the specific Gaushala
            fetchBioplantData(gaushalaId); // Fetch data for the currently logged-in Gaushala
        } else {
            Toast.makeText(getContext(), "Gaushala ID not found", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    private void fetchBioplantData(long gaushalaId) {
        ReportApi reportApi = RetrofitClient.getRetrofitInstance().create(ReportApi.class);

        Call<Bioplant> call = reportApi.getBioplant(gaushalaId); // API that returns Bioplant by Gaushala ID
        call.enqueue(new Callback<Bioplant>() {
            @Override
            public void onResponse(Call<Bioplant> call, Response<Bioplant> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Bioplant bioplant = response.body();

                    List<Bioplant> bioplantList = new ArrayList<>();
                    bioplantList.add(bioplant);

                    String report = generateReport(bioplant, gaushalaId);
                    Toast.makeText(getContext(), report, Toast.LENGTH_LONG).show();

                    galleryAdapter.updateData(bioplantList);
                } else {
                    Toast.makeText(getContext(), "Failed to fetch Bioplant data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Bioplant> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateStatus(Bioplant bioplant, String status) {
        ReportApi reportApi = RetrofitClient.getRetrofitInstance().create(ReportApi.class);

        Call<String> call = reportApi.updateBioplantStatus(bioplant.getId(), gaushalaId, status);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Status updated to: " + status, Toast.LENGTH_SHORT).show();
                    fetchBioplantData(gaushalaId);  // Refresh the data after updating status
                } else {
                    Toast.makeText(getContext(), "Failed to update status", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String generateReport(Bioplant bioplant, long gaushalaId) {
        String bioplantName = bioplant.getName();
        String gaushalaLocation = bioplant.getGaushala().getLocation();
        String dungType = bioplant.getDungType();
        double dungRequested = bioplant.getDungRequested();
        String date = bioplant.getDate();

        return "Bioplant: " + bioplantName + "\n" +
                "Gaushala (ID: " + gaushalaId + ") located in " + gaushalaLocation + "\n" +
                "Dung Type: " + dungType + "\n" +
                "Dung Requested: " + dungRequested + " kg\n" +
                "Request Date: " + date;
    }
}
