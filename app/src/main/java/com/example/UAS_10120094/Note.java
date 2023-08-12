package com.example.UAS_10120094;

import com.google.firebase.Timestamp;

// Nim  : 10120094
// Nama : Tiara Trisanti Ramadhani
// Kelas: IF3

public class Note {
    String title;
    String content;
    Timestamp timestamp;

    public Note() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
