package com.estebanmoncaleano.flickrclone.sync;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;
import com.estebanmoncaleano.flickrclone.data.model.People;
import com.estebanmoncaleano.flickrclone.data.model.Photo;
import com.estebanmoncaleano.flickrclone.utilties.web.FlickrJsonUtils;
import com.estebanmoncaleano.flickrclone.utilties.web.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AsyncLoaderPeople {
    private static final String TAG = AsyncLoaderPeople.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<Cursor> getPeopleInfoCursor(final Context context, final Bundle args) {
        return new AsyncTaskLoader<Cursor>(context) {

            Cursor cursorPeopleList;
            String idPeople;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                String usernamePeopleSearching = args.getString(FlickrContract.PeopleListEntry._ID);

                if (idPeople.equalsIgnoreCase(usernamePeopleSearching) && cursorPeopleList != null) {
                    deliverResult(cursorPeopleList);
                } else {
                    idPeople = usernamePeopleSearching;
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    String selection = String.format("%s = ?", FlickrContract.PeopleListEntry._ID);
                    String[] selectionArgs = new String[]{idPeople};

                    return context.getContentResolver().query(
                            FlickrContract.PeopleListEntry.CONTENT_URI,
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
                cursorPeopleList = data;
                super.deliverResult(data);
            }
        };
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<Cursor> getPeopleCursor(final Context context, final Bundle args) {
        return new AsyncTaskLoader<Cursor>(context) {

            Cursor cursorPeopleList;
            String usernamePeople;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                String usernamePeopleSearching = args.getString(FlickrContract.PeopleListEntry.USERNAME);

                if (usernamePeople.equalsIgnoreCase(usernamePeopleSearching) && cursorPeopleList != null) {
                    deliverResult(cursorPeopleList);
                } else {
                    usernamePeople = usernamePeopleSearching;
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    String selection = String.format("%s LIKE ?", FlickrContract.PeopleListEntry.USERNAME);
                    String[] selectionArgs = new String[]{"%" + usernamePeople + "%"};

                    return context.getContentResolver().query(
                            FlickrContract.PeopleListEntry.CONTENT_URI,
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
                cursorPeopleList = data;
                super.deliverResult(data);
            }
        };
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<Cursor> getPeoplePhotosCursor(final Context context, final Bundle args) {
        return new AsyncTaskLoader<Cursor>(context) {

            Cursor cursorPeopleList;
            String idPeople;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                String idPeopleSearching = args.getString(FlickrContract.PeopleListEntry._ID);

                if (idPeople.equalsIgnoreCase(idPeopleSearching) && cursorPeopleList != null) {
                    deliverResult(cursorPeopleList);
                } else {
                    idPeople = idPeopleSearching;
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                String selection = String.format("%s = ?", FlickrContract.PhotoListEntry.OWNER);
                String[] selectionArgs = new String[]{String.valueOf(idPeople)};

                return context.getContentResolver().query(
                        FlickrContract.PhotoListEntry.CONTENT_URI,
                        null,
                        selection,
                        selectionArgs,
                        null
                );
            }

            @Override
            public void deliverResult(Cursor data) {
                cursorPeopleList = data;
                super.deliverResult(data);
            }
        };
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<People> getPeopleByUsername(final Context context, final Bundle args) {
        return new AsyncTaskLoader<People>(context) {

            People people;
            String usernamePeople;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                String usernamePeopleSearching = args.getString(FlickrContract.PeopleListEntry.USERNAME);

                if (usernamePeople.equalsIgnoreCase(usernamePeopleSearching) && people != null) {
                    deliverResult(people);
                } else {
                    usernamePeople = usernamePeopleSearching;
                    forceLoad();
                }
            }

            @Override
            public People loadInBackground() {
                URL searchURL = NetworkUtils.buildUrlPeopleFindByUsername(usernamePeople);
                try {
                    String peopleSearchResult = NetworkUtils.getResponseFromHttpUrl(searchURL);
                    int idUser = FlickrJsonUtils.getFindByUsernameFromJson(peopleSearchResult);
                    searchURL = NetworkUtils.buildUrlPeopleGetInfo(String.valueOf(idUser));
                    String peopleResult = NetworkUtils.getResponseFromHttpUrl(searchURL);
                    return FlickrJsonUtils.getPeopleInfoFromJson(peopleResult);
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
                return null;
            }

            @Override
            public void deliverResult(People data) {
                people = data;
                super.deliverResult(data);
            }
        };
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<ArrayList<Photo>> getPeoplePhotoList(final Context context, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<Photo>>(context) {

            ArrayList<Photo> peoplePhotoList;
            String usernameId;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                String idPeopleSearching = args.getString(FlickrContract.PeopleListEntry._ID);

                if (usernameId.equalsIgnoreCase(idPeopleSearching) && peoplePhotoList != null) {
                    deliverResult(peoplePhotoList);
                } else {
                    usernameId = idPeopleSearching;
                    forceLoad();
                }
            }

            @Override
            public ArrayList<Photo> loadInBackground() {
                URL searchURL = NetworkUtils.buildUrlPeopleGetPhotos(usernameId);
                try {
                    String peopleSearchResult = NetworkUtils.getResponseFromHttpUrl(searchURL);
                    return FlickrJsonUtils.getPeoplePhotosFromJson(peopleSearchResult);
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
                return null;
            }

            @Override
            public void deliverResult(ArrayList<Photo> data) {
                peoplePhotoList = data;
                super.deliverResult(data);
            }
        };
    }
}
