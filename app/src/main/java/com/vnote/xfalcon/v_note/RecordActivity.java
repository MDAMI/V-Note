package com.vnote.xfalcon.v_note;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;


import java.util.ArrayList;


public class RecordActivity extends AppCompatActivity implements RecognitionListener {


    EditText resultsText;
    SpeechRecognizer speech;
    Intent recog;
    ArrayList<String> matches;
    private String LOG_TAG = "VoiceRecognitionActivity";
    private ProgressBar progress;
    private ToggleButton toggle;
    EditText title;
    EditText editText;
    private DatabaseHelper db;
    NoteContainer note;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        //OUTPUT_DIR = Environment.getExternalStorageDirectory() + "/Vnote/";
        //Calendar c = Calendar.getInstance();
        //OUTPUT_NAME = c.get(Calendar.YEAR) + c.get(Calendar.MONTH) + c.get(Calendar.DATE) + c.get(Calendar.HOUR) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND) + ".3gp";
        //OUTPUT_FILE = OUTPUT_DIR + OUTPUT_NAME + "";
//        Log.d("Output", OUTPUT_FILE);
//        try {
//            beginRecording();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        chronometer = (Chronometer) findViewById(R.id.chrono);
//        stop = (Button) findViewById(R.id.stop_record_button);
//        chronometer.start();
//
//        stop.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                try {
//                    stopRecording();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                chronometer.stop();
//            }
//        });
//
//
//        resultsText = (TextView) findViewById(R.id.speechResults);
//
//        start = (Button) findViewById(R.id.start_record_button);
//        start.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                startSpeechInput();
//            }
//
//        });
//

        progress = (ProgressBar) findViewById(R.id.progressBar);
        //resultsText = (EditText) findViewById(R.id.speechResults);
        toggle = (ToggleButton) findViewById(R.id.toggleButton);
        title = (EditText) findViewById(R.id.record_title);
        editText = (EditText) findViewById(R.id.speechResults);
        db = new DatabaseHelper(this);

        id = -1;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");

            if (id > 0) {
                note = db.getNote(id);

                title.setText(note.getTitle());
                editText.setText(note.getText());
            } else {
                id = db.newNote(title.getText().toString(), editText.getText().toString());
                note = new NoteContainer(id, title.getText().toString(), editText.getText().toString());
            }
        }

        progress.setVisibility(View.INVISIBLE);

        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);

        recog = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recog.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        recog.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        recog.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recog.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    progress.setVisibility(View.VISIBLE);
                    progress.setIndeterminate(true);
                    speech.startListening(recog);
                } else {
                    progress.setIndeterminate(false);
                    progress.setVisibility(View.INVISIBLE);
                    speech.stopListening();
                }
            }
        });
    }

