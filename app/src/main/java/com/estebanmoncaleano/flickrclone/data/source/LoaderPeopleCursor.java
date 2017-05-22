package com.estebanmoncaleano.flickrclone.data.source;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.estebanmoncaleano.flickrclone.sync.AsyncLoaderPeople;

public class LoaderPeopleCursor implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int TASK_PEOPLE_CURSOR = 21;
    public static final int TASK_PEOPLE_INFO_CURSOR = 22;
    public static final int TASK_PEOPLE_PHOTOS_CURSOR = 23;

    private Context context;

    public LoaderPeopleCursor(Context context) {
        this.context = context;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        AsyncLoaderPeople loaderPeople = new AsyncLoaderPeople();
        switch (id) {
            case TASK_PEOPLE_CURSOR:
                return loaderPeople.getPeopleCursor(context, args);

            case TASK_PEOPLE_INFO_CURSOR:
                return loaderPeople.getPeopleInfoCursor(context, args);

            case TASK_PEOPLE_PHOTOS_CURSOR:
                return loaderPeople.getPeoplePhotosCursor(context, args);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case TASK_PEOPLE_CURSOR:
                break;

            case TASK_PEOPLE_INFO_CURSOR:
                break;

            case TASK_PEOPLE_PHOTOS_CURSOR:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case TASK_PEOPLE_CURSOR:
                break;

            case TASK_PEOPLE_INFO_CURSOR:
                break;

            case TASK_PEOPLE_PHOTOS_CURSOR:
                break;
        }
    }
}
