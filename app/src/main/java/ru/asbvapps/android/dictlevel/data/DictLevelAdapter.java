package ru.asbvapps.android.dictlevel.data;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import ru.asbvapps.android.dictlevel.TestingFragment;

/**
 * Created by Артем on 18.10.2015.
 */
public class DictLevelAdapter extends CursorAdapter {


    public DictLevelAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return new TextView(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textview = (TextView) view;
        textview.setText(cursor.getString(TestingFragment.COL_NAME));
    }
}
