package ru.asbvapps.android.dictlevel;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ShigabovAV on 10.03.2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Cursor mDataset;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит только из одного TextView
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv_lang);
        }
    }
        // Конструктор
        public RecyclerAdapter(Cursor dataset) {
            mDataset = dataset;
        }

        // Создает новые views (вызывается layout manager-ом)
        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.language_text_view, parent, false);

            // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Заменяет контент отдельного view (вызывается layout manager-ом)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            mDataset.moveToPosition(position);
            holder.mTextView.setText(mDataset.getString(1));
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainFragment.mLang = (String) v.getTag();
                    MainFragment.mTvDesc.setVisibility(View.VISIBLE);
                    MainFragment.mBtnStart.setVisibility(View.VISIBLE);
                }
            });

        }

        // Возвращает размер данных (вызывается layout manager-ом)
        @Override
        public int getItemCount() {
            return mDataset.getCount();
        }

    public void swapCursor(Cursor cursor){
        mDataset = cursor;
    }

}
