package com.brendon.inspirationboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/*
This is the database class.
 */
public class InspirationDatabase {

    private Context context;
    private SQLiteDatabase db;
    private SQLHelper helper;

    // DB information
    protected static final String DB_NAME = "Inspiration";
    protected static final String DB_TABLE = "inspiration_table";
    protected static final int DB_VERSION = 1;

    // Column Names
    protected static final String PRIMARY_KEY_COL = "_id";
    protected static final String PICTURE_COL = "Picture";
    protected static final String HASHTAG_COL = "Picture_Hash";
    protected static final String NOTES_COL = "Notes";
    protected static final String DATE_COL = "Date";

    // Tags for error checking.
    private static final String DB_TAG = "Database Manager";
    private static final String SQL_HELPER = "SQLHelper";
    private static final String SQL_TAG = "SQL Problem";


    public InspirationDatabase(Context c ) {

        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();


    }


    public class SQLHelper extends SQLiteOpenHelper {

        public SQLHelper(Context c){

            super(c, DB_NAME, null, DB_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            // I'm storing that date as an Integer rather than a text to avoid having to string format.
            String createDB = "CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s BLOB, %s TEXT, %s TEXT, %s INTEGER )";
            String createSQL = String.format(createDB, DB_TABLE, PRIMARY_KEY_COL, PICTURE_COL, HASHTAG_COL, NOTES_COL, DATE_COL);
            db.execSQL(createSQL);

        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
            Log.w(SQL_HELPER, "Upgrade table - drop and recreate it");

        }


    }

    // Closes the database.
    public void close() {

        helper.close();

    }

    // Adds a new note to the database.
    public boolean addNewNote(String text, long date ) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_COL, text);
        contentValues.put(DATE_COL, date);

        try {

            db.insertOrThrow(DB_TABLE, null, contentValues);

            return true;

        } catch (SQLiteConstraintException sqle) {

            Log.d(SQL_TAG, "Error inserting note into table.");
            return false;
        }

    }

    // Adds a new photo to the database.
    public boolean addNewPhoto(byte[] image, String hashtag, long date) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(PICTURE_COL, image);
        contentValues.put(HASHTAG_COL, hashtag);
        contentValues.put(DATE_COL, date);

        try {

            db.insertOrThrow(DB_TABLE, null, contentValues);

            return true;

        } catch (SQLiteConstraintException sqle) {

            Log.d(SQL_TAG, "Error inserting photo into table.");
            return false;
        }

    }







}





