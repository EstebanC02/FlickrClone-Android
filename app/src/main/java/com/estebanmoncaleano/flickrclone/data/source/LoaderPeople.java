package com.estebanmoncaleano.flickrclone.data.source;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;
import com.estebanmoncaleano.flickrclone.data.database.service.PeopleUpdateService;
import com.estebanmoncaleano.flickrclone.data.model.People;
import com.estebanmoncaleano.flickrclone.sync.AsyncLoaderPeople;

public class LoaderPeople implements LoaderManager.LoaderCallbacks<People> {

    public static final int TASK_PEOPLE_BY_USERNAME = 23;

    private Context context;

    public LoaderPeople(Context context) {
        this.context = context;
    }

    @Override
    public Loader<People> onCreateLoader(int id, Bundle args) {
        AsyncLoaderPeople loaderPeople = new AsyncLoaderPeople();
        switch (id) {
            case TASK_PEOPLE_BY_USERNAME:
                return loaderPeople.getPeopleByUsername(context, args);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<People> loader, People people) {
        switch (loader.getId()) {
            case TASK_PEOPLE_BY_USERNAME: {
                if (people != null) {
                    ContentValues value = new ContentValues();
                    value.put(FlickrContract.PeopleListEntry._ID, people.getId());
                    value.put(FlickrContract.PeopleListEntry.USERNAME, people.getUsername());
                    value.put(FlickrContract.PeopleListEntry.DESCRIPTION, people.getDescription());
                    value.put(FlickrContract.PeopleListEntry.PHOTO_URL, people.getPhoto_url());
                    value.put(FlickrContract.PeopleListEntry.REALNAME, people.getRealname());
                    value.put(FlickrContract.PeopleListEntry.LOCATION, people.getLocation());
                    PeopleUpdateService.insertNewTask(context, value);
                }
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<People> loader) {
        switch (loader.getId()) {
            case TASK_PEOPLE_BY_USERNAME:
                break;
        }
    }
}
