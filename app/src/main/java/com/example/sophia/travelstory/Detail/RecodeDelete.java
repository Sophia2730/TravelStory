package com.example.sophia.travelstory.Detail;


import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sophia.travelstory.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecodeDelete extends Activity implements View.OnClickListener, OnCompletionListener {

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
    private SeekBar mRecProgressBar, mPlayProgressBar;
    private Button mBtnStartRec, mBtnStartPlay, mBtnStopPlay;
    private String mFilePath, mFileName = null;
    private TextView mTvPlayMaxPoint;

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
        setContentView(R.layout.activity_main);

        // 미디어 레코더 저장할 파일 생성
        mFilePath = "/sdcard/Download/";

        // 파일명을 년도월일시간분초 로 생성 겹치는 상황 없애기
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm");

        // 파일명 위에서 정한 파일명을 WJ 폴더에 저장
        mFileName = "/WJ"
                + timeStampFormat.format(new Date()).toString()
                + "Rec.mp4";

        mBtnStartPlay = (Button) findViewById(R.id.btnStartPlay);
        mBtnStopPlay = (Button) findViewById(R.id.btnStopPlay);
        mPlayProgressBar = (SeekBar) findViewById(R.id.playProgressBar);
        mTvPlayMaxPoint = (TextView) findViewById(R.id.tvPlayMaxPoint);

        mBtnStartPlay.setOnClickListener(this);
        mBtnStopPlay.setOnClickListener(this);
    }

    // 버튼의 OnClick 이벤트 리스너
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartPlay:
                mBtnStartPlayOnClick();
                break;
            case R.id.btnStopPlay:
                mBtnStopPlayOnClick();
                break;
            default:
                break;
        }
    }

    private void mBtnStartPlayOnClick() {
        if (mPlayerState == PLAY_STOP) {
            mPlayerState = PLAYING;
            initMediaPlayer();
            startPlay();
            updateUI();
        } else if (mPlayerState == PLAYING) {
            mPlayerState = PLAY_PAUSE;
            pausePlay();
            updateUI();
        } else if (mPlayerState == PLAY_PAUSE) {
            mPlayerState = PLAYING;
            startPlay();
            updateUI();
        }
    }

    private void mBtnStopPlayOnClick() {
        if (mPlayerState == PLAYING || mPlayerState == PLAY_PAUSE) {
            mPlayerState = PLAY_STOP;
            stopPlay();
            releaseMediaPlayer();
            updateUI();
        }
    }

    private void initMediaPlayer() {
        // 미디어 플레이어 생성
        if (mPlayer == null)
            mPlayer = new MediaPlayer();
        else
            mPlayer.reset();

        mPlayer.setOnCompletionListener(this);
        String fullFilePath = mFilePath + mFileName;

        try {
            mPlayer.setDataSource("/sdcard/Download/WJ2017_06_03_23_52Rec.mp4");        //이놈이이ㅏㅣㅁㄴ얼ㅣㅁ넝ㄹㅣㅁ나ㅓㅇㄹㅣㅏ먼ㅇㄹㅣ먼ㅇㅣ럼ㄴㅇㅣ라ㅓㅁㄴㅇㄹ
            mPlayer.prepare();
            mPlayProgressBar.setProgress(0);
        } catch (Exception e) {
            Log.v("ProgressRecorder", "미디어 플레이어 Prepare Error ==========> " + e);
        }
    }

    // 재생 시작
    private void startPlay() {
        Log.v("ProgressRecorder", "startPlay().....");

        try {
            mPlayer.start();

            // SeekBar의 상태를 0.1초마다 체크
            mProgressHandler2.sendEmptyMessageDelayed(0, 100);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void pausePlay() {
        Log.v("ProgressRecorder", "pausePlay().....");

        // 재생을 일시 정지하고
        mPlayer.pause();

        // 재생이 일시정지되면 즉시 SeekBar 메세지 핸들러를 호출한다.
        mProgressHandler2.sendEmptyMessageDelayed(0, 0);
    }

    private void stopPlay() {
        Log.v("ProgressRecorder", "stopPlay().....");

        // 재생을 중지하고
        mPlayer.stop();

        // 즉시 SeekBar 메세지 핸들러를 호출한다.
        mProgressHandler2.sendEmptyMessageDelayed(0, 0);
    }

    private void releaseMediaPlayer() {
        Log.v("ProgressRecorder", "releaseMediaPlayer().....");
        mPlayer.release();
        mPlayer = null;
        mPlayProgressBar.setProgress(0);
    }

    public void onCompletion(MediaPlayer mp) {
        mPlayerState = PLAY_STOP; // 재생이 종료됨

        // 재생이 종료되면 즉시 SeekBar 메세지 핸들러를 호출한다.
        mProgressHandler2.sendEmptyMessageDelayed(0, 0);

        updateUI();
    }

    private void updateUI() {
        if (mRecState == REC_STOP) {
            mBtnStartRec.setText("Rec");
            mRecProgressBar.setProgress(0);
        } else if (mRecState == RECORDING)
            mBtnStartRec.setText("Stop");

        if (mPlayerState == PLAY_STOP) {
            mBtnStartPlay.setText("Play");
            mPlayProgressBar.setProgress(0);
        } else if (mPlayerState == PLAYING)
            mBtnStartPlay.setText("Pause");
        else if (mPlayerState == PLAY_PAUSE)
            mBtnStartPlay.setText("Start");
    }
}