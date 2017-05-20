package com.estebanmoncaleano.flickrclone.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FlickrDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FlickrDataBase.db";
    private static final int DATABASE_VERSION = 1;

    public FlickrDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PHOTOLIST_TABLE = "CREATE TABLE " +
                FlickrContract.PhotoListEntry.TABLE_NAME + "( " +
                FlickrContract.PhotoListEntry._ID + " INTEGER PRIMARY KEY, " +
                FlickrContract.PhotoListEntry.OWNER + " TEXT NOT NULL, " +
                FlickrContract.PhotoListEntry.SECRET + " TEXT NOT NULL, " +
                FlickrContract.PhotoListEntry.SERVER + " INTEGER NOT NULL, " +
                FlickrContract.PhotoListEntry.FARM + " INTEGER NOT NULL, " +
                FlickrContract.PhotoListEntry.TITLE + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_PHOTOLIST_TABLE);

        final String SQL_CREATE_PERSONLIST_TABLE = "CREATE TABLE " +
                FlickrContract.PersonListEntry.TABLE_NAME + "( " +
                FlickrContract.PersonListEntry._ID + " INTEGER PRIMARY KEY, " +
                FlickrContract.PersonListEntry.USERNAME + " TEXT NOT NULL, " +
                FlickrContract.PersonListEntry.NSID + " TEXT NOT NULL, " +
                FlickrContract.PersonListEntry.REALNAME + " TEXT NOT NULL, " +
                FlickrContract.PersonListEntry.LOCATION + " TEXT NOT NULL, " +
                FlickrContract.PersonListEntry.DESCRIPTION + " TEXT NOT NULL, " +
                FlickrContract.PersonListEntry.PHOTO_URL + " TEXT NOT NULL, " +
                FlickrContract.PersonListEntry.PROFILE_URL + " TEXT NOT NULL, " +
                FlickrContract.PersonListEntry.MOBILE_URL + " TEXT NOT NULL, " +
                FlickrContract.PersonListEntry.FIRST_DATE_TAKEN + " TEXT NOT NULL, " +
                FlickrContract.PersonListEntry.COUNT + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_PERSONLIST_TABLE);

        final String SQL_CREATE_GROUPLIST_TABLE = "CREATE TABLE " +
                FlickrContract.GroupListEntry.TABLE_NAME + "( " +
                FlickrContract.GroupListEntry._ID + " INTEGER PRIMARY KEY, " +
                FlickrContract.GroupListEntry.NAME + " TEXT NOT NULL, " +
                FlickrContract.GroupListEntry.DESCRIPTION + " TEXT NOT NULL, " +
                FlickrContract.GroupListEntry.RULES + " TEXT NOT NULL, " +
                FlickrContract.GroupListEntry.MEMBERS + " INTEGER NOT NULL, " +
                FlickrContract.GroupListEntry.TOPIC_COUNT + " INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_GROUPLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FlickrContract.PhotoListEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FlickrContract.PersonListEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FlickrContract.GroupListEntry.TABLE_NAME);
        onCreate(db);
    }
}
