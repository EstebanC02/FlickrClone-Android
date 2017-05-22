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

public class LoaderGroup implements LoaderManager.LoaderCallbacks<Group> {

    public static final int TASK_GROUP_INFO = 30;

    private Context context;

    public LoaderGroup(Context context) {
        this.context = context;
    }

    @Override
    public Loader<Group> onCreateLoader(int id, Bundle args) {
        AsyncLoaderGroup loaderGroup = new AsyncLoaderGroup();
        switch (id) {
            case TASK_GROUP_INFO:
                return loaderGroup.getGroupInfo(context, args);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Group> loader, Group group) {
        switch (loader.getId()) {
            case TASK_GROUP_INFO: {
                if (group != null) {
                    ContentValues values = new ContentValues();
                    values.put(FlickrContract.GroupListEntry._ID, group.getId());
                    values.put(FlickrContract.GroupListEntry.NAME, group.getName());
                    values.put(FlickrContract.GroupListEntry.DESCRIPTION, group.getDescription());
                    values.put(FlickrContract.GroupListEntry.RULES, group.getRules());
                    values.put(FlickrContract.GroupListEntry.MEMBERS, group.getMembers());
                    values.put(FlickrContract.GroupListEntry.TOPIC_COUNT, group.getTopics());
                    GroupUpdateService.updateTask(context, FlickrContract.GroupListEntry.CONTENT_URI, values);
                }
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Group> loader) {
        switch (loader.getId()) {
            case TASK_GROUP_INFO:
                break;
        }
    }
}
