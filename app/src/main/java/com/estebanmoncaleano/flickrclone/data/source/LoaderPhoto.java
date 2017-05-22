package com.estebanmoncaleano.flickrclone.data.source;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.estebanmoncaleano.flickrclone.PhotoInfoActivity;
import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;
import com.estebanmoncaleano.flickrclone.data.database.service.PhotoUpdateService;
import com.estebanmoncaleano.flickrclone.data.model.Photo;
import com.estebanmoncaleano.flickrclone.sync.AsyncLoaderPhoto;

public class LoaderPhoto implements LoaderManager.LoaderCallbacks<Photo> {

    public static final int TASK_INFO_PHOTO = 14;

    private Context context;
    private PhotoInfoActivity photoInfoActivity;

    public LoaderPhoto(Context context, PhotoInfoActivity activity) {
        this.context = context;
        this.photoInfoActivity = activity;
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
                    values.put(FlickrContract.PhotoListEntry.USERNAME, photo.getUsername());
                    values.put(FlickrContract.PhotoListEntry.REALNAME, photo.getRealname());
                    values.put(FlickrContract.PhotoListEntry.LOCATION, photo.getLocation());
                    PhotoUpdateService.updateTask(context, FlickrContract.PhotoListEntry.CONTENT_URI, values);
                    this.photoInfoActivity.setPhotoInfo(photo);
                }
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Photo> loader) {
        switch (loader.getId()) {
            case TASK_INFO_PHOTO:
                this.photoInfoActivity.setPhotoInfo(null);
                break;
        }
    }
}
