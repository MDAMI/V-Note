package com.vnote.xfalcon.v_note;

/**
 * Created by mahad on 2015-11-30.
 *
 */
public class NoteContainer {
    private String title;
    private String text;
    private String transcript;
    private String audio;

    public NoteContainer(String title_in, String text_in, String transcript_in, String audio_in){
        title = title_in;
        text = text_in;
        transcript = transcript_in;
        audio = audio_in;
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

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}
