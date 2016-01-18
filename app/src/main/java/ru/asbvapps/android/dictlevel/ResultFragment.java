package ru.asbvapps.android.dictlevel;

import android.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Артем on 01.11.2015.
 */
public class ResultFragment extends Fragment {
    private String result;

    private ShareActionProvider shareActionProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra(TestingFragment.RESULT)) {
            result = intent.getStringExtra(TestingFragment.RESULT);
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result_fragment, container, false);

        ((TextView) rootView.findViewById(R.id.result)).setText(result);
        ((Button) rootView.findViewById(R.id.retry)).setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent1 = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent1);
                }
        });

        return rootView;
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.result_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text)+" "+result);
        shareActionProvider.setShareIntent(shareIntent);
    }
}
