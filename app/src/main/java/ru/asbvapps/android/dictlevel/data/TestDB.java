package ru.asbvapps.android.dictlevel.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Артем on 16.10.2015.
 */
public class TestDB {
    public static String LOG_TAG = TestDB.class.getSimpleName();

    public void TestDB() {

    }

    ;

    public void doTest(Context c) {
        //SQLiteDatabase db = new DictLevelDBHelper(c).getWritableDatabase();


        DictLevelDBHelper myDbHelper = new DictLevelDBHelper(c);
       // myDbHelper = new DictLevelDBHelper(this);

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        SQLiteDatabase db = myDbHelper.getWritableDatabase();

        ContentValues testData = createTestData();
        long rowId = db.insert(DictContract.WordsEntry.TABLE_NAME, null, testData);
        if (rowId != -1) {
            Log.d(LOG_TAG, "row inserted");
        }
        Cursor cursor = db.query(
                DictContract.WordsEntry.TABLE_NAME,
                new String[]{DictContract.WordsEntry.COL_NAME},
                DictContract.WordsEntry.COL_LANG + "=?",
                new String[]{"english"},
                null,
                null,
                DictContract.WordsEntry.COL_NAME + " DESC");

        cursor.close();
        db.close();

    }

    static ContentValues createTestData() {
        ContentValues testValues = new ContentValues();
        testValues.put(DictContract.WordsEntry.COL_NAME, "apple");
        testValues.put(DictContract.WordsEntry.COL_GROUP_ID, 1);
        testValues.put(DictContract.WordsEntry.COL_LANG, "english");
        return testValues;

    }

}
