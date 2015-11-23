package com.vnote.xfalcon.v_note;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

public class RecordActivity extends AppCompatActivity {

    private MediaPlayer mPlayer;
    private MediaRecorder recorder;
    private String OUTPUT_FILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        OUTPUT_FILE = Environment.getExternalStorageDirectory()+"/recorder.3gpp";
    }
    public void buttonTapped(View view){
        switch(view.getId()){
            case R.id.start_record_button:
                try{
                    beginRecording();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.stop_record_button:
                try{
                    stopRecording();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.play_record_button:
                try{
                    playRecording();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }
    private void beginRecording() throws Exception{
        ditchMediaRecorder();
        File outFile = new File(OUTPUT_FILE);

        if (outFile.exists())
            outFile.delete();

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(OUTPUT_FILE);
        recorder.prepare();
        recorder.start();

    }
    private void stopRecording(){
        if (recorder != null)
        recorder.stop();

    }
    private void playRecording()  throws Exception{
        ditchMediaPlayer();
        mPlayer = new MediaPlayer();
        mPlayer.setDataSource(OUTPUT_FILE);
        mPlayer.prepare();
        mPlayer.start();

    }
    private void ditchMediaRecorder() {
        if(recorder != null)
            recorder.release();
    }
    private void ditchMediaPlayer() {
        if(mPlayer != null)
        {
            try{
                mPlayer.release();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    public void goHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
