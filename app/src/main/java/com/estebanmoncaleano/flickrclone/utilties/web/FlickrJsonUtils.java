package com.estebanmoncaleano.flickrclone.utilties.web;

import android.util.Log;

import com.estebanmoncaleano.flickrclone.data.model.Comment;
import com.estebanmoncaleano.flickrclone.data.model.Group;
import com.estebanmoncaleano.flickrclone.data.model.People;
import com.estebanmoncaleano.flickrclone.data.model.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FlickrJsonUtils {

    public static String statusCodeMessage;
    private static final String TAG = FlickrJsonUtils.class.getSimpleName();

    public static ArrayList<Photo> getListRecentPhotoFromJson(String photoRecentJsonStr)
            throws JSONException {
        if (errorInJson(photoRecentJsonStr)) return null;

        final String OWM_PHOTOS = "photos";
        final String OWM_PHOTO = "photo";
        final String OWM_ID = "id";
        final String OWM_OWNER = "owner";
        final String OWM_SECRET = "secret";
        final String OWM_SERVER = "server";
        final String OWM_FARM = "farm";
        final String OWM_TITLE = "title";

        JSONArray jsonArray;
        try {
            JSONObject photoJson = new JSONObject(photoRecentJsonStr);
            JSONObject photoJsonInfo = photoJson.getJSONObject(OWM_PHOTOS);
            jsonArray = photoJsonInfo.getJSONArray(OWM_PHOTO);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }

        ArrayList<Photo> photoList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject photoRecentList = jsonArray.getJSONObject(i);
            Photo photoInfo = new Photo(
                    photoRecentList.getInt(OWM_ID),
                    photoRecentList.getString(OWM_OWNER),
                    photoRecentList.getString(OWM_SECRET),
                    photoRecentList.getInt(OWM_SERVER),
                    photoRecentList.getInt(OWM_FARM),
                    photoRecentList.getString(OWM_TITLE),
                    null,
                    null
            );
            photoList.add(photoInfo);
        }
        return photoList;
    }

    public static Photo getPhotoInfoFromJson(String photoInfoJsonStr)
            throws JSONException {
        if (errorInJson(photoInfoJsonStr)) return null;

        final String OWM_PHOTO = "photo";
        final String OWM_ID = "id";
        final String OWM_DESCRIPTION = "description";
        final String OWM_CONTENT = "_content";
        final String OWM_DATES = "dates";
        final String OWM_TAKEN = "taken";

        Photo photo = new Photo();
        JSONObject photoJson = new JSONObject(photoInfoJsonStr);
        JSONObject photoJsonInfo = photoJson.getJSONObject(OWM_PHOTO);
        photo.setId(photoJsonInfo.getInt(OWM_ID));

        JSONObject phoJsonDescription = photoJsonInfo.getJSONObject(OWM_DESCRIPTION);
        photo.setDescription(phoJsonDescription.getString(OWM_CONTENT));

        JSONObject phoJsonDates = photoJsonInfo.getJSONObject(OWM_DATES);
        photo.setDate(phoJsonDates.getString(OWM_TAKEN));
        return photo;
    }

    public static ArrayList<Comment> getCommentsFromJson(String commentsInfoJsonStr)
            throws JSONException {
        if (errorInJson(commentsInfoJsonStr)) return null;

        final String OWM_COMMENTS = "comments";
        final String OWM_COMMENT = "comment";
        final String OWM_PHOTO_ID = "photo_id";
        final String OWM_ID = "id";
        final String OWM_AUTHOR = "author";
        final String OWM_AUTHORNAME = "author";
        final String OWM_CONTENT = "_content";

        JSONArray jsonArray;
        int photoId;
        try {
            JSONObject commentJson = new JSONObject(commentsInfoJsonStr);
            JSONObject commentJsonInfo = commentJson.getJSONObject(OWM_COMMENTS);
            photoId = commentJsonInfo.getInt(OWM_PHOTO_ID);
            jsonArray = commentJsonInfo.getJSONArray(OWM_COMMENT);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }

        ArrayList<Comment> commentArrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject commentList = jsonArray.getJSONObject(i);
            Comment comment = new Comment(
                    commentList.getInt(OWM_ID),
                    photoId,
                    commentList.getString(OWM_AUTHOR),
                    commentList.getString(OWM_AUTHORNAME),
                    commentList.getString(OWM_CONTENT)
            );
            commentArrayList.add(comment);
        }
        return commentArrayList;
    }

    public static ArrayList<Photo> getListSearchPhotoFromJson(String photoSearchJsonStr)
            throws JSONException {
        if (errorInJson(photoSearchJsonStr)) return null;

        final String OWM_PHOTOS = "photos";
        final String OWM_PHOTO = "photo";
        final String OWM_ID = "id";
        final String OWM_OWNER = "owner";
        final String OWM_SECRET = "secret";
        final String OWM_SERVER = "server";
        final String OWM_FARM = "farm";
        final String OWM_TITLE = "title";

        JSONArray jsonArray;
        try {
            JSONObject photoJson = new JSONObject(photoSearchJsonStr);
            JSONObject photoJsonInfo = photoJson.getJSONObject(OWM_PHOTOS);
            jsonArray = photoJsonInfo.getJSONArray(OWM_PHOTO);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }

        ArrayList<Photo> photoList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject photoRecentList = jsonArray.getJSONObject(i);
            Photo photoInfo = new Photo(
                    photoRecentList.getInt(OWM_ID),
                    photoRecentList.getString(OWM_OWNER),
                    photoRecentList.getString(OWM_SECRET),
                    photoRecentList.getInt(OWM_SERVER),
                    photoRecentList.getInt(OWM_FARM),
                    photoRecentList.getString(OWM_TITLE),
                    null,
                    null
            );
            photoList.add(photoInfo);
        }
        return photoList;
    }

    public static int getFindByUsernameFromJson(String usernameSearchJsonStr)
            throws JSONException {

        if (errorInJson(usernameSearchJsonStr)) return 0;
        final String OWM_USER = "user";
        final String OWM_ID = "id";

        try {
            JSONObject userJson = new JSONObject(usernameSearchJsonStr);
            JSONObject userJsonInfo = userJson.getJSONObject(OWM_USER);
            return userJsonInfo.getInt(OWM_ID);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return 0;
        }
    }

    public static People getPeopleInfoFromJson(String peopleSearchJsonStr)
            throws JSONException {

        if (errorInJson(peopleSearchJsonStr)) return null;
        final String OWM_PERSON = "people";
        final String OWM_ID = "id";
        final String OWM_USERNAME = "username";
        final String OWM_REALNAME = "realname";
        final String OWM_LOCATION = "location";
        final String OWM_CONTENT = "_content";
        final String OWM_DESCRIPTION = "description";
        final String OWM_PHOTO_URL = "photosurl";

        People people = new People();

        try {
            JSONObject jsonObject = new JSONObject(peopleSearchJsonStr);
            JSONObject peopleJsonInfo = jsonObject.getJSONObject(OWM_PERSON);
            people.setId(peopleJsonInfo.getString(OWM_ID));

            peopleJsonInfo = jsonObject.getJSONObject(OWM_USERNAME);
            people.setUsername(peopleJsonInfo.getString(OWM_CONTENT));

            peopleJsonInfo = jsonObject.getJSONObject(OWM_REALNAME);
            people.setRealname(peopleJsonInfo.getString(OWM_CONTENT));

            peopleJsonInfo = jsonObject.getJSONObject(OWM_LOCATION);
            people.setLocation(peopleJsonInfo.getString(OWM_CONTENT));

            peopleJsonInfo = jsonObject.getJSONObject(OWM_DESCRIPTION);
            people.setDescription(peopleJsonInfo.getString(OWM_CONTENT));

            peopleJsonInfo = jsonObject.getJSONObject(OWM_PHOTO_URL);
            people.setPhoto_url(peopleJsonInfo.getString(OWM_CONTENT));

            return people;
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static ArrayList<Photo> getPeoplePhotosFromJson(String peoplePhotosJsonStr)
            throws JSONException {
        if (errorInJson(peoplePhotosJsonStr)) return null;

        final String OWM_PHOTOS = "photos";
        final String OWM_PHOTO = "photo";
        final String OWM_ID = "id";
        final String OWM_OWNER = "owner";
        final String OWM_SECRET = "secret";
        final String OWM_SERVER = "server";
        final String OWM_FARM = "farm";
        final String OWM_TITLE = "title";

        JSONArray jsonArray;
        try {
            JSONObject photoJson = new JSONObject(peoplePhotosJsonStr);
            JSONObject photoJsonInfo = photoJson.getJSONObject(OWM_PHOTOS);
            jsonArray = photoJsonInfo.getJSONArray(OWM_PHOTO);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }

        ArrayList<Photo> photoList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject photoRecentList = jsonArray.getJSONObject(i);
            Photo photoInfo = new Photo(
                    photoRecentList.getInt(OWM_ID),
                    photoRecentList.getString(OWM_OWNER),
                    photoRecentList.getString(OWM_SECRET),
                    photoRecentList.getInt(OWM_SERVER),
                    photoRecentList.getInt(OWM_FARM),
                    photoRecentList.getString(OWM_TITLE),
                    null,
                    null
            );
            photoList.add(photoInfo);
        }
        return photoList;
    }

    public static ArrayList<Group> getGroupSearchFromJson(String groupSearchJsonStr)
            throws JSONException {
        if (errorInJson(groupSearchJsonStr)) return null;

        final String OWM_GROUPS = "groups";
        final String OWM_GROUP = "group";
        final String OWM_NSID = "nsid";
        final String OWM_NAME = "name";

        JSONArray jsonArray;
        try {
            JSONObject photoJson = new JSONObject(groupSearchJsonStr);
            JSONObject photoJsonInfo = photoJson.getJSONObject(OWM_GROUPS);
            jsonArray = photoJsonInfo.getJSONArray(OWM_GROUP);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }

        ArrayList<Group> groupArrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Group group = new Group();
            group.setId(jsonObject.getString(OWM_NSID));
            group.setName(jsonObject.getString(OWM_NAME));
            groupArrayList.add(group);
        }
        return groupArrayList;
    }

    public static Group getGroupInfoFromJson(String groupSearchJsonStr)
            throws JSONException {

        if (errorInJson(groupSearchJsonStr)) return null;
        final String OWM_GROUP = "group";
        final String OWM_ID = "id";
        final String OWM_NAME = "name";
        final String OWM_DESCRIPTION = "description";
        final String OWM_RULES = "rules";
        final String OWM_MEMBERS = "members";
        final String OWM_TOPIC_COUNT = "topic_count";
        final String OWM_CONTENT = "_content";

        Group group = new Group();

        try {
            JSONObject jsonObject = new JSONObject(groupSearchJsonStr);
            JSONObject groupJsonInfo = jsonObject.getJSONObject(OWM_GROUP);
            group.setId(groupJsonInfo.getString(OWM_ID));

            groupJsonInfo = jsonObject.getJSONObject(OWM_NAME);
            group.setName(groupJsonInfo.getString(OWM_CONTENT));

            groupJsonInfo = jsonObject.getJSONObject(OWM_DESCRIPTION);
            group.setDescription(groupJsonInfo.getString(OWM_CONTENT));

            groupJsonInfo = jsonObject.getJSONObject(OWM_RULES);
            group.setRules(groupJsonInfo.getString(OWM_CONTENT));

            groupJsonInfo = jsonObject.getJSONObject(OWM_MEMBERS);
            group.setMembers(groupJsonInfo.getInt(OWM_CONTENT));

            groupJsonInfo = jsonObject.getJSONObject(OWM_TOPIC_COUNT);
            group.setTopics(groupJsonInfo.getInt(OWM_CONTENT));

            return group;
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    private static boolean errorInJson(String photoRecentJsonStr) {
        final String OWM_STAT = "stat";
        final String OWM_STAT_MESSAGE = "message";

        try {
            JSONObject photoJson = new JSONObject(photoRecentJsonStr);
            if (photoJson.has(OWM_STAT)) {
                if (photoJson.getString(OWM_STAT).equalsIgnoreCase("fail")) {
                    statusCodeMessage = photoJson.getString(OWM_STAT_MESSAGE);
                    return true;
                }
            }
            return false;
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }
}
