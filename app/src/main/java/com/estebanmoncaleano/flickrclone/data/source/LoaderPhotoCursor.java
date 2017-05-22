package com.estebanmoncaleano.flickrclone.data.source;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.estebanmoncaleano.flickrclone.PhotoInfoActivity;
import com.estebanmoncaleano.flickrclone.PhotoSearchActivity;
import com.estebanmoncaleano.flickrclone.data.adapter.PhotoCursorAdapter;
import com.estebanmoncaleano.flickrclone.sync.AsyncLoaderPhoto;

public class LoaderPhotoCursor implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int TASK_CURSOR_PHOTO = 10;
    public static final int TASK_CURSOR_SEARCH_PHOTO = 11;

    private Context context;
    private PhotoCursorAdapter photoCursorAdapter;
    private PhotoInfoActivity activity;

    public LoaderPhotoCursor(Context context, PhotoCursorAdapter adapter) {
        this.context = context;
        this.photoCursorAdapter = adapter;
    }

    public LoaderPhotoCursor(Context context, PhotoInfoActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        AsyncLoaderPhoto loaderPhoto = new AsyncLoaderPhoto();
        switch (id) {
            case TASK_CURSOR_PHOTO:
                return loaderPhoto.getPhotoCursor(this.context, args);

            case TASK_CURSOR_SEARCH_PHOTO:
                return loaderPhoto.getPhotoSearchCursor(this.context, args);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case TASK_CURSOR_PHOTO:
                activity.setPhotoCursorInfo(cursor);
                break;

            case TASK_CURSOR_SEARCH_PHOTO:
                photoCursorAdapter.setPhotoCursorData(cursor);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case TASK_CURSOR_PHOTO:
                activity.setPhotoCursorInfo(null);
                break;

            case TASK_CURSOR_SEARCH_PHOTO:
                photoCursorAdapter.setPhotoCursorData(null);
                break;
        }
    }
}
