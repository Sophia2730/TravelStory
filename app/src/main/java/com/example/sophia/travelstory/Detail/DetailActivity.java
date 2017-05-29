package com.example.sophia.travelstory.Detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sophia.travelstory.R;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    RecodeFragment recodeFragment;
    AlbumFragment albumFragment;
    Fragment fragment;
    BottomNavigationView bottomNavigation;
    ListView listView;
    RecodeAdapter recodeAdapter;
    ArrayList<RecodeItem> Recode = new ArrayList<RecodeItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        recodeFragment = new RecodeFragment();
        albumFragment = new AlbumFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, recodeFragment).commit();
//        final ArrayAdapter recodeadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Recode) ;

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

                        //listView 레이아웃 참조
                        listView = (ListView) findViewById(R.id.listView);

                        //어댑터 객체를 리스트 뷰에 설정
//                        listView.setAdapter(recodeAdapter);
                        break;
                    case R.id.navigation_album:
                        fragment = new AlbumFragment();
                        break;
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragment).commit();
                return true;
            }
        });
    }

    class RecodeAdapter extends BaseAdapter {
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



