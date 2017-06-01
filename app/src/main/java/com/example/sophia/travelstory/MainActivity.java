package com.example.sophia.travelstory;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.sophia.travelstory.Detail.DetailActivity;
import com.example.sophia.travelstory.Detail.DetailDBHelper;

import java.util.ArrayList;

public class MainActivity extends Activity {
    ListView listView;
    MyAdapter adapter;
    String dateFrom, dateTo, location;
    ArrayList<TravelItem> Plan = new ArrayList<TravelItem>();       //여행정보를 담아둘 ArrayList 생성
    DetailDBHelper dbHelper;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DetailDBHelper(getApplicationContext(), "TRAVEL.db", null, 1);

        if (dbHelper != null) {
            database = dbHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM TRAVEL", null);
            while (cursor.moveToNext()) {
                Plan.add(new TravelItem(R.drawable.travelstory_main_add, cursor.getString(1), cursor.getString(2) + "~" + cursor.getString(3)));
            }
        }

        adapter = new MyAdapter(this, R.layout.travel_item, Plan);
        //listView 레이아웃 참조
        listView = (ListView) findViewById(R.id.listView);

        //어댑터 객체를 리스트 뷰에 설정
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MainActivity.this);
                // 제목셋팅
                alertDialogBuilder.setTitle("여행 삭제");

                // AlertDialog 셋팅
                alertDialogBuilder
                        .setMessage("여행을 삭제하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("삭제",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        //장소를 받아옴
                                        TravelItem curItem = (TravelItem) adapter.getItem(position);
                                        String curName = curItem.getLocation();

                                        //받아온 장소를 조건으로 삼아 delete해줌
                                        Plan.remove(position);
                                        dbHelper.deleteTravel(curName);
                                        adapter.notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        // 다이얼로그를 취소한다
                                        dialog.cancel();
                                    }
                                });

                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();

                // 다이얼로그 보여주기
                alertDialog.show();
                return true;
            }
        });

        //리스트뷰에서 아이템 클릭시 이벤트 처리
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
                TravelItem curItem = (TravelItem) adapter.getItem(position);

                //getName() 메서드를 이용하여 아이템에서 이름을 가져옴
                String curLocation = curItem.getLocation();
                Intent myintent = new Intent(MainActivity.this, DetailActivity.class);
                myintent.putExtra("curLocation", curLocation);
                startActivity(myintent);
            }
        });

        ImageButton addButton = (ImageButton) findViewById(R.id.btn_traveladd);
        addButton.setOnClickListener(new Button.OnClickListener()

        {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainTravelAdd.class);
                startActivityForResult(intent, 2001);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2001 & resultCode == 201) {
            Cursor cursor = database.rawQuery("SELECT * FROM TRAVEL", null);
            cursor.moveToLast();
            Plan.add(new TravelItem(R.drawable.travelstory_main_add, cursor.getString(1), cursor.getString(2) + "~" + cursor.getString(3)));
            adapter.notifyDataSetChanged();
        }
    }

    //어댑터객체 클래스 선언(리스트뷰에 사용할 데이터를 관리하고, 각 아이템을 위한 뷰 객체를 생성)
    class MyAdapter extends BaseAdapter {
        Context mContext;//전달받은 Context 객체를 저장할 변수
        int travel_item;
        ArrayList<TravelItem> Plan;
        LayoutInflater inflater;

        //어댑터 생성자
        public MyAdapter(Context context, int travel_item, ArrayList<TravelItem> Plan) {
            mContext = context;
            this.travel_item = travel_item;
            this.Plan = Plan;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /* 어댑터를 리스트뷰에 설정하면 리스트뷰(위젯)가 자동 호출함
                - 리스트뷰가 아댑터에게 요청하는 메서드들... */

        /* 어댑터에서 관리하고 있는 데이터(아이템)의 갯수를 반환
           (itemsList의 크기(size) 반환) */
        @Override
        public int getCount() {
            return Plan.size();
        }

        //파라미터로 전달된 인덱스에 해당하는 데이터를 반환
        @Override
        public Object getItem(int position) {
            //리스트에서 아이템을 가져와 반환
            return Plan.get(position);
        }

        //현재 아이템의 Id값을 인덱스값(position)을 반환
        @Override
        public long getItemId(int position) {
            return position;
        }

        //리스트에 아이템을 추가
        public void addItem(TravelItem item) {
            Plan.add(item);
        }

        //리스트의 모든 아이템을 삭제
        public void clear() {
            Plan.clear();
        }

        @Override//화면에 보일 아이템을 위한 뷰를 만들어 반환
        public View getView(int position, View convertView, ViewGroup parent) {
            //아이템을 위한 레이아웃 생성
            TravelLayout travelLayout = null;

            // 리스트뷰에서 아이템을 재사용할 수 있도록 singerLayout을 처리

            if (convertView == null)
                travelLayout = new TravelLayout(mContext);
            else
                travelLayout = (TravelLayout) convertView;

            //아이템의 인덱스값(position)을 이용해 리스트에 들어있는 아이템을 가져옴
            TravelItem items = Plan.get(position);

            //아이템에서 이미지 리소스 id를 가져와, 레이아웃에 이미지 설정
            travelLayout.setImage(items.getResId());
            travelLayout.setLocation(items.getLocation());
            travelLayout.setPeriod(items.getPeriod());
            return travelLayout;//레이아웃을 리턴
        }
    }
}
