package com.example.fixbug.objects;

public class EmailObject {
    private long id;
    private String title;
    private String content;
    private String from;
    private long sentDate;

    public EmailObject() {
    }

    public EmailObject(long id, String title, String content, String from, long sentDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.from = from;
        this.sentDate = sentDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public long getSentDate() {
        return sentDate;
    }

    public void setSentDate(long sentDate) {
        this.sentDate = sentDate;
    }
}
