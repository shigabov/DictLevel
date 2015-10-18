package ru.asbvapps.android.dictlevel;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import ru.asbvapps.android.dictlevel.data.DictContract;
import ru.asbvapps.android.dictlevel.data.DictLevelAdapter;

/**
 * Created by Артем on 18.10.2015.
 */
public class TestingFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int COL_NAME = 2;
    private final int WORDS_LOADER = 1;

    DictLevelAdapter mAdapter;


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {


        Uri wordsUri = DictContract.WordsEntry.CONTENT_URI;

        return new CursorLoader(getActivity(), wordsUri, null, null, null, null);


    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override

    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(WORDS_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.testing_fragment, container, false);
        mAdapter = new DictLevelAdapter(getActivity(),null,0);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(mAdapter);
        return rootView;

    }
}
