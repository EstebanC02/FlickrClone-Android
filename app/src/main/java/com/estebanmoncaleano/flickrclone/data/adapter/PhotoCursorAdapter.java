package com.estebanmoncaleano.flickrclone.data.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.estebanmoncaleano.flickrclone.R;
import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;
import com.estebanmoncaleano.flickrclone.utilties.web.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class PhotoCursorAdapter extends RecyclerView.Adapter<PhotoCursorAdapter.PhotoViewHolder> {

    private static final String TAG = PhotoCursorAdapter.class.getSimpleName();

    private Cursor photoRecentCursor;
    private Context context;
    private final ListPhotoClickListener onClickListener;

    public PhotoCursorAdapter(ListPhotoClickListener listener) {
        onClickListener = listener;
    }

    public interface ListPhotoClickListener {
        void onListItemClick(long clickedItemIndex);
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.photo_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder photoViewHolder, int i) {
        if (photoRecentCursor.moveToPosition(i)) {

            URL urlPhoto = NetworkUtils.buildURLPhotoSource(
                    String.valueOf(photoRecentCursor.getColumnIndex(FlickrContract.PhotoListEntry.FARM)),
                    String.valueOf(photoRecentCursor.getColumnIndex(FlickrContract.PhotoListEntry.SERVER)),
                    String.valueOf(photoRecentCursor.getColumnIndex(FlickrContract.PhotoListEntry._ID)),
                    String.valueOf(photoRecentCursor.getColumnIndex(FlickrContract.PhotoListEntry.SECRET)),
                    "n"
            );

            assert urlPhoto != null;
            String url = urlPhoto.toString();
            Picasso.with(context)
                    .load(url)
                    .into(photoViewHolder.photoListImage);
        }

        if (!NetworkUtils.isNetworkAvailable(context)) {
            photoViewHolder.errorView.setVisibility(View.VISIBLE);
            photoViewHolder.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (photoRecentCursor == null) return 0;
        return photoRecentCursor.getCount();
    }

    public void setPhotoCursorData(Cursor photos) {
        photoRecentCursor = photos;
        notifyDataSetChanged();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView photoListImage;
        private final ProgressBar progressBar;
        private final RelativeLayout errorView;

        private PhotoViewHolder(View itemView) {
            super(itemView);
            errorView = (RelativeLayout) itemView.findViewById(R.id.rl_error_photo_item);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_photo_item);
            photoListImage = (ImageView) itemView.findViewById(R.id.iv_photo_item);
            photoListImage.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int selectedPhoto = getAdapterPosition();
            if (photoRecentCursor.moveToPosition(selectedPhoto)) {
                long id = photoRecentCursor.getLong(photoRecentCursor.getColumnIndex(FlickrContract.PhotoListEntry._ID));
                onClickListener.onListItemClick(id);
            }
        }
    }
}
