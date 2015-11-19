package com.vnote.xfalcon.v_note;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void addButtonPressed(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);

    }
    public void recordButtonPressed(View view) {
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);

    }
    public void searchButtonPressed(View view) {
        Intent intent = new Intent(this, null);
        startActivity(intent);

    }
}
