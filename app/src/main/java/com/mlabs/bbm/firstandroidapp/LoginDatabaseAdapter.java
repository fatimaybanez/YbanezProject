package com.mlabs.bbm.firstandroidapp;

/**
 * Created by ybanez on 9/22/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDatabaseAdapter {
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;


    static final String DATABASE_CREATE = "create table " + "LOGIN" +
            "( " + "ID" + " integer primary key autoincrement," + " FIRSTNAME text,LASTNAME text,USERNAME text UNIQUE,EMAIL text,PASSWORD text); ";

    public SQLiteDatabase db;

    private final Context context;

    private DatabaseHelper dbHelper;

    public LoginDatabaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public LoginDatabaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void insertEntry(String fName, String lName, String uName, String email, String password) {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("FIRSTNAME", fName);
        newValues.put("LASTNAME", lName);
        newValues.put("USERNAME", uName);
        newValues.put("EMAIL", email);
        newValues.put("PASSWORD", password);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
          }

    public int deleteEntry(String email) {
        //String id=String.valueOf(ID);
        String where = "USERNAME=?";
        int numberOFEntriesDeleted = db.delete("LOGIN", where, new String[]{email});
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }


    public String getSingleEntry(String uName) {
        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{uName}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }
    public String getSingleEntry2(String email) {
        Cursor cursor = db.query("LOGIN", null, " EMAIL=?", new String[]{email}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }
    public String getSignUpUsername(String uname) {


        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{uname}, null, null, null);
        if (cursor.getCount() < 1)
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("USERNAME"));
        cursor.close();
        return password;
    }

    public String getSignUpEmail(String email) {


        Cursor cursor = db.query("LOGIN", null, " EMAIL=?", new String[]{email}, null, null, null);
        if (cursor.getCount() < 1)
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("EMAIL"));
        cursor.close();
        return password;
    }

    public void updateEntry(String email, String password) {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", email);
        updatedValues.put("PASSWORD", password);

        String where = "USERNAME = ?";
        db.update("LOGIN", updatedValues, where, new String[]{email});
    }
}
