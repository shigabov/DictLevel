package ru.asbvapps.android.dictlevel;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import java.util.ArrayList;

import ru.asbvapps.android.dictlevel.data.DictContract;
import ru.asbvapps.android.dictlevel.data.DictLevelAdapter;

/**
 * Created by Артем on 18.10.2015.
 */
public class TestingFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = TestingFragment.class.getSimpleName();
    public String lang;
    public static final int COL_NAME = 1;
    public static final String RESULT = "result";
    public static final int COL_ID = 0;

    private final int WORDS_LOADER = 1;

    public static ArrayList<String> mSelectedWords = new ArrayList<>();

    DictLevelAdapter mAdapter;


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        Uri wordsUri = DictContract.WordsEntry.buildWordsUIUri(lang);
        return new CursorLoader(getActivity(), wordsUri, null, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(LOG_TAG, "onLoadFinished count=" + data.getCount());

        for (int i=0;i<data.getCount()-1;i++){
            data.moveToPosition(i);
            mAdapter.mWords.add(new Word(data.getInt(COL_ID),data.getString(COL_NAME)));
        }
        Log.d(LOG_TAG, "mAdapter.mWords.size=" + mAdapter.mWords.size());
        //Log.d(LOG_TAG, "mAdapter.mWords.size=" + mAdapter.mSelectedWords.size());

        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override

    public void onActivityCreated(Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        lang = intent.getStringExtra(MainFragment.LANG);
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
        Button btn_calc = (Button) rootView.findViewById(R.id.btn_calc);
        btn_calc.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = calcWords(mAdapter.mSelectedWords, mAdapter.mWords);

                Intent intent = new Intent(getActivity(),ResultActivity.class);
                intent.putExtra(RESULT, result);
                startActivity(intent);

                //Toast.makeText(getActivity(), "result=" + , Toast.LENGTH_LONG).show();
            }
        });
        return rootView;

    }

    public String calcWords(ArrayList<Word> selectedWords, ArrayList<Word> allWords){

        int TotalWords = 5000;
        int TestWords = 150;
        double inGroup = 5000/150;
        double Result = TotalWords;


        ArrayList<Word> diffWords = new ArrayList<>();

        for (Word word : allWords) {
            if (!selectedWords.contains(word)){
                diffWords.add(word);
            }
        }

        for (Word word : diffWords) {
            Result = Result - inGroup;
        }
        //Format df = new DecimalFormat("#####");
        //df.setRoundingMode(RoundingMode.CEILING);

        Log.d(LOG_TAG, "diffWords=" + diffWords.size());
        return ""+(int) Math.round (Result/100)*100;
    }


}
