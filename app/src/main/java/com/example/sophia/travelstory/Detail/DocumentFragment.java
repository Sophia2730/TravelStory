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
import android.widget.Toast;

import com.example.sophia.travelstory.R;

import java.util.ArrayList;

public class DocumentFragment extends Fragment {
    ArrayList<DocumentItem> Document = new ArrayList<DocumentItem>();
    ListView listView;
    DetailActivity.DocumentAdapter adapter;
    int resultCode;
    DetailDBHelper dbHelper;
    SQLiteDatabase database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_document, container, false);

        final String curLocation = getArguments().getString("curLocation");
//        Toast.makeText(getActivity(), curLocation + "", Toast.LENGTH_SHORT).show();

        dbHelper = new DetailDBHelper(getContext(), "DOCUMENT.db", null, 1);
        if (dbHelper != null) {
            database = dbHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM DOCUMENT WHERE location = '" + curLocation + "';", null);
            while (cursor.moveToNext()) {
                Document.add(new DocumentItem((cursor.getString(1)).substring(0, 3), cursor.getInt(2), cursor.getString(3)));
            }
        }

        adapter = new DetailActivity.DocumentAdapter(getActivity(), R.layout.document_item, Document);
        listView = (ListView) rootView.findViewById(R.id.documentlistview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = database.rawQuery("SELECT * FROM DOCUMENT", null);
                cursor.moveToPosition(position);
                ((DetailActivity) getActivity()).onDocuItemSelected(position, cursor.getString(1), cursor.getInt(2), cursor.getString(3));
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {        //삭제시 실행되는 메소드
        super.onStart();
        Bundle bundle = getArguments();
        resultCode = bundle.getInt("resultCode");
        int position = bundle.getInt("position");
        Cursor cursor = database.rawQuery("SELECT * FROM DOCUMENT", null);
        if (resultCode == 200) {      //document add
            cursor.moveToLast();
            Document.add(new DocumentItem((cursor.getString(1)).substring(0, 3), cursor.getInt(2), cursor.getString(3)));
        } else if (resultCode == 201) {                    //document delete
            Document.remove(position);
        } else if (resultCode == 202) {                    //document update
            Toast.makeText(getContext(), "2020202020202   :   " + position, Toast.LENGTH_SHORT).show();
            cursor.moveToPosition(position);
            Document.set(position, new DocumentItem(cursor.getString(1).substring(0, 3), cursor.getInt(2), cursor.getString(3)));     //asdfasdfadfsadf
        }
        Cursor cursor1 = database.rawQuery("SELECT * FROM DOCUMENT", null);
        while (cursor1.moveToNext()) {
            Toast.makeText(getContext(), "DB : " + cursor1.getString(3), Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }
}
