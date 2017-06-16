package com.example.sophia.travelstory.Detail;

import android.content.Context;
import android.content.Intent;
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

import com.example.sophia.travelstory.R;

import java.util.ArrayList;

//MainActivity에서 리스트뷰 눌럿을 때 뜨는 화면
public class DetailActivity extends ActionBarActivity {

    RecodeFragment recodeFragment;
    DocumentFragment documentFragment;
    Fragment fragment;
    BottomNavigationView bottomNavigation;
    String curLocation;             //현재 위치
    Bundle bundle;                  //값을 넘겨주는 Bundle 객체
    DocumentDetail documentDetail;
    static int currentView = 1;     //현재 보여지는 뷰 확인
    static int position;            //수정되는 listview 위치

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentView = 1;
    }

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

        curLocation = getIntent().getStringExtra("curLocation");
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
                if (currentView == 1) {     //Recode 화면이 띄워져 있으면
                    Intent intent = new Intent(DetailActivity.this, RecodeAdd.class);
                    intent.putExtra("curLocation", curLocation);
                    startActivityForResult(intent, 1001);
                } else {                    //Document화면이 띄워져 있으면
                    Intent intent = new Intent(DetailActivity.this, DocumentAdd.class);
                    intent.putExtra("curLocation", curLocation);
                    startActivityForResult(intent, 1001);
                }
            }
        });

        //지도 아이콘 눌럿을 경우
        ImageButton btn_location = (ImageButton) findViewById(R.id.btn_location);
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, CurrentLocation.class);
                bundle.putInt("resultCode", 99);
                startActivity(intent);
            }
        });

        //하단 네비게이션 눌럿을 경우
        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.navigation_recode:        //recode 탭을 눌럿을 경우
                        fragment = new RecodeFragment();
                        bundle.putInt("resultCode", 199);
                        currentView = 1;
                        break;
                    case R.id.navigation_document:      //document 탭을 눌럿을 경우
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {     //Add, Detail 자바에서 받아오는 값 따라 처리
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 & resultCode == 100) {              //recode Add에서 넘어왓을 경우
            bundle.putInt("resultCode", resultCode);
        } else if (requestCode == 1001 & resultCode == 101) {       //recode Delete 넘어왓을 경우
            bundle.putInt("resultCode", resultCode);
            bundle.putInt("position", position);
        }  else if (requestCode == 1001 & resultCode == 200) {      //Document Add에서 넘어왓을 경우
            bundle.putInt("resultCode", resultCode);
        } else if (requestCode == 1001 & resultCode == 201) {       //Document Delete에서 넘어왔을 경우
            bundle.putInt("resultCode", resultCode);
            bundle.putInt("position", position);
        } else if (requestCode == 1001 & resultCode == 202) {       //Document Update를 실행할 경우
            bundle.putInt("resultCode", resultCode);
            bundle.putInt("position", position);
        } else if (requestCode == 1001 & resultCode == 0) {         //backpress로 넘어왓을 경우
            bundle.putInt("resultCode", resultCode);
        }
    }

    //DocumentFragment에서 리스트뷰 아이템이 선택되었을 경우
    public void onDocuItemSelected(int position, int dbindex, String month, String date, String content) {
        //position을 이용해 어댑터에서 아이템을 가져옴
        String cur[] = {curLocation, month, date, content};
        Intent intent = new Intent(DetailActivity.this, DocumentDetail.class);
        intent.putExtra("selectItem", cur);
        intent.putExtra("dbindex", dbindex);
        this.position = position;
        startActivityForResult(intent, 1001);
    }

    //RecodeFragment에서 리스트뷰 아이템이 선택되었을 경우
    public void onRecodeItemSelected(int position, String name, String time) {
        String cur[] = {curLocation, name, time};
        Intent intent = new Intent(DetailActivity.this, RecodeDelete.class);
        intent.putExtra("selectItem", cur);
        this.position = position;
        startActivityForResult(intent, 1001);
    }

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
            RecodeLayout recodeLayout = null;

            if (convertView == null)
                recodeLayout = new RecodeLayout(mContext);
            else
                recodeLayout = (RecodeLayout) convertView;

            RecodeItem items = Recode.get(position);

            recodeLayout.setImage(items.getResId());
            recodeLayout.setName(items.getName());
            recodeLayout.setTime(items.getTime());
            return recodeLayout;
        }
    }

    static class DocumentAdapter extends BaseAdapter {
        Context mContext;
        int document_item;
        ArrayList<DocumentItem> Document;
        LayoutInflater inflater;

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
            DocumentLayout documentLayout = null;
            if (convertView == null)
                documentLayout = new DocumentLayout(mContext);
            else
                documentLayout = (DocumentLayout) convertView;

            DocumentItem items = Document.get(position);

            documentLayout.setMonth(items.getMonth());
            documentLayout.setDate(items.getDate());
            documentLayout.setContent(items.getContent());
            return documentLayout;
        }
    }
}
