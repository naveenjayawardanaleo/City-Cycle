package com.example.citycycle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FortgotPassword extends AppCompatActivity {

    TextView gotologin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fortgot_password);

        gotologin = findViewById(R.id.gotologin);



        gotologin.setOnClickListener(view -> {
            Intent intent = new Intent(FortgotPassword.this, LoginActivity.class);
            startActivity(intent);
        });



    }
}