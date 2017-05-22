package com.estebanmoncaleano.flickrclone.data.source;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.estebanmoncaleano.flickrclone.data.adapter.PhotoListAdapter;
import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;
import com.estebanmoncaleano.flickrclone.data.database.service.PhotoUpdateService;
import com.estebanmoncaleano.flickrclone.data.model.Photo;
import com.estebanmoncaleano.flickrclone.sync.AsyncLoaderPhoto;

import java.util.ArrayList;

public class LoaderPhotoService implements LoaderManager.LoaderCallbacks<ArrayList<Photo>> {

    public static final int TASK_LIST_RECENT_PHOTO = 12;
    public static final int TASK_SEARCH_PHOTO = 13;

    private Context context;
    private PhotoListAdapter photoListAdapter;

    public LoaderPhotoService(Context context, PhotoListAdapter adapter) {
        this.context = context;
        this.photoListAdapter = adapter;
    }

    @Override
    public Loader<ArrayList<Photo>> onCreateLoader(int id, Bundle args) {
        AsyncLoaderPhoto loaderPhoto = new AsyncLoaderPhoto();
        switch (id) {
            case TASK_LIST_RECENT_PHOTO:
                return loaderPhoto.getPhotoRecentList(context, args);

            case TASK_SEARCH_PHOTO:
                return loaderPhoto.getPhotoSearchList(context, args);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Photo>> loader, ArrayList<Photo> photos) {
        switch (loader.getId()) {

            case TASK_LIST_RECENT_PHOTO:
                if (photos != null)
                    photoListAdapter.setPhotoListData(photos);
                break;

            case TASK_SEARCH_PHOTO: {
                if (photos != null) {
                    for (int i = 0; i < photos.size(); i++) {
                        Photo photo = photos.get(i);
                        ContentValues value = new ContentValues();
                        value.put(FlickrContract.PhotoListEntry._ID, photo.getId());
                        value.put(FlickrContract.PhotoListEntry.OWNER, photo.getOwner());
                        value.put(FlickrContract.PhotoListEntry.SECRET, photo.getSecret());
                        value.put(FlickrContract.PhotoListEntry.SERVER, photo.getServer());
                        value.put(FlickrContract.PhotoListEntry.FARM, photo.getFarm());
                        value.put(FlickrContract.PhotoListEntry.TITLE, photo.getTitle());
                        PhotoUpdateService.insertNewTask(context, value);
                    }
                    photoListAdapter.setPhotoListData(photos);
                }
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Photo>> loader) {
        switch (loader.getId()) {
            case TASK_LIST_RECENT_PHOTO:
                photoListAdapter.setPhotoListData(null);
                break;

            case TASK_SEARCH_PHOTO:
                photoListAdapter.setPhotoListData(null);
                break;
        }
    }
}
