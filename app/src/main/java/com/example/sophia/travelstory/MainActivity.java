package com.example.sophia.travelstory;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
        ListView listView;
        TextView itemView;
        MyAdapter adapter;

        //가수 앨범을 담을 리스트(SingerItem 객체를 담아둘 ArrayList 생성)
        ArrayList<TravelItem> Plan = new ArrayList<TravelItem>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //앨범(album) ArrayList에 데이터를 담는다.
            Plan.add(new TravelItem(R.drawable.travelstory_main_add, "정은지", "에이핑크"));
            Plan.add(new TravelItem(R.drawable.travelstory_main_add, "트와이스", "JYP엔터테인먼트"));
            Plan.add(new TravelItem(R.drawable.travelstory_main_add, "하이라이트", "어라운드어스"));
            Plan.add(new TravelItem(R.drawable.travelstory_main_add, "워너", "YG엔터테인먼트"));
            Plan.add(new TravelItem(R.drawable.travelstory_main_add, "걸스데이", "드림티엔터테인먼트"));

            /* 어댑터 객체 생성
                - MyAdapter params
                  . this
                  . singer_item layout
                  . data(ArrayList)
             */
            
			adapter = new MyAdapter(this, R.layout.travel_item, Plan);

            //listView 레이아웃 참조
            listView = (ListView) findViewById(R.id.listView);

            //어댑터 객체를 리스트 뷰에 설정
            listView.setAdapter(adapter);

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
                    String curName = curItem.getLocation();

                    Toast.makeText(getApplicationContext(),
                                   "안녕하세요. " + curName + "입니다.",
                                   Toast.LENGTH_LONG).show();
                }
            });
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
			//리스트의 크기
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
