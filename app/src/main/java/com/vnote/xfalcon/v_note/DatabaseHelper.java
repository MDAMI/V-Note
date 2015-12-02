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
    public static final String NOTE_COLUMN_TEXT = "noteword";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE notes" +
                        "(id integer primary key, title text, noteword text);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }

    public int newNote(String title, String noteText) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NOTE_COLUMN_TITLE, title);
            contentValues.put(NOTE_COLUMN_TEXT, noteText);
            return (int) db.insert(NOTE_TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            return -1;
        }
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from notes where id = " + id + "", null);
    }

    public NoteContainer getNote(int id){
        Cursor res = getData(id);
        res.moveToFirst();
        String title = res.getString(res.getColumnIndex(NOTE_COLUMN_TITLE));
        String text = res.getString(res.getColumnIndex(NOTE_COLUMN_TEXT));
        return new NoteContainer(id,title,text);
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, NOTE_TABLE_NAME);
    }

    public boolean updateNote(Integer id, String title, String noteText) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NOTE_COLUMN_TITLE, title);
            contentValues.put(NOTE_COLUMN_TEXT, noteText);
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
            int id = res.getInt(res.getColumnIndex(NOTE_COLUMN_ID));
            String title = res.getString(res.getColumnIndex(NOTE_COLUMN_TITLE));
            String text = res.getString(res.getColumnIndex(NOTE_COLUMN_TEXT));
            arrayList.add(new NoteContainer(id, title, text));
            res.moveToNext();
        }

        res.close();

        return arrayList;
    }

    public ArrayList<NoteContainer> searchNotes(String term){
        ArrayList<NoteContainer> array = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM notes WHERE " + NOTE_COLUMN_TEXT + " LIKE \'%" + term + "%\' OR " + NOTE_COLUMN_TITLE + " LIKE \'%" + term +"%\'", null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            int id = res.getInt(res.getColumnIndex(NOTE_COLUMN_ID));
            String title = res.getString(res.getColumnIndex(NOTE_COLUMN_TITLE));
            String text = res.getString(res.getColumnIndex(NOTE_COLUMN_TEXT));
            array.add(new NoteContainer(id, title, text));
            res.moveToNext();
        }
        res.close();

        return array;
    }

}
