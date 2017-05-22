package com.estebanmoncaleano.flickrclone.sync;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;
import com.estebanmoncaleano.flickrclone.data.model.Photo;
import com.estebanmoncaleano.flickrclone.utilties.web.FlickrJsonUtils;
import com.estebanmoncaleano.flickrclone.utilties.web.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AsyncLoaderPhoto {
    private static final String TAG = AsyncLoaderPhoto.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<Cursor> getPhotoCursor(final Context context, final Bundle args) {
        return new AsyncTaskLoader<Cursor>(context) {

            Cursor cursorPhotoList;
            int idPhoto;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                int idArgs = args.getInt(FlickrContract.PhotoListEntry._ID);

                if (idPhoto == idArgs && cursorPhotoList != null) {
                    deliverResult(cursorPhotoList);
                } else {
                    idPhoto = idArgs;
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    String selection = String.format("%s = ?", FlickrContract.PhotoListEntry._ID);
                    String[] selectionArgs = new String[]{String.valueOf(idPhoto)};

                    return context.getContentResolver().query(
                            FlickrContract.PhotoListEntry.CONTENT_URI,
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
                cursorPhotoList = data;
                super.deliverResult(data);
            }
        };
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<Cursor> getPhotoSearchCursor(final Context context, final Bundle args) {
        return new AsyncTaskLoader<Cursor>(context) {

            Cursor cursorPhotoList;
            String valuePhoto;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                String idArgs = args.getString(FlickrContract.PhotoListEntry.TITLE);

                if (valuePhoto.equalsIgnoreCase(idArgs) && cursorPhotoList != null) {
                    deliverResult(cursorPhotoList);
                } else {
                    valuePhoto = idArgs;
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    String selection = String.format("%s LIKE ?", FlickrContract.PhotoListEntry.TITLE);
                    String[] selectionArgs = new String[]{"%" + valuePhoto + "%"};

                    return context.getContentResolver().query(
                            FlickrContract.PhotoListEntry.CONTENT_URI,
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
                cursorPhotoList = data;
                super.deliverResult(data);
            }
        };
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<ArrayList<Photo>> getPhotoRecentList(final Context context, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<Photo>>(context) {

            ArrayList<Photo> cursorPhotoRecentList;
            int idPhoto;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                int idArgs = args.getInt(FlickrContract.PhotoListEntry._ID);

                if (idPhoto == idArgs && cursorPhotoRecentList != null) {
                    deliverResult(cursorPhotoRecentList);
                } else {
                    idPhoto = idArgs;
                    forceLoad();
                }
            }

            @Override
            public ArrayList<Photo> loadInBackground() {
                try {
                    URL searchURL = NetworkUtils.buildUrlGetRecent();
                    try {
                        String photoSearchResult = NetworkUtils.getResponseFromHttpUrl(searchURL);
                        return FlickrJsonUtils.getListRecentPhotoFromJson(photoSearchResult);
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                    return null;
                } catch (Exception e) {
                    Log.e(TAG, "Failed asynchronously load task data.");
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(ArrayList<Photo> data) {
                cursorPhotoRecentList = data;
                super.deliverResult(data);
            }
        };
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<Photo> getPhotoInfo(final Context context, final Bundle args) {
        return new AsyncTaskLoader<Photo>(context) {

            Photo photo;
            int idPhoto;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                int idArgs = args.getInt(FlickrContract.PhotoListEntry._ID);

                if (idPhoto == idArgs && photo != null) {
                    deliverResult(photo);
                } else {
                    idPhoto = idArgs;
                    forceLoad();
                }
            }

            @Override
            public Photo loadInBackground() {
                try {
                    URL searchURL = NetworkUtils.buildUrlPhotoGetInfo(String.valueOf(idPhoto));
                    try {
                        String photoSearchResult = NetworkUtils.getResponseFromHttpUrl(searchURL);
                        return FlickrJsonUtils.getPhotoInfoFromJson(photoSearchResult);
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                    return null;
                } catch (Exception e) {
                    Log.e(TAG, "Failed asynchronously load task data.");
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(Photo data) {
                photo = data;
                super.deliverResult(data);
            }
        };
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<ArrayList<Photo>> getPhotoSearchList(final Context context, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<Photo>>(context) {

            ArrayList<Photo> photoSearchList;
            String valuePhoto;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                String idArgs = args.getString(FlickrContract.PhotoListEntry.TITLE);

                if (valuePhoto.equalsIgnoreCase(idArgs) && photoSearchList != null) {
                    deliverResult(photoSearchList);
                } else {
                    valuePhoto = idArgs;
                    forceLoad();
                }
            }

            @Override
            public ArrayList<Photo> loadInBackground() {
                try {
                    URL searchURL = NetworkUtils.buildUrlSearch(valuePhoto);
                    try {
                        String photoSearchResult = NetworkUtils.getResponseFromHttpUrl(searchURL);
                        return FlickrJsonUtils.getListSearchPhotoFromJson(photoSearchResult);
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                    return null;
                } catch (Exception e) {
                    Log.e(TAG, "Failed asynchronously load task data.");
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(ArrayList<Photo> data) {
                photoSearchList = data;
                super.deliverResult(data);
            }
        };
    }
}