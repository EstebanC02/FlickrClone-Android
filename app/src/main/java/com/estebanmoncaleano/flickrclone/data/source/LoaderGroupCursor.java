package com.estebanmoncaleano.flickrclone.data.source;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.estebanmoncaleano.flickrclone.sync.AsyncLoaderGroup;

public class LoaderGroupCursor implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int TASK_GROUP_CURSOR = 31;
    public static final int TASK_GROUP_SEARCH_CURSOR = 32;

    private Context context;

    public LoaderGroupCursor(Context context) {
        this.context = context;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        AsyncLoaderGroup loaderGroup = new AsyncLoaderGroup();
        switch (id) {
            case TASK_GROUP_CURSOR:
                return loaderGroup.getGroupCursor(context, args);

            case TASK_GROUP_SEARCH_CURSOR:
                return loaderGroup.getGroupSearchCursor(context, args);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case TASK_GROUP_CURSOR:
                break;

            case TASK_GROUP_SEARCH_CURSOR:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case TASK_GROUP_CURSOR:
                break;

            case TASK_GROUP_SEARCH_CURSOR:
                break;
        }
    }
}
