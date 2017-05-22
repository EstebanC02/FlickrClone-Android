package com.estebanmoncaleano.flickrclone.sync;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;
import com.estebanmoncaleano.flickrclone.data.model.Comment;
import com.estebanmoncaleano.flickrclone.utilties.web.FlickrJsonUtils;
import com.estebanmoncaleano.flickrclone.utilties.web.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AsyncLoaderComment {
    private static final String TAG = AsyncLoaderComment.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<Cursor> getPhotoCommentsCursor(final Context context, final Bundle args) {
        return new AsyncTaskLoader<Cursor>(context) {

            Cursor cursorCommentList;
            int idPhoto;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                int idPhotoSearching = args.getInt(FlickrContract.CommentListEntry.PHOTO_ID);

                if (idPhoto == idPhotoSearching && cursorCommentList != null) {
                    deliverResult(cursorCommentList);
                } else {
                    idPhoto = idPhotoSearching;
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                String selection = String.format("%s = ?", FlickrContract.CommentListEntry.PHOTO_ID);
                String[] selectionArgs = new String[]{String.valueOf(idPhoto)};

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
                cursorCommentList = data;
                super.deliverResult(data);
            }
        };
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<ArrayList<Comment>> getPhotoCommentsList(final Context context, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<Comment>>(context) {

            ArrayList<Comment> commentList;
            int idPhoto;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                int idPhotoSearching = args.getInt(FlickrContract.CommentListEntry.PHOTO_ID);

                if (idPhoto == idPhotoSearching && commentList != null) {
                    deliverResult(commentList);
                } else {
                    idPhoto = idPhotoSearching;
                    forceLoad();
                }
            }

            @Override
            public ArrayList<Comment> loadInBackground() {
                URL searchURL = NetworkUtils.buildUrlPhotoGetComments(String.valueOf(idPhoto));
                try {
                    String commentSearchResult = NetworkUtils.getResponseFromHttpUrl(searchURL);
                    return FlickrJsonUtils.getCommentsFromJson(commentSearchResult);
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
                return null;
            }

            @Override
            public void deliverResult(ArrayList<Comment> data) {
                commentList = data;
                super.deliverResult(data);
            }
        };
    }
}
