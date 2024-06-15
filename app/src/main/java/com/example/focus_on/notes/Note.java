package com.example.focus_on.notes;

public class Note {
    private String id;
    private String noteTitle;
    private String noteText;
    private String key;

    public Note() {
    }

    public Note(String noteTitle, String noteText, String key) {
        this.noteTitle = noteTitle;
        this.noteText = noteText;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
