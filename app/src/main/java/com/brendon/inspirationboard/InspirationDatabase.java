package com.brendon.inspirationboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    //  Gets all the data for the database.
    public Cursor getAllData() {

        Cursor cursor = db.query(DB_TABLE, null, null, null, null, null, DATE_COL + " DESC");
        return cursor;

    }

    // Search query for user from the search field.
    public Cursor getSearchData(String search) {


        String where = HASHTAG_COL + " LIKE  ? " + " OR " + NOTES_COL + " LIKE ? ";

        String whereArgs[] = { "%" + search + "%" , "%" + search + "%"};

        Cursor cursor = db.query(DB_TABLE, null, where, whereArgs, null, null, null );

        return cursor;
    }




    public byte[] getPhoto(String hashTag) {

        byte[] photo = new byte[0];

        String cols[] = { PICTURE_COL };

        String where = HASHTAG_COL + " = ? ";

        String whereArgs[] = { hashTag };

        Cursor cursor = db.query(DB_TABLE, cols, where, whereArgs, null, null, null);

        try {

            cursor.moveToFirst();

            photo = cursor.getBlob(cursor.getColumnIndex(PICTURE_COL));



        } finally {

            cursor.close();
        }

        return photo;

    }

    // Returns the full text note and the date it was created to the NoteFragment.
    public List getNote(String note) {

        List noteList = new ArrayList();

        String cols[] = { NOTES_COL, DATE_COL };

        String where = NOTES_COL + " LIKE ? ";

        String whereArgs[] = { "%" + note + "%" };

        Cursor cursor = db.query(DB_TABLE, cols, where, whereArgs, null, null, null );

        String fullNote = "";
        long date = 0;

        try {

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {

                fullNote = cursor.getString(cursor.getColumnIndex(NOTES_COL));

                date = cursor.getLong(cursor.getColumnIndex(DATE_COL));

                noteList.add(fullNote);
                noteList.add(date);

                if (noteList.size() == 2) {

                    break;

                } else {

                    cursor.moveToNext();

                }

            }

        } finally{

            cursor.close();
        }

        return noteList;

    }

    public void deletePhoto(String tag) {

        String where = HASHTAG_COL + " = ? ";

        String[] whereArgs = { tag };

        db.delete(DB_TABLE, where, whereArgs);


    }

    public void deletNoteEntry(String note) {


        String where = NOTES_COL + " LIKE ? ";

        String whereArgs[] = { "%" + note + "%" };

        db.delete(DB_TABLE, where, whereArgs);

    }



    public boolean updateNote(String newNote, long dateCreated) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_COL, newNote);

        String where = DATE_COL + " = ? ";

        String[] whereArgs = { Long.toString(dateCreated) };

        int rowMod = db.update(DB_TABLE, contentValues, where, whereArgs);

        if (rowMod == 1) {

            return true;

        } else {

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





