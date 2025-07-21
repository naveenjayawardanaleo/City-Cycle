package com.example.citycycle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RentalHistory extends AppCompatActivity {
    private LinearLayout rentalsListLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button btnSubmit;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_history);

        rentalsListLayout = findViewById(R.id.rentalsListLayout);
        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getAllRentals();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String bikeName = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_BIKE_NAME));
                @SuppressLint("Range") String bikeId = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_BIKE_ID));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMN_PRICE));

                View rentalView = getLayoutInflater().inflate(R.layout.rental, null);
                TextView bikeNameText = rentalView.findViewById(R.id.bikeName);
                TextView BikeIdText = rentalView.findViewById(R.id.bikeId);
                TextView BikePriceText = rentalView.findViewById(R.id.bikePrice);

                ImageView bikeImage = rentalView.findViewById(R.id.bikeImage);

                Button button = rentalView.findViewById(R.id.btnSubmit);

                bikeNameText.setText("Bike Name: " + bikeName);

                BikeIdText.setText("Bike Id: " + bikeId);

                BikePriceText.setText("Bike Id: " + price);

                bikeImage.setImageResource(R.drawable.bike);

//                bikeDetailsText.setText("Bike Name " +bikeName+ "\nBike ID: " + bikeId + "\nPrice: LKR " +price);

                rentalsListLayout.addView(rentalView);
            } while (cursor.moveToNext());
            cursor.close();
        }

        findViewById(R.id.backtoHome).setOnClickListener(view -> goBackToHome());

        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(view -> {
            Intent homepage = new Intent(RentalHistory.this, Payments.class);
            startActivity(homepage);
        });
    }

    public void goBackToHome() {
        Intent homepageIntent = new Intent(RentalHistory.this, MainActivity.class);
        startActivity(homepageIntent);
    }
}
