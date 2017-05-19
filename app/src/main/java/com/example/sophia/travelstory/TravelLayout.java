package com.example.sophia.travelstory;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/* 아이템을 위한 XML 레이아웃은 LinearLayout과 같은
   레이아웃 클래스를 상속하는 클래스를 만들어 설정함 */
public class TravelLayout extends LinearLayout {
	Context mContext;
    LayoutInflater inflater;

    ImageView imgView;
	TextView txt_location;
	TextView txt_period;


	public TravelLayout(Context context) {
		super(context);

		mContext = context;
		init();
	}

	public TravelLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mContext = context;
		init();
	}

	private void init() {
        // 아이템의 화면을 구성한 XML 레이아웃(singer_item.xml)을 인플레이션
		inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.travel_item, this, true);

		//부분화면 레이아웃에 정의된 객체 참조
		imgView = (ImageView) findViewById(R.id.img_add);
		txt_location = (TextView) findViewById(R.id.txt_location);
		txt_period = (TextView) findViewById(R.id.txt_period);
    }

    public void setImage(int resId) {//이미지 리소스 id 설정
        imgView.setImageResource(resId);
    }

	public void setLocation(String location) {//이름 설정
		txt_location.setText(location);
	}
	
	public void setPeriod(String period) {//소속 설정
		txt_period.setText(period);
	}

}
