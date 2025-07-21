package com.example.citycycle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private DBHelper dbHelper;
    private EditText nameEditText, contactEditText, dobEditText, genderEditText;
    private ImageView profileImageView;
    private Button updateButton, selectImageButton;
    private String userEmail;
    private String selectedImagePath; // Store selected image path
    private Button backtoHome;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new DBHelper(this);
        nameEditText = findViewById(R.id.editTextName);
        contactEditText = findViewById(R.id.editTextContact);
        dobEditText = findViewById(R.id.editTextDob);
        genderEditText = findViewById(R.id.editTextGender);
        profileImageView = findViewById(R.id.profileImageView);
        updateButton = findViewById(R.id.updateButton);
        selectImageButton = findViewById(R.id.selectImageButton);
        backtoHome = findViewById(R.id.backtoHome);
        logout = findViewById(R.id.logout);


        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", null);

        if (userEmail == null) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if no user email is found
            return;
        }


        loadUserProfile();


        updateButton.setOnClickListener(view -> updateProfile());


        selectImageButton.setOnClickListener(view -> openGallery());


        backtoHome.setOnClickListener(view -> {
            Intent homepage = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(homepage);
        });

        //logout button with session destroy
        logout.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent loginPage = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(loginPage);
            finish();
        });
    }

    private void loadUserProfile() {
        Cursor cursor = dbHelper.getUserDetails(userEmail);
        if (cursor != null && cursor.moveToFirst()) {
            nameEditText.setText(cursor.getString(cursor.getColumnIndex("name")));
            contactEditText.setText(cursor.getString(cursor.getColumnIndex("contact")));
            dobEditText.setText(cursor.getString(cursor.getColumnIndex("dob")));
            genderEditText.setText(cursor.getString(cursor.getColumnIndex("gender")));

            String imagePath = cursor.getString(cursor.getColumnIndex("profile_image"));
            if (imagePath != null && !imagePath.isEmpty()) {
                File imgFile = new File(imagePath);
                if (imgFile.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                    profileImageView.setImageBitmap(bitmap);
                }
            }
            cursor.close();
        }
    }

    private void updateProfile() {
        String name = nameEditText.getText().toString().trim();
        String contact = contactEditText.getText().toString().trim();
        String dob = dobEditText.getText().toString().trim();
        String gender = genderEditText.getText().toString().trim();

        if (dbHelper.updateUser(userEmail, name, contact, dob, gender, selectedImagePath)) {
            Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {

            selectedImagePath = data.getData().toString();
            profileImageView.setImageURI(data.getData());
        }
    }
}
