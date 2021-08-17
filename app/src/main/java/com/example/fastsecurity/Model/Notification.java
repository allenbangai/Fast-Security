package com.example.fastsecurity.Model;

public class Notification {
    private String Title;
    private String Details;

    public Notification(){

    }

    public Notification(String title, String details) {
        Title = title;
        Details = details;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }


}
