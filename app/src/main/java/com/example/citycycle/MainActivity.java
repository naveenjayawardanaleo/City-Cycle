package com.example.citycycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private BikeListAdapter adapter;
    private Button rentalbtn, editprofile, pricingbtn;

    private HashMap<String, List<String>> locationBikes;
    private HashMap<String, List<Integer>> locationBikeImages;
    private HashMap<String, List<String>> locationBikeIds;
    private HashMap<String, List<Double>> locationBikePrices;


    private List<String> filteredBikeNames = new ArrayList<>();
    private List<Integer> filteredBikeImages = new ArrayList<>();
    private List<String> filteredBikeIds = new ArrayList<>();
    private List<Double> filteredBikePrices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Spinner
        Spinner spinner = findViewById(R.id.locationSpinner);
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.locations,
                android.R.layout.simple_spinner_item
        );
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(locationAdapter);
        spinner.setOnItemSelectedListener(this);

        // RecyclerView
        recyclerView = findViewById(R.id.listOfBikes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Cycle and Location Combination
        locationBikes = new HashMap<>();
        locationBikeImages = new HashMap<>();
        locationBikeIds = new HashMap<>();
        locationBikePrices = new HashMap<>();

        locationBikes.put("Colombo 1", Arrays.asList("Cycle A", "Cycle B", "Cycle C"));
        locationBikeImages.put("Colombo 1", Arrays.asList(R.drawable.bike, R.drawable.bike2, R.drawable.bike3));
        locationBikeIds.put("Colombo 1", Arrays.asList("1", "2", "3"));
        locationBikePrices.put("Colombo 1", Arrays.asList(200.0, 180.0, 150.0));

        locationBikes.put("Colombo 2", Arrays.asList("Cycle D", "Cycle E"));
        locationBikeImages.put("Colombo 2", Arrays.asList(R.drawable.bike4, R.drawable.bike5));
        locationBikeIds.put("Colombo 2", Arrays.asList("4", "5"));
        locationBikePrices.put("Colombo 2", Arrays.asList(250.0, 230.0));

        locationBikes.put("Colombo 3", Arrays.asList("Cycle F", "Cycle G", "Cycle H"));
        locationBikeImages.put("Colombo 3", Arrays.asList(R.drawable.bike, R.drawable.bike2, R.drawable.bike3));
        locationBikeIds.put("Colombo 3", Arrays.asList("6", "7", "8"));
        locationBikePrices.put("Colombo 3", Arrays.asList(220.0, 210.0, 200.0));

        locationBikes.put("Colombo 4", Arrays.asList("Cycle I"));
        locationBikeImages.put("Colombo 4", Arrays.asList(R.drawable.bike4));
        locationBikeIds.put("Colombo 4", Arrays.asList("9"));
        locationBikePrices.put("Colombo 4", Arrays.asList(300.0));

        locationBikes.put("Colombo 5", Arrays.asList("Cycle J", "Cycle K"));
        locationBikeImages.put("Colombo 5", Arrays.asList(R.drawable.bike5, R.drawable.bike2));
        locationBikeIds.put("Colombo 5", Arrays.asList("10", "11"));
        locationBikePrices.put("Colombo 5", Arrays.asList(180.0, 160.0));


        adapter = new BikeListAdapter(filteredBikeNames, filteredBikeImages, filteredBikeIds, filteredBikePrices);

        GridLayoutManager layoutManager=new GridLayoutManager(this,2);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);



        updateBikeList("Colombo 1");

//        navigation
        rentalbtn = findViewById(R.id.rentalHistoryPage);
        editprofile = findViewById(R.id.editprofile);
        pricingbtn = findViewById(R.id.pricingBtn);

        rentalbtn.setOnClickListener(view -> {
            Intent homepage = new Intent(MainActivity.this, RentalHistory.class);
            startActivity(homepage);
        });

        editprofile.setOnClickListener(view -> {
            Intent homepage = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(homepage);
        });
        pricingbtn.setOnClickListener(view -> {
            Intent homepage = new Intent(MainActivity.this, Pricing.class);
            startActivity(homepage);
        });


    }


    private void updateBikeList(String location) {
        filteredBikeNames.clear();
        filteredBikeImages.clear();
        filteredBikeIds.clear();
        filteredBikePrices.clear();


        filteredBikeNames.addAll(locationBikes.getOrDefault(location, new ArrayList<>()));
        filteredBikeImages.addAll(locationBikeImages.getOrDefault(location, new ArrayList<>()));
        filteredBikeIds.addAll(locationBikeIds.getOrDefault(location, new ArrayList<>()));
        filteredBikePrices.addAll(locationBikePrices.getOrDefault(location, new ArrayList<>()));


        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedLocation = parent.getItemAtPosition(position).toString();
        updateBikeList(selectedLocation);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Default to first location
        updateBikeList("Colombo 1");
    }
}
