package ru.asbvapps.android.dictlevel;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

/**
 * Created by Артем on 11.10.2015.
 */
public class MainFragment extends Fragment {

    public static final String LANG = "language";
    public static final String LANG_ENGLISH = "english";
    public static final String LANG_RUSSIAN = "russian";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        rootView.findViewById(R.id.rb_eng).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTestingActivity(R.id.rb_eng);
            }
        });

        rootView.findViewById(R.id.rb_ru).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTestingActivity(R.id.rb_ru);
            }
        });

        return rootView;
    }

    private void startTestingActivity (int radioBtn){
        Intent intent = new Intent(getActivity(),TestingActivity.class);

        switch (radioBtn){
            case R.id.rb_eng: intent.putExtra(LANG,LANG_ENGLISH);
            case R.id.rb_ru: intent.putExtra(LANG,LANG_RUSSIAN);

        }
        startActivity(intent);
    }
}
