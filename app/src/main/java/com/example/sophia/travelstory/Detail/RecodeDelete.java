package com.example.sophia.travelstory.Detail;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sophia.travelstory.R;

public class RecodeDelete extends Activity implements View.OnClickListener, OnCompletionListener {
    String selectItem[];
    // 미리 상수 선언
    private static final int REC_STOP = 0;
    private static final int RECORDING = 1;
    private static final int PLAY_STOP = 0;
    private static final int PLAYING = 1;
    private static final int PLAY_PAUSE = 2;

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private int mRecState = REC_STOP;
    private int mPlayerState = PLAY_STOP;
    private SeekBar mPlayProgressBar;
    private Button mBtnStartPlay, mBtnDelete;
    private String mFilePath, mFileName = null;
    private TextView mTvPlayMaxPoint;
    DetailDBHelper dbHelper;

    private int mCurRecTimeMs = 0;
    private int mCurProgressTimeDisplay = 0;

    // 재생시 SeekBar 처리
    Handler mProgressHandler2 = new Handler() {
        public void handleMessage(Message msg) {
            if (mPlayer == null) return;

            try {
                if (mPlayer.isPlaying()) {
                    mPlayProgressBar.setProgress(mPlayer.getCurrentPosition());
                    mProgressHandler2.sendEmptyMessageDelayed(0, 100);
                }
            } catch (IllegalStateException e) {
            } catch (Exception e) {
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recode_delete);

        // 미디어 레코더 저장할 파일 생성
        mFilePath = "/sdcard/Download/";
        dbHelper = new DetailDBHelper(getApplicationContext(), "RECODE.db", null, 1);

        mBtnStartPlay = (Button) findViewById(R.id.btnStartPlay);
        mBtnDelete = (Button) findViewById(R.id.btnDelete);
        mPlayProgressBar = (SeekBar) findViewById(R.id.playProgressBar);
        mTvPlayMaxPoint = (TextView) findViewById(R.id.tvPlayMaxPoint);

        Intent intent = getIntent();
        selectItem = intent.getStringArrayExtra("selectItem");
        Toast.makeText(this, "" + selectItem[0] + selectItem[1] + selectItem[2], Toast.LENGTH_SHORT).show();

        String str = selectItem[2].substring(3);
        mPlayProgressBar.setMax(Integer.parseInt(str) * 1000 + 500);

        mBtnStartPlay.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mTvPlayMaxPoint.setText(selectItem[2]);


    }

    // 버튼의 OnClick 이벤트 리스너
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartPlay:
                mBtnStartPlayOnClick();
                break;
            case R.id.btnDelete:
                mBtnDeleteOnClick();
                break;
            default:
                break;
        }
    }

    private void mBtnStartPlayOnClick() {
        mPlayerState = PLAYING;
        initMediaPlayer();
        startPlay();
    }

    private void mBtnDeleteOnClick() {
        if (mPlayer != null)
            mPlayer.stop();
        dbHelper.deleteRecode(selectItem[0], mFilePath, selectItem[1]);
        setResult(101);
        finish();
    }

    private void initMediaPlayer() {
        // 미디어 플레이어 생성
        if (mPlayer == null)
            mPlayer = new MediaPlayer();
        else
            mPlayer.reset();

        mPlayer.setOnCompletionListener(this);

        try {
            mPlayer.setDataSource(mFilePath + selectItem[1]);        //이놈이이ㅏㅣㅁㄴ얼ㅣㅁ넝ㄹㅣㅁ나ㅓㅇㄹㅣㅏ먼ㅇㄹㅣ먼ㅇㅣ럼ㄴㅇㅣ라ㅓㅁㄴㅇㄹ
            mPlayer.prepare();
            mPlayProgressBar.setProgress(0);
        } catch (Exception e) {
        }
    }

    // 재생 시작
    private void startPlay() {
        try {
            mPlayer.start();
            mProgressHandler2.sendEmptyMessageDelayed(0, 100);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onCompletion(MediaPlayer mp) {
        mPlayerState = PLAY_STOP; // 재생이 종료됨

        // 재생이 종료되면 즉시 SeekBar 메세지 핸들러를 호출한다.
        mProgressHandler2.sendEmptyMessageDelayed(0, 0);

    }
}