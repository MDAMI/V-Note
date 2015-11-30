package com.vnote.xfalcon.v_note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by mahad on 2015-11-26
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "V-NoteDB.db";
    public static final String NOTE_TABLE_NAME = "notes";
    public static final String NOTE_COLUMN_ID = "id";
    public static final String NOTE_COLUMN_TITLE = "title";
    public static final String NOTE_COLUMN_TEXT = "notetext";
    public static final String NOTE_COLUMN_TRANSCRIPT = "transcript";
    public static final String NOTE_COLUMN_AUDIO = "audio";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table notes" +
                        "(id integer primary key, title text, notetext text, transcript text, audio text"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }

    public boolean newNote(String title, String noteText, String transcript, String audio) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NOTE_COLUMN_TITLE, title);
            contentValues.put(NOTE_COLUMN_TEXT, noteText);
            contentValues.put(NOTE_COLUMN_TRANSCRIPT, transcript);
            contentValues.put(NOTE_COLUMN_AUDIO, audio);
            db.insert(NOTE_TABLE_NAME, null, contentValues);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from notes where id = " + id + "", null);
    }

    public NoteContainer getNote(int id){
        Cursor res = getData(id);
        String title = res.getString(res.getColumnIndex(NOTE_COLUMN_TITLE));
        String text = res.getString(res.getColumnIndex(NOTE_COLUMN_TEXT));
        String transcript = res.getString(res.getColumnIndex(NOTE_COLUMN_TRANSCRIPT));
        String audio = res.getString(res.getColumnIndex(NOTE_COLUMN_AUDIO));
        return new NoteContainer(title,text,transcript,audio);
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, NOTE_TABLE_NAME);
    }

    public boolean updateNote(Integer id, String title, String noteText, String transcript, String audio) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NOTE_COLUMN_TITLE, title);
            contentValues.put(NOTE_COLUMN_TEXT, noteText);
            contentValues.put(NOTE_COLUMN_TRANSCRIPT, transcript);
            contentValues.put(NOTE_COLUMN_AUDIO, audio);
            db.update(NOTE_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public Integer deleteNote(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(NOTE_TABLE_NAME, "id = ? ", new String[] {Integer.toString(id)});
    }

    public ArrayList<NoteContainer> getAllNotes(){
        ArrayList<NoteContainer> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from notes", null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            String title = res.getString(res.getColumnIndex(NOTE_COLUMN_TITLE));
            String text = res.getString(res.getColumnIndex(NOTE_COLUMN_TEXT));
            String transcript = res.getString(res.getColumnIndex(NOTE_COLUMN_TRANSCRIPT));
            String audio = res.getString(res.getColumnIndex(NOTE_COLUMN_AUDIO));
            arrayList.add(new NoteContainer(title,text,transcript,audio));
            res.moveToNext();
        }

        res.close();

        return arrayList;
    }

}