//
//
//
//        /*check = 0;
//        start = (Button) findViewById(R.id.start_record_button);
//        start.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                check = 1;
//                speechHandler();
//            }
//
//        });
//
//        stop = (Button) findViewById(R.id.stop_record_button);
//
//        stop.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                check = 0;
//                speechHandler();
//            }
//        });*/
//
//    }
//
//    /*private void speechHandler() {
//        if(check == 1){
//            speech.startListening(recog);
//        }else{
//            speech.stopListening();
//        }
//    }*/
//
//    /*public void startSpeechInput() {
//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.recording));
//        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,10000);
//        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,10000);
//        //intent.putExtra("android.speech.extra.GET_AUDIO_FORMAT", "audio/AMR");
//        //intent.putExtra("android.speech.extra.GET_AUDIO", true);
//
//        try {
//            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(getApplicationContext(), "Speech Recognition Not Supported", Toast.LENGTH_SHORT).show();
//        }
//    }*/
//
//   /* @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case REQ_CODE_SPEECH_INPUT: {
//                if (resultCode == RESULT_OK && data != null) {
//                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    for(String str : result){
//                        resultsText.append(str);
//                    }
//                    /*Uri audioUri = data.getData();
//                    ContentResolver contentResolver = getContentResolver();
//                    try {
//                        InputStream input = contentResolver.openInputStream(audioUri);
//                        File file = new File(OUTPUT_DIR, OUTPUT_NAME);
//                        OutputStream output = new FileOutputStream(file);
//                        try {
//                            byte[] buffer = new byte[4 * 1024];
//                            int read;
//                            if (input != null) {
//                                while ((read = input.read(buffer)) != -1) {
//                                    output.write(buffer, 0, read);
//                                }
//                                output.flush();
//                                output.close();
//                            }
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                } else if (resultCode == RecognizerIntent.RESULT_AUDIO_ERROR) {
//                    Toast.makeText(getApplicationContext(), "Audio Error", Toast.LENGTH_SHORT).show();
//                } else if (resultCode == RecognizerIntent.RESULT_CLIENT_ERROR) {
//                    Toast.makeText(getApplicationContext(), "Client Error", Toast.LENGTH_SHORT).show();
//                } else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR) {
//                    Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
//                } else if (resultCode == RecognizerIntent.RESULT_NO_MATCH) {
//                    Toast.makeText(getApplicationContext(), "No Match", Toast.LENGTH_SHORT).show();
//                } else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR) {
//                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }
//    }*/
//
//
//   /* public void buttonTapped(View view) {
//        switch (view.getId()) {
//            case R.id.play_record_button:
//                try {
//                    playRecording();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
//    }*/
//
//    /*
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case RESULT_SPEECH: {
//                if (resultCode == RESULT_OK && null != data) {
//
//                    ArrayList<String> text = data
//                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//
//                    txtText.setText(text.get(0));
//                }
//                break;
//            }
//
//        }
//
//
//
//        Log.d("ActivityReturn", "onActivityResult running");
//        if (requestCode == VOICE_REQUEST_CODE) {
//            switch (resultCode) {
//                case RESULT_OK: {
//                    Log.d("ActivityReturn", "Made it to code block");
//                    Bundle bundle = data.getExtras();
//                    ArrayList<String> matches = bundle.getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
//                    Uri audioUri = data.getData();
//                    ContentResolver contentResolver = getContentResolver();
//                    try {
//                        InputStream filestream = contentResolver.openInputStream(audioUri);
//                        File file = new File(OUTPUT_DIR, OUTPUT_NAME);
//                        file.createNewFile();
//                        OutputStream output = new FileOutputStream(file);
//                        try {
//                            byte[] buffer = new byte[4 * 1024];
//                            int read;
//
//                            if (filestream != null) {
//                                while ((read = filestream.read(buffer)) != -1) {
//                                    output.write(buffer, 0, read);
//                                }
//                            }
//                            output.flush();
//                            output.close();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        if (filestream != null) {
//                            filestream.close();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }*/
//
//
//    /*private void beginRecording() throws Exception {
//        /*ditchMediaRecorder();
//        File outFile = new File(OUTPUT_FILE);
//
//        if (outFile.exists())
//            //noinspection ResultOfMethodCallIgnored
//            outFile.delete();
//
//        recorder = new MediaRecorder();
//        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//        recorder.setOutputFile(OUTPUT_FILE);
//        recorder.prepare();
//        recorder.start();
//
//
//        //saveFile(outFile);
//
//
//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra("android.speech.extra.GET_AUDIO_FORMAT", "audio/AMR");
//        intent.putExtra("android.speech.extra.GET_AUDIO", "true");
//
//        startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
//
//    }*/
//
//    /*private void stopRecording() {
//
//        Log.d("test", "THIS HAS BEEN REACHED");
//        if (recorder != null)
//            recorder.stop();
//
//    }*/
//
//   /* private void playRecording() throws Exception {
//        ditchMediaPlayer();
//        mPlayer = new MediaPlayer();
//        mPlayer.setDataSource(OUTPUT_FILE);
//        mPlayer.prepare();
//        mPlayer.start();
//
//    }
//
//    private void ditchMediaRecorder() {
//        if (recorder != null)
//            recorder.release();
//    }
//
//    private void ditchMediaPlayer() {
//        if (mPlayer != null) {
//            try {
//                mPlayer.release();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }*/
//

    public void goHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speech.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CharSequence titleChar = title.getText();
        String titleString = titleChar.toString() + "";
        note.setTitle(titleString);
        note.setText(editText.getText().toString());

        db.updateNote(id, note.getTitle(), note.getText());
        if (speech != null) {
            speech.destroy();
        }
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {
        progress.setIndeterminate(false);
        progress.setMax(10);
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progress.setProgress((int) rmsdB);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
        progress.setIndeterminate(true);
        toggle.setChecked(false);
    }

    @Override
    public void onError(int error) {
        String errorMessage = getErrorText(error);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        //text.setText(errorMessage);
    }

    @Override
    public void onResults(Bundle results) {
        matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        String text = "";
        if (matches != null) {
            text = matches.get(0);
        }
        editText.append(text);
        toggle.setChecked(false);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

}
