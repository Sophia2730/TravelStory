package com.example.sophia.travelstory.Detail;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sophia.travelstory.R;

public class RecodeLayout extends LinearLayout {
	Context mContext;
	LayoutInflater inflater;

	ImageView imageView;
	TextView nameTextView;
	TextView timeTextView;

	public RecodeLayout(Context context) {
		super(context);
		mContext = context;
		init();
	}

	//생성자-2
	public RecodeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	//초기화 메서드
	private void init() {
		inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.recode_item, this, true);

		imageView = (ImageView) findViewById(R.id.imageView);
		nameTextView = (TextView) findViewById(R.id.nameTextView);
		timeTextView = (TextView) findViewById(R.id.timeTextView);
	}//end of init()

	public void setImage(int resId) {//이미지 리소스 id 설정
		imageView.setImageResource(resId);
	}

	public void setName(String name) {//이름 설정
		nameTextView.setText(name);
	}


	public void setTime(String time) {//노래 설정
		timeTextView.setText(time);
	}
}
