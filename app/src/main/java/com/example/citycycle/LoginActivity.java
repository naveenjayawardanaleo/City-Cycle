package com.example.citycycle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvLogin, forgotPassword;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvLogin = findViewById(R.id.tvLogin);
        forgotPassword = findViewById(R.id.forgotPassword);



        dbHelper = new DBHelper(this);

        btnLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.checkUser(email, password)) {
                Toast.makeText(this, "Welcome to CityCycle", Toast.LENGTH_SHORT).show();



                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.apply();


                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("email", email); // Corrected email passing
                startActivity(intent);

                finish();
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

        tvLogin.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
        forgotPassword.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, FortgotPassword.class);
            startActivity(intent);
        });
    }
}
