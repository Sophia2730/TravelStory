package com.example.sophia.travelstory;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
		inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.travel_item, this, true);

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
