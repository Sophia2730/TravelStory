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
    ArrayList<TravelItem> Plan = new ArrayList<TravelItem>();       //여행정보를 담아둘 ArrayList 생성
    DetailDBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DetailDBHelper(getApplicationContext(), "TRAVEL.db", null, 1);

        //dbHelper가 null이 아니라면 db에서 값을 불러와 ArrayList에 추가시켜준다.
        if (dbHelper != null) {
            database = dbHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM TRAVEL", null);
            while (cursor.moveToNext()) {
                Plan.add(new TravelItem(R.drawable.travelstory_main_add, cursor.getString(1), cursor.getString(2) + "~" + cursor.getString(3)));
            }
        }

        adapter = new MyAdapter(this, R.layout.travel_item, Plan);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        //리스트뷰 아이템을 길게 누를때 삭제가능한 다이얼로그 생성
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MainActivity.this);
                alertDialogBuilder.setTitle("여행 삭제");   //제목 설정

                // AlertDialog 셋팅
                alertDialogBuilder
                        .setMessage("여행을 삭제하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("삭제",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        //삭제 버튼을 눌럿을 경우
                                        //장소를 받아옴
                                        TravelItem curItem = (TravelItem) adapter.getItem(position);
                                        String curName = curItem.getLocation();

                                        //받아온 장소를 조건으로 삼아 ArrayList에서 remove해줌
                                        Plan.remove(position);

                                        Cursor cursor = database.rawQuery("SELECT * FROM TRAVEL ", null);
                                        while (cursor.moveToNext()){
                                            database.rawQuery("DELETE FROM TRAVEL WHERE location = '" + curName +"';", null);
                                        }
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

                AlertDialog alertDialog = alertDialogBuilder.create();
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
        if (requestCode == 2001 & resultCode == 201) {      //resultCode값을 통해서 여행을 추가
            Cursor cursor = database.rawQuery("SELECT * FROM TRAVEL", null);
            cursor.moveToLast();            //마지막 cursor의 값을 ArrayList에 추가시켜 줌
            Plan.add(new TravelItem(R.drawable.travelstory_main_add, cursor.getString(1), cursor.getString(2) + "~" + cursor.getString(3)));
            adapter.notifyDataSetChanged();
        }
    }

    class MyAdapter extends BaseAdapter {
        Context mContext;
        int travel_item;
        ArrayList<TravelItem> Plan;
        LayoutInflater inflater;

        public MyAdapter(Context context, int travel_item, ArrayList<TravelItem> Plan) {
            mContext = context;
            this.travel_item = travel_item;
            this.Plan = Plan;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return Plan.size();
        }

        @Override
        public Object getItem(int position) {
            return Plan.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TravelLayout travelLayout = null;

            if (convertView == null)
                travelLayout = new TravelLayout(mContext);
            else
                travelLayout = (TravelLayout) convertView;

            TravelItem items = Plan.get(position);

            travelLayout.setImage(items.getResId());
            travelLayout.setLocation(items.getLocation());
            travelLayout.setPeriod(items.getPeriod());
            return travelLayout;
        }
    }
}
