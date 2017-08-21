package com.example.websitetrivia.storage.database;

// SQL Statement for Table

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.websitetrivia.storage.data.WebsitesPersistenceContract;

public class WebsitesDBHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WebsitesTrivia.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEPARATOR = ",";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ WebsitesPersistenceContract.WebsiteEntry.TABLE_NAME
                +" ("+ WebsitesPersistenceContract.WebsiteEntry._ID+TEXT_TYPE+" PRIMARY KEY," +
                WebsitesPersistenceContract.WebsiteEntry.COLUMN_ENTRY_ID+TEXT_TYPE+COMMA_SEPARATOR+
                WebsitesPersistenceContract.WebsiteEntry.COLUMN_NAME+TEXT_TYPE+COMMA_SEPARATOR+
                WebsitesPersistenceContract.WebsiteEntry.COLUMN_FOUNDING_YEAR+INTEGER_TYPE+COMMA_SEPARATOR+
                WebsitesPersistenceContract.WebsiteEntry.COLUMN_FOUNDERS+TEXT_TYPE+COMMA_SEPARATOR+
                WebsitesPersistenceContract.WebsiteEntry.COLUMN_LOCATION+TEXT_TYPE+COMMA_SEPARATOR+
                WebsitesPersistenceContract.WebsiteEntry.COLUMN_CEO+TEXT_TYPE+COMMA_SEPARATOR+
                WebsitesPersistenceContract.WebsiteEntry.COLUMN_RANK+INTEGER_TYPE+COMMA_SEPARATOR+
                WebsitesPersistenceContract.WebsiteEntry.COLUMN_TIME_SPENT+TEXT_TYPE+" )";


    public WebsitesDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Database is opened or read for the first time. Create the first table.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    // Not used in this app, Used to update the database.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
