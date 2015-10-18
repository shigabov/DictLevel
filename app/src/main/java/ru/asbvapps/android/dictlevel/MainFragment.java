package ru.asbvapps.android.dictlevel;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Артем on 11.10.2015.
 */
public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
        //rootView.findViewById(R.id.rb_eng).setOnClickListener();
        //rootView.findViewById(R.id.rb_ru).setOnClickListener();
    }
}
