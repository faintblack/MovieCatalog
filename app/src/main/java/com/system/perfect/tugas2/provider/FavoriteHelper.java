package com.system.perfect.tugas2.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.system.perfect.tugas2.model.Movie;

import java.util.ArrayList;

import static com.system.perfect.tugas2.provider.DatabaseHelper.DESCRIPTION;
import static com.system.perfect.tugas2.provider.DatabaseHelper.ID;
import static com.system.perfect.tugas2.provider.DatabaseHelper.POSTER;
import static com.system.perfect.tugas2.provider.DatabaseHelper.RELEASE_DATE;
import static com.system.perfect.tugas2.provider.DatabaseHelper.TITLE;


public class FavoriteHelper {

    private static String TABLE = DatabaseContract.TABLE_FAVORITE;
    private Context context;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public FavoriteHelper(Context context) {
        this.context = context;
    }

    public FavoriteHelper open () throws SQLException{
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
        return this;
    }

    public void close(){
        helper.close();
    }

    public ArrayList<Movie> getData(){
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = db.query(TABLE,null ,null,null,null,null,TITLE +" ASC"
                ,null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount()>0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));

                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Movie> getDataById(String id){
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + ID + " = '" + id.trim() + "'",null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount()>0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));

                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertDataFavorite(Movie movie){
        ContentValues initVal =  new ContentValues();
        initVal.put(ID, movie.getId());
        initVal.put(TITLE, movie.getTitle());
        initVal.put(DESCRIPTION, movie.getOverview());
        initVal.put(RELEASE_DATE, movie.getReleaseDate());
        initVal.put(POSTER, movie.getPosterPath());
        return db.insert(TABLE, null, initVal);
    }

    public int deleteDataFavorite(String id){
        return db.delete(TABLE, ID + " ='" + id + "'",null);
    }


    public Cursor queryByIdProvider(String id){
        return db.query(TABLE,null,ID + " = ?" ,new String[]{id},null,null,null,null);
    }
    public Cursor queryProvider(){
        return db.query(TABLE,null,null ,null,null,null,ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return db.insert(TABLE,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return db.update(TABLE,values,ID +" = ?",new String[]{id} );
    }
    public int deleteProvider(String id){
        return db.delete(TABLE,ID + " = ?", new String[]{id});
    }

}
