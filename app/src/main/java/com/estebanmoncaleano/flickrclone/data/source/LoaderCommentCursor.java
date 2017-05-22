package com.estebanmoncaleano.flickrclone.data.source;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.estebanmoncaleano.flickrclone.sync.AsyncLoaderComment;

public class LoaderCommentCursor implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int TASK_COMMENT_CURSOR = 40;

    private Context context;

    public LoaderCommentCursor(Context context) {
        this.context = context;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        AsyncLoaderComment loaderComment = new AsyncLoaderComment();
        switch (id) {
            case TASK_COMMENT_CURSOR:
                return loaderComment.getPhotoCommentsCursor(context, args);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case TASK_COMMENT_CURSOR:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case TASK_COMMENT_CURSOR:
                break;
        }
    }
}
