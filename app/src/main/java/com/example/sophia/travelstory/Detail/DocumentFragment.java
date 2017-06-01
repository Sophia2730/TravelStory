package com.example.sophia.travelstory.Detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sophia.travelstory.R;

import java.util.ArrayList;

public class DocumentFragment extends Fragment {
    ArrayList<DocumentItem> Document = new ArrayList<DocumentItem>();
    ListView listView;
    DetailActivity.DocumentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_document, container, false);

        String curLocation = getArguments().getString("curLocation");
        Toast.makeText(getActivity(), curLocation + "", Toast.LENGTH_SHORT).show();

        Document.add(new DocumentItem("Jan", "1", "치킨을먹을까 피자를먹을까"));
        Document.add(new DocumentItem("Feb", "3", "과제를할까 "));
        Document.add(new DocumentItem("Mar", "6", "왜아되는거안야ㅓㄹㄴ어린ㅇㄹ"));

        // 어댑터 객체 생성
        adapter = new DetailActivity.DocumentAdapter(getActivity(), R.layout.document_item, Document);

        //listView 레이아웃 참조
        listView = (ListView) rootView.findViewById(R.id.documentlistview);

        //어댑터 객체를 리스트 뷰에 설정
        listView.setAdapter(adapter);

        return rootView;
    }
}
