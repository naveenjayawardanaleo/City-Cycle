package com.example.citycycle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CityCyclefinal.db";
    private static final int DATABASE_VERSION = 1;


    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_CONTACT = "contact";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PROFILE_IMAGE = "profile_image";

    // Rentals Table
    public static final String TABLE_RENTALS = "rentals";
    public static final String COLUMN_BIKE_NAME = "bikeName";
    public static final String COLUMN_BIKE_ID = "bikeId";
    public static final String COLUMN_PRICE = "price";


    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_EMAIL + " TEXT UNIQUE, "
            + COLUMN_CONTACT + " TEXT, "
            + COLUMN_DOB + " TEXT, "
            + COLUMN_GENDER + " TEXT, "
            + COLUMN_PASSWORD + " TEXT, "
            + COLUMN_PROFILE_IMAGE + " TEXT);";


    private static final String CREATE_TABLE_RENTALS = "CREATE TABLE " + TABLE_RENTALS + " ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_BIKE_NAME + " TEXT, "
            + COLUMN_BIKE_ID + " TEXT, "
            + COLUMN_PRICE + " REAL);";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_RENTALS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RENTALS);
        onCreate(db);
    }

    // Insert User
    public boolean insertUser(String name, String email, String contact, String dob, String gender, String password, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_CONTACT, contact);
        values.put(COLUMN_DOB, dob);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PROFILE_IMAGE, imagePath);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email = ? AND password = ?",
                new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


    public boolean updateUser(String email, String name, String contact, String dob, String gender, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CONTACT, contact);
        values.put(COLUMN_DOB, dob);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_PROFILE_IMAGE, imagePath);

        int rowsUpdated = db.update(TABLE_USERS, values, "email=?", new String[]{email});
        return rowsUpdated > 0;
    }


    public Cursor getUserDetails(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email=?", new String[]{email});
    }


    public void insertRental(String bikeName, String bikeId, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BIKE_NAME, bikeName);
        values.put(COLUMN_BIKE_ID, bikeId);
        values.put(COLUMN_PRICE, price);
        db.insert(TABLE_RENTALS, null, values);
        db.close();
    }


    public Cursor getAllRentals() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_RENTALS, null, null, null, null, null, null);
    }
}