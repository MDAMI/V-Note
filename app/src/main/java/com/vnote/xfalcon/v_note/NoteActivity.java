package com.vnote.xfalcon.v_note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mahad on 2015-11-15.
 *
 */
public class NoteActivity extends AppCompatActivity {

    TextView title;
    TextView text;

    private DatabaseHelper db;
    NoteContainer note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        title = (TextView) findViewById(R.id.note_title);
        text = (TextView) findViewById(R.id.note_text);

        db = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int val = extras.getInt("id");

            if(val>0){
                note = db.getNote(val);

                title.setText(note.getTitle());
                text.setText(note.getText());
            }
        }
    }

    public void goHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
