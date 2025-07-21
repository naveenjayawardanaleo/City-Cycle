package com.example.citycycle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Pricing extends AppCompatActivity {

    Button backtohome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pricing);

        backtohome = findViewById(R.id.backtohome);

        backtohome.setOnClickListener(view -> {
            Intent homepage = new Intent(Pricing.this, MainActivity.class);
            startActivity(homepage);
        });




    }
}