package com.example.testone_lesh;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

public class FunkoDBProvider extends ContentProvider {

    public final static String DBNAME = "FunkoDatabase";
    public final static String TABLE_NAME = "Funkos";
    public final static String COLUMN_NAME = "Name";
    public final static String COLUMN_NUMBER = "Number";
    public final static String COLUMN_TYPE = "Type";
    public final static String COLUMN_FANDOM = "Fandom";
    public final static String COLUMN_ON = "_On";
    public final static String COLUMN_ULTIMATE = "Ultimate";
    public final static String COLUMN_PRICE = "Price";
    public final static String AUTHORITY = "com.funko.provider";
    private MainDatabaseHelper mOpenHelper;

    protected static final class MainDatabaseHelper extends SQLiteOpenHelper{ //Object we are using to preform operations to the database
        MainDatabaseHelper(@Nullable Context context){super(context, DBNAME, null, 1);}
        @Override
        public void onCreate(SQLiteDatabase db) {db.execSQL(SQL_CREATE_MAIN);}
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    };
    public final static Uri CONTENT_URI = Uri.parse("content://" +
            AUTHORITY + "/" + TABLE_NAME);
    private static final String SQL_CREATE_MAIN = "CREATE TABLE " + TABLE_NAME +
            "(" + " _ID INTEGER PRIMARY KEY, " +
            COLUMN_NAME + " TEXT," +
            COLUMN_NUMBER + " TEXT," +
            COLUMN_TYPE + " TEXT," +
            COLUMN_FANDOM + " TEXT," +
            COLUMN_ON + " TEXT," +
            COLUMN_ULTIMATE + " TEXT," +
            COLUMN_PRICE + " TEXT)";
                // The all type text?
    public FunkoDBProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return mOpenHelper.getWritableDatabase().delete(TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // should check to make sure nothing is null and trim off excess
        long id = mOpenHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
        return Uri.withAppendedPath(CONTENT_URI, "" + id);
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MainDatabaseHelper(getContext());
        return true;
    }

    @Override
    // whenever we get a query we recieve the elements and pass it into the database
    // becuase we only have one table we are not worried about the Uri we are receiving (it can only be one)
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
       return mOpenHelper.getReadableDatabase().query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    //the selection and selection arg tells us which row in the table will be update with value
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return mOpenHelper.getWritableDatabase().update(TABLE_NAME, values, selection, selectionArgs);
    }
}