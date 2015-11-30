package com.vnote.xfalcon.v_note;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper notes_db;
    private ListView obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notes_db = new DatabaseHelper(this);
        ArrayList<NoteContainer> arrayList = notes_db.getAllNotes();
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, R.layout.list_view_single, arrayList);
        obj = (ListView) findViewById(R.id.listView);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int id_To_Search = position + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
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
