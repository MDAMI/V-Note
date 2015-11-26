package com.vnote.xfalcon.v_note;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {

    private MediaPlayer mPlayer;
    private MediaRecorder recorder;
    private String OUTPUT_FILE;
    private Chronometer chronometer;
    private Button stop;
    private TextView txtText;
    protected static final int RESULT_SPEECH = 1;

    //private File[] files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        OUTPUT_FILE = Environment.getExternalStorageDirectory()+"/recorder.3gpp";

        txtText = (TextView) findViewById(R.id.txtText);


        try {
            beginRecording();



            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

            try {
                startActivityForResult(intent, RESULT_SPEECH);
                txtText.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }




        } catch (Exception e) {
            e.printStackTrace();
        }
        chronometer = (Chronometer) findViewById(R.id.chrono);
        stop = (Button) findViewById(R.id.stop_record_button);
        chronometer.start();

        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    stopRecording();
                }catch (Exception e){
                    e.printStackTrace();
                }
                chronometer.stop();
            }
        });
    }

    public void buttonTapped(View view){
        switch(view.getId()){
            case R.id.play_record_button:
                try{
                    playRecording();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    txtText.setText(text.get(0));
                }
                break;
            }

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



        //saveFile(outFile);

    }
    private void stopRecording(){

        Log.e("test", "THIS HAS BEEN REACHED");
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
