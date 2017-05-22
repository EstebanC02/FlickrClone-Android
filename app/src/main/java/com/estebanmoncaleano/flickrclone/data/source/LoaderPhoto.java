package com.estebanmoncaleano.flickrclone.data.source;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;
import com.estebanmoncaleano.flickrclone.data.database.service.PhotoUpdateService;
import com.estebanmoncaleano.flickrclone.data.model.Photo;
import com.estebanmoncaleano.flickrclone.sync.AsyncLoaderPhoto;

public class LoaderPhoto implements LoaderManager.LoaderCallbacks<Photo> {

    public static final int TASK_INFO_PHOTO = 14;

    private Context context;

    public LoaderPhoto(Context context) {
        this.context = context;
    }

    @Override
    public Loader<Photo> onCreateLoader(int id, Bundle args) {
        AsyncLoaderPhoto loaderPhoto = new AsyncLoaderPhoto();
        switch (id) {
            case TASK_INFO_PHOTO:
                return loaderPhoto.getPhotoInfo(context, args);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Photo> loader, Photo photo) {
        switch (loader.getId()) {
            case TASK_INFO_PHOTO: {
                if (photo != null) {
                    ContentValues values;
                    values = new ContentValues();
                    values.put(FlickrContract.PhotoListEntry._ID, photo.getId());
                    values.put(FlickrContract.PhotoListEntry.DESCRIPTION, photo.getDescription());
                    values.put(FlickrContract.PhotoListEntry.DATE, photo.getDate());
                    PhotoUpdateService.updateTask(context, FlickrContract.PhotoListEntry.CONTENT_URI, values);
                }
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Photo> loader) {
        switch (loader.getId()) {
            case TASK_INFO_PHOTO:
                break;
        }
    }
}
