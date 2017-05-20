package com.estebanmoncaleano.flickrclone.data.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FlickrContentProvider extends ContentProvider {

    private static final String TAG = FlickrContentProvider.class.getSimpleName();
    public static final int PHOTO = 100;
    public static final int PHOTO_WITH_ID = 101;
    public static final int PERSON = 200;
    public static final int PERSON_WITH_ID = 201;
    public static final int GROUP = 300;
    public static final int GROUP_WITH_ID = 301;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Directories
        uriMatcher.addURI(FlickrContract.AUTHORITY, FlickrContract.PATH_PHOTOS, PHOTO);
        uriMatcher.addURI(FlickrContract.AUTHORITY, FlickrContract.PATH_PERSONS, PERSON);
        uriMatcher.addURI(FlickrContract.AUTHORITY, FlickrContract.PATH_GROUPS, GROUP);

        // Single Item
        uriMatcher.addURI(FlickrContract.AUTHORITY, FlickrContract.PATH_PHOTOS + "/#", PHOTO_WITH_ID);
        uriMatcher.addURI(FlickrContract.AUTHORITY, FlickrContract.PATH_PERSONS + "/#", PERSON_WITH_ID);
        uriMatcher.addURI(FlickrContract.AUTHORITY, FlickrContract.PATH_GROUPS + "/#", GROUP_WITH_ID);

        return uriMatcher;
    }

    private FlickrDBHelper flickrDBHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        flickrDBHelper = new FlickrDBHelper(context);
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case PHOTO:
                // directory
                return "vnd.android.cursor.dir" + "/" + FlickrContract.AUTHORITY + "/" + FlickrContract.PATH_PHOTOS;
            case PHOTO_WITH_ID:
                // single item type
                return "vnd.android.cursor.item" + "/" + FlickrContract.AUTHORITY + "/" + FlickrContract.PATH_PHOTOS;

            case PERSON:
                // directory
                return "vnd.android.cursor.dir" + "/" + FlickrContract.AUTHORITY + "/" + FlickrContract.PATH_PERSONS;
            case PERSON_WITH_ID:
                // single item type
                return "vnd.android.cursor.item" + "/" + FlickrContract.AUTHORITY + "/" + FlickrContract.PATH_PERSONS;

            case GROUP:
                // directory
                return "vnd.android.cursor.dir" + "/" + FlickrContract.AUTHORITY + "/" + FlickrContract.PATH_GROUPS;
            case GROUP_WITH_ID:
                // single item type
                return "vnd.android.cursor.item" + "/" + FlickrContract.AUTHORITY + "/" + FlickrContract.PATH_GROUPS;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = flickrDBHelper.getReadableDatabase();

        Cursor retCursor;
        String id;
        switch (uriMatcher.match(uri)) {
            case PHOTO:
                retCursor = db.query(
                        FlickrContract.PhotoListEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case PHOTO_WITH_ID:
                id = uri.getPathSegments().get(1);

                String mSelectionApp = "_id?";
                String[] mSelectionAppArgs = new String[]{id};

                retCursor = db.query(
                        FlickrContract.PhotoListEntry.TABLE_NAME,
                        projection,
                        mSelectionApp,
                        mSelectionAppArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case PERSON:
                retCursor = db.query(
                        FlickrContract.PersonListEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case PERSON_WITH_ID:
                id = uri.getPathSegments().get(1);

                String mSelectionPerson = "_id?";
                String[] mSelectionPersonArgs = new String[]{id};

                retCursor = db.query(
                        FlickrContract.PersonListEntry.TABLE_NAME,
                        projection,
                        mSelectionPerson,
                        mSelectionPersonArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case GROUP:
                id = uri.getPathSegments().get(1);

                retCursor = db.query(
                        FlickrContract.GroupListEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case GROUP_WITH_ID:
                id = uri.getPathSegments().get(1);

                String mSelectionGroup = "_id?";
                String[] mSelectionGroupArgs = new String[]{id};

                retCursor = db.query(
                        FlickrContract.GroupListEntry.TABLE_NAME,
                        projection,
                        mSelectionGroup,
                        mSelectionGroupArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = flickrDBHelper.getWritableDatabase();

        Uri returnUri = null;
        long id;

        switch (uriMatcher.match(uri)) {
            case PHOTO:
                id = db.insert(FlickrContract.PhotoListEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(FlickrContract.PhotoListEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            case PERSON:
                id = db.insert(FlickrContract.PersonListEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(FlickrContract.PersonListEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            case GROUP:
                id = db.insert(FlickrContract.GroupListEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(FlickrContract.GroupListEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = flickrDBHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case PHOTO:
                db.beginTransaction();
                int rowsPhotoInserted = 0;

                try {
                    for (ContentValues value : values) {
                        long id = db.insert(FlickrContract.PhotoListEntry.TABLE_NAME, null, value);
                        if (id != -1) {
                            rowsPhotoInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsPhotoInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsPhotoInserted;

            case PERSON:
                db.beginTransaction();
                int rowsPersonInserted = 0;

                try {
                    for (ContentValues value : values) {
                        long id = db.insert(FlickrContract.PersonListEntry.TABLE_NAME, null, value);

                        if (id != -1) {
                            rowsPersonInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsPersonInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsPersonInserted;

            case GROUP:
                db.beginTransaction();
                int rowsGroupInserted = 0;

                try {
                    for (ContentValues value : values) {
                        long id = db.insert(FlickrContract.GroupListEntry.TABLE_NAME, null, value);

                        if (id != -1) {
                            rowsGroupInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsGroupInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsGroupInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = flickrDBHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case PHOTO:
                // directory
                int rowsPhotoDeleted = db.delete(
                        FlickrContract.PhotoListEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );

                if (rowsPhotoDeleted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsPhotoDeleted;

            case PERSON:
                // directory
                int rowsPersonDeleted = db.delete(
                        FlickrContract.PersonListEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );

                if (rowsPersonDeleted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsPersonDeleted;

            case GROUP:
                // directory
                int rowsGroupDeleted = db.delete(
                        FlickrContract.GroupListEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );

                if (rowsGroupDeleted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsGroupDeleted;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }
}
