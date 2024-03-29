package com.example.moviehub.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moviehub.model.Favorite;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorite.db";
    private static final int DATABASE_VERSION = 2;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE favorites " +
                "(id INTEGER PRIMARY KEY, " +
                "title TEXT, " +
                "type TEXT, " +
                "year INTEGER, " +
                "poster_path TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS favorites");
        onCreate(db);
    }

    public void addFavorite(Favorite fav) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", fav.getId());
        values.put("title", fav.getTitle());
        values.put("type", fav.getType());
        values.put("year", fav.getYear());
        values.put("poster_path", fav.getPosterPath());

        db.insert("favorites", null, values);
        db.close();
    }

    public void deleteFavorite (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("favorites", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Favorite> getAllFavorites(){
        List<Favorite> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] favoritesColumn = {"id", "title", "type", "year", "poster_path"};
        Cursor cursor = db.query("favorites",
                favoritesColumn,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
            int year = cursor.getInt(cursor.getColumnIndexOrThrow("year"));
            String posterPath = cursor.getString(cursor.getColumnIndexOrThrow("poster_path"));
            Favorite fav = new Favorite(id, title, type, year, posterPath);
            list.add(fav);
        }
        cursor.close();
        db.close();

        return list;
    }

    public boolean isFavorite(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM favorites WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }

    public void deleteAllFavorite() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("favorites", null, null);
        db.close();
    }

    public List<Favorite> sortFavorites(String sort) {
        List<Favorite> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM favorites ORDER BY " + sort, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
            int year = cursor.getInt(cursor.getColumnIndexOrThrow("year"));
            String posterPath = cursor.getString(cursor.getColumnIndexOrThrow("poster_path"));
            Favorite fav = new Favorite(id, title, type, year, posterPath);
            list.add(fav);
        }
        cursor.close();
        db.close();
        return list;
    }
}
