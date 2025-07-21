package com.example.citycycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RentingDetails extends AppCompatActivity {

    private TextView bikeNameTextView;
    private ImageView bikeImageView;
    private TextView bikeIdTextView;
    private TextView rentalPriceTextView;
    private TextView rentalDurationTextView;
    private TextView rentalTimesTextView;
    private Button btnConfirmRental;
    private Button backtoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renting_details);


        bikeNameTextView = findViewById(R.id.bikeName);
        bikeImageView = findViewById(R.id.bikeImage);
        bikeIdTextView = findViewById(R.id.bikeId);
        rentalPriceTextView = findViewById(R.id.rentalPrice);
        rentalTimesTextView = findViewById(R.id.rentalTimes);
        btnConfirmRental = findViewById(R.id.btnConfirmRental);
        backtoHome = findViewById(R.id.backtoHome);


        if (bikeIdTextView == null) {
            Log.e("RentingDetails", "bikeIdTextView is null. Check the layout file.");
        }

        Intent intent = getIntent();


        String bikeName = intent.getStringExtra("bikeName");
        int bikeImageResId = intent.getIntExtra("bikeImageResId", R.drawable.baseline_add_a_photo_24); // Default image if not provided
        String bikeId = intent.getStringExtra("bikeId");
        double price = intent.getDoubleExtra("price", 0.0);


        bikeNameTextView.setText(bikeName);
        bikeImageView.setImageResource(bikeImageResId);
        bikeIdTextView.setText("Bike ID: " + bikeId);
        rentalPriceTextView.setText(String.format("Price: LKR%.2f/hour", price));


        backtoHome.setOnClickListener(view -> {
            Intent homepage = new Intent(RentingDetails.this, MainActivity.class);
            startActivity(homepage);
        });

        btnConfirmRental.setOnClickListener(view -> {

            DBHelper dbHelper = new DBHelper(RentingDetails.this);
            dbHelper.insertRental(bikeName, bikeId, price);



            Intent historydata = new Intent(RentingDetails.this, RentalHistory.class);


            historydata.putExtra("bikeName", bikeName);
            historydata.putExtra("bikeId", bikeId);
            historydata.putExtra("price", price);


            startActivity(historydata);
        });

    }
}