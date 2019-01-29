package com.system.perfect.tugas2.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.system.perfect.tugas2.provider.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.system.perfect.tugas2.provider.DatabaseContract.MovieColumns.POSTER;
import static com.system.perfect.tugas2.provider.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.system.perfect.tugas2.provider.DatabaseContract.MovieColumns.TITLE;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbmoviefavorite";
    private static final int DATABASE_VERSION = 1;

    public static String TABLE = DatabaseContract.TABLE_FAVORITE;
    public static String COLUMN_ID = _ID;
    public static String COLUMN_TITLE = TITLE;
    public static String COLUMN_DESCRIPTION = DESCRIPTION;
    public static String COLUMN_RELEASE_DATE = RELEASE_DATE;
    public static String COLUMN_POSTER = POSTER;

    public static final String CREATE_TABLE_FAVORITE = "CREATE TABLE " + TABLE + " (" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TITLE + " text not null, " +
            COLUMN_DESCRIPTION + " text not null, " +
            COLUMN_RELEASE_DATE + " text not null, " +
            COLUMN_POSTER + " text not null);";

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
