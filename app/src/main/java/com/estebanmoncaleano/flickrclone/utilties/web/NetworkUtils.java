package com.estebanmoncaleano.flickrclone.utilties.web;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.estebanmoncaleano.flickrclone.PhotoSearchActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    static {
        System.loadLibrary("keys");
    }

    public static native String getFlickrAPIkey();

    private final static String FLICKR_PHOTO_SOURCE_FARM_URL = "https://farm";
    private final static String FLICKR_PHOTO_SOURCE_STATIC_FLICKR_URL = ".staticflickr.com/";
    private final static String FLICKR_BASE_URL = "https://api.flickr.com/services/rest/?";
    private final static String PARAM_METHOD_KEY = "method";
    private final static String PARAM_FLICKR_API_KEY = "api_key";
    private final static String FLICKR_API_KEY = new String(Base64.decode(getFlickrAPIkey(), Base64.DEFAULT));

    private final static String PARAM_FORMAT = "format";
    private final static String PARAM_JSON = "json";
    private final static String PARAM_NO_JSON_CALLBACK_KEY = "nojsoncallback";
    private final static String PARAM_NO_JSON_CALLBACK = "1";

    public static URL buildURLPhotoSource(String farm, String server_id, String photo_id,
                                          String secret) {
        Uri builtUri = Uri.parse(
                FLICKR_PHOTO_SOURCE_FARM_URL
                        + farm
                        + FLICKR_PHOTO_SOURCE_STATIC_FLICKR_URL)
                .buildUpon()
                .appendPath(server_id)
                .appendPath(photo_id + "_" + secret + ".jpg")
                .build();

        try {
            return new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static URL buildURLPhotoSource(String farm, String server_id, String photo_id,
                                          String secret, String size) {
        Uri builtUri = Uri.parse(
                FLICKR_PHOTO_SOURCE_FARM_URL
                        + farm
                        + FLICKR_PHOTO_SOURCE_STATIC_FLICKR_URL)
                .buildUpon()
                .appendPath(server_id)
                .appendPath(photo_id + "_" + secret + "_" + size + ".jpg")
                .build();

        try {
            return new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private final static String PARAM_METHOD_GETRECENT = "flickr.photos.getRecent";

    public static URL buildUrlGetRecent() {
        Uri builtUri = Uri.parse(FLICKR_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_METHOD_KEY, PARAM_METHOD_GETRECENT)
                .appendQueryParameter(PARAM_FLICKR_API_KEY, FLICKR_API_KEY)
                .appendQueryParameter(PARAM_FORMAT, PARAM_JSON)
                .appendQueryParameter(PARAM_NO_JSON_CALLBACK_KEY, PARAM_NO_JSON_CALLBACK)
                .build();

        try {
            return new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private final static String PARAM_TAGS = "tags";
    private final static String PARAM_PER_PAGE = "per_page";
    private final static String PARAM_PER_PAGE_VALUE = "10";
    private final static String PARAM_PAGE = "page";
    public static String PAGE_VALUE_KEY = "page";
    private final static String PARAM_METHOD_SEARCH = "flickr.photos.search";

    public static URL buildUrlSearch(String valueSearch, int valuePage) {
        Log.i(TAG, "Value page: " + String.valueOf(valuePage));
        Uri builtUri = Uri.parse(FLICKR_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_METHOD_KEY, PARAM_METHOD_SEARCH)
                .appendQueryParameter(PARAM_FLICKR_API_KEY, FLICKR_API_KEY)
                .appendQueryParameter(PARAM_TAGS, valueSearch)
                .appendQueryParameter(PARAM_PER_PAGE, PARAM_PER_PAGE_VALUE)
                .appendQueryParameter(PARAM_PAGE, String.valueOf(valuePage))
                .appendQueryParameter(PARAM_FORMAT, PARAM_JSON)
                .appendQueryParameter(PARAM_NO_JSON_CALLBACK_KEY, PARAM_NO_JSON_CALLBACK)
                .build();

        try {
            return new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private final static String PARAM_USERNAME = "username";
    private final static String PARAM_METHOD_FINDBYUSERNAME = "flickr.people.findByUsername";

    public static URL buildUrlPeopleFindByUsername(String usernameSearch) {
        Uri builtUri = Uri.parse(FLICKR_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_METHOD_KEY, PARAM_METHOD_FINDBYUSERNAME)
                .appendQueryParameter(PARAM_FLICKR_API_KEY, FLICKR_API_KEY)
                .appendQueryParameter(PARAM_USERNAME, usernameSearch)
                .appendQueryParameter(PARAM_FORMAT, PARAM_JSON)
                .appendQueryParameter(PARAM_NO_JSON_CALLBACK_KEY, PARAM_NO_JSON_CALLBACK)
                .build();

        try {
            return new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private final static String PARAM_USER_ID = "user_id";
    private final static String PARAM_METHOD_GETINFO = "flickr.people.getInfo";

    public static URL buildUrlPeopleGetInfo(String userIdSearch) {
        Uri builtUri = Uri.parse(FLICKR_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_METHOD_KEY, PARAM_METHOD_GETINFO)
                .appendQueryParameter(PARAM_FLICKR_API_KEY, FLICKR_API_KEY)
                .appendQueryParameter(PARAM_USER_ID, userIdSearch)
                .appendQueryParameter(PARAM_FORMAT, PARAM_JSON)
                .appendQueryParameter(PARAM_NO_JSON_CALLBACK_KEY, PARAM_NO_JSON_CALLBACK)
                .build();

        try {
            return new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private final static String PARAM_METHOD_GETPHOTOS = "flickr.people.getPhotos";

    public static URL buildUrlPeopleGetPhotos(String userIdSearch) {
        Uri builtUri = Uri.parse(FLICKR_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_METHOD_KEY, PARAM_METHOD_GETPHOTOS)
                .appendQueryParameter(PARAM_FLICKR_API_KEY, FLICKR_API_KEY)
                .appendQueryParameter(PARAM_USER_ID, userIdSearch)
                .appendQueryParameter(PARAM_FORMAT, PARAM_JSON)
                .appendQueryParameter(PARAM_NO_JSON_CALLBACK_KEY, PARAM_NO_JSON_CALLBACK)
                .build();

        try {
            return new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private final static String PARAM_METHOD_PHOTO_GET_INFO = "flickr.photos.getInfo";

    public static URL buildUrlPhotoGetInfo(String photoIdSearch) {
        Uri builtUri = Uri.parse(FLICKR_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_METHOD_KEY, PARAM_METHOD_PHOTO_GET_INFO)
                .appendQueryParameter(PARAM_FLICKR_API_KEY, FLICKR_API_KEY)
                .appendQueryParameter(PARAM_USER_ID, photoIdSearch)
                .appendQueryParameter(PARAM_FORMAT, PARAM_JSON)
                .appendQueryParameter(PARAM_NO_JSON_CALLBACK_KEY, PARAM_NO_JSON_CALLBACK)
                .build();

        try {
            return new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private final static String PARAM_METHOD_PHOTO_GET_COMMENTS = "flickr.photos.comments.getList";

    public static URL buildUrlPhotoGetComments(String photoIdSearch) {
        Uri builtUri = Uri.parse(FLICKR_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_METHOD_KEY, PARAM_METHOD_PHOTO_GET_COMMENTS)
                .appendQueryParameter(PARAM_FLICKR_API_KEY, FLICKR_API_KEY)
                .appendQueryParameter(PARAM_USER_ID, photoIdSearch)
                .appendQueryParameter(PARAM_FORMAT, PARAM_JSON)
                .appendQueryParameter(PARAM_NO_JSON_CALLBACK_KEY, PARAM_NO_JSON_CALLBACK)
                .build();

        try {
            return new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private final static String PARAM_METHOD_GROUPS_SEARCH = "flickr.groups.search";

    public static URL buildUrlGroupSearch(String textSearch) {
        Uri builtUri = Uri.parse(FLICKR_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_METHOD_KEY, PARAM_METHOD_GROUPS_SEARCH)
                .appendQueryParameter(PARAM_FLICKR_API_KEY, FLICKR_API_KEY)
                .appendQueryParameter(PARAM_USER_ID, textSearch)
                .appendQueryParameter(PARAM_FORMAT, PARAM_JSON)
                .appendQueryParameter(PARAM_NO_JSON_CALLBACK_KEY, PARAM_NO_JSON_CALLBACK)
                .build();

        try {
            return new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private final static String PARAM_METHOD_GROUPS_GETINFO = "flickr.groups.getInfo";

    public static URL buildUrlGroupGetInfo(String groupIdSearch) {
        Uri builtUri = Uri.parse(FLICKR_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_METHOD_KEY, PARAM_METHOD_GROUPS_GETINFO)
                .appendQueryParameter(PARAM_FLICKR_API_KEY, FLICKR_API_KEY)
                .appendQueryParameter(PARAM_USER_ID, groupIdSearch)
                .appendQueryParameter(PARAM_FORMAT, PARAM_JSON)
                .appendQueryParameter(PARAM_NO_JSON_CALLBACK_KEY, PARAM_NO_JSON_CALLBACK)
                .build();

        try {
            return new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
