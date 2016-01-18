package ru.asbvapps.android.dictlevel.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Артем on 11.10.2015.
 */
public class DictContract {

    public static final String CONTENT_AUTHORITY = "ru.asbvapps.android.dictlevel.app";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_WORDS = "words";
    public static final String PATH_WORDS_UI = "words";
    public static final String PATH_GROUPS = "groups";
    public static final String PATH_LANG = "languages";

    public static final Uri WORDS_UI_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORDS_UI).build();

    public static final class WordsEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORDS).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WORDS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WORDS;
        public final static String TABLE_NAME = "words";
        public static final String COL_NAME = "name";
        public static final String COL_LANG = "language";
        public static final String COL_GROUP_ID = "group_id ";

        public static Uri buildWordsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Uri buildWordsUIUri (String lang){
            return CONTENT_URI.buildUpon().appendPath(lang).build();
        }
    }

    public static final class GroupEntry implements BaseColumns {
        public final static String TABLE_NAME = "groups";
        public static final String COL_NAME = "name";
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_GROUPS).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GROUPS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GROUPS;

        public static Uri buildGroupsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class LangEntry implements BaseColumns {
        public final static String TABLE_NAME = "languages";
        public final static String COL_NAME = "name";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LANG).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LANG;
    }

    public static String getLanguageFromUri(Uri uri) {
        return uri.getPathSegments().get(1);
    }


}