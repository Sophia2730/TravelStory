package com.example.sophia.travelstory.Detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sophia.travelstory.R;

import java.util.ArrayList;

public class RecodeFragment extends Fragment {
    ArrayList<RecodeItem> Recode = new ArrayList<RecodeItem>();
    ListView listview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recode, container, false);


        return rootView;
    }


}
