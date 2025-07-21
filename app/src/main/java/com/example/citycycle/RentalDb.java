//package com.example.citycycle;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class RentalDb extends SQLiteOpenHelper {
//
//    private static final String DATABASE_NAME = "CityCycle.db";
//    private static final int DATABASE_VERSION = 3;
//
//    // Table and column names
//    public static final String TABLE_RENTALS = "rentals";
//    public static final String COLUMN_BIKE_NAME = "bikeName";
//    public static final String COLUMN_BIKE_ID = "bikeId";
//    public static final String COLUMN_PRICE = "price";
//
//    // SQL query to create the rentals table
//    private static final String CREATE_TABLE_RENTALS = "CREATE TABLE " + TABLE_RENTALS + " ("
//            + COLUMN_BIKE_NAME + " TEXT, "
//            + COLUMN_BIKE_ID + " TEXT, "
//            + COLUMN_PRICE + " REAL);";
//
//    // Constructor
//    public RentalDb(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String createTableQuery = "CREATE TABLE IF NOT EXISTS rentals (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "rental_date TEXT, " +
//                "amount REAL);";
//        db.execSQL(createTableQuery);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Drop the old table if it exists and create a new one
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RENTALS);
//        onCreate(db);
//    }
//
//    // Method to insert rental data into the table
//    public void insertRental(String bikeName, String bikeId, double price) {
//        SQLiteDatabase db = this.getWritableDatabase();  // Get writable database
//
//        // Create ContentValues object to hold the data
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_BIKE_NAME, bikeName);
//        values.put(COLUMN_BIKE_ID, bikeId);
//        values.put(COLUMN_PRICE, price);
//
//        // Insert the data into the rentals table
//        db.insert(TABLE_RENTALS, null, values);
//
//        // Close the database connection
//        db.close();
//    }
//    public Cursor getAllRentals() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.query(TABLE_RENTALS, null, null, null, null, null, null);
//    }
//
//}
