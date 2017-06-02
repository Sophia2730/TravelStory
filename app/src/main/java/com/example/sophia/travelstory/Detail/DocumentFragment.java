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

        String curLocation = getArguments().getString("curLocation");
        Toast.makeText(getActivity(), curLocation + "", Toast.LENGTH_SHORT).show();

        dbHelper = new DetailDBHelper(getContext(), "DOCUMENT.db", null, 1);
        if (dbHelper != null) {
            database = dbHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM DOCUMENT", null);
            while (cursor.moveToNext()) {
                Document.add(new DocumentItem(cursor.getString(1), cursor.getInt(2), cursor.getString(3)));
            }
        }

        adapter = new DetailActivity.DocumentAdapter(getActivity(), R.layout.document_item, Document);
        listView = (ListView) rootView.findViewById(R.id.documentlistview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* params
                  - parent : 클릭한 아이템을 포함하는 부모 뷰(ListView)
                  - view : 클릭한 항목의 View
                  - position : 클릭한 아이템의 Adepter에서의 위치값(0, 1, 2,...)
                  - id : DB를 사용했을 때 Cursor의 id 값값
               */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position을 이용해 어댑터에서 아이템을 가져옴
                DocumentItem curItem = (DocumentItem) adapter.getItem(position);
                String curName = curItem.getMonth();
                Toast.makeText(getActivity(), "" + curName, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        Toast.makeText(getActivity(), bundle.getString("curLocation") + "", Toast.LENGTH_SHORT).show();
        if (bundle.getInt("resultCode") != 0){
            Cursor cursor = database.rawQuery("SELECT * FROM DOCUMENT", null);
            cursor.moveToLast();
            Document.add(new DocumentItem(cursor.getString(1), cursor.getInt(2), cursor.getString(3)));
            adapter.notifyDataSetChanged();

            resultCode = bundle.getInt("resultCode");
            Toast.makeText(getActivity(), "resultCode = " + resultCode, Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getActivity(), "resultCode = 0", Toast.LENGTH_SHORT).show();
    }
}
