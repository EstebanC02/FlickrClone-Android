package com.estebanmoncaleano.flickrclone.data.source;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;
import com.estebanmoncaleano.flickrclone.data.database.service.GroupUpdateService;
import com.estebanmoncaleano.flickrclone.data.model.Group;
import com.estebanmoncaleano.flickrclone.sync.AsyncLoaderGroup;

import java.util.ArrayList;

public class LoaderGroupService implements LoaderManager.LoaderCallbacks<ArrayList<Group>> {

    public static final int TASK_LIST_GROUP = 33;

    private Context context;

    public LoaderGroupService(Context context) {
        this.context = context;
    }

    @Override
    public Loader<ArrayList<Group>> onCreateLoader(int id, Bundle args) {
        AsyncLoaderGroup loaderGroup = new AsyncLoaderGroup();
        switch (id) {
            case TASK_LIST_GROUP:
                return loaderGroup.getGroupList(context, args);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Group>> loader, ArrayList<Group> groups) {
        switch (loader.getId()) {
            case TASK_LIST_GROUP: {
                if (groups != null) {
                    ContentValues[] groupValues = new ContentValues[groups.size()];
                    for (int i = 0; i < groups.size(); i++) {
                        Group group = groups.get(i);
                        ContentValues value = new ContentValues();
                        value.put(FlickrContract.GroupListEntry._ID, group.getId());
                        value.put(FlickrContract.GroupListEntry.NAME, group.getName());
                        groupValues[i] = value;
                    }
                    GroupUpdateService.bulkInsertNewTask(context, groupValues);
                }
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Group>> loader) {
        switch (loader.getId()) {
            case TASK_LIST_GROUP:
                break;
        }
    }
}
