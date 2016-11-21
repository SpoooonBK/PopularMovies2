package net.estebanrodriguez.apps.popularmovies.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Spoooon on 11/21/2016.
 */

public class LocalMovieItemProvider extends ContentProvider {

    private LocalMovieDBHelper mLocalMovieDBHelper;
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {

        mLocalMovieDBHelper = new LocalMovieDBHelper(getContext());

        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        db = mLocalMovieDBHelper.getReadableDatabase();
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        db = mLocalMovieDBHelper.getWritableDatabase();


        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
