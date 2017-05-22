package com.estebanmoncaleano.flickrclone.data.source;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.estebanmoncaleano.flickrclone.PhotoSearchActivity;
import com.estebanmoncaleano.flickrclone.sync.AsyncLoaderPhoto;

public class LoaderPhotoCursor implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int TASK_CURSOR_PHOTO = 10;
    public static final int TASK_CURSOR_SEARCH_PHOTO = 11;

    private Context context;

    public LoaderPhotoCursor(Context context) {
        this.context = context;
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
                break;

            case TASK_CURSOR_SEARCH_PHOTO:
                PhotoSearchActivity.setPhotoCursorAdapter(cursor);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case TASK_CURSOR_PHOTO:
                break;

            case TASK_CURSOR_SEARCH_PHOTO:
                PhotoSearchActivity.setPhotoCursorAdapter(null);
                break;
        }
    }
}
