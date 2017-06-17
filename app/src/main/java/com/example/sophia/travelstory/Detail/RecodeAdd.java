package com.example.sophia.travelstory.Detail;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.sophia.travelstory.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecodeAdd extends Activity implements View.OnClickListener {
    private static final int REC_STOP = 0;
    private static final int RECORDING = 1;

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private int mRecState = REC_STOP;
    private SeekBar mRecProgressBar;
    private Button mBtnStartRec;
    private String mFilePath, mFileName = null;
    DetailDBHelper dbHelper;
    private int mCurRecTimeMs = 0;
    private int mCurProgressTimeDisplay = 0;
    TextView tvRecStartPoint;

    // 녹음시 SeekBar처리
    Handler mProgressHandler = new Handler() {
        public void handleMessage(Message msg) {
            mCurRecTimeMs = mCurRecTimeMs + 100;
            mCurProgressTimeDisplay = mCurProgressTimeDisplay + 100;

            if (mCurRecTimeMs < 0) {
            } else if (mCurRecTimeMs < 59000) {
                mRecProgressBar.setProgress(mCurProgressTimeDisplay);
                mProgressHandler.sendEmptyMessageDelayed(0, 100);
            } else if (mCurRecTimeMs > 59000) {
                stopRec();
            } else {
                mBtnStartRecOnClick();
            }
            tvRecStartPoint.setText(mCurRecTimeMs / 1000 + " sec");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recode_add);

        tvRecStartPoint = (TextView) findViewById(R.id.tvRecStartPoint);
        dbHelper = new DetailDBHelper(getApplicationContext(), "RECODE.db", null, 1);

        //파일이 저장되는 위치 지정
        mFilePath = "/sdcard/Download/";

        //파일 이름 지정
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");

        mFileName = timeStampFormat.format(new Date()).toString()
                + ".mp4";

        mBtnStartRec = (Button) findViewById(R.id.btnStartRec);
        mRecProgressBar = (SeekBar) findViewById(R.id.recProgressBar);

        mBtnStartRec.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartRec:
                mBtnStartRecOnClick();
                break;
            default:
                break;
        }
    }

    private void mBtnStartRecOnClick() {
        if (mRecState == REC_STOP) {        //녹음중이지 않으면 녹음 시작
            mRecState = RECORDING;
            startRec();
            updateUI();
        } else if (mRecState == RECORDING) {    //녹음중이면 녹음을 중지
            mRecState = REC_STOP;
            stopRec();
        }
    }

    // 녹음시작
    private void startRec() {
        mCurRecTimeMs = 0;
        mCurProgressTimeDisplay = 0;
        mProgressHandler.sendEmptyMessageDelayed(0, 100);

        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.reset();
        } else {
            mRecorder.reset();
        }
        try {
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mRecorder.setOutputFile(mFilePath + mFileName);
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 녹음정지
    private void stopRec() {
        Intent intent = getIntent();
        try {
            mRecorder.stop();
        } catch (Exception e) {

        } finally {
            mRecorder.release();
            mRecorder = null;
        }
        mCurRecTimeMs = -999;
        // SeekBar의 상태를 즉시 체크
        mProgressHandler.sendEmptyMessageDelayed(0, 0);

        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFilePath + mFileName);
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        int point = mPlayer.getDuration();
//
//        int maxMinPoint = point / 1000 / 60;
//        int maxSecPoint = (point / 1000) % 60;
//        String maxMinPointStr = "";
//        String maxSecPointStr = "";
//
//        if (maxMinPoint < 10)
//            maxMinPointStr = "0" + maxMinPoint + ":";
//        else
//            maxMinPointStr = maxMinPoint + ":";
//        if (maxSecPoint < 10)
//            maxSecPointStr = "0" + maxSecPoint;
//        else
//            maxSecPointStr = String.valueOf(maxSecPoint);

        dbHelper.insertRecode(intent.getStringExtra("curLocation"), mFilePath, mFileName, tvRecStartPoint.getText().toString());
        setResult(100);
        finish();
    }

    private void updateUI() {
        if (mRecState == REC_STOP) {
            mBtnStartRec.setText("Rec");
            mRecProgressBar.setProgress(0);
        } else if (mRecState == RECORDING)
            mBtnStartRec.setText("Stop");
    }
}
