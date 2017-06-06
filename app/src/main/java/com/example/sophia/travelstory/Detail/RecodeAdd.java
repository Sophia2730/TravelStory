package com.example.sophia.travelstory.Detail;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.sophia.travelstory.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecodeAdd extends AppCompatActivity implements View.OnClickListener {
    private static final int REC_STOP = 0;
    private static final int RECORDING = 1;
    private static final int PLAY_STOP = 0;

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private int mRecState = REC_STOP;
    private SeekBar mRecProgressBar;
    private Button mBtnStartRec;
    private String mFilePath, mFileName = null;
    DetailDBHelper dbHelper;
    private int mCurRecTimeMs = 0;
    private int mCurProgressTimeDisplay = 0;

    // 녹음시 SeekBar처리
    Handler mProgressHandler = new Handler() {
        public void handleMessage(Message msg) {
            mCurRecTimeMs = mCurRecTimeMs + 100;
            mCurProgressTimeDisplay = mCurProgressTimeDisplay + 100;

            // 녹음시간이 음수이면 정지버튼을 눌러 정지시켰음을 의미하므로
            // SeekBar는 그대로 정지시키고 레코더를 정지시킨다.
            if (mCurRecTimeMs < 0) {
            }
            // 녹음시간이 아직 최대녹음제한시간보다 작으면 녹음중이라는 의미이므로
            // SeekBar의 위치를 옮겨주고 0.1초 후에 다시 체크하도록 한다.
            else if (mCurRecTimeMs < 60000) {
                mRecProgressBar.setProgress(mCurProgressTimeDisplay);
                mProgressHandler.sendEmptyMessageDelayed(0, 100);
            }
            // 녹음시간이 최대 녹음제한 시간보다 크면 녹음을 정지 시킨다.
            else {
                mBtnStartRecOnClick();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recode_add);
        dbHelper = new DetailDBHelper(getApplicationContext(), "RECODE.db", null, 1);

        // 미디어 레코더 저장할 파일 생성
        mFilePath = "/sdcard/Download/";

        // 파일명을 년도월일시간분초 로 생성 겹치는 상황 없애기
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm");

        // 파일명 위에서 정한 파일명을 WJ 폴더에 저장
        mFileName = timeStampFormat.format(new Date()).toString()
                + ".mp4";

        mBtnStartRec = (Button) findViewById(R.id.btnStartRec);
        mRecProgressBar = (SeekBar) findViewById(R.id.recProgressBar);

        mBtnStartRec.setOnClickListener(this);
    }

    // 버튼의 OnClick 이벤트 리스너
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
        if (mRecState == REC_STOP) {
            mRecState = RECORDING;
            startRec();
            updateUI();
        } else if (mRecState == RECORDING) {
            mRecState = REC_STOP;
            stopRec();
//            updateUI();
        }
    }

    // 녹음시작
    private void startRec() {
        mCurRecTimeMs = 0;
        mCurProgressTimeDisplay = 0;

        // SeekBar의 상태를 0.1초후 체크 시작
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
            mPlayer.setDataSource(mFilePath + mFileName);        //이놈이이ㅏㅣㅁㄴ얼ㅣㅁ넝ㄹㅣㅁ나ㅓㅇㄹㅣㅏ먼ㅇㄹㅣ먼ㅇㅣ럼ㄴㅇㅣ라ㅓㅁㄴㅇㄹ
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int point = mPlayer.getDuration();

        int maxMinPoint = point / 1000 / 60;
        int maxSecPoint = (point / 1000) % 60;
        String maxMinPointStr = "";
        String maxSecPointStr = "";

        if (maxMinPoint < 10)
            maxMinPointStr = "0" + maxMinPoint + ":";
        else
            maxMinPointStr = maxMinPoint + ":";
        if (maxSecPoint < 10)
            maxSecPointStr = "0" + maxSecPoint;
        else
            maxSecPointStr = String.valueOf(maxSecPoint);

        dbHelper.insertRecode(intent.getStringExtra("curLocation"), mFilePath, mFileName, maxMinPointStr + maxSecPointStr);
        Toast.makeText(RecodeAdd.this, "디비에 넣는 값\n" + "/" + mFilePath + "/" + mFileName + "/" + maxMinPointStr + maxSecPointStr, Toast.LENGTH_SHORT).show();
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
