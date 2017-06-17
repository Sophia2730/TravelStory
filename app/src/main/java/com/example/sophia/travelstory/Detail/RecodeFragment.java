package com.example.sophia.travelstory.Detail;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sophia.travelstory.R;

import java.util.ArrayList;

public class RecodeFragment extends Fragment {
    ArrayList<RecodeItem> Recode = new ArrayList<RecodeItem>();
    ListView listView;
    DetailActivity.RecodeAdapter adapter;
    DetailDBHelper dbHelper;
    SQLiteDatabase database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recode, container, false);

        final String curLocation = getArguments().getString("curLocation");

        dbHelper = new DetailDBHelper(getContext(), "RECODE.db", null, 1);
        //초기에 DB에 있는 값들 불러와서 listView에 추가
        if (dbHelper != null) {
            database = dbHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM RECODE WHERE location = '" + curLocation + "';", null);
            while (cursor.moveToNext()) {
                Recode.add(new RecodeItem(R.drawable.ic_recode, cursor.getString(3), cursor.getString(4)));
            }
        }
        adapter = new DetailActivity.RecodeAdapter(getActivity(), R.layout.recode_item, Recode);
        listView = (ListView) rootView.findViewById(R.id.recodelistview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((DetailActivity) getActivity()).onRecodeItemSelected(position, Recode.get(position).name,
                        Recode.get(position).time);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {        //수정 및 삭제시 실행되는 메소드
        super.onStart();
        Bundle bundle = getArguments();
        int resultCode = bundle.getInt("resultCode");

        if (resultCode == 100) {                           //recode Add 요청시
            Cursor cursor = database.rawQuery("SELECT * FROM RECODE", null);
            cursor.moveToLast();
            Recode.add(new RecodeItem(R.drawable.ic_recode, cursor.getString(3), cursor.getString(4)));
        } else if (resultCode == 101) {                     //recode Delete 요청시
            Recode.remove(bundle.getInt("position"));
        }
        adapter.notifyDataSetChanged();
    }
}
