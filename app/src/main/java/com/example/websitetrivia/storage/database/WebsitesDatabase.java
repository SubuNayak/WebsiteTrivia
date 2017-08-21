package com.example.websitetrivia.storage.database;

// CRUD Operations

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.websitetrivia.domain.model.Website;
import com.example.websitetrivia.domain.repository.WebsitesDataSource;
import com.example.websitetrivia.storage.data.WebsitesPersistenceContract;

import java.util.ArrayList;
import java.util.List;

public class WebsitesDatabase implements WebsitesDataSource {
    private static WebsitesDatabase INSTANCE;
    private WebsitesDBHelper mDBHelper;

    private WebsitesDatabase(Context context){
        if(context!=null){
            mDBHelper = new WebsitesDBHelper(context);
        }
    }

    public static WebsitesDatabase getInstance(Context context){
        if(INSTANCE==null){
            INSTANCE = new WebsitesDatabase(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private String[] getDefaultProjection(){
        return new String[]{
                WebsitesPersistenceContract.WebsiteEntry.COLUMN_ENTRY_ID,
                WebsitesPersistenceContract.WebsiteEntry.COLUMN_NAME,
                WebsitesPersistenceContract.WebsiteEntry.COLUMN_FOUNDING_YEAR,
                WebsitesPersistenceContract.WebsiteEntry.COLUMN_FOUNDERS,
                WebsitesPersistenceContract.WebsiteEntry.COLUMN_LOCATION,
                WebsitesPersistenceContract.WebsiteEntry.COLUMN_CEO,
                WebsitesPersistenceContract.WebsiteEntry.COLUMN_RANK,
                WebsitesPersistenceContract.WebsiteEntry.COLUMN_TIME_SPENT
        };
    }

    // Retrieve - Returns all Websites
    @Override
    public void getWebsites(GetWebsitesCallback callback) {
        List<Website> websites = new ArrayList<Website>();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String[] projection = getDefaultProjection();
        Cursor c = db.query(WebsitesPersistenceContract.WebsiteEntry.TABLE_NAME, projection, null,
                            null, null, null,null);
        if(c!=null&&c.getCount()>0){
                while(c.moveToNext()) {
                    String id = c.getString(c.getColumnIndexOrThrow(WebsitesPersistenceContract.WebsiteEntry.COLUMN_ENTRY_ID));
                    String name = c.getString(c.getColumnIndexOrThrow(WebsitesPersistenceContract.WebsiteEntry.COLUMN_NAME));
                    int foundingYear = c.getInt(c.getColumnIndexOrThrow(WebsitesPersistenceContract.WebsiteEntry.COLUMN_FOUNDING_YEAR));
                    String founders = c.getString(c.getColumnIndexOrThrow(WebsitesPersistenceContract.WebsiteEntry.COLUMN_FOUNDERS));
                    String location = c.getString(c.getColumnIndexOrThrow(WebsitesPersistenceContract.WebsiteEntry.COLUMN_LOCATION));
                    String CEO = c.getString(c.getColumnIndexOrThrow(WebsitesPersistenceContract.WebsiteEntry.COLUMN_CEO));
                    int rank = c.getInt(c.getColumnIndexOrThrow(WebsitesPersistenceContract.WebsiteEntry.COLUMN_RANK));
                    String timeSpent = c.getString(c.getColumnIndexOrThrow(WebsitesPersistenceContract.WebsiteEntry.COLUMN_TIME_SPENT));
                    Website website = new Website(id, name, foundingYear, founders, location, CEO, rank, timeSpent);
                    websites.add(website);
                }
        }
        if(c!=null){
            c.close();
        }
        db.close();
        if(websites.isEmpty()){
            callback.onDataNotAvailable();
        } else {
            callback.onGetWebsites(websites);
        }

    }

    // CREATE
    @Override
    public void addWebsite(Website website, AddWebsiteCallback callback) {
        long result=-1;
        if(website !=null&&callback!=null){
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            result = db.insert(WebsitesPersistenceContract.WebsiteEntry.TABLE_NAME, null, assembleContentValues(true, website));
            db.close();
            if (result!=-1) {
                callback.onWebsiteAdded();
                return;
            } else {
                callback.onWebsiteAddedError();
                return;
            }
        }
        if(callback!=null){
            callback.onWebsiteAddedError();
        }
    }

    // UPDATE
    @Override
    public void updateWebsite(Website website, UpdateWebsiteCallback callback) {
        if (website !=null&&callback!=null) {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            String selection = WebsitesPersistenceContract.WebsiteEntry.COLUMN_ENTRY_ID + " LIKE ?";
            String[] selectionArgs = {website.getId()};
            db.update(WebsitesPersistenceContract.WebsiteEntry.TABLE_NAME, assembleContentValues(false, website),
                    selection, selectionArgs);
            db.close();
            callback.onWebsiteUpdated(website);
            return;
        }
        if(callback!=null){
            callback.onUpdateWebsiteError();
        }

    }

    private ContentValues assembleContentValues(boolean needId, Website website){
        ContentValues contentValues = new ContentValues();
        if (needId) {
            contentValues.put(WebsitesPersistenceContract.WebsiteEntry.COLUMN_ENTRY_ID, website.getId());
        }
        contentValues.put(WebsitesPersistenceContract.WebsiteEntry.COLUMN_NAME, website.getName());
        contentValues.put(WebsitesPersistenceContract.WebsiteEntry.COLUMN_FOUNDING_YEAR, website.getFoundingYear());
        contentValues.put(WebsitesPersistenceContract.WebsiteEntry.COLUMN_FOUNDERS, website.getFounders());
        contentValues.put(WebsitesPersistenceContract.WebsiteEntry.COLUMN_LOCATION, website.getLocation());
        contentValues.put(WebsitesPersistenceContract.WebsiteEntry.COLUMN_CEO, website.getCEO());
        contentValues.put(WebsitesPersistenceContract.WebsiteEntry.COLUMN_RANK, website.getRank());
        contentValues.put(WebsitesPersistenceContract.WebsiteEntry.COLUMN_TIME_SPENT, website.getTimeSpent());
        return contentValues;
    }

    // RETRIEVE - Return one website.
    @Override
    public void getIndividualWebsite(String id, GetIndividualWebsiteCallback callback) {
        if (id!=null&&callback!=null) {
            SQLiteDatabase db = mDBHelper.getReadableDatabase();
            String[] projection = getDefaultProjection();
            String selection = WebsitesPersistenceContract.WebsiteEntry.COLUMN_ENTRY_ID + " LIKE ?";
            String[] selectionArgs = {id};
            Cursor cursor = db.query(WebsitesPersistenceContract.WebsiteEntry.TABLE_NAME, projection,
                                selection, selectionArgs, null, null, null);
            Website website = null;

            if (cursor!=null&&cursor.getCount()>0) {
                cursor.moveToFirst();
                String websiteId = cursor.getString(cursor.getColumnIndexOrThrow(
                        WebsitesPersistenceContract.WebsiteEntry.COLUMN_ENTRY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(
                        WebsitesPersistenceContract.WebsiteEntry.COLUMN_NAME));
                int foundingYear = cursor.getInt(cursor.getColumnIndexOrThrow(
                                WebsitesPersistenceContract.WebsiteEntry.COLUMN_FOUNDING_YEAR));
                String founders = cursor.getString(cursor.getColumnIndexOrThrow(
                        WebsitesPersistenceContract.WebsiteEntry.COLUMN_FOUNDERS));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(
                        WebsitesPersistenceContract.WebsiteEntry.COLUMN_LOCATION));
                String CEO = cursor.getString(cursor.getColumnIndexOrThrow(
                        WebsitesPersistenceContract.WebsiteEntry.COLUMN_CEO));
                int rank = cursor.getInt(cursor.getColumnIndexOrThrow(
                        WebsitesPersistenceContract.WebsiteEntry.COLUMN_CEO));
                String timeSpent = cursor.getString(cursor.getColumnIndexOrThrow(
                        WebsitesPersistenceContract.WebsiteEntry.COLUMN_TIME_SPENT));
                website = new Website(websiteId, name, foundingYear, founders, location, CEO, rank, timeSpent);
            }
            if (cursor!=null) {
                cursor.close();
            }
            db.close();
            if (website !=null) {
                callback.onGetIndividualWebsite(website);
            } else {
                callback.onDataNotAvailable();
            }
        }
    }

    // Delete
    @Override
    public void deleteWebsite(String id, DeleteWebsiteCallback callback) {
        if(id!=null&&callback!=null) {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            String selection = WebsitesPersistenceContract.WebsiteEntry.COLUMN_ENTRY_ID + " LIKE ?";
            String[] selectionargs = {id};
            db.delete(WebsitesPersistenceContract.WebsiteEntry.TABLE_NAME, selection, selectionargs);
            db.close();
            callback.onWebsiteDeleted();
            return;
        }
        if(callback!=null){
            callback.onWebsiteDeleteError();
        }
    }
}
