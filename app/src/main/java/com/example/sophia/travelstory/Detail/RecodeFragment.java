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
//        Toast.makeText(getActivity(), curLocation + "", Toast.LENGTH_SHORT).show();

        dbHelper = new DetailDBHelper(getContext(), "RECODE.db", null, 1);
        if (dbHelper != null) {
            database = dbHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM RECODE WHERE location = '" + curLocation + "';", null);
            while (cursor.moveToNext()) {
                Recode.add(new RecodeItem(R.drawable.ic_recode, cursor.getString(2), cursor.getString(3)));
            }
        }
        Recode.add(new RecodeItem(R.drawable.ic_recode, "미국", "00:19"));

        // 어댑터 객체 생성
        adapter = new DetailActivity.RecodeAdapter(getActivity(), R.layout.recode_item, Recode);

        //listView 레이아웃 참조
        listView = (ListView) rootView.findViewById(R.id.recodelistview);

        //어댑터 객체를 리스트 뷰에 설정
        listView.setAdapter(adapter);

        //리스트뷰에서 아이템 클릭시 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });//end of setOnItemClickListener

        return rootView;
    }

    @Override
    public void onStart() {        //삭제시 실행되는 메소드
        super.onStart();
        Bundle bundle = getArguments();
        int resultCode = bundle.getInt("resultCode");

        if (resultCode == 100 ) {      //detailadd
            Cursor cursor = database.rawQuery("SELECT * FROM RECODE", null);
            cursor.moveToLast();
            Recode.add(new RecodeItem(R.drawable.ic_recode, cursor.getString(2), cursor.getString(3)));
        } else if (resultCode == 101) {                    //detaildelete
            Toast.makeText(getContext(), "" + bundle.getInt("position"), Toast.LENGTH_SHORT).show();
            Recode.remove(bundle.getInt("position"));
        }
        adapter.notifyDataSetChanged();
    }
}
