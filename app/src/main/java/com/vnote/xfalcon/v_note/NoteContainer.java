package com.vnote.xfalcon.v_note;

/**
 * Created by mahad on 2015-11-30.
 *
 */
public class NoteContainer {
    private String title;
    private String text;
    private int id;

    public NoteContainer(int id_in, String title_in, String text_in){
        title = title_in;
        text = text_in;
        id = id_in;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return title + "";
    }
}
