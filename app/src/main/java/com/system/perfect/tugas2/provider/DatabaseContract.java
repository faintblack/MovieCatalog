package com.system.perfect.tugas2.provider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_FAVORITE = "favorite_movie";
    static final String AUTHOR = "com.mhrdkk.dicoding.moviecatalog";

    static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
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
