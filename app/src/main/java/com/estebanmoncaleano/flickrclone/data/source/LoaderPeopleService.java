package com.estebanmoncaleano.flickrclone.data.source;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;
import com.estebanmoncaleano.flickrclone.data.database.service.PhotoUpdateService;
import com.estebanmoncaleano.flickrclone.data.model.Photo;
import com.estebanmoncaleano.flickrclone.sync.AsyncLoaderPeople;

import java.util.ArrayList;

public class LoaderPeopleService implements LoaderManager.LoaderCallbacks<ArrayList<Photo>> {

    public static final int TASK_LIST_PEOPLE = 20;

    private Context context;

    public LoaderPeopleService(Context context) {
        this.context = context;
    }

    @Override
    public Loader<ArrayList<Photo>> onCreateLoader(int id, Bundle args) {
        AsyncLoaderPeople loaderPeople = new AsyncLoaderPeople();
        switch (id) {
            case TASK_LIST_PEOPLE:
                return loaderPeople.getPeoplePhotoList(context, args);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Photo>> loader, ArrayList<Photo> photos) {
        switch (loader.getId()) {
            case TASK_LIST_PEOPLE: {
                if (photos != null) {
                    ContentValues[] photoValues = new ContentValues[photos.size()];
                    for (int i = 0; i < photos.size(); i++) {
                        Photo photo = photos.get(i);
                        ContentValues value = new ContentValues();
                        value.put(FlickrContract.PhotoListEntry._ID, photo.getId());
                        value.put(FlickrContract.PhotoListEntry.OWNER, photo.getOwner());
                        value.put(FlickrContract.PhotoListEntry.SECRET, photo.getSecret());
                        value.put(FlickrContract.PhotoListEntry.SERVER, photo.getServer());
                        value.put(FlickrContract.PhotoListEntry.FARM, photo.getFarm());
                        value.put(FlickrContract.PhotoListEntry.TITLE, photo.getTitle());
                        photoValues[i] = value;
                    }
                    PhotoUpdateService.bulkInsertNewTask(context, photoValues);
                }
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Photo>> loader) {
        switch (loader.getId()) {
            case TASK_LIST_PEOPLE:
                break;
        }
    }
}
