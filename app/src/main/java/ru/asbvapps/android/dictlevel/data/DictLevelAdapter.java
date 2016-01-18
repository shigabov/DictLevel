package ru.asbvapps.android.dictlevel.data;

import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.asbvapps.android.dictlevel.R;
import ru.asbvapps.android.dictlevel.TestingFragment;
import ru.asbvapps.android.dictlevel.Word;

/**
 * Created by Артем on 18.10.2015.
 */
public class DictLevelAdapter extends CursorAdapter {
    private final String LOG_TAG = DictLevelAdapter.class.getSimpleName();
    int colorChecked;
    int colorUnChecked;

    public ArrayList<Word> mSelectedWords = new ArrayList<>();
    public ArrayList<Word> mWords = new ArrayList<>();
    public DictLevelAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //return new
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView v = (TextView) inflater.inflate(R.layout.grid_element,null);
        colorChecked = context.getResources().getColor(R.color.colorAccent);
        colorUnChecked = context.getResources().getColor(R.color.colorUnCheked);

        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //Log.d(LOG_TAG, "bindView");
        TextView textview = (TextView) view;
        Word word = new Word(
                cursor.getInt(TestingFragment.COL_ID),
                cursor.getString(TestingFragment.COL_NAME)
        );

        textview.setText(cursor.getString(TestingFragment.COL_NAME));
        textview.setTag(cursor.getInt(TestingFragment.COL_ID));

        if (mSelectedWords.contains(word)){
            textview.setBackgroundColor(colorChecked);
        }
        else textview.setBackgroundColor(colorUnChecked);

        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "onClick");

                //startTestingActivity(R.id.rb_eng);.
                TextView tv = (TextView) v;
                Word word = new Word((int) tv.getTag(), tv.getText().toString());

                Log.d(LOG_TAG, "COL_NAME=" + tv.getText().toString());
                Log.d(LOG_TAG, "COL_ID=" + tv.getTag());

                int pos = mSelectedWords.indexOf(word);

                if (pos !=-1) {
                    mSelectedWords.remove(pos);
                    tv.setBackgroundColor(colorUnChecked);
                    Log.d(LOG_TAG, "mSelectedWords = " + mSelectedWords.size());

                } else {
                    mSelectedWords.add(word);
                    tv.setBackgroundColor(colorChecked);
                    Log.d(LOG_TAG, "mSelectedWords = " + mSelectedWords.size());
                }
            }
        });
    }
}
