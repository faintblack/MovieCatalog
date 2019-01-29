package com.system.perfect.tugas2.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import static com.system.perfect.tugas2.provider.DatabaseContract.AUTHOR;
import static com.system.perfect.tugas2.provider.DatabaseContract.CONTENT_URI;
import static com.system.perfect.tugas2.provider.DatabaseContract.TABLE_FAVORITE;

public class FavoriteProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher uMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private FavoriteHelper helper;

    static {
        uMatcher.addURI(AUTHOR, TABLE_FAVORITE, MOVIE);
        uMatcher.addURI(AUTHOR,TABLE_FAVORITE+ "/#",MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        helper = new FavoriteHelper(getContext());
        helper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch(uMatcher.match(uri)){
            case MOVIE:
                cursor = helper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = helper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long added ;

        switch (uMatcher.match(uri)){
            case MOVIE:
                added = helper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (uMatcher.match(uri)) {
            case MOVIE_ID:
                deleted =  helper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updated ;
        switch (uMatcher.match(uri)) {
            case MOVIE_ID:
                updated =  helper.updateProvider(uri.getLastPathSegment(),values);
                break;
            default:
                updated = 0;
                break;
        }
        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
