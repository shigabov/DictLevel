package ru.asbvapps.android.dictlevel.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import ru.asbvapps.android.dictlevel.data.DictContract;

/**
 * Created by Артем on 17.10.2015.
 */
public class DictLevelProvider extends ContentProvider {

        // The URI Matcher used by this content provider.
        private static final UriMatcher sUriMatcher = buildUriMatcher();
        private DictLevelDBHelper mOpenHelper;

        static final int WORDS  = 100;
        static final int GROUPS = 101;
        /*
            Students: Here is where you need to create the UriMatcher. This UriMatcher will
            match each URI to the WEATHER, WEATHER_WITH_LOCATION, WEATHER_WITH_LOCATION_AND_DATE,
            and LOCATION integer constants defined above.  You can test this by uncommenting the
            testUriMatcher test within TestUriMatcher.
         */
        static UriMatcher buildUriMatcher() {
            // I know what you're thinking.  Why create a UriMatcher when you can use regular
            // expressions instead?  Because you're not crazy, that's why.

            // All paths added to the UriMatcher have a corresponding code to return when a match is
            // found.  The code passed into the constructor represents the code to return for the root
            // URI.  It's common to use NO_MATCH as the code for this case.
            final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
            final String authority = DictContract.CONTENT_AUTHORITY;

            // For each type of URI you want to add, create a corresponding code.
            matcher.addURI(authority, DictContract.PATH_WORDS, WORDS);
           // matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*", WEATHER_WITH_LOCATION);
           // matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*/#", WEATHER_WITH_LOCATION_AND_DATE);

            matcher.addURI(authority, DictContract.PATH_GROUPS, GROUPS);
            return matcher;
        }

        /*
            Students: We've coded this for you.  We just create a new WeatherDbHelper for later use
            here.
         */
        @Override
        public boolean onCreate() {
            mOpenHelper = new DictLevelDBHelper(getContext());
            return true;
        }

        /*
            Students: Here's where you'll code the getType function that uses the UriMatcher.  You can
            test this by uncommenting testGetType in TestProvider.

         */
        @Override
        public String getType(Uri uri) {

            // Use the Uri Matcher to determine what kind of URI this is.
            final int match = sUriMatcher.match(uri);

            switch (match) {
                // Student: Uncomment and fill out these two cases

                case WORDS:
                    return DictContract.WordsEntry.CONTENT_TYPE;
                case GROUPS:
                    return DictContract.GroupEntry.CONTENT_TYPE;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        @Override
        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                            String sortOrder) {
            // Here's the switch statement that, given a URI, will determine what kind of request it is,
            // and query the database accordingly.
            Cursor retCursor;
            switch (sUriMatcher.match(uri)) {
                // "weather/*/*"

                // "weather"
                case WORDS: {
                    retCursor = mOpenHelper.getReadableDatabase().query(
                            DictContract.WordsEntry.TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder
                    );
                    break;
                }
                // "location"
                case GROUPS: {
                    retCursor = mOpenHelper.getReadableDatabase().query(
                            DictContract.GroupEntry.TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder
                    );
                    break;
                }

                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
            retCursor.setNotificationUri(getContext().getContentResolver(), uri);
            return retCursor;
        }

        /*
            Student: Add the ability to insert Locations to the implementation of this function.
         */
        @Override
        public Uri insert(Uri uri, ContentValues values) {
            final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            final int match = sUriMatcher.match(uri);
            Uri returnUri;

            switch (match) {
                case WORDS: {
                    long _id = db.insert(DictContract.WordsEntry.TABLE_NAME, null, values);
                    if ( _id > 0 )
                        returnUri = DictContract.WordsEntry.buildWordsUri(_id);
                    else
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    break;
                }
                case GROUPS: {
                    long _id = db.insert(DictContract.GroupEntry.TABLE_NAME, null, values);
                    if ( _id > 0 )
                        returnUri = DictContract.GroupEntry.buildGroupsUri(_id);
                    else
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    break;
                }
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return returnUri;
        }

        @Override
        public int delete(Uri uri, String selection, String[] selectionArgs) {
            final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            final int match = sUriMatcher.match(uri);
            int rowsDeleted;
            // this makes delete all rows return the number of rows deleted
            if ( null == selection ) selection = "1";
            switch (match) {
                case WORDS:
                    rowsDeleted = db.delete(
                            DictContract.WordsEntry.TABLE_NAME, selection, selectionArgs);
                    break;
                case GROUPS:
                    rowsDeleted = db.delete(
                            DictContract.GroupEntry.TABLE_NAME, selection, selectionArgs);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
            // Because a null deletes all rows
            if (rowsDeleted != 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return rowsDeleted;
        }

        @Override
        public int update(
                Uri uri, ContentValues values, String selection, String[] selectionArgs) {
            final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            final int match = sUriMatcher.match(uri);
            int rowsUpdated;

            switch (match) {
                case WORDS:
                    rowsUpdated = db.update(DictContract.WordsEntry.TABLE_NAME, values, selection,
                            selectionArgs);
                    break;
                case GROUPS:
                    rowsUpdated = db.update(DictContract.GroupEntry.TABLE_NAME, values, selection,
                            selectionArgs);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
            if (rowsUpdated != 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return rowsUpdated;
        }

        @Override
        public int bulkInsert(Uri uri, ContentValues[] values) {
            final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            final int match = sUriMatcher.match(uri);
            switch (match) {
                case WORDS:
                    db.beginTransaction();
                    int returnCount = 0;
                    try {
                        for (ContentValues value : values) {

                            long _id = db.insert(DictContract.WordsEntry.TABLE_NAME, null, value);
                            if (_id != -1) {
                                returnCount++;
                            }
                        }
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }
                    getContext().getContentResolver().notifyChange(uri, null);
                    return returnCount;
                default:
                    return super.bulkInsert(uri, values);
            }
        }

        // You do not need to call this method. This is a method specifically to assist the testing
        // framework in running smoothly. You can read more at:
        // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
        @Override
        @TargetApi(11)
        public void shutdown() {
            mOpenHelper.close();
            super.shutdown();
        }

}
