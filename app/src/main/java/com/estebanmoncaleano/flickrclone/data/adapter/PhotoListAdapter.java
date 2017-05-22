package com.estebanmoncaleano.flickrclone.data.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.estebanmoncaleano.flickrclone.R;
import com.estebanmoncaleano.flickrclone.data.model.Photo;
import com.estebanmoncaleano.flickrclone.utilties.web.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder> {

    private static final String TAG = PhotoListAdapter.class.getSimpleName();

    private ArrayList<Photo> photoRecentList;
    private Context context;
    private final ListPhotoClickListener onClickListener;

    public PhotoListAdapter(ListPhotoClickListener listener) {
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
        URL urlPhoto = NetworkUtils.buildURLPhotoSource(
                String.valueOf(photoRecentList.get(i).getFarm()),
                String.valueOf(photoRecentList.get(i).getServer()),
                String.valueOf(photoRecentList.get(i).getId()),
                photoRecentList.get(i).getSecret(),
                "n"
        );

        assert urlPhoto != null;
        String url = urlPhoto.toString();
        Picasso.with(context)
                .load(url)
                .into(photoViewHolder.photoListImage);

        if(!NetworkUtils.isNetworkAvailable(context))
            photoViewHolder.errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        if (photoRecentList == null) return 0;
        return photoRecentList.size();
    }

    public void setPhotoListData(ArrayList<Photo> photos) {
        if (photoRecentList != null) {
            photos.addAll(photoRecentList);
            photoRecentList = photos;
        }
        else {
            photoRecentList = photos;
        }
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
            long photoId = photoRecentList.get(selectedPhoto).getId();
            onClickListener.onListItemClick(photoId);
        }
    }
}
