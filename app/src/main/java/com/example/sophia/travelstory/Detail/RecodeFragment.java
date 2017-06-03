package com.example.sophia.travelstory.Detail;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recode, container, false);

        String curLocation = getArguments().getString("curLocation");
        Toast.makeText(getActivity(), curLocation + "", Toast.LENGTH_SHORT).show();


        Recode.add(new RecodeItem(R.drawable.ic_recode, "미국", "20170302", "00:19"));
        Recode.add(new RecodeItem(R.drawable.ic_recode, "캐나다", "170503", "00:52"));

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
//                position을 이용해 어댑터에서 아이템을 가져옴
//                SingerItem curItem = (SingerItem) adapter.getItem(position);
//
//                //getName() 메서드를 이용하여 아이템에서 이름을 가져옴
//                String curName = curItem.getName();

//                ((DetailActivity) getActivity()).onItemSelected(Recode.get(position).resId, Recode.get(position).name,
//                        Recode.get(position).date, Recode.get(position).time);
            }
        });//end of setOnItemClickListener

        return rootView;
    }


}
