package com.example.sophia.travelstory.Detail;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sophia.travelstory.R;

import java.util.ArrayList;

public class DetailActivity extends ActionBarActivity {

    RecodeFragment recodeFragment;
    DocumentFragment documentFragment;
    Fragment fragment;
    BottomNavigationView bottomNavigation;
    ListView listView;
    RecodeAdapter recodeAdapter;
    ArrayList<RecodeItem> Recode = new ArrayList<RecodeItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        View customView = getLayoutInflater().inflate(R.layout.custom_title, null);
        getSupportActionBar().setCustomView(customView);


        recodeFragment = new RecodeFragment();
        documentFragment = new DocumentFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, recodeFragment).commit();

        ImageButton btn_location = (ImageButton) findViewById(R.id.btn_location);
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(DetailActivity.this, CurrentLocation.class);
                startActivity(myintent);
            }
        });

        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.navigation_recode:
                        fragment = new RecodeFragment();
                        Recode.add(new RecodeItem(R.drawable.travelstory_main_addimg, "홍콩", "170310~170311", "playtime"));
                        recodeAdapter = new RecodeAdapter(getApplicationContext(), R.layout.recode_item, Recode);

                        break;
                    case R.id.navigation_document:
                        fragment = new DocumentFragment();
                        break;
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragment).commit();
                return true;
            }
        });
    }

    public void onItemSelected(int img, String name, String company, String song) { //아이템 선택시 실행되는 함수

        //임시실행페이지
        DocumentFragment cur = new DocumentFragment();
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {    //세로화면 에서 눌럿을 경우
            getSupportFragmentManager().beginTransaction().replace(R.id.container, cur).commit();    //mainFrame에 상세정보 화면을 띄워줌
        }
    }

    static class RecodeAdapter extends BaseAdapter {
        Context mContext;
        int recode_item;
        ArrayList<RecodeItem> Recode;
        LayoutInflater inflater;

        ImageView imageView;
        TextView nameTextView;
        TextView dateTextView;
        TextView timeTextView;

        public RecodeAdapter(Context context, int recode_item, ArrayList<RecodeItem> Recode) {
            mContext = context;
            this.recode_item = recode_item;
            this.Recode = Recode;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return Recode.size();
        }

        @Override
        public Object getItem(int position) {
            return Recode.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //아이템을 위한 레이아웃 생성
            RecodeLayout recodeLayout = null;

            // 리스트뷰에서 아이템을 재사용할 수 있도록 singerLayout을 처리

            if (convertView == null)
                recodeLayout = new RecodeLayout(mContext);
            else
                recodeLayout = (RecodeLayout) convertView;

            //아이템의 인덱스값(position)을 이용해 리스트에 들어있는 아이템을 가져옴
            RecodeItem items = Recode.get(position);

            //아이템에서 이미지 리소스 id를 가져와, 레이아웃에 이미지 설정
            recodeLayout.setImage(items.getResId());
            recodeLayout.setName(items.getName());
            recodeLayout.setDate(items.getDate());
            recodeLayout.setTime(items.getTime());
            return recodeLayout;//레이아웃을 리턴
        }
    }

}



