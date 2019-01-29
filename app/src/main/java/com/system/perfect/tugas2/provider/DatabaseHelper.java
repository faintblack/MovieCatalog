package com.system.perfect.tugas2.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbmoviefavorite";
    private static final int DATABASE_VERSION = 1;

    public static String TABLE = DatabaseContract.TABLE_FAVORITE;
    public static String ID = "id";
    public static String TITLE = "title";
    public static String DESCRIPTION = "description";
    public static String RELEASE_DATE = "release_date";
    public static String POSTER = "poster";

    private static final String CREATE_TABLE_FAVORITE = "CREATE TABLE " + TABLE + " (" +
            ID + " integer primary key, " +
            TITLE + " text not null, " +
            DESCRIPTION + " text not null, " +
            RELEASE_DATE + " text not null, " +
            POSTER + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

}
