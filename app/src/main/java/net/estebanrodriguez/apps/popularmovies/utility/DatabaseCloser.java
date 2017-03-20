package net.estebanrodriguez.apps.popularmovies.utility;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.estebanrodriguez.apps.popularmovies.local_database.DatabaseContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spoooon on 3/20/17.
 */

public class DatabaseCloser {
    private static List<SQLiteDatabase> mSQLiteDatabases  = new ArrayList<>();
    private static final String LOG_TAG = DatabaseCloser.class.getSimpleName();

    public static void addDatabase(SQLiteDatabase sqLiteDatabase){
        mSQLiteDatabases.add(sqLiteDatabase);
    }

    public static void closeAllDatabases(){
        for(SQLiteDatabase db : mSQLiteDatabases){
            if(db.isOpen()){
                db.close();
            }
        }
        mSQLiteDatabases.clear();
    }

}
