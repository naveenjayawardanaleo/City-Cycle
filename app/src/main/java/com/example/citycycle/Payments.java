package com.example.citycycle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Payments extends AppCompatActivity {

    Button backtohome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button backtoHome;

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payments);

        backtohome = findViewById(R.id.backtoHome);

        backtohome.setOnClickListener(view -> {
            Intent homepage = new Intent(Payments.this, MainActivity.class);
            startActivity(homepage);
        });


    }


}