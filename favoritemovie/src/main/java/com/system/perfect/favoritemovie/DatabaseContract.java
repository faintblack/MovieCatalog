package com.system.perfect.favoritemovie;

import android.database.Cursor;
import android.net.Uri;

public class DatabaseContract {

    static String TABLE_FAVORITE = "favorite_movie";
    static final String AUTHOR = BuildConfig.AUTHOR;

    public static String ID = "id";
    public static String TITLE = "title";
    public static String DESCRIPTION = "description";
    public static String RELEASE_DATE = "release_date";
    public static String POSTER = "poster";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHOR)
            .appendPath(TABLE_FAVORITE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }

}
