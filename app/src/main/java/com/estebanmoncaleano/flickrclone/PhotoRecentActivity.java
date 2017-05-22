package com.estebanmoncaleano.flickrclone;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.estebanmoncaleano.flickrclone.data.adapter.PhotoCursorAdapter;
import com.estebanmoncaleano.flickrclone.data.adapter.PhotoListAdapter;
import com.estebanmoncaleano.flickrclone.data.model.Photo;
import com.estebanmoncaleano.flickrclone.data.source.LoaderPhotoService;
import com.estebanmoncaleano.flickrclone.utilties.web.NetworkUtils;

import java.util.ArrayList;

public class PhotoRecentActivity extends AppCompatActivity implements
        PhotoListAdapter.ListPhotoClickListener,
        PhotoCursorAdapter.ListPhotoClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private View errorView;
    private PhotoListAdapter photoListAdapter;
    private int valuePage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_recent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.action_bar_title_recent));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_photos_recent);
        swipeRefreshLayout.setOnRefreshListener(this);
        errorView = findViewById(R.id.fl_internet_disable);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_photos_list);
        recyclerView.setHasFixedSize(true);

        if (NetworkUtils.isNetworkAvailable(this)) {
            photoListAdapter = new PhotoListAdapter(this);
            photoListAdapter.setProgressBar((ProgressBar) findViewById(R.id.pb_photo_search));
            photoListAdapter.setPhotoListData(null);
            recyclerView.setAdapter(photoListAdapter);
        } else {
            showErrorMessage();
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        loadRecentPhotosList();
    }

    private void loadRecentPhotosList() {
        Bundle bundle = new Bundle();
        bundle.putInt(NetworkUtils.PAGE_VALUE_KEY, valuePage++);

        if (NetworkUtils.isNetworkAvailable(this)) {
            LoaderPhotoService photoService = new LoaderPhotoService(this, photoListAdapter);
            Loader<ArrayList<Photo>> photoList = getSupportLoaderManager().getLoader(LoaderPhotoService.TASK_LIST_RECENT_PHOTO);
            if (photoList == null)
                getSupportLoaderManager().initLoader(LoaderPhotoService.TASK_LIST_RECENT_PHOTO, bundle, photoService);
            else
                getSupportLoaderManager().restartLoader(LoaderPhotoService.TASK_LIST_RECENT_PHOTO, bundle, photoService);
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onRefresh() {
        loadRecentPhotosList();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showErrorMessage() {
        swipeRefreshLayout.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListItemClick(long clickedItemIndex) {

    }
}
