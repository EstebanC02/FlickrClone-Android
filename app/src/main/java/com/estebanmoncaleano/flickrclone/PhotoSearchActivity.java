package com.estebanmoncaleano.flickrclone;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.estebanmoncaleano.flickrclone.data.adapter.PhotoCursorAdapter;
import com.estebanmoncaleano.flickrclone.data.adapter.PhotoListAdapter;
import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;
import com.estebanmoncaleano.flickrclone.data.model.Photo;
import com.estebanmoncaleano.flickrclone.data.source.LoaderPhotoCursor;
import com.estebanmoncaleano.flickrclone.data.source.LoaderPhotoService;
import com.estebanmoncaleano.flickrclone.utilties.web.NetworkUtils;

import java.util.ArrayList;

public class PhotoSearchActivity extends AppCompatActivity implements
        PhotoListAdapter.ListPhotoClickListener,
        SwipeRefreshLayout.OnRefreshListener, PhotoCursorAdapter.ListPhotoClickListener {

    private static final String TAG = PhotoSearchActivity.class.getSimpleName();

    private SwipeRefreshLayout swipeRefreshLayout;
    private String valuePhoto;
    private PhotoListAdapter photoListAdapter;
    private PhotoCursorAdapter photoCursorAdapter;
    private int valuePage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String valueSearch = getIntent().getExtras().getString(FlickrContract.PhotoListEntry.TITLE);
        if (valueSearch != null) valuePhoto = valueSearch;
        else startActivity(new Intent(this, MainActivity.class));

        setContentView(R.layout.activity_photo_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(valueSearch);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_photos_list);
        swipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_photos_list);
        recyclerView.setHasFixedSize(true);

        if (NetworkUtils.isNetworkAvailable(this)) {
            photoListAdapter = new PhotoListAdapter(this);
            photoListAdapter.setProgressBar((ProgressBar) findViewById(R.id.pb_photo_search));
            photoListAdapter.setPhotoListData(null);
            recyclerView.setAdapter(photoListAdapter);
        } else {
            photoCursorAdapter = new PhotoCursorAdapter(this);
            photoCursorAdapter.setPhotoCursorData(null);
            recyclerView.setAdapter(photoCursorAdapter);
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        loadSearchPhotosList();
    }

    private void loadSearchPhotosList() {
        Bundle bundle = new Bundle();
        bundle.putString(FlickrContract.PhotoListEntry.TITLE, valuePhoto);
        bundle.putInt(NetworkUtils.PAGE_VALUE_KEY, valuePage++);

        if (NetworkUtils.isNetworkAvailable(this)) {
            LoaderPhotoService photoService = new LoaderPhotoService(this, photoListAdapter);
            Loader<ArrayList<Photo>> photoList = getSupportLoaderManager().getLoader(LoaderPhotoService.TASK_SEARCH_PHOTO);
            if (photoList == null)
                getSupportLoaderManager().initLoader(LoaderPhotoService.TASK_SEARCH_PHOTO, bundle, photoService);
            else
                getSupportLoaderManager().restartLoader(LoaderPhotoService.TASK_SEARCH_PHOTO, bundle, photoService);
        } else {
            LoaderPhotoCursor photoCursorLoader = new LoaderPhotoCursor(this, photoCursorAdapter);
            Loader<Cursor> photoCursor = getSupportLoaderManager().getLoader(LoaderPhotoCursor.TASK_CURSOR_SEARCH_PHOTO);
            if (photoCursor == null)
                getSupportLoaderManager().initLoader(LoaderPhotoCursor.TASK_CURSOR_SEARCH_PHOTO, bundle, photoCursorLoader);
            else
                getSupportLoaderManager().restartLoader(LoaderPhotoCursor.TASK_CURSOR_SEARCH_PHOTO, bundle, photoCursorLoader);
        }
    }

    @Override
    public void onListItemClick(long clickedItemIndex) {
        Intent intent = new Intent(this, PhotoInfoActivity.class);
        intent.putExtra(FlickrContract.PhotoListEntry._ID, clickedItemIndex);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        loadSearchPhotosList();
        swipeRefreshLayout.setRefreshing(false);
    }
}
