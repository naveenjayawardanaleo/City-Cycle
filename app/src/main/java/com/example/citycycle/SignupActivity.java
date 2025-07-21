package com.example.citycycle;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {


    private EditText etName, etEmail, etContact, etDob, etPassword;
    private RadioGroup rgGender;

    private ImageView imgProfile;
    private CheckBox cbTerms;
    private TextView tvLogin;
    private String imagePath;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        
        dbHelper = new DBHelper(this);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etContact = findViewById(R.id.etContact);
        etDob = findViewById(R.id.etDob);
        etPassword = findViewById(R.id.etPassword);
        rgGender = findViewById(R.id.rgGender);

        imgProfile = findViewById(R.id.imgProfile);
        cbTerms = findViewById(R.id.cbTerms);
        tvLogin = findViewById(R.id.tvLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        etDob.setOnClickListener(view -> showDatePicker());

        imgProfile.setOnClickListener(view -> selectImageFromGallery());

        btnRegister.setOnClickListener(view -> registerUser());

        tvLogin.setOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            etDob.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void selectImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imgProfile.setImageURI(imageUri);

            imagePath = saveImageToStorage(imageUri);
        }
    }

    private String saveImageToStorage(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            File directory = getFilesDir();
            File file = new File(directory, "profile_" + System.currentTimeMillis() + ".jpg");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                return file.getAbsolutePath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void registerUser() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String contact = etContact.getText().toString();
        String dob = etDob.getText().toString();
        String password = etPassword.getText().toString();

        int selectedGenderId = rgGender.getCheckedRadioButtonId();
        String gender = selectedGenderId != -1 ? ((RadioButton) findViewById(selectedGenderId)).getText().toString() : "";

        if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || dob.isEmpty() || password.isEmpty() || gender.isEmpty() || imagePath == null) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Please agree to Terms and Conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean result = dbHelper.insertUser(name, email, contact, dob, gender, password, imagePath);

        if (result) {
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Error registering user!", Toast.LENGTH_SHORT).show();
        }
    }
}

