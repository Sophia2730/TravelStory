package com.example.sophia.travelstory.Detail;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TextView;

import com.example.sophia.travelstory.R;

import java.util.ArrayList;

public class DetailActivity extends ActionBarActivity {

    RecodeFragment recodeFragment;
    DocumentFragment documentFragment;
    Fragment fragment;
    BottomNavigationView bottomNavigation;
    String curLocation;
    Bundle bundle;
    DetailDBHelper dbHelper;
    SQLiteDatabase database;
    ArrayList<DocumentItem> Document = new ArrayList<DocumentItem>();
    DocumentDetail documentDetail;
    static int currentView = 1;    //현재 보여지는 뷰 확인
    static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //타이틀바 설정
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        View customView = getLayoutInflater().inflate(R.layout.custom_title, null);
        getSupportActionBar().setCustomView(customView);


        Intent getintent = getIntent();
        curLocation = getintent.getStringExtra("curLocation");
        bundle = new Bundle();
        bundle.putString("curLocation", curLocation);

        recodeFragment = new RecodeFragment();
        documentFragment = new DocumentFragment();
        documentDetail = new DocumentDetail();
        recodeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, recodeFragment).commit();

        //Tab에서 추가버튼을 누를 때
        ImageButton btn_add = (ImageButton) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentView == 1) {   //Recode 화면이 띄워져 있으면
                    Intent intent = new Intent(DetailActivity.this, RecodeAdd.class);
                    intent.putExtra("curLocation", curLocation);
                    startActivityForResult(intent, 1001);
                } else {        //Document화면이 띄워져 있으면
                    Intent intent = new Intent(DetailActivity.this, DocumentAdd.class);
                    intent.putExtra("curLocation", curLocation);
                    startActivityForResult(intent, 1001);
                }
            }
        });

        ImageButton btn_location = (ImageButton) findViewById(R.id.btn_location);
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, CurrentLocation.class);
                startActivity(intent);
            }
        });

        //아래 탭 눌럿을 경우
        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.navigation_recode:
                        fragment = new RecodeFragment();
                        currentView = 1;
                        break;
                    case R.id.navigation_document:
                        fragment = new DocumentFragment();
                        currentView = 2;
                        bundle.putInt("resultCode", 299);
                        break;
                }
                fragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragment).commit();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 & resultCode == 100) { //recodeAdd에서 넘어왓을 경우

        } else if (requestCode == 1001 & resultCode == 200) {    //DocumentAdd에서 넘어왓을 경우
            bundle.putInt("resultCode", resultCode);
        } else if (requestCode == 1001 & resultCode == 201) {      //DocumentDetail에서 넘어왔을 경우
            bundle.putInt("resultCode", resultCode);
            bundle.putInt("position", position);
        }
    }

    public void onDocuItemSelected(int position, String month, int date, String content) {
        //position을 이용해 어댑터에서 아이템을 가져옴
        String cur[] = {curLocation, month, Integer.toString(date), content};
        Intent intent = new Intent(DetailActivity.this, DocumentDetail.class);
        intent.putExtra("selectItem", cur);
        this.position = position;
        startActivityForResult(intent, 1001);
    }

//    public void onItemSelected(int img, String name, String company, String song) { //아이템 선택시 실행되는 함수
//        //임시실행페이지
//        DocumentFragment cur = new DocumentFragment();
//        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {    //세로화면 에서 눌럿을 경우
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, cur).commit();    //mainFrame에 상세정보 화면을 띄워줌
//        }
//    }

    static class RecodeAdapter extends BaseAdapter {
        Context mContext;
        int recode_item;
        ArrayList<RecodeItem> Recode;
        LayoutInflater inflater;

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

    static class DocumentAdapter extends BaseAdapter {
        Context mContext;
        int document_item;
        ArrayList<DocumentItem> Document;
        LayoutInflater inflater;

        TextView nameTextView;
        TextView dateTextView;
        TextView timeTextView;

        public DocumentAdapter(Context context, int document_item, ArrayList<DocumentItem> Document) {
            mContext = context;
            this.document_item = document_item;
            this.Document = Document;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return Document.size();
        }

        @Override
        public Object getItem(int position) {
            return Document.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //아이템을 위한 레이아웃 생성
            DocumentLayout documentLayout = null;

            // 리스트뷰에서 아이템을 재사용할 수 있도록 singerLayout을 처리

            if (convertView == null)
                documentLayout = new DocumentLayout(mContext);
            else
                documentLayout = (DocumentLayout) convertView;

            //아이템의 인덱스값(position)을 이용해 리스트에 들어있는 아이템을 가져옴
            DocumentItem items = Document.get(position);

            documentLayout.setMonth(items.getMonth());
            documentLayout.setDate(items.getDate());
            documentLayout.setContent(items.getContent());
            return documentLayout;//레이아웃을 리턴
        }
    }
}



