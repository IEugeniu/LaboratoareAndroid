package com.example.laboratorul_3.Model;

public class Information {
    private String title;
    private String link;

    public Information() {
        title = "null";
        link = "null";
    }

    public Information(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

