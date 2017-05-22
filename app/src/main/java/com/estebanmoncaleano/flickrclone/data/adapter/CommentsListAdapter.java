package com.estebanmoncaleano.flickrclone.data.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.estebanmoncaleano.flickrclone.R;
import com.estebanmoncaleano.flickrclone.data.model.Comment;

import java.util.ArrayList;

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.CommentViewHolder> {

    private ArrayList<Comment> commentRecentList;
    private Context context;

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.comment_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new CommentsListAdapter.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder commentViewHolder, int i) {
        Comment comment = commentRecentList.get(i);
        if (comment != null) {
            commentViewHolder.commentRealname.setText(comment.getAuthor_name());
            commentViewHolder.commentUsername.setText(comment.getAuthor());
            commentViewHolder.commentMessage.setText(comment.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        if (commentRecentList == null) return 0;
        return commentRecentList.size();
    }

    public void setCommentListData(ArrayList<Comment> comment) {
        if (commentRecentList != null && comment != null) {
            comment.addAll(commentRecentList);
            commentRecentList = comment;
        } else {
            commentRecentList = comment;
        }
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
