package com.example.sophia.travelstory.Detail;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sophia.travelstory.R;

public class DocumentLayout extends LinearLayout {
	Context mContext;
	LayoutInflater inflater;

	TextView monthTextView;
	TextView dateTextView;
	TextView contentTextView;

	public DocumentLayout(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public DocumentLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	private void init() {
		inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.document_item, this, true);
		monthTextView = (TextView) findViewById(R.id.monthTextView);
		dateTextView = (TextView) findViewById(R.id.dateTextView);
		contentTextView = (TextView) findViewById(R.id.contentTextView);
	}

	public void setMonth(String month) {//노래 설정
		monthTextView.setText(month);
	}

	public void setDate(String date) {
		dateTextView.setText(date);
	}

	public void setContent(String content) {//노래 설정
		contentTextView.setText(content);
	}
}
