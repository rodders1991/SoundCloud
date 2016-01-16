package com.example.george.soundboard;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by George on 02/01/2016.
 */
public class AudioCap extends Activity {
    private static final String LOG_TAG = "AudioCap";
    private String mFileName;
    public boolean isSet;


    private MediaRecorder mRecorder = null;
    private MediaPlayer   mPlayer = null;

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }
    public void prepare()
        {
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(mFileName);
                mPlayer.prepare();
            }
            catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }

        }

    public void start()
    {
        mPlayer.start();
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);


        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }


    private boolean recordStart = true;
    public void clickRecord(Button button)
    {
        isSet = true;
        onRecord(recordStart);
        if (recordStart) {
            button.setText("Stop recording");
        } else {
            button.setText("Start recording");
        }
        recordStart = !recordStart;
    }

    private boolean playStart = true;
    public void clickPlay (Button button)
    {
        onPlay(playStart);
        if (playStart) {
            button.setText("Stop playing");
        } else {
            button.setText("Start playing");
        }
        playStart = !playStart;
    }


    public AudioCap(int index) {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audio"+index+".3gp";
        isSet = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
