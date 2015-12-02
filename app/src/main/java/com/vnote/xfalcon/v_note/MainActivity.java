package com.vnote.xfalcon.v_note;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper notes_db;
    private ListView obj;
    EditText search;
    ArrayList<NoteContainer> arrayList;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notes_db = new DatabaseHelper(this);
        arrayList = notes_db.getAllNotes();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        obj = (ListView) findViewById(R.id.listView);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("CURSOR", Long.toString(id));
                Log.d("CURSOR", Integer.toString(position));
                int id_To_Search = (int) id;

                selectItem(id_To_Search);
            }
        });
        search = (EditText) findViewById(R.id.searchField);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("SEARCH", "Reached: " + s.toString());
                searchHandle(s);


            }
        });

    }

    private void selectItem(int id_To_Search) {
        int true_id = arrayList.get(id_To_Search).getId();

        Bundle dataBundle = new Bundle();
        dataBundle.putInt("id", true_id);

        Intent intent = new Intent(getApplicationContext(), RecordActivity.class);

        intent.putExtras(dataBundle);
        startActivity(intent);
    }

    private void searchHandle(Editable s) {
        Log.d("SEARCH", "Reached in method: " + s.toString());
        arrayList = notes_db.searchNotes(s.toString());
        String debug = "";
        for (NoteContainer rt : arrayList){
            debug += rt.getTitle();
        }
        Log.d("ARRAY: \n", debug);
        //arrayAdapter.notifyDataSetChanged()
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        obj.setAdapter(arrayAdapter);
        obj.invalidateViews();

    }

    /*public void addButtonPressed(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);

    }*/

    public void recordButtonPressed(View view) {
        Intent intent = new Intent(this, RecordActivity.class);
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("id", -1);
        intent.putExtras(dataBundle);
        startActivity(intent);

    }

    public void searchButtonPressed(View view) {

    }
}
