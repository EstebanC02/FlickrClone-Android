package com.estebanmoncaleano.flickrclone.data.database.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;

/* Process DB actions on a background thread */
public class GroupUpdateService extends IntentService {

    private static final String TAG = GroupUpdateService.class.getSimpleName();
    //Intent actions
    public static final String ACTION_INSERT = TAG + ".INSERT";
    public static final String ACTION_BULK_INSERT = TAG + ".BULK_INSERT";
    public static final String ACTION_UPDATE = TAG + ".UPDATE";
    public static final String ACTION_DELETE = TAG + ".DELETE";

    public static final String EXTRA_VALUES = TAG + ".ContentValues";

    public GroupUpdateService() {
        super(TAG);
    }

    public static void insertNewTask(Context context, ContentValues values) {
        Intent intent = new Intent(context, GroupUpdateService.class);
        intent.setAction(ACTION_INSERT);
        intent.putExtra(EXTRA_VALUES, values);
        context.startService(intent);
    }

    public static void bulkInsertNewTask(Context context, ContentValues[] values) {
        Intent intent = new Intent(context, GroupUpdateService.class);
        intent.setAction(ACTION_BULK_INSERT);
        intent.putExtra(EXTRA_VALUES, values);
        context.startService(intent);
    }

    public static void updateTask(Context context, Uri uri, ContentValues values) {
        Intent intent = new Intent(context, GroupUpdateService.class);
        intent.setAction(ACTION_UPDATE);
        intent.setData(uri);
        intent.putExtra(EXTRA_VALUES, values);
        context.startService(intent);
    }

    public static void deleteTask(Context context, Uri uri) {
        Intent intent = new Intent(context, GroupUpdateService.class);
        intent.setAction(ACTION_DELETE);
        intent.setData(uri);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        assert intent != null;
        if (ACTION_INSERT.equals(intent.getAction())) {
            ContentValues values = intent.getParcelableExtra(EXTRA_VALUES);
            performInsert(values);
        } else if (ACTION_BULK_INSERT.equals(intent.getAction())) {
            ContentValues[] values = (ContentValues[]) intent.getParcelableArrayExtra(EXTRA_VALUES);
            performInsert(values);
        } else if (ACTION_UPDATE.equals(intent.getAction())) {
            ContentValues values = intent.getParcelableExtra(EXTRA_VALUES);
            performUpdate(intent.getData(), values);
        } else if (ACTION_DELETE.equals(intent.getAction())) {
            performDelete(intent.getData());
        }
    }

    private void performInsert(ContentValues values) {
        if (getContentResolver().insert(FlickrContract.GroupListEntry.CONTENT_URI, values) != null)
            Log.d(TAG, "Inserted new group");
        else
            Log.w(TAG, "Error inserting new group");
    }

    private void performInsert(ContentValues[] values) {
        if (getContentResolver().bulkInsert(FlickrContract.GroupListEntry.CONTENT_URI, values) > 0)
            Log.d(TAG, "Inserted new groups");
        else
            Log.w(TAG, "Error inserting new groups");
    }

    private void performUpdate(Uri uri, ContentValues values) {
        String selection = String.format("%s = ?", FlickrContract.GroupListEntry._ID);
        String[] selectionArgs = new String[]{String.valueOf(values.getAsString(FlickrContract.GroupListEntry._ID))};
        int count = getContentResolver().update(uri, values, selection, selectionArgs);
        Log.d(TAG, "Updated " + count + " group items");
    }

    private void performDelete(Uri uri) {
        int count = getContentResolver().delete(uri, null, null);
        Log.d(TAG, "Deleted " + count + " group");
    }
}
