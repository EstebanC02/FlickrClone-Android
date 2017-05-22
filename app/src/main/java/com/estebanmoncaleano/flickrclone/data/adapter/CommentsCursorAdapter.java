package com.estebanmoncaleano.flickrclone.data.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.estebanmoncaleano.flickrclone.R;
import com.estebanmoncaleano.flickrclone.data.database.FlickrContract;

public class CommentsCursorAdapter extends RecyclerView.Adapter<CommentsCursorAdapter.CommentViewHolder> {

    private Cursor commentCursor;
    private Context context;

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.comment_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new CommentsCursorAdapter.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder commentViewHolder, int i) {
        if (commentCursor.moveToPosition(i)) {
            commentViewHolder.commentRealname.setText(String.valueOf(commentCursor.getColumnIndex(FlickrContract.CommentListEntry.AUTHOR_NAME)));
            commentViewHolder.commentUsername.setText(String.valueOf(commentCursor.getColumnIndex(FlickrContract.CommentListEntry.AUTHOR)));
            commentViewHolder.commentMessage.setText(String.valueOf(commentCursor.getColumnIndex(FlickrContract.CommentListEntry.MESSAGE)));
        }
    }

    @Override
    public int getItemCount() {
        if (commentCursor == null) return 0;
        return commentCursor.getCount();
    }

    public void setCommentsCursorData(Cursor comments) {
        commentCursor = comments;
        notifyDataSetChanged();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private final TextView commentRealname;
        private final TextView commentUsername;
        private final TextView commentMessage;

        private CommentViewHolder(View itemView) {
            super(itemView);
            commentRealname = (TextView) itemView.findViewById(R.id.tv_comment_realname);
            commentUsername = (TextView) itemView.findViewById(R.id.tv_comment_username);
            commentMessage = (TextView) itemView.findViewById(R.id.tv_comment_message);
        }
    }
}
