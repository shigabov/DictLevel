package ru.asbvapps.android.dictlevel;

import android.app.Application;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.zip.Inflater;

import ru.asbvapps.android.dictlevel.data.DictContract;

/**
 * Created by Артем on 11.10.2015.
 */
public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String LANG = "language";
    private String mLang="";
    private TextView mTvDesc;
    private Button mBtnStart;
    private static final String LOG_TAG = MainFragment.class.getSimpleName();
    LanguagesAdapter mAdapter;

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        Uri langUri = DictContract.LangEntry.CONTENT_URI;
        return new CursorLoader(getActivity(), langUri, null, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(LOG_TAG, "onLoadFinished count=" + data.getCount());

        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        mTvDesc.setVisibility(View.GONE);
        mBtnStart.setVisibility(View.GONE);
        getLoaderManager().initLoader(0, null, this);
        super.onActivityCreated(savedInstanceState);

    }

    private void startTestingActivity (){
        Intent intent = new Intent(getActivity(), TestingActivity.class);
        intent.putExtra(LANG, mLang);
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAdapter = new LanguagesAdapter(getActivity(), null, 0);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_lang);

        mTvDesc = (TextView) rootView.findViewById(R.id.tv_desc);
        mBtnStart = (Button) rootView.findViewById(R.id.btn_start);
        ProgressBar progressBar = new ProgressBar(getActivity());
        /*progressBar.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));*/
        progressBar.setIndeterminate(true);

        lv.setAdapter(mAdapter);
        lv.setEmptyView(progressBar);

        rootView.findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTestingActivity();
            }
        });

        return rootView;
    }

    class LanguagesAdapter extends CursorAdapter {

        public LanguagesAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            Log.d(LOG_TAG, "newView");
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.language_text_view, null);
        }

        @Override
        public void bindView(final View view, Context context, Cursor cursor) {
            Log.d(LOG_TAG, "bindView");

            TextView v = (TextView) view.findViewById(R.id.tv_lang);
            ImageView iv = (ImageView) view.findViewById(R.id.img_lang);


            v.setText(cursor.getString(1));
            v.setTag(cursor.getString(0));

            Log.d(LOG_TAG, "0=" + cursor.getString(0));

            int imgId = getResources().getIdentifier(cursor.getString(0), "drawable", getActivity().getPackageName());
            Log.d(LOG_TAG, "imgId=" + imgId);

            if (imgId != 0){
                iv.setImageDrawable(getResources().getDrawable(imgId));
            }

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLang = (String) v.getTag();
                    mTvDesc.setVisibility(View.VISIBLE);
                    mBtnStart.setVisibility(View.VISIBLE);
                }
            });

        }
    }
}
