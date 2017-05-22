package com.estebanmoncaleano.flickrclone;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.estebanmoncaleano.flickrclone.data.adapter.CommentsCursorAdapter;
import com.estebanmoncaleano.flickrclone.data.adapter.CommentsListAdapter;
import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;
import com.estebanmoncaleano.flickrclone.data.model.Photo;
import com.estebanmoncaleano.flickrclone.data.source.LoaderCommentCursor;
import com.estebanmoncaleano.flickrclone.data.source.LoaderCommentService;
import com.estebanmoncaleano.flickrclone.data.source.LoaderPhoto;
import com.estebanmoncaleano.flickrclone.data.source.LoaderPhotoCursor;
import com.estebanmoncaleano.flickrclone.utilties.web.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class PhotoInfoActivity extends AppCompatActivity {

    private CommentsListAdapter commentsListAdapter;
    private CommentsCursorAdapter commentsCursorAdapter;
    private TextView userNameTv, realNameTv, locationTv, dateTv, descriptionTv, titleTv;
    private ImageView photoImage;
    private long valuePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_info);

        final long valueSearch = getIntent().getExtras().getLong(FlickrContract.PhotoListEntry._ID);
        if (valueSearch != 0) valuePhoto = valueSearch;
        else startActivity(new Intent(this, MainActivity.class));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleTv = (TextView) findViewById(R.id.tv_photo_title);
        userNameTv = (TextView) findViewById(R.id.tv_photo_username);
        realNameTv = (TextView) findViewById(R.id.tv_photo_realname);
        locationTv = (TextView) findViewById(R.id.tv_photo_location);
        dateTv = (TextView) findViewById(R.id.tv_photo_date);
        descriptionTv = (TextView) findViewById(R.id.tv_photo_description);

        photoImage = (ImageView) findViewById(R.id.iv_photo_item);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_photo_comments);
        recyclerView.setHasFixedSize(true);

        if (NetworkUtils.isNetworkAvailable(this)) {
            commentsListAdapter = new CommentsListAdapter();
            commentsListAdapter.setCommentListData(null);
            recyclerView.setAdapter(commentsListAdapter);
        } else {
            commentsCursorAdapter = new CommentsCursorAdapter();
            commentsCursorAdapter.setCommentsCursorData(null);
            recyclerView.setAdapter(commentsCursorAdapter);
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        loadInfoPhoto();
        loadCommentsPhotosList();
    }

    private void loadInfoPhoto() {
        Bundle bundle = new Bundle();
        bundle.putLong(FlickrContract.PhotoListEntry._ID, valuePhoto);

        if (NetworkUtils.isNetworkAvailable(this)) {
            LoaderPhoto photoService = new LoaderPhoto(this, this);
            Loader<Photo> photoInfo = getSupportLoaderManager().getLoader(LoaderPhoto.TASK_INFO_PHOTO);
            if (photoInfo == null)
                getSupportLoaderManager().initLoader(LoaderPhoto.TASK_INFO_PHOTO, bundle, photoService);
            else
                getSupportLoaderManager().restartLoader(LoaderPhoto.TASK_INFO_PHOTO, bundle, photoService);
        } else {
            LoaderPhotoCursor photoCursorLoader = new LoaderPhotoCursor(this, this);
            Loader<Cursor> photoCursor = getSupportLoaderManager().getLoader(LoaderPhotoCursor.TASK_CURSOR_PHOTO);
            if (photoCursor == null)
                getSupportLoaderManager().initLoader(LoaderPhotoCursor.TASK_CURSOR_PHOTO, bundle, photoCursorLoader);
            else
                getSupportLoaderManager().restartLoader(LoaderPhotoCursor.TASK_CURSOR_PHOTO, bundle, photoCursorLoader);
        }
    }

    private void loadCommentsPhotosList() {
        Bundle bundle = new Bundle();
        bundle.putLong(FlickrContract.PhotoListEntry._ID, valuePhoto);

        if (NetworkUtils.isNetworkAvailable(this)) {
            LoaderCommentService commentService = new LoaderCommentService(this, commentsListAdapter);
            Loader<Photo> photoInfo = getSupportLoaderManager().getLoader(LoaderCommentService.TASK_COMMENT_LIST);
            if (photoInfo == null)
                getSupportLoaderManager().initLoader(LoaderCommentService.TASK_COMMENT_LIST, bundle, commentService);
            else
                getSupportLoaderManager().restartLoader(LoaderCommentService.TASK_COMMENT_LIST, bundle, commentService);
        } else {
            LoaderCommentCursor commentCursor = new LoaderCommentCursor(this, commentsCursorAdapter);
            Loader<Cursor> photoCursor = getSupportLoaderManager().getLoader(LoaderCommentCursor.TASK_COMMENT_CURSOR);
            if (photoCursor == null)
                getSupportLoaderManager().initLoader(LoaderCommentCursor.TASK_COMMENT_CURSOR, bundle, commentCursor);
            else
                getSupportLoaderManager().restartLoader(LoaderCommentCursor.TASK_COMMENT_CURSOR, bundle, commentCursor);
        }
    }

    public void setPhotoInfo(Photo photoInfo) {
        if (photoInfo != null) {
            URL urlPhoto = NetworkUtils.buildURLPhotoSource(
                    String.valueOf(photoInfo.getFarm()),
                    String.valueOf(photoInfo.getServer()),
                    String.valueOf(photoInfo.getId()),
                    photoInfo.getSecret(),
                    "n"
            );

            assert urlPhoto != null;
            String url = urlPhoto.toString();
            Picasso.with(this)
                    .load(url)
                    .into(photoImage);


            String title = photoInfo.getTitle() == null ? getString(R.string.no_tittle) : photoInfo.getTitle();
            getSupportActionBar().setTitle(title);
            titleTv.setText(title);

            String userName = photoInfo.getUsername().equalsIgnoreCase("") ? getString(R.string.no_username) : photoInfo.getUsername();
            userNameTv.setText(userName);

            String realName = photoInfo.getRealname().equalsIgnoreCase("") ? getString(R.string.no_realname) : photoInfo.getRealname();
            realNameTv.setText(realName);

            String location = photoInfo.getLocation().equalsIgnoreCase("") ? getString(R.string.no_location) : photoInfo.getLocation();
            locationTv.setText(location);

            String date = photoInfo.getDate() == null ? getString(R.string.no_date) : photoInfo.getDate();
            dateTv.setText(date);

            String description = photoInfo.getDescription().equalsIgnoreCase("") ? getString(R.string.no_description) : photoInfo.getDescription();
            descriptionTv.setText(description);
        }
    }

    public void setPhotoCursorInfo(Cursor photoCursorInfo) {
        if (photoCursorInfo != null && photoCursorInfo.moveToFirst()) {
            URL urlPhoto = NetworkUtils.buildURLPhotoSource(
                    String.valueOf(photoCursorInfo.getColumnIndex(FlickrContract.PhotoListEntry.FARM)),
                    String.valueOf(photoCursorInfo.getColumnIndex(FlickrContract.PhotoListEntry.SERVER)),
                    String.valueOf(photoCursorInfo.getColumnIndex(FlickrContract.PhotoListEntry._ID)),
                    String.valueOf(photoCursorInfo.getColumnIndex(FlickrContract.PhotoListEntry.SECRET)),
                    "n"
            );

            assert urlPhoto != null;
            String url = urlPhoto.toString();
            Picasso.with(this)
                    .load(url)
                    .into(photoImage);

            String title = String.valueOf(photoCursorInfo.getColumnIndex(FlickrContract.PhotoListEntry.TITLE)).equals("")
                    ? getString(R.string.no_tittle) : String.valueOf(photoCursorInfo.getColumnIndex(FlickrContract.PhotoListEntry.TITLE));
            getSupportActionBar().setTitle(title);
            titleTv.setText(title);

            String userName = String.valueOf(photoCursorInfo.getColumnIndex(FlickrContract.PhotoListEntry.USERNAME)).equals("")
                    ? getString(R.string.no_username) : String.valueOf(photoCursorInfo.getColumnIndex(FlickrContract.PhotoListEntry.USERNAME));
            userNameTv.setText(userName);

            String realName = String.valueOf(photoCursorInfo.getColumnIndex(FlickrContract.PhotoListEntry.REALNAME)).equals("")
                    ? getString(R.string.no_realname) : String.valueOf(photoCursorInfo.getColumnIndex(FlickrContract.PhotoListEntry.REALNAME));
            realNameTv.setText(realName);

            String location = String.valueOf(photoCursorInfo.getColumnIndex(FlickrContract.PhotoListEntry.LOCATION)).equals("")
                    ? getString(R.string.no_location) : String.valueOf(photoCursorInfo.getColumnIndex(FlickrContract.PhotoListEntry.LOCATION));
            locationTv.setText(location);

            String date = String.valueOf(photoCursorInfo.getColumnIndex(FlickrContract.PhotoListEntry.DATE)).equals("")
                    ? getString(R.string.no_date) : String.valueOf(photoCursorInfo.getColumnIndex(FlickrContract.PhotoListEntry.DATE));
            dateTv.setText(date);

            String description = String.valueOf(photoCursorInfo.getColumnIndex(FlickrContract.PhotoListEntry.DESCRIPTION)).equals("")
                    ? getString(R.string.no_description) : String.valueOf(photoCursorInfo.getColumnIndex(FlickrContract.PhotoListEntry.DESCRIPTION));
            descriptionTv.setText(description);
        }
    }
}
