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

public class DocumentFragment extends Fragment {
    ArrayList<DocumentItem> Document = new ArrayList<DocumentItem>();
    ListView listView;
    DetailActivity.DocumentAdapter adapter;
    int resultCode;
    DetailDBHelper dbHelper;
    SQLiteDatabase database;
    String curLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_document, container, false);

        curLocation = getArguments().getString("curLocation");

        dbHelper = new DetailDBHelper(getContext(), "DOCUMENT.db", null, 1);
        if (dbHelper != null) {
            database = dbHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM DOCUMENT WHERE location = '" + curLocation + "';", null);
            while (cursor.moveToNext()) {
                Document.add(new DocumentItem(cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            }
        }

        adapter = new DetailActivity.DocumentAdapter(getActivity(), R.layout.document_item, Document);
        listView = (ListView) rootView.findViewById(R.id.documentlistview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = database.rawQuery("SELECT _id FROM DOCUMENT WHERE month = '" + Document.get(position).getMonth() + "'AND date = '" + Document.get(position).getDate()
                        + "'AND content = '" + Document.get(position).getContent() + "';", null);
                cursor.moveToNext();
                int dbindex = cursor.getInt(0);

                //DetailActivity의 함수 실행
                ((DetailActivity) getActivity()).onDocuItemSelected(position, dbindex, Document.get(position).getMonth(), Document.get(position).getDate(), Document.get(position).getContent());
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {        //삭제 및 수정시 실행되는 메소드
        super.onStart();
        Bundle bundle = getArguments();
        resultCode = bundle.getInt("resultCode");
        int position = bundle.getInt("position");
        Cursor cursor = database.rawQuery("SELECT * FROM DOCUMENT WHERE location = '" + curLocation + "';", null);
        if (resultCode == 200) {                            //document add를 요청한 경우
            cursor.moveToLast();
            Document.add(new DocumentItem(cursor.getString(2), cursor.getString(3), cursor.getString(4)));
        } else if (resultCode == 201) {                     //document delete를 요청한 경우
            Document.remove(position);
        } else if (resultCode == 202) {                     //document update를 요청한 경우
            cursor.moveToPosition(position);
            Document.set(position, new DocumentItem(cursor.getString(2), cursor.getString(3), cursor.getString(4)));     //해당 position의 값을 변경한다
        }
        adapter.notifyDataSetChanged();
    }
}
