package com.estebanmoncaleano.flickrclone.sync;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;
import com.estebanmoncaleano.flickrclone.data.model.Group;
import com.estebanmoncaleano.flickrclone.utilties.web.FlickrJsonUtils;
import com.estebanmoncaleano.flickrclone.utilties.web.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AsyncLoaderGroup {
    private static final String TAG = AsyncLoaderGroup.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<Cursor> getGroupCursor(final Context context, final Bundle args) {
        return new AsyncTaskLoader<Cursor>(context) {

            Cursor cursorGroupList;
            String idGroup;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                String nameGroupSearching = args.getString(FlickrContract.GroupListEntry._ID);

                if (idGroup.equalsIgnoreCase(nameGroupSearching) && cursorGroupList != null) {
                    deliverResult(cursorGroupList);
                } else {
                    idGroup = nameGroupSearching;
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    String selection = String.format("%s = ?", FlickrContract.GroupListEntry._ID);
                    String[] selectionArgs = new String[]{String.valueOf(idGroup)};

                    return context.getContentResolver().query(
                            FlickrContract.GroupListEntry.CONTENT_URI,
                            null,
                            selection,
                            selectionArgs,
                            null
                    );
                } catch (Exception e) {
                    Log.e(TAG, "Failed asynchronously load task data.");
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                cursorGroupList = data;
                super.deliverResult(data);
            }
        };
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<Cursor> getGroupSearchCursor(final Context context, final Bundle args) {
        return new AsyncTaskLoader<Cursor>(context) {

            Cursor cursorGroupList;
            String nameGroup;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                String nameGroupSearching = args.getString(FlickrContract.GroupListEntry.NAME);

                if (nameGroup.equalsIgnoreCase(nameGroupSearching) && cursorGroupList != null) {
                    deliverResult(cursorGroupList);
                } else {
                    nameGroup = nameGroupSearching;
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    String selection = String.format("%s LIKE ?", FlickrContract.GroupListEntry.NAME);
                    String[] selectionArgs = new String[]{"%" + nameGroup + "%"};

                    return context.getContentResolver().query(
                            FlickrContract.GroupListEntry.CONTENT_URI,
                            null,
                            selection,
                            selectionArgs,
                            null
                    );
                } catch (Exception e) {
                    Log.e(TAG, "Failed asynchronously load task data.");
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                cursorGroupList = data;
                super.deliverResult(data);
            }
        };
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<ArrayList<Group>> getGroupList(final Context context, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<Group>>(context) {

            ArrayList<Group> groupList;
            String nameGroup;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                String nameGroupSearching = args.getString(FlickrContract.GroupListEntry.NAME);

                if (nameGroup.equalsIgnoreCase(nameGroupSearching) && groupList != null) {
                    deliverResult(groupList);
                } else {
                    nameGroup = nameGroupSearching;
                    forceLoad();
                }
            }

            @Override
            public ArrayList<Group> loadInBackground() {
                URL searchURL = NetworkUtils.buildUrlGroupSearch(nameGroup);
                try {
                    String groupSearchResult = NetworkUtils.getResponseFromHttpUrl(searchURL);
                    return FlickrJsonUtils.getGroupSearchFromJson(groupSearchResult);
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
                return null;
            }

            @Override
            public void deliverResult(ArrayList<Group> data) {
                groupList = data;
                super.deliverResult(data);
            }
        };
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<Group> getGroupInfo(final Context context, final Bundle args) {
        return new AsyncTaskLoader<Group>(context) {

            Group group;
            String idGroup;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                String nameGroupSearching = args.getString(FlickrContract.GroupListEntry.NAME);

                if (idGroup.equalsIgnoreCase(nameGroupSearching) && group != null) {
                    deliverResult(group);
                } else {
                    idGroup = nameGroupSearching;
                    forceLoad();
                }
            }

            @Override
            public Group loadInBackground() {
                URL searchURL = NetworkUtils.buildUrlGroupGetInfo(idGroup);
                try {
                    String groupSearchResult = NetworkUtils.getResponseFromHttpUrl(searchURL);
                    return FlickrJsonUtils.getGroupInfoFromJson(groupSearchResult);
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
                return null;
            }

            @Override
            public void deliverResult(Group data) {
                group = data;
                super.deliverResult(data);
            }
        };
    }
}
