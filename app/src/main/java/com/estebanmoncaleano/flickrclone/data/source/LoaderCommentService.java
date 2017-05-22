package com.estebanmoncaleano.flickrclone.data.source;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;
import com.estebanmoncaleano.flickrclone.data.database.service.CommentUpdateService;
import com.estebanmoncaleano.flickrclone.data.model.Comment;
import com.estebanmoncaleano.flickrclone.sync.AsyncLoaderComment;

import java.util.ArrayList;

public class LoaderCommentService implements LoaderManager.LoaderCallbacks<ArrayList<Comment>> {

    public static final int TASK_COMMENT_LIST = 41;

    private Context context;

    public LoaderCommentService(Context context) {
        this.context = context;
    }

    @Override
    public Loader<ArrayList<Comment>> onCreateLoader(int id, Bundle args) {
        AsyncLoaderComment loaderComment = new AsyncLoaderComment();
        switch (id) {
            case TASK_COMMENT_LIST:
                return loaderComment.getPhotoCommentsList(context, args);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Comment>> loader, ArrayList<Comment> comments) {
        switch (loader.getId()) {
            case TASK_COMMENT_LIST: {
                if (comments != null) {
                    ContentValues[] commentValues = new ContentValues[comments.size()];
                    for (int i = 0; i < comments.size(); i++) {
                        Comment comment = comments.get(i);
                        ContentValues value = new ContentValues();
                        value.put(FlickrContract.CommentListEntry._ID, comment.getId());
                        value.put(FlickrContract.CommentListEntry.PHOTO_ID, comment.getPhoto_id());
                        value.put(FlickrContract.CommentListEntry.AUTHOR, comment.getAuthor());
                        value.put(FlickrContract.CommentListEntry.AUTHOR_NAME, comment.getAuthor_name());
                        value.put(FlickrContract.CommentListEntry.MESSAGE, comment.getMessage());
                        commentValues[i] = value;
                    }
                    CommentUpdateService.bulkInsertNewTask(context, commentValues);
                }
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Comment>> loader) {
        switch (loader.getId()) {
            case TASK_COMMENT_LIST:
                break;
        }
    }
}
