package com.estebanmoncaleano.flickrclone.data.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class FlickrContract {

    public static final String AUTHORITY = "com.estebanmoncaleano.flickrclone";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_PHOTOS = "photos";
    public static final String PATH_COMMENTS = "comments";
    public static final String PATH_PERSONS = "persons";
    public static final String PATH_GROUPS = "groups";

    public static final class PhotoListEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PHOTOS).build();

        public static final String TABLE_NAME = "PhotoList";

        public static final String OWNER = "owner";
        public static final String SECRET = "secret";
        public static final String SERVER = "server";
        public static final String FARM = "farm";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String DATE = "date";
    }

    public static final class CommentListEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COMMENTS).build();

        public static final String TABLE_NAME = "CommentList";

        public static final String PHOTO_ID = "photo_id";
        public static final String AUTHOR = "author";
        public static final String AUTHOR_NAME = "authorname";
        public static final String MESSAGE = "farm";
    }

    public static final class PersonListEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PERSONS).build();

        public static final String TABLE_NAME = "PersonList";

        public static final String USERNAME = "username";
        public static final String REALNAME = "realname";
        public static final String LOCATION = "location";
        public static final String DESCRIPTION = "description";
        public static final String PHOTO_URL = "photosurl";
    }

    public static final class GroupListEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_GROUPS).build();

        public static final String TABLE_NAME = "GroupList";

        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String RULES = "rules";
        public static final String MEMBERS = "members";
        public static final String TOPIC_COUNT = "topic_count";
    }
}
