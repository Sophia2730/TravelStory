package com.example.sophia.travelstory.Detail;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sophia.travelstory.R;

/* 아이템을 위한 XML 레이아웃은 LinearLayout과 같은
   레이아웃 클래스를 상속하는 클래스를 만들어 설정함 */
public class DocumentLayout extends LinearLayout {
	Context mContext;
	LayoutInflater inflater;

	TextView monthTextView;
	TextView dateTextView;
	TextView contentTextView;

	//생성자-1
	public DocumentLayout(Context context) {
		super(context);
		mContext = context;

		//객체가 생성될 때 초기화
		init();
	}

	//생성자-2
	public DocumentLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		//객체가 생성될 때 초기화
		init();
	}

	//초기화 메서드
	private void init() {
		// 아이템의 화면을 구성한 XML 레이아웃(singer_item.xml)을 인플레이션
		inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.document_item, this, true);

		//부분화면 레이아웃에 정의된 객체 참조
		monthTextView = (TextView) findViewById(R.id.monthTextView);
		dateTextView = (TextView) findViewById(R.id.dateTextView);
		contentTextView = (TextView) findViewById(R.id.contentTextView);
	}//end of init()

	public void setMonth(String month) {//노래 설정
		monthTextView.setText(month);
	}

	public void setDate(int date) {//소속 설정
		dateTextView.setText(date + "");
	}

	public void setContent(String content) {//노래 설정
		contentTextView.setText(content);
	}
}
